package com.algaworks.algafoodapi.domain.exception;

public class EntidateNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidateNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
