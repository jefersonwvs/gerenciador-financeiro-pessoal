package modelo.servicos;

import java.sql.Date;
import java.util.List;
import modelo.dao.FabricaDao;
import modelo.dao.MovimentacaoDao;
import modelo.entidades.Movimentacao;

public class MovimentacaoService {
    
    MovimentacaoDao dao = FabricaDao.criaMovimentacaDao();
    
    public List<Movimentacao> listaMovimentacoes() {
        return dao.listaMovimentacoes();
    }
    
    public List<Movimentacao> listaMovimentacoes(String tipo) {
        return dao.listaMovimentacoes(tipo);
    }
    
    public List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim) {
        return dao.listaMovimentacoesPorPeriodo(inicio, fim);
    }
    
    public List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim, int idCaixa) {
        return dao.listaMovimentacoesPorPeriodo(inicio, fim, idCaixa);
    }
    
    public List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim, String tipo) {
        return dao.listaMovimentacoesPorPeriodo(inicio, fim, tipo);
    }
    
    public List<Movimentacao> listaMovimentacoesPorPeriodo(Date inicio, Date fim, int idCaixa, String tipo) {
        return dao.listaMovimentacoesPorPeriodo(inicio, fim, idCaixa, tipo);
    }
    
    public List<java.util.Date> listaMesesMovimentacoes() {
        return dao.listaMesesMovimentacoes();
    }

    public void insereOuAtualiza(Movimentacao obj) {
        if (obj.getId() == null)
            dao.insere(obj);
        else
            dao.atualiza(obj);
    }
    
    public void deleta(Movimentacao obj) {
        dao.deletaPorId(obj.getId());
    }
    
}
