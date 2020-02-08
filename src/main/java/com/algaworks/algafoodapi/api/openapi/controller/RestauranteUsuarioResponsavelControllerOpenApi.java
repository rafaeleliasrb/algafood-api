package com.algaworks.algafoodapi.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@ApiOperation("Lista responsáveis de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	CollectionModel<UsuarioModel> listar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante);
	
	@ApiOperation("Adiciona um usuário responsável a um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário responsável adicionado ao restaurante", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> adicionar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idResponsavel);
	
	@ApiOperation("Remove um usuário responsável de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário responsável removido do restaurante", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> remover(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idResponsavel);
}
