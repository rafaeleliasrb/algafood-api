package com.algaworks.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafoodapi.api.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PagedModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Getter
	@Setter
	private class CozinhasEmbeddedModelOpenApi {
		private List<CozinhaModel> cozinhas;
	}
}
