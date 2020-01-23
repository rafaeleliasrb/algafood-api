package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.algaworks.algafoodapi.api.model.GrupoModel;
import com.algaworks.algafoodapi.api.model.input.GrupoInput;
import com.algaworks.algafoodapi.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.repository.GrupoRepository;
import com.algaworks.algafoodapi.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	private final GrupoService grupoService;
	private final GrupoRepository grupoRepository;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public GrupoController(GrupoService grupoService, GrupoRepository grupoRepository,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.grupoService = grupoService;
		this.grupoRepository = grupoRepository;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	public List<GrupoModel> listar() {
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(GrupoModel.class, grupoRepository.findAll());
	}
	
	@GetMapping("/{idGrupo}")
	public GrupoModel buscar(@PathVariable Long idGrupo) {
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(GrupoModel.class, grupoService.buscarOuFalhar(idGrupo));
	}
	
	@PostMapping
	public ResponseEntity<GrupoModel> adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = representationModelAssemblerAndDisassembler
				.toRepresentationModel(Grupo.class, grupoInput);
		GrupoModel grupoNovo = representationModelAssemblerAndDisassembler
				.toRepresentationModel(GrupoModel.class, grupoService.salvar(grupo));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(grupoNovo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(grupoNovo);
	}
	
	@PutMapping("/{idGrupo}")
	public GrupoModel atualizar(@PathVariable Long idGrupo, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(idGrupo);

		representationModelAssemblerAndDisassembler.copyProperties(grupoInput, grupoAtual);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(GrupoModel.class, grupoService.salvar(grupoAtual));
	}
	
	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idGrupo) {
		grupoService.remover(idGrupo);
	}
}
