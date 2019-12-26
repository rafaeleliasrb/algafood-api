package com.algaworks.algafoodapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafoodapi.api.model.EnderecoModel;
import com.algaworks.algafoodapi.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		var enderecoToEnderecoModel = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		enderecoToEnderecoModel.<String>addMapping(
				enderecoSource -> enderecoSource.getCidade().getEstado().getNome(), 
				(enderecoModelDestination, value) -> enderecoModelDestination.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
