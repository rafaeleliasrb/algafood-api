package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.PedidoModel;
import com.algaworks.algafoodapi.api.model.PedidoResumoModel;
import com.algaworks.algafoodapi.api.model.input.PedidoInput;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
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
	
	@GetMapping("{codigoPedido}")
	PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		
		return representationModelAssemblerAndDisassembler
				.toRepresentationModel(PedidoModel.class, pedido);
	}
	
	@PostMapping
	ResponseEntity<PedidoModel> adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			Pedido pedido = representationModelAssemblerAndDisassembler
					.toRepresentationModel(Pedido.class, pedidoInput);
			
			PedidoModel novoPedido = representationModelAssemblerAndDisassembler
					.toRepresentationModel(PedidoModel.class, pedidoService.adicionarPedido(pedido));
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{codigoPedido}").buildAndExpand(novoPedido.getCodigo()).toUri();
			
			return ResponseEntity.created(uri).body(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
