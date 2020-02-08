package com.algaworks.algafoodapi.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões de um grupo")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	CollectionModel<PermissaoModel> listar(
			@ApiParam(example = "1", value = "Id de um grupo", required = true)
			Long idGrupo);
	
	@ApiOperation("Atribui uma permissão a um grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Permissão atribuída ao grupo", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> atribuir(
			@ApiParam(example = "1", value = "Id de um grupo", required = true)
			Long idGrupo, 
			
			@ApiParam(example = "1", value = "Id de uma permissão", required = true)
			Long idPermissao);
	
	@ApiOperation("Desatribui uma permissão de um grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Permissão desatribuída ao grupo", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> desatribuir(
			@ApiParam(example = "1", value = "Id de um grupo", required = true)
			Long idGrupo, 
			
			@ApiParam(example = "1", value = "Id de uma permissão", required = true)
			Long idPermissao);
}
