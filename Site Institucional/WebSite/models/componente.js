'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let componente = sequelize.define('componente',{
		idComponentes: {
			field: 'idComponentes',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nomeComponentes: {
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

    return componente;
};
