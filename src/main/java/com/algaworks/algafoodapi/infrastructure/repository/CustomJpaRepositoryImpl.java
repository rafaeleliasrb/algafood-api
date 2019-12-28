package com.algaworks.algafoodapi.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.algafoodapi.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	private EntityManager em;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName();
		
		TypedQuery<T> query = em.createQuery(jpql, getDomainClass()).setMaxResults(1);
		T entity = query.getSingleResult();
		
		return Optional.ofNullable(entity);
	}

	@Override
	public void detached(T entity) {
		em.detach(entity);
	}

}
