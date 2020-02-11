package com.algaworks.algafoodapi.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.algaworks.algafoodapi.domain.model.Usuario;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@ApiModelProperty(example = "João", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "joao@email.com.br", required = true)
	@NotBlank
	@Email
	private String email;
	
	@ApiModelProperty(example = "12345", required = true)
	@NotBlank
	private String senha;
	
	public Usuario novoUsuario() {
		return new Usuario(nome, email, senha);
	}
}
