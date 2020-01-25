package com.algaworks.algafoodapi.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.CozinhaModel;
import com.algaworks.algafoodapi.api.model.CozinhaXMLWrapper;
import com.algaworks.algafoodapi.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation(value = "Lista cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
	Page<CozinhaModel> listar(Pageable pageable);

	@ApiOperation(value = "Lista cozinhas em XML", produces = MediaType.APPLICATION_XML_VALUE, hidden = true)
	CozinhaXMLWrapper listarXML();
	
	@ApiOperation(value = "Busca uma cozinha por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel buscar(
			@ApiParam(example = "1", value = "Id de uma cozinha", required = true) 
			Long id);
	
	@ApiOperation(value = "Insere uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha adicionada")
	})
	ResponseEntity<CozinhaModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma cozinha", required = true) 
			CozinhaInput cozinhaInput);
	
	
	@ApiOperation(value = "Atualiza uma cozinha pelo Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel atualizar(
			@ApiParam(name = "corpo", value = "Representação com os novos dados de uma cozinha", required = true)
			CozinhaInput cozinhaInput,
			
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
