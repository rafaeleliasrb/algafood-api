package com.algaworks.algafoodapi.domain.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private final RestauranteRepository restauranteRepository;
	private final CozinhaRepository cozinhaRepository;
	private final CidadeRepository cidadeRepository;
	private final FormaPagamentoService formaPagamentoService;
	private final UsuarioService usuarioService;

	@Autowired
	public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository,
			CidadeRepository cidadeRepository, FormaPagamentoService formaPagamentoService, 
			UsuarioService usuarioService) {
		this.restauranteRepository = restauranteRepository;
		this.cozinhaRepository = cozinhaRepository;
		this.cidadeRepository = cidadeRepository;
		this.formaPagamentoService = formaPagamentoService;
		this.usuarioService = usuarioService;
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
			throw new RestauranteNaoEncontradoException(id);
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
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalharDeUmRestaurante(idFormaPagamento, restaurante);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
		
		restaurante.associarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void abrir(Long idRestaurante) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long idRestaurante) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		restaurante.fechar();
	}

	@Transactional
	public void adicionarResponsavel(Long idRestaurante, Long idResponsavel) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		Usuario responsavel = usuarioService.buscarOuFalhar(idResponsavel);
		
		restaurante.adicionarResponsavel(responsavel);
	}
	
	@Transactional
	public void removerResponsavel(Long idRestaurante, Long idResponsavel) {
		Restaurante restaurante = buscarOuFalha(idRestaurante);
		Usuario responsavel = usuarioService.buscarOuFalhar(idResponsavel);
		
		restaurante.removerResponsavel(responsavel);
	}
	
	@Transactional
	public void ativar(List<Long> idsRestaurante) {
		idsRestaurante.forEach(this::ativar);
	}

	@Transactional
	public void desativar(List<Long> idsRestaurante) {
		idsRestaurante.forEach(this::inativar);
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
		return () -> new RestauranteNaoEncontradoException(id);
	}
}
