package com.algaworks.algafoodapi.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.model.UsuarioModel;
import com.algaworks.algafoodapi.api.v1.model.input.SenhaInput;
import com.algaworks.algafoodapi.api.v1.model.input.UsuarioInput;
import com.algaworks.algafoodapi.api.v1.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import com.algaworks.algafoodapi.domain.service.UsuarioService;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		return UsuarioModel.criarCollectionUsuarioModelComLinks(usuarioRepository.findAll());
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping("/{idUsuario}")
	public UsuarioModel buscar(@PathVariable Long idUsuario) {
		return UsuarioModel.criarUsuarioModelComLinks(usuarioService.buscarOuFalhar(idUsuario));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = usuarioInput.novoUsuario(); 
		
		return UsuarioModel.criarUsuarioModelComLinks(usuarioService.salvar(usuario));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@Override
	@PutMapping("/{idUsuario}")
	public UsuarioModel atualizar(@PathVariable Long idUsuario, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
		Usuario usuarioAtualizada = usuarioSemSenhaInput.usuarioAtualizado(idUsuario, usuarioService);
		
		return UsuarioModel.criarUsuarioModelComLinks(usuarioService.salvar(usuarioAtualizada));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@Override
	@PutMapping("/{idUsuario}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long idUsuario, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(idUsuario, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}
}
