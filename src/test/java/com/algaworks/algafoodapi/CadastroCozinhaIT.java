package com.algaworks.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.service.CozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Test
	public void deveAtribuirId_QaundoCadastrarCozinhaComDadosCorretos() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Peruana");
		
		cozinhaService.salvar(novaCozinha);
	
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome(null);
			
			cozinhaService.salvar(novaCozinha);
		});
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEUso() {
		Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cozinhaService.remover(1L);
		});
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cozinhaService.remover(15L);
		});
	}
	
}
