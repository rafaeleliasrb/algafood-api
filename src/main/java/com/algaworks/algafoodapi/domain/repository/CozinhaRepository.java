package com.algaworks.algafoodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
}
