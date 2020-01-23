package com.algaworks.algafoodapi.api.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafoodapi.api.model.CozinhaModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModelOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Coco Bambu")
	private String nome;
	
	@ApiModelProperty(example = "15.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
}
