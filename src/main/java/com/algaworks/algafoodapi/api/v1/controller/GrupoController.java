package com.algaworks.algafoodapi.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.model.GrupoModel;
import com.algaworks.algafoodapi.api.v1.model.input.GrupoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.repository.GrupoRepository;
import com.algaworks.algafoodapi.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	private final GrupoService grupoService;
	private final GrupoRepository grupoRepository;

	@Autowired
	public GrupoController(GrupoService grupoService, GrupoRepository grupoRepository) {
		this.grupoService = grupoService;
		this.grupoRepository = grupoRepository;
	}
	
	@GetMapping
	public CollectionModel<GrupoModel> listar() {
		return GrupoModel.criarCollectionGrupoModelComLinks(grupoRepository.findAll());
	}
	
	@GetMapping("/{idGrupo}")
	public GrupoModel buscar(@PathVariable Long idGrupo) {
		return GrupoModel.criarGrupoModelComLinks(grupoService.buscarOuFalhar(idGrupo));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = new Grupo(grupoInput.getNome());
		
		return GrupoModel.criarGrupoModelComLinks(grupoService.salvar(grupo));
	}
	
	@PutMapping("/{idGrupo}")
	public GrupoModel atualizar(@PathVariable Long idGrupo, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(idGrupo);
		grupoAtual.setNome(grupoInput.getNome());
		
		return GrupoModel.criarGrupoModelComLinks(grupoService.salvar(grupoAtual));
	}
	
	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idGrupo) {
		grupoService.remover(idGrupo);
	}
}
