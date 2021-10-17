package model;

public class ComponentesModel {
    
    private Integer idComponente;
    private String descComponente;

    public ComponentesModel(Integer idComponente, String descComponente) {
        this.idComponente = idComponente;
        this.descComponente = descComponente;
    }

    public int getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getDescComponente() {
        return descComponente;
    }

    public void setDescComponente(String descComponente) {
        this.descComponente = descComponente;
    }
    
    
    
    
    
}
