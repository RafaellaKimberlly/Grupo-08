var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var maquinaComponente = require('../models').maquinaComponente;
var env = process.env.NODE_ENV || 'development';

let sessoes = [];

var nome =  Math.random() * (1000 - 1) + 1;


// Cadastrando Máquina
router.post('/cadastrar', function(req, res, next) {
	console.log('Vinculando máquina e componente');

	maquinaComponente.create({
        fkMaquina: req.body.fkMaquina,
		fkComponente: req.body.fkComponente,
		descComponente: nome.toString(),
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
	
	let instrucaoSql = "";

	if(env == 'dev') {
		instrucaoSql = `select * from tbMaquinas;`;
	} else if (env == 'production') {
		instrucaoSql = `select * from tbMaquinas;`;
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

router.get('/contar_cpu/:idUsuario', function(req, res, next) {
	console.log('Recuperando quantidades');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = "";

if( env == 'dev' ) {
	instrucaoSql = `select count(fkComponente) as qtdcpu from tb_maquina_componente as mc
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	where m.fkUsuario = ${idUsuario} and nomeComponente = 'cpu';`;
} else if(dev == 'production') {
	instrucaoSql = `select count(fkComponente) as qtdcpu from tb_maquina_componente as mc
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	where m.fkUsuario = ${idUsuario} and nomeComponente = 'cpu';`;
}

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

	let instrucaoSql = "";

	if(env == 'dev') {
		instrucaoSql = `select count(fkComponente) as qtdRam from tb_maquina_componente as mc
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkUsuario = ${idUsuario} and nomeComponente = 'Memoria-ram';`;
	} else if (env == 'production') {
		instrucaoSql = `select count(fkComponente) as qtdRam from tb_maquina_componente as mc
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkUsuario = ${idUsuario} and nomeComponente = 'Memoria-ram';`;
	}

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

	if(env == 'dev') {
		instrucaoSql = `select count(fkComponente) as qtdDisco from tb_maquina_componente as mc
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkUsuario = ${idUsuario} and nomeComponente = 'disco';`;
	} else if(env == 'production') {
		instrucaoSql = `select count(fkComponente) as qtdDisco from tb_maquina_componente as mc
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkUsuario = ${idUsuario} and nomeComponente = 'disco';`;
	}

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

router.post('/atualizar_tb_maquina_componente', function(req, res, next) {
	console.log('Atualizando status');

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
