package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.RestauranteController;
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

	public RestauranteResumoModel(Restaurante restaurante) {
		this.id = restaurante.getId();
		this.nome = restaurante.getNome();
	}
	
	public static RestauranteResumoModel criarRestauranteResumoModelComLinks(Restaurante restaurante) {
		RestauranteResumoModel restauranteResumoModel = new RestauranteResumoModel(restaurante);
		
		restauranteResumoModel.add(linkTo(methodOn(RestauranteController.class).buscar(restaurante.getId())).withSelfRel());
		restauranteResumoModel.add(linkTo(RestauranteController.class).withRel("restaurantes"));
		
		return restauranteResumoModel;
	}
}
