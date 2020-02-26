package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.ItemPedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "itens")
@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Cheese Burguer")
	private String produtoNome;
	
	@ApiModelProperty(example = "2")
	private Integer quantidade;
	
	@ApiModelProperty(example = "15.00")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "30.00")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Ao ponto")
	private String observacao;

	public ItemPedidoModel(ItemPedido itemPedido) {
		this.produtoId = itemPedido.getProduto().getId();
		this.produtoNome = itemPedido.getProduto().getNome();
		this.quantidade = itemPedido.getQuantidade();
		this.precoUnitario = itemPedido.getPrecoUnitario();
		this.precoTotal = itemPedido.getPrecoTotal();
		this.observacao = itemPedido.getObservacao();
	}
	
	public static ItemPedidoModel criarItemPedidoModelComLinks(ItemPedido itemPedido, Long idRestaurante) {
		ItemPedidoModel itemPedidoModel = new ItemPedidoModel(itemPedido);
		
		if(AlgaSecurity.podeConsultar()) {
			itemPedidoModel.add(linkTo(methodOn(RestauranteProdutoController.class)
					.buscar(idRestaurante, itemPedidoModel.getProdutoId())).withRel("produto"));
		}
		
		return itemPedidoModel;
	}
}
