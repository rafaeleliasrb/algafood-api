package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.FormaPagamentoController;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formas-pagamento")
@Getter
@Setter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Débito")
	private String descricao;

	public FormaPagamentoModel(FormaPagamento formaPagamento) {
		this.id = formaPagamento.getId();
		this.descricao = formaPagamento.getDescricao();
	}
	
	public static FormaPagamentoModel criarFormaPagamentoModelComLinks(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel(formaPagamento);
		
		formaPagamentoModel.add(linkTo(methodOn(FormaPagamentoController.class)
				.buscar(formaPagamento.getId(), null)).withSelfRel());
		formaPagamentoModel.add(linkTo(FormaPagamentoController.class).withRel("formas-pagamento"));
		
		return formaPagamentoModel;
	}
}
