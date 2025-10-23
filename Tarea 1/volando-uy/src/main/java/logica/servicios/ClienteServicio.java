package logica.servicios;

import dato.dao.ClienteDAO;
import dato.entidades.Cliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;
import logica.excepciones.ClienteException;

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
                             String numeroDocumento, byte[] foto, String contrasena) throws ClienteException {
        // Validaciones previas
        if (clienteDAO.buscarPorNickname(nickname) != null) {
            throw new ClienteException("Ya existe un cliente con ese nickname.");
        }

        Cliente cliente = new Cliente(nickname, nombre, correo, apellido,
                fechaNacimiento, nacionalidad,
                tipoDoc, numeroDocumento, foto, contrasena);
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

    // Método para precargar clientes
    public void precargarClientes() {
        try {
            // Datos de clientes de prueba
            Object[][] clientesData = {
                    // {nickname, nombre, correo, apellido, dia, mes, año, nacionalidad, tipoDoc, numeroDoc, foto, contraseña}
                    {"jperez", "Juan", "juan.perez@email.com", "Pérez", 15, 3, 1990, "Uruguaya", TipoDoc.CI, "12345678", null, "Jperez1234"},
                    {"mgarcia", "María", "maria.garcia@email.com", "García", 22, 7, 1985, "Argentina", TipoDoc.PASAPORTE, "AB123456", null, "Mgarcia1234"},
                    {"rlopez", "Roberto", "roberto.lopez@email.com", "López", 8, 12, 1992, "Brasileña", TipoDoc.CI, "87654321", null, "Rlopez1234"},
                    {"acastro", "Ana", "ana.castro@email.com", "Castro", 3, 5, 1988, "Chilena", TipoDoc.PASAPORTE, "CD789012", null, "Acastro1234"},
                    {"fmartinez", "Fernando", "fernando.martinez@email.com", "Martínez", 18, 9, 1995, "Uruguaya", TipoDoc.CI, "11223344", null, "Fmartinez1234"},
                    {"srodriguez", "Sofía", "sofia.rodriguez@email.com", "Rodríguez", 25, 11, 1991, "Paraguaya", TipoDoc.PASAPORTE, "EF345678", null, "Srodriguez1234"},
                    {"dfernandez", "Diego", "diego.fernandez@email.com", "Fernández", 12, 2, 1987, "Argentina", TipoDoc.CI, "55667788", null, "Dfernandez1234"},
                    {"lgonzalez", "Laura", "laura.gonzalez@email.com", "González", 30, 6, 1993, "Uruguaya", TipoDoc.CI, "99887766", null, "Lgonzalez1234"},
                    {"chernandez", "Carlos", "carlos.hernandez@email.com", "Hernández", 7, 4, 1989, "Brasileña", TipoDoc.PASAPORTE, "GH901234", null, "Chernandez1234"},
                    {"mruiz", "Mónica", "monica.ruiz@email.com", "Ruiz", 14, 8, 1994, "Chilena", TipoDoc.CI, "44556677", null, "Mruiz1234"},
            };

            for (Object[] clienteData : clientesData) {
                String nickname = (String) clienteData[0];

                // Verificar si el cliente ya existe
                if (clienteDAO.buscarPorNickname(nickname) == null) {
                    try {
                        DTFecha fechaNac = new DTFecha((Integer) clienteData[4], (Integer) clienteData[5], (Integer) clienteData[6]);
                        TipoDoc tipoDoc = (TipoDoc) clienteData[8];
                        byte[] foto = (byte[]) clienteData[10];
                        String contrasena = (String) clienteData[11];

                        crearCliente(
                                nickname,
                                (String) clienteData[1], // nombre
                                (String) clienteData[2], // correo
                                (String) clienteData[3], // apellido
                                fechaNac,
                                (String) clienteData[7], // nacionalidad
                                tipoDoc,
                                (String) clienteData[9], // numeroDoc
                                foto,
                                contrasena
                        );
                        System.out.println("Cliente precargado: " + nickname);
                    } catch (Exception e) {
                        System.err.println("Error al precargar cliente " + nickname + ": " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error en la precarga de clientes: " + e.getMessage(), e);
        }
    }
}
