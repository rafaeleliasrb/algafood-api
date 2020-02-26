package com.algaworks.algafoodapi.core.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptConfig {

	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static String criptografar(String senha) {
		return passwordEncoder.encode(senha);
	}
	
	public static boolean verificarSenhaCorreta(String senhaAVerificar, String senhaAtualComHash) {
		return passwordEncoder.matches(senhaAVerificar, senhaAtualComHash);
	}
}
