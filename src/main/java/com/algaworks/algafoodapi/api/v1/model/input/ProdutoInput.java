package com.algaworks.algafoodapi.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.ProdutoService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@ApiModelProperty(example = "1", required = true, position = 1)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "Cheese Burguer da casa", required = true, position = 5)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(example = "10.00", position = 15)
	@PositiveOrZero
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", position = 20)
	@NotNull
	private Boolean ativo;
	
	public Produto novoProduto() {
		return new Produto(nome, descricao, preco, ativo);
	}
	
	public Produto produtoAtualizado(Long idProduto, Restaurante restaurante, ProdutoService produtoService) {
		Produto produtoAtual = produtoService.buscarOuFalhar(idProduto, restaurante);
		
		produtoAtual.setNome(nome);
		produtoAtual.setDescricao(descricao);
		produtoAtual.setPreco(preco);
		produtoAtual.setAtivo(ativo);
		
		return produtoAtual;
	}
}
