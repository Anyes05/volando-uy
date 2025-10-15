package logica.servicios;

import dato.dao.GenericDAO;
import dato.entidades.Usuario;

import java.util.List;

public class UsuarioServicio {
    private GenericDAO<Usuario> usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = new GenericDAO<>(Usuario.class);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }


}
