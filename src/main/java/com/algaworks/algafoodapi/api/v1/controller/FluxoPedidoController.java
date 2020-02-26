package com.algaworks.algafoodapi.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path = "/v1/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

	private final FluxoPedidoService fluxoPedidoService;
	
	@Autowired
	public FluxoPedidoController(FluxoPedidoService fluxoPedidoService) {
		this.fluxoPedidoService = fluxoPedidoService;
	}

	@CheckSecurity.Pedidos.PodeGerenciar
	@Override
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
		fluxoPedidoService.confirmar(codigoPedido);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Pedidos.PodeGerenciar
	@Override
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
		fluxoPedidoService.entregar(codigoPedido);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Pedidos.PodeGerenciar
	@Override
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
		fluxoPedidoService.cancelar(codigoPedido);
		
		return ResponseEntity.noContent().build();
	}
}
