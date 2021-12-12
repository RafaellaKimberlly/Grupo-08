package cryptoverse;

import controller.RegistroController;
import database.ConexaoBD;
import java.io.IOException;
import java.sql.SQLException;
import services.ComponentesServices;
import services.LeituraService;
import org.springframework.jdbc.core.JdbcTemplate;

public class Application {

    ConexaoBD configBanco = new ConexaoBD();

    JdbcTemplate assistenteBd = new JdbcTemplate(configBanco.getDataSource());

    LeituraService leitura = new LeituraService();

    ComponentesServices componente = new ComponentesServices();


    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        AcoesCli sistema = new AcoesCli();
        
        System.out.println("\"  ___  ____  _  _  ____  ____  __     _  _  ____  ____  ____  ____ \"");
        System.out.println("\" / __)(  _ \\( \\/ )(  _ \\(_  _)/  \\   / )( \\(  __)(  _ \\/ ___)(  __)\"");
        System.out.println("\"( (__  )   / )  /  ) __/  )( (  O )  \\ \\/ / ) _)  )   /\\___ \\ ) _) \"");
        System.out.println("\" \\___)(__\\_)(__/  (__)   (__) \\__/    \\__/ (____)(__\\_)(____/(____)\"");

        System.out.println("\n\nFavor Inserir seu Login\n");

        sistema.loginCli();

    }

}
