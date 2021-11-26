
import controller.RegistroController;
import database.ConexaoBD;
import org.springframework.jdbc.core.JdbcTemplate;
import services.ComponentesServices;
import services.LeituraService;

public class Application {

    ConexaoBD configBanco = new ConexaoBD();

    JdbcTemplate assistenteBd = new JdbcTemplate(configBanco.getDataSource());

    LeituraService leitura = new LeituraService();
    
    ComponentesServices componente = new ComponentesServices();
    
    RegistroController teste = new RegistroController();

    public static void main(String[] args) {

        AcoesCli sistema = new AcoesCli();

        System.out.println("\nFavor Insirir seu Login\n");

        sistema.loginCli();
        
        sistema.leiturasRamCli();

    }
    
    
    
}
