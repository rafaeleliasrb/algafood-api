package com.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representação de um cidade")
@Getter
@Setter
public class CidadeModel {

//	@ApiModelProperty(value = "Id da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Fortaleza")
	private String nome;
	
	
	private EstadoModel estado;
}
