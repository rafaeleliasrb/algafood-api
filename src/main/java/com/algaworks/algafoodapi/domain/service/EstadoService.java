package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
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
		return estadoRepository.salvar(estado);
	}

	@Transactional
	public Estado atualizar(Long id, Estado estado) {
		Estado estadoAtual = estadoRepository.buscarPorId(id)
			.orElseThrow(entidadeNaoEncontradaSupplier(estado.getId()));
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return estadoRepository.salvar(estadoAtual);
	}
	
	public void remover(Long id) {
		Estado estado = estadoRepository.buscarPorId(id)
				.orElseThrow(entidadeNaoEncontradaSupplier(id));
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			    protected void doInTransactionWithoutResult(TransactionStatus status) {
			    	estadoRepository.remover(estado);
			    }
			});
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado de id %d está em uso", id));
		}
	}

	private Supplier<? extends EntidadeNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new EntidadeNaoEncontradaException(String.format("Estado de id %d não encontrado", id));
	}
}
