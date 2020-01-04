package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.core.validation.Offset;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatisticas")
@Validated
public class EstatisticasController {

	private VendaQueryService vendaQueryService;

	@Autowired
	public EstatisticasController(VendaQueryService vendaQueryService) {
		this.vendaQueryService = vendaQueryService;
	}
	
	@GetMapping("vendas-diarias")
	List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") @Offset String offset) {
		return vendaQueryService.consultarVendasDiarias(filter, offset);
	}
}
