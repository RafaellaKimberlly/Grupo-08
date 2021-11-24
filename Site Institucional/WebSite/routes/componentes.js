var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var componente = require('../models').componente;

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

module.exports = router;

module.exports = router;