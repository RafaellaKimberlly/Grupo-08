package model;

public class MaquinasComponentesModel {
    
    private int idMaquinaComponente;
    private int idMaquina;
    private int idComponente;
    private String status;

    public MaquinasComponentesModel(int idMaquinaComponente, int idMaquina, int idComponente, String status) {
        this.idMaquinaComponente = idMaquinaComponente;
        this.idMaquina = idMaquina;
        this.idComponente = idComponente;
        this.status = status;
    }

    public int getIdMaquinaComponente() {
        return idMaquinaComponente;
    }

    public void setIdMaquinaComponente(int idMaquinaComponente) {
        this.idMaquinaComponente = idMaquinaComponente;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(int idComponente) {
        this.idComponente = idComponente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
