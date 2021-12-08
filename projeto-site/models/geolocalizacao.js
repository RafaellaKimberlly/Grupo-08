'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let geolocalicao = sequelize.define('geolocalicao',{
		idgeolocalicao: {
			field: 'idgeolocalicao',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		latlng: {
			field: 'latlng',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'tb_geolocalicaos', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return geolocalicao;
};
