    package modelo.dao.impl;

import bd.BD;
import bd.BdException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.dao.MovimentacaoDao;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;
import modelo.entidades.TipoMovimentacao;
import modelo.entidades.utilitarios.Utilitaria;

public class MovimentacaoDaoImpl implements MovimentacaoDao {

    private Connection conexao;

    public MovimentacaoDaoImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void insere(Movimentacao obj) {

        PreparedStatement st = null;

        try {
            String comandoSQL = "INSERT INTO MOVIMENTACOES(Data, Valor, Tipo, Descricao, fkIdCaixa) VALUES(?, ?, ?, ?, ?)";
            st = conexao.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS);
            st.setTimestamp(1, Utilitaria.transformaData(obj.getData()));
            st.setDouble(2, obj.getValor());
            st.setString(3, obj.getTipo().toString());
            st.setString(4, obj.getDescricao());
            st.setInt(5, obj.getCaixa().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            } else {
                throw new BdException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
        }
    }

    @Override
    public void atualiza(Movimentacao obj) {

        PreparedStatement st = null;
        try {
            String comandoSQL = "UPDATE MOVIMENTACOES SET Data = ?, Valor = ?, Tipo = ?, Descricao = ?, fkIdCaixa = ? WHERE IdMovimentacao = ?";
            st = conexao.prepareStatement(comandoSQL);
            st.setTimestamp(1, Utilitaria.transformaData(obj.getData()));
            st.setDouble(2, obj.getValor());
            st.setString(3, obj.getTipo().toString());
            st.setString(4, obj.getDescricao());
            st.setInt(5, obj.getCaixa().getId());
            st.setInt(6, obj.getId());
            
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
        }
    }

    @Override
    public void deletaPorId(Integer id) {
        PreparedStatement st = null;
        try {
            String comandoSQL = "DELETE FROM MOVIMENTACOES WHERE IdMovimentacoes = ?";
            st = conexao.prepareStatement(comandoSQL);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
        }
    }

    @Override
    public List<Movimentacao> listaMovimentacoes() {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String comandoSQL = 
                    "SELECT MOVIMENTACOES.IdMovimentacao, MOVIMENTACOES.Data, MOVIMENTACOES.Valor, MOVIMENTACOES.Tipo, MOVIMENTACOES.Descricao, CAIXA.* " +
                    "FROM MOVIMENTACOES INNER JOIN CAIXA " +
                    "ON MOVIMENTACOES.fkIdCaixa = CAIXA.IdCaixa " +
                    "ORDER BY Data";
                    
            st = conexao.prepareStatement(comandoSQL);

            rs = st.executeQuery();

            List<Movimentacao> list = new ArrayList<>();
            Map<Integer, Caixa> map = new HashMap<>();

            while (rs.next()) {
                Caixa caixa = map.get(rs.getInt("IdCaixa"));
                if (caixa == null) {
                    caixa = new Caixa();
                    caixa.setId(rs.getInt("IdCaixa"));
                    caixa.setNomeCaixa(rs.getString("NomeCaixa"));
                    caixa.setSaldoCaixa(rs.getDouble("SaldoCaixa"));
                    map.put(rs.getInt("IdCaixa"), caixa);
                }
                Movimentacao obj = new Movimentacao();
                obj.setId(rs.getInt("IdMovimentacao"));
                obj.setData(new java.util.Date(rs.getTimestamp("Data").getTime()));
                obj.setValor(rs.getDouble("Valor"));
                obj.setTipo(TipoMovimentacao.valueOf(rs.getString("Tipo")));
                obj.setDescricao(rs.getString("Descricao"));
                obj.setCaixa(caixa);
                list.add(obj);
            }
            return list;
            
        } catch (SQLException e) {
            //System.out.println("foi aqui");
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
            BD.encerraResultado(rs);
        }

    }

    @Override
    public List<Movimentacao> listaMovimentacoes(String tipo) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String comandoSQL = "SELECT MOVIMENTACOES.*, CAIXA.NomeCaixa, CAIXA.SaldoCaixa " +
                    "FROM MOVIMENTACOES INNER JOIN CAIXA " +
                    "ON MOVIMENTACOES.fkIdCaixa = CAIXA.IdCaixa " +
                    "WHERE Tipo = ? " +
                    "ORDER BY Data";
                    
            st = conexao.prepareStatement(comandoSQL);
            st.setString(1, tipo);
            
            rs = st.executeQuery();

            List<Movimentacao> list = new ArrayList<>();
            Map<Integer, Caixa> map = new HashMap<>();

            while (rs.next()) {
                Caixa caixa = map.get(rs.getInt("fkIdCaixa"));
                if (caixa == null) {
                    caixa = new Caixa();
                    caixa.setId(rs.getInt("IdCaixa"));
                    caixa.setNomeCaixa(rs.getString("NomeCaixa"));
                    caixa.setSaldoCaixa(rs.getDouble("SaldoCaixa"));
                    map.put(rs.getInt("fkIdCaixa"), caixa);
                }
                Movimentacao obj = new Movimentacao();
                obj.setId(rs.getInt("Id"));
                obj.setData(new java.util.Date(rs.getTimestamp("Data").getTime()));
                obj.setValor(rs.getDouble("Valor"));
                obj.setTipo(TipoMovimentacao.valueOf(rs.getString("Tipo")));
                obj.setDescricao(rs.getString("Descricao"));
                obj.setCaixa(caixa);
                list.add(obj);
            }
            return list;
            
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
            BD.encerraResultado(rs);
        }
    }

    @Override
    public List<Movimentacao> listaMovimentacoesPorCaixa(Caixa caixa) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String comandoSQL = "SELECT MOVIMENTACOES.*, CAIXA.NomeCaixa, CAIXA.SaldoCaixa " +
                    "FROM MOVIMENTACOES INNER JOIN CAIXA " +
                    "ON MOVIMENTACOES.fkIdCaixa = CAIXA.IdCaixa " +
                    "WHERE IdCaixa = ? "+
                    "ORDER BY Data";
                    
            st = conexao.prepareStatement(comandoSQL);
            st.setInt(1, caixa.getId());
            
            rs = st.executeQuery();

            List<Movimentacao> list = new ArrayList<>();
            Map<Integer, Caixa> map = new HashMap<>();

            while (rs.next()) {
                Caixa cx = map.get(rs.getInt("fkIdCaixa"));
                if (cx == null) {
                    cx = new Caixa();
                    cx.setId(rs.getInt("IdCaixa"));
                    cx.setNomeCaixa(rs.getString("NomeCaixa"));
                    cx.setSaldoCaixa(rs.getDouble("SaldoCaixa"));
                    map.put(rs.getInt("fkIdCaixa"), cx);
                }
                Movimentacao obj = new Movimentacao();
                obj.setId(rs.getInt("Id"));
                obj.setData(new java.util.Date(rs.getTimestamp("Data").getTime()));
                obj.setValor(rs.getDouble("Valor"));
                obj.setTipo(TipoMovimentacao.valueOf(rs.getString("Tipo")));
                obj.setDescricao(rs.getString("Descricao"));
                obj.setCaixa(cx);
                list.add(obj);
            }
            return list;
            
        } catch (SQLException e) {
            throw new BdException(e.getMessage());
        } finally {
            BD.encerraDeclaracao(st);
            BD.encerraResultado(rs);
        }

    }
    
    
    
}