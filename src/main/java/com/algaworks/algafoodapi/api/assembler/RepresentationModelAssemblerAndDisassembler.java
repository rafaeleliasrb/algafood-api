package com.algaworks.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepresentationModelAssemblerAndDisassembler {

	private ModelMapper modelMapper;
	
	@Autowired
	public RepresentationModelAssemblerAndDisassembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public <T> T toRepresentationModel(Class<T> clazz, Object object) {
		return modelMapper.map(object, clazz);
	}
	
	public <T, E> List<T> toCollectionRepresentationModel(Class<T> clazz, List<E> objects) {
		return objects.stream()
				.map(domainModel -> toRepresentationModel(clazz, domainModel))
				.collect(Collectors.toList());
	}
	
	public <T, E> void copyProperties(T source, E target) {
		modelMapper.map(source, target);
	}
}
