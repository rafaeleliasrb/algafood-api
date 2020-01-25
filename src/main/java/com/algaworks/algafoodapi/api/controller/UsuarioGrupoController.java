package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.GrupoModel;
import com.algaworks.algafoodapi.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.service.UsuarioService;

@RestController
@RequestMapping("usuarios/{idUsuario}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	private final UsuarioService usuarioService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public UsuarioGrupoController(UsuarioService usuarioService, 
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.usuarioService = usuarioService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioService.buscarOuFalhar(idUsuario);
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(GrupoModel.class, usuario.getGrupos());
	}
	
	@PutMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		usuarioService.associarGrupo(idUsuario, idGrupo);
	}
	
	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		usuarioService.desassociarGrupo(idUsuario, idGrupo);
	}
}
