package modelo.dao;

import java.util.List;
import modelo.entidades.Caixa;

public interface CaixaDao {
    
    void insere(Caixa obj);
    void atualiza(Caixa obj);
    void deletaPorId(Integer id);
    Caixa buscaCaixa(Integer id);
    List<Caixa> listaCaixas();
    
}
