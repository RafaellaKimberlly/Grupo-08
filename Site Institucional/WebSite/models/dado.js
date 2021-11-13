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
			primaryKey: true,
			autoIncrement: true
		},		
		nome: {
			field: 'descDado',
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
