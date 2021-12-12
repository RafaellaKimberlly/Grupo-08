'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let dado = sequelize.define('dado',{
		idDado: {
			field: 'idDado',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		descDado: {
			field: 'descDado',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tb_tipo_dados', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return dado;
};
