package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafoodapi.domain.model.Restaurante;

public interface RestauranteRepository {

	public List<Restaurante> listarTodos();
	public Optional<Restaurante> buscarPorId(Long id);
	public Restaurante salvar(Restaurante cozinha);
	public void remover(Restaurante cozinha);
	
}
