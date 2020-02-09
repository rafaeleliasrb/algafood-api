package com.algaworks.algafoodapi.api.openapi.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.api.model.RestauranteResumoModel;
import com.algaworks.algafoodapi.api.model.input.RestauranteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de restaurantes", allowableValues = "apenas-nome",
				name = "projecao", paramType = "query", type = "string")
	})
	CollectionModel<RestauranteResumoModel> listar();
	
	@ApiIgnore
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNome();
	
	@ApiOperation("Busca restaurante por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteModel buscar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long id);
	
	@ApiOperation("Insere um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante adicionado")
	})
	RestauranteModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
			RestauranteInput restauranteInput);
	
	@ApiOperation("Atualiza um restaurante por Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteModel atualizar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long id, 
			
			@ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
			RestauranteInput restauranteInput);
	
	@ApiOperation("Remove um restaurante por Id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante removido"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long id);
	
	@ApiOperation("Atualiza um restaurante parcialmente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado parcialmente"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	@ApiImplicitParams(
	        @ApiImplicitParam(name = "campos", dataType = "RestauranteInput")
	)
	RestauranteModel atualizarParcial(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long id, 
			
			@ApiParam(name = "campos", value = "Nomes e valores das propriedades a serem atualizadas")
			Map<String, Object> camposOrigem,
			
			HttpServletRequest request);
	
	@ApiOperation("Ativa um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante ativado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> ativar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante);
	
	@ApiOperation("Inativa um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante inativado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> inativar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante);
	
	@ApiOperation("Abre um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante aberto"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> abrir(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante);
	
	@ApiOperation("Fecha um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante fechado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> fechar(
			@ApiParam(example = "1", value = "Id de um restaurante", required = true)
			Long idRestaurante);
	
	@ApiOperation("Ativa uma lista de restaurantes")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurantes ativados"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> ativarMutiplos(
			@ApiParam(example = "{1, 2}", value = "Ids de restaurantes", required = true)
			List<Long> idsRestaurante);
	
	@ApiOperation("Desativa uma lista de restaurantes")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante desativados"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> desativarMutiplos(
			@ApiParam(example = "{1, 2}", value = "Ids de restaurantes", required = true)
			List<Long> idsRestaurante);

	/* Endpoints extras */
	@ApiOperation(value = "Busca restaurantes por parâmetros")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Requisição inválida (erro do cliente)", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	List<RestauranteModel> buscar(
			@ApiParam(example = "Coco Bambu", value = "Nome de um restaurante")
			String nome, 
			
			@ApiParam(example = "5.00", value = "Valor inicial da taxa frete")
			BigDecimal taxaFreteInicial, 
			
			@ApiParam(example = "15.00", value = "Valor final da taxa frete")
			BigDecimal taxaFreteFinal);
	
	@ApiOperation(value = "Busca restaurantes por parâmetros")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Requisição inválida (erro do cliente)", response = Problem.class),
	})
	List<RestauranteModel> buscarComCriteria(
			@ApiParam(example = "Coco Bambu", value = "Nome de um restaurante")
			String nome, 
			
			@ApiParam(example = "5.00", value = "Valor inicial da taxa frete")
			BigDecimal taxaFreteInicial, 
			
			@ApiParam(example = "15.00", value = "Valor final da taxa frete")
			BigDecimal taxaFreteFinal);
	
	@ApiOperation("Busca restaurantes com frete gratis")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Requisição inválida (erro do cliente)", response = Problem.class),
	})
	List<RestauranteModel> buscarComFreteGratis(
			@ApiParam(example = "Coco Bambu", value = "Nome de um restaurante", required = true)
			String nome);
	
	@ApiOperation("Busca o primeiro restaurante")
	Optional<RestauranteModel> buscarPrimeiro();
}
