package com.algaworks.algafoodapi.api.model.input;

import com.algaworks.algafoodapi.core.validation.Offset;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OffsetInput {

	@ApiParam(example = "+00:00", value = "Deslocamento de fuso horário a ser considerado na consulta")
	@Offset
	String offset = "+00:00";
}
