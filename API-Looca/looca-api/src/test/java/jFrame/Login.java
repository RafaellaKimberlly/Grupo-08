package jFrame;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Reyel Soares
 */
public class Login {
    
       private String email;
   private String senha;

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "TesteLogin{" + "email=" + email + ", senha=" + senha + '}';
    }
   
    
    

void login(String email, String senha){
    ConfigBanco config = new ConfigBanco();
       JdbcTemplate template = new JdbcTemplate(config.getBancoDeDados());
    try {
        
        System.out.println(template.queryForList("SELECT * FROM usuario where email = ? and senha = ?", email, senha));
    } catch (DataAccessException e) {
        e.getMessage();
	System.out.println("Usuário ou senha inválido");
    }
    
    
}




}
