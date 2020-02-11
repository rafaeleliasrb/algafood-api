package com.algaworks.algafoodapi.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PagedModelOpenApi {

	@ApiModelProperty(example = "0", value = "Quantidade de registros por página")
	private Long size;
	
	@ApiModelProperty(example = "10", value = "Total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example = "10", value = "Total de páginas")
	private Long tetalPages;
	
	@ApiModelProperty(example = "10", value = "Número da página (começa em 0)")
	private Long number;
}
