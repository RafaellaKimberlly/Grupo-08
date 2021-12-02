package services;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoBD;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import log.LogRecorder;
import org.h2.jdbc.JdbcException;
import org.h2.jdbc.JdbcSQLException;
import org.h2.message.DbException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeituraService {

    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate controller = new JdbcTemplate(configBanco.getDataSource());
    Slack slack = new Slack();
    JSONObject json = new JSONObject();
    LogRecorder logging = new LogRecorder();
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
        if (componente.ram.getEmUso() < 30) {
            System.out.printf("Estado Critico!");
            json.put("text", "Componente critico! Memória Ram com baixo desempenho. \n"
                    + Math.round(componente.ram.getEmUso() * 100) / 100 + "GB");
            Slack.sendMessage(json);
            nvAlerta = "C";
            logging.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" + "\nComponente em estado critico, memória Ram com baixo desempenho. \n");
        } else if (componente.ram.getEmUso() < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua memória ram está perdendo desempenho. \n"
                    + Math.round(componente.ram.getEmUso() * 100) / 100 + "GB");
            Slack.sendMessage(json);
            nvAlerta = "B";
            logging.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" + "\nAtenção!!! Sua memória ram está perdendo desempenho. \n");
        } else if (componente.ram.getEmUso() < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Memória Ram em ótimo desempenho. \n"
                    + Math.round(componente.ram.getEmUso() * 100) / 100 + "GB");
            Slack.sendMessage(json);
            nvAlerta = "A";
            logging.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" + "\nComponente em bom estado! Memória Ram em ótimo desempenho. \n");
        } else {
            System.out.println("Em perfeito estado!!!");
            json.put("text", "Componente em ótimo estado! Memória Ram em seu desempenho máximo. \n"
                    + Math.round(componente.ram.getEmUso() * 100) / 100 + "GB");
            Slack.sendMessage(json);
            nvAlerta = "S";
            logging.registrarHistorico("\nRAM\n" + componente.looca.getMemoria() + "\n" + "\nComponente em ótimo estado! Memória Ram em seu desempenho máximo. \n");
        }

        try {
            controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                    nvAlerta, componente.ram.getEmUso(), data, 1, 1);
        } catch (Exception e) {
            logging.registrarLog(e);
        }

    }

    public void addLeituraCpu() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de CPU");
        json.put("text", "Enviando dados de CPU.");
        Slack.sendMessage(json);
        String nvAlerta = "";
        if (componente.processador.getUso() < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! CPU em baixo desempenho. \n"
                    + Math.round(componente.processador.getUso() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "C";
            logging.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "\nComponente critico! CPU em baixo desempenho. \n");
        } else if (componente.processador.getUso() < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua CPU está perdendo desempenho. \n"
                    + Math.round(componente.processador.getUso() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "B";
            logging.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "\nAtenção!!! Sua CPU está perdendo desempenho. \n");
        } else if (componente.processador.getUso() < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! CPU em ótimo desempenho. \n"
                    + Math.round(componente.processador.getUso() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "A";
            logging.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "\nComponente em bom estado! CPU em ótimo desempenho. \n");
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! CPU em seu desempenho máximo. \n"
                    + Math.round(componente.processador.getUso() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "S";
            logging.registrarHistorico("\nProcessador \n" + componente.looca.getProcessador() + "\n" + "\nComponente em ótimo estado! CPU em seu desempenho máximo. \n");
        }
        try {
            controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                    nvAlerta, componente.processador.getUso(), data, 1, 1);
        } catch (Exception e) {
            logging.registrarLog(e);
        }

    }

    public void addLeituraDisco() throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de Disco");
        json.put("text", "Enviando dados de Disco.");
        Slack.sendMessage(json);
        String nvAlerta = "";
        if (componente.discos.getTamanhoTotal() < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Disco com baixo armazenamento. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "C";
            logging.registrarHistorico("\nComponente em estado critico! Disco com baixo capacidade de armazenamento. \n");
        } else if (componente.discos.getTamanhoTotal() < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "B";
            logging.registrarHistorico("\nAtenção!!! Seu disco está abaixo da capacidade de armazenamento recomendado. \n");
        } else if (componente.discos.getTamanhoTotal() < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Disco com bom armazenamento. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "A";
            logging.registrarHistorico("\nComponente em bom estado! Disco com boa capacidade armazenamento restante. \n");
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! Disco com excelente armazenamento. \n"
                    + Math.round(componente.discos.getTamanhoTotal() * 100) / 100 + "%");
            Slack.sendMessage(json);
            nvAlerta = "S";
            logging.registrarHistorico( "\nComponente em ótimo estado! Disco com excelente capacidade armazenamento restante. \n");
        }

        try {
            controller.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                    nvAlerta, componente.discos.getTamanhoTotal(), data, 1, 1);
        } catch (Exception e) {
            logging.registrarLog(e);
        }


    }

}
