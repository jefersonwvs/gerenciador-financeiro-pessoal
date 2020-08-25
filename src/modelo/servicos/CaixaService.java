package modelo.servicos;

import java.util.List;
import modelo.dao.CaixaDao;
import modelo.dao.FabricaDao;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;

public class CaixaService {
    
    CaixaDao dao = FabricaDao.criaCaixaDao();
    
    public List<Caixa> listaCaixas() {
        return dao.listaCaixas();
    }
    
    public void movimentaCaixa(Movimentacao movimentacao) {
        
        if (movimentacao.getValor() == 0.0)
            throw new IllegalArgumentException("Valor de movimentação inválido!");
        
        if ((movimentacao.getTipo().toString().equals("DESPESA") && movimentacao.getCaixa().getSaldoCaixa() >= movimentacao.getValor()) || movimentacao.getTipo().toString().equals("RECEITA")) {
            Caixa caixa = movimentacao.getCaixa();
            if (movimentacao.getTipo().toString().equals("DESPESA"))
                caixa.setSaldoCaixa(caixa.getSaldoCaixa() - movimentacao.getValor());
            else
                caixa.setSaldoCaixa(caixa.getSaldoCaixa() + movimentacao.getValor());
            dao.atualiza(caixa);
            MovimentacaoService servico = new MovimentacaoService();
            servico.insereOuAtualiza(movimentacao);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }
    }

}
