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


INSERT INTO cozinha (id, nome) VALUES (1, "Brasileira");
INSERT INTO cozinha (id, nome) VALUES (2, "Indiana");
INSERT INTO cozinha (id, nome) VALUES (3, "Árabe");

INSERT INTO estado (id, nome) VALUES (1, "Ceará");
INSERT INTO estado (id, nome) VALUES (2, "São Paulo");

INSERT INTO cidade (id, nome, estado_id) VALUES (1, "Fortaleza", 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (2, "Aracati", 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (3, "São Paulo", 2);
INSERT INTO cidade (id, nome, estado_id) VALUES (4, "Santos", 2);

INSERT INTO forma_pagamento (id, descricao) VALUES (1, "Cartão de crédito");
INSERT INTO forma_pagamento (id, descricao) VALUES (2, "Cartão de débito");
INSERT INTO forma_pagamento (id, descricao) VALUES (3, "Dinheiro");

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
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ("Camarão tailandês", "16 camarões grandes ao molho picante", 110, 1, 1);

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

insert into usuario (id, nome, email, senha, data_cadastro) values (1, "João", "joao@email.com", "12345", utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2, "Maria", "maria@email.com", "12345", utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (3, "Raimunda", "raimunda@email.com", "12345", utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (4, "Teresa", "teresa@email.com", "12345", utc_timestamp);

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), 
(2, 1), (2, 3), (2, 5), (2, 7), (3, 1), (3, 3), (3, 5), (3, 7), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8);

