package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.service.EstadoService;

@RestController
@RequestMapping(value = "estados")
public class EstadoController {

	private EstadoRepository estadoRepository;
	private EstadoService estadoService;

	@Autowired
	public EstadoController(EstadoRepository estadoRepository, EstadoService estadoService) {
		this.estadoRepository = estadoRepository;
		this.estadoService = estadoService;
	}
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return estadoService.buscarOuFalhar(id);
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody @Valid Estado estado) {
		Estado estadoNovo = estadoService.adicionar(estado);
		URI estadoUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(estado.getId()).toUri();
		return ResponseEntity.created(estadoUri).body(estadoNovo);
	}
	
	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		return estadoService.atualizar(id, estado);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		estadoService.remover(id);
	}
}
