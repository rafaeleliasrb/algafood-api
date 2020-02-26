package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.CozinhaController;
import com.algaworks.algafoodapi.api.v1.model.view.RestauranteView;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class})
	private Long id;
	
	@ApiModelProperty(example = "Cearense")
	@JsonView({RestauranteView.Resumo.class})
	private String nome;

	public CozinhaModel(Cozinha cozinha) {
		this.id = cozinha.getId();
		this.nome = cozinha.getNome();
	}
	
	public static CozinhaModel criarCozinhaModelComLinks(Cozinha cozinha) {
		CozinhaModel cozinhaModel = new CozinhaModel(cozinha);
		
		if(AlgaSecurity.podeConsultar()) {
			cozinhaModel.add(linkTo(methodOn(CozinhaController.class).buscar(cozinhaModel.getId())).withSelfRel());
			cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
		}
		
		return cozinhaModel;
	}
}
