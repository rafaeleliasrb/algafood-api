package com.algaworks.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.FotoProduto;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import com.algaworks.algafoodapi.domain.service.FotoStorageService.NovaFoto;

@Service
public class FotoProdutoService {

	private final ProdutoRepository produtoRepository;
	private final FotoStorageService fotoStorageService;
	private final ProdutoService produtoService;
	private final RestauranteService restauranteService;
	
	@Autowired
	public FotoProdutoService(ProdutoRepository produtoRepository, FotoStorageService fotoStorageService,
			ProdutoService produtoService, RestauranteService restauranteService){
		this.produtoRepository = produtoRepository;
		this.fotoStorageService = fotoStorageService;
		this.produtoService = produtoService;
		this.restauranteService = restauranteService;
	}

	@Transactional
	public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {
		Restaurante restaurante = fotoProduto.getRestaurante();
		Long produtoId = fotoProduto.getProduto().getId();
		var fotoExistente = produtoRepository.findByProdutoIdAndRestaurante(produtoId, restaurante);
		
		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
		Optional<String> nomeFotoExistente = Optional.empty();
		
		if(fotoExistente.isPresent()) {
			nomeFotoExistente = Optional.of(fotoExistente.get().getNomeArquivo());
			produtoRepository.delete(fotoExistente.get());
		}
		
		fotoProduto.setNomeArquivo(nomeNovoArquivo);
		fotoProduto = produtoRepository.salvar(fotoProduto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.inputStream(inputStream)
				.contentType(fotoProduto.getContentType())
				.nomeArquivo(nomeNovoArquivo)
				.build();
		fotoStorageService.substituir(nomeFotoExistente, novaFoto);
		
		return fotoProduto;
	}
	
	public FotoProduto buscarOuFalhar(Long idRestaurante, Long idProduto) {
		Restaurante restaurante = restauranteService.buscarOuFalha(idRestaurante);
		Produto produto = produtoService.buscarOuFalhar(idProduto, restaurante);
		
		return produtoRepository.findByProdutoIdAndRestaurante(produto.getId(), restaurante)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(idRestaurante, idProduto));
	}
	
	@Transactional
	public void excluir(Long idRestaurante, Long idProduto) {
		FotoProduto fotoProdutoExistente = buscarOuFalhar(idRestaurante, idProduto);
		
		String nomeArquivo = fotoProdutoExistente.getNomeArquivo();
		
		produtoRepository.delete(fotoProdutoExistente);
		produtoRepository.flush();
		
		fotoStorageService.remover(nomeArquivo);
	}
}
