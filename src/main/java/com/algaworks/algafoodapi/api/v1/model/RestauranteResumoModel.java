package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.RestauranteController;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Coco Bambu")
	private String nome;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;

	public RestauranteResumoModel(Restaurante restaurante) {
		this.id = restaurante.getId();
		this.nome = restaurante.getNome();
		this.taxaFrete = restaurante.getTaxaFrete();
		this.cozinha = new CozinhaModel(restaurante.getCozinha());
	}
	
	public static RestauranteResumoModel criarRestauranteResumoModelComLinks(Restaurante restaurante) {
		RestauranteResumoModel restauranteResumoModel = new RestauranteResumoModel(restaurante);
		
		restauranteResumoModel.add(linkTo(methodOn(RestauranteController.class).buscar(restaurante.getId())).withSelfRel());
		restauranteResumoModel.add(new Link(UriTemplate.of(linkTo(RestauranteController.class).toUri().toString(), 
				TemplateVariableEnum.projecaoVariables()), "restaurantes"));
		
		restauranteResumoModel.setCozinha(CozinhaModel.criarCozinhaModelComLinks(restaurante.getCozinha()));
		
		return restauranteResumoModel;
	}
	
	public static CollectionModel<RestauranteResumoModel> criarCollectorRestauranteResumoModelComLinks(
			List<Restaurante> restaurantes) {
		CollectionModel<RestauranteResumoModel> collectionModel = new CollectionModel<>(restaurantes.stream()
				.map(RestauranteResumoModel::criarRestauranteResumoModelComLinks)
				.collect(Collectors.toList()));
		
		collectionModel.add(new Link(UriTemplate.of(linkTo(RestauranteController.class).toUri().toString(), 
				TemplateVariableEnum.projecaoVariables()), "restaurantes"));
		
		return collectionModel;
	}
}
