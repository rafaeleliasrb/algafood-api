package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Permissao;
import com.algaworks.algafoodapi.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {

	private PermissaoRepository permissaoRepository;

	@Autowired
	public PermissaoService(PermissaoRepository permissaoRepository) {
		this.permissaoRepository = permissaoRepository;
	}
	
	public Permissao buscarOuFalhar(Long id) {
		return permissaoRepository.findById(id)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}
}
