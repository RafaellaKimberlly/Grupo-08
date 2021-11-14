'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let usuario = sequelize.define('dado',{
		idUsuario: {
			field: 'idDado',
			type: DataTypes.INTEGER,
			autoIncrement: true
		},
        fkMaquinas: {
			field: 'fkMaquinas',
			type: DataTypes.INTEGER,
            allowNull: false
		},
        fkComponentes: {
			field: 'fkComponentes',
			type: DataTypes.INTEGER,
            allowNull: false
		},		
		mcStatus: {
			field: 'mcStatus',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tipoDados', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return usuario;
};
