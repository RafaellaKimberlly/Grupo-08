var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var maquina = require('../models').maquina;

let sessoes = [];

// Cadastrando Máquina
router.post('/cadastrar', function(req, res, next) {
	console.log('Criando uma maquina');
	
	maquina.create({
		hostname : req.body.hostname,
		numSerie : req.body.numSerie,
		tipo_Processador : req.body.tipo_Processador,
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
router.get('/', function(req, res, next) {
	console.log('Recuperando todas as publicações');
	
	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');

    let instrucaoSql = `select distinct(idMaquina), hostname, coalesce(mcStatus, 'Inativo') as mcStatus, fkUsuario from tb_maquina as m
	left join tb_maquina_componente as mc
	on mc.fkMaquina = m.idMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	order by idMaquina;`;

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

	let instrucaoSql = `select count(idMaquina) as qtdMaquina from tb_maquina as m 
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	where m.fkUsuario = ${idUsuario};`;
	
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
