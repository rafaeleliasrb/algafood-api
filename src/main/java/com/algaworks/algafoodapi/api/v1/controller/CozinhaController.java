package com.algaworks.algafoodapi.api.v1.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.model.CozinhaModel;
import com.algaworks.algafoodapi.api.v1.model.CozinhaXMLWrapper;
import com.algaworks.algafoodapi.api.v1.model.input.CozinhaInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	private final CozinhaRepository cozinhaRepository;
	private final CozinhaService cozinhaService;
	private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@Autowired
	public CozinhaController(CozinhaRepository cozinhaRepository, CozinhaService cozinhaService,
			PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
		this.cozinhaRepository = cozinhaRepository;
		this.cozinhaService = cozinhaService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhas = cozinhaRepository.findAll(pageable);

		return pagedResourcesAssembler.toModel(cozinhas, CozinhaModel::criarCozinhaModelComLinks);
	}

	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
	public CozinhaXMLWrapper listarXML() {
		return new CozinhaXMLWrapper(cozinhaRepository.findAll().stream()
					.map(CozinhaModel::new)
					.collect(Collectors.toList()));
	}
	
	@GetMapping(value = "/{id}")
	public CozinhaModel buscar(@PathVariable Long id) {
		return CozinhaModel.criarCozinhaModelComLinks(cozinhaService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha novaCozinha = cozinhaService.salvar(new Cozinha(cozinhaInput.getNome()));
		
		return CozinhaModel.criarCozinhaModelComLinks(novaCozinha);
	}
	
	@PutMapping("/{id}")
	public CozinhaModel atualizar(@RequestBody @Valid CozinhaInput cozinhaInput, @PathVariable Long id) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		cozinhaAtual.setNome(cozinhaInput.getNome());
		
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		
		return CozinhaModel.criarCozinhaModelComLinks(cozinhaAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cozinhaService.remover(id);
	}
}
