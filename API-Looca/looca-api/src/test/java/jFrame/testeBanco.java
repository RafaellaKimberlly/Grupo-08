package jFrame;

import org.apache.commons.dbcp2.BasicDataSource;

public class testeBanco {
    
    private BasicDataSource bancoDeDados;
    
    public testeBanco() {
        this.bancoDeDados = new BasicDataSource();
        this.bancoDeDados​.setDriverClassName("org.h2.Driver");
        this.bancoDeDados​.setUrl("jdbc:h2:file:./cryptoverse");
        this.bancoDeDados​.setUsername("demo@admcrypto");
        this.bancoDeDados​.setPassword("#Gfgrupo8");
    }

    public BasicDataSource getBancoDeDados() {
        return bancoDeDados;
    }
    
}
