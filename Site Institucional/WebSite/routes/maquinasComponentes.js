var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var maquinaComponente = require('../models').maquinaComponente;
var env = process.env.NODE_ENV || 'development';

let sessoes = [];

// Cadastrando Máquina
router.post('/cadastrar', function(req, res, next) {
	console.log('Vinculando máquina e componente');

	maquinaComponente.create({
        fkMaquina: req.body.fkMaquina,
		fkComponente: req.body.fkComponente,
        mcStatus: "Ativo"
	}).then(resultado => {
		console.log(`Registro criado: ${resultado}`)
        res.send(resultado);
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

// Recuperando todas as maquinas
router.get('/', function(req, res, next) {
	console.log('Recuperando todas as publicações');
	
    let instrucaoSql = `select * from tbMaquinas;`;

	sequelize.query(instrucaoSql, {
		model: maquina,
		mapToModel: true 
	})
	.then(resultado => {
		console.log(`Encontrados: ${resultado.length}`);
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

router.get('/componentes', function(req, res, next) {
	
	// quantas são as últimas leituras que quer? 7 está bom?
	const limite_linhas = 7;

	var idFreezer = req.params.idFreezer;

	console.log(`Recuperando as ultimas ${limite_linhas} leituras`);
	
	let instrucaoSql = "";

	if (env == 'dev') {
		// abaixo, escreva o select de dados para o Workbench
		instrucaoSql = `select u.idUsuario, count(fkMaquina) as componentes, mc.fkComponente, c.nomeComponente from tb_maquina_componente as mc
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkUsuario = 4
		group by mc.fkComponente;`;
	} else if (env == 'production') {
		// abaixo, escreva o select de dados para o SQL Server
		instrucaoSql = `select top ${limite_linhas} 
		temperatura, 
		dataHora,
		FORMAT(dataHora,'HH:mm:ss') as momento_grafico
		from historicoFreezer
		where fkFreezer = ${idFreezer}
		order by idhistoricoFreezer desc`;
	} else {
		console.log("\n\n\n\nVERIFIQUE O VALOR DE LINHA 1 EM APP.JS!\n\n\n\n")
	}
	
	sequelize.query(instrucaoSql, {
		model: maquinaComponente,
		mapToModel: true 
	})
	.then(resultado => {
		console.log(`Encontrados: ${resultado.length}`);
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

router.get('/contar_cpu/:idUsuario', function(req, res, next) {
	console.log('Recuperando quantidades');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = `select count(fkComponente) as qtdcpu from tb_maquina_componente as mc
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	where m.fkUsuario = ${idUsuario} and nomeComponente = 'cpu';`;

	sequelize.query(instrucaoSql, { 
		model: maquinaComponente
	})
	.then(resultado => {
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

router.get('/contar_ram/:idUsuario', function(req, res, next) {
	console.log('Recuperando quantidades');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = `select count(fkComponente) as qtdRam from tb_maquina_componente as mc
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	where fkUsuario = ${idUsuario} and nomeComponente = 'Memoria-ram';`;

	sequelize.query(instrucaoSql, { 
		model: maquinaComponente
	})
	.then(resultado => {
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

router.get('/contar_disco/:idUsuario', function(req, res, next) {
	console.log('Recuperando quantidades');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = `select count(fkComponente) as qtdDisco from tb_maquina_componente as mc
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	where fkUsuario = ${idUsuario} and nomeComponente = 'disco';`;

	sequelize.query(instrucaoSql, { 
		model: maquinaComponente
	})
	.then(resultado => {
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

router.post('/atualizar_freezer', function(req, res, next) {
	console.log('Recuperando usuário por id e senha');

	var fkMaquina = req.body.fkMaquina; // depois de .body, use o nome (name) do campo em seu formulário de login
	var fkComponente = req.body.fkComponente; // depois de .body, use o nome (name) do campo em seu formulário de login

	let instrucaoSql = `update tb_maquina_componente set mcStatus = 'Inativo' where fkMaquina = ${fkMaquina} and fkComponente = ${fkComponente};`;
	console.log(instrucaoSql);

	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.UPDATE })
	.then(resultado => {
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
})

module.exports = router;

module.exports = router;
