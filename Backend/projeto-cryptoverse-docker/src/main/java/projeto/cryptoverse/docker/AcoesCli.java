package projeto.cryptoverse.docker;


import controller.RegistroController;
import database.ConexaoBD;
import java.util.List;
import java.util.Scanner;
import model.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fabricio
 */
public class AcoesCli {

    ConexaoBD configBanco = new ConexaoBD();

    JdbcTemplate assistenteBd = new JdbcTemplate(configBanco.getDataSource());
    
    RegistroController rgController = new RegistroController();

    public String loginCli() {

        Scanner leitor = new Scanner(System.in);

        System.out.println("Entre com o Usuário:");

        String user = leitor.nextLine();

        System.out.println("Entre com a Senha:");

        String pass = leitor.nextLine();

        List<Usuario> usuario = assistenteBd.query(String.format("Select * from tb_usuario where email = '%s' and senha = '%s';", 
                                                                  user, pass), new BeanPropertyRowMapper(Usuario.class)
        );

        if (usuario.size() >= 1) {
            String message = "\nBem-vindo!\nLogin realizado com sucesso\n";
            return message;
        } else {
            System.out.println("\n!!Usuario ou senha inválidos!!\n\nPor Favor Entre com um Usuário Válido\n");
            return loginCli();
        }
        
    }
    
    public void leiturasRamCli() {
        rgController.addLeituraRam();
    }
}
