package com.algaworks.algafoodapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafoodapi.domain.model.Restaurante;

@Repository
public interface RestauranteRepositoryTests extends JpaRepository<Restaurante, Long> {
	
	List<Restaurante> nome(String nome);
	
	List<Restaurante> findByNome(String nome);
	
	@Query("FROM Restaurante r WHERE nome like %:nome%")
	List<Restaurante> buscarPeloNome(@PathVariable("nome") String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long id);
	
	List<Restaurante> buscarPeloNomeXml(@PathVariable("nome") String nome);
}
