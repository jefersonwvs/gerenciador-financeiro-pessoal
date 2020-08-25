package modelo.dao;

import bd.BD;
import modelo.dao.impl.CaixaDaoImpl;
import modelo.dao.impl.MovimentacaoDaoImpl;
import modelo.dao.impl.UsuarioDaoImpl;

public class FabricaDao {
    
    public static UsuarioDao criaUsuarioDao() {
        return new UsuarioDaoImpl(BD.criaConexao());
    }
    
    public static MovimentacaoDao criaMovimentacaDao() {
        return new MovimentacaoDaoImpl(BD.criaConexao());            
    }

    public static CaixaDao criaCaixaDao() {
        return new CaixaDaoImpl(BD.criaConexao());
    }
}
