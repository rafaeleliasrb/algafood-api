package com.algaworks.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.input.RestauranteInput;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;

@Component
public class RestauranteInputAssemblerAndDisassembler {

	private ModelMapper modelMapper;
	
	@Autowired
	public RestauranteInputAssemblerAndDisassembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public RestauranteInput toInput(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteInput.class);
	}
	
	public Restaurante toDomainModel(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteInput, restaurante);
	}
}
