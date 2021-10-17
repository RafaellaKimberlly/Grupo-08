package model;

public class ComponentesModel {
    
    private int idComponente;
    private String descComponente;

    public ComponentesModel(int idComponente, String descComponente) {
        this.idComponente = idComponente;
        this.descComponente = descComponente;
    }

    public int getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(int idComponente) {
        this.idComponente = idComponente;
    }

    public String getDescComponente() {
        return descComponente;
    }

    public void setDescComponente(String descComponente) {
        this.descComponente = descComponente;
    }
    
    
    
    
    
}
