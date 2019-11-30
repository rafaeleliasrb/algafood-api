package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafoodapi.domain.model.Cozinha;

public interface CozinhaRepository {

	public List<Cozinha> listarTodos();
	public Optional<Cozinha> buscarPorId(Long id);
	public Cozinha salvar(Cozinha cozinha);
	public void remover(Cozinha cozinha);
	
}
