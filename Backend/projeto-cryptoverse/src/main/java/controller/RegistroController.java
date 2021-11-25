package controller;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoBD;
import services.ComponentesServices;
import services.LeituraService;
import services.ServiceTeste;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegistroController {
    Looca looca = new Looca();
    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate controller = new JdbcTemplate(configBanco.getDataSource());
    
    LeituraService leitura = new LeituraService();
    ComponentesServices componente = new ComponentesServices();

    public void addLeituraRam(){
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorRam = componente.tamanhoUsadoRam();
        Double totalRam = componente.tamanhoTotalRam();
        leitura.addLeituraRam(dataAtual, valorRam, totalRam);
        
        }
        
        public void addLeituraCpu(){
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorCpu = componente.getCpuUsoPorc();
        leitura.addLeituraCpu(dataAtual, valorCpu);
        
        }
    
        public void addLeituraDisco(){
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorDisco = componente.getDiscoPorc();
        leitura.addLeituraDisco(dataAtual, valorDisco);
        
        }
        
        public static void main(String[] args) {
        RegistroController enviaDados = new RegistroController();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                enviaDados.addLeituraRam();
                enviaDados.addLeituraCpu();
                enviaDados.addLeituraDisco();
            }
        }, 0, 10000);
            
    }
    
    }
   