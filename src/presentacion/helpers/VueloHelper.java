
package presentacion.helpers;

import presentacion.helpers.*;
import logica.clase.Factory;
import logica.clase.ISistema;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;
import dato.entidades.Aeropuerto;

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
            throw new Exception("Nombre y descripción son obligatorios.");
        }
        if (origen.trim().isEmpty() || destino.trim().isEmpty()) {
            throw new Exception("Debe indicar ciudad de origen y destino.");
        }
        if (origen.equalsIgnoreCase(destino)) {
            throw new Exception("El origen y destino no pueden ser iguales.");
        }

        int hora, minutos;
        try {
            String[] partes = horaStr.split(":");
            if (partes.length != 2) {
                throw new Exception("Formato inválido. Use HH:mm");
            }

            hora = Integer.parseInt(partes[0]);
            minutos = Integer.parseInt(partes[1]);

            if (hora < 0 || hora > 23 || minutos < 0 || minutos > 59) {
                throw new Exception("Ingrese una hora válida en formato HH:mm");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Formato inválido. Use HH:mm (ejemplo: 14:30)");
        }

        float costoTurista, costoEjecutivo, costoEquipaje;
        try {
            costoTurista = Float.parseFloat(costoTuristaStr);
            costoEjecutivo = Float.parseFloat(costoEjecutivoStr);
            costoEquipaje = Float.parseFloat(costoEquipajeStr);

            if (costoTurista < 0 || costoEjecutivo < 0 || costoEquipaje < 0) {
                throw new Exception("Los costos no pueden ser negativos.");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Los costos deben ser números válidos.");
        }

        // Validación de ruta existente
        List<DTRutaVuelo> rutasExistentes = getSistema().listarRutaVueloDeVuelo();
        for (DTRutaVuelo r : rutasExistentes) {
            if (r.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe una ruta con ese nombre.");
            }
        }

        Date fechaDate = fechaCal.getTime();

// Obtener fecha de hoy sin hora
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

// Fecha a validar sin hora
        Calendar fechaVal = (Calendar) fechaCal.clone();
        fechaVal.set(Calendar.HOUR_OF_DAY, 0);
        fechaVal.set(Calendar.MINUTE, 0);
        fechaVal.set(Calendar.SECOND, 0);
        fechaVal.set(Calendar.MILLISECOND, 0);

// Comparar
        if (!fechaVal.equals(hoy)) {
            throw new IllegalArgumentException("La fecha de alta debe ser el día de hoy.");
        }

        // ------------------- CONVERSIONES -------------------
        DTFecha fecha = new DTFecha(
                fechaCal.get(Calendar.DAY_OF_MONTH),
                fechaCal.get(Calendar.MONTH) + 1,
                fechaCal.get(Calendar.YEAR)
        );

        DTHora horaVuelo = new DTHora(hora, minutos);


        // ------------------- CREAR LA RUTA -------------------

        for (String categoria : categoriasSeleccionadas) {
            try {
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
            } catch (IllegalArgumentException e) {
                throw e; // re-lanzar tal cual
            } catch (Exception e) {
                throw new RuntimeException(e); // otras excepciones se envuelven
            }
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

    public static DTRutaVuelo getRutasDeAerolinea(String nicknameAerolinea, String nombreRuta) {
        for (DTRutaVuelo ruta : getSistema().seleccionarAerolineaRet(nicknameAerolinea)) {
            if (ruta.getNombre().equals(nombreRuta)) return ruta;
        }
        return null;
    }


    // ------------ ALTA VUELO ----------------
    public static void ingresarVuelo(
            String nombre,
            String duracionStr,
            String horaStr,
            Calendar fechaAltaCal,
            Calendar fechaVueloCal,
            String maxTurista,
            String maxEjecutivo,
            DTRutaVuelo ruta
    ) throws Exception {

        // ------------------- VALIDACIONES -------------------
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del vuelo es obligatorio.");
        }

        if (duracionStr == null || duracionStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La duración es obligatoria.");
        }

        if (horaStr == null || horaStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de salida es obligatoria.");
        }
        // Validamos que no estén vacíos
        if (maxTurista.trim().isEmpty()) {
            throw new IllegalArgumentException("La cantidad de asientos turista es obligatoria.");
        }
        if (maxEjecutivo.trim().isEmpty()) {
            throw new IllegalArgumentException("La cantidad de asientos ejecutivo es obligatoria.");
        }

        if (ruta == null) {
            throw new IllegalArgumentException("Debe seleccionar una ruta primero.");
        }


        // Parseo a int
        int maxTuristaInt;
        int maxEjecutivoInt;
        try {
            maxTuristaInt = Integer.parseInt(maxTurista.trim());
            maxEjecutivoInt = Integer.parseInt(maxEjecutivo.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad de asientos debe ser un número válido.");
        }

// Validaciones de rango
        int maxAsientos = 200;
        if (maxTuristaInt < 0 || maxTuristaInt > maxAsientos || maxEjecutivoInt < 0 || maxEjecutivoInt > maxAsientos) {
            throw new IllegalArgumentException(
                    "La cantidad de asientos debe estar entre 0 y " + maxAsientos + "."
            );
        }
        if ((maxTuristaInt + maxEjecutivoInt) > maxAsientos) {
            throw new IllegalArgumentException(
                    "La suma de asientos turista y ejecutivo no puede superar " + maxAsientos + "."
            );
        }
        //chequeamos que las fechas sean coherentes
        // Fecha de alta: solo hoy
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        Calendar fechaAltaVal = (Calendar) fechaAltaCal.clone();
        fechaAltaVal.set(Calendar.HOUR_OF_DAY, 0);
        fechaAltaVal.set(Calendar.MINUTE, 0);
        fechaAltaVal.set(Calendar.SECOND, 0);
        fechaAltaVal.set(Calendar.MILLISECOND, 0);

        if (!fechaAltaVal.equals(hoy)) {
            throw new IllegalArgumentException("La fecha de alta debe ser el día de hoy.");
        }

        // Fecha del vuelo: hoy o futuro
        Date fechaVueloDate = fechaVueloCal.getTime();
        if (fechaVueloDate.before(hoy.getTime())) {
            throw new IllegalArgumentException("La fecha del vuelo no puede ser anterior al día de hoy.");
        }


        //------------CHEQUEO QUE EL VUELO NO EXISTA---------------

        //---------------------------------------------------------
        // ------------------- PARSEAR DURACIÓN -------------------
        int durHoras, durMinutos;
        String[] partesDur = duracionStr.split(":");
        if (partesDur.length != 2) {
            throw new IllegalArgumentException("Formato de duración inválido. Use HH:mm");
        }

        String durHorasStr = partesDur[0];
        String durMinutosStr = partesDur[1];

// Validar que tengan exactamente 2 dígitos cada uno
        if (durHorasStr.length() != 2 || durMinutosStr.length() != 2) {
            throw new IllegalArgumentException("Formato de duración inválido. Use HH:mm (dos dígitos por componente)");
        }

        try {
            durHoras = Integer.parseInt(durHorasStr);
            durMinutos = Integer.parseInt(durMinutosStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de duración inválido. Use HH:mm");
        }

        if (durHoras < 0 || durHoras > 23 || durMinutos < 0 || durMinutos > 59) {
            throw new IllegalArgumentException("Ingrese una duración válida en formato HH:mm");
        }

        DTHora duracion = new DTHora(durHoras, durMinutos);

        // ------------------- PARSEAR HORA DE SALIDA -------------------
        int horaSalida, minSalida;
        String[] partesHora = horaStr.split(":");
        if (partesHora.length != 2) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm");
        }

        String horaStrH = partesHora[0];
        String horaStrM = partesHora[1];

// Validar dos dígitos también para la hora de salida
        if (horaStrH.length() != 2 || horaStrM.length() != 2) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm (dos dígitos por componente)");
        }

        try {
            horaSalida = Integer.parseInt(horaStrH);
            minSalida = Integer.parseInt(horaStrM);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm");
        }

        if (horaSalida < 0 || horaSalida > 23 || minSalida < 0 || minSalida > 59) {
            throw new IllegalArgumentException("Ingrese una hora válida en formato HH:mm");
        }

        DTHora horaVuelo = new DTHora(horaSalida, minSalida);

        // ------------------- CONVERTIR FECHA -------------------
        DTFecha fecha = new DTFecha(
                fechaAltaCal.get(Calendar.DAY_OF_MONTH),
                fechaAltaCal.get(Calendar.MONTH) + 1,
                fechaAltaCal.get(Calendar.YEAR)
        );
        DTFecha fechaVuelo = new DTFecha(
                fechaVueloCal.get(Calendar.DAY_OF_MONTH),
                fechaVueloCal.get(Calendar.MONTH) + 1,
                fechaVueloCal.get(Calendar.YEAR)
        );


        // ------------------- CREAR EL VUELO -------------------
        try {
            getSistema().ingresarDatosVuelo(
                    nombre,
                    fechaVuelo,
                    horaVuelo,
                    duracion,
                    maxTuristaInt,
                    maxEjecutivoInt,
                    fecha,
                    ruta
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }







}

