package com.algaworks.algafoodapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.model.PermissaoModel;
import com.algaworks.algafoodapi.api.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafoodapi.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(value = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	private final PermissaoRepository repository;
	
	@Autowired
	public PermissaoController(PermissaoRepository permissaoRepository) {
		this.repository = permissaoRepository;
	}

	@GetMapping
	public CollectionModel<PermissaoModel> listar() {
		return PermissaoModel.criarCollectionPermissaoModelComLinks(repository.findAll());
	}
}