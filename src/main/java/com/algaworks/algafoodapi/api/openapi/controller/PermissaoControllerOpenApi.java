package com.algaworks.algafoodapi.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafoodapi.api.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Formas de pagamento")
public interface PermissaoControllerOpenApi {

	@ApiOperation(value = "Lista permissões")
	CollectionModel<PermissaoModel> listar();
}