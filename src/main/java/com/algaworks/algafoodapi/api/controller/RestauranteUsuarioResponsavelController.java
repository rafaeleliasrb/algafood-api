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
import com.algaworks.algafoodapi.api.model.UsuarioModel;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	private final RestauranteService restauranteService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public RestauranteUsuarioResponsavelController(RestauranteService restauranteService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.restauranteService = restauranteService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<UsuarioModel> listar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(UsuarioModel.class, restaurante.getResponsaveis());
	}
	
	@PutMapping("/{idResponsavel}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void adicionar(@PathVariable Long idRestaurante, @PathVariable Long idResponsavel) {
		restauranteService.adicionarResponsavel(idRestaurante, idResponsavel);
	}
	
	@DeleteMapping("/{idResponsavel}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void remover(@PathVariable Long idRestaurante, @PathVariable Long idResponsavel) {
		restauranteService.removerResponsavel(idRestaurante, idResponsavel);
	}
}
