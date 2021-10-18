package services;

import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;

public class ServiceTeste {
    
    Memoria ram = new Memoria();
    Processador processador = new Processador();
    DiscosGroup disco = new DiscosGroup();
    
    
    public void infoRam() {
        
//        Pegando RAM total
        ram.getTotal();
//        Pegando RAM em uso
        ram.getEmUso();
//        Pegando RAM disponível
        ram.getDisponivel();
        
    }
    
    public void infoProcessador(){
        
//        Pegando o nome do Processador
        processador.getNome();
//        Pegando o numero de cpus fisicas
        processador.getNumeroCpusFisicas();
//        Pegando o numero de cpus logicas
        processador.getNumeroCpusLogicas();
    }
    
    public void infoMemoriaDisco() {
        
//        Tamanho total do Disco
        disco.getTamanhoTotal();
    }
    
}
