package com.algaworks.algafoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {

	@ApiModelProperty(example = "12345", required = true)
	@NotBlank
	private String senhaAtual;
	
	@ApiModelProperty(example = "54321", required = true)
	@NotBlank
	private String novaSenha;
}
