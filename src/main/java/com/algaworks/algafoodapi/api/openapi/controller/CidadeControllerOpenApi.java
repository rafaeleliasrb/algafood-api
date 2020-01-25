package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.CidadeModel;
import com.algaworks.algafoodapi.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	List<CidadeModel> listar();
	
	@ApiOperation("Busca uma cidade por id")
	@ApiResponses({
			@ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel buscar(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true)
			Long id);
	
	@ApiOperation("Insere uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada")
	})
	ResponseEntity<CidadeModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma cidade", required = true)
			CidadeInput cidadeInput);
	
	@ApiOperation("Atualiza uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel atualizar(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true)
			Long id, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de uma cidade", required = true)
			CidadeInput cidadeInput);
	
	@ApiOperation("Deleta uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true)
			Long id);
}
