package com.algaworks.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);

	InputStream recuperar(String nomeArquivo);
	
	default String gerarNomeArquivo(String nomeArquivo) {
		return UUID.randomUUID().toString() + "_" + nomeArquivo;
	}
	
	default void substituir(Optional<String> nomeFotoExistente, NovaFoto novaFoto) {
		if(nomeFotoExistente.isPresent()) {
			remover(nomeFotoExistente.get());
		}
		
		armazenar(novaFoto);
	}
	
	@Getter
	@Builder
	class NovaFoto {
		private String nomeArquivo;
		private InputStream inputStream;
	}

}
