package com.algaworks.algafoodapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;
	
	@Column(nullable = false)
	private String nomeArquivo;
	
	private String descricao;
	
	@Column(nullable = false)
	private String contentType;
	
	@Column(nullable = false)
	private Long tamanho;

	public Restaurante getRestaurante() {
		if(getProduto() != null)
			return getProduto().getRestaurante();
		
		return null;
	}
}
