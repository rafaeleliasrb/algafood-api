package com.algaworks.algafoodapi.api.v1.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.v1.model.input.PedidoInput;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.model.ItemPedido;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.FormaPagamentoService;
import com.algaworks.algafoodapi.domain.service.ProdutoService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.algaworks.algafoodapi.domain.service.UsuarioService;

@Component
public class PedidoFactory {

	private final RestauranteService restauranteService; 
	private final FormaPagamentoService formaPagamentoService;
	private final CidadeService cidadeService;
	private final ProdutoService produtoService;
	private final UsuarioService usuarioService;
	
	@Autowired
	public PedidoFactory(RestauranteService restauranteService, FormaPagamentoService formaPagamentoService,
			CidadeService cidadeService, ProdutoService produtoService, UsuarioService usuarioService) {
		this.restauranteService = restauranteService;
		this.formaPagamentoService = formaPagamentoService;
		this.cidadeService = cidadeService;
		this.produtoService = produtoService;
		this.usuarioService = usuarioService;
	}

	public Pedido novoPedido(PedidoInput pedidoInput) {
		Restaurante restauranteExistente = restauranteService.buscarOuFalha(pedidoInput.getRestaurante().getId());
		FormaPagamento formaPagamentoExistente = 
				formaPagamentoService.buscarOuFalhar(pedidoInput.getFormaPagamento().getId());
		
		Usuario cliente = usuarioService.buscarOuFalhar(AlgaSecurity.getUsuarioId());
		
		List<ItemPedido> itensPedido = pedidoInput.getItens().stream()
				.map(itemPedidoInput -> itemPedidoInput.itemPedido(restauranteExistente, produtoService))
				.collect(Collectors.toList());
		
		return new Pedido(pedidoInput.getEnderecoEntrega().novoEndereco(cidadeService), formaPagamentoExistente, 
				restauranteExistente, cliente, itensPedido);
	}
}
