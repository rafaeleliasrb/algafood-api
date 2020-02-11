package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.EstadoController;
import com.algaworks.algafoodapi.domain.model.Estado;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoModel extends RepresentationModel<EstadoModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Ceará")
	private String nome;

	public EstadoModel(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
	}
	
	public static EstadoModel criarEstadoModelComLinks(Estado estado) {
		EstadoModel estadoModel = new EstadoModel(estado);
		
		estadoModel.add(linkTo(methodOn(EstadoController.class).buscar(estadoModel.getId())).withSelfRel());
		estadoModel.add(linkTo(methodOn(EstadoController.class).listar()).withRel("estados"));
		
		return estadoModel;
	}
	
	public static CollectionModel<EstadoModel> criarCollectorEstadoModelComLinks(List<Estado> estados) {
		CollectionModel<EstadoModel> collectionModel = new CollectionModel<>(estados.stream().map(EstadoModel::criarEstadoModelComLinks)
			.collect(Collectors.toList()));
		
		collectionModel.add(linkTo(EstadoController.class).withSelfRel());
		
		return collectionModel;
	}
}
