package com.algaworks.algafoodapi.api.v1.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoodapi.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafoodapi.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafoodapi.domain.model.FotoProduto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "fotos-produto")
@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

	@ApiModelProperty(example = "cheese-burguer.jpg")
	private String nomeArquivo;
	
	@ApiModelProperty(example = "Cheese burguer à moda da casa")
	private String descricao;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "60523")
	private Long tamanho;

	public FotoProdutoModel(FotoProduto fotoProduto) {
		this.nomeArquivo = fotoProduto.getNomeArquivo();
		this.descricao = fotoProduto.getDescricao();
		this.contentType = fotoProduto.getContentType();
		this.tamanho = fotoProduto.getTamanho();
	}
	
	public static FotoProdutoModel criarFotoProdutoModelComLinksRestauranteProduto(FotoProduto fotoProduto, 
			Long idRestaurante, Long idProduto) {
		FotoProdutoModel fotoProdutoModel = new FotoProdutoModel(fotoProduto);
		
		fotoProdutoModel.add(linkTo(methodOn(RestauranteProdutoFotoController.class).buscar(idRestaurante, idProduto))
				.withSelfRel());
		fotoProdutoModel.add(linkTo(methodOn(RestauranteProdutoController.class).buscar(idRestaurante, idProduto))
				.withRel("produto"));
		
		return fotoProdutoModel;
	}
}
