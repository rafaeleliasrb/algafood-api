package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.UsuarioModel;
import com.algaworks.algafoodapi.api.model.input.SenhaInput;
import com.algaworks.algafoodapi.api.model.input.UsuarioInput;
import com.algaworks.algafoodapi.api.model.input.UsuarioSemSenhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	List<UsuarioModel> listar();
	
	@ApiOperation("Busca um usuário pelo Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do usuário inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	UsuarioModel buscar(
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idUsuario);
	
	@ApiOperation("Adiciona um usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário adicionado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	ResponseEntity<UsuarioModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de um usuário", required = true)
			UsuarioInput usuarioInput);
	
	@ApiOperation("Atualiza um usuário pelo Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário atualizado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	UsuarioModel atualizar(
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idUsuario, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de um usuário", required = true)
			UsuarioSemSenhaInput usuarioSemSenhaInput);
	
	@ApiOperation("Altera a senha de um usuário pelo Id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Senha atualizada"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	void alterarSenha(
			@ApiParam(example = "1", value = "Id de um usuário", required = true)
			Long idUsuario, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados da senha de um usuário", 
				required = true)
			SenhaInput senhaInput);
}
