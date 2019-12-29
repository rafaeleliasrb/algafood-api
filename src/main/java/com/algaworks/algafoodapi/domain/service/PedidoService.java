package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	private PedidoRepository pedidoRepository;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	public Pedido buscarOuFalhar(Long id) {
		return pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException(id));
	}
}
