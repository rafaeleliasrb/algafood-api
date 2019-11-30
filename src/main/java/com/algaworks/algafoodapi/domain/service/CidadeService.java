package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	private final CidadeRepository cidadeRepository;
	private final EstadoRepository estadoRepository;

	@Autowired
	public CidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
		this.cidadeRepository = cidadeRepository;
		this.estadoRepository = estadoRepository;
	}

	@Transactional
	public Cidade adicionar(Cidade cidade) {
		return cidadeRepository.salvar(cidade);
	}

	@Transactional
	public Cidade atualizar(Long id, Cidade cidade) {
		Cidade cidadeAtual = cidadeRepository.buscarPorId(id)
			.orElseThrow(entidadeNaoEncontradaSupplier(cidade.getId()));
		Estado estado = estadoPorId(cidade.getEstado().getId());
		cidade.setEstado(estado);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		return cidadeRepository.salvar(cidadeAtual);
	}
	
	@Transactional
	public void remover(Long id) {
		Cidade cidade = cidadeRepository.buscarPorId(id)
				.orElseThrow(entidadeNaoEncontradaSupplier(id));
		cidadeRepository.remover(cidade);
	}


	private Estado estadoPorId(Long id) {
		return estadoRepository.buscarPorId(id)
				.orElseThrow(() -> new AssociacaoNaoEncontradaException(String.format("Estado de id %d não encontrado", id)));
	}
	
	private Supplier<? extends EntidadeNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new EntidadeNaoEncontradaException(String.format("Cidade de id %d não encontrado", id));
	}
}
