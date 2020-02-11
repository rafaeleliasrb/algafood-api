package com.algaworks.algafoodapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafoodapi.api.v1.model.EnderecoModel;
import com.algaworks.algafoodapi.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafoodapi.domain.model.Endereco;
import com.algaworks.algafoodapi.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		//setando o nome do estado ao inves do estado no DTO EnderecoModel
		var enderecoToEnderecoModel = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		enderecoToEnderecoModel.<String>addMapping(
				enderecoSource -> enderecoSource.getCidade().getEstado().getNome(), 
				(enderecoModelDestination, value) -> enderecoModelDestination.getCidade().setEstado(value));
		
		//deixando de setar o id do item pedido pois o modelmapper tentara colocar o pedidoId no id do ItemPedido
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		return modelMapper;
	}
}
