package log;

import com.github.britooo.looca.api.core.Looca;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogRecorder {

    public void registrarLog(Exception erro) throws IOException {

        String home = System.getProperty("user.dir");

        File logFile = new File(home + "/registros/RegistroDeErros.txt");

        Looca looca = new Looca();

        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        FileWriter logging = new FileWriter(logFile, true);

        BufferedWriter bufferedRecLog = new BufferedWriter(logging);

        bufferedRecLog.write("\n----------------------------------------");

        bufferedRecLog.newLine();

        bufferedRecLog.write("\nData e hora: " + dataFormatada.format(LocalDateTime.now()) + "\n" );

        bufferedRecLog.newLine();

        bufferedRecLog.write(looca.getSistema() + "\n");

        bufferedRecLog.newLine();

        bufferedRecLog.write(String.valueOf(erro));

        bufferedRecLog.flush();

        bufferedRecLog.close();

    }

    public void registrarHistorico(String historico) throws IOException {

        DateTimeFormatter data = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm");

        String dataCerta = data.format(LocalDateTime.now());

        String dataCorrigida = dataCerta.toString();

        String home = System.getProperty("user.home");

        File registros = new File(home + "/Registros");

        registros.mkdir();

        File logFile = new File( home + "/Registros/" + dataCorrigida + "_log_de_atividades.txt");

        Looca looca = new Looca();

        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        FileWriter logging = new FileWriter(logFile, true);

        BufferedWriter bufferedRecLog = new BufferedWriter(logging);

        bufferedRecLog.write("\n-------------------------------------------------");

        bufferedRecLog.newLine();

        bufferedRecLog.write("\nRegistro de Atividade Sistêmica");

        bufferedRecLog.newLine();

        bufferedRecLog.write("\nData e hora: " + dataFormatada.format(LocalDateTime.now()) + "\n" );

        bufferedRecLog.write(historico);

        bufferedRecLog.newLine();

        bufferedRecLog.flush();

        bufferedRecLog.close();
    }
}
