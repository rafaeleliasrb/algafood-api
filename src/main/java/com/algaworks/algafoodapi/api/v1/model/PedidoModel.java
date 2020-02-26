package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafoodapi.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafoodapi.api.v1.controller.PedidoController;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.Pedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

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
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z")
	private OffsetDateTime dataCancelamento;
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z")
	private OffsetDateTime dataEntrega;

	private RestauranteApenasNomeModel restaurante;
	
	private EnderecoModel enderecoEntrega;
	
	private FormaPagamentoModel formaPagamento;
	
	private UsuarioModel cliente;
	
	private List<ItemPedidoModel> itens;
	
	public PedidoModel(Pedido pedido) {
		RestauranteApenasNomeModel restauranteResumoModel = new RestauranteApenasNomeModel(pedido.getRestaurante());
		EnderecoModel enderecoModel = new EnderecoModel(pedido.getEnderecoEntrega());
		FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel(pedido.getFormaPagamento());
		UsuarioModel usuarioModel = new UsuarioModel(pedido.getCliente());
		List<ItemPedidoModel> itensPedidoModel = pedido.getItens().stream().map(ItemPedidoModel::new)
				.collect(Collectors.toList());

		this.codigo = pedido.getCodigo();
		this.subtotal = pedido.getSubtotal();
		this.taxaFrete = pedido.getTaxaFrete();
		this.valorTotal = pedido.getValorTotal();
		this.status = pedido.getStatus().getDescricao();
		this.dataCriacao = pedido.getDataCriacao();
		this.dataConfirmacao = pedido.getDataConfirmacao();
		this.dataCancelamento = pedido.getDataCancelamento();
		this.dataEntrega = pedido.getDataEntrega();
		this.restaurante = restauranteResumoModel;
		this.enderecoEntrega = enderecoModel;
		this.formaPagamento = formaPagamentoModel;
		this.cliente = usuarioModel;
		this.itens = itensPedidoModel;
	}
	
	public static PedidoModel criarPedidoModelComLinks(Pedido pedido, AlgaSecurity algaSecurity) {
		PedidoModel pedidoModel = new PedidoModel(pedido);
		pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedidoModel.getCodigo())).withSelfRel());
		pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		
		if(algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			if(pedido.podeConfirmar()) {
				pedidoModel.add(linkTo(methodOn(FluxoPedidoController.class)
						.confirmar(pedidoModel.getCodigo())).withRel("confirmar"));
			}
			if(pedido.podeCancelar()) {
				pedidoModel.add(linkTo(methodOn(FluxoPedidoController.class)
						.cancelar(pedidoModel.getCodigo())).withRel("cancelar"));
			}
			if(pedido.podeEntregar()) {
				pedidoModel.add(linkTo(methodOn(FluxoPedidoController.class)
						.entregar(pedidoModel.getCodigo())).withRel("entregar"));
			}
		}
		
		pedidoModel.setRestaurante(RestauranteApenasNomeModel.criarRestauranteApenasNomeModelComLinks(pedido.getRestaurante()));
		pedidoModel.setEnderecoEntrega(EnderecoModel.criarEnderecoModelComLinks(pedido.getEnderecoEntrega()));
		pedidoModel.setFormaPagamento(FormaPagamentoModel.criarFormaPagamentoModelComLinks(pedido.getFormaPagamento()));
		pedidoModel.setCliente(UsuarioModel.criarUsuarioModelComLinks(pedido.getCliente()));
		pedidoModel.setItens(pedido.getItens().stream()
				.map(item -> ItemPedidoModel.criarItemPedidoModelComLinks(item, pedido.getRestaurante().getId()))
				.collect(Collectors.toList()));
		
		return pedidoModel;
	}
}
