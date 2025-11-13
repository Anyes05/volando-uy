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

    // Método sobrecargado para registrar ruta con estado específico
    public void registrarRutaVuelo(String nombre, String descripcion, DTCostoBase DTCostoBase, DTFecha fechaAlta, Aerolinea aerolinea, Ciudad ciudadOrigen, Ciudad ciudadDestino, List<Categoria> categorias, byte[] foto, EstadoRutaVuelo estado, String videoUrl)  {
        RutaVuelo rv = new RutaVuelo(nombre, descripcion, DTCostoBase, fechaAlta, foto);
        rv.setCiudadDestino(ciudadDestino);
        rv.setCiudadOrigen(ciudadOrigen);
        rv.getAerolineas().add(aerolinea); // Se asocia la aerolinea a la ruta de vuelo
        rv.setCategorias(categorias);
        rv.setEstado(estado); // Establecer estado específico
        // Asignar videoUrl si fue proporcionado (opcional)
        if (videoUrl != null && !videoUrl.trim().isEmpty()) {
            rv.setVideoUrl(videoUrl.trim());
        }
        rutaVueloDAO.guardar(rv);  // Se guarda en la BD
    }


    public RutaVuelo obtenerRutaVuelo(Long id) {
        return rutaVueloDAO.buscarPorId(id);
    }

    // Método para precargar rutas de vuelo
    public void precargarRutasVuelo() {
        try {
            // Datos de rutas de vuelo comunes con diferentes estados
            // Incluye los 4 estados: INGRESADA, CONFIRMADA, FINALIZADA, RECHAZADA
            Object[][] rutasData = {
                    // {nombre, descripcion, costoTurista, costoEjecutivo, costoEquipajeExtra, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, aerolineaNickname, categoria, estado}
                    
                    // Rutas en estado INGRESADA (pendientes de confirmación)
                    {"Montevideo-São Paulo", "Conexión directa con la capital económica de Brasil", 450.0f, 750.0f, 20.0f, "Montevideo", "Uruguay", "São Paulo", "Brasil", "latam", "Internacional", EstadoRutaVuelo.INGRESADA},
                    {"Montevideo-Lima", "Conexión con la capital peruana", 500.0f, 800.0f, 25.0f, "Montevideo", "Uruguay", "Lima", "Perú", "latam", "Internacional", EstadoRutaVuelo.INGRESADA},
                    {"Montevideo-Quito", "Ruta hacia la capital ecuatoriana", 550.0f, 900.0f, 30.0f, "Montevideo", "Uruguay", "Quito", "Ecuador", "latam", "Internacional", EstadoRutaVuelo.INGRESADA},
                    {"Buenos Aires-Córdoba", "Ruta doméstica argentina", 150.0f, 250.0f, 10.0f, "Buenos Aires", "Argentina", "Córdoba", "Argentina", "aerolineas", "Nacional", EstadoRutaVuelo.INGRESADA},
                    {"Lima-Cusco", "Ruta turística hacia Machu Picchu", 200.0f, 350.0f, 15.0f, "Lima", "Perú", "Cusco", "Perú", "latam", "Turismo", EstadoRutaVuelo.INGRESADA},
                    
                    // Rutas en estado CONFIRMADA (aceptadas y activas)
                    {"Montevideo-Buenos Aires", "Ruta directa entre las capitales de Uruguay y Argentina", 200.0f, 350.0f, 15.0f, "Montevideo", "Uruguay", "Buenos Aires", "Argentina", "pluna", "Regional", EstadoRutaVuelo.CONFIRMADA},
                    {"Montevideo-Santiago", "Ruta hacia la capital chilena", 400.0f, 650.0f, 18.0f, "Montevideo", "Uruguay", "Santiago", "Chile", "latam", "Internacional", EstadoRutaVuelo.CONFIRMADA},
                    {"Punta del Este-Buenos Aires", "Ruta turística entre destinos populares", 180.0f, 300.0f, 12.0f, "Punta del Este", "Uruguay", "Buenos Aires", "Argentina", "aerolineas", "Turismo", EstadoRutaVuelo.CONFIRMADA},
                    {"Buenos Aires-Córdoba Confirmada", "Ruta doméstica argentina confirmada", 150.0f, 250.0f, 10.0f, "Buenos Aires", "Argentina", "Córdoba", "Argentina", "aerolineas", "Nacional", EstadoRutaVuelo.CONFIRMADA},
                    {"São Paulo-Río de Janeiro", "Conexión entre las principales ciudades brasileñas", 120.0f, 200.0f, 8.0f, "São Paulo", "Brasil", "Río de Janeiro", "Brasil", "gol", "Nacional", EstadoRutaVuelo.CONFIRMADA},
                    {"Montevideo-Bogotá Confirmada", "Ruta hacia la capital colombiana confirmada", 600.0f, 950.0f, 30.0f, "Montevideo", "Uruguay", "Bogotá", "Colombia", "avianca", "Internacional", EstadoRutaVuelo.CONFIRMADA},
                    
                    // Rutas en estado FINALIZADA (completadas)
                    {"Montevideo-Buenos Aires Finalizada", "Ruta directa completada entre las capitales", 200.0f, 350.0f, 15.0f, "Montevideo", "Uruguay", "Buenos Aires", "Argentina", "pluna", "Regional", EstadoRutaVuelo.FINALIZADA},
                    {"Buenos Aires-Mendoza", "Ruta turística hacia los viñedos completada", 180.0f, 280.0f, 12.0f, "Buenos Aires", "Argentina", "Mendoza", "Argentina", "aerolineas", "Turismo", EstadoRutaVuelo.FINALIZADA},
                    {"São Paulo-Brasília", "Conexión con la capital brasileña completada", 250.0f, 400.0f, 15.0f, "São Paulo", "Brasil", "Brasília", "Brasil", "gol", "Nacional", EstadoRutaVuelo.FINALIZADA},
                    {"Santiago-Buenos Aires", "Conexión entre capitales sudamericanas completada", 350.0f, 550.0f, 20.0f, "Santiago", "Chile", "Buenos Aires", "Argentina", "latam", "Regional", EstadoRutaVuelo.FINALIZADA},
                    {"Montevideo-Lima Finalizada", "Conexión con la capital peruana completada", 500.0f, 800.0f, 25.0f, "Montevideo", "Uruguay", "Lima", "Perú", "latam", "Internacional", EstadoRutaVuelo.FINALIZADA},
                    
                    // Rutas en estado RECHAZADA (no aprobadas)
                    {"Montevideo-Bogotá Rechazada", "Ruta hacia la capital colombiana rechazada", 600.0f, 950.0f, 30.0f, "Montevideo", "Uruguay", "Bogotá", "Colombia", "avianca", "Internacional", EstadoRutaVuelo.RECHAZADA},
                    {"São Paulo-Brasília Rechazada", "Conexión con la capital brasileña rechazada", 250.0f, 400.0f, 15.0f, "São Paulo", "Brasil", "Brasília", "Brasil", "gol", "Nacional", EstadoRutaVuelo.RECHAZADA},
                    {"Buenos Aires-Lima", "Conexión entre capitales sudamericanas rechazada", 450.0f, 700.0f, 25.0f, "Buenos Aires", "Argentina", "Lima", "Perú", "latam", "Internacional", EstadoRutaVuelo.RECHAZADA},
                    {"Montevideo-Santiago Rechazada", "Ruta hacia la capital chilena rechazada", 400.0f, 650.0f, 18.0f, "Montevideo", "Uruguay", "Santiago", "Chile", "latam", "Internacional", EstadoRutaVuelo.RECHAZADA}
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
                    registrarRutaVuelo(
                            nombreRuta,
                            (String) rutaData[1], // descripcion
                            costoBase,
                            fechaAlta,
                            aerolinea,
                            ciudadOrigen,
                            ciudadDestino,
                            categorias,
                            null,  // foto
                            estado,
                            null   // videoUrl (opcional)
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
