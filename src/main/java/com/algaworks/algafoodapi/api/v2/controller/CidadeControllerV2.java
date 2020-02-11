package com.algaworks.algafoodapi.api.v2.controller;

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

import com.algaworks.algafoodapi.api.v2.model.CidadeModelV2;
import com.algaworks.algafoodapi.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafoodapi.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.EstadoService;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

	private final CidadeRepository cidadeRepository;
	private final CidadeService cidadeService;
	private final EstadoService estadoService;

	@Autowired
	public CidadeControllerV2(CidadeRepository cidadeRepository, CidadeService cidadeService,
			EstadoService estadoService) {
		this.cidadeRepository = cidadeRepository;
		this.cidadeService = cidadeService;
		this.estadoService = estadoService;
	}
	
	@GetMapping
	public CollectionModel<CidadeModelV2> listar() {
		return CidadeModelV2.criarCollectionCidadeModelComLinks(cidadeRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public CidadeModelV2 buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscarOuFalhar(id);
		return CidadeModelV2.criarCidadeModelComLinks(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		Cidade cidade = cidadeService.salvar(cidadeInput.novaCidade(estadoService));
		
		return CidadeModelV2.criarCidadeModelComLinks(cidade);
	}
	
	@PutMapping("/{idCidade}")
	public CidadeModelV2 atualizar(@PathVariable Long idCidade, @RequestBody @Valid CidadeInputV2 cidadeInput) {
		Cidade cidadeAtualizada = cidadeInput.cidadeAtualizada(idCidade, cidadeService, estadoService);
		
		return CidadeModelV2.criarCidadeModelComLinks(cidadeService.salvar(cidadeAtualizada));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}
}
