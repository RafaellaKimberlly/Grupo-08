package model;

public class LeiturasModel {
    
    private int idLeitura;
    private String descricao;
    private int valor;
    private String dataHora; //Dúvida: podemos instânciar um  date = new date() ?

    public LeiturasModel(int idLeitura, String descricao, int valor, String dataHora) {
        this.idLeitura = idLeitura;
        this.descricao = descricao;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public int getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(int idLeitura) {
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

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
    
}
