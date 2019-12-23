package com.algaworks.algafoodapi.core.jackson;

import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.mixin.CidadeMixin;
import com.algaworks.algafoodapi.api.model.mixin.CozinhaMixin;
import com.algaworks.algafoodapi.api.model.mixin.RestauranteMixin;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}
}
