package com.algaworks.algafoodapi.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafoodapi.api.v2.model.CozinhaModelV2;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelV2OpenApi {

	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PagedModelV2OpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Getter
	@Setter
	private class CozinhasEmbeddedModelOpenApi {
		private List<CozinhaModelV2> cozinhas;
	}
}
