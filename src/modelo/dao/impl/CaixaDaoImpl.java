package modelo.dao.impl;

import bd.BD;
import bd.BdException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.CaixaDao;
import modelo.entidades.Caixa;

public class CaixaDaoImpl implements CaixaDao {

    private Connection conexao;

    public CaixaDaoImpl(Connection conexao) {
        this.conexao = conexao;
    }
    
    @Override
    public void insere(Caixa obj) {
        
        PreparedStatement st = null;
        try {
            String comandoSQL = "INSERT INTO CAIXA(NomeCaixa, SaldoCaixa) VALUES(?, ?)";
            st = conexao.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNomeCaixa());
            st.setDouble(2, obj.getSaldoCaixa());
            
            int linhasAfetadas = st.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt("IdCaixa"));
                }
                BD.encerraResultado(rs);
            } else {
                throw new BdException("Erro inesperado! Nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
        }
            
    }
    
    @Override
    public void atualiza(Caixa obj) {
        
        PreparedStatement st = null;
        try {
            String comandoSQL = "UPDATE CAIXA SET NomeCaixa = ?, SaldoCaixa = ? WHERE IdCaixa = ?";
            st = conexao.prepareStatement(comandoSQL);
            st.setString(1, obj.getNomeCaixa()); 
            st.setDouble(2, obj.getSaldoCaixa());
            st.setInt(3, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
        }

    }

    @Override
    public Caixa buscaCaixa(Integer id) {
        
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String comandoSQL = "SELECT * FROM CAIXA WHERE Id = ?";
            st = conexao.prepareStatement(comandoSQL);
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                Caixa obj = new Caixa();
                obj.setId(rs.getInt("Id"));
                obj.setNomeCaixa(rs.getString("NomeCaixa"));
                obj.setSaldoCaixa(rs.getDouble("SaldoCaixa"));
                return obj;
            }
            return null;

        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
            BD.encerraResultado(rs);
        }

    }
    
    @Override
    public List<Caixa> listaCaixas() {
        
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String comandoSQL = "SELECT * FROM CAIXA";
            st = conexao.prepareStatement(comandoSQL);
            rs = st.executeQuery();
            
            List<Caixa> lista = new ArrayList<>();
            while (rs.next()) {
                Caixa obj = new Caixa();
                obj.setId(rs.getInt("IdCaixa"));
                obj.setNomeCaixa(rs.getString("NomeCaixa"));
                obj.setSaldoCaixa(rs.getDouble("SaldoCaixa"));
                lista.add(obj);
            }
            System.out.println(lista);
            return lista;
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
            BD.encerraResultado(rs);
        }
        
    }

    @Override
    public void deletaPorId(Integer id) {
        
        PreparedStatement st = null;
        try {
            String comandoSQL = "DELETE FROM CAIXA WHERE IdCaixa = ?";
            st = conexao.prepareStatement(comandoSQL);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
        }
        
    }
}
