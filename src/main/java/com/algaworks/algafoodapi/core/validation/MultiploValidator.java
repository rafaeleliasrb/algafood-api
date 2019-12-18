package com.algaworks.algafoodapi.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		numeroMultiplo = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var numeroMultiploDecimal = BigDecimal.valueOf(numeroMultiplo);
			var resto = valorDecimal.remainder(numeroMultiploDecimal);
			
			return resto.compareTo(BigDecimal.ZERO) == 0;
		}
		return false;
	}

}
