package com.algaworks.algafoodapi.core.security.authorizationserver;

import org.springframework.security.core.userdetails.User;

import com.algaworks.algafoodapi.domain.model.Usuario;

public class AuthUser extends User {
	
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String fullName;
	
	public AuthUser(Usuario usuario) {
		super(usuario.getEmail(), usuario.getSenha(), usuario.getAuthorities());
		this.userId = usuario.getId();
		this.fullName = usuario.getNome();
	}

	public Long getUserId() {
		return userId;
	}
	
	public String getFullName() {
		return fullName;
	}
}
