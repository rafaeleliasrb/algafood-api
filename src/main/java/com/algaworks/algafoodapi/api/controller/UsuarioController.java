package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
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
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	public List<UsuarioModel> listar() {
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(UsuarioModel.class, usuarioRepository.findAll());
	}
	
	@GetMapping("/{idUsuario}")
	public UsuarioModel buscar(@PathVariable Long idUsuario) {
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(UsuarioModel.class, usuarioService.buscarOuFalhar(idUsuario));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioModel> adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = representationModelAssemblerAndDisassembler
				.toRepresentationModel(Usuario.class, usuarioInput);
		
		UsuarioModel novoUsuario = representationModelAssemblerAndDisassembler
				.toRepresentationModel(UsuarioModel.class, usuarioService.salvar(usuario));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(novoUsuario);
	}
	
	@PutMapping("/{idUsuario}")
	public UsuarioModel atualizar(@PathVariable Long idUsuario, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(idUsuario);
		
		representationModelAssemblerAndDisassembler.copyProperties(usuarioSemSenhaInput, usuarioAtual);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(UsuarioModel.class, usuarioService.salvar(usuarioAtual));
	}
	
	@PutMapping("/{idUsuario}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long idUsuario, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(idUsuario, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}
}
