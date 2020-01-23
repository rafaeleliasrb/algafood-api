package com.algaworks.algafoodapi.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {

	@ApiModelProperty(example = "1", value = "Id do cliente")
	private Long clienteId;
	
	@ApiModelProperty(example = "1", value = "Id do restaurante")
	private Long restauranteId;
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z", value = "Valor limite inicial da data de criação")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z", value = "Valor limite final da data de criação")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
}
