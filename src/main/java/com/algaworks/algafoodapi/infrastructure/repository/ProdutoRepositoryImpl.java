package com.algaworks.algafoodapi.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.model.FotoProduto;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public FotoProduto salvar(FotoProduto fotoProduto) {
		return entityManager.merge(fotoProduto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto fotoProduto) {
		entityManager.remove(fotoProduto);
	}

}
