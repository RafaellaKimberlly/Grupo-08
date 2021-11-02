package services;

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
                System.out.println(componente.getCpuUsoPorc());
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, intervalo);

    }

}
