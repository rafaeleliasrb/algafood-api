package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafoodapi.domain.model.Estado;

public interface EstadoRepository {

	public List<Estado> listarTodos();
	public Optional<Estado> buscarPorId(Long id);
	public Estado salvar(Estado estado);
	public void remover(Estado estado);
	
}
