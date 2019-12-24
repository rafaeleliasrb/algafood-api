package com.algaworks.algafoodapi.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	//será utilizado DTOs logo não se faz necessario a utilização de Mixins
	public JacksonMixinModule() {
//		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
//		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
//		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}
}
