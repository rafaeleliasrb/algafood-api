package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.RestauranteController;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteApenasNomeModel extends RepresentationModel<RestauranteApenasNomeModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Coco Bambu")
	private String nome;

	public RestauranteApenasNomeModel(Restaurante restaurante) {
		this.id = restaurante.getId();
		this.nome = restaurante.getNome();
	}
	
	public static RestauranteApenasNomeModel criarRestauranteApenasNomeModelComLinks(Restaurante restaurante) {
		RestauranteApenasNomeModel restauranteResumoModel = new RestauranteApenasNomeModel(restaurante);
		
		if(AlgaSecurity.podeConsultar()) {
			restauranteResumoModel.add(linkTo(methodOn(RestauranteController.class).buscar(restaurante.getId())).withSelfRel());
			restauranteResumoModel.add(new Link(UriTemplate.of(linkTo(RestauranteController.class).toUri().toString(), 
					TemplateVariableEnum.projecaoVariables()), "restaurantes"));
		}
		
		return restauranteResumoModel;
	}
	
	public static CollectionModel<RestauranteApenasNomeModel> criarCollectorRestauranteApenasNomeModelComLinks(
			List<Restaurante> restaurantes) {
		CollectionModel<RestauranteApenasNomeModel> collectionModel = new CollectionModel<>(restaurantes.stream()
				.map(RestauranteApenasNomeModel::criarRestauranteApenasNomeModelComLinks)
				.collect(Collectors.toList()));
		
		if(AlgaSecurity.podeConsultar()) {
			collectionModel.add(new Link(UriTemplate.of(linkTo(RestauranteController.class).toUri().toString(), 
					TemplateVariableEnum.projecaoVariables()), "restaurantes"));
		}
		
		return collectionModel;
	}
}
