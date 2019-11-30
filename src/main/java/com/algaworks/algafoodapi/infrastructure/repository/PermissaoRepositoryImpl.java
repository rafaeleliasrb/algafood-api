package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Permissao;
import com.algaworks.algafoodapi.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Permissao> listarTodos() {
		return entityManager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Optional<Permissao> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(Permissao.class, id));
	}

	@Override
	public Permissao salvar(Permissao permissao) {
		return entityManager.merge(permissao);
	}

	@Override
	public void remover(Permissao permissao) {
		Optional<Permissao> permissaoOpt = buscarPorId(permissao.getId());
		if(permissaoOpt.isPresent())
			entityManager.remove(permissaoOpt.get());
	}

}
