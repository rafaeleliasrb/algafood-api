package com.algaworks.algafoodapi.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafoodapi.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafoodapi.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafoodapi.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	@PrePersist
	private void inserirCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

	@Deprecated
	public Pedido() {}
	
	public Pedido(Endereco enderecoEntrega, FormaPagamento formaPagamento,
			Restaurante restaurante, Usuario cliente, List<ItemPedido> itens) {
		this.enderecoEntrega = enderecoEntrega;
		this.formaPagamento = formaPagamento;
		this.restaurante = restaurante;
		this.cliente = cliente;
		this.itens = itens;
		
		itens.stream().forEach(item -> item.setPedido(this));
		
		this.taxaFrete = restaurante.getTaxaFrete();
		calcularValorTotal();
	}
	
	private void calcularValorTotal() {
		//Outra versao do reduce
		/* BigDecimal valorTotalDosItens = getItens().stream()
				.reduce(BigDecimal.ZERO, 
				(valorParcial, y) -> valorParcial.add(y.getPrecoTotal()), BigDecimal::add); */
		BigDecimal valorTotalDosItens = getItens().stream()
			.map(ItemPedido::getPrecoTotal)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.subtotal = valorTotalDosItens;
		this.valorTotal = valorTotalDosItens.add(getRestaurante().getTaxaFrete());
	}

	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		
		registerEvent(new PedidoConfirmadoEvent(this));
	}

	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}

	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	private void setStatus(StatusPedido novoStatusPedido) {
		if(novoStatusPedido.isNaoPermiteAlterarStatusPara(getStatus())) {
			throw new NegocioException(String.format("Status do pedido de código %s não pode ser alterado de %s para %s", 
					this.codigo, this.status.getDescricao(), novoStatusPedido.getDescricao()));
		}
		this.status = novoStatusPedido;
	}
}
