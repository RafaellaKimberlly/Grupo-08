'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let usuario = sequelize.define('usuario',{
		idUsuario: {
			field: 'idLeituras',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nome: {
			field: 'nvAlerta',
			type: DataTypes.STRING,
			allowNull: false
		},
		sobrenome: {
			field: 'valor',
			type: DataTypes.STRING,
			allowNull: false
		},
		email: {
			field: 'dataHora',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tbLeituras', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return usuario;
};
