package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto salvar(FotoProduto fotoProduto);
	
	void delete(FotoProduto fotoProduto);
}
