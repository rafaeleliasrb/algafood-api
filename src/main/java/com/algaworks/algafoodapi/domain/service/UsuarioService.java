package com.algaworks.algafoodapi.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final GrupoService grupoService;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, GrupoService grupoService) {
		this.usuarioRepository = usuarioRepository;
		this.grupoService = grupoService;
	}
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detached(usuario);
		
		verificarEmailJaEmUso(usuario);
		
		return usuarioRepository.save(usuario);
	}

	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);
		}
	}

	@Transactional
	public void alterarSenha(Long idUsuario, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(idUsuario);
		
		usuario.verificarSenhaCorreta(senhaAtual);
		
		usuario.atualizarSenha(novaSenha);
	}
	
	@Transactional
	public void associarGrupo(Long idUsuario, Long idGrupo) {
		Usuario usuario = buscarOuFalhar(idUsuario);
		Grupo grupo = grupoService.buscarOuFalhar(idGrupo);
		
		usuario.associarGrupo(grupo);
	}

	@Transactional
	public void desassociarGrupo(Long idUsuario, Long idGrupo) {
		Usuario usuario = buscarOuFalhar(idUsuario);
		Grupo grupo = grupoService.buscarOuFalhar(idGrupo);
		
		usuario.desassociarGrupo(grupo);
	}
	
	private void verificarEmailJaEmUso(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("Já existe usuário cadastrado com esse email %s", usuario.getEmail()));
		}
	}

}
