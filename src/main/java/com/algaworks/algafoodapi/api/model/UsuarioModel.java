package com.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(example = "José", position = 5)
	private String nome;
	
	@ApiModelProperty(example = "jose@email.com", position = 10)
	private String email;
}
