package presentacion.helpers;

import logica.clase.Factory;
import logica.clase.ISistema;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentEvent;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class ReservaHelper {

    // Método helper para obtener la instancia de ISistema a través del Factory
    private static ISistema getSistema() {
        Factory factory = new Factory();
        return factory.getSistema();
    }

    // ------------------- LISTAR AEROLÍNEAS PARA RESERVA -------------------
    public static void cargarAerolineasEnTabla(JTable tabla) {
        try {
            List<DTAerolinea> aerolineas = getSistema().listarAerolineas();
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0); // Limpiar tabla
            
            for (DTAerolinea a : aerolineas) {
                model.addRow(new Object[]{
                    a.getNickname(),
                    a.getNombre(),
                    a.getCorreo(),
                    a.getDescripcion(),
                    a.getLinkSitioWeb()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar aerolíneas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------- LISTAR RUTAS DE VUELO -------------------
    public static List<DTRutaVuelo> obtenerRutasDeAerolinea(String nicknameAerolinea) {
        try {
            return getSistema().listarRutaVuelo(nicknameAerolinea);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener rutas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    // ------------------- LISTAR VUELOS DE UNA RUTA -------------------
    public static List<DTVuelo> obtenerVuelosDeRuta(String nombreRuta) {
        try {
            return getSistema().seleccionarRutaVuelo(nombreRuta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener vuelos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    // ------------------- CONFIGURAR SELECCIÓN DE CLIENTE -------------------
    public static void configurarSeleccionCliente(JTable tablaClientes, JTextField campoCliente) {
        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1) { // Un solo clic
                    int filaSeleccionada = tablaClientes.getSelectedRow();
                    if (filaSeleccionada >= 0) {
                        // Obtener el nickname del cliente (primera columna)
                        String nickname = (String) tablaClientes.getValueAt(filaSeleccionada, 0);
                        if (nickname != null && !nickname.trim().isEmpty()) {
                            campoCliente.setText(nickname.trim());
                            System.out.println("Cliente seleccionado: " + nickname.trim());
                            
                            // Mostrar mensaje informativo
                            String nombreCliente = (String) tablaClientes.getValueAt(filaSeleccionada, 1);
                            String apellidoCliente = (String) tablaClientes.getValueAt(filaSeleccionada, 2);
                            JOptionPane.showMessageDialog(null, 
                                "Cliente seleccionado: " + nombreCliente + " " + apellidoCliente + 
                                "\nNickname: " + nickname.trim(),
                                "Cliente Seleccionado", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    // ------------------- LISTAR CLIENTES -------------------
    public static void cargarClientesEnTabla(JTable tabla) {
        try {
            List<DTCliente> clientes = getSistema().listarClientes();
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0); // Limpiar tabla
            
            for (DTCliente c : clientes) {
                model.addRow(new Object[]{
                    c.getNickname(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getCorreo(),
                    c.getNacionalidad()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------- SELECCIONAR VUELO PARA RESERVA -------------------
    public static void seleccionarVuelo(String nombreVuelo) {
        try {
            getSistema().seleccionarVueloParaReserva(nombreVuelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar vuelo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------- REALIZAR RESERVA -------------------
    public static void realizarReserva(
            TipoAsiento tipoAsiento,
            int cantidadPasajes,
            int equipajeExtra,
            List<String> nombresPasajeros,
            JCalendar fechaReservaCalendar,
            String nombrePasajeroAdicional,
            String apellidoPasajeroAdicional) throws Exception {

        // Probar conexión a BD antes de proceder
        System.out.println("Probando conexión a BD...");
        getSistema().probarConexionBD();

        // Validaciones
        if (nombresPasajeros == null || nombresPasajeros.isEmpty()) {
            throw new Exception("Debe ingresar al menos un pasajero.");
        }

        if (cantidadPasajes <= 0) {
            throw new Exception("La cantidad de pasajes debe ser mayor a 0.");
        }

        if (equipajeExtra < 0) {
            throw new Exception("El equipaje extra no puede ser negativo.");
        }

        if (fechaReservaCalendar == null) {
            throw new Exception("Debe seleccionar una fecha de reserva.");
        }

        // Validar fecha de reserva (no puede ser anterior a hoy)
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        Calendar fechaReserva = (Calendar) fechaReservaCalendar.getCalendar().clone();
        fechaReserva.set(Calendar.HOUR_OF_DAY, 0);
        fechaReserva.set(Calendar.MINUTE, 0);
        fechaReserva.set(Calendar.SECOND, 0);
        fechaReserva.set(Calendar.MILLISECOND, 0);

        if (fechaReserva.before(hoy)) {
            throw new Exception("La fecha de reserva no puede ser anterior al día de hoy.");
        }

        // Convertir fecha
        DTFecha fechaReservaDT = new DTFecha(
                fechaReserva.get(Calendar.DAY_OF_MONTH),
                fechaReserva.get(Calendar.MONTH) + 1,
                fechaReserva.get(Calendar.YEAR)
        );

        // Realizar la reserva
        try {
            getSistema().datosReserva(tipoAsiento, cantidadPasajes, equipajeExtra, nombresPasajeros, fechaReservaDT);
            JOptionPane.showMessageDialog(null, "Reserva realizada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error inesperado al realizar la reserva: " + e.getMessage());
        }
    }

    // ------------------- VALIDAR DATOS DE PASAJERO -------------------
    public static void validarDatosPasajero(String nombre, String apellido) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del pasajero es obligatorio.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("El apellido del pasajero es obligatorio.");
        }
    }

    // ------------------- CONVERTIR TIPO ASIENTO -------------------
    public static TipoAsiento convertirTipoAsiento(String tipoAsientoStr) throws Exception {
        if (tipoAsientoStr == null || tipoAsientoStr.trim().isEmpty()) {
            throw new Exception("Debe seleccionar un tipo de asiento.");
        }
        
        switch (tipoAsientoStr.toLowerCase()) {
            case "turista":
                return TipoAsiento.Turista;
            case "ejecutivo":
                return TipoAsiento.Ejecutivo;
            default:
                throw new Exception("Tipo de asiento inválido. Debe ser 'Turista' o 'Ejecutivo'.");
        }
    }

    // ------------------- AGREGAR PASAJEROS ADICIONALES -------------------
    public static List<String> obtenerPasajerosParaReserva(
            String clientePrincipal,
            int cantidadPasajes,
            JTextField campoNombre,
            JTextField campoApellido) throws Exception {
        
        List<String> nombresPasajeros = new ArrayList<>();
        
        // Siempre agregar el cliente principal como primer pasajero
        nombresPasajeros.add(clientePrincipal);
        
        // Si hay más de un pasaje, agregar pasajeros adicionales
        if (cantidadPasajes > 1) {
            // Verificar si hay datos específicos para pasajeros adicionales
            if (campoNombre != null && campoApellido != null && 
                !campoNombre.getText().trim().isEmpty() && 
                !campoApellido.getText().trim().isEmpty()) {
                
                // Crear pasajeros adicionales con los datos ingresados
                for (int i = 1; i < cantidadPasajes; i++) {
                    // Por ahora, usar el cliente principal pero con datos específicos
                    // En el futuro se podría crear clientes temporales
                    nombresPasajeros.add(clientePrincipal);
                }
            } else {
                // Si no hay datos específicos, usar el cliente principal para todos
                for (int i = 1; i < cantidadPasajes; i++) {
                    nombresPasajeros.add(clientePrincipal);
                }
            }
        }
        
        return nombresPasajeros;
    }
    
    // ------------------- CREAR PASAJERO TEMPORAL -------------------
    public static void crearPasajeroTemporal(
            String nombre,
            String apellido,
            String nicknameCliente) throws Exception {
        
        // Validar datos
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del pasajero es obligatorio.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("El apellido del pasajero es obligatorio.");
        }
        if (nicknameCliente == null || nicknameCliente.trim().isEmpty()) {
            throw new Exception("El nickname del cliente es obligatorio.");
        }
        
        // Por ahora, solo validamos. En el futuro se podría crear un cliente temporal
        // o almacenar los datos de manera diferente
    }

    // ------------------- LIMPIAR FORMULARIOS -------------------
    public static void limpiarFormularioReserva(
            JTextField campoAerolinea,
            JTextField campoRuta,
            JTextField campoVuelo,
            JTextField campoCliente,
            JComboBox<TipoAsiento> comboBoxTipoAsiento,
            JSpinner spinnerCantidadPasajes,
            JSpinner spinnerEquipajeExtra,
            JCalendar fechaReserva) {
        
        if (campoAerolinea != null) campoAerolinea.setText("");
        if (campoRuta != null) campoRuta.setText("");
        if (campoVuelo != null) campoVuelo.setText("");
        if (campoCliente != null) campoCliente.setText("");
        if (comboBoxTipoAsiento != null) {
            comboBoxTipoAsiento.setSelectedIndex(0); // Seleccionar Turista por defecto
        }
        if (spinnerCantidadPasajes != null) spinnerCantidadPasajes.setValue(1);
        if (spinnerEquipajeExtra != null) spinnerEquipajeExtra.setValue(0);
        if (fechaReserva != null) fechaReserva.setCalendar(Calendar.getInstance());
    }
    
    // ------------------- LIMPIAR FORMULARIOS DE PASAJE -------------------
    public static void limpiarFormularioPasaje(
            JTextField campoNombre,
            JTextField campoApellido,
            JSpinner spinnerEquipajeExtra) {
        
        if (campoNombre != null) campoNombre.setText("");
        if (campoApellido != null) campoApellido.setText("");
        if (spinnerEquipajeExtra != null) spinnerEquipajeExtra.setValue(0);
    }
}
