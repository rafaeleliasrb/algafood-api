package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private final EstadoRepository estadoRepository;
	private final TransactionTemplate transactionTemplate;

	@Autowired
	public EstadoService(EstadoRepository estadoRepository, TransactionTemplate transactionTemplate) {
		this.estadoRepository = estadoRepository;
		this.transactionTemplate = transactionTemplate;
	}

	@Transactional
	public Estado adicionar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public Estado atualizar(Long id, Estado estado) {
		Estado estadoAtual = buscarOuFalhar(id);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return estadoRepository.save(estadoAtual);
	}

	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id)
			.orElseThrow(entidadeNaoEncontradaSupplier(id));
	}
	
	public void remover(Long id) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			    protected void doInTransactionWithoutResult(TransactionStatus status) {
			    	estadoRepository.deleteById(id);
			    }
			});
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
