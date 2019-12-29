package com.algaworks.algafoodapi.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNaoEncontradoException(Long id) {
		super(String.format("Restaurante de id %d não encontrado", id));
	}
}
