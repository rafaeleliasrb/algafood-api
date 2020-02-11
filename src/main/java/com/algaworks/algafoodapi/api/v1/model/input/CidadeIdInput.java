package com.algaworks.algafoodapi.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInput {

	@ApiModelProperty(example = "1")
	@NotNull
	private Long id;

	@Deprecated
	public CidadeIdInput() {}
	
	public CidadeIdInput(@NotNull Long id) {
		this.id = id;
	}
}
