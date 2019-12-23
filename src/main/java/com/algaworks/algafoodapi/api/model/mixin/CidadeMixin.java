package com.algaworks.algafoodapi.api.model.mixin;

import com.algaworks.algafoodapi.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
}
