package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.core.validation.Offset;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;
import com.algaworks.algafoodapi.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatisticas")
@Validated
public class EstatisticasController {

	private final VendaQueryService vendaQueryService;
	private final VendaReportService vendaReportService;

	@Autowired
	public EstatisticasController(VendaQueryService vendaQueryService, VendaReportService vendaReportService) {
		this.vendaQueryService = vendaQueryService;
		this.vendaReportService = vendaReportService;
	}
	
	@GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") @Offset String offset) {
		return vendaQueryService.consultarVendasDiarias(filter, offset);
	}
	
	@GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	ResponseEntity<byte[]> pesquisarVendasDiariasPdf(VendaDiariaFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") @Offset String offset) {
		byte[] report = vendaReportService.emitirVendasDiarias(filter, offset);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(report);
	}	
}
