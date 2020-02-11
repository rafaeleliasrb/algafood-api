package com.algaworks.algafoodapi.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafoodapi.api.v1.model.FotoProdutoModel;
import com.algaworks.algafoodapi.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.FotoProduto;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.FotoProdutoService;
import com.algaworks.algafoodapi.domain.service.FotoStorageService;
import com.algaworks.algafoodapi.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafoodapi.domain.service.ProdutoService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{idRestaurante}/produtos/{idProduto}/foto",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

	private final ProdutoService produtoService;
	private final RestauranteService restauranteService;
	private final FotoProdutoService fotoProdutoService;
	private final FotoStorageService fotoStorageService;
	
	@Autowired
	public RestauranteProdutoFotoController(ProdutoService produtoService, RestauranteService restauranteService,
			FotoProdutoService catalogoFotoProdutoService, FotoStorageService fotoStorageService) {
		this.produtoService = produtoService;
		this.restauranteService = restauranteService;
		this.fotoProdutoService = catalogoFotoProdutoService;
		this.fotoStorageService = fotoStorageService;
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@Valid FotoProdutoInput fotoProdutoInput,
			@RequestPart(required = true) MultipartFile arquivo) throws IOException {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		Produto produto = produtoService.buscarOuFalhar(idProduto, restaurante);
		
		FotoProduto fotoProduto = criarFotoProduto(fotoProdutoInput, produto);
		
		return FotoProdutoModel.criarFotoProdutoModelComLinksRestauranteProduto(
				fotoProdutoService.salvar(fotoProduto, fotoProdutoInput.getArquivo().getInputStream()), 
				idRestaurante, idProduto);
	}
	
	@GetMapping
	public FotoProdutoModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
		
		return FotoProdutoModel.criarFotoProdutoModelComLinksRestauranteProduto(fotoProduto, idRestaurante, idProduto);
	}
	
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<Object> servir(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@RequestHeader(name = "accept") String acceptheader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

			List<MediaType> mediaTypesAceitasPeloCliente = MediaType.parseMediaTypes(acceptheader);
			
			verificaCompatibilidadeMediaTypes(mediaTypesAceitasPeloCliente, mediaTypeFoto);
			
			if(fotoRecuperada.hasInputStream()) {
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			} 
			else {
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		fotoProdutoService.excluir(idRestaurante, idProduto);
	}
	
	private FotoProduto criarFotoProduto(FotoProdutoInput fotoProdutoInput, Produto produto) {
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setId(produto.getId());
		fotoProduto.setProduto(produto);
		fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
		fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());
		return fotoProduto;
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
