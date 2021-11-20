create database projetoCryptoverse;

use projetoCryptoverse;

create table tb_pool (
	idPool int primary key auto_increment,
	nomePool varchar(60),
	descricao varchar(100)
)auto_increment = 1;


insert into tb_pool values
(null, 'BitCoin', 'Venha minerara conosco'),
(null, 'Cryptoverse', 'Venha minerar conosco'),
(null, 'PoolParaTeste', 'Pool destinada apenas a testes');

select * from tb_pool;

create table tb_usuario(
	idUsuario INT PRIMARY KEY auto_increment,
	nome VARCHAR(30),
	sobrenome VARCHAR(60),
	email VARCHAR(40),
	senha VARCHAR(8),
	uf CHAR(2),
	fkPool int,
	foreign key (fkPool) references tb_pool(idPool)
)auto_increment = 1;

insert into tb_usuario (nome, sobrenome, email, senha, uf, fkPool) values
('Rafaella', 'Souza', 'rafaella@teste.com', '12345678', 'sp', null),
('Fabricio','Ajala','fabricio@teste.com','12345678','rj', null),
('Isaque','Cruz','isaque@teste.com','12345678','ac', null),
('Matheus', 'Lemos', 'matheus@teste.com', 'teste123', 'sp', null);

select * from tb_usuario;

create table tb_maquina(
	idMaquina int primary key auto_increment,
	hostname varchar(60),
	numSerie varchar(60),
	tipo_processador varchar(20),
	fkUsuario int,
	foreign key (fkUsuario) references tb_usuario (idUsuario)
)auto_increment = 100;

insert into tb_maquina values
(null,'naj45454opee663','102789456132','i5',4),
(null,'bgcfc1515332515','45484965233745','i7',1),
(null,'zsaws10265899451','1997665454052','i3',3),
(null, 'Maquina de teste', '9183591', 'i5', 4),
(null, 'Maquina de teste 2', '0184658391009874', 'i3', 2);

select * from tb_maquina;

create table tb_componente(
	idComponente int primary key auto_increment,
	nomeComponente varchar(30)
)auto_increment = 1000;

insert into tb_componente values
(null,'cpu'),
(null,'Memoria-RAM'),
(null,'Disco');

select * from tb_componente;
    
create table tb_maquina_componente(
	idMaquinaComponente int primary key auto_increment,
	fkMaquina int,
	foreign key (fkMaquina) references tb_maquina(idMaquina),
	fkComponente int,
	foreign key (fkComponente) references tb_componente(idComponente),
	mcStatus varchar(7),
	check ( mcStatus = 'Ativo' or mcStatus = 'Inativo')
)auto_increment = 10000;

insert into tb_maquina_componente values
(null, 100, 1000, 'Ativo'),
(null, 100, 1001, 'Ativo'),
(null, 100, 1002, 'Ativo'),
(null, 103, 1002, 'Ativo'),
(null, 103, 1001, 'Ativo'),
(null, 103, 1000, 'Ativo'),
(null, 102, 1000, 'Inativo'),
(null, 102, 1001, 'Ativo'),
(null, 102, 1002, 'Ativo'),
(null, 101, 1001, 'Inativo'),
(null, 101, 1000, 'Ativo');

select * from tb_maquina_componente;
    
create table tb_tipo_dados(
	idDado int primary key auto_increment,
    descDado varchar(20)
)auto_increment = 10;

insert into tb_tipo_dados values
(null, 'GB'),
(null, 'MB'),
(null, 'MHz'),
(null, 'TB');

create table tb_leitura(
	idLeitura int primary key auto_increment,
	nvAlerta varchar(10),
	valor int,
	dataHora datetime,
	fkDado int,
	foreign key (fkDado) references tb_tipo_dados(idDado),
	fkMaquinaComponente int,
	foreign key (fkMaquinaComponente) references tb_maquina_componente(idMaquinaComponente)
) auto_increment = 100000;

insert into tb_leitura (valor, dataHora, fkDado, fkMaquinaComponente) values
(10, '2021-04-19', 10, 10001),
(1000, '2021-04-19', 13, 10001),
(920, '2021-04-19', 12, 10002),
(12, '2021-04-19', 10, 10003),
(947, '2021-04-19', 12, 10004),
(106, '2021-04-19', 10, 10005),
(10, '2021-04-19', 10, 10006),
(5, '2021-04-19', 10, 10007),
(20, '2021-04-19', 10, 10008),
(15, '2021-04-19', 10, 10009),
(246, '2021-04-19', 12, 10010),
(284, '2021-04-19', 12, 10000),
(915, '2021-04-19', 10, 10001),
(816, '2021-04-19', 13, 10002),
(1, '2021-04-19', 10, 10003),
(8, '2021-04-19', 10, 10004),
(690, '2021-04-19', 10, 10005),
(274, '2021-04-19', 12, 10006),
(20, '2021-04-19', 10, 10007);

select * from tb_leitura;

select * from tb_usuario as u
join tb_maquina as m
on m.fkUsuario = u.idUsuario
where idUsuario = 4;

select fkComponente, 
case
when fkComponente = 1000 then fkDado = 12
when fkComponente = 1001 then fkDado = 11
when fkComponente = 1002 then fkDado = 10
end
from tb_maquina_componente as mc
join tb_maquina as m
on m.idMaquina = mc.fkMaquina
join tb_componente as c
on c.idComponente = mc.fkComponente
join tb_tipo_dados as d
on d.idDado = m.fkDado;

select tb_usuario.*, tb_maquina_componente.*, hostname, tipo_processador, fkUsuario,
                                                                        nomeComponente, dataHora, idLeitura, fkDado, idDado, valor, descDado, nvAlerta
from tb_usuario
join tb_maquina on tb_usuario.idUsuario = tb_maquina.fkUsuario
join tb_maquina_componente on tb_maquina.idMaquina = tb_maquina_componente.fkMaquina
join tb_componente on tb_maquina_componente.fkComponente = tb_componente.idComponente
join tb_leitura on tb_maquina_componente.idMaquinaComponente = tb_leitura.fkMaquinaComponente
join tb_tipo_dados on tb_leitura.fkDado = tb_tipo_dados.idDado;
