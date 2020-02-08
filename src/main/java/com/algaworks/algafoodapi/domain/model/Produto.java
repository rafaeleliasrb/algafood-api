package com.algaworks.algafoodapi.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;
	
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

	@Deprecated
	public Produto() {}
	
	public Produto(String nome, String descricao, BigDecimal preco, Boolean ativo) {
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.ativo = ativo;
	}
}
