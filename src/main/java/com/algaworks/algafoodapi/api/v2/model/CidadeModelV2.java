package com.algaworks.algafoodapi.api.v2.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.CidadeController;
import com.algaworks.algafoodapi.domain.model.Cidade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CidadeModel")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	@ApiModelProperty(example = "1")
	private Long idCidade;
	
	@ApiModelProperty(example = "Fortaleza")
	private String nomeCidade;
	
	@ApiModelProperty(example = "1")
	private Long idEstado;
	
	@ApiModelProperty(example = "Ceará")
	private String nomeEstado;

	public CidadeModelV2(Cidade cidade) {
		this.idCidade = cidade.getId();
		this.nomeCidade = cidade.getNome();
		this.idEstado = cidade.getEstado().getId();
		this.nomeEstado = cidade.getEstado().getNome();
	}
	
	public static CidadeModelV2 criarCidadeModelComLinks(Cidade cidade) {
		CidadeModelV2 cidadeModel = new CidadeModelV2(cidade);
		
		cidadeModel.add(linkTo(methodOn(CidadeController.class).buscar(cidade.getId())).withSelfRel());
		cidadeModel.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));
		
		return cidadeModel;
	}
	
	public static CollectionModel<CidadeModelV2> criarCollectionCidadeModelComLinks(List<Cidade> cidades) {
		CollectionModel<CidadeModelV2> collectionModel = 
				new CollectionModel<>(cidades.stream()
						.map(CidadeModelV2::criarCidadeModelComLinks).collect(Collectors.toList()));
		
		collectionModel.add(linkTo(CidadeController.class).withSelfRel());
		return collectionModel;
	}
}
