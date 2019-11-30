package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Estado> listarTodos() {
		return entityManager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Optional<Estado> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(Estado.class, id));
	}

	@Override
	public Estado salvar(Estado estado) {
		return entityManager.merge(estado);
	}

	@Override
	public void remover(Estado estado) {
		Optional<Estado> estadoOpt = buscarPorId(estado.getId());
		if(estadoOpt.isPresent())
			entityManager.remove(estadoOpt.get());
	}

}
