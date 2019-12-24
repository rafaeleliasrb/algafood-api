package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.EstadoModel;
import com.algaworks.algafoodapi.api.model.input.EstadoInput;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.service.EstadoService;

@RestController
@RequestMapping(value = "estados")
public class EstadoController {

	private final EstadoRepository estadoRepository;
	private final EstadoService estadoService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public EstadoController(EstadoRepository estadoRepository, EstadoService estadoService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.estadoRepository = estadoRepository;
		this.estadoService = estadoService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<EstadoModel> listar() {
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(EstadoModel.class, estadoRepository.findAll()) ;
	}
	
	@GetMapping("/{id}")
	EstadoModel buscar(@PathVariable Long id) {
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(EstadoModel.class, estadoService.buscarOuFalhar(id));
	}
	
	@PostMapping
	ResponseEntity<EstadoModel> adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = representationModelAssemblerAndDisassembler
				.toRepresentationModel(Estado.class, estadoInput);
		
		EstadoModel estadoNovo = representationModelAssemblerAndDisassembler
				.toRepresentationModel(EstadoModel.class, estadoService.salvar(estado));
		
		URI estadoUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(estadoNovo.getId()).toUri();
		return ResponseEntity.created(estadoUri).body(estadoNovo);
	}
	
	@PutMapping("/{id}")
	EstadoModel atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = estadoService.buscarOuFalhar(id);
		
		representationModelAssemblerAndDisassembler.copyProperties(estadoInput, estadoAtual);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(EstadoModel.class, estadoService.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void remover(@PathVariable Long id) {
		estadoService.remover(id);
	}
}
