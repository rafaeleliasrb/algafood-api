package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafoodapi.api.v1.controller.PermissaoController;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.Permissao;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "permissoes")
@Getter
@Setter
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "CONSULTAR_COZINHAS")
	private String nome;
	
	@ApiModelProperty(example = "Permite consultar cozinhas")
	private String descricao;

	public PermissaoModel(Permissao permissao) {
		this.id = permissao.getId();
		this.nome = permissao.getNome();
		this.descricao = permissao.getDescricao();
	}
	
	public static CollectionModel<PermissaoModel> criarCollectionPermissaoModelComLinks(List<Permissao> permissoes) {
		CollectionModel<PermissaoModel> collectionModel = new CollectionModel<>(permissoes.stream()
					.map(PermissaoModel::new).collect(Collectors.toList()));
		
		if(AlgaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(linkTo(PermissaoController.class).withSelfRel());
		}
		
		return collectionModel;
	}
	
	public static PermissaoModel criarPermissaoModelComLinksGrupo(Permissao permissao, Long idGrupo) {
		PermissaoModel permissaoModel = new PermissaoModel(permissao);
		
		if(AlgaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			permissaoModel.add(linkTo(methodOn(GrupoPermissaoController.class).listar(idGrupo)).withRel("permissoes"));
			permissaoModel.add(linkTo(methodOn(GrupoPermissaoController.class).desatribuir(idGrupo, permissao.getId()))
					.withRel("desatribuir"));
		}
		
		return permissaoModel;
	}
	
	public static CollectionModel<PermissaoModel> criarCollectionPermissaoModelComLinksGrupo(
			Collection<Permissao> permissoes, Long idGrupo) {
		CollectionModel<PermissaoModel> collectionModel = new CollectionModel<>(permissoes.stream()
				.map(permissao -> criarPermissaoModelComLinksGrupo(permissao, idGrupo))
				.collect(Collectors.toList()));
		
		if(AlgaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(linkTo(methodOn(GrupoPermissaoController.class).listar(idGrupo)).withRel("permissoes"));
			collectionModel.add(linkTo(methodOn(GrupoPermissaoController.class).atribuir(idGrupo, null))
					.withRel("atribuir"));
		}
		
		return collectionModel;
	}
}
