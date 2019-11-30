package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidateNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private CozinhaRepository cozinhaRepository;

	@Autowired
	public CozinhaService(CozinhaRepository cozinhaRepository) {
		this.cozinhaRepository = cozinhaRepository;
	}
	
	@Transactional
	public Cozinha adicionar(Cozinha novaCozinha) {
		return cozinhaRepository.salvar(novaCozinha);
	}
	
	@Transactional
	public Cozinha atualizar(Long id, Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.buscarPorId(id)
				.orElseThrow(() -> new EntidateNaoEncontradaException(""));
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cozinhaRepository.salvar(cozinhaAtual);
	}
	
	@Transactional
	public void remover(Long id) {
		Cozinha cozinhaAtual = cozinhaRepository.buscarPorId(id)
				.orElseThrow(() -> new EntidateNaoEncontradaException(""));
		try {
			cozinhaRepository.remover(cozinhaAtual);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida pois esta em uso", cozinhaAtual.getId()));
		}
	}
}
