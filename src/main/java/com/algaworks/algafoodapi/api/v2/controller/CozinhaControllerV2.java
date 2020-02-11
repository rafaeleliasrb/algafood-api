package com.algaworks.algafoodapi.api.v2.controller;

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

import com.algaworks.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algaworks.algafoodapi.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafoodapi.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

	private final CozinhaRepository cozinhaRepository;
	private final CozinhaService cozinhaService;
	private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@Autowired
	public CozinhaControllerV2(CozinhaRepository cozinhaRepository, CozinhaService cozinhaService,
			PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
		this.cozinhaRepository = cozinhaRepository;
		this.cozinhaService = cozinhaService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhas = cozinhaRepository.findAll(pageable);

		return pagedResourcesAssembler.toModel(cozinhas, CozinhaModelV2::criarCozinhaModelComLinks);
	}

	@GetMapping(value = "/{id}")
	public CozinhaModelV2 buscar(@PathVariable Long id) {
		return CozinhaModelV2.criarCozinhaModelComLinks(cozinhaService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		Cozinha novaCozinha = cozinhaService.salvar(new Cozinha(cozinhaInput.getNomeCozinha()));
		
		return CozinhaModelV2.criarCozinhaModelComLinks(novaCozinha);
	}
	
	@PutMapping("/{id}")
	public CozinhaModelV2 atualizar(@RequestBody @Valid CozinhaInputV2 cozinhaInput, @PathVariable Long id) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		cozinhaAtual.setNome(cozinhaInput.getNomeCozinha());
		
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		
		return CozinhaModelV2.criarCozinhaModelComLinks(cozinhaAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cozinhaService.remover(id);
	}
}
