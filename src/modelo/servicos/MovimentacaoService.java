package modelo.servicos;

import java.util.Date;
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
    
    public List<Date> listaMesesMovimentacoes() {
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
