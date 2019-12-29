package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.PedidoModel;
import com.algaworks.algafoodapi.api.model.PedidoResumoModel;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import com.algaworks.algafoodapi.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;
	private final PedidoService pedidoService;
	private final RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler;

	@Autowired
	public PedidoController(PedidoRepository pedidoRepository, PedidoService pedidoService,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoService = pedidoService;
		this.representationModelAssemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<PedidoResumoModel> listar() {
		return representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(PedidoResumoModel.class, pedidoRepository.findAll());
	}
	
	@GetMapping("{idPedido}")
	PedidoModel buscar(@PathVariable Long idPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(idPedido);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(PedidoModel.class, pedido);
	}
}
