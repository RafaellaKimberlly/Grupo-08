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

    public void addLeituraRam() throws IOException, InterruptedException {
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorRam = componente.tamanhoUsadoRam();
        Double totalRam = componente.tamanhoTotalRam();
        leitura.addLeituraRam();

    }

    public void addLeituraCpu() throws IOException, InterruptedException {
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Double valorCpu = componente.getCpuUsoPorc();
        leitura.addLeituraCpu();

    }

    public void addLeituraDisco() throws IOException, InterruptedException {
        String dataAtual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        leitura.addLeituraDisco();
    }

    public void tempo() {
        RegistroController enviaDados = new RegistroController();

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    enviaDados.addLeituraRam();
                } catch (IOException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    enviaDados.addLeituraCpu();
                } catch (IOException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    enviaDados.addLeituraDisco();
                } catch (IOException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, 10000);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        RegistroController enviaDados = new RegistroController();
//
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//
//            @Override
//            public void run() {
//                try {
//                    enviaDados.addLeituraRam();
//                } catch (IOException ex) {
//                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                try {
//                    enviaDados.addLeituraCpu();
//                } catch (IOException ex) {
//                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                try {
//                    enviaDados.addLeituraDisco();
//                } catch (IOException ex) {
//                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }, 0, 10000);
    }

}
