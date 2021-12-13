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
import log.LogRecorder;
import model.Usuario;
import org.json.JSONObject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import services.ComponentesServices;
import services.Slack;
import view.TelaSistema2;

/**
 *
 * @author Fabricio
 */
public class AcoesCli {

    Looca looca = new Looca();

    Date data = new Date();

    LogRecorder logs = new LogRecorder();

    ComponentesServices componente = new ComponentesServices();

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

    public String loginCli() throws UnknownHostException, SQLException, IOException, InterruptedException {

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
                menuCli(user, pass);
            return message;
        } else {
            System.out.println("\n!!Usuario ou senha inválidos!!\n\nPor Favor Entre com um Usuário/Senha Válido\n");
            return loginCli();
        }

    }
    
    public  void menuCli(String user, String pass) throws SQLException, IOException, UnknownHostException, InterruptedException {
        System.out.println("\n------------------------------");
        System.out.println("Escolha os próximos passos: ");
        System.out.println("1 - Iniciar Monitoramento");
        System.out.println("2 - Abrir Monitor (Necessário uma GUI)");
        System.out.println("3 - Sair");
        System.out.println("------------------------------\n");
        
        Scanner leitorOpcoesMenu = new Scanner(System.in);
        Integer opcoesMenu = leitorOpcoesMenu.nextInt();
        
        switch (opcoesMenu) {
            case 1:
                cadastrarMaquina(user, pass);
                break;
            case 2:
                new TelaSistema2().setVisible(true);
                cadastrarMaquina(user, pass);
                break;
            case 3:
                System.out.println("\nAté Logo!\n\n");
                System.exit(0);
                break;
            default:
        System.out.println("Digite uma opção valida!!");
        System.out.println("\n1 - Iniciar Monitoramento");
        System.out.println("2 - Abrir Monitor (Necessário uma GUI)");
        System.out.println("3 - Sair");
        System.out.println("------------------------------\n");
                opcoesMenu = leitorOpcoesMenu.nextInt();
                break;
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

    public void cadastrarMaquina(String user, String pass) throws UnknownHostException, SQLException, IOException, InterruptedException {
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

        if (checkCardCpu(user, pass) != null) {
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
        if (checkCardRam(user, pass) != null) {
            System.out.println("\n------------------------------");
            System.out.println("\nChecando Status RAM");
            System.out.println("\nRAM já cadastrada e Ativa!");
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

        System.out.println("\n------------------------------");
        System.out.println("Verificando Quantidade de Discos");

        if (looca.getGrupoDeDiscos().getQuantidadeDeDiscos().intValue() > 1) {
            System.out.println("A Máquina possui: " + looca.getGrupoDeDiscos().getVolumes().size() + " Discos");
            System.out.println("------------------------------\n");

            if (checkCardDiscoUm(user, pass) != null) {
                System.out.println("\n------------------------------");
                System.out.println("\nChecando Status Disco 1");
                System.out.println("Disco 1 já cadastrado e Ativo!");
                System.out.println("------------------------------\n");
            } else {
                System.out.println("\n------------------------------");
                System.out.println("\nChecando Status Disco 1");
                System.out.println("\nDisco 1 não cadastrado ou Inativo");
                Integer discoUmFk = 3;
                System.out.println("\nCadastrando o Disco 1");
                System.out.println("\nFavor Digitar um Nome ao Disco 1: ");
                String nomeDisco = leitorComp.nextLine();
                String query = "insert into tb_maquina_componente ( descComponente, fkMaquina, fkComponente, mcStatus) values (?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setString(
                        1, nomeDisco);
                stn.setInt(
                        2, this.recuperarFkMaquina(user, pass));
                stn.setInt(
                        3, discoUmFk);
                stn.setString(
                        4, "Ativo");
                stn.execute();
                System.out.println("\nDisco: " + nomeDisco + " cadastrada com sucesso!\n");
                System.out.println("------------------------------\n");
            }

            if (checkCardDiscoDois(user, pass) != null) {
                System.out.println("\n------------------------------");
                System.out.println("\nChecando Status Disco 2");
                System.out.println("Disco 2 já cadastrado e Ativo!");
                System.out.println("Iniciando monitoramento!");
                System.out.println("------------------------------\n");
            } else {
                System.out.println("\n------------------------------");
                System.out.println("\nChecando Status Disco 2");
                System.out.println("\nDisco 2 não cadastrado ou Inativo");
                Integer discoDoisFk = 4;
                System.out.println("\nCadastrando o Disco");
                System.out.println("\nFavor Digitar um Nome ao Disco 2: ");
                String nomeDisco = leitorComp.nextLine();
                String query = "insert into tb_maquina_componente ( descComponente, fkMaquina, fkComponente, mcStatus) values (?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setString(
                        1, nomeDisco);
                stn.setInt(
                        2, this.recuperarFkMaquina(user, pass));
                stn.setInt(
                        3, discoDoisFk);
                stn.setString(
                        4, "Ativo");
                stn.execute();
                System.out.println("\nDisco: " + nomeDisco + " cadastrada com sucesso!\n");
                System.out.println("------------------------------\n");
            }

        } else {
            System.out.println("A Máquina possui apenas 1 Disco");
            System.out.println("------------------------------\n");

            if (checkCardDiscoUm(user, pass) != null) {
                System.out.println("\n------------------------------");
                System.out.println("\nChecando Status Disco 1");
                System.out.println("Disco 1 já cadastrado e Ativo!");
                System.out.println("------------------------------\n");
            } else {
                System.out.println("\n------------------------------");
                System.out.println("\nChecando Status Disco 1");
                System.out.println("\nDisco 1 não cadastrado ou Inativo");
                Integer discoUmFk = 3;
                System.out.println("\nCadastrando o Disco 1");
                System.out.println("\nFavor Digitar um Nome ao Disco 1: ");
                String nomeDisco = leitorComp.nextLine();
                String query = "insert into tb_maquina_componente ( descComponente, fkMaquina, fkComponente, mcStatus) values (?,?,?,?)";

                PreparedStatement stn = conexao.prepareStatement(query);

                stn.setString(
                        1, nomeDisco);
                stn.setInt(
                        2, this.recuperarFkMaquina(user, pass));
                stn.setInt(
                        3, discoUmFk);
                stn.setString(
                        4, "Ativo");
                stn.execute();
                System.out.println("\nDisco: " + nomeDisco + " cadastrada com sucesso!\n");
                System.out.println("------------------------------\n");
            }
        }

        Integer cpuFkAjustada = checkCardCpu(login, senha);
        Integer ramFkAjustada = checkCardRam(login, senha);
        Integer discoUmFkAjustada = checkCardDiscoUm(login, senha);
        Integer discoDoisFkAjustada = checkCardDiscoDois(login, senha);

        System.out.println("\nIniciando monitoramento\n!");

        temporizadorComponentes(login, senha, ramFkAjustada, cpuFkAjustada, discoUmFkAjustada, discoDoisFkAjustada);

        //addLeituraRam(login, senha, ramFkAjustada);
        //addLeituraCpu(login, senha, cpuFkAjustada);
        //addLeituraDiscoUm(login, senha, discoUmFkAjustada);
        //addLeituraDiscoDois(login, senha, discoDoisFkAjustada);
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
        } catch (SQLException err) {
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
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    public Integer checkCardDiscoUm(String user, String pass) throws UnknownHostException, SQLException {
        PreparedStatement stm = null;
        try {
            String query = "select * from tb_maquina_componente where fkComponente = 3 and fkMaquina = " + recuperarFkMaquina(user, pass) + " and mcStatus = 'Ativo' order by idMaquinaComponente desc;";
            stm = conexao.prepareStatement(query);
            stm.executeQuery().next();
            ResultSet rs = stm.getResultSet();
            Integer idMaquinaComponenteDisco = rs.getInt("idMaquinaComponente");
            return idMaquinaComponenteDisco;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    public Integer checkCardDiscoDois(String user, String pass) throws UnknownHostException, SQLException {
        PreparedStatement stm = null;
        try {
            String query = "select * from tb_maquina_componente where fkComponente = 4 and fkMaquina = " + recuperarFkMaquina(user, pass) + " and mcStatus = 'Ativo' order by idMaquinaComponente desc;";
            stm = conexao.prepareStatement(query);
            stm.executeQuery().next();
            ResultSet rs = stm.getResultSet();
            Integer idMaquinaComponenteDisco = rs.getInt("idMaquinaComponente");
            return idMaquinaComponenteDisco;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    public void temporizadorComponentes(String user, String pass, Integer ramFkAjustada, Integer cpuFkAjustada, Integer discoUmFkAjustada, Integer discoDoisFkAjustada) {

        String usuario = user;
        String senha = pass;
        Integer ramFk = ramFkAjustada;
        Integer cpuFk = cpuFkAjustada;
        Integer discoUmFk = discoUmFkAjustada;
        Integer discoDoisFk = discoDoisFkAjustada;

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    addLeituraRam(usuario, senha, ramFk);
                    addLeituraCpu(usuario, senha, cpuFk);
                    if (looca.getGrupoDeDiscos().getQuantidadeDeDiscos() > 1) {
                        addLeituraDiscoUm(usuario, senha, discoUmFk);
                        addLeituraDiscoDois(usuario, senha, discoDoisFk);
                    } else {
                        addLeituraDiscoUm(usuario, senha, discoUmFk);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AcoesCli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AcoesCli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AcoesCli.class.getName()).log(Level.SEVERE, null, ex);
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
        String nvAlerta = "";

        long ramTotalGb = (looca.getMemoria().getTotal() / 1000000000);
        long ramEmUsoGb = (looca.getMemoria().getEmUso() / 1000000000);
        long porcentagemRam = ((ramEmUsoGb * 100) / ramTotalGb);
        
        System.out.println("Uso de RAM: " + ramEmUsoGb + "GB de um total de " + ramTotalGb + "GB"
                            +"\nUm porcentual de: " + porcentagemRam + "%");

        if (porcentagemRam < 30) {
            System.out.printf("Estado Critico!");
            json.put("text", "Componente critico! Memória RAM com baixo desempenho. \n"
                    + ramEmUsoGb + "GB");
            slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("\nRAM\n" + ramEmUsoGb + "GB\n" + "Um porcentual de uso de: " + porcentagemRam + " % / 100%\nComponente critico! Memória RAM com baixo desempenho. \n");
        } else if (porcentagemRam < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua memória RAM está perdendo desempenho. \n"
                    + ramEmUsoGb + "GB");
            slack.sendMessage(json);
            nvAlerta = "B";
            logs.registrarHistorico("\nRAM\n" + ramEmUsoGb + "GB\n" + "Um porcentual de uso de: " + porcentagemRam + " % / 100%\nAtenção!!! Sua memória RAM está perdendo desempenho. \n");
        } else if (porcentagemRam < 90) {
            System.out.println("Em bom estado!");
            nvAlerta = "A";
            logs.registrarHistorico("\nRAM\n" + ramEmUsoGb + "GB\n" + "Um porcentual de uso de: " + porcentagemRam + " % / 100%\nComponente em bom estado! Memória RAM em ótimo desempenho. \n");
        } else {
            System.out.println("Em perfeito estado!!!");
            nvAlerta = "S";
            logs.registrarHistorico("\nRAM\n" + ramEmUsoGb + "GB\n" + "Um porcentual de uso de: " + porcentagemRam + " % / 100%\nComponente em ótimo estado! Memória Ram em seu desempenho máximo. \n");
        }
        System.out.println("\n------------------------------\n");
        assistenteBd.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, porcentagemRam, data, 2, ramFk);
    }

    public void addLeituraCpu(String user, String pass, Integer cpuFk) throws IOException, InterruptedException {
        System.out.println("\n------------------------------\n");
        System.out.println(data);
        System.out.println("Enviando dados de CPU");
        String nvAlerta = "";

        long usoCpuPorc = (looca.getProcessador().getUso().intValue());
        System.out.println("Uso CPU: " + usoCpuPorc + "%");

        if (usoCpuPorc < 30) {
            System.out.println("Estado Critico!");
            json.put("text", "Componente critico! CPU em baixo desempenho. \n"
                    + usoCpuPorc + "%");
            slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("\nProcessador \n" + usoCpuPorc + "\n" + "Componente critico! CPU em baixo desempenho. \n");
        } else if (usoCpuPorc < 60) {
            System.out.println("Estado de Atenção!!!");
            json.put("text", "Atenção!!! Sua CPU está perdendo desempenho. \n"
                    + usoCpuPorc + "%");
            slack.sendMessage(json);
            nvAlerta = "B";
            logs.registrarHistorico("\nProcessador \n" + usoCpuPorc + "\n" + "Atenção!!! Sua CPU está perdendo desempenho. \n");
        } else if (usoCpuPorc < 90) {
            System.out.println("Em bom estado!");
            nvAlerta = "A";
            logs.registrarHistorico("\nProcessador \n" + usoCpuPorc + "\n" + "Componente em bom estado! CPU em ótimo desempenho. \n");
        } else {
            System.out.println("Em perfeito estado");
            nvAlerta = "S";
            logs.registrarHistorico("\nProcessador \n" + usoCpuPorc + "\n" + "Componente em ótimo estado! CPU em seu desempenho máximo. \n");
        }
        System.out.println("\n------------------------------\n");
        assistenteBd.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, usoCpuPorc, data, 1, cpuFk);

    }

    public void addLeituraDiscoUm(String user, String pass, Integer discoUmFk) throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de Disco");
        String nvAlerta = "";

        long totalDisco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1000000000;
        long disponivelDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000000000;
        long ocupadoDisco = (totalDisco - disponivelDisco);
        long porcDisco = (ocupadoDisco * 100) / totalDisco;
        
        System.out.println("Uso de Disco1: " + ocupadoDisco + "GB de um total de " + totalDisco + "GB"
                            +"\nUm porcentual de: " + porcDisco + "%");

        if (porcDisco < 30) {
            System.out.println("Em perfeito estado");
            nvAlerta = "S";
            logs.registrarHistorico("Componente em ótimo estado! Disco com excelente armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        } else if (porcDisco < 60) {
            System.out.println("Em bom estado!");
            nvAlerta = "A";
            logs.registrarHistorico("Componente em bom estado! Disco com bom armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        } else if (porcDisco < 90) {

            json.put("text", "Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
            nvAlerta = "B";
            logs.registrarHistorico("Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        } else {

                        System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Disco com baixo armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
            Slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("Componente critico! Disco com baixo armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        }

        assistenteBd.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, porcDisco, data, 1, discoUmFk);

    }

    public void addLeituraDiscoDois(String user, String pass, Integer discoDoisFk) throws IOException, InterruptedException {
        System.out.println(data);
        System.out.println("Enviando dados de Disco");
        String nvAlerta = "";

        long totalDisco = looca.getGrupoDeDiscos().getVolumes().get(1).getTotal() / 1000000000;
        long disponivelDisco = looca.getGrupoDeDiscos().getVolumes().get(1).getDisponivel() / 1000000000;
        long ocupadoDisco = (totalDisco - disponivelDisco);
        long porcDisco = (disponivelDisco * 100) / totalDisco;
        
        System.out.println("Uso de Disco1: " + ocupadoDisco + "GB de um total de " + totalDisco + "GB"
                            +"\nUm porcentual de: " + porcDisco + "%");

        if (porcDisco < 30) {
            System.out.println("Em perfeito estado");
            nvAlerta = "S";
            logs.registrarHistorico("Componente em ótimo estado! Disco com excelente armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        } else if (porcDisco < 60) {
            System.out.println("Em bom estado!");
            nvAlerta = "A";
            logs.registrarHistorico("Componente em bom estado! Disco com bom armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        } else if (porcDisco < 90) {

            json.put("text", "Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
            nvAlerta = "B";
            logs.registrarHistorico("Atenção!!! Seu disco está abaixo do armazenamento recomendado. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        } else {

                        System.out.println("Estado Critico!");
            json.put("text", "Componente critico! Disco com baixo armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
            Slack.sendMessage(json);
            nvAlerta = "C";
            logs.registrarHistorico("Componente critico! Disco com baixo armazenamento. \n"
                    + ocupadoDisco + "GB ocupados de " + totalDisco + "GB");
        }

        assistenteBd.update("insert into tb_leitura (nvAlerta, valor, dataHora, fkDado, fkMaquinaComponente) values (?,?,?,?,?)",
                nvAlerta, porcDisco, data, 1, discoDoisFk);

    }

}
