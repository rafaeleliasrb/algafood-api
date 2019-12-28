package com.algaworks.algafoodapi.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafoodapi.api.assembler.RestauranteInputAssemblerAndDisassembler;
import com.algaworks.algafoodapi.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.api.model.input.RestauranteInput;
import com.algaworks.algafoodapi.domain.exception.ValidacaoException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private final RestauranteRepository restauranteRepository;
	private final RestauranteService restauranteService;
	private final MergeadorDeRecurso mergeadorDeRecurso;
	private final SmartValidator smartValidator;
	private final RestauranteModelAssembler restauranteModelAssembler;
	private final RestauranteInputAssemblerAndDisassembler restauranteInputAssemblerAndDisassembler;
	
	@Autowired
	public RestauranteController(RestauranteRepository restauranteRepository, RestauranteService restauranteService, 
			MergeadorDeRecurso mergeadorDeRecurso, SmartValidator smartValidator, 
			RestauranteModelAssembler restauranteModelAssembler, 
			RestauranteInputAssemblerAndDisassembler restauranteInputAssemblerAndDisassembler) {
		this.restauranteRepository = restauranteRepository;
		this.restauranteService = restauranteService;
		this.mergeadorDeRecurso = mergeadorDeRecurso;
		this.smartValidator = smartValidator;
		this.restauranteModelAssembler = restauranteModelAssembler;
		this.restauranteInputAssemblerAndDisassembler = restauranteInputAssemblerAndDisassembler;
	}
	
	@GetMapping
	List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping(value = "/{id}")
	RestauranteModel buscar(@PathVariable Long id) {
		return restauranteModelAssembler.toModel(restauranteService.buscarOuFalha(id));
	}
	
	@PostMapping
	ResponseEntity<Object> adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = restauranteInputAssemblerAndDisassembler.toDomainModel(restauranteInput);
		
		RestauranteModel restauranteNovo = restauranteModelAssembler
				.toModel(restauranteService.salvar(restaurante));
		
		URI restauranteUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(restauranteNovo.getId()).toUri();
		return ResponseEntity.created(restauranteUri).body(restauranteNovo);
	}
	
	@PutMapping(value = "{id}")
	RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = restauranteService.buscarOuFalha(id);
		
		if(restauranteAtual.getEndereco() != null) {
			restauranteAtual.getEndereco().setCidade(new Cidade());
		}
		
		restauranteInputAssemblerAndDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		return restauranteModelAssembler.toModel(restauranteService.salvar(restauranteAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void remover(@PathVariable Long id) {
		restauranteService.remover(id);
	}
	
	@PatchMapping("/{id}")
	RestauranteModel atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> camposOrigem,
			HttpServletRequest request) {
		RestauranteInput restauranteAtual = 
				restauranteInputAssemblerAndDisassembler.toInput(restauranteService.buscarOuFalha(id));
		mergeadorDeRecurso.mergeCampos(RestauranteInput.class, camposOrigem, restauranteAtual, request);
		validadarCampos(restauranteAtual, "restauranteInput");
		
		return atualizar(id, restauranteAtual);
	}
	
	@PutMapping(value = "{idRestaurante}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void ativar(@PathVariable Long idRestaurante) {
		restauranteService.ativar(idRestaurante);
	}
	
	@DeleteMapping(value = "{idRestaurante}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void inativar(@PathVariable Long idRestaurante) {
		restauranteService.inativar(idRestaurante);
	}
	
	@PutMapping("/{idRestaurante}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void abrir(@PathVariable Long idRestaurante) {
		restauranteService.abrir(idRestaurante);
	}
	
	@PutMapping("/{idRestaurante}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void fechar(@PathVariable Long idRestaurante) {
		restauranteService.fechar(idRestaurante);
	}
	
	private void validadarCampos(RestauranteInput restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		smartValidator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	@GetMapping("/buscar")
	List<RestauranteModel> buscar(@RequestParam("nome") String nome, @RequestParam BigDecimal taxaFreteInicial, 
			@RequestParam BigDecimal taxaFreteFinal) {
		return restauranteModelAssembler
				.toCollectionModel(restauranteRepository.buscar(nome, taxaFreteInicial, taxaFreteFinal));
	}
	
	@GetMapping("/buscar-com-criteria")
	List<RestauranteModel> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteModelAssembler
				.toCollectionModel(restauranteRepository.buscarComCriteria(nome, taxaFreteInicial, taxaFreteFinal));
	}
	
	@GetMapping("/com-frete-gratis")
	List<RestauranteModel> buscarComFreteGratis(String nome) {
		return restauranteModelAssembler
				.toCollectionModel(restauranteRepository.buscarComFreteGratis(nome));
	}
	
	@GetMapping("/buscar-primeiro")
	Optional<RestauranteModel> buscarPrimeiro() {
		return restauranteModelAssembler.toOptionalModel(restauranteRepository.buscarPrimeiro());
	}
}
