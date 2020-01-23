package com.algaworks.algafoodapi.api.model;

import com.algaworks.algafoodapi.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModel {

	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class})
	private Long id;
	
	@ApiModelProperty(example = "Cearense")
	@JsonView({RestauranteView.Resumo.class})
	private String nome;
}
