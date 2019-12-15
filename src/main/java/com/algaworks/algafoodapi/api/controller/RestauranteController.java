package com.algaworks.algafoodapi.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public Restaurante buscar(@PathVariable Long id) {
		return restauranteService.buscarOuFalha(id);
	}
	
	@PostMapping
	public ResponseEntity<Object> adicionar(@RequestBody Restaurante restaurante) {
		Restaurante restauranteNovo = restauranteService.adicionar(restaurante);
		URI restauranteUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(restauranteNovo.getId()).toUri();
		return ResponseEntity.created(restauranteUri).body(restauranteNovo);
	}
	
	@PutMapping(value = "{id}")
	public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		return restauranteService.atualizar(id, restaurante);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		restauranteService.remover(id);
	}
	
	@PatchMapping("/{id}")
	public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> camposOrigem,
			HttpServletRequest request) {
		Restaurante restauranteAtual = restauranteService.buscarOuFalha(id);
		mergeCampos(camposOrigem, restauranteAtual, request);
		
		return atualizar(id, restauranteAtual);
	}

	private void mergeCampos(Map<String, Object> propriedadesOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Restaurante restauranteOrigem = mapper.convertValue(propriedadesOrigem, Restaurante.class);
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			propriedadesOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, 
					new ServletServerHttpRequest(request));
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
