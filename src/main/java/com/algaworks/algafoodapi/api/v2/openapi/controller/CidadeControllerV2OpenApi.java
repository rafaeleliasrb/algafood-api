package com.algaworks.algafoodapi.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.v2.model.CidadeModelV2;
import com.algaworks.algafoodapi.api.v2.model.input.CidadeInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeModelV2> listar();
	
	@ApiOperation("Busca uma cidade por id")
	@ApiResponses({
			@ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModelV2 buscar(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true)
			Long id);
	
	@ApiOperation("Insere uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada")
	})
	CidadeModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma cidade", required = true)
			CidadeInputV2 cidadeInput);
	
	@ApiOperation("Atualiza uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModelV2 atualizar(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true)
			Long id, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de uma cidade", required = true)
			CidadeInputV2 cidadeInput);
	
	@ApiOperation("Deleta uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true)
			Long id);
}
