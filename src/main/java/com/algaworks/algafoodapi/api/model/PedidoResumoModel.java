package com.algaworks.algafoodapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel {

	@ApiModelProperty(example = "f229e133-2c0a-11ea-9866-d09466a32949")
	private String codigo;
	
	@ApiModelProperty(example = "298.9")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "308.9")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
	
	@ApiModelProperty(example = "2020-01-21T23:15:22Z")
	private OffsetDateTime dataCriacao;

	private RestauranteResumoModel restaurante;
	
	private UsuarioModel cliente;
}
