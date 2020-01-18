set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

INSERT INTO cozinha (id, nome) VALUES (1, "Brasileira");
INSERT INTO cozinha (id, nome) VALUES (2, "Indiana");
INSERT INTO cozinha (id, nome) VALUES (3, "Árabe");

INSERT INTO estado (id, nome) VALUES (1, "Ceará");
INSERT INTO estado (id, nome) VALUES (2, "São Paulo");

INSERT INTO cidade (id, nome, estado_id) VALUES (1, "Fortaleza", 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (2, "Aracati", 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (3, "São Paulo", 2);
INSERT INTO cidade (id, nome, estado_id) VALUES (4, "Santos", 2);

INSERT INTO forma_pagamento (id, descricao, data_atualizacao) VALUES (1, "Cartão de crédito", utc_timestamp);
INSERT INTO forma_pagamento (id, descricao, data_atualizacao) VALUES (2, "Cartão de débito", utc_timestamp);
INSERT INTO forma_pagamento (id, descricao, data_atualizacao) VALUES (3, "Dinheiro", utc_timestamp);

INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) VALUES (1, "Pé de fava", 10.50, 2, utc_timestamp, utc_timestamp, true, false, 1, "38400-999", "Rua João Pinheiro", "1000", "Centro");
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (2, "Era uma vez Chalezinho", 50.70, 1, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (3, "Coco Bambu", 20.50, 1, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (4, "Indiano", 2.70, 2, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (5, "Habbibs", 7.00, 3, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (6, "Esfiharia", 0.0, 3, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (7, "Baião de dois", 0.0, 1, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (8, "Cai duro", 0.0, 1, utc_timestamp, utc_timestamp, true, false);

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (3, 2), (3, 3), (4, 2), (5, 3), (6, 1), (6, 3), (7, 3), (8, 2), (8, 1);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ("Porco com molho agridoce", "Deliciosa carne suína ao molho especial", 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Camarão tailandês", "16 camarões grandes ao molho picante", 110, 0, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Salada picante com carne grelhada", "Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha", 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Garlic Naan", "Pão tradicional indiano com cobertura de alho", 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Murg Curry", "Cubos de frango preparados com molho curry e especiarias", 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Bife Ancho", "Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé", 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("T-Bone", "Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon", 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Sanduíche X-Tudo", "Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese", 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Espetinho de Cupim", "Acompanha farinha, mandioca e vinagrete", 8, 1, 6);

insert into permissao (id, nome, descricao) values (1, "CONSULTAR_COZINHAS", "Permite consultar cozinhas");
insert into permissao (id, nome, descricao) values (2, "EDITAR_COZINHAS", "Permite editar cozinhas");
insert into permissao (id, nome, descricao) values (3, "CONSULTAR_RESTAURANTES", "Permite consultar restaurantes");
insert into permissao (id, nome, descricao) values (4, "EDITAR_RESTAURANTES", "Permite editar restaurantes");
insert into permissao (id, nome, descricao) values (5, "CONSULTAR_CIDADES", "Permite consultar cidades");
insert into permissao (id, nome, descricao) values (6, "EDITAR_CIDADES", "Permite editar cidades");
insert into permissao (id, nome, descricao) values (7, "CONSULTAR_ESTADOS", "Permite consultar estados");
insert into permissao (id, nome, descricao) values (8, "EDITAR_ESTADOS", "Permite editar estados");

insert into grupo (id, nome) values (1, "Gerente");
insert into grupo (id, nome) values (2, "Vendedor");
insert into grupo (id, nome) values (3, "Secretária");
insert into grupo (id, nome) values (4, "Cadastrador");

insert into usuario (id, nome, email, senha, data_cadastro) values (1, "João", "rafael_elias007@hotmail.com", "12345", utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2, "Maria", "maria@email.com", "12345", utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (3, "Raimunda", "raimunda@email.com", "12345", utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (4, "Teresa", "teresa@email.com", "12345", utc_timestamp);

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), 
(2, 1), (2, 3), (2, 5), (2, 7), (3, 1), (3, 3), (3, 5), (3, 7), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (2, 3), (3, 2), (3, 4), (4, 2), (4, 4);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 1), (1, 2), (2, 3), (3, 4), (4, 3), (4, 4);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, "f229e133-2c0a-11ea-9866-d09466a32949", 1, 1, 1, 1, "38400-000", "Rua Floriano Peixoto", "500", "Apto 801", "Brasil",
        "CRIADO", utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, "Menos picante, por favor");

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, "1e458599-2c0b-11ea-9866-d09466a32949", 4, 1, 2, 1, "38400-111", "Rua Acre", "300", "Casa 2", "Centro",
        "CRIADO", utc_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, "Ao ponto");

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (3, "b5741512-8fbc-47fa-9ac1-b530354fc0ff", 1, 1, 1, 1, "38400-222", "Rua Natal", "200", null, "Brasil",
        "ENTREGUE", "2019-10-30 21:10:00", "2019-10-30 21:10:45", "2019-10-30 21:55:44", 110, 10, 120);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (4, "5c621c9a-ba61-4454-8631-8aabefe58dc2", 1, 2, 1, 1, "38400-800", "Rua Fortaleza", "900", "Apto 504", "Centro",
        "ENTREGUE", "2019-11-02 20:34:04", "2019-11-02 20:35:10", "2019-11-02 21:10:32", 174.4, 5, 179.4);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 87.2, 174.4, null);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (5, "8d774bcf-b238-42f3-aef1-5fb388754d63", 1, 3, 2, 1, "38400-200", "Rua 10", "930", "Casa 20", "Martins",
        "ENTREGUE", "2019-11-02 21:00:30", "2019-11-02 21:01:21", "2019-11-02 21:20:10", 87.2, 10, 97.2);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 87.2, 87.2, null);
