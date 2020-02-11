package com.algaworks.algafoodapi.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Endereco;
import com.algaworks.algafoodapi.domain.service.CidadeService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@ApiModelProperty(example = "38400-000", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "500", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "Apto 801")
	private String complemento;

	@ApiModelProperty(example = "Vila Monte Alegre", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;
	
	@Deprecated
	public EnderecoInput() {}
	
	public EnderecoInput(Endereco endereco) {
		this.cep = endereco.getCep();
		this.logradouro = endereco.getLogradouro();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.cidade = new CidadeIdInput(endereco.getCidade().getId());
	}
	
	public Endereco novoEndereco(CidadeService cidadeService) {
		Cidade cidadeExistente = cidadeService.buscarOuFalhar(cidade.getId());
		return new Endereco(cep, logradouro, numero, complemento, bairro, cidadeExistente);
	}
}
