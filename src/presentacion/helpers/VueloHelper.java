
package presentacion.helpers;

import presentacion.helpers.*;
import logica.clase.Factory;
import logica.clase.ISistema;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;
import logica.clase.Aeropuerto;

import java.awt.event.ComponentEvent;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.BorderLayout;

public class VueloHelper {

    // Método helper para obtener la instancia de ISistema a través del Factory
    private static ISistema getSistema() {
        Factory factory = new Factory();
        return factory.getSistema();
    }

    // ------------------- RESET FORMULARIO -------------------
    public static void resetFormularioRuta(
            JTextField nombre,
            JTextArea descripcion,
            JTextField hora,
            JTextField costoTurista,
            JTextField costoEjecutivo,
            JTextField costoEquipaje,
            JTextField origen,
            JTextField destino
    ) {
        if (nombre != null) nombre.setText("");
        if (descripcion != null) descripcion.setText("");
        if (hora != null) hora.setText("");
        if (costoTurista != null) costoTurista.setText("");
        if (costoEjecutivo != null) costoEjecutivo.setText("");
        if (costoEquipaje != null) costoEquipaje.setText("");
        if (origen != null) origen.setText("");
        if (destino != null) destino.setText("");

        if (nombre != null) nombre.requestFocus();
    }

    // ------------------- AEROLÍNEAS -------------------
    public static List<DTAerolinea> listarAerolineas() {
        try {
            return getSistema().listarAerolineas(); // Llamamos directamente a Sistema
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar aerolíneas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }

    public static void seleccionarAerolinea(String nickname) {
        try {
            getSistema().seleccionarAerolinea(nickname); // Sistema guarda la aerolínea seleccionada
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ingresarRutaVuelo(
            String nombre,
            String descripcion,
            String horaStr,
            String costoTuristaStr,
            String costoEjecutivoStr,
            String costoEquipajeStr,
            String origen,
            String destino,
            Calendar fechaCal,
            List<String> categoriasSeleccionadas) throws Exception {

        // ------------------- VALIDACIONES -------------------
        if (nombre.trim().isEmpty() || descripcion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y descripción son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (origen.trim().isEmpty() || destino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe indicar ciudad de origen y destino.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (origen.equalsIgnoreCase(destino)) {
            JOptionPane.showMessageDialog(null, "El origen y destino no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int hora, minutos;
        try {
            String[] partes = horaStr.split(":");
            if (partes.length != 2) {
                JOptionPane.showMessageDialog(null, "Formato inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            hora = Integer.parseInt(partes[0]);
            minutos = Integer.parseInt(partes[1]);

            if (hora < 0 || hora > 23 || minutos < 0 || minutos > 59) {
                JOptionPane.showMessageDialog(null, "Ingrese una hora válida en formato HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Formato inválido. Use HH:mm (ejemplo: 14:30)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float costoTurista, costoEjecutivo, costoEquipaje;
        try {
            costoTurista = Float.parseFloat(costoTuristaStr);
            costoEjecutivo = Float.parseFloat(costoEjecutivoStr);
            costoEquipaje = Float.parseFloat(costoEquipajeStr);

            if (costoTurista < 0 || costoEjecutivo < 0 || costoEquipaje < 0) {
                JOptionPane.showMessageDialog(null, "Los costos no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los costos deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ------------------- CONVERSIONES -------------------
        DTFecha fecha = new DTFecha(
                fechaCal.get(Calendar.DAY_OF_MONTH),
                fechaCal.get(Calendar.MONTH) + 1,
                fechaCal.get(Calendar.YEAR)
        );

        DTHora horaVuelo = new DTHora(hora, minutos);

        // ------------------- CREAR LA RUTA -------------------
        try {
            for (String categoria : categoriasSeleccionadas) {
                getSistema().ingresarDatosRuta(
                        nombre,
                        descripcion,
                        horaVuelo,
                        costoTurista,
                        costoEjecutivo,
                        costoEquipaje,
                        origen,
                        destino,
                        fecha,
                        categoria // un String por vez
                );
                getSistema().registrarRuta();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void crearCiudad(String nombre, String pais, String aeropuerto, String descripcion, String sitioWeb, DTFecha fechaAlta) {
        if (nombre == null || nombre.trim().isEmpty() || pais == null || pais.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y país son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            getSistema().altaCiudad(nombre.trim(), pais.trim(), aeropuerto, descripcion, sitioWeb, fechaAlta);
            JOptionPane.showMessageDialog(null, "Ciudad creada con éxito.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void crearCategoria(String nombre) {
        getSistema().altaCategoria(nombre);
    }
    
    public static void precargarAeropuertos() {
        try {
            getSistema().precargarAeropuertos();
            JOptionPane.showMessageDialog(null, "Aeropuertos precargados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al precargar aeropuertos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static DTRutaVuelo getRutasDeAerolinea(String nicknameAerolinea, String nombreRuta) {
//        for (DTRutaVuelo ruta : Sistema.getInstance().listarRutaVuelo(nicknameAerolinea)) {
//            if (ruta.getNombre().equals(nombreRuta)) return ruta;
//        }
//        return null;
//    }
//
//
//    // ------------ ALTA VUELO ----------------
//    public static void ingresarVuelo(
//            String nombre,
//            String duracionStr,
//            String horaStr,
//            Calendar fechaCal,
//            int maxTurista,
//            int maxEjecutivo,
//            DTRutaVuelo ruta
//    ) throws Exception {
//
//        // ------------------- VALIDACIONES -------------------
//        if (nombre == null || nombre.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "El nombre del vuelo es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (duracionStr == null || duracionStr.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "La duración es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (horaStr == null || horaStr.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "La hora de salida es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (ruta == null) {
//            JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta primero.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (maxTurista < 0 || maxEjecutivo < 0) {
//            JOptionPane.showMessageDialog(null, "La cantidad de asientos no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // ------------------- PARSEAR DURACIÓN -------------------
//        int durHoras, durMinutos;
//        try {
//            String[] partesDur = duracionStr.split(":");
//            if (partesDur.length != 2) {
//                JOptionPane.showMessageDialog(null, "Formato de duración inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            durHoras = Integer.parseInt(partesDur[0]);
//            durMinutos = Integer.parseInt(partesDur[1]);
//
//            if (durHoras < 0 || durHoras > 23 || durMinutos < 0 || durMinutos > 59) {
//                JOptionPane.showMessageDialog(null, "Ingrese una duración válida en formato HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Formato de duración inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        DTHora duracion = new DTHora(durHoras, durMinutos);
//
//        // ------------------- PARSEAR HORA DE SALIDA -------------------
//        int horaSalida, minSalida;
//        try {
//            String[] partesHora = horaStr.split(":");
//            if (partesHora.length != 2) {
//                JOptionPane.showMessageDialog(null, "Formato de hora inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            horaSalida = Integer.parseInt(partesHora[0]);
//            minSalida = Integer.parseInt(partesHora[1]);
//
//            if (horaSalida < 0 || horaSalida > 23 || minSalida < 0 || minSalida > 59) {
//                JOptionPane.showMessageDialog(null, "Ingrese una hora válida en formato HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Formato de hora inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        DTHora horaVuelo = new DTHora(horaSalida, minSalida);
//
//        // ------------------- CONVERTIR FECHA -------------------
//        DTFecha fecha = new DTFecha(
//                fechaCal.get(Calendar.DAY_OF_MONTH),
//                fechaCal.get(Calendar.MONTH) + 1,
//                fechaCal.get(Calendar.YEAR)
//        );
//
//        // ------------------- CREAR EL VUELO -------------------
//        try {
//            Sistema.getInstance().ingresarDatosVuelo(
//                    nombre,
//                    fecha,
//                    horaVuelo,
//                    duracion,
//                    maxTurista,
//                    maxEjecutivo,
//                    fecha,
//                    ruta
//            );
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//
}

