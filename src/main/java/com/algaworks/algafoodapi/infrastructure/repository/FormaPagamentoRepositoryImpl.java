package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<FormaPagamento> listarTodos() {
		return entityManager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public Optional<FormaPagamento> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(FormaPagamento.class, id));
	}

	@Override
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return entityManager.merge(formaPagamento);
	}

	@Override
	public void remover(FormaPagamento formaPagamento) {
		Optional<FormaPagamento> formaPagamentoOpt = buscarPorId(formaPagamento.getId());
		if(formaPagamentoOpt.isPresent())
			entityManager.remove(formaPagamentoOpt.get());
	}

}
