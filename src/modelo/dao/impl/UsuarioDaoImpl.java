package modelo.dao.impl;

import bd.BD;
import bd.BdException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.dao.UsuarioDao;
import modelo.entidades.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

    private Connection conexao;

    public UsuarioDaoImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public Usuario buscaUsuario(String login) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String comandoSQL = "SELECT * FROM USUARIOS WHERE Login = ?";
            st = conexao.prepareStatement(comandoSQL);
            st.setString(1, login);

            rs = st.executeQuery();
            
            if (rs.next()) {
                Usuario obj = new Usuario();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setLogin(rs.getString("Login"));
                obj.setSenha(rs.getString("Senha"));
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

}
