package com.algaworks.algafoodapi.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafoodapi.domain.model.Endereco;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {

	@ApiModelProperty(example = "38400-000")
	private String cep;
	
	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;
	
	@ApiModelProperty(example = "500")
	private String numero;
	
	@ApiModelProperty(example = "Apto 801")
	private String complemento;
	
	@ApiModelProperty(example = "Vila Monte Alegre")
	private String bairro;
	
	private CidadeResumoModel cidade;
	
	public EnderecoModel(Endereco endereco) {
		this.cep = endereco.getCep();
		this.logradouro = endereco.getLogradouro();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.cidade = new CidadeResumoModel(endereco.getCidade());
	}
	
	public static EnderecoModel criarEnderecoModelComLinks(Endereco endereco) {
		EnderecoModel enderecoModel = new EnderecoModel(endereco);
		
		enderecoModel.setCidade(CidadeResumoModel.criarCidadeResumoModelComLinks(endereco.getCidade()));
		
		return enderecoModel;
	}
}
