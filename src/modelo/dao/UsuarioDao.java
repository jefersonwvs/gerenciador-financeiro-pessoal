package modelo.dao;

import modelo.entidades.Usuario;

public interface UsuarioDao {
    
    Usuario buscaUsuario(String login);

}
