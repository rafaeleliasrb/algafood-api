package com.algaworks.algafoodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Estado;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long>{
	
}
