package com.algaworks.algafoodapi.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
	
	@ManyToOne
	private Produto produto;
	
	@ManyToOne
	private Pedido pedido;
	
	@Deprecated
	public ItemPedido() {}
	
	public ItemPedido(Integer quantidade, BigDecimal precoUnitario, Produto produto) {
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
		this.produto = produto;
		
		calcularPrecoTotal();
	}
	
	private void calcularPrecoTotal() {
		this.precoTotal = produto.getPreco().multiply(BigDecimal.valueOf(getQuantidade()));
	}
}
