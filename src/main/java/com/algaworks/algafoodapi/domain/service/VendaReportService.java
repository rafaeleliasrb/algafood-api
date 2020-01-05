package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String offset);
}
