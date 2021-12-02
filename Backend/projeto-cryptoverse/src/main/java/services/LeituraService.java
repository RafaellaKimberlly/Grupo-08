package services;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoBD;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeituraService {

    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate controller = new JdbcTemplate(configBanco.getDataSource());
    Slack slack = new Slack();
    JSONObject json = new JSONObject();

    ComponentesServices componente = new ComponentesServices();
    Timer timer = new Timer();

    Date data = new Date();

    public void rodarTempoEmTempo() {

        Integer intervalo = (1000 * 30);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hostname:");
                componente.hostName();
                System.out.println(componente.looca.getSistema());
                System.out.println("-------------------------------");
                
                System.out.println("Dados do Processador:");
                System.out.println(componente.looca.getProcessador());
                System.out.println("Este é o uso do seu processador\n");
                System.out.println(componente.usoProcessador());
                System.out.println("Essa é a frequencia do seu processador\n");
                System.out.println(componente.frequenciaProcessador());
                System.out.println("Essa é a porcentagem de uso do seu processador\n");
                System.out.println(componente.getCpuUsoPorc());
                
                System.out.println("---------------------------------");
                System.out.println("Dados da Memoria:");
                System.out.println(componente.looca.getMemoria());
                System.out.println("Tamanho disponivel de ram: " + componente.tamanhoDisponivelRam());
                System.out.println("Porcentagem de memoria em uso: " + componente.getMemoriaEmUsoPorc());
                
                System.out.println("-------------------------------");
                System.out.println("Dados do Dados de disco:");
                System.out.println("Porcentagem de discos em uso: " + componente.getDiscoPorc());
                
                System.out.println("----------");
                System.out.println("Quantidade de discos:" + componente.discos.getQuantidadeDeDiscos());
                System.out.println("Quantidade de volumes:" + componente.discos.getQuantidadeDeVolumes());
                System.out.println("Tamanho total:" + componente.discos.getTamanhoTotal());
                
                System.out.println("--------------------------------------------------");
                
                System.out.println("---Nova leitura----");
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, intervalo);

    }

    public void addLeituraRam() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados da Ram");
        json.put("text", "Enviando dados de RAM.");
        Slack.sendMessage(json);
        String nvAlerta = "";
        
        long ramTotalGb = (componente.ram.getTotal()/1000000000);
        long ramEmUsoGb = (componente.ram.getEmUso()/1000000000);
        
        System.out.println(ramEmUsoGb + " GB em uso.");
        System.out.println("Total disponível: " + ramTotalGb + " GB");
        
        if ((ramEmUsoGb * 100) / ramTotalGb < 30) {
            System.out.printf("Performance: Estado Critico!");
            json.put("text", "Componente critico! Memória Ram com baixo desempenho. \n"
                    + Math.round(componente.ram.getEmUso())/1000000000 + " GB");
            Slack.sendMessage(json);
            nvAlerta = "C";
            
        } else if ((ramEmUsoGb * 100) / ramTotalGb < 60) {
            System.out.println("Performance: Estado de Atenção!");
            json.put("text", "Atenção!!! Sua memória ram está com baixo desempenho. \n"
                    + Math.round(componente.ram.getEmUso())/1000000000 + " GB");
            Slack.sendMessage(json);
            nvAlerta = "B";
            
        } else if ((ramEmUsoGb * 100) / ramTotalGb < 90) {
            System.out.println("Performance: Em bom estado!");
            json.put("text", "Componente em bom estado! Memória Ram em ótimo desempenho. \n"
                    + Math.round(componente.ram.getEmUso())/1000000000 + " GB");
            Slack.sendMessage(json);
            nvAlerta = "A";
            
        } else {
            System.out.println("Performance: Em perfeito estado!");
            json.put("text", "Componente em perfeito estado! Memória Ram em seu desempenho máximo. \n"
                    + Math.round(componente.ram.getEmUso())/1000000000 + " GB");
            Slack.sendMessage(json);
            nvAlerta = "S";
        }
        controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, componente.ram.getEmUso(), data, 1, 1);
    }

    public void addLeituraCpu() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de CPU");
        json.put("text", "Enviando dados de CPU.");
        Slack.sendMessage(json);
        String nvAlerta = "";
        
        long usoCpuPorc = Math.round(componente.processador.getUso());
        System.out.println(usoCpuPorc + "%");
        
        if (usoCpuPorc < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! CPU em baixo desempenho. \n"
                    + usoCpuPorc + "%");

            Slack.sendMessage(json);
            nvAlerta = "C";
        } else if (usoCpuPorc < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua CPU está perdendo desempenho. \n"
                    + usoCpuPorc + "%");
            Slack.sendMessage(json);
            nvAlerta = "B";
        } else if (usoCpuPorc < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! CPU em ótimo desempenho. \n"
                    + usoCpuPorc + "%");
            Slack.sendMessage(json);
            nvAlerta = "A";
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! CPU em seu desempenho máximo. \n"
                    + usoCpuPorc + "%");
            Slack.sendMessage(json);
            nvAlerta = "S";
        }
        controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, usoCpuPorc, data, 1, 1);
    }

    public void addLeituraDisco() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de Disco");
        json.put("text", "Enviando dados de Disco.");
        Slack.sendMessage(json);
        String nvAlerta = "";
        
        System.out.println(componente.discos.getTamanhoTotal()/1000000000 + "GB");
        componente.discos.getVolumes();
       
        
        if (componente.discos.getTamanhoTotal() < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Disco com baixo armazenamento. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "C";
        } else if (componente.discos.getTamanhoTotal() < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "B";
        } else if (componente.discos.getTamanhoTotal() < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Disco com bom armazenamento. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "A";
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! Disco com excelente armazenamento. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "S";
        }

        controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, componente.discos.getTamanhoTotal(), data, 1, 1);
    }

}
