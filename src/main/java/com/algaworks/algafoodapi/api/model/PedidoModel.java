package com.algaworks.algafoodapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;

	private RestauranteResumoModel restaurante;
	
	private EnderecoModel enderecoEntrega;
	
	private FormaPagamentoModel formaPagamento;
	
	private UsuarioModel cliente;
	
	private List<ItemPedidoModel> itens;
}
