package com.algaworks.algafoodapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.model.GrupoModel;
import com.algaworks.algafoodapi.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.service.UsuarioService;

@RestController
@RequestMapping("usuarios/{idUsuario}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioGrupoController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping
	public CollectionModel<GrupoModel> listar(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioService.buscarOuFalhar(idUsuario);
		
		return GrupoModel.criarCollectionGrupoModelComLinksUsuario(usuario.getGrupos(), idUsuario);
	}
	
	@PutMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		usuarioService.associarGrupo(idUsuario, idGrupo);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		usuarioService.desassociarGrupo(idUsuario, idGrupo);
		
		return ResponseEntity.noContent().build();
	}
}
