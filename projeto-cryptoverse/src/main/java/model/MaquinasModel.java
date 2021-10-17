package model;

public class MaquinasModel {
    
    private int idMaquina;
    private String hostName;
    private String numSerie;
    private String tipoProcessador;

    public MaquinasModel(int idMaquina, String hostName, String numSerie, String tipoProcessador) {
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
        this.tipoProcessador = tipoProcessador;
    }
    
    
    
    
    
}
