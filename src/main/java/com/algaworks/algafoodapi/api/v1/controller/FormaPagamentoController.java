package com.algaworks.algafoodapi.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafoodapi.domain.service.FormaPagamentoService;

@RestController
@RequestMapping(value = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	private final FormaPagamentoService formaPagamentoService;
	private final FormaPagamentoRepository repository;
	
	@Autowired
	public FormaPagamentoController(FormaPagamentoService formaPagamentoService, 
			FormaPagamentoRepository formaPagamentoRepository) {
		this.repository = formaPagamentoRepository;
		this.formaPagamentoService = formaPagamentoService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar() {
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = 
				FormaPagamentoModel.criarCollectorFormaPagamentoModelComLink(repository.findAll());
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//				.cacheControl(CacheControl.noCache())
//				.cacheControl(CacheControl.noStore())
				.body(formasPagamentoModel);
	}
	
	@GetMapping("/{idFormaPagamento}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long idFormaPagamento, ServletWebRequest request) {
		//desabilita o shallow ETag para conseguir fazer o Deep ETag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		OffsetDateTime dataAtualizacao = repository.findDataAtualizacaoById(idFormaPagamento);
		
		String eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoModel formaPagamentoModel = FormaPagamentoModel
				.criarFormaPagamentoModelComLinks(formaPagamentoService.buscarOuFalhar(idFormaPagamento));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@PostMapping
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInput.novaFormaPagamento();
		
		return FormaPagamentoModel.criarFormaPagamentoModelComLinks(formaPagamentoService.salvar(formaPagamento));
	}
	
	@PutMapping("/{idFormaPagamento}")
	public FormaPagamentoModel atualizar(@PathVariable Long idFormaPagamento, 
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		
		FormaPagamento formaPagamento = 
				formaPagamentoInput.formaPagamentoAtualizada(idFormaPagamento, formaPagamentoService);
		
		return FormaPagamentoModel.criarFormaPagamentoModelComLinks(formaPagamentoService.salvar(formaPagamento));
	}
	
	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idFormaPagamento) {
		formaPagamentoService.remover(idFormaPagamento);
	}
}
