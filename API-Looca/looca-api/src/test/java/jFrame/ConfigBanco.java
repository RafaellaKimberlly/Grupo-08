package jFrame;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConfigBanco {
    
    private BasicDataSource bancoDeDados;
    
    public ConfigBanco() {
        this.bancoDeDados = new BasicDataSource();
        this.bancoDeDados​.setDriverClassName("org.h2.Driver");
        this.bancoDeDados​.setUrl("jdbc:h2:file:./cryptoverse");
        this.bancoDeDados​.setUsername("admcrypto");
        this.bancoDeDados​.setPassword("#Gfgrupo8");
    }

    public BasicDataSource getBancoDeDados() {
        return bancoDeDados;
    }
    
}
