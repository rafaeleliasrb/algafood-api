package com.algaworks.algafoodapi.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	List<FormaPagamentoModel> listar() {
		return assemblerAndDisassembler
				.toCollectionRepresentationModel(FormaPagamentoModel.class, repository.findAll());
	}
	
	@GetMapping("/{idFormaPagamento}")
	FormaPagamentoModel buscar(@PathVariable Long idFormaPagamento) {
		return assemblerAndDisassembler
				.toRepresentationModel(FormaPagamentoModel.class, service.buscarOuFalhar(idFormaPagamento));
	}
	
	@PostMapping
	ResponseEntity<FormaPagamentoModel> adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = assemblerAndDisassembler
				.toRepresentationModel(FormaPagamento.class, formaPagamentoInput);
		
		FormaPagamentoModel formaPagamentoNova = assemblerAndDisassembler
				.toRepresentationModel(FormaPagamentoModel.class, service.salvar(formaPagamento));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{id}").buildAndExpand(formaPagamentoNova).toUri();
		
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
