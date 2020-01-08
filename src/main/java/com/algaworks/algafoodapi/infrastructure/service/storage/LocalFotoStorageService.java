package com.algaworks.algafoodapi.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafoodapi.domain.exception.StorageException;
import com.algaworks.algafoodapi.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path caminhoArquivo = getArquivoPath(novaFoto.getNomeArquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(caminhoArquivo));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar o arquivo.", e);
		}
	}
	
	@Override
	public void remover(String nomeArquivo) {
		try {
			Path caminhoArquivoExistente = getArquivoPath(nomeArquivo);
			
			Files.deleteIfExists(caminhoArquivoExistente);
		} catch (Exception e) {
			throw new StorageException("Não foi possível remover o arquivo.", e);
		}
	}
	
	@Override
	public InputStream recuperar(String nomeArquivo) {
		Path caminhoArquivoExistente = getArquivoPath(nomeArquivo);
		
		try {
			return Files.newInputStream(caminhoArquivoExistente);
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(nomeArquivo);
	}

}
