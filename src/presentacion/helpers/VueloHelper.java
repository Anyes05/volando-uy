package presentacion.helpers;

import presentacion.helpers.*;
import logica.clase.Sistema;
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
            return Sistema.getInstance().listarAerolineas(); // Llamamos directamente a Sistema
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar aerolíneas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }

    public static void seleccionarAerolinea(String nickname) {
        try {
            Sistema.getInstance().seleccionarAerolinea(nickname); // Sistema guarda la aerolínea seleccionada
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
            String categoria
    ) throws Exception {

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
            DTRutaVuelo ruta = Sistema.getInstance().ingresarDatosRuta(
                    nombre,
                    descripcion,
                    horaVuelo,
                    costoTurista,
                    costoEjecutivo,
                    costoEquipaje,
                    origen,
                    destino,
                    fecha,
                    categoria
            );
            Sistema.getInstance().registrarRuta();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void crearCiudad(String nombre, String pais, Aeropuerto aeropuerto, DTFecha fechaAlta) {
        if (nombre == null || nombre.trim().isEmpty() || pais == null || pais.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y país son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Sistema.getInstance().altaCiudad(nombre.trim(), pais.trim(), aeropuerto, fechaAlta);
            JOptionPane.showMessageDialog(null, "Ciudad creada con éxito.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void crearCategoria(String nombre) {
        Sistema.getInstance().altaCategoria(nombre);
    }




}



