package com.algaworks.algafoodapi.domain.service;

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
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private final CozinhaRepository cozinhaRepository;
	private final TransactionTemplate transactionTemplate;

	@Autowired
	public CozinhaService(CozinhaRepository cozinhaRepository, TransactionTemplate transactionTemplate) {
		this.cozinhaRepository = cozinhaRepository;
		this.transactionTemplate = transactionTemplate;
	}
	
	@Transactional
	public Cozinha adicionar(Cozinha novaCozinha) {
		return cozinhaRepository.save(novaCozinha);
	}
	
	@Transactional
	public Cozinha atualizar(Long id, Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(""));
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cozinhaRepository.save(cozinhaAtual);
	}
	
	
	public void remover(Long id) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			    protected void doInTransactionWithoutResult(TransactionStatus status) {
			    	cozinhaRepository.deleteById(id);
			    }
			});
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de id %d está em uso", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha de id %d não encontrado", id));
		}
	}

}
