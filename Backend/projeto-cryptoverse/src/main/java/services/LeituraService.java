package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LeituraService {

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

    public void addLeitura(String data, Double valorRam) {

        try{



        } catch (Exception e){

        }

    }

}
