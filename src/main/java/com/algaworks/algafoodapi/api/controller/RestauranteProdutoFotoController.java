package com.algaworks.algafoodapi.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.FotoProdutoModel;
import com.algaworks.algafoodapi.api.model.input.FotoProdutoInput;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.FotoProduto;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.FotoProdutoService;
import com.algaworks.algafoodapi.domain.service.FotoStorageService;
import com.algaworks.algafoodapi.domain.service.ProdutoService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {

	private final ProdutoService produtoService;
	private final RestauranteService restauranteService;
	private final FotoProdutoService fotoProdutoService;
	private final FotoStorageService fotoStorageService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;
	
	@Autowired
	public RestauranteProdutoFotoController(ProdutoService produtoService, RestauranteService restauranteService,
			FotoProdutoService catalogoFotoProdutoService, FotoStorageService fotoStorageService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.produtoService = produtoService;
		this.restauranteService = restauranteService;
		this.fotoProdutoService = catalogoFotoProdutoService;
		this.fotoStorageService = fotoStorageService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	FotoProdutoModel atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		Produto produto = produtoService.buscarOuFalhar(idProduto, restaurante);
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setId(produto.getId());
		fotoProduto.setProduto(produto);
		fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
		fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(FotoProdutoModel.class, fotoProdutoService.salvar(fotoProduto, 
						fotoProdutoInput.getArquivo().getInputStream()));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	FotoProdutoModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(FotoProdutoModel.class, fotoProduto);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void remover(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		fotoProdutoService.excluir(idRestaurante, idProduto);
	}
	
	@GetMapping
	ResponseEntity<InputStreamResource> servir(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@RequestHeader(name = "accept") String acceptheader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			InputStream inputStreamFoto = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

			List<MediaType> mediaTypesAceitasPeloCliente = MediaType.parseMediaTypes(acceptheader);
			
			verificaCompatibilidadeMediaTypes(mediaTypesAceitasPeloCliente, mediaTypeFoto);
			
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(inputStreamFoto));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificaCompatibilidadeMediaTypes(List<MediaType> mediaTypesAceitasPeloCliente,
			MediaType mediaTypeFoto) throws HttpMediaTypeNotAcceptableException {
		boolean isCompaivel = mediaTypesAceitasPeloCliente.stream()
			.anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));
		
		if(!isCompaivel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitasPeloCliente);
		}
	}
}
