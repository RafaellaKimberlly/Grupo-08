package services;

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

    public void rodarTempoEmTempo() {

        Integer intervalo = (1000 * 30);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Este é o uso do seu processador\n");
                System.out.println(componente.usoProcessador());
                System.out.println("Essa é a frequencia do seu processador\n");
                System.out.println(componente.frequenciaProcessador());
                System.out.println("Essa é a porcentagem de uso do seu processador\n");
                System.out.println(componente.getCpuUsoPorc());
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, intervalo);

    }

    public void addLeituraRam(String data, Double valorRam, Double totalRam) throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados da Ram");
        String nvAlerta = "";
        if (((valorRam * 100) / totalRam) < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Memória Ram com baixo desempenho.");
            Slack.sendMessage(json);
            nvAlerta = "C";
        } else if (((valorRam * 100) / totalRam) < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua memória ram está perdendo desempenho.");
            Slack.sendMessage(json);
            nvAlerta = "B";
        } else if (((valorRam * 100) / totalRam) < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Memória Ram em ótimo desempenho.");
            Slack.sendMessage(json);
            nvAlerta = "A";
        } else {
            System.out.println("Em perfeito estado!!!");
            json.put("text", "Componente em ótimo estado! Memória Ram em seu desempenho máximo.");
            Slack.sendMessage(json);
            nvAlerta = "S";
        }
        controller.update("insert into tb_leitura (valor, nvAlerta, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                valorRam, nvAlerta, data, 10, 10001);
    }

    public void addLeituraCpu(String data, Double valorCpu) throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de CPU");
        String nvAlerta = "";
        if (valorCpu < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! CPU em baixo desempenho.");
            Slack.sendMessage(json);
            nvAlerta = "C";
        } else if (valorCpu < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua CPU está perdendo desempenho.");
            Slack.sendMessage(json);
            nvAlerta = "B";
        } else if (valorCpu < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! CPU em ótimo desempenho.");
            Slack.sendMessage(json);
            nvAlerta = "A";
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! CPU em seu desempenho máximo.");
            Slack.sendMessage(json);
            nvAlerta = "S";
        }
        controller.update("insert into tb_leitura (valor, nvAlerta, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                valorCpu, nvAlerta, data, 10, 10001);
    }

    public void addLeituraDisco(String data, Double valorDisco) throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de Disco");
        String nvAlerta = "";
        if (valorDisco < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Disco com baixo armazenamento.");
            Slack.sendMessage(json);
            nvAlerta = "C";
        } else if (valorDisco < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Seu disco está abaixo do armazenamento recomendado");
            Slack.sendMessage(json);
            nvAlerta = "B";
        } else if (valorDisco < 90) {
            System.out.println("Em bom estado!");
            json.put("text", "Componente em bom estado! Disco com bom armazenamento.");
            Slack.sendMessage(json);
            nvAlerta = "A";
        } else {
            System.out.println("Em perfeito estado");
            json.put("text", "Componente em ótimo estado! Disco com excelente armazenamento.");
            Slack.sendMessage(json);
            nvAlerta = "S";
        }

        controller.update("insert into tb_leitura (valor, nvAlerta, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                valorDisco, nvAlerta, data, 10, 10001);
    }

}
