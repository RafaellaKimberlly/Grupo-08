package model;

import com.github.britooo.looca.api.group.processador.Processador;

public class MaquinasModel {
    
    Processador processador = new Processador();
    
    private Integer idMaquina;
    private String hostName;
    private String numSerie;
    private String tipoProcessador;
    private Integer idUsuario;

    public MaquinasModel(Integer idMaquina, String hostName, String numSerie, String tipoProcessador) {
        this.idMaquina = idMaquina;
        this.hostName = hostName;
        this.numSerie = numSerie;
        this.tipoProcessador = tipoProcessador;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getTipoProcessador() {
        return tipoProcessador;
    }

    public void setTipoProcessador(String tipoProcessador) {
        this.tipoProcessador = processador.getNome();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }   
    
}
