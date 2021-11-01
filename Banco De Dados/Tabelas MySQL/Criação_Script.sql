create database CryptoVerse;

use CryptoVerse;

create table tbUsuarios(
	idUsuario int primary key auto_increment,
	nome varchar(20),
    sobrenome varchar(45),
    email varchar(40),
    senha varchar(30),
    uf char(2),
    fkPool int
)auto_increment = 1;

create table tbPools(
	idPool int primary key auto_increment,
    nomePool varchar(30),
    descricao varchar(100)
)auto_increment = 1;

create table tbMaquinas(
	idMaquina int primary key auto_increment,
    hostname varchar(20),
    numSerie varchar(30),
    tipoProcessador varchar(30),
    fkUsuario int
)auto_increment = 1;

create table tbComponentes(
	idComponente int primary key auto_increment,
    NomeComponente varchar(30)
)auto_increment = 1;

create table tbMaquinas_Componentes(
	idMaquinas_Componentes int primary key auto_increment,
    fkMaquina int,
    fkComponentes int,
    status varchar(7)
)auto_increment = 1;

create table tipo_dados(
	idTipo_dados int primary key auto_increment,
    desc_dado varchar(45)
)auto_increment = 1;

create table tbLeituras(
	idLeitura int primary key auto_increment,
    nvAlerta char(1),
    valor int,
    dataHora datetime,
    fkTipo_Dados int,
    fkMaquinas_Componentes int,
    fkMaquinas int,
    fkComponentes int
)auto_increment = 1;

alter table tbUsuarios add foreign key (fkPool) references tbPools(idPool);
alter table tbMaquinas add foreign key (fkUsuario) references tbUsuarios(idUsuario);
alter table tbMaquinas_Componentes add foreign key (fkMaquina) references tbMaquinas(idMaquina);
alter table tbMaquinas_Componentes add foreign key (fkComponentes) references tbComponentes(idComponente);
alter table tbLeituras add foreign key (fkTipo_Dados) references tipo_dados(idTipo_dados);
alter table tbLeituras add foreign key (fkMaquinas_Componentes) references tbMaquinas_Componentes(idMaquinas_Componentes);
alter table tbLeituras add foreign key (fkMaquinas) references tbMaquinas_Componentes(fkMaquina);
alter table tbLeituras add foreign key (fkComponentes) references tbMaquinas_Componentes(fkComponentes);