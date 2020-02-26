package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafoodapi.api.v1.controller.UsuarioController;
import com.algaworks.algafoodapi.api.v1.controller.UsuarioGrupoController;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
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
		
		if(AlgaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			usuarioModel.add(linkTo(methodOn(UsuarioController.class).buscar(usuarioModel.getId())).withSelfRel());
			usuarioModel.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
			usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuario.getId())).withRel("grupos-usuario"));
		}
		
		return usuarioModel;
	}
	
	public static CollectionModel<UsuarioModel> criarCollectionUsuarioModelComLinks(Collection<Usuario> usuarios) {
		CollectionModel<UsuarioModel> collectionModel = new CollectionModel<>(usuarios.stream()
				.map(UsuarioModel::criarUsuarioModelComLinks).collect(Collectors.toList()));
		
		if(AlgaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(linkTo(UsuarioController.class).withSelfRel());
		}
		
		return collectionModel;
	}
	
	public static UsuarioModel criarUsuarioModelComLinksRestaurante(Usuario responsavel, Long idRestaurante, AlgaSecurity algaSecurity) {
		UsuarioModel usuarioModel = UsuarioModel.criarUsuarioModelComLinks(responsavel);
		
		if(algaSecurity.podeGerenciarInformacoesCadastraisDoRestaurante()) {
			usuarioModel.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
					.remover(idRestaurante, responsavel.getId())).withRel("remover"));
		}
		
		return usuarioModel;
	}
	
	public static CollectionModel<UsuarioModel> criarCollectionUsuarioModelComLinksRestaurante(
			Collection<Usuario> responsaveis, Long idRestaurante, AlgaSecurity algaSecurity) {
		CollectionModel<UsuarioModel> collectionModel = new CollectionModel<>(responsaveis.stream()
				.map(responsavel -> criarUsuarioModelComLinksRestaurante(responsavel, idRestaurante, algaSecurity))
				.collect(Collectors.toList()));
		
		if(AlgaSecurity.podeConsultar()) {
			collectionModel.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(idRestaurante)).withSelfRel());
		}
		
		if(algaSecurity.podeGerenciarInformacoesCadastraisDoRestaurante()) {
			collectionModel.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class).adicionar(idRestaurante, null))
					.withRel("adicionar"));
		}
		
		return collectionModel;
	}
}
