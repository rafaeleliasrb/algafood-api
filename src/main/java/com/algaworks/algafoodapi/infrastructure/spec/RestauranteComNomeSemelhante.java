package com.algaworks.algafoodapi.infrastructure.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafoodapi.domain.model.Restaurante;

import lombok.AllArgsConstructor;

@Deprecated
@AllArgsConstructor
public class RestauranteComNomeSemelhante implements Specification<Restaurante> {

	private static final long serialVersionUID = 1L;

	private String nome;
	
	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return builder.like(root.get("nome"), "%" + nome + "%");
	}

}
