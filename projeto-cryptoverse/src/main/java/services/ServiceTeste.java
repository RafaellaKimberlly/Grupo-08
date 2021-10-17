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

    public void infoProcessador() {

//        Pegando o nome do Processador
        processador.getNome();
//        Pegando o identificador do processador
        processador.getIdentificador();
//        Pegando a frequência do processador
        processador.getFrequencia();
//        Pegando o uso do processador
        processador.getUso();
    }

    public void infoMemoriaDisco() {

//        Pegando o tamanho total do disco
        disco.getTamanhoTotal();
//        Pegando o(s) disco(s) da máquina        
        disco.getDiscos();
//        Pegando a quantidade de discos da máquina        
        disco.getQuantidadeDeDiscos();
//        Pegando os volumes da máquina        
        disco.getVolumes();
//        Pegando a quantidade de volumes da máquina        
        disco.getQuantidadeDeVolumes();
    }

//    @Override
//    public String toString() {
//        return "ServiceTeste{" + "ram=" + ram + "\n" + ", processador=" + processador + "\n" + ", disco=" + disco + '}';
//    }

}
