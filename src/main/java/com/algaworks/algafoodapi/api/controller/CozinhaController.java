package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algaworks.algafoodapi.api.model.CozinhaModel;
import com.algaworks.algafoodapi.api.model.CozinhaXMLWrapper;
import com.algaworks.algafoodapi.api.model.input.CozinhaInput;
import com.algaworks.algafoodapi.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	private final CozinhaRepository cozinhaRepository;
	private final CozinhaService cozinhaService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public CozinhaController(CozinhaRepository cozinhaRepository, CozinhaService cozinhaService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.cozinhaRepository = cozinhaRepository;
		this.cozinhaService = cozinhaService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhas = cozinhaRepository.findAll(pageable);
		
		List<CozinhaModel> cozinhasModel = representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(CozinhaModel.class, cozinhas.getContent());
		
		return new PageImpl<>(cozinhasModel, pageable, cozinhas.getTotalElements());
	}

	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
	public CozinhaXMLWrapper listarXML() {
		return new CozinhaXMLWrapper(representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(CozinhaModel.class, cozinhaRepository.findAll()));
	}
	
	@GetMapping(value = "/{id}")
	public CozinhaModel buscar(@PathVariable Long id) {
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(CozinhaModel.class, cozinhaService.buscarOuFalhar(id));
	}
	
	@PostMapping
	public ResponseEntity<CozinhaModel> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha novaCozinha = representationModelAssemblerAndDisassembler
				.toRepresentationModel(Cozinha.class, cozinhaInput);
		
		CozinhaModel cozinha = representationModelAssemblerAndDisassembler
				.toRepresentationModel(CozinhaModel.class, cozinhaService.salvar(novaCozinha));
		
		URI cozinhaUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/{id}").buildAndExpand(cozinha.getId()).toUri();
		return ResponseEntity.created(cozinhaUri).body(cozinha);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cozinhaService.remover(id);
	}
	
	@PutMapping("/{id}")
	public CozinhaModel atualizar(@RequestBody @Valid CozinhaInput cozinhaInput, @PathVariable Long id) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		representationModelAssemblerAndDisassembler.copyProperties(cozinhaInput, cozinhaAtual);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(CozinhaModel.class, cozinhaService.salvar(cozinhaAtual));
	}
}
