package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.assembler.RepresentationModelAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.model.input.FormaPagamentoInput;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafoodapi.domain.service.FormaPagamentoService;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {

	private final FormaPagamentoService service;
	private final FormaPagamentoRepository repository;
	private final RepresentationModelAssemblerAndDisassembler assemblerAndDisassembler;
	
	@Autowired
	public FormaPagamentoController(FormaPagamentoService formaPagamentoService, FormaPagamentoRepository formaPagamentoRepository,
			RepresentationModelAssemblerAndDisassembler representationModelAssemblerAndDisassembler) {
		this.repository = formaPagamentoRepository;
		this.service = formaPagamentoService;
		this.assemblerAndDisassembler = representationModelAssemblerAndDisassembler;
	}

	@GetMapping
	ResponseEntity<List<FormaPagamentoModel>> listar() {
		//desabilita o shallow ETag para conseguir fazer o Deep ETag
		/*ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		Optional<OffsetDateTime> dataAtualizacaoMaisRecente = repository.findDataAtualizacaoMaisRecente();

		String eTag = "0";
		if(dataAtualizacaoMaisRecente.isPresent()) {
			eTag = String.valueOf(dataAtualizacaoMaisRecente.get().toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}*/
		
		
		List<FormaPagamentoModel> formasPagamentoModel = assemblerAndDisassembler
				.toCollectionRepresentationModel(FormaPagamentoModel.class, repository.findAll());
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//				.cacheControl(CacheControl.noCache())
//				.cacheControl(CacheControl.noStore())
				.body(formasPagamentoModel);
	}
	
	@GetMapping("/{idFormaPagamento}")
	ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long idFormaPagamento, ServletWebRequest request) {
		//desabilita o shallow ETag para conseguir fazer o Deep ETag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		OffsetDateTime dataAtualizacao = repository.findDataAtualizacaoById(idFormaPagamento);
		
		String eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoModel formaPagamentoModel = assemblerAndDisassembler
				.toRepresentationModel(FormaPagamentoModel.class, service.buscarOuFalhar(idFormaPagamento));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@PostMapping
	ResponseEntity<FormaPagamentoModel> adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = assemblerAndDisassembler
				.toRepresentationModel(FormaPagamento.class, formaPagamentoInput);
		
		FormaPagamentoModel formaPagamentoNova = assemblerAndDisassembler
				.toRepresentationModel(FormaPagamentoModel.class, service.salvar(formaPagamento));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(formaPagamentoNova.getId()).toUri();
		
		return ResponseEntity.created(uri).body(formaPagamentoNova);
	}
	
	@PutMapping("/{idFormaPagamento}")
	FormaPagamentoModel atualizar(@PathVariable Long idFormaPagamento, 
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = service.buscarOuFalhar(idFormaPagamento);
		
		assemblerAndDisassembler.copyProperties(formaPagamentoInput, formaPagamentoAtual);
		
		return assemblerAndDisassembler.toRepresentationModel(FormaPagamentoModel.class, 
				service.salvar(formaPagamentoAtual));
	}
	
	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void remover(@PathVariable Long idFormaPagamento) {
		service.remover(idFormaPagamento);
	}
}
