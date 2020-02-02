package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.UsuarioController;
import com.algaworks.algafoodapi.domain.model.Usuario;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(example = "José", position = 5)
	private String nome;
	
	@ApiModelProperty(example = "jose@email.com", position = 10)
	private String email;

	public UsuarioModel(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
	}
	
	public static UsuarioModel criarUsuarioModelComLinks(Usuario usuario) {
		UsuarioModel usuarioModel = new UsuarioModel(usuario);
		
		usuarioModel.add(linkTo(methodOn(UsuarioController.class).buscar(usuarioModel.getId())).withSelfRel());
		
		usuarioModel.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
		
		return usuarioModel;
	}
	
	public static CollectionModel<UsuarioModel> criarCollectionUsuarioModelComLinks(Collection<Usuario> usuarios) {
		CollectionModel<UsuarioModel> collectionModel = new CollectionModel<UsuarioModel>(usuarios.stream()
				.map(UsuarioModel::criarUsuarioModelComLinks).collect(Collectors.toList()));
		
		collectionModel.add(linkTo(UsuarioController.class).withSelfRel());
		
		return collectionModel;
	}
}
