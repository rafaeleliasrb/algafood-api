package com.algaworks.algafoodapi.api.v1.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.model.ProdutoModel;
import com.algaworks.algafoodapi.api.v1.model.input.ProdutoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import com.algaworks.algafoodapi.domain.service.ProdutoService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{idRestaurante}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	private final ProdutoRepository produtoRepository;
	private final ProdutoService produtoService;
	private final RestauranteService restauranteService;

	@Autowired
	public RestauranteProdutoController(ProdutoRepository produtoRepository, ProdutoService produtoService,
			RestauranteService restauranteService) {
		this.produtoRepository = produtoRepository;
		this.produtoService = produtoService;
		this.restauranteService = restauranteService;
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<ProdutoModel> listar(@PathVariable Long idRestaurante, 
			@RequestParam(required = false) Boolean incluirInativos) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		List<Produto> produtos = produtoRepository.findByRestaurante(restaurante).stream()
					.filter(produto -> !(incluirInativos != null && incluirInativos) && produto.getAtivo())
					.collect(Collectors.toList());
		
		return ProdutoModel.criarCollectionProdutoModelComLinksRestaurante(produtos, idRestaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{idProduto}")
	public ProdutoModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		return ProdutoModel.criarProdutoModelComLinksRestaurante(produtoService.buscarOuFalhar(idProduto, restaurante), 
				idRestaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesFuncionais
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		Produto novoProduto =  produtoInput.novoProduto();
				
		return ProdutoModel.criarProdutoModelComLinksRestaurante(produtoService.salvar(restaurante, novoProduto), 
				idRestaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesFuncionais
	@Override
	@PutMapping("/{idProduto}")
	public ProdutoModel atualizar(@PathVariable Long idRestaurante, @PathVariable Long idProduto, 
			@RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		Produto produtoAtualizado = produtoInput.produtoAtualizado(idProduto, restaurante, produtoService);
		
		return ProdutoModel.criarProdutoModelComLinksRestaurante(produtoService.salvar(restaurante, produtoAtualizado), 
				idRestaurante);
	}
}
