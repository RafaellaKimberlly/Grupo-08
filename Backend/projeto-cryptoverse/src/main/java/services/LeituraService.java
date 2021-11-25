package services;

import database.ConexaoBD;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeituraService {
    
    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate controller = new JdbcTemplate(configBanco.getDataSource());
    
    ComponentesServices componente = new ComponentesServices();
    Timer timer = new Timer();


    public void rodarTempoEmTempo(){

        Integer intervalo = ( 1000 * 30 );

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

    public void addLeituraRam(String data, Double valorRam, Double totalRam) {
        System.out.println(data);
        System.out.println("Enviando dados da Ram");
        String nvAlerta = "";
        if(((valorRam * 100) / totalRam) < 30){
        System.out.println("Estado Critico!");
        nvAlerta = "C";
        } else if(((valorRam * 100) / totalRam) < 60){
        System.out.println("Estado de Atenção!!!");
        nvAlerta = "B";
        }else if(((valorRam * 100) / totalRam) < 90){
        System.out.println("Em bom estado!");
        nvAlerta = "A";
        } else{
            System.out.println("Em perfeito estado!!!");
            nvAlerta = "S";
        }
        controller.update("insert into tb_leitura (valor, nvAlerta, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)" ,
            valorRam, nvAlerta, data, 10, 10001);
    }
        public void addLeituraCpu(String data, Double valorCpu){
            System.out.println(data);
            System.out.println("Enviando dados de CPU");
            String nvAlerta = "";
            if(valorCpu < 30){
                System.out.println("Estado Critico!");
                nvAlerta = "C";
            }else if(valorCpu < 60){
                System.out.println("Estado de Atenção!!!");
                nvAlerta = "B";
            }else if(valorCpu < 90){
                System.out.println("Em bom estado!");
                nvAlerta = "A";
            }else{
                System.out.println("Em perfeito estado");
                nvAlerta = "S";
            }
            controller.update("insert into tb_leitura (valor, nvAlerta, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)" ,
            valorCpu, nvAlerta, data, 10, 10001);
        }
        
        public void addLeituraDisco(String data, Double valorDisco){
        System.out.println(data);
            System.out.println("Enviando dados de Disco");
            String nvAlerta = "";
            
            controller.update("insert into tb_leitura (valor, nvAlerta, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)" ,
            valorDisco, nvAlerta, data, 10, 10001);
        }
    
    

}
