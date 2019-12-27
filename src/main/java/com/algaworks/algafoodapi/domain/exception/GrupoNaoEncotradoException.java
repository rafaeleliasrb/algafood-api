package com.algaworks.algafoodapi.domain.exception;

public class GrupoNaoEncotradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncotradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncotradoException(Long id) {
		super(String.format("Grupo de id %d não foi encontrado", id));
	}
}
