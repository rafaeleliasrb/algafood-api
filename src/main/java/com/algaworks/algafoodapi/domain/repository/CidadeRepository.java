package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafoodapi.domain.model.Cidade;

public interface CidadeRepository {

	public List<Cidade> listarTodos();
	public Optional<Cidade> buscarPorId(Long id);
	public Cidade salvar(Cidade cidade);
	public void remover(Cidade cidade);
	
}
