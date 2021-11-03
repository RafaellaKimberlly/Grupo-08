package controller;

import services.ComponentesServices;
import services.LeituraService;
import services.ServiceTeste;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroController {

    LeituraService leitura = new LeituraService();
    ComponentesServices componente = new ComponentesServices();

    public void addLeitura(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        Double valorRam = componente.tamanhoUsadoRam();


        leitura.addLeitura(formatter.format(date), valorRam);

    }
}
