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

import com.algaworks.algafoodapi.api.v1.model.CidadeModel;
import com.algaworks.algafoodapi.api.v1.model.input.CidadeInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.EstadoService;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	private final CidadeRepository cidadeRepository;
	private final CidadeService cidadeService;
	private final EstadoService estadoService;

	@Autowired
	public CidadeController(CidadeRepository cidadeRepository, CidadeService cidadeService,
			EstadoService estadoService) {
		this.cidadeRepository = cidadeRepository;
		this.cidadeService = cidadeService;
		this.estadoService = estadoService;
	}
	
	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		return CidadeModel.criarCollectionCidadeModelComLinks(cidadeRepository.findAll());
	}
	
	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping("/{id}")
	public CidadeModel buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscarOuFalhar(id);
		return CidadeModel.criarCidadeModelComLinks(cidade);
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = cidadeService.salvar(cidadeInput.novaCidade(estadoService));
		
		return CidadeModel.criarCidadeModelComLinks(cidade);
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PutMapping("/{idCidade}")
	public CidadeModel atualizar(@PathVariable Long idCidade, @RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtualizada = cidadeInput.cidadeAtualizada(idCidade, cidadeService, estadoService);
		
		return CidadeModel.criarCidadeModelComLinks(cidadeService.salvar(cidadeAtualizada));
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}
}
