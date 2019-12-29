package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	private final RestauranteService restauranteService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;
	
	@Autowired
	public RestauranteFormaPagamentoController(RestauranteService restauranteService, 
			RepresentationModelAssemblerAndDisassembler restauranteInputAssemblerAndDisassembler) {
		this.restauranteService = restauranteService;
		this.representationModelAssemblerAndDisassembler = restauranteInputAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<FormaPagamentoModel> listar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(FormaPagamentoModel.class, restaurante.getFormasPagamento());
	}

	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void desassociar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
		restauranteService.desassociarFormaPagamento(idRestaurante, idFormaPagamento);
	}
	
	@PutMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void associar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
		restauranteService.associarFormaPagamento(idRestaurante, idFormaPagamento);
	}
}