var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var usuario = require('../models').usuario;
var env = process.env.NODE_ENV || 'development';


let sessoes = [];

/* Recuperar usuário por login e senha */
router.post('/autenticar', function (req, res, next) {
	console.log('Recuperando usuário por email e senha');

	var email = req.body.email; // depois de .body, use o nome (name) do campo em seu formulário de login
	var senha = req.body.senha; // depois de .body, use o nome (name) do campo em seu formulário de login	

	if(env == 'dev') {
		instrucaoSql = `select * from tb_usuario where email='${email}' and senha='${senha}'`;
	} else if (env == 'production') {
		instrucaoSql = `select * from tb_usuario where email='${email}' and senha='${senha}'`;
	}
	console.log(instrucaoSql);

	sequelize.query(instrucaoSql, {
		model: usuario
	}).then(resultado => {
		console.log(`Encontrados: ${resultado.length}`);

		if (resultado.length == 1) {
			sessoes.push(resultado[0].dataValues.email);
			console.log('sessoes: ', sessoes);
			res.json(resultado[0]);
		} else if (resultado.length == 0) {
			res.status(403).send('Email e/ou senha inválido(s)');
		} else {
			res.status(403).send('Mais de um usuário com o mesmo email e senha!');
		}

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

/* Cadastrar usuário */
router.post('/cadastrar', function (req, res, next) {
	console.log('Criando um usuário');

	usuario.create({
		nome: req.body.nome,
		sobrenome: req.body.sobrenome,
		email: req.body.email,
		senha: req.body.senha,
		uf: req.body.uf,
		fkPool: req.body.fkPool

	}).then(resultado => {
		console.log(`Registro criado: ${resultado}`)
		res.send(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

/* Alterar dados do usuário */
router.post('/alterar/', function (req, res, next) {
	console.log('Alterando os dados do usuário.');

	var idUsuario = req.body.idUsuario;
	var nome = req.body.nome;
	var sobrenome = req.body.sobrenome;
	var email = req.body.email;
	var senha = req.body.senha;
	var uf = req.body.uf;

	let instrucaoSql = "";

	if(env == 'dev') {
		instrucaoSql = `UPDATE tb_usuario
		SET nome='${nome}', sobrenome='${sobrenome}', email='${email}',
		senha='${senha}', uf='${uf}' 
		WHERE idUsuario = ${idUsuario};`;
	} else if (env == 'production') {
		instrucaoSql = `UPDATE tb_usuario
		SET nome='${nome}', sobrenome='${sobrenome}', email='${email}',
		senha='${senha}', uf='${uf}' 
		WHERE idUsuario = ${idUsuario};`;
	}

	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.UPDATE })
	.then(resultado => {
			res.json(resultado);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
})

/* Verificação de usuário */
router.get('/sessao/:email', function (req, res, next) {
	let email = req.params.email;
	console.log(`Verificando se o usuário ${email} tem sessão`);

	let tem_sessao = false;
	for (let u = 0; u < sessoes.length; u++) {
		if (sessoes[u] == email) {
			tem_sessao = true;
			break;
		}
	}

	if (tem_sessao) {
		let mensagem = `Usuário ${email} possui sessão ativa!`;
		console.log(mensagem);
		res.send(mensagem);
	} else {
		res.sendStatus(403);
	}

});


/* Logoff de usuário */
router.get('/sair/:email', function (req, res, next) {
	let email = req.params.email;
	console.log(`Finalizando a sessão do usuário ${email}`);
	let nova_sessoes = []
	for (let u = 0; u < sessoes.length; u++) {
		if (sessoes[u] != email) {
			nova_sessoes.push(sessoes[u]);
		}
	}
	sessoes = nova_sessoes;
	res.send(`Sessão do usuário ${email} finalizada com sucesso!`);
});


/* Recuperar todos os usuários */
router.get('/', function (req, res, next) {
	console.log('Recuperando todos os usuários');
	usuario.findAndCountAll().then(resultado => {
		console.log(`${resultado.count} registros`);

		res.json(resultado.rows);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

/* router.post('/atualizar/:idUsuario', function (req, res, next) {
	console.log('Recuperando todas as publicações');

	// let idUsuario = sessionStorage.getItem('id_usuario_meuapp');
	let email = req.body.email;
	let senha = req.body.senha;
	let idUsuario = req.params.idUsuario

	let instrucaoSql = `update tb_usuario set
	email = ${email},
	senha = ${senha}
	where idUsuario = ${idUsuario};`;

	sequelize.query(instrucaoSql, {
		model: usuario,
		mapToModel: true
	}
		.then(resultado => {
			console.log(`Encontrados: ${resultado.length}`);
			res.json(resultado);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		}))
}); */

module.exports = router;
