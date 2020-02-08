package com.algaworks.algafoodapi.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.CozinhaService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

	@ApiModelProperty(example = "Coco Bambu", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "10.00")
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;
	
	@Deprecated
	public RestauranteInput() {}
	
	public RestauranteInput(Restaurante restaurante) {
		this.nome = restaurante.getNome();
		this.taxaFrete = restaurante.getTaxaFrete();
		this.cozinha = new CozinhaIdInput(restaurante.getCozinha().getId());
		this.endereco = new EnderecoInput(restaurante.getEndereco());
	}
	
	public Restaurante novoRestaurante(CozinhaService cozinhaService, CidadeService cidadeService) {
		Cozinha cozinhaExistente = cozinhaService.buscarOuFalhar(cozinha.getId());
		
		return new Restaurante(nome, taxaFrete, endereco.novoEndereco(cidadeService), cozinhaExistente);
	}
	
	public Restaurante restauranteAtualizado(Long idRestaurante, RestauranteService restauranteService,
			CozinhaService cozinhaService, CidadeService cidadeService) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		
		restaurante.setNome(nome);
		restaurante.setTaxaFrete(taxaFrete);
		restaurante.setCozinha(cozinhaService.buscarOuFalhar(cozinha.getId()));
		restaurante.setEndereco(endereco.novoEndereco(cidadeService));
		
		return restaurante;
	}
}
