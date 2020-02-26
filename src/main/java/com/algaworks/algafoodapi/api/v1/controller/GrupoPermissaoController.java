package com.algaworks.algafoodapi.api.v1.controller;

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

import com.algaworks.algafoodapi.api.v1.model.PermissaoModel;
import com.algaworks.algafoodapi.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.service.GrupoService;

@RestController
@RequestMapping("/v1/grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	private final GrupoService grupoService;

	@Autowired
	public GrupoPermissaoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<PermissaoModel> listar(@PathVariable Long idGrupo) {
		Grupo grupo = grupoService.buscarOuFalhar(idGrupo);
		
		return PermissaoModel.criarCollectionPermissaoModelComLinksGrupo(grupo.getPermissoes(), idGrupo);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{idPermissao}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> atribuir(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
		grupoService.atribuirPermissao(idGrupo, idPermissao);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{idPermissao}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desatribuir(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
		grupoService.desatribuirPermissao(idGrupo, idPermissao);
		
		return ResponseEntity.noContent().build();
	}
}
