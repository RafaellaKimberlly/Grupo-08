var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var leitura = require('../models').leitura;
var env = process.env.NODE_ENV || 'development';


router.get('/tempo-atual/:idMaquinaComponente', function(req, res, next) {
	console.log('Recuperando idMaquinaComponente');
	
	//var idcaminhao = req.body.idcaminhao; // depois de .body, use o nome (name) do campo em seu formulário de login
	var idMaquinaComponente = req.params.idMaquinaComponente;
	
	let instrucaoSql = "";
	
	if (env == 'dev') {
		// abaixo, escreva o select de dados para o Workbench
		instrucaoSql = `select nvAlerta, valor, DATE_FORMAT(momento,'%H:%i:%s') as momento_grafico, fkMaquinaComponente from leitura where fkMaquinaComponente = ${idMaquinaComponente} order by id desc limit 1`;
	} else if (env == 'production') {
		// abaixo, escreva o select de dados para o SQL Server
		instrucaoSql = `select top 1 valor, format(dataHora,'dd/MM/yyyy HH:mm:ss') as dataHora from tb_leitura where fkMaquinaComponente = ${idMaquinaComponente} order by dataHora desc`;
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

	if(env == 'dev') {
		instrucaoSql = `select idLeitura, nvAlerta, dataHora,  m.hostname, c.nomeComponente, m.fkUsuario from tb_leitura as l
		join tb_maquina_componente as mc
		on mc.idMaquinaComponente = l.fkMaquinaComponente
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		order by idLeitura;`;
	} else if (env == 'product') {
		instrucaoSql = `select idLeitura, nvAlerta, dataHora,  m.hostname, c.nomeComponente, m.fkUsuario from tb_leitura as l
		join tb_maquina_componente as mc
		on mc.idMaquinaComponente = l.fkMaquinaComponente
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_usuario as u
		on u.idUsuario = m.fkUsuario
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		order by idLeitura;`;
	}

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


router.get('/marcadores', function (req, res, next) {
	
	console.log(`Recuperando os marcadores`);

	const instrucaoSql = `select lat, lng from tb_maquina;`;
				
	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
		.then(resultado => {
			res.json(resultado);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
  
});

router.delete('/deletar/:idMaquina', function (req, res, next) {
    console.log('Deletando maquina');
    var idMaquina = req.params.idMaquina;
    let instrucaoSql = "";
    let instrucaoSql2 = "";
	
     

    if (env == 'dev') {
		instrucaoSql = `update tb_maquina_componente set fkMaquina = null where fkMaquina = ${idMaquina} `;
		instrucaoSql2 = `delete from tb_maquina where idMaquina = ${idMaquina};`;
    } else if (env == 'production') {
        instrucaoSql = `update tb_maquina_componente set fkMaquina = null where fkMaquina = ${idMaquina} `;
		instrucaoSql2 = `delete from tb_maquina where idMaquina = ${idMaquina};`;
    } else {
        console.log("\n\n\n\nVERIFIQUE O VALOR DE LINHA 1 EM APP.JS!\n\n\n\n")
    }

    console.log(instrucaoSql);
    console.log(instrucaoSql2);
	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.UPDATE })
	.then(resultado => {
		console.log(resultado)
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});

    sequelize.query(instrucaoSql2, { type: sequelize.QueryTypes.DELETE })
        .then(resultado => {
		console.log(resultado)
            
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
		instrucaoSql = `select idLeitura, valor, dataHora, fkDado, fkMaquina, fkComponente from tb_leitura as l
		join tb_maquina_componente as mc
		on mc.idMaquinaComponente = l.fkMaquinaComponente
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkMaquina = 100 and fkComponente = 1001
		order by idLeitura desc limit 7;`;
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



router.get('/componentes/:idMaquina', function(req, res, next) {
	
	// quantas são as últimas leituras que quer? 7 está bom?
	const limite_linhas = 6;

	var idMaquina = req.params.idMaquina;

	console.log(`Recuperando as ultimas ${limite_linhas} leituras`);
	
	let instrucaoSql = "";

	if (env == 'dev') {
		// abaixo, escreva o select de dados para o Workbench
		instrucaoSql = `select idLeitura, valor, dataHora, fkDado, fkMaquina, fkComponente from tb_leitura as l
		join tb_maquina_componente as mc
		on mc.idMaquinaComponente = l.fkMaquinaComponente
		join tb_maquina as m
		on m.idMaquina = mc.fkMaquina
		join tb_componente as c
		on c.idComponente = mc.fkComponente
		where fkMaquina = ${idMaquina} 
		order by idLeitura desc limit 7;`;

	} else if (env == 'production') {
		// abaixo, escreva o select de dados para o SQL Server
		instrucaoSql = `select top ${limite_linhas} idLeitura, valor, dataHora, format(dataHora,'dd/MM/yyyy HH:mm:ss') as dataHora, fkDado, fkMaquina, fkComponente from tb_leitura 
		join tb_maquina_componente on tb_maquina_componente.idMaquinaComponente = tb_leitura.fkMaquinaComponente
		join tb_maquina on tb_maquina.idMaquina = tb_maquina_componente.fkMaquina
		join tb_componente on tb_componente.idComponente = tb_maquina_componente.fkComponente
		where fkMaquina = ${idMaquina} 
		order by idLeitura desc`;
	} else {
		console.log("\n\n\n\nVERIFIQUE O VALOR DE LINHA 1 EM APP.JS!\n\n\n\n")
	}
	
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




router.get('/tempo-Atual:idMaquinaComponente', function(req, res, next) {
	console.log('Recuperando leituras');
	
	//var idcaminhao = req.body.idcaminhao; // depois de .body, use o nome (name) do campo em seu formulário de login
	var idMaquinaComponente = req.params.idMaquinaComponente;
	
	let instrucaoSql = "";
	
	if (env == 'dev') {
		// abaixo, escreva o select de dados para o Workbench
		instrucaoSql = `select valor, dataHora from tb_leitura order by idLeitura desc limit 1;`;
	} else if (env == 'production') {
		// abaixo, escreva o select de dados para o SQL Server
		instrucaoSql = `select top 1 valor, format(dataHora,'dd/MM/yyyy HH:mm:ss') as dataHora from tb_leitura where fkMaquinaComponente = ${idMaquinaComponente} order by dataHora desc`;
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

//TESTE//
router.get('/situacao_componente/:idUsuario', function(req, res, next) {
	console.log('Recuperando valor');	

	var idUsuario = req.params.idUsuario;

	let instrucaoSql = `select u.idUsuario, mc.idMaquinaComponente, c.nomeComponente, m.hostname, l.valor, d.descDado from tb_leitura as l
	join tb_maquina_componente as mc
	on mc.idMaquinaComponente = l.fkMaquinaComponente
	join tb_componente as c
	on c.idComponente = mc.fkComponente
	join tb_maquina as m
	on m.idMaquina = mc.fkMaquina
	join tb_usuario as u
	on u.idUsuario = m.fkUsuario
	join tb_tipo_dados as d
	on d.idDado = l.fkDado
	where idUsuario = ${idUsuario};`;
	
	console.log(instrucaoSql);

	sequelize.query(instrucaoSql, { 
		model: leitura
	})
	.then(resultado => {
		res.json(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});


module.exports = router;
