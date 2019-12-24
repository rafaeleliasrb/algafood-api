package com.algaworks.algafoodapi.api.controller;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MergeadorDeRecurso {

	public <T> void mergeCampos(Class<T> clazz, Map<String, Object> propriedadesOrigem, T objetoeDestino,
			HttpServletRequest request) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			T objetoOrigem = mapper.convertValue(propriedadesOrigem, clazz);
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			propriedadesOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(clazz, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, objetoOrigem);
				ReflectionUtils.setField(field, objetoeDestino, novoValor);
			});
		} catch (IllegalArgumentException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, 
					new ServletServerHttpRequest(request));
		}
	}
}
