package log;

import com.github.britooo.looca.api.core.Looca;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {

    public static void main(String[] args) throws IOException {

        try {
            Looca looca = new Looca();

            DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            File logFile = new File("C:\\Users\\gvsilva1\\Desktop\\projeto-cryptoverse\\registros\\RegistroDeErros.txt");

            FileWriter logging = new FileWriter(logFile, true);

            BufferedWriter bufferedRecLog = new BufferedWriter(logging);

            bufferedRecLog.write("\n--------------------ERROR--------------------");

            bufferedRecLog.newLine();

            bufferedRecLog.write("\nData e hora do erro: " + dataFormatada.format(LocalDateTime.now()) + "\n");

            bufferedRecLog.newLine();

            bufferedRecLog.write(String.valueOf(looca.getSistema()) + "\n");

            bufferedRecLog.flush();

            bufferedRecLog.close();
            
        } catch (IOException err) {
            err.printStackTrace();
            System.out.println("Erro ao registrar informações");
        }
    }
}
