package com.algaworks.algafoodapi.domain.service;

import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradaException;
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
		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public Restaurante atualizar(Long id, Restaurante restaurante) {
		Restaurante restauranteAtual = buscarOuFalha(id);
		Cozinha cozinha = cozinhaPorId(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro", "endereco", "produto");
		return restauranteRepository.save(restauranteAtual);
	}

	public Restaurante buscarOuFalha(Long id) {
		return restauranteRepository.findById(id)
				.orElseThrow(entidadeNaoEncontradaSupplier(id));
	}

	@Transactional
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(id);
		}
	}
	
	private Cozinha cozinhaPorId(Long id) {
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new AssociacaoNaoEncontradaException(String.format("Cozinha de id %d não encontrada", id)));
	}

	private Supplier<? extends EntidadeNaoEncontradaException> entidadeNaoEncontradaSupplier(Long id) {
		return () -> new RestauranteNaoEncontradaException(id);
	}
}
