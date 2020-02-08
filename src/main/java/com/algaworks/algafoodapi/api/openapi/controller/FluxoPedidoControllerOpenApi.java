package com.algaworks.algafoodapi.api.openapi.controller;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation("Confirma um pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado"),
		@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	ResponseEntity<Void> confirmar(
			@ApiParam(example = "f229e133-2c0a-11ea-9866-d09466a32949", value = "Código de um pedido", required = true)
			String codigoPedido);
	
	@ApiOperation("Registra a entrega um pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Entrega de pedido registrada"),
		@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	ResponseEntity<Void> entregar(
			@ApiParam(example = "f229e133-2c0a-11ea-9866-d09466a32949", value = "Código de um pedido", required = true)
			String codigoPedido);
	
	@ApiOperation("Cancela um pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido cancelado"),
		@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	ResponseEntity<Void> cancelar(
			@ApiParam(example = "f229e133-2c0a-11ea-9866-d09466a32949", value = "Código de um pedido", required = true)
			String codigoPedido);
}
