package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

	private final FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
		this.formaPagamentoRepository = formaPagamentoRepository;
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}
	
	public FormaPagamento buscarOuFalharDeUmRestaurante(Long idFormaPagamento, Restaurante restaurante) {
		FormaPagamento formaPagamento = buscarOuFalhar(idFormaPagamento);
		restaurante.validarFormaPagamentoAceita(formaPagamento);
		return formaPagamento;
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("FormaPagamento de id %d está em uso", id));
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);
		}
	}
}
