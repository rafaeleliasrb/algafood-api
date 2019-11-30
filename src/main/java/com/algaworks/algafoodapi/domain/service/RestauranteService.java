package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidateNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private RestauranteRepository restauranteRepository;
	private CozinhaRepository cozinhaRepository;

	@Autowired
	public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
		this.restauranteRepository = restauranteRepository;
		this.cozinhaRepository = cozinhaRepository;
	}

	@Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		Cozinha cozinha = cozinhaPorId(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		return restauranteRepository.salvar(restaurante);
	}

	@Transactional
	public Restaurante atualizar(Long id, Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteRepository.buscarPorId(id)
				.orElseThrow(entidadeNaoEncontradaSupplier(id));
		Cozinha cozinha = cozinhaPorId(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		return restauranteRepository.salvar(restauranteAtual);
	}

	@Transactional
	public void remover(Long id) {
		Restaurante restaurante = restauranteRepository.buscarPorId(id)
				.orElseThrow(entidadeNaoEncontradaSupplier(id));
		restauranteRepository.remover(restaurante);
	}
	
	private Cozinha cozinhaPorId(Long id) {
		return cozinhaRepository.buscarPorId(id)
				.orElseThrow(() -> new AssociacaoNaoEncontradaException(String.format("Cozinha de id %d não encontrada", id)));
	}

	private Supplier<? extends EntidateNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new EntidateNaoEncontradaException(String.format("Restaurante de id %d não encontrado", id));
	}
}
