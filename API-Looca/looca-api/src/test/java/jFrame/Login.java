package jFrame;

import jFrame.ConfigBanco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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

    public Boolean entrar(String email, String senha) {
    
       ConfigBanco config = new ConfigBanco();
       JdbcTemplate template = new JdbcTemplate(config.getBancoDeDados());
       
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Boolean check = false;
        
        List<Login> login = new ArrayList<>();
        
        try {

            stmt = (PreparedStatement) template.queryForList("SELECT * FROM usuario where email = ? and senha = ?", email, senha);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                check = true;
            }
            
            
        } catch (Exception e) {
        }
        
        return check;
        
    }
    
//    public Boolean entrar(String email, String senha) {
//        ConfigBanco config = new ConfigBanco();
//        JdbcTemplate template = new JdbcTemplate(config.getBancoDeDados());
//        
//        if(email == getEmail() && senha == getSenha()) {
//
//            System.out.println(template.queryForList("SELECT * FROM usuario where email = ? and senha = ?", email, senha));
//            
//            return true;
//        } else {
//            
//            System.out.println("Usuário ou senha inválido");
//
//            return false;
//        }
//        
//    }

}
