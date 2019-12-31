package com.algaworks.algafoodapi.domain.exception;

public class FormaPagamentoDeUmRestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoDeUmRestauranteNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoDeUmRestauranteNaoEncontradaException(Long idFormaPagamento, Long idRestaurante) {
		super(String.format("Forma de pagamento de id %d não encontrada para o restaurante de id %d", 
				idFormaPagamento, idRestaurante));
	}
}
