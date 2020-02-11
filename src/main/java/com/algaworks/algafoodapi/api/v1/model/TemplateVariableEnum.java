package com.algaworks.algafoodapi.api.v1.model;

import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.TemplateVariable.VariableType;

public enum TemplateVariableEnum {

	PAGE(new TemplateVariable("page", VariableType.REQUEST_PARAM)),
	SIZE(new TemplateVariable("size", VariableType.REQUEST_PARAM)),
	SORT(new TemplateVariable("sort", VariableType.REQUEST_PARAM)),
	RESTAURANTE_ID(new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM)),
	CLIENTE_ID(new TemplateVariable("clienteId", VariableType.REQUEST_PARAM)),
	DATA_CRIACAO_INICIO(new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM)),
	DATA_CRIACAO_FIM(new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)),
	PROJECAO(new TemplateVariable("projecao", VariableType.REQUEST_PARAM)),
	OFFSET(new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
	
	private TemplateVariable templateVariable;
	
	private TemplateVariableEnum(TemplateVariable templateVariable) {
		this.templateVariable = templateVariable;
	}
	
	public static TemplateVariables pageVariables() {
		return new TemplateVariables(
				PAGE.getTemplateVariable(), SIZE.getTemplateVariable(), SORT.getTemplateVariable());
	}
	
	public static TemplateVariables filtroPedidoVariables() {
		return new TemplateVariables(RESTAURANTE_ID.getTemplateVariable(), CLIENTE_ID.getTemplateVariable(), 
				DATA_CRIACAO_INICIO.getTemplateVariable(), DATA_CRIACAO_FIM.getTemplateVariable());
	}
	
	public static TemplateVariables projecaoVariables() {
		return new TemplateVariables(PROJECAO.getTemplateVariable());
	}
	
	public static TemplateVariables estatisticasVariables() {
		return new TemplateVariables(RESTAURANTE_ID.getTemplateVariable(), DATA_CRIACAO_INICIO.getTemplateVariable(),
				DATA_CRIACAO_FIM.getTemplateVariable(), OFFSET.getTemplateVariable());
	}
	
	public TemplateVariable getTemplateVariable() {
		return templateVariable;
	}
}
