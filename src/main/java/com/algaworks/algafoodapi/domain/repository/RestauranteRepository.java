package com.algaworks.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
	JpaSpecificationExecutor<Restaurante> {
	
	@Query("SELECT CASE WHEN count(responsavel.id) > 0 THEN true ELSE false END "
			+ "FROM Restaurante restaurante INNER JOIN restaurante.responsaveis responsavel "
			+ "WHERE restaurante.id = :idRestaurante AND responsavel.id = :idUsuario")
	public Boolean ehUsuarioResponsavel(Long idRestaurante, Long idUsuario);
}
