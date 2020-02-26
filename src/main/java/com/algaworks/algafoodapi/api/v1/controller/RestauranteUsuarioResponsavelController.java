package com.algaworks.algafoodapi.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.model.UsuarioModel;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	private final RestauranteService restauranteService;
	private final AlgaSecurity algaSecurity;

	@Autowired
	public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, AlgaSecurity algaSecurity) {
		this.restauranteService = restauranteService;
		this.algaSecurity = algaSecurity;
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		return UsuarioModel.criarCollectionUsuarioModelComLinksRestaurante(
				restaurante.getResponsaveis(), idRestaurante, algaSecurity);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@PutMapping("/{idResponsavel}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> adicionar(@PathVariable Long idRestaurante, @PathVariable Long idResponsavel) {
		restauranteService.adicionarResponsavel(idRestaurante, idResponsavel);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@DeleteMapping("/{idResponsavel}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remover(@PathVariable Long idRestaurante, @PathVariable Long idResponsavel) {
		restauranteService.removerResponsavel(idRestaurante, idResponsavel);
		
		return ResponseEntity.noContent().build();
	}
}
