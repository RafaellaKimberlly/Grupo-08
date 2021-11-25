var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var leitura = require('../models').leitura;
var env = process.env.NODE_ENV || 'development';


router.get('/tempo-real/:idcaminhao', function(req, res, next) {
	console.log('Recuperando caminhões');
	
	//var idcaminhao = req.body.idcaminhao; // depois de .body, use o nome (name) do campo em seu formulário de login
	var idcaminhao = req.params.idcaminhao;
	
	let instrucaoSql = "";
	
	if (env == 'dev') {
		// abaixo, escreva o select de dados para o Workbench
		instrucaoSql = `select temperatura, umidade, DATE_FORMAT(momento,'%H:%i:%s') as momento_grafico, fkcaminhao from leitura where fkcaminhao = ${idcaminhao} order by id desc limit 1`;
	} else if (env == 'production') {
		// abaixo, escreva o select de dados para o SQL Server
		instrucaoSql = `select top 1 temperatura, umidade, FORMAT(momento,'HH:mm:ss') as momento_grafico, fkcaminhao from leitura where fkcaminhao = ${idcaminhao} order by id desc`;
	} else {
		console.log("\n\n\n\nVERIFIQUE O VALOR DE LINHA 1 EM APP.JS!\n\n\n\n")
	}
	
	console.log(instrucaoSql);
	
	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
	.then(resultado => {
		res.json(resultado[0]);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

router.get('/', function(req, res, next) {
	console.log('Recuperando todas as publicações');
	
	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');

    let instrucaoSql = `select idLeitura, nvAlerta, dataHora,  m.hostname, c.nomeComponente, m.fkUsuario from tb_leitura as l
	join tb_maquina_componente as mc
	on mc.idMaquinaComponente = l.fkMaquinaComponente
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	order by idLeitura;`;

	sequelize.query(instrucaoSql, {
		model: leitura,
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


// estatísticas (max, min, média, mediana, quartis etc)
router.get('/estatisticas', function (req, res, next) {
	
	console.log(`Recuperando as estatísticas atuais`);

	const instrucaoSql = `select 
							max(temperatura) as temp_maxima, 
							min(temperatura) as temp_minima, 
							avg(temperatura) as temp_media,
							max(umidade) as umidade_maxima, 
							min(umidade) as umidade_minima, 
							avg(umidade) as umidade_media 
						from leitura`;
					

	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
		.then(resultado => {
			res.json(resultado[0]);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
  
});

//TESTE//
router.get('/situacao_componente/:idUsuario', function(req, res, next) {
	console.log('Recuperando valor');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = `select * from isaque join matheus where idUsuario = ${idUsuario}`;
	
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
