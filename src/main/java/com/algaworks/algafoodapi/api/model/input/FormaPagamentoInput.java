package com.algaworks.algafoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.service.FormaPagamentoService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {

	@ApiModelProperty(example = "Débito", required = true)
	@NotBlank
	private String descricao;
	
	public FormaPagamento novaFormaPagamento() {
		return new FormaPagamento(descricao);
	}
	
	public FormaPagamento formaPagamentoAtualizada(Long idFormaPagamento, FormaPagamentoService formaPagamentoService) {
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
		formaPagamento.setDescricao(descricao);
		
		return formaPagamento;
	}
}
