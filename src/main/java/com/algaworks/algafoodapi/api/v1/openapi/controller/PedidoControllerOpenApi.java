package com.algaworks.algafoodapi.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.v1.model.PedidoModel;
import com.algaworks.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algaworks.algafoodapi.api.v1.model.input.PedidoInput;
import com.algaworks.algafoodapi.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation("Pesquisa um pedido")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Requisição inválida (erro do cliente)", response = Problem.class)
	})
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes de propriedades para filtra a resposta separados por vírgula",
				name = "campos", paramType = "query", type = "string")
	})
	PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

	@ApiOperation("Busca um pedido por Id")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	PedidoModel buscar(
			@ApiParam(example = "f229e133-2c0a-11ea-9866-d09466a32949", value = "Código de um pedido", required = true)
			String codigoPedido);
	
	@ApiOperation("Insere um pedido")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Pedido criado")
	})
	PedidoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um pedido", required = true)
			PedidoInput pedidoInput);
}
