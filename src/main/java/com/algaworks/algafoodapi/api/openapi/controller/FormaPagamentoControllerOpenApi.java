package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Lista formas de pagamento")
	ResponseEntity<List<FormaPagamentoModel>> listar();
	
	@ApiOperation(value = "Busca uma forma de pagamento por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da forma de pagamento inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	ResponseEntity<FormaPagamentoModel> buscar(
			@ApiParam(example = "1", value = "Id de uma forma de pagamento", required = true)
			Long idFormaPagamento, 
			
			ServletWebRequest request);
	
	@ApiOperation(value = "Insere uma forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Forma de pagamento adicionada")
	})
	ResponseEntity<FormaPagamentoModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento", required = true)
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation(value = "Atualiza uma forma de pagamento pelo Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	FormaPagamentoModel atualizar(
			@ApiParam(example = "1", value = "Id de uma forma de pagamento", required = true)
			Long idFormaPagamento, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de forma de pagamento", required = true)
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation(value = "Deleta uma forma de pagamento pelo Id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento removida"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(example = "1", value = "Id de uma forma de pagamento", required = true)
			Long idFormaPagamento);
}