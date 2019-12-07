package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepositoryTests;

@RestController
@RequestMapping("restaurantes-teste")
public class TesteController {

	private RestauranteRepositoryTests restauranteRepositoryTests; 

	@Autowired
	public TesteController(RestauranteRepositoryTests restauranteRepositoryTests) {
		this.restauranteRepositoryTests = restauranteRepositoryTests;
	}
	
	@GetMapping("/lista-pelo-nome")
	List<Restaurante> listaPeloNome(@RequestParam String nome) {
		return restauranteRepositoryTests.nome(nome);
	}
	
	@GetMapping("/lista-find-by-nome")
	List<Restaurante> listaFindByNome(@RequestParam String nome) {
		return restauranteRepositoryTests.findByNome(nome);
	}
	
	@GetMapping("/lista-busca-pelo-nome")
	List<Restaurante> listaBuscaPeloNome(@RequestParam String nome) {
		return restauranteRepositoryTests.buscarPeloNome(nome);
	}

	@GetMapping("/lista-top2-pelo-nome")
	List<Restaurante> listaTop2ByNome(@RequestParam String nome) {
		return restauranteRepositoryTests.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/count-cozinha-id")
	int countCozinhaId(@RequestParam Long idCozinha) {
		return restauranteRepositoryTests.countByCozinhaId(idCozinha);
	}

	@GetMapping("/lista-busca-pelo-nome-xml")
	List<Restaurante> listaBuscaPeloNomeXml(@RequestParam String nome) {
		return restauranteRepositoryTests.buscarPeloNomeXml(nome);
	}
	
}
