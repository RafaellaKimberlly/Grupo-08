var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var componente = require('../models').componente;

let sessoes = [];

// Cadastrando Máquina
router.post('/cadastrar', function (req, res, next) {
	console.log('Criando uma maquina');

	maquina.create({
		hostname: req.body.hostname,
		numSerie: req.body.numSerie,
		tipo_Processador: req.body.tipo_Processador,
		fkUsuario: req.body.fkUsuario
	}).then(resultado => {
		console.log(`Registro criado: ${resultado}`)
		res.send(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

// Recuperando todas as maquinas
router.get('/', function (req, res, next) {
	console.log('Recuperando todas as publicações');

	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');

	let instrucaoSql = `select idComponente, nomeComponente from tb_componente;`;

	sequelize.query(instrucaoSql, {
		model: componente,
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

router.get('/componente_usuario/:idUsuario', function (req, res, next) {
	console.log('Recuperando todas as publicações');

	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');
	let idUsuario = req.params.idUsuario;

	let instrucaoSql = `select fkMaquina, nomeComponente from tb_maquina_componente as mc
	join tb_componente as c
	on c.idComponente = mc.fkComponente 
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	where nomeComponente = 'Memoria-RAM'
	and idUsuario = ${idUsuario}
	order by fkMaquina;`;

	sequelize.query(instrucaoSql, {
		model: componente,
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

router.get('/cpu_usuario/:idUsuario', function (req, res, next) {
	console.log('Recuperando todas as publicações');

	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');
	let idUsuario = req.params.idUsuario;

	let instrucaoSql = `select fkMaquina, nomeComponente from tb_maquina_componente as mc
	join tb_componente as c
	on c.idComponente = mc.fkComponente 
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	where nomeComponente = 'cpu'
	and idUsuario = ${idUsuario}
	order by fkMaquina;`;

	sequelize.query(instrucaoSql, {
		model: componente,
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

router.get('/disco_usuario/:idUsuario', function (req, res, next) {
	console.log('Recuperando todas as publicações');

	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');
	let idUsuario = req.params.idUsuario;

	let instrucaoSql = `select fkMaquina, nomeComponente from tb_maquina_componente as mc
	join tb_componente as c
	on c.idComponente = mc.fkComponente 
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	where nomeComponente = 'Disco'
	and idUsuario = ${idUsuario}
	order by fkMaquina;`;

	sequelize.query(instrucaoSql, {
		model: componente,
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

module.exports = router;

module.exports = router;