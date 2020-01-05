package com.algaworks.algafoodapi.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;
import com.algaworks.algafoodapi.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {

	private final VendaQueryService vendaQueryService;
	
	@Autowired
	public PdfVendaReportService(VendaQueryService vendaReportService) {
		this.vendaQueryService = vendaReportService;
	}
	

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String offset) {
		try {
			var report = this.getClass().getResourceAsStream("/report/vendas-diarias.jasper");
			
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			var datasource = new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filter, offset));
			
			var jasperPrint = JasperFillManager.fillReport(report, parameters, datasource);
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir o relatório vendas diárias", e);
		}
	}

}
