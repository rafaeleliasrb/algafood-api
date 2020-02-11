package com.algaworks.algafoodapi.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoodapi.api.v1.model.input.OffsetInput;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatisticas")
public interface EstatisticasControllerOpenApi {

	@ApiOperation("Consulta estatísticas de vendas diárias")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "restauranteId", value = "Id de um restaurante",
				example = "1", dataType = "long"),
		@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da crição do pedido",
			example = "2019-10-28T00:00:00Z", dataType = "date-time"),
		@ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da crição do pedido",
			example = "2019-10-30T23:59:59Z", dataType = "date-time")
	})
	List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter, 
			@ApiParam(example = "+00:00", value = "Deslocamento de fuso horário a ser considerado na consulta")
			OffsetInput offsetInput);
	
	ResponseEntity<byte[]> pesquisarVendasDiariasPdf(VendaDiariaFilter filter, OffsetInput offsetInput);
}