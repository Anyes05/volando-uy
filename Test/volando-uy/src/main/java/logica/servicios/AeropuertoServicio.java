package logica.servicios;

import dato.dao.AeropuertoDAO;
import dato.entidades.Aeropuerto;
import dato.entidades.Ciudad;
import dato.dao.CiudadDAO;
import logica.DataTypes.DTFecha;
import java.util.ArrayList;
import java.util.List;

public class AeropuertoServicio {
    private AeropuertoDAO aeropuertoDAO = new AeropuertoDAO();
    private CiudadDAO ciudadDAO = new CiudadDAO();

    public void registrarAeropuerto(String nombre, Ciudad ciudad) {
        Aeropuerto a = new Aeropuerto(nombre, ciudad);
        aeropuertoDAO.guardar(a);  // Se guarda en la BD
    }

    public Aeropuerto obtenerAeropuerto(Long id) {
        return aeropuertoDAO.buscarPorId(id);
    }

    // Método para precargar aeropuertos con validación de transacciones
    public void precargarAeropuertos() {
        // Lista de aeropuertos principales de Uruguay y países vecinos
        String[][] aeropuertosData = {
                // Uruguay
                {"Carrasco", "Montevideo", "Uruguay"},
                {"Laguna del Sauce", "Punta del Este", "Uruguay"},
                {"Santa Bernardina", "Durazno", "Uruguay"},
                {"Capitan Corbeta C.A. Curbelo", "Punta del Este", "Uruguay"},
                {"Angel S. Adami", "Montevideo", "Uruguay"},

                // Argentina
                {"Ezeiza", "Buenos Aires", "Argentina"},
                {"Aeroparque", "Buenos Aires", "Argentina"},
                {"Córdoba", "Córdoba", "Argentina"},
                {"Rosario", "Rosario", "Argentina"},
                {"Mendoza", "Mendoza", "Argentina"},

                // Brasil
                {"Guarulhos", "São Paulo", "Brasil"},
                {"Congonhas", "São Paulo", "Brasil"},
                {"Galeão", "Río de Janeiro", "Brasil"},
                {"Santos Dumont", "Río de Janeiro", "Brasil"},
                {"Brasília", "Brasília", "Brasil"},

                // Chile
                {"Arturo Merino Benítez", "Santiago", "Chile"},
                {"La Araucanía", "Temuco", "Chile"},
                {"El Tepual", "Puerto Montt", "Chile"},

                // Paraguay
                {"Silvio Pettirossi", "Asunción", "Paraguay"},
                {"Guaraní", "Ciudad del Este", "Paraguay"},

                // Bolivia
                {"El Alto", "La Paz", "Bolivia"},
                {"Viru Viru", "Santa Cruz", "Bolivia"},

                // Perú
                {"Jorge Chávez", "Lima", "Perú"},
                {"Alejandro Velasco Astete", "Cusco", "Perú"},

                // Colombia
                {"El Dorado", "Bogotá", "Colombia"},
                {"José María Córdova", "Medellín", "Colombia"},

                // Ecuador
                {"Mariscal Sucre", "Quito", "Ecuador"},
                {"José Joaquín de Olmedo", "Guayaquil", "Ecuador"},

                // Venezuela
                {"Simón Bolívar", "Caracas", "Venezuela"},
                {"La Chinita", "Maracaibo", "Venezuela"}
        };

        for (String[] aeropuertoData : aeropuertosData) {
            String nombreAeropuerto = aeropuertoData[0];
            String nombreCiudad = aeropuertoData[1];
            String pais = aeropuertoData[2];

            // Verificar si el aeropuerto ya existe
            if (!aeropuertoDAO.existeAeropuertoPorNombre(nombreAeropuerto)) {
                // Buscar o crear la ciudad
                Ciudad ciudad = ciudadDAO.buscarCiudadPorNombreYPais(nombreCiudad, pais);
                if (ciudad == null) {
                    // Crear la ciudad si no existe
                    ciudad = new Ciudad(nombreCiudad, pais, new DTFecha(1, 1, 2024));
                    ciudadDAO.guardar(ciudad);
                }

                // Crear el aeropuerto
                Aeropuerto aeropuerto = new Aeropuerto(nombreAeropuerto, ciudad);
                aeropuertoDAO.guardar(aeropuerto);
            }
        }
    }

    // Método para verificar si un aeropuerto existe
    public boolean existeAeropuerto(String nombre) {
        return aeropuertoDAO.existeAeropuertoPorNombre(nombre);
    }

    // Método para listar todos los aeropuertos
    public List<Aeropuerto> listarAeropuertos() {
        return aeropuertoDAO.listarTodos();
    }

    public List<String> listarNombresAeropuertos() {
        List<String> lista = new ArrayList<>();
        List<Aeropuerto> a = AeropuertoDAO.listarTodos();
        for (Aeropuerto aeropuerto : a) {
            lista.add(aeropuerto.getNombre());
        }
        return lista;
    }
}
