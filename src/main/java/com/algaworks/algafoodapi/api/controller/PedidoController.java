package com.algaworks.algafoodapi.api.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.model.PedidoFactory;
import com.algaworks.algafoodapi.api.model.PedidoModel;
import com.algaworks.algafoodapi.api.model.PedidoResumoModel;
import com.algaworks.algafoodapi.api.model.input.PedidoInput;
import com.algaworks.algafoodapi.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafoodapi.core.data.PageableTranslator;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.filter.PedidoFilter;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import com.algaworks.algafoodapi.domain.service.PedidoService;
import com.algaworks.algafoodapi.infrastructure.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	private final PedidoRepository pedidoRepository;
	private final PedidoService pedidoService;
	private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	private final PedidoFactory pedidoFactory;

	@Autowired
	public PedidoController(PedidoRepository pedidoRepository, PedidoService pedidoService,
			PagedResourcesAssembler<Pedido> pagedResourcesAssembler,
			PedidoFactory pedidoFactory) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoService = pedidoService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.pedidoFactory = pedidoFactory;
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
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size = 10) Pageable pageable) {
		pageable = traduzirPageable(pageable);
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
		
		return pagedResourcesAssembler.toModel(pedidos, PedidoResumoModel::criarPedidoResumoModelComLinks);
	}

	@GetMapping("{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		return PedidoModel.criarPedidoModelComLinks(pedidoService.buscarOuFalhar(codigoPedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			Pedido pedido = pedidoFactory.novoPedido(pedidoInput);
			
			return PedidoModel.criarPedidoModelComLinks(pedidoService.adicionarPedido(pedido));
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
