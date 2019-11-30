package com.algaworks.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	public List<FormaPagamento> listarTodos();
	public Optional<FormaPagamento> buscarPorId(Long id);
	public FormaPagamento salvar(FormaPagamento formaPagamento);
	public void remover(FormaPagamento formaPagamento);
	
}
