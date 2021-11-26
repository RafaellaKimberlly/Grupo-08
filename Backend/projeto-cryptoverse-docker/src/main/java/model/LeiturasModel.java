package model;

public class LeiturasModel {
    
    private Integer idLeitura;
    private String descricao;
    private Integer valor;
    private String dataHora; //Dúvida: podemos instânciar um  date = new date() ?

    public LeiturasModel(Integer idLeitura, String descricao, Integer valor, String dataHora) {
        this.idLeitura = idLeitura;
        this.descricao = descricao;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public LeiturasModel() {
    }

    public int getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
    
}
