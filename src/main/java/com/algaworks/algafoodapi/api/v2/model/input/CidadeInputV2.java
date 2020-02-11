package com.algaworks.algafoodapi.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.EstadoService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CidadeInput")
@Setter
@Getter
public class CidadeInputV2 {

	@ApiModelProperty(example = "Fortaleza", required = true)
	@NotBlank
	private String nomeCidade;
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long idEstado;

	public Cidade novaCidade(EstadoService estadoService) {
		Estado estadoExistente = estadoService.buscarOuFalhar(this.idEstado);
		return new Cidade(nomeCidade, estadoExistente);
	}
	
	public Cidade cidadeAtualizada(Long idCidade, CidadeService cidadeService, EstadoService estadoService) {
		Cidade cidadeAtual = cidadeService.buscarOuFalhar(idCidade);
		Estado novoEstado = estadoService.buscarOuFalhar(this.idEstado);
		cidadeAtual.setNome(nomeCidade);
		cidadeAtual.setEstado(novoEstado);
		
		return cidadeAtual;
	}
}
