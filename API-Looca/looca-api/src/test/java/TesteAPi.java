
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.ProcessosGroup;
import com.github.britooo.looca.api.group.servicos.ServicosGroup;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import java.util.List;

/*
 * The MIT License
 *
 * Copyright 2021 imkbe.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 *
 * @author imkbe
 */
public class TesteAPi {
    public static void main(String[] args) {
        
        Looca looca = new Looca();
        Sistema sistema = looca.getSistema();
        Processador processador = looca.getProcessador();
        Memoria memoria = looca.getMemoria();
        DiscosGroup disco = looca.getGrupoDeDiscos();
        ProcessosGroup processo = looca.getGrupoDeProcessos();
        ServicosGroup servico = looca.getGrupoDeServicos();
        Temperatura temperatura = looca.getTemperatura();
        
        
        //informações necessárias:
        sistema.getSistemaOperacional();
        sistema.getInicializado();
        sistema.getTempoDeAtividade();
        
        //processador
        processador.getIdentificador();
        processador.getId();
        processador.getNumeroCpusFisicas();
        processador.getNumeroCpusLogicas();
        processador.getUso();
        
         //memoria
        memoria.getDisponivel();
        memoria.getEmUso();
        memoria.getTotal();

        //disco
        disco.getQuantidadeDeDiscos();
        disco.getQuantidadeDeVolumes();
        disco.getTamanhoTotal();
        
        //Processo    
        processo.getTotalProcessos();
        processo.getTotalThreads();
        
        //servicos     
        servico.getTotalServicosAtivos();
        servico.getTotalServicosInativos();

        //temperatura
        temperatura.getTemperatura();
        
        
        
        
        //sistema
//        sistema.getPermissao();
//        sistema.getFabricante();
//        sistema.getArquitetura();
//        sistema.getInicializado();
//        sistema.getTempoDeAtividade();
//        sistema.getSistemaOperacional();
//
//        //processador
//        processador.getFabricante();
//        processador.getNome();
//        processador.getId();
//        processador.getIdentificador();
//        processador.getMicroarquitetura();
//        processador.getFrequencia();
//        processador.getNumeroPacotesFisicos();
//        processador.getNumeroCpusFisicas();
//        processador.getNumeroCpusLogicas();
//        processador.getUso();
//
//        //memoria
//        memoria.getDisponivel();
//        memoria.getEmUso();
//        memoria.getTotal();
//
//        //disco
//        disco.getDiscos();
//        disco.getVolumes();
//        disco.getQuantidadeDeDiscos();
//        disco.getQuantidadeDeVolumes();
//        disco.getTamanhoTotal();
//
//        //grupo de processo
//        processo.getProcessos();
//        processo.getTotalProcessos();
//        processo.getTotalThreads();
//
//        //grupo de serviços
//        servico.getServicos();
//        servico.getServicosAtivos();
//        servico.getServicosInativos();
//        servico.getTotalDeServicos();
//        servico.getTotalServicosAtivos();
//        servico.getTotalServicosInativos();
//
//        //temperatura
//        temperatura.getTemperatura();

        
        //Aparecendo no sistema
        System.out.println(sistema);
        System.out.println(processador);
        System.out.println(memoria);
        System.out.println(disco);
        System.out.println(processo);
        System.out.println(servico);
        System.out.println(temperatura);
        

        
    }
}
