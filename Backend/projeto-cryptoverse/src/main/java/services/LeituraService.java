package services;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoBD;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.LogRecorder;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeituraService {

    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate controller = new JdbcTemplate(configBanco.getDataSource());
    Slack slack = new Slack();
    JSONObject json = new JSONObject();
    ComponentesServices componente = new ComponentesServices();
    Timer timer = new Timer();

    LogRecorder logs = new LogRecorder();

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
        slack.sendMessage(json);
        String nvAlerta = "";

        long ramTotalGb = (componente.ram.getTotal() / 1000000000);
        long ramEmUsoGb = (componente.ram.getEmUso() / 1000000000);

        if ((((ramEmUsoGb * 100) / ramTotalGb) / 10) < (componente.ram.getTotal() / 2)) {
            System.out.printf("Estado Critico!");
            json.put("text", "Componente critico! Memória Ram com baixo desempenho. \n"
                    + Math.round((((ramEmUsoGb * 100) / ramTotalGb) / 10)) + "GB");
            slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" + "\nComponente critico! Memória Ram com baixo desempenho. \n");
        } else if ((((ramEmUsoGb * 100) / ramTotalGb) / 10) >= (componente.ram.getTotal() / 2)) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua memória ram está perdendo desempenho. \n"
                    + Math.round((((ramEmUsoGb * 100) / ramTotalGb) / 10)) + "GB");
            slack.sendMessage(json);
            nvAlerta = "B";
            logs.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" +  "Atenção!!! Sua memória ram está perdendo desempenho. \n");
        } else if ((((ramEmUsoGb * 100) / ramTotalGb) / 10) == (componente.ram.getTotal() / 2)) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Memória Ram em ótimo desempenho. \n"
                    + Math.round((((ramEmUsoGb * 100) / ramTotalGb) / 10)) + "GB");
            slack.sendMessage(json);
            nvAlerta = "A";
            logs.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" +  "Componente em bom estado! Memória Ram em ótimo desempenho. \n");
        } else {
            System.out.println("Em perfeito estado!!!");
            json.put("text", "Componente em ótimo estado! Memória Ram em seu desempenho máximo. \n"
                    + Math.round((((ramEmUsoGb * 100) / ramTotalGb) / 10)) + "GB");
            slack.sendMessage(json);
            nvAlerta = "S";
            logs.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" +  "Componente em ótimo estado! Memória Ram em seu desempenho máximo. \n");
        }

//        return String.format("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values ('%s',%x,getDate(),%d,%d)",
//                nvAlerta, ((((ramEmUsoGb * 100) / ramTotalGb) / 10)), 2, 66);
        controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, ((((ramEmUsoGb * 100) / ramTotalGb) / 10)), data, 2, 66);
    }

    public void addLeituraCpu() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de CPU");
        json.put("text", "Enviando dados de CPU.");
        slack.sendMessage(json);
        String nvAlerta = "";

        long usoCpuPorc = Math.round(componente.processador.getUso());
        System.out.println(usoCpuPorc + "%");

        if (usoCpuPorc < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! CPU em baixo desempenho. \n"
                    + usoCpuPorc + "%");
            slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "Componente critico! CPU em baixo desempenho. \n");
        } else if (usoCpuPorc < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua CPU está perdendo desempenho. \n"
                    + usoCpuPorc + "%");
            slack.sendMessage(json);
            nvAlerta = "B";
            logs.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "Atenção!!! Sua CPU está perdendo desempenho. \n");
        } else if (usoCpuPorc < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! CPU em ótimo desempenho. \n"
                    + usoCpuPorc + "%");
            slack.sendMessage(json);
            nvAlerta = "A";
            logs.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "Componente em bom estado! CPU em ótimo desempenho. \n");
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! CPU em seu desempenho máximo. \n"
                    + usoCpuPorc + "%");
            slack.sendMessage(json);
            nvAlerta = "S";
            logs.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "Componente em ótimo estado! CPU em seu desempenho máximo. \n");
        }

        controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, usoCpuPorc, data, 1, 67);

//        return String.format("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values ('%s',%x,getDate(),%d,%d)",
//                nvAlerta, usoCpuPorc, 1, 67);
    }

    public void addLeituraDisco() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de Disco");
        json.put("text", "Enviando dados de Disco.");
        Slack.sendMessage(json);
        String nvAlerta = "";

        long totalDisco = Math.round(componente.tamanhoTotalDisco());

        System.out.println(componente.discos.getTamanhoTotal() / 1000000000 + "GB");
        componente.discos.getVolumes();

        if (componente.discos.getTamanhoTotal() / 1000000000 < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Disco com baixo armazenamento. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
            Slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("Componente critico! Disco com baixo armazenamento. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
        } else if (componente.discos.getTamanhoTotal() / 1000000000 < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
            Slack.sendMessage(json);
            nvAlerta = "B";
            logs.registrarHistorico("Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
        } else if (componente.discos.getTamanhoTotal() / 1000000000 < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Disco com bom armazenamento. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
            Slack.sendMessage(json);
            nvAlerta = "A";
            logs.registrarHistorico("Componente em bom estado! Disco com bom armazenamento. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! Disco com excelente armazenamento. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
            Slack.sendMessage(json);
            nvAlerta = "S";
            logs.registrarHistorico("Componente em ótimo estado! Disco com excelente armazenamento. \n"
                    + totalDisco + "GB ocupados de " + ((componente.discos.getTamanhoTotal() / 10) / 100000000) + "GB");
        }

        controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, totalDisco, data, 2, 3);

//        return String.format("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values ('%s',%x,getDate(),%d,%d)",
//                nvAlerta, totalDisco, 2, 3);
    }

//    TimerTask timeTeste = new TimerTask() {
//        @Override
//        public void run() {
//            try {
//                controller.update(addLeituraRam());
//                controller.update(addLeituraCpu());
//                controller.update(addLeituraDisco());
//            } catch (IOException ex) {
//                Logger.getLogger(LeituraService.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(LeituraService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//    };
//    public void inserindoDados() {
//        timer.scheduleAtFixedRate(timeTeste, 0, 4000);
//    }
}