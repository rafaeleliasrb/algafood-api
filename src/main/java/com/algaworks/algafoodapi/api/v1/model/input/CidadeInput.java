package com.algaworks.algafoodapi.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.EstadoService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@ApiModelProperty(example = "Fortaleza", required = true)
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdInput estado;
	
	public Cidade novaCidade(EstadoService estadoService) {
		Estado estadoExistente = estadoService.buscarOuFalhar(this.estado.getId());
		return new Cidade(nome, estadoExistente);
	}
	
	public Cidade cidadeAtualizada(Long idCidade, CidadeService cidadeService, EstadoService estadoService) {
		Cidade cidadeAtual = cidadeService.buscarOuFalhar(idCidade);
		Estado novoEstado = estadoService.buscarOuFalhar(this.estado.getId());
		cidadeAtual.setNome(nome);
		cidadeAtual.setEstado(novoEstado);
		
		return cidadeAtual;
	}
}
