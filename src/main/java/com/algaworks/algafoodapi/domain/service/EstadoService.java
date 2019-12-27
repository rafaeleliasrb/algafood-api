package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private final EstadoRepository estadoRepository;

	@Autowired
	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id)
			.orElseThrow(entidadeNaoEncontradaSupplier(id));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
	    	estadoRepository.deleteById(id);
	    	estadoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado de id %d está em uso", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(id);
		}
	}

	private Supplier<? extends EntidadeNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new EstadoNaoEncontradaException(id);
	}
}
