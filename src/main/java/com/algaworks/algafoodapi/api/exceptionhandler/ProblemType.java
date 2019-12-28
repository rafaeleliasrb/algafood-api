package com.algaworks.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ASSOCIACAO_NAO_ENCONTRADA("/associacao-nao-encontrada", "Associação não encontrada"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_NEGOCIO("/erro-negocio", "Violação regra de negócio"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro interno do sistema");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}
}
