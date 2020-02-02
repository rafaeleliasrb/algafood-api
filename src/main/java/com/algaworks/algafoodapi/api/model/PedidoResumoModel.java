package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.PedidoController;
import com.algaworks.algafoodapi.domain.model.Pedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

	@ApiModelProperty(example = "f229e133-2c0a-11ea-9866-d09466a32949")
	private String codigo;
	
	@ApiModelProperty(example = "298.9")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "308.9")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z")
	private OffsetDateTime dataCriacao;

	private RestauranteResumoModel restaurante;
	
	private UsuarioModel cliente;

	public PedidoResumoModel(Pedido pedido) {
		RestauranteResumoModel restauranteResumoModel = new RestauranteResumoModel(pedido.getRestaurante());
		UsuarioModel usuarioModel = new UsuarioModel(pedido.getCliente());

		this.codigo = pedido.getCodigo();
		this.subtotal = pedido.getSubtotal();
		this.taxaFrete = pedido.getTaxaFrete();
		this.valorTotal = pedido.getValorTotal();
		this.status = pedido.getStatus().getDescricao();
		this.dataCriacao = pedido.getDataCriacao();
		this.restaurante = restauranteResumoModel;
		this.cliente = usuarioModel;
	}
	
	public static PedidoResumoModel criarPedidoResumoModelComLinks(Pedido pedido) {
		PedidoResumoModel pedidoModel = new PedidoResumoModel(pedido);
		pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedidoModel.getCodigo())).withSelfRel());
		pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		
		pedidoModel.setRestaurante(RestauranteResumoModel.criarRestauranteResumoModelComLinks(pedido.getRestaurante()));
		pedidoModel.setCliente(UsuarioModel.criarUsuarioModelComLinks(pedido.getCliente()));
		
		return pedidoModel;
	}
}
