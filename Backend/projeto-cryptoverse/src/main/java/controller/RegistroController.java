package controller;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoBD;
import java.io.IOException;
import services.ComponentesServices;
import services.LeituraService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import services.Slack;

public class RegistroController {
    Looca looca = new Looca();
    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate controller = new JdbcTemplate(configBanco.getDataSource());
    
    LeituraService leitura = new LeituraService();
    ComponentesServices componente = new ComponentesServices();
    Slack slack = new Slack();


    public void addLeituraRam(){
//        String dataAtual = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
//        System.out.println("Coletando Hora atual: " + dataAtual);
        Double valorRam = (componente.tamanhoUsadoRam()/1000000000);
        Double totalRam = (componente.tamanhoTotalRam()/1000000000);
        leitura.addLeituraRam(valorRam, totalRam);
        }
        
        public void addLeituraCpu() throws IOException, InterruptedException{
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorCpu = componente.getCpuUsoPorc();
        leitura.addLeituraCpu(dataAtual, valorCpu);
        
        }
    
        public void addLeituraDisco() throws IOException, InterruptedException{
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorDisco = componente.getDiscoPorc();
        leitura.addLeituraDisco(dataAtual, valorDisco);
        
        }
    
    }
   