/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Fabricio
 */
public class ConexaoBD {
    
    private BasicDataSource bancoDeDados;
    
//    // MySQL
//    public ConexaoBD() {
        
//        this.bancoDeDados = new BasicDataSource();
//        this.bancoDeDados.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        this.bancoDeDados.setUrl("jdbc:mysql://localhost:3306/projetoCryptoverse?useTimezone=true&serverTimezone=UTC");
//        this.bancoDeDados.setUsername("admcrypto");
//        this.bancoDeDados.setPassword("teste123");
        
//    }
    
    //  SQLServer  
    public ConexaoBD() {
        this.bancoDeDados = new BasicDataSource();
        this.bancoDeDados.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.bancoDeDados.setUrl("jdbc:sqlserver://cryptoverse.database.windows.net:1433;database=CryptoVerse;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;");
        this.bancoDeDados.setUsername("admcrypto");
        this.bancoDeDados.setPassword("#Gfgrupo8");
    }
    
    public BasicDataSource getDataSource() {
        return bancoDeDados;
    }
    
}
