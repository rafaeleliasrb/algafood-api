package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.algaworks.algafoodapi.core.data.PageableTranslator;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import com.algaworks.algafoodapi.domain.repository.filter.PedidoFilter;
import com.algaworks.algafoodapi.domain.service.PedidoService;
import com.algaworks.algafoodapi.infrastructure.spec.PedidoSpecs;

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
	
//	usando jsonFilter
//	@GetMapping
//	MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//		List<PedidoResumoModel> pedidosModel = representationModelAssemblerAndDisassembler
//				.toCollectionRepresentationModel(PedidoResumoModel.class, pedidoRepository.findAll());
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
	@GetMapping
	Page<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size = 10) Pageable pageable) {
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
		List<PedidoResumoModel> pedidosModel = representationModelAssemblerAndDisassembler
				.toCollectionRepresentationModel(PedidoResumoModel.class, pedidos.getContent());
		return new PageImpl<>(pedidosModel, pageable, pedidos.getTotalElements());
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
	
	private Pageable traduzirPageable(Pageable pageable) {
		Map<String, String> fieldsMapping = Map.of( 
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome");
		return PageableTranslator.translate(pageable, fieldsMapping);
	}
	
}
