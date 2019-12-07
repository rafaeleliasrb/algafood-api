package com.algaworks.algafoodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafoodapi.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> buscar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> buscarComFreteGratis(String nome);
}
