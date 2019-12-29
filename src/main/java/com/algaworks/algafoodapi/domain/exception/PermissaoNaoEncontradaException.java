package com.algaworks.algafoodapi.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PermissaoNaoEncontradaException(Long id) {
		super(String.format("Permissão com id %d não encontrada", id));
	}
}
