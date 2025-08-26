package presentacion.helpers;

import presentacion.helpers.*;
import logica.clase.Sistema;
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

public class UsuarioHelper {

    // Método para limpiar campos de texto de un formulario
    public static void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    // Método para actualizar un JTable con la lista de usuarios
//    public static void actualizarTablaUsuarios(JTable tablaUsuarios) {
//        if (tablaUsuarios == null) return;
//
//        // Definir las columnas
//        String[] columnas = {"Tipo", "Nickname", "Nombre", "Correo"};
//        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
//
//        try {
//            List<DTUsuario> usuarios = Sistema.getInstance().consultarUsuarios();
//            for (DTUsuario u : usuarios) {
//                String tipo;
//                if (u instanceof DTCliente) {
//                    tipo = "Cliente";
//                } else if (u instanceof DTAerolinea) {
//                    tipo = "Aerolínea";
//                } else {
//                    tipo = "Desconocido";
//                }
//
//                Object[] fila = {
//                        tipo,
//                        u.getNickname(),
//                        u.getNombre(),
//                        u.getCorreo()
//                };
//                modeloTabla.addRow(fila);
//            }
//        } catch (IllegalStateException ex) {
//            JOptionPane.showMessageDialog(
//                    tablaUsuarios,
//                    ex.getMessage(),
//                    "Información",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//        }
//
//        // Aplicar el modelo nuevo a la tabla
//        tablaUsuarios.setModel(modeloTabla);
//    }
//
//
//    // Para mostrar usuarios en consola
//    public static void comprobarUsuarios(){
//        try {
//            List<DTUsuario> lista = Sistema.getInstance().consultarUsuarios();
//            for (DTUsuario u : lista) {
//                System.out.println(u.getNickname() + " | " + u.getNombre() + " | " + u.getCorreo());
//            }
//            JOptionPane.showMessageDialog(null, "Usuarios listados en consola.");
//        } catch (IllegalStateException ex) {
//            JOptionPane.showMessageDialog(null, "No hay usuarios cargados.");
//        }
//    }

    // para validar campos de usuario antes de crearlo
    public static boolean validarCliente(
            JTextField nickname,
            JTextField nombre,
            JTextField apellido,
            JTextField correo,
            JTextField nacionalidad,
            TipoDoc tipoDocumento,
            JTextField numeroDocumento
    ) {
        String nick = nickname.getText().trim();
        String nom = nombre.getText().trim();
        String ape = apellido.getText().trim();
        String mail = correo.getText().trim();
        String nac = nacionalidad.getText().trim();
        String doc = numeroDocumento.getText().trim();


        if (nick.isEmpty() || nom.isEmpty() || ape.isEmpty() || mail.isEmpty() || nac.isEmpty() || doc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nickname mínimo 4
        if (nick.length() < 4) {
            JOptionPane.showMessageDialog(null, "El nickname debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nombre y apellido solo letras
        if (!nom.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!ape.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El apellido solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nacionalidad
        if (!nac.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "La nacionalidad solo puede contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Correo
        if (!mail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(null, "Ingrese un correo electrónico válido (ej: usuario@dominio.com).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // --- Validar documento según tipo ---
        switch (tipoDocumento) {
            case CI: // Cédula uruguaya
                // Formato: 12345678 o 1.234.567-8
                if (!doc.matches("^[0-9]{7,8}$") && !doc.matches("^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9]$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese una CI uruguaya válida (ej: 12345678 o 1.234.567-8).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case Pasaporte:
                // Letras + números, entre 6 y 9 caracteres
                if (!doc.matches("^[A-Z0-9]{6,9}$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese un pasaporte válido (ej: A1234567).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case DNI:
                // 7-8 dígitos + opcional una letra
                if (!doc.matches("^[0-9]{7,8}[A-Za-z]?$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese un DNI válido (ej: 12345678 o 12345678A).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de documento válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }

        return true;
    }



    public static void crearCliente(String nickname,
                                    String nombre,
                                    String correo,
                                    String apellido,
                                    String nacionalidad,
                                    TipoDoc tipoDocumento,
                                    String numeroDocumento,
                                    Date fechaNacimiento) throws Exception {

        // Convertir Date a DTFecha
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH) + 1;
        int anio = cal.get(Calendar.YEAR);
        DTFecha fechaNac = new DTFecha(dia, mes, anio);

        // Llamar a la función de Sistema
        Sistema.getInstance().altaCliente(
                nickname,
                nombre,
                correo,
                apellido,
                fechaNac,
                nacionalidad,
                tipoDocumento,
                numeroDocumento
        );
    }

    // reiniciar ComboBox y Calendar
    public static void resetFormulario(JComboBox<?> combo, JCalendar calendario, JTextField primerCampo) {
        if (combo != null) combo.setSelectedIndex(0);
        if (calendario != null) calendario.setDate(new Date());
        if (primerCampo != null) primerCampo.requestFocus();
    }

    /// ////// Aerolinea ///////////
    public static boolean validarAerolinea(
            JTextField nickname,
            JTextField nombre,
            JTextField correo,
            JTextField sitioWeb,
            JTextArea descripcion
    ) {
        // Validar nickname
        if (nickname.getText().trim().length() < 4) {
            JOptionPane.showMessageDialog(null, "El nickname debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar nombre
        if (nombre.getText().trim().length() < 2) {
            JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 2 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar correo
        String correoText = correo.getText().trim();
        if (!correoText.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "El correo electrónico no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        /*// Validar sitio web
        String sitioText = sitioWeb.getText().trim();
        if (!sitioText.matches("^(http://|https://).+\\..+")) {
            JOptionPane.showMessageDialog(null, "El sitio web debe comenzar con http:// o https:// y ser válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }*/

        // Validar descripción
        if (descripcion.getText().trim().length() < 10) {
            JOptionPane.showMessageDialog(null, "La descripción debe tener al menos 10 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    public static void crearAerolinea(
            String nickname,
            String nombre,
            String correo,
            String sitioWeb,
            String descripcion
    ) throws Exception {
        // Validaciones
        if (!validarAerolinea(
                new JTextField(nickname),
                new JTextField(nombre),
                new JTextField(correo),
                new JTextField(sitioWeb),
                new JTextArea(descripcion)
        )) {
            throw new Exception("Datos de la aerolínea inválidos.");
        }

        // Llamada al sistema
        Sistema.getInstance().altaAerolinea(
                nickname,
                nombre,
                correo,
                descripcion,
                sitioWeb
        );
    }

    public static void resetFormularioAerolinea(
            JTextField nickname,
            JTextField nombre,
            JTextField correo,
            JTextField sitioWeb,
            JTextPane descripcion
    ) {
        limpiarCampos(nickname, nombre, correo, sitioWeb);
        descripcion.setText("");
        nickname.requestFocus();
    }



}
