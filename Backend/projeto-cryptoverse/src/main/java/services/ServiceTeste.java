package services;

import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;

import java.util.List;

public class ServiceTeste {

    Memoria ram = new Memoria();
    Processador processador = new Processador();
    DiscosGroup disco = new DiscosGroup();


    public Integer tamanhoTotalRam(){
        return ram.getTotal().intValue();
    }

    public Integer tamanhoUsadoRam(){
        return ram.getEmUso().intValue();
    }

    public Integer tamanhoDisponivelRam(){
        return ram.getDisponivel().intValue();
    }

    public String nomeProcessador(){
        return processador.getNome();
    }

    public Integer usoProcessador(){
        return processador.getUso().intValue();
    }

    public String identificadorProcessador(){
        return processador.getIdentificador();
    }

    public Integer frequenciaProcessador(){
        return processador.getFrequencia().intValue();
    }

    public Integer tamanhoTotalDisco(){
        return disco.getTamanhoTotal().intValue();
    }

    public List<Disco> totalDiscos(){
        return disco.getDiscos();
    }

    public Integer quantidadeDeDiscos(){
        return disco.getQuantidadeDeDiscos();
    }

    public List<Volume> totalVolume(){
        return disco.getVolumes();
    }

    public Integer quantidadeDeVolumes(){
        return disco.getQuantidadeDeVolumes();
    }

}
