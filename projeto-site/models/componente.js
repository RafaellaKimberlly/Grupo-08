'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let componente = sequelize.define('componente',{
		idComponente: {
			field: 'idComponente',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nomeComponente: {
			field: 'nomeComponente',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tb_componente', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return componente;
};
