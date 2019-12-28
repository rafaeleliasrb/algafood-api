package com.algaworks.algafoodapi.domain.exception;

public class ProdutoRestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoRestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ProdutoRestauranteNaoEncontradoException(Long idProduto, Long idRestaurante) {
		super(String.format("Produto de id %d não encontrado para o Restaurante %d", idProduto, idRestaurante));
	}
}
