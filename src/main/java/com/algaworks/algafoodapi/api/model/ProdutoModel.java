package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.RestauranteProdutoController;
import com.algaworks.algafoodapi.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafoodapi.domain.model.Produto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(example = "Cheese Burguer da casa", position = 5)
	private String nome;
	
	@ApiModelProperty(example = "Cheese Burguer com tempero especial", position = 10)
	private String descricao;
	
	@ApiModelProperty(example = "10.00", position = 15)
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", position = 20)
	private Boolean ativo;

	public ProdutoModel(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.ativo = produto.getAtivo();
	}
	
	public static ProdutoModel criarProdutoModelComLinksRestaurante(Produto produto, Long idRestaurante) {
		ProdutoModel produtoModel = new ProdutoModel(produto);
		
		produtoModel.add(linkTo(methodOn(RestauranteProdutoController.class).buscar(idRestaurante, produto.getId()))
				.withSelfRel());
		produtoModel.add(linkTo(methodOn(RestauranteProdutoController.class).listar(idRestaurante, null)).withRel("produtos"));
		produtoModel.add(linkTo(methodOn(RestauranteProdutoFotoController.class).buscar(idRestaurante, produto.getId()))
				.withRel("foto"));
		
		return produtoModel;
	}
	
	public static CollectionModel<ProdutoModel> criarCollectionProdutoModelComLinksRestaurante(
			Collection<Produto> produtos, Long idRestaurante) {
		CollectionModel<ProdutoModel> collectionModel = new CollectionModel<>(produtos.stream()
				.map(produto -> criarProdutoModelComLinksRestaurante(produto, idRestaurante))
				.collect(Collectors.toList()));
		
		collectionModel.add(linkTo(methodOn(RestauranteProdutoController.class).listar(idRestaurante, null))
				.withRel("produtos"));
		
		return collectionModel;
	}
}
