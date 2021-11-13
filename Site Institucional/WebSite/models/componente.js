'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let usuario = sequelize.define('componente',{
		idUsuario: {
			field: 'idComponentes',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nome: {
			field: 'nomeComponentes',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tbComponentes', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return usuario;
};
