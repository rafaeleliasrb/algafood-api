package com.algaworks.algafoodapi.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAnteriores;

	private StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return descricao;
	}

	public boolean isNaoPermiteAlterarStatusPara(StatusPedido novoStatusPedido) {
		return !this.statusAnteriores.contains(novoStatusPedido);
	}
}
