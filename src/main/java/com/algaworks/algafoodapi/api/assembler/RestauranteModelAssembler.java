package com.algaworks.algafoodapi.api.assembler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	private ModelMapper modelMapper;
	
	@Autowired
	public RestauranteModelAssembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
	}
	
	public Optional<RestauranteModel> toOptionalModel(Optional<Restaurante> restauranteOpt) {
		if(restauranteOpt.isPresent()) {
			return Optional.of(modelMapper.map(restauranteOpt.get(), RestauranteModel.class));
		}
		return Optional.empty();
	}
	
	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
