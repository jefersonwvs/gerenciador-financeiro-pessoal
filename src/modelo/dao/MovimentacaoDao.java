package modelo.dao;

import java.sql.Date;
import java.util.List;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;

public interface MovimentacaoDao {
    
    void insere(Movimentacao obj);
    void atualiza(Movimentacao obj);
    void deletaPorId(Integer id);
    List<Movimentacao> listaMovimentacoes();
    List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim);
    List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim, int idCaixa);
    List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim, String tipo);
    List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim, int idCaixa, String tipo);
    List<Movimentacao> listaMovimentacoes(String tipo); // Receita ou Despesa
    List<Movimentacao> listaMovimentacoesPorCaixa(Caixa caixa);
    List<java.util.Date> listaMesesMovimentacoes();

}
