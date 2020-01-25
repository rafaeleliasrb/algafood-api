package com.algaworks.algafoodapi.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {

	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(example = "Cheese Burguer da casa", position = 5)
	private String nome;
	
	@ApiModelProperty(example = "Cheese Burguer com tempero especial", position = 10)
	private String descricao;
	
	@ApiModelProperty(example = "10.00", position = 15)
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", position = 20)
	private Boolean ativo;
}
