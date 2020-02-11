package com.algaworks.algafoodapi.api.v2.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.model.view.RestauranteView;
import com.algaworks.algafoodapi.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CozinhaModel")
@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {

	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class})
	private Long idCozinha;
	
	@ApiModelProperty(example = "Cearense")
	@JsonView({RestauranteView.Resumo.class})
	private String nomeCozinha;

	public CozinhaModelV2(Cozinha cozinha) {
		this.idCozinha = cozinha.getId();
		this.nomeCozinha = cozinha.getNome();
	}
	
	public static CozinhaModelV2 criarCozinhaModelComLinks(Cozinha cozinha) {
		CozinhaModelV2 cozinhaModel = new CozinhaModelV2(cozinha);
		
		cozinhaModel.add(linkTo(methodOn(CozinhaControllerV2.class)
				.buscar(cozinhaModel.getIdCozinha())).withSelfRel());
		cozinhaModel.add(linkTo(CozinhaControllerV2.class).withRel("cozinhas"));
		
		return cozinhaModel;
	}
}
