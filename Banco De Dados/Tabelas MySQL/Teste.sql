create database CryptoVerse;

use CryptoVerse;

create table usuario(
idUsuario INT PRIMARY KEY auto_increment,
         nome VARCHAR(30),
         sobrenome VARCHAR(40),
         pais VARCHAR(25),
         login VARCHAR(50),
         senha VARCHAR(30)
);

drop table Usuario;

select * from usuario;