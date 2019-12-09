create table pedido (
    id bigint not null AUTO_INCREMENT,
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,
   
    data_criacao datetime not null,
    data_confirmacao datetime null,
    data_cancelamento datetime null,
    data_entrega datetime null,
    
    endereco_cidade_id bigint,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),
    
    status varchar(15) not null,
    
    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,
    
    PRIMARY KEY (id),
    
    CONSTRAINT fk_pedido_forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id),
    CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
    CONSTRAINT fk_pedido_usuario_cliente FOREIGN KEY (usuario_cliente_id) REFERENCES usuario (id)
) engine=InnoDB DEFAULT charset=utf8;


create table item_pedido (
	id bigint not null AUTO_INCREMENT,
    quantidade SMALLINT(6) not null,
    preco_unitario decimal(10, 2) not null,
    preco_total decimal(10, 2) not null,
    observacao varchar(255) null,
    produto_id bigint not null,
    pedido_id bigint not null,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_item_pedido_produto (pedido_id, produto_id),
    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id),
    CONSTRAINT fk_item_pedido_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
) ENGINE=InnoDB DEFAULT charset=utf8;
