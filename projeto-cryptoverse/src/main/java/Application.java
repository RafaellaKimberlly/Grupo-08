
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import controller.RegistroController;
import services.ServiceTeste;

import java.util.List;


public class Application {

    public static void main(String[] args) {

        RegistroController controller = new RegistroController();

        Memoria ram = new Memoria();
        Processador processador = new Processador();
        DiscosGroup disco = new DiscosGroup();
        Looca looca = new Looca();
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();

//        System.out.println(ram.getTotal());
//        System.out.println(ram.getEmUso());
//        System.out.println(ram.getDisponivel());
//
//         System.out.println(processador.getNome());
//         System.out.println(processador.getIdentificador());
//         System.out.println(processador.getFrequencia());
//         System.out.println(processador.getUso());
//
//         System.out.println(disco.getDiscos());
//         System.out.println(disco.getQuantidadeDeDiscos());
//         System.out.println(disco.getVolumes());
//         System.out.println(disco.getQuantidadeDeVolumes());
//         System.out.println(disco.getTamanhoTotal());


//         System.out.println(ram.toString() + "\n");
//         System.out.println(processador.toString() + "\n");
//         System.out.println(grupoDeDiscos.getDiscos());


//        Long media = service.media(ram.getTotal(), ram.getDisponivel());
//
//        System.out.println(media);


    }

}
