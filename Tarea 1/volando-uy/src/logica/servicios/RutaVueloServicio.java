package logica.servicios;

import dato.dao.RutaVueloDAO;
import dato.entidades.Aerolinea;
import dato.entidades.Categoria;
import dato.entidades.Ciudad;
import dato.entidades.RutaVuelo;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;
import logica.DataTypes.EstadoRutaVuelo;

import java.util.List;

public class RutaVueloServicio {
    private RutaVueloDAO rutaVueloDAO = new RutaVueloDAO();

    public void registrarRutaVuelo(String nombre, String descripcion, DTCostoBase DTCostoBase, DTFecha fechaAlta, Aerolinea aerolinea, Ciudad ciudadOrigen, Ciudad ciudadDestino, List<Categoria> categorias, byte[] foto)  {
        RutaVuelo rv = new RutaVuelo(nombre, descripcion, DTCostoBase, fechaAlta, foto);
        rv.setCiudadDestino(ciudadDestino);
        rv.setCiudadOrigen(ciudadOrigen);
        rv.getAerolineas().add(aerolinea); // Se asocia la aerolinea a la ruta de vuelo
        rv.setCategorias(categorias);
        rutaVueloDAO.guardar(rv);  // Se guarda en la BD
    }

    // Método sobrecargado para registrar ruta con estado específico
    public void registrarRutaVueloConEstado(String nombre, String descripcion, DTCostoBase DTCostoBase, DTFecha fechaAlta, Aerolinea aerolinea, Ciudad ciudadOrigen, Ciudad ciudadDestino, List<Categoria> categorias, byte[] foto, EstadoRutaVuelo estado)  {
        RutaVuelo rv = new RutaVuelo(nombre, descripcion, DTCostoBase, fechaAlta, foto);
        rv.setCiudadDestino(ciudadDestino);
        rv.setCiudadOrigen(ciudadOrigen);
        rv.getAerolineas().add(aerolinea); // Se asocia la aerolinea a la ruta de vuelo
        rv.setCategorias(categorias);
        rv.setEstado(estado); // Establecer estado específico
        rutaVueloDAO.guardar(rv);  // Se guarda en la BD
    }


    public RutaVuelo obtenerRutaVuelo(Long id) {
        return rutaVueloDAO.buscarPorId(id);
    }

    // Método para precargar rutas de vuelo
    public void precargarRutasVuelo() {
        try {
            // Datos de rutas de vuelo comunes con diferentes estados
            Object[][] rutasData = {
                    // {nombre, descripcion, costoTurista, costoEjecutivo, costoEquipajeExtra, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, aerolineaNickname, categoria, estado}
                    {"Montevideo-Buenos Aires", "Ruta directa entre las capitales de Uruguay y Argentina", 200.0f, 350.0f, 15.0f, "Montevideo", "Uruguay", "Buenos Aires", "Argentina", "pluna", "Regional", EstadoRutaVuelo.CONFIRMADA},
                    {"Montevideo-São Paulo", "Conexión directa con la capital económica de Brasil", 450.0f, 750.0f, 20.0f, "Montevideo", "Uruguay", "São Paulo", "Brasil", "latam", "Internacional", EstadoRutaVuelo.INGRESADA},
                    {"Montevideo-Santiago", "Ruta hacia la capital chilena", 400.0f, 650.0f, 18.0f, "Montevideo", "Uruguay", "Santiago", "Chile", "latam", "Internacional", EstadoRutaVuelo.CONFIRMADA},
                    {"Montevideo-Lima", "Conexión con la capital peruana", 500.0f, 800.0f, 25.0f, "Montevideo", "Uruguay", "Lima", "Perú", "latam", "Internacional", EstadoRutaVuelo.INGRESADA},
                    {"Montevideo-Bogotá", "Ruta hacia la capital colombiana", 600.0f, 950.0f, 30.0f, "Montevideo", "Uruguay", "Bogotá", "Colombia", "avianca", "Internacional", EstadoRutaVuelo.RECHAZADA},
                    {"Montevideo-Madrid", "Conexión transatlántica con España", 1200.0f, 2000.0f, 50.0f, "Montevideo", "Uruguay", "Madrid", "España", "iberia", "Internacional", EstadoRutaVuelo.CONFIRMADA},
                    {"Montevideo-Miami", "Ruta hacia Estados Unidos", 800.0f, 1300.0f, 40.0f, "Montevideo", "Uruguay", "Miami", "Estados Unidos", "american", "Internacional", EstadoRutaVuelo.INGRESADA},
                    {"Punta del Este-Buenos Aires", "Ruta turística entre destinos populares", 180.0f, 300.0f, 12.0f, "Punta del Este", "Uruguay", "Buenos Aires", "Argentina", "aerolineas", "Turismo", EstadoRutaVuelo.CONFIRMADA},
                    {"Buenos Aires-Córdoba", "Ruta doméstica argentina", 150.0f, 250.0f, 10.0f, "Buenos Aires", "Argentina", "Córdoba", "Argentina", "aerolineas", "Nacional", EstadoRutaVuelo.INGRESADA},
                    {"São Paulo-Río de Janeiro", "Conexión entre las principales ciudades brasileñas", 120.0f, 200.0f, 8.0f, "São Paulo", "Brasil", "Río de Janeiro", "Brasil", "gol", "Nacional", EstadoRutaVuelo.RECHAZADA},
                    {"Santiago-Valparaíso", "Ruta doméstica chilena", 80.0f, 130.0f, 5.0f, "Santiago", "Chile", "Valparaíso", "Chile", "sky", "Nacional", EstadoRutaVuelo.CONFIRMADA},
                    {"Lima-Cusco", "Ruta turística hacia Machu Picchu", 200.0f, 350.0f, 15.0f, "Lima", "Perú", "Cusco", "Perú", "latam", "Turismo", EstadoRutaVuelo.INGRESADA}
            };

            // Servicios necesarios
            AerolineaServicio aerolineaServicio = new AerolineaServicio();
            CiudadServicio ciudadServicio = new CiudadServicio();
            CategoriaServicio categoriaServicio = new CategoriaServicio();

            for (Object[] rutaData : rutasData) {
                String nombreRuta = (String) rutaData[0];

                try {
                    // Buscar aerolínea
                    Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname((String) rutaData[9]);
                    if (aerolinea == null) {
                        System.err.println("Aerolínea no encontrada: " + rutaData[9]);
                        continue;
                    }

                    // Buscar ciudades
                    Ciudad ciudadOrigen = ciudadServicio.buscarCiudadPorNombreYPais((String) rutaData[5], (String) rutaData[6]);
                    Ciudad ciudadDestino = ciudadServicio.buscarCiudadPorNombreYPais((String) rutaData[7], (String) rutaData[8]);

                    if (ciudadOrigen == null || ciudadDestino == null) {
                        System.err.println("Ciudades no encontradas para ruta: " + nombreRuta);
                        continue;
                    }

                    // Buscar categoría
                    Categoria categoria = categoriaServicio.obtenerCategoriaPorNombre((String) rutaData[10]);
                    if (categoria == null) {
                        System.err.println("Categoría no encontrada: " + rutaData[10]);
                        continue;
                    }

                    // Crear costo base
                    DTCostoBase costoBase = new DTCostoBase(
                            (Float) rutaData[2], // costoTurista
                            (Float) rutaData[3], // costoEjecutivo
                            (Float) rutaData[4]  // costoEquipajeExtra
                    );

                    // Crear fecha de alta
                    DTFecha fechaAlta = new DTFecha(1, 1, 2024);

                    // Crear lista de categorías
                    List<Categoria> categorias = new java.util.ArrayList<>();
                    categorias.add(categoria);

                    // Obtener estado
                    EstadoRutaVuelo estado = (EstadoRutaVuelo) rutaData[11];

                    // Registrar la ruta con estado específico
                    registrarRutaVueloConEstado(
                            nombreRuta,
                            (String) rutaData[1], // descripcion
                            costoBase,
                            fechaAlta,
                            aerolinea,
                            ciudadOrigen,
                            ciudadDestino,
                            categorias,
                            null,  // foto
                            estado
                    );

                    System.out.println("Ruta de vuelo precargada: " + nombreRuta + " con estado: " + estado);

                } catch (Exception e) {
                    System.err.println("Error al precargar ruta " + nombreRuta + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error en la precarga de rutas de vuelo: " + e.getMessage(), e);
        }
    }

    // Método para buscar ruta de vuelo por nombre
    public RutaVuelo buscarRutaVueloPorNombre(String nombreRuta) {
        return rutaVueloDAO.buscarPorNombre(nombreRuta);
    }

    // Método para listar rutas de vuelo por estado
    public List<RutaVuelo> listarRutasPorEstado(EstadoRutaVuelo estado) {
        return rutaVueloDAO.listarPorEstado(estado);
    }

    // Método para listar rutas de vuelo por aerolínea y estado
    public List<RutaVuelo> listarRutasPorAerolineaYEstado(String aerolineaNickname, EstadoRutaVuelo estado) {
        return rutaVueloDAO.listarPorAerolineaYEstado(aerolineaNickname, estado);
    }

    // Método para cambiar el estado de una ruta de vuelo
    public void cambiarEstadoRutaVuelo(Long id, EstadoRutaVuelo nuevoEstado) {
        rutaVueloDAO.actualizarEstado(id, nuevoEstado);
    }

    // Método para buscar ruta de vuelo por ID
    public RutaVuelo buscarRutaVueloPorId(Long id) {
        return rutaVueloDAO.buscarPorId(id);
    }

}
