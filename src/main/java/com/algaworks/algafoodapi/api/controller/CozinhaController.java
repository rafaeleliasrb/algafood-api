package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

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

import com.algaworks.algafoodapi.api.model.CozinhaXMLWrapper;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	private CozinhaRepository cozinhaRepository;
	private CozinhaService cozinhaService;

	@Autowired
	public CozinhaController(CozinhaRepository cozinhaRepository, CozinhaService cozinhaService) {
		this.cozinhaRepository = cozinhaRepository;
		this.cozinhaService = cozinhaService;
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
	public CozinhaXMLWrapper listarXML() {
		return new CozinhaXMLWrapper(cozinhaRepository.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public Cozinha buscar(@PathVariable Long id) {
		return cozinhaService.buscarOuFalhar(id);
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha novaCozinha) {
		Cozinha cozinha = cozinhaService.adicionar(novaCozinha);
		
		URI cozinhaUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("cozinhas/{id}").buildAndExpand(cozinha.getId()).toUri();
		return ResponseEntity.created(cozinhaUri).body(cozinha);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cozinhaService.remover(id);
	}
	
	@PutMapping("/{id}")
	public Cozinha atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		return cozinhaService.atualizar(id, cozinha);
	}
}
