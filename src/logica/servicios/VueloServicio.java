package logica.servicios;

import dato.dao.VueloDAO;
import dato.entidades.RutaVuelo;
import dato.entidades.Vuelo;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;
import java.util.List;
import java.util.ArrayList;

public class VueloServicio {
    private VueloDAO vueloDAO = new VueloDAO();

    public void registrarVuelo(String nombre, DTFecha fechaVuelo, DTHora horaVuelo, DTHora duracion, int asientosMaxTurista, int asientosMaxEjecutivo, DTFecha fechaAlta, RutaVuelo rutaVuelo) {
        Vuelo v = new Vuelo(nombre, fechaVuelo, horaVuelo, duracion, asientosMaxTurista, asientosMaxEjecutivo, fechaAlta);
        v.setRutaVuelo(rutaVuelo);
        vueloDAO.guardar(v);  // Se guarda en la BD
    }

    public Vuelo obtenerVuelo(Long id) {
        return vueloDAO.buscarPorId(id);
    }

    public Vuelo buscarVueloPorNombre(String nombre) {
        return vueloDAO.buscarPorNombre(nombre);
    }

    public List<Vuelo> listarVuelos() {
        return vueloDAO.listarVuelos();
    }

    public void actualizarVuelo(Vuelo vuelo) {
        vueloDAO.actualizar(vuelo);
    }
    
    // Método para precargar vuelos
    public void precargarVuelos() {
        try {
            // Datos de vuelos de ejemplo
            Object[][] vuelosData = {
                // {nombre, diaFecha, mesFecha, añoFecha, horaHora, minutoHora, duracionHora, duracionMinuto, asientosTurista, asientosEjecutivo, nombreRuta}
                {"PLU001", 15, 3, 2024, 8, 30, 1, 30, 120, 20, "Montevideo-Buenos Aires"},
                {"PLU002", 15, 3, 2024, 14, 45, 1, 30, 120, 20, "Montevideo-Buenos Aires"},
                {"PLU003", 16, 3, 2024, 9, 15, 1, 30, 120, 20, "Montevideo-Buenos Aires"},
                {"LAT001", 15, 3, 2024, 10, 0, 2, 15, 150, 30, "Montevideo-São Paulo"},
                {"LAT002", 16, 3, 2024, 16, 30, 2, 15, 150, 30, "Montevideo-São Paulo"},
                {"LAT003", 15, 3, 2024, 11, 45, 2, 30, 140, 25, "Montevideo-Santiago"},
                {"LAT004", 16, 3, 2024, 18, 0, 2, 30, 140, 25, "Montevideo-Santiago"},
                {"LAT005", 15, 3, 2024, 13, 20, 3, 0, 160, 35, "Montevideo-Lima"},
                {"LAT006", 16, 3, 2024, 20, 15, 3, 0, 160, 35, "Montevideo-Lima"},
                {"AVI001", 15, 3, 2024, 12, 30, 3, 45, 180, 40, "Montevideo-Bogotá"},
                {"AVI002", 16, 3, 2024, 22, 0, 3, 45, 180, 40, "Montevideo-Bogotá"},
                {"IBE001", 15, 3, 2024, 15, 45, 11, 30, 200, 50, "Montevideo-Madrid"},
                {"IBE002", 16, 3, 2024, 23, 30, 11, 30, 200, 50, "Montevideo-Madrid"},
                {"AME001", 15, 3, 2024, 14, 0, 8, 15, 190, 45, "Montevideo-Miami"},
                {"AME002", 16, 3, 2024, 21, 45, 8, 15, 190, 45, "Montevideo-Miami"},
                {"ARG001", 15, 3, 2024, 7, 30, 1, 15, 100, 15, "Punta del Este-Buenos Aires"},
                {"ARG002", 15, 3, 2024, 6, 0, 1, 45, 110, 18, "Buenos Aires-Córdoba"},
                {"ARG003", 16, 3, 2024, 7, 30, 1, 45, 110, 18, "Buenos Aires-Córdoba"},
                {"GOL001", 15, 3, 2024, 8, 15, 1, 15, 130, 22, "São Paulo-Río de Janeiro"},
                {"GOL002", 16, 3, 2024, 9, 45, 1, 15, 130, 22, "São Paulo-Río de Janeiro"},
                {"SKY001", 15, 3, 2024, 7, 0, 1, 30, 90, 12, "Santiago-Valparaíso"},
                {"SKY002", 16, 3, 2024, 8, 30, 1, 30, 90, 12, "Santiago-Valparaíso"},
                {"LAT007", 15, 3, 2024, 10, 30, 1, 15, 120, 20, "Lima-Cusco"},
                {"LAT008", 16, 3, 2024, 12, 0, 1, 15, 120, 20, "Lima-Cusco"}
            };
            
            // Servicios necesarios
            RutaVueloServicio rutaVueloServicio = new RutaVueloServicio();
            
            for (Object[] vueloData : vuelosData) {
                String nombreVuelo = (String) vueloData[0];
                
                try {
                    // Buscar ruta de vuelo
                    RutaVuelo rutaVuelo = rutaVueloServicio.buscarRutaVueloPorNombre((String) vueloData[10]);
                    if (rutaVuelo == null) {
                        System.err.println("Ruta de vuelo no encontrada: " + vueloData[10]);
                        continue;
                    }
                    
                    // Crear fecha del vuelo
                    DTFecha fechaVuelo = new DTFecha(
                        (Integer) vueloData[1], // dia
                        (Integer) vueloData[2], // mes
                        (Integer) vueloData[3]  // año
                    );
                    
                    // Crear hora del vuelo
                    DTHora horaVuelo = new DTHora(
                        (Integer) vueloData[4], // hora
                        (Integer) vueloData[5]  // minuto
                    );
                    
                    // Crear duración
                    DTHora duracion = new DTHora(
                        (Integer) vueloData[6], // hora duracion
                        (Integer) vueloData[7]  // minuto duracion
                    );
                    
                    // Crear fecha de alta
                    DTFecha fechaAlta = new DTFecha(1, 1, 2024);
                    
                    // Registrar el vuelo
                    registrarVuelo(
                        nombreVuelo,
                        fechaVuelo,
                        horaVuelo,
                        duracion,
                        (Integer) vueloData[8],  // asientosMaxTurista
                        (Integer) vueloData[9],  // asientosMaxEjecutivo
                        fechaAlta,
                        rutaVuelo
                    );
                    
                    System.out.println("Vuelo precargado: " + nombreVuelo);
                    
                } catch (Exception e) {
                    System.err.println("Error al precargar vuelo " + nombreVuelo + ": " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error en la precarga de vuelos: " + e.getMessage(), e);
        }
    }
    

}
