var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var maquinasComponentes = require('../models').maquinaComponente;

let sessoes = [];

// Cadastrando Máquina
router.post('/cadastrar', function(req, res, next) {
	console.log('Vinculando máquina e componente');

    var nome = req.body.nomeEmpresa; // depois de .body, use o nome (name) do campo em seu formulário de login
	
	
	let instrucaoSql = `select * from tbMaquinas where hostname='${hostname}' and numSerie='${numSerie} and tipo_Processador='${tipo_Processador}''`;
	console.log(instrucaoSql);

	sequelize.query(instrucaoSql, {
		model: maquina
	}).then(resultado => {
		sessoes.push(resultado[0].dataValues.login);
			console.log('sessoes: ',sessoes);
			res.json(resultado[0]);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});


	
	maquinasComponentes.create({
        fkMaquina: req.body.fkMaquina,
		fkComponentes : req.body.fkComponentes,
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

module.exports = router;

module.exports = router;
