package com.algaworks.algafoodapi.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.model.StatusPedido;
import com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String offset) {
		var builder = entityManager.getCriteriaBuilder();
		var criteria = builder.createQuery(VendaDiaria.class);
		var root = criteria.from(Pedido.class);

		var functionConvertTzInDataCriacao = builder.function("convert_tz", Date.class, 
				root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(offset));
		
		var functionDateInDataCriacao = builder.function("date", Date.class, functionConvertTzInDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class, 
				functionDateInDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		var predicates = predicatesVendasDiarias(filter, builder, root);
		
		criteria.select(selection);
		criteria.where(predicates.toArray(new Predicate[0]));
		criteria.groupBy(functionDateInDataCriacao);
		
		return entityManager.createQuery(criteria).getResultList();
	}

	private List<Predicate> predicatesVendasDiarias(VendaDiariaFilter filter, CriteriaBuilder builder, Root<Pedido> root) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(root.get("status").in(StatusPedido.ENTREGUE, StatusPedido.CONFIRMADO));
		
		if(filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		if(filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
		}
		if(filter.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
		}
		
		return predicates;
	}

	
}
