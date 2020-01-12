package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	private final PedidoService pedidoService;
	private final PedidoRepository pedidoRepository;
	
	@Autowired
	public FluxoPedidoService(PedidoService pedidoService, PedidoRepository pedidoRepository) {
		this.pedidoService = pedidoService;
		this.pedidoRepository = pedidoRepository;
	}

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();		
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	}
}
