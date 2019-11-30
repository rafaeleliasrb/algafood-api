package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cozinha> listarTodos() {
		return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public Optional<Cozinha> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(Cozinha.class, id));
	}

	@Transactional
	@Override
	public Cozinha salvar(Cozinha cozinha) {
		return entityManager.merge(cozinha);
	}

	@Transactional
	@Override
	public void remover(Cozinha cozinha) {
		Optional<Cozinha> cozinhaOpt = buscarPorId(cozinha.getId());
		if(cozinhaOpt.isPresent())
			entityManager.remove(cozinhaOpt.get());
	}

}
