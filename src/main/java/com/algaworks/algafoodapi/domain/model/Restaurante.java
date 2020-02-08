package com.algaworks.algafoodapi.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafoodapi.domain.exception.FormaPagamentoDeUmRestauranteNaoEncontradaException;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@Embedded
	private Endereco endereco;
	
	@Setter(AccessLevel.NONE)
	private Boolean ativo = Boolean.TRUE;
	
	@Setter(AccessLevel.NONE)
	private Boolean aberto = Boolean.FALSE;
	
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	@Deprecated
	public Restaurante() {}
	
	public Restaurante(String nome, BigDecimal taxaFrete, Endereco endereco, Cozinha cozinha) {
		this.nome = nome;
		this.taxaFrete = taxaFrete;
		this.endereco = endereco;
		this.cozinha = cozinha;
	}
	
	public void ativar() {
		this.ativo = Boolean.TRUE;
	}
	
	public void inativar() {
		this.ativo = Boolean.FALSE;
	}

	public void abrir() {
		this.aberto = Boolean.TRUE;
	}
	
	public void fechar() {
		this.aberto = Boolean.FALSE;
	}
	
	public boolean permiteAtivar() {
		return !this.ativo;
	}
	
	public boolean permiteInativar() {
		return this.ativo;
	}
	
	public boolean permiteAbrir() {
		return this.ativo && !this.aberto;
	}
	
	public boolean permiteFechar() {
		return this.aberto;
	}
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean associarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}

	public boolean adicionarResponsavel(Usuario responsavel) {
		return getResponsaveis().add(responsavel);
	}

	public boolean removerResponsavel(Usuario responsavel) {
		return getResponsaveis().remove(responsavel);
	}

	public void validarFormaPagamentoAceita(FormaPagamento formaPagamento) {
		if(!getFormasPagamento().contains(formaPagamento)) {
			throw new FormaPagamentoDeUmRestauranteNaoEncontradaException(formaPagamento.getId(), getId());
		}
	}
}
