package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

@RestController
@RequestMapping(value = "estados")
public class EstadoController {

	private EstadoRepository estadoRepository;

	@Autowired
	public EstadoController(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.listarTodos();
	}
}
