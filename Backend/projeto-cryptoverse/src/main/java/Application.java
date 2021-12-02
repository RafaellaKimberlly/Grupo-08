
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import controller.RegistroController;
import java.io.IOException;
import java.util.Date;
import services.ComponentesServices;
import services.LeituraService;
import services.ServiceTeste;
import java.util.List;
import org.json.JSONObject;
import services.Slack;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {

//        Creio que não precise desse código para funcionar, porém, vou deixar comentado caso seja útil
//        JSONObject json = new JSONObject();
//        json.put("text", "Slack funcionando time. Faz o Pix pro pai.");
//        Slack.sendMessage(json);
        Slack slack = new Slack();
        JSONObject json = new JSONObject();
        Date data = new Date();

        RegistroController controller = new RegistroController();

        LeituraService leitura = new LeituraService();
        ComponentesServices componentes = new ComponentesServices();

        Memoria ram = new Memoria();
        Processador processador = new Processador();
        DiscosGroup disco = new DiscosGroup();
        Looca looca = new Looca();
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();

       // leitura.rodarTempoEmTempo();
        
        System.out.println("---------------------");
        leitura.addLeituraRam();
        System.out.println("----------------------");
        //leitura.addLeituraCpu();
        System.out.println("----------------------");
        //leitura.addLeituraDisco();
        System.out.println("----------------------");

    }

}
