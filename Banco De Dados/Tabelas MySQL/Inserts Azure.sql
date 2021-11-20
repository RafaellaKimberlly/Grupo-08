select * from [dbo].[tb_maquina];
select * from [dbo].[tb_usuario];
select * from [dbo].[tb_pool];
select * from tb_componente;
select * from tb_tipo_dados;
select * from [dbo].[tb_maquina_componente];
select * from tb_leitura;

insert into tb_maquina(hostname, numSerie, tipo_processador, fkUSuario) values
('naj45454opee6','102789456132','i5',4),
('bgcfc15153325','45484965233745','i7',1),
('zsaws102658994','1997665454052','i3',3),
('Maquina2334', '018465839100', 'i3', 2);

insert into tb_componente(nomeComponente) values
('cpu'),
('Memoria-RAM'),
('Disco');

alter table [dbo].[tb_maquina] drop column hotname;

alter table [dbo].[tb_maquina] add hostname varchar(60);

insert into tb_pool(nomePool,descricao) values
('BitCoin', 'Venha minerara conosco'),
('Cryptoverse', 'Venha minerar conosco'),
('PoolParaTeste', 'Pool destinada apenas a testes');

insert into tb_maquina_componente(fkMaquina,fkComponente,mcStatus) values
(1, 100, 'Ativo'),
(1, 200, 'Ativo'),
(1, 200, 'Ativo'),
(2, 100, 'Inativo'),
(2, 300, 'Ativo'),
(2, 100, 'Ativo'),
(1, 300, 'Inativo'),
(1, 100, 'Ativo');

insert into tb_tipo_dados(descDado) values
('GB'),
('MB'),
('MHz'),
('TB');

insert into tb_leitura (valor, dataHora, fkDado, fkMaquinaComponente) values
(10, '2021-04-19 12:09:50', 10, 30000),
(1000, '2021-04-19 12:09:50', 50, 90000),
(8, '2021-04-19 12:09:50', 10, 40000),
(690, '2021-04-19 12:09:50', 10, 50000),
(274, '2021-04-19 12:09:50', 20, 60000),
(20, '2021-04-19 12:09:50', 10, 70000);

