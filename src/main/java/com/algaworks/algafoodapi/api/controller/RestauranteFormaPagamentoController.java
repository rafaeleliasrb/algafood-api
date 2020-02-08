package com.algaworks.algafoodapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{idRestaurante}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	private final RestauranteService restauranteService;
	
	@Autowired
	public RestauranteFormaPagamentoController(RestauranteService restauranteService) {
		this.restauranteService = restauranteService;
	}
	
	@GetMapping
	public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		return FormaPagamentoModel.criarCollectorFormaPagamentoModelComLinkRestaurante(
				restaurante.getFormasPagamento(), idRestaurante);
	}

	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
		restauranteService.desassociarFormaPagamento(idRestaurante, idFormaPagamento);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
		restauranteService.associarFormaPagamento(idRestaurante, idFormaPagamento);
		
		return ResponseEntity.noContent().build();
	}
}
