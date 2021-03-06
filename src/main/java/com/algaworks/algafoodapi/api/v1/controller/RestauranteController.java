package com.algaworks.algafoodapi.api.v1.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafoodapi.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafoodapi.api.v1.model.RestauranteModel;
import com.algaworks.algafoodapi.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafoodapi.api.v1.model.input.RestauranteInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafoodapi.domain.exception.ValidacaoException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.CozinhaService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	private final RestauranteRepository restauranteRepository;
	private final RestauranteService restauranteService;
	private final MergeadorDeRecurso mergeadorDeRecurso;
	private final SmartValidator smartValidator;
	private final CozinhaService cozinhaService;
	private final CidadeService cidadeService;
	private final AlgaSecurity algaSecurity;
	
	@Autowired
	public RestauranteController(RestauranteRepository restauranteRepository, RestauranteService restauranteService, 
			MergeadorDeRecurso mergeadorDeRecurso, SmartValidator smartValidator, 
			CozinhaService cozinhaService, CidadeService cidadeService, AlgaSecurity algaSecurity) {
		this.restauranteRepository = restauranteRepository;
		this.restauranteService = restauranteService;
		this.mergeadorDeRecurso = mergeadorDeRecurso;
		this.smartValidator = smartValidator;
		this.cozinhaService = cozinhaService;
		this.cidadeService = cidadeService;
		this.algaSecurity = algaSecurity;
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<RestauranteResumoModel> listar() {
		return RestauranteResumoModel.criarCollectorRestauranteResumoModelComLinks(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
		return RestauranteApenasNomeModel.criarCollectorRestauranteApenasNomeModelComLinks(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(value = "/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		return RestauranteModel.criarRestauranteModelComLinks(restauranteService.buscarOuFalha(id), algaSecurity);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = restauranteInput.novoRestaurante(cozinhaService, cidadeService);
		
		return RestauranteModel.criarRestauranteModelComLinks(restauranteService.salvar(restaurante), algaSecurity); 
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@PutMapping(value = "{idRestaurante}")
	public RestauranteModel atualizar(@PathVariable Long idRestaurante, @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = restauranteInput
				.restauranteAtualizado(idRestaurante, restauranteService, cozinhaService, cidadeService);
		
		return RestauranteModel.criarRestauranteModelComLinks(restauranteService.salvar(restaurante), algaSecurity);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		restauranteService.remover(id);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@PatchMapping("/{id}")
	public RestauranteModel atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> camposOrigem,
			HttpServletRequest request) {
		RestauranteInput restauranteAtual = new RestauranteInput(restauranteService.buscarOuFalha(id));
		mergeadorDeRecurso.mergeCampos(RestauranteInput.class, camposOrigem, restauranteAtual, request);
		validadarCampos(restauranteAtual, "restauranteInput");
		
		return atualizar(id, restauranteAtual);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@PutMapping(value = "{idRestaurante}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long idRestaurante) {
		restauranteService.ativar(idRestaurante);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@DeleteMapping(value = "{idRestaurante}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long idRestaurante) {
		restauranteService.inativar(idRestaurante);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesFuncionais
	@Override
	@PutMapping("/{idRestaurante}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long idRestaurante) {
		restauranteService.abrir(idRestaurante);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesFuncionais
	@Override
	@PutMapping("/{idRestaurante}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long idRestaurante) {
		restauranteService.fechar(idRestaurante);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarMutiplos(@RequestBody List<Long> idsRestaurante) {
		try {
			restauranteService.ativar(idsRestaurante);
			
			return ResponseEntity.noContent().build();
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarInformacoesCadastrais
	@Override
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desativarMutiplos(@RequestBody List<Long> idsRestaurante) {
		try {
			restauranteService.desativar(idsRestaurante);
			
			return ResponseEntity.noContent().build();
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private void validadarCampos(RestauranteInput restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		smartValidator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	/* Endpoints extras */
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/buscar")
	public List<RestauranteModel> buscar(@RequestParam("nome") String nome, @RequestParam BigDecimal taxaFreteInicial, 
			@RequestParam BigDecimal taxaFreteFinal) {
		return restauranteRepository.buscar(nome, taxaFreteInicial, taxaFreteFinal).stream()
				.map(restaurante -> RestauranteModel.criarRestauranteModelComLinks(restaurante, algaSecurity))
				.collect(Collectors.toList());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/buscar-com-criteria")
	public List<RestauranteModel> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.buscarComCriteria(nome, taxaFreteInicial, taxaFreteFinal).stream()
				.map(restaurante -> RestauranteModel.criarRestauranteModelComLinks(restaurante, algaSecurity))
				.collect(Collectors.toList());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/com-frete-gratis")
	public List<RestauranteModel> buscarComFreteGratis(String nome) {
		return restauranteRepository.buscarComFreteGratis(nome).stream()
				.map(restaurante -> RestauranteModel.criarRestauranteModelComLinks(restaurante, algaSecurity))
				.collect(Collectors.toList());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/buscar-primeiro")
	public Optional<RestauranteModel> buscarPrimeiro() {
		Optional<Restaurante> primeiroRestaurante = restauranteRepository.buscarPrimeiro();
		if(primeiroRestaurante.isPresent()) {
			return Optional.of(RestauranteModel.criarRestauranteModelComLinks(primeiroRestaurante.get(), algaSecurity));
		}
		return Optional.empty();
	}
}
