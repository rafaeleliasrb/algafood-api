package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafoodapi.api.v1.controller.RestauranteFormaPagamentoController;
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
	
	public static CollectionModel<FormaPagamentoModel> criarCollectorFormaPagamentoModelComLink(
			Collection<FormaPagamento> formasPagamento) {
		CollectionModel<FormaPagamentoModel> collectionModel = 
				new CollectionModel<>(formasPagamento.stream()
						.map(FormaPagamentoModel::criarFormaPagamentoModelComLinks).collect(Collectors.toList()));
		
		collectionModel.add(linkTo(FormaPagamentoController.class).withRel("formas-pagamento"));
		
		return collectionModel;
	}
	
	public static FormaPagamentoModel criarFormaPagamentoModelComLinkRestaurante(
			FormaPagamento formaPagamento, Long idRestaurante) {
		FormaPagamentoModel formaPagamentoModel = criarFormaPagamentoModelComLinks(formaPagamento);
		
		formaPagamentoModel.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.desassociar(idRestaurante, formaPagamento.getId())).withRel("desassociar"));
		
		return formaPagamentoModel;
	}

	public static CollectionModel<FormaPagamentoModel> criarCollectorFormaPagamentoModelComLinkRestaurante(
			Collection<FormaPagamento> formasPagamento, Long idRestaurante) {
		CollectionModel<FormaPagamentoModel> collectionModel = new CollectionModel<>(formasPagamento.stream()
						.map(formaPagamento -> criarFormaPagamentoModelComLinkRestaurante(formaPagamento, idRestaurante))
						.collect(Collectors.toList()));
		
		collectionModel.add(linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(idRestaurante)).withSelfRel());
		collectionModel.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.associar(idRestaurante, null)).withRel("associar"));
		
		return collectionModel;
	}
}
