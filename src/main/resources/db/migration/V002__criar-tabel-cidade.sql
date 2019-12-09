CREATE TABLE cidade (
	id bigint not null AUTO_INCREMENT,
    nome_cidade varchar(80) not null,
    nome_estado varchar(80) not null,
    
    PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT charset=utf8;
