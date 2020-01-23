package com.algaworks.algafoodapi.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageModelOpenApi<T> {

	private List<T> content;
	
	@ApiModelProperty(example = "0", value = "Quantidade de registros por página")
	private Long size;
	
	@ApiModelProperty(example = "10", value = "Total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example = "10", value = "Total de páginas")
	private Long tetalPages;
	
	@ApiModelProperty(example = "10", value = "Número da página (começa em 0)")
	private Long number;
}
