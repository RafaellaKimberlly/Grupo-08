
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import controller.RegistroController;
import services.ComponentesServices;
import services.LeituraService;
import services.ServiceTeste;

import java.util.List;


public class Application {

    public static void main(String[] args) {

        RegistroController controller = new RegistroController();

        LeituraService leitura = new LeituraService();
        ComponentesServices componentes = new ComponentesServices();

        Memoria ram = new Memoria();
        Processador processador = new Processador();
        DiscosGroup disco = new DiscosGroup();
        Looca looca = new Looca();
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();

//        leitura.rodarTempoEmTempo();
        System.out.println(componentes.getDiscoPorc());

    }

}
