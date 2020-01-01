package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

	public List<Produto> findByRestaurante(Restaurante restaurante);
	
	public Optional<Produto> findByIdAndRestaurante(Long id, Restaurante restaurante);

	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
	public List<Produto> findAtivosByRestaurante(Restaurante restaurante);
}
