package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.GrupoNaoEncotradoException;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.model.Permissao;
import com.algaworks.algafoodapi.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	private final GrupoRepository grupoRepository;
	private final PermissaoService permissaoService;

	@Autowired
	public GrupoService(GrupoRepository grupoRepository, PermissaoService permissaoService) {
		this.grupoRepository = grupoRepository;
		this.permissaoService = permissaoService;
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
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("O grupo de código %d não pode ser removido pois está em uso", id));
		}
	}

	@Transactional
	public void atribuirPermissao(Long idGrupo, Long idPermissao) {
		Grupo grupo = buscarOuFalhar(idGrupo);
		Permissao permissao = permissaoService.buscarOuFalhar(idPermissao);
		
		grupo.atribuirPermissao(permissao);
	}
	
	@Transactional
	public void desatribuirPermissao(Long idGrupo, Long idPermissao) {
		Grupo grupo = buscarOuFalhar(idGrupo);
		Permissao permissao = permissaoService.buscarOuFalhar(idPermissao);
		
		grupo.desatribuirPermissao(permissao);
	}
}
