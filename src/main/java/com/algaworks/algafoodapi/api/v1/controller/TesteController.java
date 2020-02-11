package com.algaworks.algafoodapi.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.model.RestauranteTestBeanValidation;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepositoryTests;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/v1/restaurantes-teste")
public class TesteController {

	private RestauranteRepositoryTests restauranteRepositoryTests;

	@Autowired
	public TesteController(RestauranteRepositoryTests restauranteRepositoryTests) {
		this.restauranteRepositoryTests = restauranteRepositoryTests;
	}
	
	@GetMapping("/lista-pelo-nome")
	public List<Restaurante> listaPeloNome(@RequestParam String nome) {
		return restauranteRepositoryTests.nome(nome);
	}
	
	@GetMapping("/lista-find-by-nome")
	public List<Restaurante> listaFindByNome(@RequestParam String nome) {
		return restauranteRepositoryTests.findByNome(nome);
	}
	
	@GetMapping("/lista-busca-pelo-nome")
	public List<Restaurante> listaBuscaPeloNome(@RequestParam String nome) {
		return restauranteRepositoryTests.buscarPeloNome(nome);
	}

	@GetMapping("/lista-top2-pelo-nome")
	public List<Restaurante> listaTop2ByNome(@RequestParam String nome) {
		return restauranteRepositoryTests.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/count-cozinha-id")
	public int countCozinhaId(@RequestParam Long idCozinha) {
		return restauranteRepositoryTests.countByCozinhaId(idCozinha);
	}

	@GetMapping("/lista-busca-pelo-nome-xml")
	public List<Restaurante> listaBuscaPeloNomeXml(@RequestParam String nome) {
		return restauranteRepositoryTests.buscarPeloNomeXml(nome);
	}
	
	@PostMapping
	public void adicionar(@RequestBody @Valid RestauranteTestBeanValidation restauranteTeste) {
		System.out.println(restauranteTeste);
	}
}
