package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cidade> listarTodos() {
		return entityManager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Optional<Cidade> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(Cidade.class, id));
	}

	@Override
	public Cidade salvar(Cidade cidade) {
		return entityManager.merge(cidade);
	}

	@Override
	public void remover(Cidade cidade) {
		Optional<Cidade> cidadeOpt = buscarPorId(cidade.getId());
		if(cidadeOpt.isPresent())
			entityManager.remove(cidadeOpt.get());
	}

}
