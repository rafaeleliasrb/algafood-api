package com.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel {

	@ApiModelProperty(example = "cheese-burguer.jpg")
	private String nomeArquivo;
	
	@ApiModelProperty(example = "Cheese burguer à moda da casa")
	private String descricao;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "60523")
	private Long tamanho;
}
