package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.GrupoNaoEncotradoException;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	private GrupoRepository grupoRepository;

	@Autowired
	public GrupoService(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	public Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id)
				.orElseThrow(() -> new GrupoNaoEncotradoException(id));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncotradoException(id);
		}
	}
}
