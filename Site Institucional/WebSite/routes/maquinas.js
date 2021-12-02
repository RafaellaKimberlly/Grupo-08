var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var maquina = require('../models').maquina;
var env = process.env.NODE_ENV || 'development';


var endereco = require('../public/maps');

let sessoes = [];

// Cadastrando Máquina
router.post('/cadastrar/:long/:lat', function(req, res, next) {
	console.log('Criando uma maquina');

	var long = req.params.long;
	var lat = req.params.lat;
	
	maquina.create({
		hostname : req.body.hostname,
		numSerie : req.body.numSerie,
		tipo_Processador : req.body.tipo_Processador,
		fkUsuario: req.body.fkUsuario,
		lat: lat,
		lng: long
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
	
	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');

	let instrucaoSql = "";

	if(env == 'dev') {
		instrucaoSql = `select distinct(idMaquina), hostname, coalesce(mcStatus, 'Inativo') as mcStatus, fkUsuario from tb_maquina as m
	left join tb_maquina_componente as mc
	on mc.fkMaquina = m.idMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	order by idMaquina;`;
	} else if (env == 'production') {
		instrucaoSql = `select distinct(idMaquina), hostname, coalesce(mcStatus, 'Inativo') as mcStatus, fkUsuario from tb_maquina as m
	left join tb_maquina_componente as mc
	on mc.fkMaquina = m.idMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	order by idMaquina;`;
	}

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

router.get('/nomeComponente', function(req, res, next) {
	console.log('Recuperando todas as publicações');
	
	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');

	let instrucaoSql = "";

	if(env == 'dev') {
		instrucaoSql = `select idMaquina, hostname, c.nomeComponente, coalesce(mcStatus, 'Inativo') as mcStatus, fkUsuario from tb_maquina as m
		left join tb_maquina_componente as mc
		on mc.fkMaquina = m.idMaquina
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		order by idMaquina;`;
	} else if (env == 'production') {
		instrucaoSql = `select idMaquina, hostname, c.nomeComponente, coalesce(mcStatus, 'Inativo') as mcStatus, fkUsuario from tb_maquina as m
		left join tb_maquina_componente as mc
		on mc.fkMaquina = m.idMaquina
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		order by idMaquina;`;
	}

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

router.get('/contar_maquinas/:idUsuario', function(req, res, next) {
	console.log('Recuperando quantidades');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = "";

	if(env == 'dev') {
		instrucaoSql = `select count(idMaquina) as qtdMaquina from tb_maquina as m 
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		where m.fkUsuario = ${idUsuario};`;
	} else if (env == 'production') {
		instrucaoSql = `select count(idMaquina) as qtdMaquina from tb_maquina as m 
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	where m.fkUsuario = ${idUsuario};`;
	}
	
	console.log(instrucaoSql);

	sequelize.query(instrucaoSql, { 
		model: maquina
	})
	.then(resultado => {
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});


module.exports = router;

module.exports = router;
