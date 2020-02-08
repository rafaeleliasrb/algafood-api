package com.algaworks.algafoodapi.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.controller.RestauranteController;
import com.algaworks.algafoodapi.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafoodapi.api.controller.RestauranteProdutoController;
import com.algaworks.algafoodapi.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Coco Bambu")
	private String nome;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
	@ApiModelProperty(example = "true")
	private Boolean aberto;
	
	private EnderecoModel endereco;

	public RestauranteModel(Restaurante restaurante) {
		this.id = restaurante.getId();
		this.nome = restaurante.getNome();
		this.taxaFrete = restaurante.getTaxaFrete();
		this.cozinha = new CozinhaModel(restaurante.getCozinha());
		this.ativo = restaurante.getAtivo();
		this.aberto = restaurante.getAberto();
		this.endereco = new EnderecoModel(restaurante.getEndereco());
	}
	
	public static RestauranteModel criarRestauranteModelComLinks(Restaurante restaurante) {
		RestauranteModel restauranteModel = new RestauranteModel(restaurante);
		
		restauranteModel.add(linkTo(methodOn(RestauranteController.class).buscar(restaurante.getId())).withSelfRel());
		restauranteModel.add(new Link(UriTemplate.of(linkTo(RestauranteController.class).toUri().toString(), 
				TemplateVariableEnum.projecaoVariables()), "restaurantes"));

		if(restaurante.permiteAbrir()) {
			restauranteModel.add(linkTo(methodOn(RestauranteController.class)
					.abrir(restaurante.getId())).withRel("abrir"));
		}
		
		if(restaurante.permiteFechar()) {
			restauranteModel.add(linkTo(methodOn(RestauranteController.class)
					.fechar(restaurante.getId())).withRel("fechar"));
		}
		
		if(restaurante.permiteAtivar()) {
			restauranteModel.add(linkTo(methodOn(RestauranteController.class)
					.ativar(restaurante.getId())).withRel("ativar"));
		}

		if(restaurante.permiteInativar()) {
			restauranteModel.add(linkTo(methodOn(RestauranteController.class)
					.inativar(restaurante.getId())).withRel("inativar"));
		}
		
		restauranteModel.setCozinha(CozinhaModel.criarCozinhaModelComLinks(restaurante.getCozinha()));
		restauranteModel.setEndereco(EnderecoModel.criarEnderecoModelComLinks(restaurante.getEndereco()));
		
		restauranteModel.add(linkTo(methodOn(RestauranteProdutoController.class).listar(restaurante.getId(), null))
				.withRel("produtos"));
		restauranteModel.add(linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restaurante.getId()))
				.withRel("formas-pagamento"));
		restauranteModel.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(restaurante.getId()))
				.withRel("responsaveis"));
		
		return restauranteModel;
	}
}
