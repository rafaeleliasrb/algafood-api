package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafoodapi.api.v1.controller.CidadeController;
import com.algaworks.algafoodapi.domain.model.Cidade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Fortaleza")
	private String nome;
	
	@ApiModelProperty(example = "Ceará")
	private String estado;

	public CidadeResumoModel(Cidade cidade) {
		this.id = cidade.getId();
		this.nome = cidade.getNome();
		this.estado = cidade.getEstado().getNome();
	}
	
	public static CidadeResumoModel criarCidadeResumoModelComLinks(Cidade cidade) {
		CidadeResumoModel cidadeResumoModel = new CidadeResumoModel(cidade);
		
		cidadeResumoModel.add(linkTo(methodOn(CidadeController.class).buscar(cidadeResumoModel.getId())).withSelfRel());
		cidadeResumoModel.add(linkTo(CidadeController.class).withRel("cidades"));
		
		return cidadeResumoModel;
	}
}
