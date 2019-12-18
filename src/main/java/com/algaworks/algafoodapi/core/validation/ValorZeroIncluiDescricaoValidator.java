package com.algaworks.algafoodapi.core.validation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;

	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		try {
			//Via ReflectionUtils
			Field field = ReflectionUtils.findField(objetoValidacao.getClass(), valorField);
			field.setAccessible(true);
			Optional<BigDecimal> valorOpt = 
					Optional.ofNullable((BigDecimal) ReflectionUtils.getField(field, objetoValidacao));
		
			
			//Via BeanUtils
			Optional<String> descricaoOpt = 
					Optional.ofNullable((String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), 
							descricaoField).getReadMethod().invoke(objetoValidacao));
			
			boolean isValorZero = valorOpt.isPresent() && valorOpt.get().compareTo(BigDecimal.ZERO) == 0;
			if(isValorZero) {
				return descricaoOpt.isPresent() && 
						descricaoOpt.get().toLowerCase().contains(descricaoObrigatoria.toLowerCase());
			}

			return true;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}
	
	
}

