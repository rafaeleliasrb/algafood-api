package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento aceitas por um restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	List<FormaPagamentoModel> listar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante);

	@ApiOperation("Desassocia uma forma de pagamento de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento desassociada do restaurante", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = Problem.class)
	})
	void desassociar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de uma forma de pagamento", required = true)
			Long idFormaPagamento);
	
	@ApiOperation("Associa uma forma de pagamento a um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento associada ao restaurante", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = Problem.class)
	})
	void associar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de uma forma de pagamento", required = true)
			Long idFormaPagamento);
}
