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

UPDATE tb_usuario
	SET nome = '${nome}', sobrenome = '${sobrenome}', email = '${email}',
	senha = '${senha}', uf = '${uf}', fkPool = '${fkPool}' 
	WHERE idUsuario = '${4}';

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

select * from tb_usuario join tb_maquina as m on m.fkUsuario = tb_usuario.idUsuario where email='matheus@teste.com' and senha='teste123';

create table tb_componente(
	idComponente int primary key auto_increment,
	nomeComponente varchar(30)
)auto_increment = 1000;

select * from tb_maquina;

insert into tb_componente values
(null,'cpu'),
(null,'Memoria-RAM'),
(null,'Disco');

select * from tb_componente;

select idMaquina, hostname, c.nomeComponente, coalesce(mcStatus, 'Inativo') as mcStatus, fkUsuario from tb_maquina as m
	left join tb_maquina_componente as mc
	on mc.fkMaquina = m.idMaquina
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	order by idMaquina;
    
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
(10, '2021-04-19 12:09:50', 10, 10001),
(1000, '2021-04-19 12:09:50', 13, 10001),
(920, '2021-04-19 12:09:50', 12, 10002),
(12, '2021-04-19 12:09:50', 10, 10003),
(947, '2021-04-19 12:09:50', 12, 10004),
(106, '2021-04-19 12:09:50', 10, 10005),
(10, '2021-04-19 12:09:50', 10, 10006),
(5, '2021-04-19 12:09:50', 10, 10007),
(20, '2021-04-19 12:09:50', 10, 10008),
(15, '2021-04-19 12:09:50', 10, 10009),
(246, '2021-04-19 12:09:50', 12, 10010),
(284, '2021-04-19 12:09:50', 12, 10000),
(915, '2021-04-19 12:09:50', 10, 10001),
(816, '2021-04-19 12:09:50', 13, 10002),
(1, '2021-04-19 12:09:50', 10, 10003),
(8, '2021-04-19 12:09:50', 10, 10004),
(690, '2021-04-19 12:09:50', 10, 10005),
(274, '2021-04-19 12:09:50', 12, 10006),
(20, '2021-04-19 12:09:50', 10, 10007);

select * from tb_leitura;

-- Selects API-PROJETO_SITE

select idLeitura, nvAlerta, dataHora, m.hostname, c.nomeComponente from tb_leitura as l
join tb_maquina_componente as mc
on mc.idMaquinaComponente = l.fkMaquinaComponente
join tb_maquina as m
on m.idMaquina = mc.fkMaquina
join tb_usuario as u
on u.idUsuario = m.fkUsuario
join tb_componente as c
on c.idComponente = mc.fkComponente
order by idLeitura;

-- Trazer Todas as máquinas do usuário ordenadas pelo id
select distinct(idMaquina), hostname, coalesce(mcStatus, 'Inativo') as Status from tb_maquina as m
left join tb_maquina_componente as mc
on mc.fkMaquina = m.idMaquina
join tb_usuario as u
on u.idUsuario = m.fkUsuario
order by idMaquina;

-- Contando a quantidade de máquinas do usuário
select count(idMaquina) as qtdMaquina from tb_maquina
where fkUsuario = 4;



-- Contando a quantidade de cpu que o usuario possui
select count(fkComponente) from tb_maquina_componente as mc
join tb_maquina as m
on m.idMaquina = mc.fkMaquina
join tb_usuario as u
on u.idUsuario = m.fkUsuario
join tb_componente as c
on c.idComponente = mc.fkComponente
where fkUsuario = 4 and nomeComponente = 'cpu';

-- Contando a quantidade de ram que o usuario possui

select count(fkComponente) from tb_maquina_componente as mc
join tb_maquina as m
on m.idMaquina = mc.fkMaquina
join tb_usuario as u
on u.idUsuario = m.fkUsuario
join tb_componente as c
on c.idComponente = mc.fkComponente
where fkUsuario = 4 and nomeComponente = 'Memoria-ram';

-- Contando a quantidade de discos que o usuario tem

select count(fkComponente) from tb_maquina_componente as mc
join tb_maquina as m
on m.idMaquina = mc.fkMaquina
join tb_usuario as u
on u.idUsuario = m.fkUsuario
join tb_componente as c
on c.idComponente = mc.fkComponente
where fkUsuario = 4 and nomeComponente = 'disco';

-- select 

/* select u.idUsuario, u.nome, m.idMaquina, m.hostname, c.idComponente, c.nomeComponente, l.idLeituras, l.nvAlerta, l.valor, d.descDado, 
l.dataHora, DATE_FORMAT(l.dataHora, '%H:%i:%s')
from tbLeituras as l
join tipoDados as d
	on tbLeituras.fkDado = tipoDados.idDado
join tbMaquinas_Componentes as mc
	on mc.idMaquinas_Componentes = tbLeituras.fkMaquinaComponente
join tbMaquinas as m
	on tbMaquinas.idMaquina = mc.fkMaquinas
join tbComponentes as c
	on tbComponentes.idComponente = mc.fkComponentes
join tbUsuarios as u
	on tbUsuario.idUsuario = tbMaquinas.fkUsuario
where u.idUsuario = ${idUsuario} and idMaquina = ${idMaquina} order by l.dataHora desc limit ${limite_linhas};*/

/*
select m.idMaquina, m.hostname, mc.mcStatus from tbMaquinas as m
join tbUsuario as u
on u.idUsuario = m.fkUsuario
join tbmaquinas_componentes as mc
on mc.fkMaquinas = m.idMaquina
where u.idUsuario = ${idUsuario}
*/

/*

*/

/*
select count(idMaquinaComponente) from maquinasComponentes as mc
join tbMaquinas as m
on m.idMaquina = mc.fkMaquina
join tbComponentes as c
on c.idComponentes = mc.fkComponentes
join tbUsuarios as u
on u.idUsuario = m.fkUsuario
where u.idUsuario = ${idUsuario} and c.nomeComponente = ${nomeComponente};
*/

-- Metricas --
select * from tbLeituras order by dataHora desc;
select * from tbLeituras order by dataHora asc;

select count(*) from tbLeituras;

select sum(dataHora) from tbLeituras;

select avg(valor) from tbLeituras;

select round(avg(valor),0) as media from tbLeituras;

select sum(valor) from tbLeituras;

select max(valor) from tbLeituras where fkComponentes = 1000;
select min(valor) from tbLeituras;

select * from tbLeituras