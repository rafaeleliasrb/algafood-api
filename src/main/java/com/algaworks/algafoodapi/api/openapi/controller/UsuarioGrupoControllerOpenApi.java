package com.algaworks.algafoodapi.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista os grupos de um usuário")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do usuário inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	CollectionModel<GrupoModel> listar(
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idUsuario);
	
	@ApiOperation("Associa um grupo a um usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo associado ao usuário"),
		@ApiResponse(code = 400, message = "Id do usuário ou grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> associar(
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idUsuario, 
			
			@ApiParam(example = "1", value = "Id de um grupo", required = true)
			Long idGrupo);
	
	@ApiOperation("Desassocia um grupo a um usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo desassociado do usuário"),
		@ApiResponse(code = 400, message = "Id do usuário ou grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> desassociar(
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idUsuario, 
			
			@ApiParam(example = "1", value = "Id de um grupo", required = true)
			Long idGrupo);
}
