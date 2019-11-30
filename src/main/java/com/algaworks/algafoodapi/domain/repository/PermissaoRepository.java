package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafoodapi.domain.model.Permissao;

public interface PermissaoRepository {

	public List<Permissao> listarTodos();
	public Optional<Permissao> buscarPorId(Long id);
	public Permissao salvar(Permissao permissao);
	public void remover(Permissao permissao);
	
}
