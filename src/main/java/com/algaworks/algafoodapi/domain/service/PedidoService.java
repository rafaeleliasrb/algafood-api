package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ProdutoService produtoService;
	private final RestauranteService restauranteService;
	private final FormaPagamentoService formaPagamentoService;
	private final UsuarioService usuarioService;
	private final CidadeService cidadeService;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService,
			RestauranteService restauranteService, FormaPagamentoService formaPagamentoService,
			UsuarioService usuarioService, CidadeService cidadeService) {
		this.pedidoRepository = pedidoRepository;
		this.produtoService = produtoService;
		this.restauranteService = restauranteService;
		this.formaPagamentoService = formaPagamentoService;
		this.usuarioService = usuarioService;
		this.cidadeService = cidadeService;
	}
	
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido adicionarPedido(Pedido pedido) {
			validarPedido(pedido);
			validarItensPedido(pedido);
	
			pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
			pedido.calcularValorTotal();
			
			return pedidoRepository.save(pedido);
	}

	private void validarItensPedido(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscarOuFalhar(item.getProduto().getId(), pedido.getRestaurante());
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}

	private void validarPedido(Pedido pedido) {
		Restaurante restaurante = restauranteService.buscarOuFalha(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService
				.buscarOuFalharDeUmRestaurante(pedido.getFormaPagamento().getId(), restaurante);
		//TODO temporariamente setando cliente 1
		Usuario cliente = usuarioService.buscarOuFalhar(1L);
		Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId()); 

		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.setCliente(cliente);
		pedido.getEnderecoEntrega().setCidade(cidade);
	}
}
