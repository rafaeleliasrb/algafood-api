package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

	Optional<Pedido> findByCodigo(String codigo);
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	public List<Pedido> findAll();
	
	@Query("select case when count(pedido.id) > 0 then true else false end "
			+ "from Pedido pedido join pedido.restaurante restaurante "
			+ "join restaurante.responsaveis responsavel "
			+ "where responsavel.id = :idResponsavel and pedido.codigo = :codigoPedido")
	public boolean gerenciaRestauranteDoPedido(String codigoPedido, Long idResponsavel);
}
