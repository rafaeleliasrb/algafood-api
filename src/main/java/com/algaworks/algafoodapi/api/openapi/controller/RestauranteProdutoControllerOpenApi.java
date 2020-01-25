package com.algaworks.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.ProdutoModel;
import com.algaworks.algafoodapi.api.model.input.ProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	@ApiOperation("Lista os produtos de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	List<ProdutoModel> listar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "true", value = "Flag para incluir ou não os inativos", defaultValue = "false")
			boolean incluirInativos);
	
	@ApiOperation("Busca um produto de um restaurante pelo Id do produto")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do restaurante ou produto inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
	})
	ProdutoModel buscar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um produto", required = true)
			Long idProduto);
	
	@ApiOperation("Adiciona um produto no restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Produto adicionado ao restaurante"),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
	})
	ResponseEntity<ProdutoModel> adicionar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(name = "corpo", value = "Representação de um produto", required = true)
			ProdutoInput produtoInput);
	
	@ApiOperation("Atualiza um produtos do restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Produto de um restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
	})
	ProdutoModel atualizar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um produto", required = true)
			Long idProduto, 
			
			@ApiParam(example = "1", value = "Representação com os novos dados de um produto", required = true)
			ProdutoInput produtoInput);
}
