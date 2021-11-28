create database projetoCryptoverse;

use projetoCryptoverse;

create table tb_pool (
	idPool int primary key auto_increment,
	nomePool varchar(60),
	descricao varchar(100)
)auto_increment = 1;

insert into tb_pool values
(null, 'BitCoin', 'Venha minerara conosco');

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
('Rafaella', 'Souza', 'rafaella@teste.com', '12345678', 'sp', 1);

select * from tb_usuario;

 create table tb_geolocalizacao(
 idgeolocalizacao int primary key auto_increment,
 latitude varchar(60),
 longitude varchar(60)
 )auto_increment = 1;

 insert into tb_geolocalizacao values(
 null, '-31.2255562', '-65.5521114455'
 );
 
 select * from tb_geolocalizacao;

create table tb_maquina(
	idMaquina int primary key auto_increment,
	hostname varchar(60),
	tipo_processador varchar(20),
	fkUsuario int,
	foreign key (fkUsuario) references tb_usuario (idUsuario),
     fkgeolocalizacao int,
		foreign key (fkgeolocalizacao) references tb_geolocalizacao (idgeolocalizacao)
)auto_increment = 1;

insert into tb_maquina values
(null,'naj45454opee663','i5',1,1);

select * from tb_maquina;

select * from tb_usuario join tb_maquina as m on m.fkUsuario = tb_usuario.idUsuario where email='rafaella@teste.com' and senha='12345678';

create table tb_componente(
	idComponente int primary key auto_increment,
	nomeComponente varchar(30)
)auto_increment = 1;

select * from tb_componente;

insert into tb_componente values
(null,'cpu');


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
)auto_increment = 1;

insert into tb_maquina_componente values
(null, 1, 1, 'Ativo');

select * from tb_maquina_componente;
    
create table tb_tipo_dados(
	idDado int primary key auto_increment,
    descDado varchar(20)
)auto_increment = 1;

insert into tb_tipo_dados values(
null, 'Porcentagem'
);

select * from tb_tipo_dados;

create table tb_leitura(
	idLeitura int primary key auto_increment,
	nvAlerta varchar(1),
	valor int,
	dataHora datetime,
	fkDado int,
	foreign key (fkDado) references tb_tipo_dados(idDado),
	fkMaquinaComponente int,
	foreign key (fkMaquinaComponente) references tb_maquina_componente(idMaquinaComponente)
) auto_increment = 1;

insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values
('c', '100', '2021-04-19 12:09:50', 1, 1);

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
where fkUsuario = 1;

-- Contando a quantidade de cpu que o usuario possui
select count(fkComponente) from tb_maquina_componente as mc
join tb_maquina as m
on m.idMaquina = mc.fkMaquina
join tb_usuario as u
on u.idUsuario = m.fkUsuario
join tb_componente as c
on c.idComponente = mc.fkComponente
where fkUsuario = 1 and nomeComponente = 'cpu';


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

-- Contando a quantidade de discos que o usuario tem
-- select count(fkComponente) from tb_maquina_componente as mc
-- join tb_maquina as m
-- on m.idMaquina = mc.fkMaquina
-- join tb_usuario as u
-- on u.idUsuario = m.fkUsuario
-- join tb_componente as c
-- on c.idComponente = mc.fkComponente
-- where fkUsuario = 4 and nomeComponente = 'disco';


/*
-- Contando a quantidade de ram que o usuario possui
-- select count(fkComponente) from tb_maquina_componente as mc
-- join tb_maquina as mon m.idMaquina = mc.fkMaquina
-- join tb_usuario as u
-- on u.idUsuario = m.fkUsuario
-- join tb_componente as c
-- on c.idComponente = mc.fkComponente
-- where fkUsuario = 1 and nomeComponente = 'Memoria-ram';
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
select * from tb_leitura order by dataHora desc;
select * from tb_leitura order by dataHora asc;

select count(*) from tb_leitura;

select sum(dataHora) from tb_leitura;

select avg(valor) from tb_leitura;

select round(avg(valor),0) as media from tb_leitura;

select sum(valor) from tb_leitura;

select min(valor) from tb_leitura;


drop database projetoCryptoverse;
