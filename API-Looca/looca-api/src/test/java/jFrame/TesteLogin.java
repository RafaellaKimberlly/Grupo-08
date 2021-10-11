
package jFrame;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Reyel Soares
 */
public class TesteLogin {
    
public static void main(String[] args) {
        ConfigBanco config = new ConfigBanco();
        JdbcTemplate template = new JdbcTemplate(config.getBancoDeDados());
//        
//        String create = "CREATE TABLE aluno (" +;
//                        "id INT NOT NULL AUTO_INCREMENT," +  
//                        "email VARCHAR(256)," +
//                        "senha VARCHAR(256)," +
//                        "PRIMARY KEY(id));";
//        
//        template.execute(create);

//        template.update("INSERT INTO usuario VALUES (null, 'teste@teste.com', 'teste123')");
//        
//       
//        
//        System.out.println(template.queryForList("SELECT * FROM usuario"));  

        Login login = new Login("teste@teste.com", "teste123");
       
       
        
        
    }
   
   
   
}
