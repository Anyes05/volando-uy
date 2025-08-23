package logica.servicios;

import dato.dao.UsuarioDAO;
import dato.entidades.Usuario;

public class UsuarioServicio {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(String nickname, String nombre, String correo) {
        Usuario u = new Usuario(nickname, nombre, correo);
        usuarioDAO.guardar(u);  // Se guarda en la BD
    }

    public Usuario obtenerUsuario(Long id) {
        return usuarioDAO.buscarPorId(id);
    }
}
