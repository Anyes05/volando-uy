package presentacion.helpers;


import presentacion.helpers.*;
import logica.clase.Factory;
import logica.clase.ISistema;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;

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


public class PaqueteHelper {

    // Método helper para obtener la instancia de ISistema a través del Factory
    private static ISistema getSistema() {
        Factory factory = new Factory();
        return factory.getSistema();
    }

    public static void ingresarPaquete(
            String nombre,
            String descripcion,
            String diasValidosStr,
            String descuentoStr,
            Calendar fechaCal
    ) {

        // ------------------- VALIDACIONES -------------------
        if (nombre.trim().isEmpty() || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre y descripción son obligatorios.");
        }

        if (diasValidosStr == null || diasValidosStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar los días válidos.");
        }

        if (descuentoStr == null || descuentoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar el descuento.");
        }
        if (fechaCal == null) {
            throw new IllegalArgumentException("Debe seleccionar una fecha de alta.");
        }


        int diasValidos;
        float descuento;
        try {
            diasValidos = Integer.parseInt(diasValidosStr);
            if (diasValidos <= 0) {
                throw new IllegalArgumentException("Los días válidos deben ser mayores a 0.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los días válidos deben ser un número entero.");
        }

        try {
            descuento = Float.parseFloat(descuentoStr);
            if (descuento < 0 || descuento > 100) {
                throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El descuento debe ser un número.");
        }

        // Comparamos solo la fecha, sin hora
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        Calendar fechaAlta = (Calendar) fechaCal.clone();
        fechaAlta.set(Calendar.HOUR_OF_DAY, 0);
        fechaAlta.set(Calendar.MINUTE, 0);
        fechaAlta.set(Calendar.SECOND, 0);
        fechaAlta.set(Calendar.MILLISECOND, 0);

        if (fechaAlta.before(hoy)) {
            throw new IllegalArgumentException("La fecha de alta no puede ser pasada.");
        }

        // ------------------- CONVERSIONES -------------------
        DTFecha fechaAltaDT = new DTFecha(
                fechaCal.get(Calendar.DAY_OF_MONTH),
                fechaCal.get(Calendar.MONTH) + 1,
                fechaCal.get(Calendar.YEAR)
        );

    }
}