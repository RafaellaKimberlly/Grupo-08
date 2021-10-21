create database CryptoVerse;

use CryptoVerse;

create table tbUsuario(
		 idUsuario INT PRIMARY KEY auto_increment,
         nome VARCHAR(30),
         sobrenome VARCHAR(60),
         email VARCHAR(40),
         senha VARCHAR(8),
         uf CHAR(2)
)auto_increment = 1;

-- Adicionando a fkPools a tabela tbUsuario--
alter table tbUsuario add column fkPools int;
-- Alterando a tabela tbUsuario e adicionando a FK a coluna fkPools referenciando o IdPools --
alter table tbUsuario add foreign key (fkPools) references tbPools(idPools);
	
    create table tbPools (
		idPools int primary key auto_increment,
        nomePools varchar(60),
        descricao varchar(100)
    )auto_increment = 1;
    
    create table tbMaquinas(
    idMaquinas int primary key auto_increment,
    hostname varchar(60),
    numSerie varchar(60),
    tipo_Processador varchar(20),
    fkUsuario int,
	foreign key (fkUsuario) references tbUsuario (idUsuario)
    )auto_increment = 100;

	create table tbComponentes(
    idComponentes int primary key auto_increment,
    nomeComponentes varchar(30)
    )auto_increment = 1000;
    
    create table tbMaquinas_Componentes(
    idMaquinas_Componentes int primary key auto_increment,
    fkMaquinas int,
	foreign key (fkMaquinas) references tbMaquinas(idMaquinas),
	fkComponentes int,
	foreign key (fkComponentes) references tbComponentes(idComponentes),
	mcStatus varchar(7),
    check ( mcStatus = 'Ativo' or mcStatus = 'Inativo')
    );
    
	create table tbLeituras(
    idLeituras int primary key auto_increment,
	nvAlerta varchar(10),
    valor int,
    dataHora datetime default now(),
    fkComponentes int,
    foreign key (fkComponentes) references tbComponentes(idComponentes)
    );    

insert into tbUsuario values
(null,'Rafaella','Souza','rafaella@teste.com','12345678','sp',1),
(null,'Fabricio','Ajala','fabricio@teste.com','12345678','rj',2),
(null,'Isaque','Cruz','isaque@teste.com','12345678','ac',3);

select * from tbUsuario;

insert into tbPools values
(null, 'BocoJa', 'Siga as regras e dará tudo certo, somos uma familia aqui'),
(null, 'MinereAqui', 'Siga as regras e dará tudo certo, somos uma familia aqui'),
(null, 'Blockchain dos Brothers', 'Siga as regras e dará tudo certo, somos uma familia aqui');

select * from tbPools;

insert into tbMaquinas values
(null,'naj45454opee663','102789456132','i5',4),
(null,'bgcfc1515332515','45484965233745','i7',5),
(null,'zsaws10265899451','1997665454052','i3',6);

select * from tbMaquinas;

insert into tbComponentes values
(null,'cpu'),
(null,'Memoria'),
(null,'Placa de Video'),
(null,'Rede-wifi'),
(null,'Disco');

select * from tbComponentes;

insert into tbMaquinas_Componentes values
(null,100,1000,'Ativo'),
(null,101,1000,'Ativo'),
(null,102,1000,'Ativo');

insert into tbMaquinas_Componentes(fkMaquinas,fkComponentes,mcstatus) values
(100,1001,'Ativo'),
(101,1001,'Ativo'),
(102,1001, 'Ativo');

insert into tbMaquinas_Componentes(fkMaquinas,fkComponentes,mcStatus) values
(100,1002,'Ativo'),
(101,1002,'Ativo'),
(102,1002,'Inativo');

insert into tbMaquinas_Componentes(fkMaquinas,fkComponentes,mcStatus) values
(100,1003,'Inativo'),
(101,1003, 'Ativo'),
(102,1003, 'Inativo');

select * from tbMaquinas_Componentes;

insert into tbLeituras values
(null,null,100,'2015-01-02 08:07:00',1000),
(null,null,70,'2015-01-02 08:07:00',1001),
(null,null,40,'2015-01-02 08:07:00',1002),
(null,null,20,'2015-01-02 08:07:00',1003),
(null,null,80,'2015-01-02 08:07:00',1004);

select * from tbLeituras;

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

drop database Cryptoverse;



