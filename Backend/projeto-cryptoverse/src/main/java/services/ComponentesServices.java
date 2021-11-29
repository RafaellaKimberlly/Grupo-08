package services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import java.net.InetAddress;

import java.util.List;

public class ComponentesServices {

    Memoria ram = new Memoria();
    Looca looca = new Looca();
    Processador processador = new Processador();
    DiscosGroup discos = new DiscosGroup();

//    =======================================================================================================
//    RAM

    public Double tamanhoTotalRam(){
        return ram.getTotal().doubleValue();
    }

    public Double tamanhoUsadoRam(){
        return ram.getEmUso().doubleValue();
    }

    public Integer tamanhoDisponivelRam(){
        return ram.getDisponivel().intValue();
    }

    public Double getMemoriaEmUsoPorc() {
        return tamanhoUsadoRam() / (tamanhoTotalRam() * 0.01);
    }

//    ======================================================================================================
//
//    ======================================================================================================
//    Processador
    
    public void hostName(){
    try{
      String nomecomputador=InetAddress.getLocalHost().getHostName();
      System.out.println(nomecomputador);
    }catch (Exception e){
      System.out.println("Exception caught ="+e.getMessage());
    }
 
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

    public Double getCpuUsoPorc(){
        Double cpuUso = usoProcessador().doubleValue();
        return cpuUso;
    }

//    ========================================================================================================

//    ========================================================================================================
//    Disco
    public Double tamanhoTotalDisco(){
        return discos.getTamanhoTotal().doubleValue();
    }

    public List<Disco> totalDiscos(){
        return discos.getDiscos();
    }

    public Integer quantidadeDeDiscos(){
        return discos.getQuantidadeDeDiscos();
    }

    public List<Volume> totalVolume(){
        return discos.getVolumes();
    }

    public Integer quantidadeDeVolumes(){
        return discos.getQuantidadeDeVolumes();
    }

    public Double getDiscoPorc(){

        List<Disco> discosConvertidos = totalDiscos();

        Long tamanho = 0L;

        for (Disco disco : discosConvertidos) {
            System.out.println(disco);
            tamanho = disco.getTamanho();
        }

        return tamanho.doubleValue();
//                / (tamanhoTotalDisco() * 0.01);
//        return disco.get  (tamanhoTotalDisco() * 0.01) + 1000000000;

//        tamanhoUsadoRam() / (tamanhoTotalRam() * 0.0
    }

//    =========================================================================================================
}
