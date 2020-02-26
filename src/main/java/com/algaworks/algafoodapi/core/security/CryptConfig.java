package com.algaworks.algafoodapi.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CryptConfig {

	//private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static String criptografar(String senha) {
		return passwordEncoder().encode(senha);
	}
	
	public static boolean verificarSenhaCorreta(String senhaAVerificar, String senhaAtualComHash) {
		return passwordEncoder().matches(senhaAVerificar, senhaAtualComHash);
	}
}
