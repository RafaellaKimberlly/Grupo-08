create database CryptoVerse;

use CryptoVerse;

create table tbUsuario(
		 idUsuario INT PRIMARY KEY auto_increment,
         nome VARCHAR(30),
         sobrenome VARCHAR(40),
         email VARCHAR(40),
         senha VARCHAR(30),
         uf CHAR(2),
         fkPool int,
         foreign key (fkPool) references pools (idPool)
)auto_increment = 1;
	
    create table tbPools (
		idPool int primary key auto_increment,
        nomePool varchar(60),
        descricao varchar(100)
    );
    
    create table tbMaquinas(
    idMaquina int primary key auto_increment,
    hostname varchar(60),
    numSerie varchar(60),
    tipo_Processador varchar(20),
    fkUsuario int,
	foreign key (fkUsuario) references tbUsuario (idUsuario)
    )auto_increment = 100;

	create table tbComponentes(
    idComponentes int primary key auto_increment,
    nomeComponente varchar(30)
    )auto_increment = 1000;
    
    create table tbMaquinas_Componentes(
    idMaquina_Componentes int primary key auto_increment,
    fkMaquinas int,
	foreign key (fkMaquinas) references tbMaquinas(idMaquinas),
	fkComponentes int,
	foreign key (fkComponentes) references tbComponetes(idComponentes),
	mcStatus varchar(7)
    );
    
	create table tbLeituras(
    idLeituras int primary key auto_increment,
	nvAlerta varchar(5),
    dataHora datetime default now(),
    fkPlacaVideo int,
    foreign key (fkPlacaVideo) references PlacaVideo (idPlacaVideo),
    fkProcessador int,
    foreign key (fkProcessador) references Processador (idProcessador)
    );    



select * from tbUsuario;
