package cryptoverse;

import controller.RegistroController;
import database.ConexaoBD;
import java.io.IOException;
import services.ComponentesServices;
import services.LeituraService;
import org.springframework.jdbc.core.JdbcTemplate;

public class Application {

    ConexaoBD configBanco = new ConexaoBD();

    JdbcTemplate assistenteBd = new JdbcTemplate(configBanco.getDataSource());

    LeituraService leitura = new LeituraService();

    ComponentesServices componente = new ComponentesServices();

    RegistroController teste = new RegistroController();

    public static void main(String[] args) throws IOException, InterruptedException {

        AcoesCli sistema = new AcoesCli();

        System.out.println("\nFavor Insirir seu Login\n");

        sistema.loginCli();

        sistema.leiturasRamCli();

    }

}
