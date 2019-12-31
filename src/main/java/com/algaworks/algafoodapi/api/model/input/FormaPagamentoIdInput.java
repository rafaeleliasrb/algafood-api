package com.algaworks.algafoodapi.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class FormaPagamentoIdInput {

	@NotNull
	private Long id;
}
