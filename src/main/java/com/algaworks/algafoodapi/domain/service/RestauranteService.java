package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private final RestauranteRepository restauranteRepository;
	private final CozinhaRepository cozinhaRepository;
	private final CidadeRepository cidadeRepository;
	private final FormaPagamentoService formaPagamentoService;

	@Autowired
	public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository,
			CidadeRepository cidadeRepository, FormaPagamentoService formaPagamentoService) {
		this.restauranteRepository = restauranteRepository;
		this.cozinhaRepository = cozinhaRepository;
		this.cidadeRepository = cidadeRepository;
		this.formaPagamentoService = formaPagamentoService;
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Cozinha cozinha = cozinhaPorId(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		
		Cidade cidade = cidadePorId(restaurante.getEndereco().getCidade().getId());
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarOuFalha(Long id) {
		return restauranteRepository.findById(id)
				.orElseThrow(entidadeNaoEncontradaSupplier(id));
	}

	@Transactional
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(id);
		}
	}

	@Transactional
	public void ativar(Long idRestaurante) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		restaurante.ativar();
	}
	
	@Transactional
	public void inativar(Long idRestaurante) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		restaurante.inativar();
	}

	@Transactional
	public void desassociarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
		
		restaurante.associarFormaPagamento(formaPagamento);
	}
	
	private Cozinha cozinhaPorId(Long id) {
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new AssociacaoNaoEncontradaException(String.format("Cozinha de id %d não encontrada", id)));
	}

	private Cidade cidadePorId(Long id) {
		return cidadeRepository.findById(id)
				.orElseThrow(() -> new AssociacaoNaoEncontradaException(String.format("Cidade de id %d não encontrada", id)));
	}
	
	private Supplier<? extends EntidadeNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new RestauranteNaoEncontradaException(id);
	}
}
