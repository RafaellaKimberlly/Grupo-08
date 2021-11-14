'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let leitura = sequelize.define('leitura',{
		idLeituras: {
			field: 'idLeituras',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nvAlerta: {
			field: 'nvAlerta',
			type: DataTypes.STRING,
			allowNull: false
		},
		valor: {
			field: 'valor',
			type: DataTypes.STRING,
			allowNull: false
		},
		dataHora: {
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

    return leitura;
};
