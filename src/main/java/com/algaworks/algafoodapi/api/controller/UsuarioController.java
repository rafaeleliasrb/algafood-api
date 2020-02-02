package com.algaworks.algafoodapi.api.controller;

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

import com.algaworks.algafoodapi.api.model.UsuarioModel;
import com.algaworks.algafoodapi.api.model.input.SenhaInput;
import com.algaworks.algafoodapi.api.model.input.UsuarioInput;
import com.algaworks.algafoodapi.api.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafoodapi.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import com.algaworks.algafoodapi.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
	}
	
	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		return UsuarioModel.criarCollectionUsuarioModelComLinks(usuarioRepository.findAll());
	}
	
	@GetMapping("/{idUsuario}")
	public UsuarioModel buscar(@PathVariable Long idUsuario) {
		return UsuarioModel.criarUsuarioModelComLinks(usuarioService.buscarOuFalhar(idUsuario));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = usuarioInput.novoUsuario(); 
		
		return UsuarioModel.criarUsuarioModelComLinks(usuarioService.salvar(usuario));
	}
	
	@PutMapping("/{idUsuario}")
	public UsuarioModel atualizar(@PathVariable Long idUsuario, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
		Usuario usuarioAtualizada = usuarioSemSenhaInput.usuarioAtualizado(idUsuario, usuarioService);
		
		return UsuarioModel.criarUsuarioModelComLinks(usuarioService.salvar(usuarioAtualizada));
	}
	
	@PutMapping("/{idUsuario}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long idUsuario, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(idUsuario, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}
}
