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
import com.algaworks.algafoodapi.api.model.PermissaoModel;
import com.algaworks.algafoodapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	private final GrupoService grupoService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public GrupoPermissaoController(GrupoService grupoService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.grupoService = grupoService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long idGrupo) {
		Grupo grupo = grupoService.buscarOuFalhar(idGrupo);
		
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(PermissaoModel.class, grupo.getPermissoes());
	}
	
	@PutMapping("/{idPermissao}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atribuir(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
		grupoService.atribuirPermissao(idGrupo, idPermissao);
	}
	
	@DeleteMapping("/{idPermissao}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desatribuir(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
		grupoService.desatribuirPermissao(idGrupo, idPermissao);
	}
}
