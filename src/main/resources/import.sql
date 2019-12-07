INSERT INTO cozinha(id, nome) VALUES (1, "Brasileira");
INSERT INTO cozinha(id, nome) VALUES (2, "Indiana");
INSERT INTO cozinha(id, nome) VALUES (3, "Árabe");

INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Pé de fava", 10.50, 2);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Era uma vez Chalezinho", 50.70, 1);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Coco Bambu", 20.50, 1);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Indiano", 2.70, 2);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Habbibs", 7.00, 3);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Esfiharia", 0.0, 3);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Baião de dois", 0.0, 1);
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ("Cai duro", 0.0, 1);

INSERT INTO estado(id, nome) VALUES (1, "Ceará");
INSERT INTO estado(id, nome) VALUES (2, "São Paulo");

INSERT INTO cidade(id, nome, estado_id) VALUES (1, "Fortaleza", 1);
INSERT INTO cidade(id, nome, estado_id) VALUES (2, "Aracati", 1);
INSERT INTO cidade(id, nome, estado_id) VALUES (3, "São Paulo", 2);
INSERT INTO cidade(id, nome, estado_id) VALUES (4, "Santos", 2);
