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
import com.algaworks.algafoodapi.api.model.CidadeModel;
import com.algaworks.algafoodapi.api.model.input.CidadeInput;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	private final CidadeRepository cidadeRepository;
	private final CidadeService cidadeService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public CidadeController(CidadeRepository cidadeRepository, CidadeService cidadeService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.cidadeRepository = cidadeRepository;
		this.cidadeService = cidadeService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<CidadeModel> listar() {
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(CidadeModel.class, cidadeRepository.findAll());
	}
	
	@GetMapping("/{id}")
	CidadeModel buscar(@PathVariable Long id) {
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(CidadeModel.class, cidadeService.buscarOuFalhar(id));
	}
	
	@PostMapping
	ResponseEntity<CidadeModel> adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = representationModelAssemblerAndDisassembler
				.toRepresentationModel(Cidade.class, cidadeInput);
		
		CidadeModel cidadeNova = representationModelAssemblerAndDisassembler
				.toRepresentationModel(CidadeModel.class, cidadeService.salvar(cidade));
		
		URI cidadeUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cidade.getId()).toUri();
		
		return ResponseEntity.created(cidadeUri).body(cidadeNova);
	}
	
	@PutMapping("/{id}")
	CidadeModel atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		cidadeAtual.setEstado(new Estado());
		
		representationModelAssemblerAndDisassembler.copyProperties(cidadeInput, cidadeAtual);
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(CidadeModel.class, cidadeService.salvar(cidadeAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}
}
