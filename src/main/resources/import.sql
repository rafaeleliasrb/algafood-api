INSERT INTO cozinha(id, nome) VALUES (1, "Brasileira");
INSERT INTO cozinha(id, nome) VALUES (2, "Indiana");

INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Pé de fava", 10.50, 1);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Era uma vez Chalezinho", 50.70, 2);

INSERT INTO estado(id, nome) VALUES (1, "Ceará");
INSERT INTO estado(id, nome) VALUES (2, "São Paulo");

INSERT INTO cidade(id, nome, estado_id) VALUES (1, "Fortaleza", 1);
INSERT INTO cidade(id, nome, estado_id) VALUES (2, "Aracati", 1);
INSERT INTO cidade(id, nome, estado_id) VALUES (3, "São Paulo", 2);
INSERT INTO cidade(id, nome, estado_id) VALUES (4, "Santos", 2);
