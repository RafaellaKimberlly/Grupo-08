'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let maquina = sequelize.define('maquina',{
		idMaquina: {
			field: 'idMaquina',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		hostname: {
			field: 'hostname',
			type: DataTypes.STRING,
			allowNull: false
		},
		numSerie: {
			field: 'numSerie',
			type: DataTypes.STRING,
			allowNull: false
		},
		tipo_Processador: {
			field: 'tipo_Processador',
			type: DataTypes.STRING,
			allowNull: false
		},
		lat: {
			field: 'lat',
			type: DataTypes.STRING,
			allowNull: false
		},
		lng: {
			field: 'lng',
			type: DataTypes.STRING,
			allowNull: false
		},
		fkUsuario: {
			field: 'fkUsuario',
			type: DataTypes.STRING,
			allowNull: false
		}

	}, 
	{
		tableName: 'tb_maquina', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return maquina;
};
