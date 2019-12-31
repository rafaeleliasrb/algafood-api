package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	private PedidoService pedidoService;
	
	@Autowired
	public FluxoPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
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
	}
}
