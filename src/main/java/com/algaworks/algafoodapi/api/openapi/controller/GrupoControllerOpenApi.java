package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.GrupoModel;
import com.algaworks.algafoodapi.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	List<GrupoModel> listar();
	
	@ApiOperation("Busca um grupo por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoModel buscar(
			@ApiParam(value = "Id de um grupo", example = "1", required = true)
			Long idGrupo);
	
	@ApiOperation("Cria um grupo")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Grupo criado")
	})
	ResponseEntity<GrupoModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de um grupo", required = true)
			GrupoInput grupoInput);
	
	@ApiOperation("Atualiza um grupo por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Grupo atualizado"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoModel atualizar(
			@ApiParam(value = "Id de um grupo", example = "1", required = true)
			Long idGrupo, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de um grupo", required = true)
			GrupoInput grupoInput);
	
	@ApiOperation("Deleta um grupo por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo excluído"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "Id de um grupo", example = "1", required = true)
			Long idGrupo);
}
