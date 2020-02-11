package com.algaworks.algafoodapi.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class FormaPagamentoIdInput {

	@ApiModelProperty(example = "1")
	@NotNull
	private Long id;
}
