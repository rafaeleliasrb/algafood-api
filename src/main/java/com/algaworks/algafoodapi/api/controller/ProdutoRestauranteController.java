package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.ProdutoModel;
import com.algaworks.algafoodapi.api.model.input.ProdutoInput;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import com.algaworks.algafoodapi.domain.service.ProdutoService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/produtos")
public class ProdutoRestauranteController {

	private final ProdutoRepository produtoRepository;
	private final ProdutoService produtoService;
	private final RestauranteService restauranteService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public ProdutoRestauranteController(ProdutoRepository produtoRepository, ProdutoService produtoService,
			RestauranteService restauranteService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.produtoRepository = produtoRepository;
		this.produtoService = produtoService;
		this.restauranteService = restauranteService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<ProdutoModel> listar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(ProdutoModel.class, 
						produtoRepository.findByRestaurante(restaurante));
	}
	
	@GetMapping("/{idProduto}")
	ProdutoModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		Produto produto = produtoService.buscarOuFalhar(idProduto, restaurante);
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(ProdutoModel.class, produto);
	}
	
	@PostMapping
	ResponseEntity<ProdutoModel> adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		Produto novoProduto = representationModelAssemblerAndDisassembler
				.toRepresentationModel(Produto.class, produtoInput);
		
		ProdutoModel produtoModel = representationModelAssemblerAndDisassembler
				.toRepresentationModel(ProdutoModel.class, produtoService.salvar(restaurante, novoProduto));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{idProduto}").buildAndExpand(produtoModel.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoModel);
	}
	
	@PutMapping("/{idProduto}")
	ProdutoModel atualizar(@PathVariable Long idRestaurante, @PathVariable Long idProduto, 
			@RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		Produto produtoAtual = produtoService.buscarOuFalhar(idProduto, restaurante);
		
		representationModelAssemblerAndDisassembler.copyProperties(produtoInput, produtoAtual);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(ProdutoModel.class, produtoService.salvar(restaurante, produtoAtual));
	}
}
