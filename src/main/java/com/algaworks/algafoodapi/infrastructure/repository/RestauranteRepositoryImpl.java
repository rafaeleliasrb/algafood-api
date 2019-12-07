package com.algaworks.algafoodapi.infrastructure.repository;

import static com.algaworks.algafoodapi.infrastructure.spec.RestauranteSpecificationFactory.comFreteGratis;
import static com.algaworks.algafoodapi.infrastructure.spec.RestauranteSpecificationFactory.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> buscar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		var parametros = new HashMap<String, Object>();
		var jpql = new StringBuilder();
				jpql.append("FROM Restaurante WHERE 1=1 ");
	
		if(StringUtils.hasLength(nome)) {
			jpql.append("AND nome like :nome ");
			parametros.put("nome", '%' + nome + '%');
		}
		
		if(taxaFreteInicial != null) {
			jpql.append("AND taxaFrete >= :taxaFreteInicial ");
			parametros.put("taxaFreteInicial", taxaFreteInicial);
		}
		
		if(taxaFreteFinal != null) {
			jpql.append("AND taxaFrete <= :taxaFreteFinal ");
			parametros.put("taxaFreteFinal", taxaFreteFinal);
		}
		
		TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach(query::setParameter);
		
		return query.getResultList();
	}

	@Override
	public List<Restaurante> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> rootRestaurante = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(nome)) {
			predicates.add(builder.like(rootRestaurante.get("nome"), '%' + nome + '%'));
		}
		
		if(taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(rootRestaurante.get("taxaFrete"), taxaFreteInicial));
		}
		
		if(taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(rootRestaurante.get("taxaFrete"), taxaFreteFinal));
		}

		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public List<Restaurante> buscarComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}

}
