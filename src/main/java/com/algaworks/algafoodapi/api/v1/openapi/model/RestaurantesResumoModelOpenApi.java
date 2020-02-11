package com.algaworks.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafoodapi.api.v1.model.RestauranteModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantesModel")
@Data
public class RestaurantesResumoModelOpenApi {

	private RestaurantesEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("RestaurantesEmbeddedModel")
	@Data
	private class RestaurantesEmbeddedModelOpenApi {
		private List<RestauranteModel> restaurantes;
	}
}
