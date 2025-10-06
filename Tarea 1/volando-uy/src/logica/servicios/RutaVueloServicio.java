package logica.servicios;

import dato.dao.RutaVueloDAO;
import dato.entidades.Aerolinea;
import dato.entidades.Categoria;
import dato.entidades.Ciudad;
import dato.entidades.RutaVuelo;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;

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


    public RutaVuelo obtenerRutaVuelo(Long id) {
        return rutaVueloDAO.buscarPorId(id);
    }

    // Método para precargar rutas de vuelo
    public void precargarRutasVuelo() {
        try {
            // Datos de rutas de vuelo comunes
            Object[][] rutasData = {
                    // {nombre, descripcion, costoTurista, costoEjecutivo, costoEquipajeExtra, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, aerolineaNickname, categoria}
                    {"Montevideo-Buenos Aires", "Ruta directa entre las capitales de Uruguay y Argentina", 200.0f, 350.0f, 15.0f, "Montevideo", "Uruguay", "Buenos Aires", "Argentina", "pluna", "Regional", null},
                    {"Montevideo-São Paulo", "Conexión directa con la capital económica de Brasil", 450.0f, 750.0f, 20.0f, "Montevideo", "Uruguay", "São Paulo", "Brasil", "latam", "Internacional", null},
                    {"Montevideo-Santiago", "Ruta hacia la capital chilena", 400.0f, 650.0f, 18.0f, "Montevideo", "Uruguay", "Santiago", "Chile", "latam", "Internacional", null},
                    {"Montevideo-Lima", "Conexión con la capital peruana", 500.0f, 800.0f, 25.0f, "Montevideo", "Uruguay", "Lima", "Perú", "latam", "Internacional", null},
                    {"Montevideo-Bogotá", "Ruta hacia la capital colombiana", 600.0f, 950.0f, 30.0f, "Montevideo", "Uruguay", "Bogotá", "Colombia", "avianca", "Internacional", null},
                    {"Montevideo-Madrid", "Conexión transatlántica con España", 1200.0f, 2000.0f, 50.0f, "Montevideo", "Uruguay", "Madrid", "España", "iberia", "Internacional", null},
                    {"Montevideo-Miami", "Ruta hacia Estados Unidos", 800.0f, 1300.0f, 40.0f, "Montevideo", "Uruguay", "Miami", "Estados Unidos", "american", "Internacional", null},
                    {"Punta del Este-Buenos Aires", "Ruta turística entre destinos populares", 180.0f, 300.0f, 12.0f, "Punta del Este", "Uruguay", "Buenos Aires", "Argentina", "aerolineas", "Turismo", null},
                    {"Buenos Aires-Córdoba", "Ruta doméstica argentina", 150.0f, 250.0f, 10.0f, "Buenos Aires", "Argentina", "Córdoba", "Argentina", "aerolineas", "Nacional", null},
                    {"São Paulo-Río de Janeiro", "Conexión entre las principales ciudades brasileñas", 120.0f, 200.0f, 8.0f, "São Paulo", "Brasil", "Río de Janeiro", "Brasil", "gol", "Nacional", null},
                    {"Santiago-Valparaíso", "Ruta doméstica chilena", 80.0f, 130.0f, 5.0f, "Santiago", "Chile", "Valparaíso", "Chile", "sky", "Nacional", null},
                    {"Lima-Cusco", "Ruta turística hacia Machu Picchu", 200.0f, 350.0f, 15.0f, "Lima", "Perú", "Cusco", "Perú", "latam", "Turismo", null}
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

                    // Registrar la ruta
                    registrarRutaVuelo(
                            nombreRuta,
                            (String) rutaData[1], // descripcion
                            costoBase,
                            fechaAlta,
                            aerolinea,
                            ciudadOrigen,
                            ciudadDestino,
                            categorias,
                            null  // foto
                    );

                    System.out.println("Ruta de vuelo precargada: " + nombreRuta);

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

}
