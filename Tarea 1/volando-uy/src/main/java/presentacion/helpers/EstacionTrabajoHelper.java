package presentacion.helpers;

import dato.entidades.RutaVuelo;
import logica.servicios.RutaVueloServicio;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Helper para la Estación de Trabajo
 * Proporciona métodos auxiliares para cargar datos en las vistas de la Estación
 * de Trabajo
 */
public class EstacionTrabajoHelper {

    /**
     * Carga las 5 rutas de vuelo más visitadas en un DefaultTableModel
     * 
     * @return DefaultTableModel con las columnas: #, Ruta de Vuelo, Aerolínea,
     *         Ciudad Origen, Ciudad Destino, Cantidad de visitas
     */

    public static DefaultTableModel cargarRutasMasVisitadas() {
        String[] columnas = {
                "#",
                "Ruta de Vuelo",
                "Aerolínea",
                "Ciudad Origen",
                "Ciudad Destino",
                "Cantidad de visitas"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };

        try {
            RutaVueloServicio rutaVueloServicio = new RutaVueloServicio();
            List<RutaVuelo> rutasMasVisitadas = rutaVueloServicio.listarTop5RutasMasVisitadas();

            int ranking = 1;
            for (RutaVuelo ruta : rutasMasVisitadas) {
                String nombreAerolinea = "";
                if (ruta.getAerolineas() != null && !ruta.getAerolineas().isEmpty()) {
                    nombreAerolinea = ruta.getAerolineas().get(0).getNombre();
                }

                String ciudadOrigen = ruta.getCiudadOrigen() != null ? ruta.getCiudadOrigen().getNombre() : "N/A";
                String ciudadDestino = ruta.getCiudadDestino() != null ? ruta.getCiudadDestino().getNombre() : "N/A";

                modelo.addRow(new Object[]{
                        ranking,
                        ruta.getNombre(),
                        nombreAerolinea,
                        ciudadOrigen,
                        ciudadDestino,
                        ruta.getVisitas()
                });

                ranking++;
            }

        } catch (Exception e) {
            System.err.println("Error al cargar rutas más visitadas: " + e.getMessage());
            e.printStackTrace();
        }

        return modelo;
    }
}
