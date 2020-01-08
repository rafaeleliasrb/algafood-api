package com.algaworks.algafoodapi.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradaException(Long idRestaurante, Long idProduto) {
		super(String.format("Foto do produto de id %d e restaurante id %d não encontrada", 
				idProduto, idRestaurante));
	}

}
