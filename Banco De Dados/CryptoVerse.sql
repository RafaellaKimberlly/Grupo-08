create database CryptoVerse;

use CryptoVerse;

create table Usuario(
		 idUsuario INT PRIMARY KEY auto_increment,
         nome VARCHAR(30),
         sobrenome VARCHAR(40),
         pais VARCHAR(25),
         login VARCHAR(50),
         senha VARCHAR(30),
         dataCriacao datetime default now(),
		 dataAtualizacao datetime default now(),
         fkPool int,
         foreign key (fkPool) references pools (idPool)
)auto_increment = 1;
	
    create table Pools (
		idPool int primary key auto_increment,
        nomePool varchar(100),
        descricao varchar(100)
    );
    
    create table Maquinas(
    idMaquina int primary key auto_increment,
    fkUsuario int,
    nomeMaquina varchar(100),
    numSerie varchar(100)
    );

	create table Processador(
    idProcessador int primary key auto_increment,
    nome varchar(100),
    fkMaquina int,
    foreign key (fkMaquina) references maquinas (idMaquina)
    );
    
	create table PlacaVideo(
    idPlacaVideo int primary key auto_increment,
    nome varchar(100),
    fkMaquina int,
    foreign key (fkMaquina) references maquinas (idMaquina)
    );    

	create table Registros(
    idRegistro int primary key auto_increment,
    descricao varchar(100),
    perfomace varchar(3),
    alerta char(1),
    dataHora datetime default now(),
    fkPlacaVideo int,
    foreign key (fkPlacaVideo) references PlacaVideo (idPlacaVideo),
    fkProcessador int,
    foreign key (fkProcessador) references Processador (idProcessador)
    );    



select * from usuario;
