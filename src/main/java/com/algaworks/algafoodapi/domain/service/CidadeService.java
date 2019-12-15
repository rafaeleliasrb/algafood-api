package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.CidadeNaoEncontradaException;
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
		return cidadeRepository.save(cidade);
	}

	@Transactional
	public Cidade atualizar(Long id, Cidade cidade) {
		Cidade cidadeAtual = buscarOuFalhar(id);
		Estado estado = estadoPorId(cidade.getEstado().getId());
		cidade.setEstado(estado);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		return cidadeRepository.save(cidadeAtual);
	}

	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id)
			.orElseThrow(entidadeNaoEncontradaSupplier(id));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		}
	}


	private Estado estadoPorId(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new AssociacaoNaoEncontradaException(String.format("Estado de id %d não encontrado", id)));
	}
	
	private Supplier<? extends CidadeNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new CidadeNaoEncontradaException(id);
	}
}
