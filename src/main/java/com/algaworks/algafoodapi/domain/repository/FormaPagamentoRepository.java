package com.algaworks.algafoodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long>{
	
}
