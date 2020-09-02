package modelo.entidades;

import java.io.Serializable;

public class Caixa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nomeCaixa;
    private Double saldoCaixa;

    public Caixa() {
    }

    public Caixa(Integer id, String nomeCaixa, Double saldoCaixa) {
        this.id = id;
        this.nomeCaixa = nomeCaixa;
        this.saldoCaixa = saldoCaixa;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCaixa() {
        return nomeCaixa;
    }
    
    public void setNomeCaixa(String nomeCaixa) {
        this.nomeCaixa = nomeCaixa;
    }

    public Double getSaldoCaixa() {
        return saldoCaixa;
    }
    
    public void setSaldoCaixa(Double saldoCaixa) {
        this.saldoCaixa = saldoCaixa;
    }

    @Override
    public String toString() {
        return id + " – " + nomeCaixa;
    }
    
}
