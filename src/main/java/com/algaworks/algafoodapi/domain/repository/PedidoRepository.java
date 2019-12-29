package com.algaworks.algafoodapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	public List<Pedido> findAll();
}