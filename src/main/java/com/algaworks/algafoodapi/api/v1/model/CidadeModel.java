package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.CidadeController;
import com.algaworks.algafoodapi.domain.model.Cidade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representação de um cidade")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

//	@ApiModelProperty(value = "Id da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Fortaleza")
	private String nome;
	
	private EstadoModel estado;

	public CidadeModel(Cidade cidade) {
		this.id = cidade.getId();
		this.nome = cidade.getNome();
		this.estado = new EstadoModel(cidade.getEstado());
	}
	
	public static CidadeModel criarCidadeModelComLinks(Cidade cidade) {
		CidadeModel cidadeModel = new CidadeModel(cidade);
		
		cidadeModel.add(linkTo(methodOn(CidadeController.class).buscar(cidade.getId())).withSelfRel());
		cidadeModel.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));
		
		cidadeModel.setEstado(EstadoModel.criarEstadoModelComLinks(cidade.getEstado()));
		/*cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).buscar(cidadeModel.getEstado().getId()))
				.withSelfRel());*/
		
		return cidadeModel;
	}
	
	public static CollectionModel<CidadeModel> criarCollectionCidadeModelComLinks(List<Cidade> cidades) {
		CollectionModel<CidadeModel> collectionModel = 
				new CollectionModel<>(cidades.stream()
						.map(CidadeModel::criarCidadeModelComLinks).collect(Collectors.toList()));
		
		collectionModel.add(linkTo(CidadeController.class).withSelfRel());
		return collectionModel;
	}
}
