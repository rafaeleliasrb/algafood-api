package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.EstadoModel;
import com.algaworks.algafoodapi.api.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista estados")
	List<EstadoModel> listar();
	
	@ApiOperation("Busca estado por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoModel buscar(
			@ApiParam(example = "1", value = "Id de um estado", required = true)
			Long id);
	
	@ApiOperation("Insere estado")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado adicionado")
	})
	ResponseEntity<EstadoModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de um estado", required = true)
			EstadoInput estadoInput);
	
	@ApiOperation("Atualiza estado por Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Estado atuaizado"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoModel atualizar(
			@ApiParam(example = "1", value = "Id de um estado", required = true)
			Long id, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de um estado", required = true)
			EstadoInput estadoInput);
	
	@ApiOperation("Deleta estado por Id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Estado removido"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(example = "1", value = "Id de um estado", required = true)
			Long id);
}
