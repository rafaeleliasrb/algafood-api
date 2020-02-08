package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.GrupoController;
import com.algaworks.algafoodapi.api.controller.GrupoPermissaoController;
import com.algaworks.algafoodapi.api.controller.UsuarioGrupoController;
import com.algaworks.algafoodapi.domain.model.Grupo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoModel extends RepresentationModel<GrupoModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerente")
	private String nome;

	public GrupoModel(Grupo grupo) {
		this.id = grupo.getId();
		this.nome = grupo.getNome();
	}
	
	public static GrupoModel criarGrupoModelComLinks(Grupo grupo) {
		GrupoModel grupoModel = new GrupoModel(grupo);
		
		grupoModel.add(linkTo(methodOn(GrupoController.class).buscar(grupo.getId())).withSelfRel());
		grupoModel.add(linkTo(methodOn(GrupoController.class).listar()).withRel("grupos"));
		grupoModel.add(linkTo(methodOn(GrupoPermissaoController.class).listar(grupo.getId())).withRel("permissoes"));
		
		return grupoModel;
	}
	
	public static CollectionModel<GrupoModel> criarCollectionGrupoModelComLinks(Collection<Grupo> grupos) {
		CollectionModel<GrupoModel> collectionModel = new CollectionModel<>(grupos.stream()
				.map(GrupoModel::criarGrupoModelComLinks).collect(Collectors.toList()));
		
		collectionModel.add(linkTo(GrupoController.class).withSelfRel());
		
		return collectionModel;
	}
	
	public static GrupoModel criarGrupoModelComLinksUsuario(Grupo grupo, Long idUsuario) {
		GrupoModel grupoModel = criarGrupoModelComLinks(grupo);
		grupoModel.add(linkTo(methodOn(UsuarioGrupoController.class).desassociar(idUsuario, grupo.getId()))
				.withRel("desassociar"));
		
		return grupoModel;
	}
	
	public static CollectionModel<GrupoModel> criarCollectionGrupoModelComLinksUsuario(Collection<Grupo> grupos, Long idUsuario) {
		CollectionModel<GrupoModel> collectionModel = new CollectionModel<>(grupos.stream()
				.map(grupo -> GrupoModel.criarGrupoModelComLinksUsuario(grupo, idUsuario))
				.collect(Collectors.toList()));
		
		collectionModel.add(linkTo(methodOn(UsuarioGrupoController.class).listar(idUsuario)).withRel("grupos"));
		collectionModel.add(linkTo(methodOn(UsuarioGrupoController.class).associar(idUsuario, null))
				.withRel("associar"));
		
		return collectionModel;
	}
}
