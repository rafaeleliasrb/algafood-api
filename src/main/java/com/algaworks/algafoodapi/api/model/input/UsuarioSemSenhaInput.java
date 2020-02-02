package com.algaworks.algafoodapi.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.service.UsuarioService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSemSenhaInput {

	@ApiModelProperty(example = "João", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "joao@email.com.br", required = true)
	@NotBlank
	@Email
	private String email;
	
	public Usuario usuarioAtualizado(Long idUsuario, UsuarioService usuarioService) {
		Usuario usuario = usuarioService.buscarOuFalhar(idUsuario);
		usuario.setNome(nome);
		usuario.setEmail(email);
		return usuario;
	}
}
