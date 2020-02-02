package com.algaworks.algafoodapi.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.algaworks.algafoodapi.domain.model.ItemPedido;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.ProdutoService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoInput {

	@ApiModelProperty(example = "1")
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "2")
	@NotNull
	@Positive
	private Integer quantidade;
	
	@ApiModelProperty(example = "Bem passado")
	private String observacao;
	
	public ItemPedido itemPedido(Restaurante restaurante, ProdutoService produtoService) {
		Produto produto = produtoService.buscarOuFalhar(produtoId, restaurante);
		
		ItemPedido item = new ItemPedido(quantidade, produto.getPreco(), produto);
		item.setProduto(produto);
		item.setPrecoUnitario(produto.getPreco());
		
		return item;
	}
}
