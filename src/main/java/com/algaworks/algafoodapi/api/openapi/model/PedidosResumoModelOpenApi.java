package com.algaworks.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafoodapi.api.model.PedidoResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Getter
@Setter
public class PedidosResumoModelOpenApi {

	private PedidosEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PagedModelOpenApi page;
	
	@ApiModel("PedidosEmbeddedModel")
	@Getter
	@Setter
	private class PedidosEmbeddedModelOpenApi {
		private List<PedidoResumoModel> pedidos;
	}
}
