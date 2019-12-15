package com.algaworks.algafoodapi.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha de id %d não encontrado";

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long id) {
		super(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
	}
}
