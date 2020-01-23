package com.algaworks.algafoodapi.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoInput {

	@ApiModelProperty(example = "1")
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "2")
	@NotNull
	@Positive
	private Integer quantidade;
	
	@ApiModelProperty(example = "Bem passado")
	private String observacao;
}
