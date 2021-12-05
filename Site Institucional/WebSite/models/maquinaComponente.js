'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let maquinaComponente = sequelize.define('maquinaComponente',{
		idMaquinaComponente: {
			field: 'idMaquinaComponente',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},
        fkMaquina: {
			field: 'fkMaquina',
			type: DataTypes.INTEGER,
            allowNull: false
		},
        fkComponente: {
			field: 'fkComponente',
			type: DataTypes.INTEGER,
            allowNull: false
		},		
		mcStatus: {
			field: 'mcStatus',
			type: DataTypes.STRING,
			allowNull: false
		},
		descComponente: {
			field: 'descComponente',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tb_maquina_componente', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return maquinaComponente;
};
