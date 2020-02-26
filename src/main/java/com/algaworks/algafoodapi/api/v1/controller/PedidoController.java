package com.algaworks.algafoodapi.api.v1.controller;

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

import com.algaworks.algafoodapi.api.v1.model.PedidoFactory;
import com.algaworks.algafoodapi.api.v1.model.PedidoModel;
import com.algaworks.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algaworks.algafoodapi.api.v1.model.input.PedidoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafoodapi.core.data.PageWrapper;
import com.algaworks.algafoodapi.core.data.PageableTranslator;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.filter.PedidoFilter;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import com.algaworks.algafoodapi.domain.service.PedidoService;
import com.algaworks.algafoodapi.infrastructure.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	private final PedidoRepository pedidoRepository;
	private final PedidoService pedidoService;
	private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	private final PedidoFactory pedidoFactory;
	private final AlgaSecurity algaSecurity;

	@Autowired
	public PedidoController(PedidoRepository pedidoRepository, PedidoService pedidoService,
			PagedResourcesAssembler<Pedido> pagedResourcesAssembler,
			PedidoFactory pedidoFactory, AlgaSecurity algaSecurity) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoService = pedidoService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.pedidoFactory = pedidoFactory;
		this.algaSecurity = algaSecurity;
	}
	
	@CheckSecurity.Pedidos.PodePesquisar
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size = 10) Pageable pageable) {
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
		
		return pagedResourcesAssembler.toModel(new PageWrapper<Pedido>(pedidosPage, pageable), 
				PedidoResumoModel::criarPedidoResumoModelComLinks);
	}

	@CheckSecurity.Pedidos.PodeBuscar
	@Override
	@GetMapping("{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		return PedidoModel.criarPedidoModelComLinks(pedidoService.buscarOuFalhar(codigoPedido), algaSecurity);
	}
	
	@CheckSecurity.Pedidos.PodeCriar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			Pedido pedido = pedidoFactory.novoPedido(pedidoInput);
			
			return PedidoModel.criarPedidoModelComLinks(pedidoService.adicionarPedido(pedido), algaSecurity);
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
