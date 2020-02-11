package com.algaworks.algafoodapi.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algaworks.algafoodapi.api.v2.model.input.CozinhaInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {

	@ApiOperation(value = "Lista cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
	PagedModel<CozinhaModelV2> listar(Pageable pageable);

	@ApiOperation(value = "Busca uma cozinha por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModelV2 buscar(
			@ApiParam(example = "1", value = "Id de uma cozinha", required = true) 
			Long id);
	
	@ApiOperation(value = "Insere uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha adicionada")
	})
	CozinhaModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma cozinha", required = true) 
			CozinhaInputV2 cozinhaInput);
	
	
	@ApiOperation(value = "Atualiza uma cozinha pelo Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModelV2 atualizar(
			@ApiParam(name = "corpo", value = "Representação com os novos dados de uma cozinha", required = true)
			CozinhaInputV2 cozinhaInput,
			
			@ApiParam(example = "1", value = "Id de uma cozinha", required = true) 
			Long id);
	
	@ApiOperation(value = "Deleta uma cozinha pelo Id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha removida"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(example = "1", value = "Id de uma cozinha", required = true) 
			Long id);
}
