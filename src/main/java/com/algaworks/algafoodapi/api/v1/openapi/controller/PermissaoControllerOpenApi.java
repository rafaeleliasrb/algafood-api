package com.algaworks.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafoodapi.api.v1.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Formas de pagamento")
public interface PermissaoControllerOpenApi {

	@ApiOperation(value = "Lista permissões")
	CollectionModel<PermissaoModel> listar();
}