/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoverse;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import controller.RegistroController;
import database.ConexaoBD;
import database.ConnectorBD;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import org.json.JSONObject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import services.ComponentesServices;
import services.Slack;

/**
 *
 * @author Fabricio
 */
public class AcoesCli {
    
    Looca looca = new Looca();
    
    Date data = new Date();
    
    Slack slack = new Slack();
    JSONObject json = new JSONObject();
    
    ConexaoBD configBanco = new ConexaoBD();
    JdbcTemplate assistenteBd = new JdbcTemplate(configBanco.getDataSource());

    RegistroController rgController = new RegistroController();
        
    public String hostname() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
    
    
    private Connection conexao;
    
    public AcoesCli() throws SQLException {
        ConnectorBD conexaoBd = new ConnectorBD();
        this.conexao = conexaoBd.getDataSource();
        
    }

    public String loginCli() throws UnknownHostException, SQLException, IOException {

        Scanner leitor = new Scanner(System.in);
        
        System.out.println("\nEntre com o Usuário:");

        String user = leitor.nextLine();

        System.out.println("\nEntre com a Senha:");

        String pass = leitor.nextLine();

        List<Usuario> usuario = assistenteBd.query(String.format("Select * from tb_usuario where email = '%s' and senha = '%s';", 
                                                                  user, pass), new BeanPropertyRowMapper(Usuario.class)
        );

        if (usuario.size() >= 1) {
            String message = "\nLogin realizado com sucesso\n\nBem-vindo!\n\n";
            try {
                cadastrarMaquina(user, pass);
            } catch (InterruptedException ex) {
                Logger.getLogger(AcoesCli.class.getName()).log(Level.SEVERE, null, ex);
            }
            return message;
        } else {
            System.out.println("\n!!Usuario ou senha inválidos!!\n\nPor Favor Entre com um Usuário/Senha Válido\n");
            return loginCli();
        }

    }
    
    public Integer recuperarFkUsuario(String user, String pass) throws UnknownHostException {
        PreparedStatement stm = null;
        try {
            String query = "select * from tb_usuario where email = '" + user + "'" + " and " + "senha = " + "'" + pass + "'";
            stm = conexao.prepareStatement(query);
            stm.executeQuery().next();
            ResultSet rs = stm.getResultSet();
            Integer fkUsuario = rs.getInt("idUsuario");
            return fkUsuario;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }
    
    public Integer recuperarFkMaquina(String user, String pass) throws UnknownHostException {
        System.out.println("\n------------------------------");
        System.out.println("\nRecuperando Dados da Máquina!");
        PreparedStatement stm = null;
        try {
            String query = "select * from tb_maquina where fkUsuario = " + this.recuperarFkUsuario(user, pass) + " and hostname = '" + this.hostname() + "';";
            stm = conexao.prepareStatement(query);
            stm.executeQuery().next();
            ResultSet rs = stm.getResultSet();
            Integer fkMaquina = rs.getInt("idMaquina");
            System.out.println("\nEsse é o id da Maq.: " + fkMaquina + "\n");
            System.out.println("------------------------------\n");
            return fkMaquina.intValue();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            System.out.println("\nMáquina não encontrada!\n");
            System.out.println("------------------------------\n");
            return null;
        }
    }
    
    public void cadastrarMaquina (String user, String pass) throws UnknownHostException, SQLException, IOException, InterruptedException {
        //São Paulo está mocado como a Localização padrão!!
        String lat = "-23.550520";
        String longi = "-46.633308";
                if (recuperarFkMaquina(user, pass) == null) {
            try {
                System.out.println("\n------------------------------");
                System.out.println("\nCadastrando nova Máquina\n");
                String query = "insert into tb_maquina ( fkUsuario, hostname, numSerie, tipo_processador, lat, lng ) values (?,?,?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setInt(
                        1, recuperarFkUsuario(user, pass));
                stn.setString(
                        2, this.hostname());
                stn.setInt(
                        3, InetAddress.getLocalHost().hashCode());
                stn.setString(
                        4, looca.getProcessador().getNome());
                stn.setString(
                        5, lat);
                stn.setString(
                        6, longi);                
                stn.execute();
                System.out.println("\nMáquina Cadastrada!");
                System.out.println("\nIniciando check de Componentes\n");
                System.out.println("------------------------------\n");
                cadastrarComponente(user, pass);
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            
        } else {
            System.out.println("\n------------------------------");
            System.out.println("\nMáquina com registro OK!");
            System.out.println("\nIniciando check de Componentes\n");
            System.out.println("------------------------------\n");
            cadastrarComponente(user, pass);
    }
    }
    
    public void cadastrarComponente(String user, String pass) throws SQLException, UnknownHostException, IOException, InterruptedException {
        
        String login = user;
        String senha = pass;
        
        Scanner leitorComp = new Scanner(System.in);
        
          if(checkCardCpu(user, pass) != null){
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status CPU");
            System.out.println("\nCPU já cadastrada e Ativa!");
            System.out.println("------------------------------\n");
            
        } else {
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status CPU");
            System.out.println("\nCPU não cadastrada ou Inativa!");
            Integer cpuFk = 1;
              System.out.println("\nCadastrando a CPU");
                System.out.println("\nFavor Digitar um Nome a CPU: ");
                String nomeCpu = leitorComp.nextLine();
                String query = "insert into tb_maquina_componente ( descComponente, fkMaquina, fkComponente, mcStatus) values (?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setString(
                        1, nomeCpu);
                stn.setInt(
                        2, this.recuperarFkMaquina(user, pass));
                stn.setInt(
                        3, cpuFk);
                stn.setString(
                        4, "Ativo");                
                stn.execute();
                System.out.println("\nCPU: " + nomeCpu + " cadastrada com sucesso!\n");
                System.out.println("------------------------------\n");
        }
          if(checkCardRam(user, pass) != null){
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status RAM");
            System.out.println("\nRAM já cadastrada e Ativa!");
            System.out.println("\nIniciando monitoramento!");
            System.out.println("------------------------------\n");
        } else {
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status RAM");
            System.out.println("\nRAM não cadastrada ou Inativa!");
            Integer ramFk = 2;
              System.out.println("\nCadastrando a RAM");
                System.out.println("\nFavor Digitar um Nome a RAM: ");
                String nomeRam = leitorComp.nextLine();
                String query = "insert into tb_maquina_componente ( descComponente, fkMaquina, fkComponente, mcStatus) values (?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setString(
                        1, nomeRam);
                stn.setInt(
                        2, this.recuperarFkMaquina(user, pass));
                stn.setInt(
                        3, ramFk);
                stn.setString(
                        4, "Ativo");                   
                stn.execute();
                System.out.println("\nRAM: " + nomeRam + " cadastrada com sucesso!\n");
                System.out.println("------------------------------\n");
        }    
          if(checkCardDisco(user, pass) != null){
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status Disco");
            System.out.println("Disco já cadastrado e Ativo!");
            System.out.println("Iniciando monitoramento!");
            System.out.println("------------------------------\n");
        } else {
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status Disco");
            System.out.println("\nDisco não cadastrado ou Inativo");
            Integer ramFk = 3;
              System.out.println("\nCadastrando o Disco");
                System.out.println("\nFavor Digitar um Nome ao Disco: ");
                String nomeDisco = leitorComp.nextLine();
                String query = "insert into tb_maquina_componente ( descComponente, fkMaquina, fkComponente, mcStatus) values (?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setString(
                        1, nomeDisco);
                stn.setInt(
                        2, this.recuperarFkMaquina(user, pass));
                stn.setInt(
                        3, ramFk);
                stn.setString(
                        4, "Ativo");                   
                stn.execute();
                System.out.println("\nDisco: " + nomeDisco + " cadastrada com sucesso!\n");
                System.out.println("------------------------------\n");
        }
          
          
          Integer cpuFkAjustada = checkCardCpu(login, senha);
          Integer ramFkAjustada = checkCardRam(login, senha);
          Integer discoFkAjustada = checkCardDisco(login, senha);
          
          System.out.println("\nIniciando monitoramento\n!");
          addLeituraRam(login, senha, ramFkAjustada);
    }
        
    
    public Integer checkCardRam(String user, String pass) throws UnknownHostException, SQLException {
        PreparedStatement stm = null;
        try {
        String query = "select * from tb_maquina_componente where fkComponente = 2 and fkMaquina = " + recuperarFkMaquina(user, pass) + " and mcStatus = 'Ativo' order by idMaquinaComponente desc;";
        stm = conexao.prepareStatement(query);
        stm.executeQuery().next();
        ResultSet rs = stm.getResultSet();
        Integer idMaquinaComponenteRam = rs.getInt("idMaquinaComponente");
        return idMaquinaComponenteRam;
        }  catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
    }
    
    public Integer checkCardCpu(String user, String pass) throws UnknownHostException, SQLException {
        PreparedStatement stm = null;
        try {
        String query = "select * from tb_maquina_componente where fkComponente = 1 and fkMaquina = " + recuperarFkMaquina(user, pass) + " and mcStatus = 'Ativo' order by idMaquinaComponente desc;";
        stm = conexao.prepareStatement(query);
        stm.executeQuery().next();
        ResultSet rs = stm.getResultSet();
        Integer idMaquinaComponenteCpu = rs.getInt("idMaquinaComponente");
        return idMaquinaComponenteCpu;
        }  catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
    }

    public Integer checkCardDisco(String user, String pass) throws UnknownHostException, SQLException {
        PreparedStatement stm = null;
        try {
        String query = "select * from tb_maquina_componente where fkComponente = 3 and fkMaquina = " + recuperarFkMaquina(user, pass) + " and mcStatus = 'Ativo' order by idMaquinaComponente desc;";
        stm = conexao.prepareStatement(query);
        stm.executeQuery().next();
        ResultSet rs = stm.getResultSet();
        Integer idMaquinaComponenteDisco = rs.getInt("idMaquinaComponente");
        return idMaquinaComponenteDisco;
        }  catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
    } 
    
    public void testeTempo(String user, String pass, Integer ramFkAjustada){
        
        String usuario = user;
        String senha = pass;
        Integer ramFk = ramFkAjustada;

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    try {
                        addLeituraRam(usuario, senha, ramFk);
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(AcoesCli.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(AcoesCli.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }, 0, 10000);
    }

    public void leiturasRamCli() throws IOException, InterruptedException {
        rgController.addLeituraCpu();
        rgController.addLeituraRam();
        rgController.addLeituraDisco();
    }
    
    
        public void addLeituraRam(String user, String pass, Integer ramFk) throws IOException, InterruptedException, UnknownHostException, SQLException {
        System.out.println("\n------------------------------\n");
        System.out.println(data);
        System.out.println("Enviando dados da Ram");
        json.put("text", "Enviando dados de RAM.");
        slack.sendMessage(json);
        String nvAlerta = "";

        long ramTotalGb = (looca.getMemoria().getTotal() / 1000000000);
        long ramEmUsoGb = (looca.getMemoria().getEmUso()/ 1000000000);
        long porcentagemRam = ((ramTotalGb * 100) / ramEmUsoGb);

        if (porcentagemRam < 30) {
            System.out.printf("Estado Critico!");
            json.put("text", "Componente critico! Memória RAM com baixo desempenho. \n"
                    + Math.round((((ramEmUsoGb * 100) / ramTotalGb) / 10)) + "GB");
            slack.sendMessage(json);
            nvAlerta = "C";
        } else if (porcentagemRam < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua memória RAM está perdendo desempenho. \n"
                    + Math.round((((ramEmUsoGb * 100) / ramTotalGb) / 10)) + "GB");
            slack.sendMessage(json);
            nvAlerta = "B";
        } else if (porcentagemRam < 90) {
            System.out.println("Em bom estado!");
            nvAlerta = "A";
        } else {
            System.out.println("Em perfeito estado!!!");
            nvAlerta = "S";
        }
        System.out.println("\n------------------------------\n");
        assistenteBd.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, porcentagemRam, data, 2, ramFk);
    }
    
    
    
}
