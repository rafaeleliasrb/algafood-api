package com.algaworks.algafoodapi.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.domain.exception.AssociacaoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private RestauranteRepository restauranteRepository;
	private RestauranteService restauranteService;
	
	@Autowired
	public RestauranteController(RestauranteRepository restauranteRepository, RestauranteService restauranteService) {
		this.restauranteRepository = restauranteRepository;
		this.restauranteService = restauranteService;
	}
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Restaurante> porId(@PathVariable Long id) {
		Optional<Restaurante> restauranteOpt = restauranteRepository.findById(id);
		if(restauranteOpt.isPresent())
			return ResponseEntity.ok(restauranteOpt.get());
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Object> adicionar(@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteNovo = restauranteService.adicionar(restaurante);
			URI restauranteUri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(restauranteNovo.getId()).toUri();
			return ResponseEntity.created(restauranteUri).body(restauranteNovo);
		} catch (AssociacaoNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtualizado = restauranteService.atualizar(id, restaurante);
			return ResponseEntity.ok(restauranteAtualizado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (AssociacaoNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remover(@PathVariable Long id) {
		try {
			restauranteService.remover(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Object> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		try {
			Restaurante restauranteAtualizado = restauranteService.atualizarParcial(id, campos);
			return ResponseEntity.ok(restauranteAtualizado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/buscar")
	List<Restaurante> buscar(@RequestParam("nome") String nome, @RequestParam BigDecimal taxaFreteInicial, 
			@RequestParam BigDecimal taxaFreteFinal) {
		return restauranteRepository.buscar(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/buscar-com-criteria")
	List<Restaurante> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.buscarComCriteria(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	List<Restaurante> buscarComFreteGratis(String nome) {
		return restauranteRepository.buscarComFreteGratis(nome);
	}
	
	@GetMapping("/buscar-primeiro")
	Optional<Restaurante> buscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
}
