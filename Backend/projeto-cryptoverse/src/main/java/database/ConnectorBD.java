/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Fabricio
 */
public class ConnectorBD {
    
    private BasicDataSource bancoDeDados;
    
//    // MySQL
//    public ConexaoBD() {
//        
//        this.bancoDeDados = new BasicDataSource();
//        this.bancoDeDados.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        this.bancoDeDados.setUrl("jdbc:mysql://localhost:3306/projetoCryptoverse?useTimezone=true&serverTimezone=UTC");
//        this.bancoDeDados.setUsername("root");
//        this.bancoDeDados.setPassword("saladaMista1234@");
//        
//    }
    
    //  SQLServer  
    public ConnectorBD() {
        this.bancoDeDados = new BasicDataSource();
        this.bancoDeDados.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.bancoDeDados.setUrl("jdbc:sqlserver://projeto-cryptoverse.database.windows.net:1433;database=cryptoverse;user=admcrypto@projeto-cryptoverse;password={#Gfgrupo8};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        this.bancoDeDados.setUsername("admcrypto");
            this.bancoDeDados.setPassword("#Gfgrupo8");
    }
    
    public Connection getDataSource() throws SQLException {
        return bancoDeDados.getConnection();
    }
    public Connection retornarConexao() throws SQLException {

        Connection connection = DriverManager
                .getConnection("jdbc:sqlserver://projeto-cryptoverse.database.windows.net:1433;database=cryptoverse","admcrypto@projeto-cryptoverse","#Gfgrupo8");

        return connection;
    }
}