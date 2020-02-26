package com.algaworks.algafoodapi.api.v1.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.v1.model.TemplateVariableEnum;
import com.algaworks.algafoodapi.api.v1.model.input.OffsetInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;
import com.algaworks.algafoodapi.domain.service.VendaReportService;

@RestController
@RequestMapping("/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

	private final VendaQueryService vendaQueryService;
	private final VendaReportService vendaReportService;

	@Autowired
	public EstatisticasController(VendaQueryService vendaQueryService, VendaReportService vendaReportService) {
		this.vendaQueryService = vendaQueryService;
		this.vendaReportService = vendaReportService;
	}
	
	@GetMapping
	public EstatisticaModel estatisticas() {
		EstatisticaModel estatisticaModel = new EstatisticaModel();
		
		String estatisticaUrl = linkTo(methodOn(EstatisticasController.class)
				.pesquisarVendasDiarias(null, null)).toUri().toString();
		estatisticaModel.add(new Link(UriTemplate.of(estatisticaUrl, TemplateVariableEnum.estatisticasVariables()), 
				"vendas-diarias"));
		
		return estatisticaModel;
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@Override
	@GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter, 
			@Valid OffsetInput offsetInput) {
		return vendaQueryService.consultarVendasDiarias(filter, offsetInput.getOffset());
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@Override
	@GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> pesquisarVendasDiariasPdf(VendaDiariaFilter filter, 
			@Valid OffsetInput offsetInput) {
		byte[] report = vendaReportService.emitirVendasDiarias(filter, offsetInput.getOffset());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(report);
	}
	
	private static class EstatisticaModel extends RepresentationModel<EstatisticaModel> {}
}
