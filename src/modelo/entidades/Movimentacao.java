package modelo.entidades;

import java.io.Serializable;
import java.util.Date;

public class Movimentacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Date data;
    private Double valor;
    private TipoMovimentacao tipo;
    private String descricao;
    private Caixa caixa;

    public Movimentacao() {
    }
    
    public Movimentacao(Integer id, Date data, Double valor, TipoMovimentacao tipo, String descricao, Caixa caixa) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.caixa = caixa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    @Override
    public String toString() {
        return "Movimentacao{" + "id=" + id + ", data=" + data + ", valor=" + valor + ", tipo=" + tipo + ", descricao=" + descricao + ", caixa=" + caixa + '}';
    }
    
}
