package com.algaworks.algafoodapi.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafoodapi.core.storage.StorageProperties;
import com.algaworks.algafoodapi.domain.exception.StorageException;
import com.algaworks.algafoodapi.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

	private StorageProperties storageProperties;
	
	@Autowired
	public LocalFotoStorageService(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;
	}

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
	public FotoRecuperada recuperar(String nomeArquivo) {
		Path caminhoArquivoExistente = getArquivoPath(nomeArquivo);
		
		try {
			return FotoRecuperada.builder()
				.inputStream(Files.newInputStream(caminhoArquivoExistente))
				.build();
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos().resolve(nomeArquivo);
	}

}
