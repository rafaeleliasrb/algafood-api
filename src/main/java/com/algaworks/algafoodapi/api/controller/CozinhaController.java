package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.model.CozinhaXMLWrapper;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Cozinha> porId(@PathVariable Long id) {
		Optional<Cozinha> cozinhaOpt = cozinhaRepository.findById(id);
		if(cozinhaOpt.isPresent())
			return ResponseEntity.ok(cozinhaOpt.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha novaCozinha) {
		Cozinha cozinha = cozinhaService.adicionar(novaCozinha);
		
		URI cozinhaUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("cozinhas/{id}").buildAndExpand(cozinha.getId()).toUri();
		return ResponseEntity.created(cozinhaUri).body(cozinha);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remover(@PathVariable Long id) {
		try {
			cozinhaService.remover(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		try {
			cozinha = cozinhaService.atualizar(id, cozinha);
			return ResponseEntity.ok(cozinha);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
