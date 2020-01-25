package com.algaworks.algafoodapi.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@ApiModelProperty(example = "1", required = true, position = 1)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "Cheese Burguer da casa", required = true, position = 5)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(example = "10.00", position = 15)
	@PositiveOrZero
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", position = 20)
	@NotNull
	private Boolean ativo;
}
