package com.algaworks.algafoodapi.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CozinhaInput")
@Getter
@Setter
public class CozinhaInputV2 {

	@ApiModelProperty(example = "Cearense", required = true)
	@NotBlank
	private String nomeCozinha;
}
