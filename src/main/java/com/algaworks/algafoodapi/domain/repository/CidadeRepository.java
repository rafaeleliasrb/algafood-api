package com.algaworks.algafoodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Cidade;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long>{

}
