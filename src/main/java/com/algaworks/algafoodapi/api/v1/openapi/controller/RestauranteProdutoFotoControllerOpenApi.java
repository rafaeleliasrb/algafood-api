package com.algaworks.algafoodapi.api.v1.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.v1.model.FotoProdutoModel;
import com.algaworks.algafoodapi.api.v1.model.input.FotoProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteProdutoFotoControllerOpenApi {

	@ApiOperation("Atualiza a foto de um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Foto atualizada"),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
	})
	FotoProdutoModel atualizarFoto(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um produto", required = true)
			Long idProduto, 
			
			@ApiParam(name = "corpo", value = "Representação com os novos dados de uma foto de um produto", 
				required = true)
			FotoProdutoInput fotoProdutoInput,
			
			@ApiParam(value = "Arquivo da foto de um produto (máximo 500KB, apenas JPG e JPEG", required = true)
			MultipartFile arquivo) throws IOException;
	
	@ApiOperation(value = "Busca a foto de um produto de um restaurante",
			produces = "application/json, image/jpg, image/jpeg")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do restaurante ou produto inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
	})
	FotoProdutoModel buscar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um produto", required = true)
			Long idProduto);
	
	@ApiOperation(value = "Busca a foto de um produto de um restaurante", hidden = true)
	ResponseEntity<Object> servir(
			Long idRestaurante, Long idProduto, String acceptheader) throws HttpMediaTypeNotAcceptableException;
	
	@ApiOperation("Remove a foto de um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Foto excluída"),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante, 
			
			@ApiParam(example = "1", value = "Id de um produto", required = true)
			Long idProduto);
}
