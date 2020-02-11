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

import com.algaworks.algafoodapi.api.v1.model.EstadoModel;
import com.algaworks.algafoodapi.api.v1.model.input.EstadoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.service.EstadoService;

@RestController
@RequestMapping(path = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	private final EstadoRepository estadoRepository;
	private final EstadoService estadoService;

	@Autowired
	public EstadoController(EstadoRepository estadoRepository, EstadoService estadoService) {
		this.estadoRepository = estadoRepository;
		this.estadoService = estadoService;
	}
	
	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		return EstadoModel.criarCollectorEstadoModelComLinks(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoModel buscar(@PathVariable Long id) {
		return EstadoModel.criarEstadoModelComLinks(estadoService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoService.salvar(new Estado(estadoInput.getNome()));
		
		return EstadoModel.criarEstadoModelComLinks(estado);
	}
	
	@PutMapping("/{id}")
	public EstadoModel atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = estadoService.buscarOuFalhar(id);
		estadoAtual.setNome(estadoInput.getNome());
		
		return EstadoModel.criarEstadoModelComLinks(estadoService.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		estadoService.remover(id);
	}
}
