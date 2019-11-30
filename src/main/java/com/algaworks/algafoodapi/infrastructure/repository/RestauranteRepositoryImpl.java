package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> listarTodos() {
		return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Optional<Restaurante> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(Restaurante.class, id));
	}

	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}

	@Override
	public void remover(Restaurante restaurante) {
		Optional<Restaurante> restauranteOpt = buscarPorId(restaurante.getId());
		if(restauranteOpt.isPresent())
			entityManager.remove(restauranteOpt.get());
	}

}
