package com.algaworks.algafoodapi.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel {

	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Cheese Burguer")
	private String produtoNome;
	
	@ApiModelProperty(example = "2")
	private Integer quantidade;
	
	@ApiModelProperty(example = "15.00")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "30.00")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Ao ponto")
	private String observacao;
}
