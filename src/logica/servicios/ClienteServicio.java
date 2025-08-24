package logica.servicios;

import dato.dao.ClienteDAO;
import dato.entidades.Cliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;

import java.util.List;

public class ClienteServicio {
    private ClienteDAO clienteDAO;

    public ClienteServicio() {
        this.clienteDAO = new ClienteDAO();
    }

    // Crear cliente aplicando reglas de negocio
    public void crearCliente(String nickname, String nombre, String correo,
                             String apellido, DTFecha fechaNacimiento,
                             String nacionalidad, TipoDoc tipoDoc,
                             String numeroDocumento) throws Exception {
        // Validaciones previas
        if (clienteDAO.buscarPorNickname(nickname) != null) {
            throw new Exception("Ya existe un cliente con ese nickname.");
        }

        Cliente cliente = new Cliente(nickname, nombre, correo, apellido,
                fechaNacimiento, nacionalidad,
                tipoDoc, numeroDocumento);
        clienteDAO.crear(cliente);
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteDAO.buscarPorId(id);
    }

    public Cliente buscarClientePorNickname(String nickname) {
        return clienteDAO.buscarPorNickname(nickname);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }

    public void actualizarCliente(Cliente cliente) {
        clienteDAO.actualizar(cliente);
    }

    public void eliminarCliente(Long id) {
        clienteDAO.eliminar(id);
    }
}
