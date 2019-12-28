package com.algaworks.algafoodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {
	
}
