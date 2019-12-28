package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.ProdutoRestauranteNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	public Produto buscarOuFalhar(Long idProduto, Restaurante restaurante) {
		return produtoRepository.findByIdAndRestaurante(idProduto, restaurante)
				.orElseThrow(() -> new ProdutoRestauranteNaoEncontradoException(idProduto, restaurante.getId()));
	}

	@Transactional
	public Produto salvar(Restaurante restaurante, Produto novoProduto) {
		novoProduto.setRestaurante(restaurante);
		return produtoRepository.save(novoProduto);
	}
	
}
