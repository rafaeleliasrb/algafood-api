package com.algaworks.algafoodapi.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradaException(Long id) {
		super(String.format("Forma Pagamento de id %d não foi encontrado", id));
	}
}
