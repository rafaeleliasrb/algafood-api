package com.algaworks.algafoodapi.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafoodapi.core.storage.StorageProperties;
import com.algaworks.algafoodapi.domain.exception.StorageException;
import com.algaworks.algafoodapi.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {

	private final AmazonS3 amazonS3;
	private final StorageProperties storageProperties;
	
	@Autowired
	public S3FotoStorageService(AmazonS3 amazonS3, StorageProperties storageProperties) {
		this.amazonS3 = amazonS3;
		this.storageProperties = storageProperties;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			var caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(), 
					caminhoArquivo, 
					novaFoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar o arquivo ao Amazon S3", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			var caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			
			var deleteObjectRequest = new DeleteObjectRequest(
					storageProperties.getS3().getBucket(), 
					caminhoArquivo);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível remover o arquivo ao Amazon S3", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), getCaminhoArquivo(nomeArquivo));
		return FotoRecuperada.builder()
				.url(url.toString())
				.build();
	}
	
	private String getCaminhoArquivo(String nomeArquivo) {
		return storageProperties.getS3().getDiretorioFotos() + "/" + nomeArquivo;
	}

}
