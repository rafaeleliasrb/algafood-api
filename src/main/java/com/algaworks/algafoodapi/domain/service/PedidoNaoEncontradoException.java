package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public PedidoNaoEncontradoException(Long id) {
		super(String.format("Pedido de id %d não encontrado", id));
	}

}
