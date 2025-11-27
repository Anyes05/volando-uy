package presentacion;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dato.entidades.Categoria;
import dato.entidades.RutaVuelo;
import presentacion.helpers.*;
import logica.DataTypes.*;
import logica.clase.Factory;
import logica.clase.ISistema;

import com.toedter.calendar.JCalendar;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.StyleContext;
import java.time.LocalDate;
import java.util.List;

import static presentacion.helpers.EstacionTrabajoHelper.cargarRutasMasVisitadas;


public class EstacionTrabajo {
    private JPanel mainPrincipal;
    private JPanel altaVuelo;
    private JComboBox aerolinea;
    private JPanel fromAltaVuelo;
    private JTextField nombreAltaVuelotxt;
    private JTextField asientosMaxTuristatxt;
    private JTextField asientoMaxEjecutivotxt;
    private JCalendar fechaAltaRutaVuelo;
    private JButton buttonAltaVuelo;
    private JPanel altaUsuario;
    private JPanel altaAerolinea;
    private JButton ButtonCrearNuevoCliente;
    private JButton ButtonCrearNuevaAerolinea;
    private JPanel altaCliente;
    private JTextField apellidoAltaCliente;
    private JTextField nombreAltaCliente;
    private JTextField nicknameAltaCliente;
    private JTextField nacionalidadAltaCliente;
    private JComboBox comboBoxAltaCliente;
    private JButton cancelarAltaClienteButton;
    private JButton aceptarAltaClienteButton;
    private JTextPane altaAerolineaDescripcion;

    private JPanel modificarUsuario;
    private JTextField modificarUsuarioTextInput;

    private JPanel parentPanel;
    private JPanel buttonsPanel;
    private JButton botonInicio;
    private JComboBox comboBoxVuelos;
    private JComboBox comboBoxUsuario;
    private JPanel consultaRutaVuelo;
    private JToolBar JToolBarPrincipal;
    private JList listaPasajerosReservaVJlist;
    private JComboBox comboBoxAeroRVConsulta;
    private JTextArea costoBaseRVConsulta;
    private JTextArea fechaAltaRVConsulta;
    private JRadioButton rutaDeVueloRadioButton;
    private JRadioButton reservaDeVueloRadioButton;
    private JTextField descuentoAltaPaqtxt;
    private JRadioButton paqueteVueloRadioButton;
    private JRadioButton usuarioRadioButton;
    private JPanel principalVacio;
    private JPanel altaRuta;
    private JCalendar fechaVuelo;
    private JCalendar fechaAltaVuelo;
    private JPanel consultaVuelo;
    private JComboBox comboBoxRutasVuelosConsultaV;
    private JComboBox aerolineaVuelo;
    private JComboBox<DTRutaVuelo> rutasVueloAltaVuelo;
    private JTextArea descRutaText;
    private JTextField correoAltaCliente;
    private JCalendar JCalendarAltaCliente;
    private JTextField numeroDocAltaCliente;
    private JButton altaAerolineaCancelar;
    private JButton altaAerolineaAceptar;
    private JTextField altaAerolineaNickname;
    private JTextField altaAerolineaNombre;
    private JTextField altaAerolineaCorreo;
    private JTextField altaAerolineaLinkWeb;
    private JButton aceptarRuta;
    private JTextField horaText;
    private JTextField costoTurText;
    private JTextField costoEjText;
    private JTextField costoEqExText;
    private JComboBox comboBoxCat;
    private JPanel altaCiudad;
    private JTextField ciudadAltaText;
    private JButton buttonAltaCiudad;
    private JLabel DescCiudadText;
    private JLabel SitioWebCiuText;
    private JTextField descripcionAltaCiText;
    private JTextField sitioWebAltaCiText;
    private JCalendar calendarCiudadAlta;
    private JLabel FechaAltaCiudText;
    private JLabel CiudadAltaText;
    private JLabel PaisAltaText;
    private JTextField paisAltaCiText;
    private JPanel altaCategoría;
    private JTextField categoriaAltaText;
    private JButton buttonAltaCategoria;
    private JTextField nombreAltRutaText;
    private JTextField duracionAltaVuelotxt;
    private JList<String> listCatAltaRuta;
    private JComboBox<DTRutaVuelo> comBoxRutVueloConsultaRV;
    private JComboBox vuelosConsultaRV;
    private JTextArea descripcionRVConsulta;
    private JTextArea costoBaseEjRVConsulta;
    private JTextArea ciudadORVConsulta;
    private JTextArea ciudadDRVConsulta;
    private JTextArea costoBaseTuRVConsulta;
    private JLabel costoEquipajeExRVConsulta;
    private JTextArea costoUnEquipajeExRVConsulta;
    private JTextField horaVuelotxt;


    //CONSULTA USUARIO
    private JScrollPane consultaUsuarioScroll;
    private JPanel consultaUsuario;
    private JPanel consultaUsuarioParentPanel;
    private JPanel consultaUsuarioJpanel1;
    private JTable consultaUsuarioTable1;
    private JTable consultaUsuarioTable2;
    private JButton consultaUsuarioAceptar;
    private JTextField consultaUsuarioText;

    // MODIFICAR USUARIO - CLIENTE AEROLINEA
    private JPanel modificarUsuarioJPanel1;
    private JTable modificarUsuariotable1;
    private JPanel modificarAerolinea;
    private JTextField modificarAerolineaTextNombre;
    private JTextField modificarAerolineaTextLink;
    private JTextArea modificarAerolineaTextArea;
    private JButton modificarAerolineaGuardar;
    private JPanel modificarCliente;
    private JTextField modificarClienteNombre;
    private JTextField modificarClienteApellido;
    private JTextField modificarClienteNacionalidad;
    private JComboBox modificarClienteComboBox;
    private JTextField modificarClienteDocumento;
    private JCalendar modificarClienteJCalendar;
    private JButton modificarClienteGuardar;
    private JButton modificarAerolineaCancelar;
    private JButton modificarClienteCancelar;
    private JButton modificarUsuarioAceptar;
    private JButton modificarUsuarioCancelar;

    //
    private JButton precargarAeropuertosButton;
    private JComboBox comboBoxPaquetes;
    private JPanel crearPaquete;
    private JTextField nombreAltaPaqtxt;
    private JTextField descripcionAltaPaqtxt;
    private JTextField períodoAltaPaqtxt;
    private JCalendar calendarAltaPaquete;
    private JButton buttonCancelarCrearPaquete;
    private JButton buttonCrearPaquete;
    private JComboBox comboBoxAeroConsultaV;
    private JTextPane descripcionConsultaVtxt;
    private JTextArea costoBaseConsultaVtxt;
    private JComboBox<DTVuelo> comboBoxVuelosConsultaV;
    private JTextArea nombVueloConsultaVtxt;
    private JTextArea fechaVueloConsultaVtxt;
    private JTextArea horaVueloConsultaVtxt;
    private JTextArea duracionVueloConsultaVtxt;
    private JTextArea maxTuristaConsultaVtxt;
    private JTextArea maxEjecutivoConsultaVtxt;
    private JTextArea fechaAltaVueloConsultaVtxt;
    private JLabel AeropuertoCiuText;
    private JTextArea fechaAlta;
    private JTextArea fechaAltaConsultaVtxt;
    private JButton buttonCancelarCiudad;
    private JButton buttonCancelarCrearRutaV;
    private JButton buttonCancelarVuelo;
    private JPanel agregarRutaaPaquete;
    private JComboBox comboBoxPaqueteAgrRutaaPaquete;
    private JComboBox<String> comboBoxAeroAgrRutaaPaquete;
    private JComboBox comboBoxRutaVueloAgrRutaaPaquete;
    private JTextField cantidadAgrRutaaPaquetetxt;
    private JButton buttonAceptarAgrRutaaPaquete;
    private JButton buttonCancelarAgrRutaaPaquete;
    private JComboBox comboBoxTipoAsientoAgrRutaaPaquete;
    private JRadioButton tipoEjecutivoAgrRutaaPaquete;
    private JRadioButton tipoTuristaAgrRutaaPaquete;

    //RESERVA VUELO
    private JPanel reservaVuelo;
    private JTable reservaVueloTablePrincipal;
    private JPanel reservaVueloParentPanel;
    private JPanel reservaVueloSeleccionarAerolinea;
    private JTextField reservaVueloSeleccionarAerolineaInput;
    private JButton reservaVueloSeleccionarAerolineaAceptar;
    private JPanel reservaVueloSeleccionarRutaVuelo;
    private JButton reservaVueloSeleccionarRutaVueloAceptar;
    private JTextField reservaVueloSeleccionarRutaVueloInput;
    private JPanel reservaVueloSeleccionarVuelo;
    private JTextField reservaVueloSeleccionarVueloInput;
    private JButton reservaVueloSeleccionarVueloAceptar;
    private JTable reservaVueloListarUsuariosTable;
    private JTextField reservaVueloSeleccionarUsuarioCliente;
    private JPanel reservaVueloSeleccionarUsuarioJPanel;
    private JComboBox<TipoAsiento> reservaVueloSeleccionarUsuarioTipoAsiento;
    private JButton reservaVueloSeleccionarUsuarioAceptar;
    private JSpinner reservaVueloSeleccionarUsuarioCantidadPasajes;
    private JSpinner reservaVueloSeleccionarUsuarioCantidadEquipajeExtra;
    private JCalendar reservaVueloSeleccionarUsuarioJCalendar;
    private JPanel reservaVueloPasajes;
    private JTextField apellidoTextField;
    private JTextField reservaVueloPasajesApellido;
    private JSpinner reservaVueloPasajesCantidadEquipajeExtra;
    private JButton aceptarButton;

    //CONSULTA PAQUETE DE RUTA DE VUELO
    private JPanel consultaPaqueteRutaVuelo;
    private JPanel consultaPaqueteRutaVueloJPanelTable;
    private JPanel consultaPaqueteRutaVueloParentPanel;
    private JPanel consultaPaqueteRutaVueloSeleccionarPaquete;
    private JTextField consultaPaqueteRutaVueloSeleccionarPaqueteInput;
    private JButton consultaPaqueteRutaVueloSeleccionarPaqueteCancelar;
    private JButton consultaPaqueteRutaVueloSeleccionarPaqueteAceptar;
    private JPanel ComprarPaquete;
    private JComboBox comboBoxPaquetesComprarPaquete;
    private JComboBox comboBoxClientesComprarPaquete;
    private JButton aceptarComprarPaquete;
    private JButton cancelarButtonComprarPaquete;
    private JButton listarUsuariosButton;
    private JComboBox comboBox1;
    private JPanel ConsultaPaquete;
    private JComboBox comboBoxPaqueteConsultaPaquete;
    private JTextArea descripcionCPtxt;
    private JTextArea diasvalidosCPtxt;
    private JTextArea descuentoCPtxt;
    private JTextArea costoCPtxt;
    private JTextArea fechaAltaCPtxt;
    private JComboBox comboBoxRutasVueloCP;
    private JButton consultaUsuarioCancelar;
    private JComboBox comboBoxAeroReservaV;
    private JComboBox comboBoxRutasVueloReservaV;
    private JComboBox comboBoxVuelosReservaV;
    private JTextArea nombreVueloReservaV;
    private JTextArea fechaVReservaV;
    private JTextArea horaVReservaV;
    private JTextArea duracionVReservaV;
    private JTextArea cantidadAsientosTReservaV;
    private JTextArea cantidadAsientosEReservaV;
    private JTextArea fechaAltaVReservaV;
    private JComboBox comboBoxClienteReservaV;
    private JComboBox comboBoxTipoAsientoReservaV;
    private JTextField cantPasajesReservaVtxt;
    private JTextField equipajeExtraReservaV;
    private JCalendar fechaReservaV;
    private JButton buttonCancelarReservaV;
    private JButton buttonAceptarReservaV;
    private JCalendar fechaReservaVJC;
    private JComboBox comboBoxPasajerosReservaV;
    private JButton buttonAgregarPasajeroReservaV;
    private JButton buttonAceptarReservaVuelo;
    
    // Panel de Administración de Rutas de Vuelo
    private JPanel adminRutasPanel;
    private JComboBox adminRutasAerolineaCombo;
    private JComboBox adminRutasRutaCombo;
    private JButton adminRutasAceptarButton;
    private JButton adminRutasRechazarButton;
    private JButton adminRutasRecargarButton;
    private JButton buttonQuitarPasajeroReservaV;
    private JComboBox comboBoxReservasConsultaV;
    private JComboBox comboBoxAeropuertosAC;
    private JComboBox comboBoxCiudadOrigenARV;
    private JComboBox comboBoxCiudadDestinoARV;
    private JTextArea categoriasRVtxt;
    private JButton buttonImagenAltaVuelo;
    private JLabel labelImagenCVuelo;
    private JButton buttonImagenRVuelo;
    private JLabel labelImagenRVuelo;
    private JButton buttonImagenPaquete;
    private JList list1;
    private JTextArea cantyTipoAsientotxt;
    private JScrollPane scrollReservaVuelo;
    private JScrollPane scrollConsultaRV;

    //Imagenes
    private JLabel labelImagenVuelo;
    private String rutaImagenVuelo;
    private String rutaImagenRVuelo;
    private String rutaImagenPaquete;
    private JLabel labelImagenPaquete;
    private JButton altaAerolineaSubirImagen;
    private JButton altaClienteSubirImagen;
    private JLabel altaClienteMostrarFoto;
    private JLabel altaAerolineaMostrarFoto;
    private JButton modificarAerolineaSubirImagen;
    private JLabel modificarAerolineaMostrarFoto;
    private JButton modificarClienteSubirImagen;
    private JLabel modificarClienteMostrarFoto;
    private JPasswordField modificarAerolineaContrasena;
    private JPasswordField modificarAerolineaConfirmarContrasena;
    private JPasswordField altaAerolineaContrasena;
    private JPasswordField altaAerolineaConfirmarContrasena;
    private JPasswordField altaClienteContrasena;
    private JPasswordField altaClienteConfirmarContrasena;
    private JPasswordField modificarClienteContrasena;
    private JPasswordField modificarClienteConfirmarContrasena;

    //CONSULTAR RUTAS MAS VISITADAS
    private JPanel consultarRutasMasVisitadas;
    private JScrollPane consultarrutasmasvisitadasjscrollpane;
    private JTable consultarRutasMasVisitadasTable;

    byte[] fotoSeleccionada = null;
    private String contrasenaActual = null;

    // Método helper para actualizar la lista de pasajeros en la interfaz
    private void actualizarListaPasajeros() {
        if (listaPasajerosReservaVJlist != null) {
            listaPasajerosReservaVJlist.setListData(nombresPasajeros.toArray(new String[0]));
        }
    }

    // Método helper para limpiar todos los campos del formulario de reserva
    private void limpiarFormularioReserva() {
        try {
            // Limpiar lista de pasajeros
            nombresPasajeros.clear();
            actualizarListaPasajeros();

            // Limpiar campos de texto
            if (cantPasajesReservaVtxt != null) {
                cantPasajesReservaVtxt.setText("");
            }
            if (equipajeExtraReservaV != null) {
                equipajeExtraReservaV.setText("");
            }

            // Limpiar selecciones de combo boxes
            if (comboBoxTipoAsientoReservaV != null) {
                comboBoxTipoAsientoReservaV.setSelectedIndex(-1);
            }
            if (comboBoxClienteReservaV != null) {
                comboBoxClienteReservaV.setSelectedIndex(-1);
            }
            if (comboBoxPasajerosReservaV != null) {
                comboBoxPasajerosReservaV.setSelectedIndex(-1);
            }

            // Limpiar fecha de reserva (establecer fecha actual)
            if (fechaReservaVJC != null) {
                fechaReservaVJC.setDate(new Date());
            }

        } catch (Exception e) {
            // Si hay algún error al limpiar, no hacer nada para evitar más errores
            System.err.println("Error al limpiar formulario: " + e.getMessage());
        }
    }

    // Método helper para quitar un pasajero de la lista
    private void quitarPasajero(String nicknamePasajero) {
        if (nombresPasajeros.contains(nicknamePasajero)) {
            nombresPasajeros.remove(nicknamePasajero);
            actualizarListaPasajeros();
            JOptionPane.showMessageDialog(null, "Pasajero quitado exitosamente: " + nicknamePasajero,
                    "Pasajero Quitado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "ERROR: El pasajero " + nicknamePasajero +
                    " no está en la lista de pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean cargandoAeroRV = false;//estos booleanos son para la carga de los comboBox, porque sino no me funcionaba
    private boolean cargandoRutasRV = false;
    private boolean cargandoVuelosRV = false;
    private boolean cargandoVuelos = false;
    private boolean cargandoPaquete = false;
    private boolean cargandoRutasCP = false;
    private boolean consultaCP = false;

    private ISistema sistema; // Variable para almacenar la instancia de ISistema obtenida a través del Factory

    // Lista de pasajeros para reservas
    public List<String> nombresPasajeros = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Estación de Trabajo - VolandoUy");
                EstacionTrabajo estacion = new EstacionTrabajo();
                
                // Intentar acceder al campo mainPrincipal usando reflexión
                JPanel mainPanel = null;
                try {
                    Field field = EstacionTrabajo.class.getDeclaredField("mainPrincipal");
                    field.setAccessible(true);
                    mainPanel = (JPanel) field.get(estacion);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    System.err.println(">>> ADVERTENCIA: No se pudo acceder al panel principal usando reflexión: " + e.getMessage());
                }

                if (mainPanel != null) {
                    frame.setContentPane(mainPanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    System.out.println(">>> Estación de Trabajo iniciada correctamente.");
                } else {
                    // Si mainPrincipal es null, mostrar un mensaje de error más útil
                    System.err.println(">>> ERROR: El panel principal (mainPrincipal) es null.");
                    System.err.println(">>> Los componentes GUI no se inicializaron correctamente.");
                    System.err.println(">>> ");
                    System.err.println(">>> SOLUCIÓN: Este problema ocurre cuando se ejecuta desde un JAR.");
                    System.err.println(">>> La Estación de Trabajo requiere que los componentes GUI se inicialicen");
                    System.err.println(">>> desde el archivo .form, lo cual solo funciona cuando se ejecuta desde el IDE.");
                    System.err.println(">>> ");
                    System.err.println(">>> Para usar la Estación de Trabajo:");
                    System.err.println(">>> 1. Abra el proyecto en IntelliJ IDEA");
                    System.err.println(">>> 2. Ejecute EstacionTrabajo.main() desde el IDE");
                    System.err.println(">>> ");
                    System.err.println(">>> O use el Web Service con: java -jar servidor.jar ws");

                    // Crear un panel de error simple
                    JPanel errorPanel = new JPanel(new BorderLayout());
                    JTextArea errorText = new JTextArea(
                        "ERROR: No se pudo inicializar la Estación de Trabajo.\n\n" +
                        "Los componentes GUI no se cargaron correctamente.\n\n" +
                        "Esto ocurre cuando se ejecuta desde un JAR porque los componentes\n" +
                        "GUI generados por IntelliJ IDEA solo se inicializan correctamente\n" +
                        "cuando se ejecuta desde el IDE.\n\n" +
                        "SOLUCIÓN:\n" +
                        "1. Abra el proyecto en IntelliJ IDEA\n" +
                        "2. Ejecute EstacionTrabajo.main() desde el IDE\n\n" +
                        "O use el Web Service con:\n" +
                        "java -jar servidor.jar ws"
                    );
                    errorText.setEditable(false);
                    errorText.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    errorPanel.add(new JScrollPane(errorText), BorderLayout.CENTER);

                    frame.setContentPane(errorPanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(600, 400);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            } catch (Exception e) {
                System.err.println(">>> ERROR crítico al iniciar la Estación de Trabajo: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void createUIComponents() {
        fechaVuelo = new JCalendar();
        fechaAltaRutaVuelo = new JCalendar();
        fechaAltaVuelo = new JCalendar();
        JCalendarAltaCliente = new JCalendar();
        calendarCiudadAlta = new JCalendar();
        calendarAltaPaquete = new JCalendar();
        reservaVueloSeleccionarUsuarioJCalendar = new JCalendar();
        listCatAltaRuta = new JList<>();
        modificarClienteJCalendar = new JCalendar();
        fechaReservaVJC = new JCalendar();
        listaPasajerosReservaVJlist = new JList<>();
        listCatAltaRuta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        reservaVueloSeleccionarUsuarioTipoAsiento = new JComboBox<>(TipoAsiento.values());
        reservaVueloSeleccionarUsuarioTipoAsiento.setSelectedIndex(0); // Seleccionar Turista por defecto


        // --- NUEVO: panel, tabla y scroll para "Rutas Más Visitadas"
        consultarRutasMasVisitadas = new JPanel(new BorderLayout());
        consultarRutasMasVisitadasTable = new JTable();
        consultarrutasmasvisitadasjscrollpane = new JScrollPane(consultarRutasMasVisitadasTable);

        consultarRutasMasVisitadas.add(consultarrutasmasvisitadasjscrollpane, BorderLayout.CENTER);

}

    // Método helper para crear paneles de fallback cuando los paneles del .form no están disponibles
    private JPanel crearPanelFallback(String titulo, String mensaje) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font(tituloLabel.getFont().getName(), Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(tituloLabel, BorderLayout.NORTH);

        JTextArea mensajeArea = new JTextArea(
            mensaje + "\n\n" +
            "SOLUCIÓN:\n" +
            "1. Abra el proyecto en IntelliJ IDEA\n" +
            "2. Vaya a File > Settings > Editor > GUI Designer\n" +
            "3. Cambie 'Generate GUI into' a 'Java source code'\n" +
            "4. Recompile el proyecto\n" +
            "5. O ejecute desde el IDE: EstacionTrabajo.main()"
        );
        mensajeArea.setEditable(false);
        mensajeArea.setLineWrap(true);
        mensajeArea.setWrapStyleWord(true);
        mensajeArea.setFont(new Font(mensajeArea.getFont().getName(), Font.PLAIN, 11));
        mensajeArea.setBackground(panel.getBackground());
        mensajeArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.add(mensajeArea, BorderLayout.CENTER);
        panel.add(centroPanel, BorderLayout.CENTER);

        return panel;
    }

    private void inicializarComboBoxTipoAsientoPaquete() {
        comboBoxTipoAsientoAgrRutaaPaquete.removeAllItems();
        for (TipoAsiento tipo : TipoAsiento.values()) {
            comboBoxTipoAsientoAgrRutaaPaquete.addItem(tipo);
        }
        comboBoxTipoAsientoAgrRutaaPaquete.setSelectedIndex(-1);
    }

    private void inicializarComboBoxTipoAsientoReserva() {
        comboBoxTipoAsientoReservaV.removeAllItems();
        for (TipoAsiento tipo : TipoAsiento.values()) {
            comboBoxTipoAsientoReservaV.addItem(tipo);
        }
        comboBoxTipoAsientoReservaV.setSelectedIndex(-1);
    }

    private void cargarAeropuertos(JComboBox<String> combo) {
        if (combo == null) return;
        combo.removeAllItems();
        for (String aero : sistema.listarAeropuertos()) {
            combo.addItem(aero);
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarCiudadesOrigen(JComboBox<String> combo) {
        if (combo == null) return;
        combo.removeAllItems(); // limpiar combo
        for (DTCiudad c : sistema.listarCiudades()) {
            combo.addItem(c.getNombre() + ", " + c.getPais());
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarCiudadesDestino(JComboBox<String> combo, String ciudadOrigen) {
        if (combo == null) return;
        combo.removeAllItems(); // limpiar combo
        List<DTCiudad> ciudades = sistema.listarCiudades();
        List<DTCiudad> ciudadesDestino = sistema.listarCiudadesDestino(ciudades, ciudadOrigen);
        for (DTCiudad c : ciudadesDestino) {
            combo.addItem(c.getNombre() + ", " + c.getPais());
        }
        combo.setSelectedIndex(-1);
    }


    private void cargarAerolineas(JComboBox<String> combo) {
        if (combo == null) return;
        combo.removeAllItems(); // limpiar combo por si ya tiene algo
        for (DTAerolinea a : sistema.listarAerolineas()) {
            combo.addItem(a.getNickname());
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarCategorias(JList<String> lista) {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (Categoria c : sistema.getCategorias()) {
            modelo.addElement(c.getNombre());
        }
        lista.setModel(modelo);
        lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    // Métodos para el panel de administración de rutas de vuelo
    private void cargarAerolineasParaAdministracion() {
        adminRutasAerolineaCombo.removeAllItems();
        try {
            for (DTAerolinea a : sistema.listarAerolineasParaAdministracion()) {
                adminRutasAerolineaCombo.addItem(a.getNickname());
            }
            adminRutasAerolineaCombo.setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar aerolíneas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarRutasIngresadas() {
        adminRutasRutaCombo.removeAllItems();
        try {
            String aerolineaSeleccionada = (String) adminRutasAerolineaCombo.getSelectedItem();
            if (aerolineaSeleccionada != null && !aerolineaSeleccionada.isEmpty()) {
                sistema.seleccionarAerolineaParaAdministracion(aerolineaSeleccionada);
                List<DTRutaVuelo> rutasIngresadas = sistema.listarRutasIngresadas();
                for (DTRutaVuelo r : rutasIngresadas) {
                    adminRutasRutaCombo.addItem(r.getNombre());
                }
                adminRutasRutaCombo.setSelectedIndex(-1);

                // Solo mostrar mensaje si no hay rutas, pero no como error
                if (rutasIngresadas.isEmpty()) {
                    adminRutasRutaCombo.addItem("No hay rutas en estado 'Ingresada'");
                    adminRutasRutaCombo.setEnabled(false);
                } else {
                    adminRutasRutaCombo.setEnabled(true);
                }
            }
        } catch (Exception e) {
            // Solo mostrar error si es un error real, no si simplemente no hay rutas
            if (!e.getMessage().contains("No hay rutas de vuelo en estado 'Ingresada'")) {
                JOptionPane.showMessageDialog(null, "Error al cargar rutas: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                adminRutasRutaCombo.addItem("No hay rutas en estado 'Ingresada'");
                adminRutasRutaCombo.setEnabled(false);
            }
        }
    }

    private void mostrarDatosRuta(String nicknameAerolinea, String nombreRuta) {
        DTRutaVuelo ruta = VueloHelper.getRutasDeAerolinea(nicknameAerolinea, nombreRuta);
        if (ruta != null) {
            descripcionRVConsulta.setText(ruta.getDescripcion());
            ciudadORVConsulta.setText(String.valueOf(ruta.getCiudadOrigen()));
            ciudadDRVConsulta.setText(String.valueOf(ruta.getCiudadDestino()));
            categoriasRVtxt.setText(ruta.toString2());
            costoBaseTuRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoTurista()));
            costoBaseEjRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoEjecutivo()));
            costoUnEquipajeExRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoEquipajeExtra()));
            fechaAltaRVConsulta.setText(ruta.getFechaAlta().toString());
        } else {
            VueloHelper.limpiarCampos(
                    descripcionRVConsulta,
                    ciudadORVConsulta,
                    ciudadDRVConsulta,
                    categoriasRVtxt,
                    costoBaseTuRVConsulta,
                    costoBaseEjRVConsulta,
                    costoUnEquipajeExRVConsulta,
                    fechaAltaRVConsulta
            );
            JOptionPane.showMessageDialog(parentPanel, "No se encontró la ruta.");
        }
    }

    private void cargarRutas(JComboBox<DTRutaVuelo> comboRutas, String nicknameAerolinea) {
        boolean esConsulta = (comboRutas == comBoxRutVueloConsultaRV);
        if (esConsulta) cargandoRutasRV = true;
        comboRutas.removeAllItems(); // Limpiar combo
        if (nicknameAerolinea == null || nicknameAerolinea.isEmpty()) {
            return; // No hacer nada si el nickname es nulo o vacío
        }
        List<DTRutaVuelo> rutas = sistema.seleccionarAerolineaRet(nicknameAerolinea);
        for (DTRutaVuelo ruta : rutas) {
            if (ruta.getEstado() == EstadoRutaVuelo.CONFIRMADA)
            comboRutas.addItem(ruta);
        }
        if (esConsulta) {
            cargandoRutasRV = false;
            comboRutas.setSelectedIndex(-1); // evita disparo inicial
        }
        comboRutas.setSelectedIndex(-1);
    }

    private void cargarVuelos(JComboBox<DTVuelo> comboVuelos, String nombreRuta) {
        boolean esConsulta = (comboVuelos == comboBoxVuelosConsultaV);
        if (esConsulta) cargandoVuelos = true;

        comboVuelos.removeAllItems(); // limpiar combo
        List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(nombreRuta);
        for (DTVuelo vuelo : vuelos) {
            comboVuelos.addItem(vuelo);
        }

        if (esConsulta) {
            cargandoVuelos = false;
            comboVuelos.setSelectedIndex(-1); // evitar disparo inicial
        }
    }

    private void cargarReservas(JComboBox<DTVueloReserva> comboR, String nombreV) {
        comboR.removeAllItems();
        List<DTVueloReserva> reservas = sistema.listarReservasVuelo(nombreV);
        for (DTVueloReserva r : reservas) {
            comboR.addItem(r);
        }
        comboR.setSelectedIndex(-1);
    }

    //---PAQUETES------
    private void cargarPaquetes(JComboBox<DTPaqueteVuelos> comboPaquete) {
        if (comboPaquete == null) return;
        comboPaquete.removeAllItems();
        for (DTPaqueteVuelos p : sistema.mostrarPaquete()) {
            comboPaquete.addItem(p);

        }
        cargandoPaquete = true;
        comboPaquete.setSelectedIndex(-1);
    }

    private void cargarPaquetesNoComprados(JComboBox<DTPaqueteVuelos> comboPaquete) {
        if (comboPaquete == null) return;
        comboPaquete.removeAllItems();
        for (DTPaqueteVuelos p : sistema.obtenerPaquetesNoComprados()) {
            comboPaquete.addItem(p);
        }
        cargandoPaquete = true;
        comboPaquete.setSelectedIndex(-1);
    }

    //para el caso de uso de comprar paquete
    private void cargarPaquetesConRutas(JComboBox<DTPaqueteVuelos> combo) {
        if (combo == null) return;
        combo.removeAllItems();
        for (DTPaqueteVuelos p : sistema.mostrarPaqueteConRutas()) {
            if (p.getRutas() != null && !p.getRutas().isEmpty()) {
                combo.addItem(p);
            }
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarRutasDePaquete(JComboBox<DTRutaVuelo> rutas) {
        rutas.removeAllItems();
        for (DTRutaVuelo r : sistema.consultaPaqueteVueloRutas()) {
            rutas.addItem(r);
        }
        cargandoRutasCP = true;
        rutas.setSelectedIndex(-1);
    }

    //----CLIENTES------
    private void cargarClientes(JComboBox<DTCliente> combo) {
        combo.removeAllItems();
        for (DTCliente c : sistema.mostrarClientes()) {
            combo.addItem(c);
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarClientesSinVueloSeleccionado(JComboBox<DTCliente> combo) {
        combo.removeAllItems();
        for (DTCliente c : sistema.mostrarClientes()) {
            combo.addItem(c);
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarPasajeros(JComboBox<DTPasajero> combo, String nombreCliente) {
        combo.removeAllItems();
        for (DTPasajero c : sistema.pasajeros(nombreCliente)) {
            combo.addItem(c);
        }
        combo.setSelectedIndex(-1);
    }
    private void mostrarImagen(byte[] fotoBytes, JLabel imagen) {
        if (imagen == null) {
            System.err.println("Warning: labelImagenConsultaVuelo no está inicializado");
            return;
        }

        if (fotoBytes == null || fotoBytes.length == 0) {
            // No hay imagen, mostrar mensaje o dejar vacío
            imagen.setIcon(null);
            imagen.setText("Sin imagen");
            imagen.setHorizontalAlignment(JLabel.CENTER);
            return;
        }

        try {
            // 🔹 Convertir bytes a ImageIcon
            ImageIcon iconoOriginal = new ImageIcon(fotoBytes);

            // 🔹 Escalar la imagen para que se ajuste al label (opcional pero recomendado)
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    200, 150, // Ajusta el tamaño según tus necesidades
                    Image.SCALE_SMOOTH
            );

            // 🔹 Mostrar la imagen escalada
            imagen.setIcon(new ImageIcon(imagenEscalada));
            imagen.setText(""); // Limpiar cualquier texto

        } catch (Exception ex) {
            System.err.println("Error al cargar la imagen del vuelo: " + ex.getMessage());
            imagen.setIcon(null);
            imagen.setText("Error al cargar imagen");
            imagen.setHorizontalAlignment(JLabel.CENTER);
        }
    };


    //----------Boton cancelar Alta vuelo------
    private void limpiarCamposAltaVuelo() {
        if (nombreAltaVuelotxt != null) nombreAltaVuelotxt.setText("");
        if (duracionAltaVuelotxt != null) duracionAltaVuelotxt.setText("");
        if (asientosMaxTuristatxt != null) asientosMaxTuristatxt.setText("");
        if (asientoMaxEjecutivotxt != null) asientoMaxEjecutivotxt.setText("");
        if (horaVuelotxt != null) horaVuelotxt.setText("");
        if (fechaAltaVuelo != null) fechaAltaVuelo.setCalendar(Calendar.getInstance());
        if (fechaVuelo != null) fechaVuelo.setCalendar(Calendar.getInstance());
        if (rutasVueloAltaVuelo != null) rutasVueloAltaVuelo.setSelectedItem(null);
        if (aerolinea != null) aerolinea.setSelectedItem(null);
    }

    //----------Boton cancelar Alta ruta vuelo------
    private void limpiarCamposAltaRutaVuelo() {
        if (nombreAltRutaText != null) nombreAltRutaText.setText("");
        if (descRutaText != null) descRutaText.setText("");
//        if (horaText != null) horaText.setText("");
        if (costoTurText != null) costoTurText.setText("");
        if (costoEjText != null) costoEjText.setText("");
        if (costoEqExText != null) costoEqExText.setText("");
        if (comboBoxCiudadOrigenARV != null) comboBoxCiudadOrigenARV.setSelectedIndex(-1);
        if (comboBoxCiudadDestinoARV != null) comboBoxCiudadDestinoARV.setSelectedIndex(-1);
        if (fechaAltaRutaVuelo != null) fechaAltaRutaVuelo.setCalendar(Calendar.getInstance());
        if (aerolineaVuelo != null) aerolineaVuelo.setSelectedItem(null);
    }

    // Limpiar alta ciudad al cambiar de panel
    private void limpiarCamposAltaCiudad() {
        if (ciudadAltaText != null) ciudadAltaText.setText("");
        if (descripcionAltaCiText != null) descripcionAltaCiText.setText("");
        if (comboBoxAeropuertosAC != null) comboBoxAeropuertosAC.setSelectedIndex(-1);
        if (sitioWebAltaCiText != null) sitioWebAltaCiText.setText("");
        if (paisAltaCiText != null) paisAltaCiText.setText("");
        if (calendarCiudadAlta != null) calendarCiudadAlta.setCalendar(Calendar.getInstance());
    }

    private void limpiarCamposCrearPaquete() {
        if (nombreAltaPaqtxt != null) nombreAltaPaqtxt.setText("");
        if (descripcionAltaPaqtxt != null) descripcionAltaPaqtxt.setText("");
        if (períodoAltaPaqtxt != null) períodoAltaPaqtxt.setText("");
        if (descuentoAltaPaqtxt != null) descuentoAltaPaqtxt.setText("");
        if (calendarAltaPaquete != null) calendarAltaPaquete.setCalendar(Calendar.getInstance());
    }

    public EstacionTrabajo() {
        // IntelliJ GUI Designer genera código que se ejecuta automáticamente aquí
        // Si ese código intenta cargar recursos con rutas relativas, puede fallar silenciosamente
        // y los paneles quedan como null

        // Verificar el estado de los paneles después de que IntelliJ intente inicializarlos
        $$$setupUI$$$();
        System.out.println(">>> DEBUG: Estado de paneles después de inicialización:");
        System.out.println(">>>   mainPrincipal: " + (mainPrincipal != null ? "OK" : "NULL"));
        System.out.println(">>>   altaRuta: " + (altaRuta != null ? "OK" : "NULL"));
        System.out.println(">>>   consultaRutaVuelo: " + (consultaRutaVuelo != null ? "OK" : "NULL"));
        System.out.println(">>>   altaVuelo: " + (altaVuelo != null ? "OK" : "NULL"));
        System.out.println(">>>   consultaVuelo: " + (consultaVuelo != null ? "OK" : "NULL"));

        // FALLBACK: Inicializar mainPrincipal manualmente si no está inicializado (ejecución desde JAR)
        if (mainPrincipal == null) {
            System.out.println(">>> ADVERTENCIA: mainPrincipal es null, inicializando manualmente...");
            System.out.println(">>> Esto indica que el código generado por IntelliJ no se ejecutó o falló.");
            System.out.println(">>> Posible causa: recursos (imágenes, archivos) con rutas relativas.");
            mainPrincipal = new JPanel(new BorderLayout());
            mainPrincipal.setPreferredSize(new Dimension(1000, 800));

            // Inicializar componentes críticos si son null
            if (buttonsPanel == null) {
                buttonsPanel = new JPanel(new GridLayout(1, 3));
            }
            if (parentPanel == null) {
                parentPanel = new JPanel(new BorderLayout());
            }
            if (principalVacio == null) {
                principalVacio = new JPanel();
                principalVacio.add(new JLabel("Bienvenido a VolandoUy - Estación de Trabajo"));
            }
            if (JToolBarPrincipal == null) {
                JToolBarPrincipal = new JToolBar();
            }
            if (botonInicio == null) {
                botonInicio = new JButton("Inicio");
            }
            if (comboBoxVuelos == null) {
                comboBoxVuelos = new JComboBox<>(new String[]{"", "Crear ruta de vuelo", "Consultar ruta de vuelo",
                    "Aceptar/Rechazar Ruta de Vuelo", "Crear Vuelo", "Consultar Vuelo", "Crear Ciudad",
                    "Crear Categoría", "Reservar vuelo", "Consultar rutas mas visitadas"});
            }
            if (comboBoxPaquetes == null) {
                comboBoxPaquetes = new JComboBox<>(new String[]{"", "Crear paquete", "Agregar ruta a paquete",
                    "Consulta de paquete", "Comprar paquete"});
            }
            if (comboBoxUsuario == null) {
                comboBoxUsuario = new JComboBox<>(new String[]{"", "Crear usuario", "Modificar usuario", "Consultar usuario"});
            }

            // NO inicializar paneles de fallback aquí - solo cuando se necesiten
            // Esto permite que si el código generado por IntelliJ se ejecuta después,
            // los paneles reales puedan inicializarse

            // Construir la estructura básica del panel
            JToolBarPrincipal.add(botonInicio);
            JToolBarPrincipal.add(comboBoxVuelos);
            JToolBarPrincipal.add(comboBoxPaquetes);
            JToolBarPrincipal.add(comboBoxUsuario);

            buttonsPanel.add(JToolBarPrincipal);
            mainPrincipal.add(buttonsPanel, BorderLayout.NORTH);
            mainPrincipal.add(parentPanel, BorderLayout.CENTER);
            parentPanel.add(principalVacio, BorderLayout.CENTER);
        }

        // Obtener la instancia de ISistema a través del Factory
        Factory factory = new Factory();
        this.sistema = factory.getSistema();

        /*----- CONSULTA USUARIO -----*/
        ButtonGroup grupo = new ButtonGroup();
        if (paqueteVueloRadioButton != null) grupo.add(paqueteVueloRadioButton);
        if (reservaDeVueloRadioButton != null) grupo.add(reservaDeVueloRadioButton);
        if (rutaDeVueloRadioButton != null) grupo.add(rutaDeVueloRadioButton);

        // Inicializar ComboBox de TipoAsiento como respaldo
        if (reservaVueloSeleccionarUsuarioTipoAsiento == null) {
            reservaVueloSeleccionarUsuarioTipoAsiento = new JComboBox<>(TipoAsiento.values());
            reservaVueloSeleccionarUsuarioTipoAsiento.setSelectedIndex(0);
            System.out.println("ComboBox TipoAsiento inicializado en constructor");
        }

        /*----- TABLAS -----*/
        // Columnas de la tabla
        // Verificar que los componentes GUI estén inicializados antes de usarlos
        if (consultaUsuarioTable1 != null && consultaUsuarioJpanel1 != null) {
            JScrollPane scroll = new JScrollPane(consultaUsuarioTable1);
            consultaUsuarioJpanel1.setLayout(new BorderLayout());
            consultaUsuarioJpanel1.add(scroll, BorderLayout.CENTER);
        }

        if (modificarUsuariotable1 != null && modificarUsuarioJPanel1 != null) {
            JScrollPane scroll3 = new JScrollPane(modificarUsuariotable1);
            modificarUsuarioJPanel1.setLayout(new BorderLayout());
            modificarUsuarioJPanel1.add(scroll3, BorderLayout.CENTER);
        }


        /*----- PANEL DE BOTONES -----*/
        //Boton de inicio
        if (botonInicio != null && parentPanel != null && principalVacio != null) {
            botonInicio.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parentPanel.removeAll();
                    parentPanel.add(principalVacio);
                    parentPanel.repaint();
                    parentPanel.revalidate();
                }
            });
        }

        /*----- MENU DE VUELOS Y RUTAS -----*/
        if (comboBoxVuelos != null) {
            comboBoxVuelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboBoxVuelos.getSelectedItem();
                // Según lo que se elija, haces algo
                switch (seleccionado) {
                    case "Crear ruta de vuelo":
                        if (parentPanel == null) break;
                        if (altaRuta == null) {
                            altaRuta = crearPanelFallback("Crear Ruta de Vuelo", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        limpiarCamposAltaRutaVuelo();
                        parentPanel.removeAll();

                        if (comboBoxCiudadOrigenARV != null) cargarCiudadesOrigen(comboBoxCiudadOrigenARV);
                        if (aerolineaVuelo != null) cargarAerolineas(aerolineaVuelo);
                        if (listCatAltaRuta != null) cargarCategorias(listCatAltaRuta);
                        parentPanel.add(altaRuta);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar ruta de vuelo":
                        if (parentPanel == null) break;
                        if (consultaRutaVuelo == null) {
                            consultaRutaVuelo = crearPanelFallback("Consultar Ruta de Vuelo", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = true;
                        if (descripcionRVConsulta != null) descripcionRVConsulta.setText("");
                        if (ciudadORVConsulta != null) ciudadORVConsulta.setText("");
                        if (ciudadDRVConsulta != null) ciudadDRVConsulta.setText("");
                        if (categoriasRVtxt != null) categoriasRVtxt.setText("");
                        if (costoBaseTuRVConsulta != null) costoBaseTuRVConsulta.setText("");
                        if (costoBaseEjRVConsulta != null) costoBaseEjRVConsulta.setText("");
                        if (costoUnEquipajeExRVConsulta != null) costoUnEquipajeExRVConsulta.setText("");
                        if (fechaAltaRVConsulta != null) fechaAltaRVConsulta.setText("");
                        if (comboBoxAeroRVConsulta != null) comboBoxAeroRVConsulta.removeAllItems();
                        if (comBoxRutVueloConsultaRV != null) comBoxRutVueloConsultaRV.removeAllItems();
                        if (vuelosConsultaRV != null) vuelosConsultaRV.removeAllItems();
                        cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = false;
                        if (comboBoxAeroRVConsulta != null) cargarAerolineas(comboBoxAeroRVConsulta);
                        String nicknameAerolinea = (comboBoxAeroRVConsulta != null) ? (String) comboBoxAeroRVConsulta.getSelectedItem() : null;
                        if (nicknameAerolinea != null && comBoxRutVueloConsultaRV != null) {
                            cargarRutas(comBoxRutVueloConsultaRV, nicknameAerolinea);
                        }
                        parentPanel.add(consultaRutaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Aceptar/Rechazar Ruta de Vuelo":
                        if (parentPanel == null) break;
                        if (adminRutasPanel == null) {
                            adminRutasPanel = crearPanelFallback("Aceptar/Rechazar Ruta de Vuelo", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (adminRutasAerolineaCombo != null) adminRutasAerolineaCombo.removeAllItems();
                        if (adminRutasRutaCombo != null) adminRutasRutaCombo.removeAllItems();

                        // Verificar si hay datos precargados
                        try {
                            List<DTAerolinea> aerolineas = sistema.listarAerolineasParaAdministracion();
                            if (aerolineas.isEmpty()) {
                                JOptionPane.showMessageDialog(null,
                                        "No hay aerolíneas en el sistema. Por favor, ejecute 'Precargar Sistema Completo' primero.",
                                        "Información", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Error al verificar datos del sistema. Por favor, ejecute 'Precargar Sistema Completo' primero.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cargarAerolineasParaAdministracion();
                        parentPanel.add(adminRutasPanel);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Vuelo":
                        if (parentPanel == null) break;
                        if (altaVuelo == null) {
                            altaVuelo = crearPanelFallback("Crear Vuelo", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        limpiarCamposAltaVuelo();
                        if (aerolinea != null) cargarAerolineas(aerolinea);
                        parentPanel.add(altaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar Vuelo":
                        if (parentPanel == null) break;
                        if (consultaVuelo == null) {
                            consultaVuelo = crearPanelFallback("Consultar Vuelo", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (comboBoxAeroConsultaV != null) cargarAerolineas(comboBoxAeroConsultaV);
                        parentPanel.add(consultaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Ciudad":
                        if (parentPanel == null) break;
                        if (altaCiudad == null) {
                            altaCiudad = crearPanelFallback("Crear Ciudad", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        limpiarCamposAltaCiudad();
                        parentPanel.removeAll();
                        if (comboBoxAeropuertosAC != null) cargarAeropuertos(comboBoxAeropuertosAC);
                        parentPanel.add(altaCiudad);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Categoría":
                        if (parentPanel == null) break;
                        if (altaCategoría == null) {
                            altaCategoría = crearPanelFallback("Crear Categoría", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (categoriaAltaText != null) categoriaAltaText.setText("");
                        parentPanel.add(altaCategoría);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar rutas mas visitadas":
                        if (parentPanel == null || consultarrutasmasvisitadasjscrollpane == null) break;
                        parentPanel.removeAll();

                        // cargar modelo desde el helper
                        if (consultarRutasMasVisitadasTable != null) {
                            consultarRutasMasVisitadasTable.setModel(
                                    EstacionTrabajoHelper.cargarRutasMasVisitadas()
                            );
                        }

                        // agregar el scroll que contiene la tabla
                        parentPanel.add(consultarrutasmasvisitadasjscrollpane);

                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Reservar vuelo":
                        // Inicializar ComboBox de TipoAsiento cuando se entra a reservar vuelo
//                        inicializarComboBoxTipoAsiento();
                        if (parentPanel == null) break;
                        if (reservaVuelo == null) {
                            reservaVuelo = crearPanelFallback("Reservar Vuelo", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (cantPasajesReservaVtxt != null) cantPasajesReservaVtxt.setText("");
                        if (equipajeExtraReservaV != null) equipajeExtraReservaV.setText("");
                        if (comboBoxAeroReservaV != null) cargarAerolineas(comboBoxAeroReservaV);
                        inicializarComboBoxTipoAsientoReserva();
                        parentPanel.add(reservaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
//                        UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarAerolinea);
//                        UsuarioHelper.cambiarPanel(parentPanel,reservaVuelo);
                        break;
                }
            }
        });
        }

        /*----- MENU DE PAQUETE -----*/
        if (comboBoxPaquetes != null) {
            comboBoxPaquetes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboBoxPaquetes.getSelectedItem();
                // Según lo que se elija, haces algo
                switch (seleccionado) {
                    case "Crear paquete":
                        parentPanel.removeAll();
                        limpiarCamposCrearPaquete();
                        parentPanel.add(crearPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Agregar ruta a paquete":
                        if (parentPanel == null) break;
                        if (agregarRutaaPaquete == null) {
                            agregarRutaaPaquete = crearPanelFallback("Agregar Ruta a Paquete", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (cantidadAgrRutaaPaquetetxt != null) cantidadAgrRutaaPaquetetxt.setText("");
                        if (comboBoxPaqueteAgrRutaaPaquete != null) cargarPaquetesNoComprados(comboBoxPaqueteAgrRutaaPaquete);
                        if (comboBoxAeroAgrRutaaPaquete != null) {
                            cargarAerolineas(comboBoxAeroAgrRutaaPaquete);
                            String aeroSel = (String) comboBoxAeroAgrRutaaPaquete.getSelectedItem();
                            if (aeroSel != null && comboBoxRutaVueloAgrRutaaPaquete != null) {
                                cargarRutas(comboBoxRutaVueloAgrRutaaPaquete, aeroSel);
                            }
                        }
                        inicializarComboBoxTipoAsientoPaquete();
                        parentPanel.add(agregarRutaaPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consulta de paquete":
                        if (parentPanel == null) break;
                        if (ConsultaPaquete == null) {
                            ConsultaPaquete = crearPanelFallback("Consulta de Paquete", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (comboBoxPaqueteConsultaPaquete != null) cargarPaquetes(comboBoxPaqueteConsultaPaquete);
                        if (descripcionCPtxt != null) descripcionCPtxt.setText("");
                        if (diasvalidosCPtxt != null) diasvalidosCPtxt.setText("");
                        if (descuentoCPtxt != null) descuentoCPtxt.setText("");
                        if (costoCPtxt != null) costoCPtxt.setText("");
                        if (fechaAltaCPtxt != null) fechaAltaCPtxt.setText("");
                        parentPanel.add(ConsultaPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Comprar paquete":
                        if (parentPanel == null) break;
                        if (ComprarPaquete == null) {
                            ComprarPaquete = crearPanelFallback("Comprar Paquete", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                        }
                        parentPanel.removeAll();
                        if (comboBoxPaquetesComprarPaquete != null) cargarPaquetesConRutas(comboBoxPaquetesComprarPaquete);
                        if (comboBoxClientesComprarPaquete != null) cargarClientes(comboBoxClientesComprarPaquete);
                        parentPanel.add(ComprarPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;

                }
            }
        });
        }

        /*----- MENU DE USUARIO -----*/
        if (comboBoxUsuario != null) {
            comboBoxUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboBoxUsuario.getSelectedItem();
                // Según lo que se elija, haces algo
                switch (seleccionado) {
                    case "Crear usuario":
                        UsuarioHelper.limpiarCampos(
                                nicknameAltaCliente,
                                nombreAltaCliente,
                                apellidoAltaCliente,
                                correoAltaCliente,
                                nacionalidadAltaCliente,
                                numeroDocAltaCliente,
                                altaAerolineaNickname,
                                altaAerolineaNombre,
                                altaAerolineaCorreo,
                                altaAerolineaLinkWeb
                        );
                        UsuarioHelper.resetFormulario(
                                comboBoxAltaCliente,
                                JCalendarAltaCliente,
                                nicknameAltaCliente
                        );
                        UsuarioHelper.limpiarTextPane(
                                altaAerolineaDescripcion
                        );
                        UsuarioHelper.cambiarPanel(parentPanel, altaUsuario);
                        break;
                    case "Modificar usuario":
                        if (parentPanel == null || modificarUsuario == null) break;
                        // Actualizar tabla y limpiar campos básicos
                        UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                        UsuarioHelper.limpiarCampos(modificarUsuarioTextInput);
                        UsuarioHelper.limpiarCampos(modificarClienteNombre, modificarClienteApellido, modificarClienteNacionalidad, modificarClienteDocumento);
                        UsuarioHelper.limpiarCampos(modificarAerolineaTextNombre, modificarAerolineaTextLink);
                        if (modificarAerolineaTextArea != null) modificarAerolineaTextArea.setText("");

                        // Limpiar contraseña
                        if (modificarClienteContrasena != null) modificarClienteContrasena.setText("");
                        if (modificarClienteConfirmarContrasena != null) modificarClienteConfirmarContrasena.setText("");
                        contrasenaActual = null;

                        // Limpiar imagen
                        fotoSeleccionada = null;
                        if (modificarClienteMostrarFoto != null) {
                            modificarClienteMostrarFoto.setIcon(null);
                            modificarClienteMostrarFoto.setText("Sin imagen");
                        }
                        if (modificarAerolineaMostrarFoto != null) {
                            modificarAerolineaMostrarFoto.setIcon(null);
                            modificarAerolineaMostrarFoto.setText("Sin imagen");
                        }

                        // Mostrar panel y enfocar
                        parentPanel.add(modificarUsuario);
                        UsuarioHelper.cambiarPanel(parentPanel, modificarUsuario);
                        if (modificarUsuarioTextInput != null) modificarUsuarioTextInput.requestFocus();
                        break;
                    case "Consultar usuario":
                        if (parentPanel == null) break;
                        if (consultaUsuarioScroll == null) {
                            JPanel consultaUsuarioPanel = crearPanelFallback("Consultar Usuario", "Este panel requiere ejecución desde IntelliJ IDEA para cargar los componentes GUI.");
                            consultaUsuarioScroll = new JScrollPane(consultaUsuarioPanel);
                        }
                        parentPanel.removeAll();
                        UsuarioHelper.actualizarTablaUsuarios(consultaUsuarioTable1);
                        UsuarioHelper.limpiarCampos(consultaUsuarioText);
                        if (grupo != null) grupo.clearSelection();
                        UsuarioHelper.cambiarPanel(consultaUsuarioParentPanel, principalVacio);
                        parentPanel.add(consultaUsuarioScroll);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                }
            }
        });
        }

        /*----- PRECARGA COMPLETA DEL SISTEMA -----*/
        // Agregar botón de precarga completa al toolbar principal
        if (JToolBarPrincipal != null) {
            JButton precargarCompletoButton = new JButton("Precargar Sistema Completo");
            JToolBarPrincipal.add(precargarCompletoButton);


        precargarCompletoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    sistema.precargarSistemaCompleto();

                    JOptionPane.showMessageDialog(null,
                            "Precarga completa del sistema realizada exitosamente!\n\n" +
                                    "Se han precargado:\n" +
                                    "• Aeropuertos\n" +
                                    "• Categorías\n" +
                                    "• Usuarios (Clientes y Aerolíneas)\n" +
                                    "• Rutas de Vuelo\n" +
                                    "• Vuelos",
                            "Precarga Exitosa",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error durante la precarga completa: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        }

        /*----- ALTA CLIENTE -----*/
        //Boton de entrada
        if (ButtonCrearNuevoCliente != null && parentPanel != null && altaCliente != null) {
            ButtonCrearNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(altaCliente);
                parentPanel.repaint();
                parentPanel.revalidate();
                altaClienteContrasena.setText("");
                altaClienteConfirmarContrasena.setText("");
                altaClienteMostrarFoto.setIcon(null);
                altaClienteMostrarFoto.setText("Sin imagen");

            }
        });

        //Boton de cancelar alta cliente
        cancelarAltaClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();

                altaClienteContrasena.setText("");
                altaClienteConfirmarContrasena.setText("");
                altaClienteMostrarFoto.setIcon(null);
                altaClienteMostrarFoto.setText("Sin imagen");

            }
        });

        // LLENAR COMBO BOX CON EL ENUM
        for (TipoDoc tipo : TipoDoc.values()) {
            comboBoxAltaCliente.addItem(tipo);
            modificarClienteComboBox.addItem(tipo);
        }

        // Boton aceptar alta cliente
        aceptarAltaClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validar primero
                    boolean valido = UsuarioHelper.validarCliente(
                            nicknameAltaCliente,
                            nombreAltaCliente,
                            apellidoAltaCliente,
                            correoAltaCliente,
                            nacionalidadAltaCliente,
                            (TipoDoc) comboBoxAltaCliente.getSelectedItem(),
                            numeroDocAltaCliente,
                            JCalendarAltaCliente,
                            altaClienteContrasena,
                            altaClienteConfirmarContrasena,
                            fotoSeleccionada

                    );

                    if (!valido) {
                        return;
                    }

                    // crea cliente
                    UsuarioHelper.crearCliente(
                            nicknameAltaCliente.getText().trim(),
                            nombreAltaCliente.getText().trim(),
                            correoAltaCliente.getText().trim(),
                            apellidoAltaCliente.getText().trim(),
                            nacionalidadAltaCliente.getText().trim(),
                            (TipoDoc) comboBoxAltaCliente.getSelectedItem(),
                            numeroDocAltaCliente.getText().trim(),
                            JCalendarAltaCliente.getDate(),
                            fotoSeleccionada,
                            new String(altaClienteContrasena.getPassword()).trim()
                    );

                    JOptionPane.showMessageDialog(altaCliente, "Cliente creado con éxito.");

                    // Limpiar campos
                    UsuarioHelper.limpiarCampos(
                            nicknameAltaCliente,
                            nombreAltaCliente,
                            apellidoAltaCliente,
                            correoAltaCliente,
                            nacionalidadAltaCliente,
                            numeroDocAltaCliente
                    );
                    altaClienteContrasena.setText("");
                    altaClienteConfirmarContrasena.setText("");
                    altaClienteMostrarFoto.setIcon(null);
                    altaClienteMostrarFoto.setText("Sin imagen");
                    UsuarioHelper.resetFormulario(comboBoxAltaCliente, JCalendarAltaCliente, nicknameAltaCliente);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(altaCliente, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*----- ALTA AEROLINEA -----*/
        ButtonCrearNuevaAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(altaAerolinea);
                parentPanel.repaint();
                parentPanel.revalidate();
                altaClienteContrasena.setText("");
                altaClienteConfirmarContrasena.setText("");
                altaAerolineaMostrarFoto.setIcon(null);
                altaAerolineaMostrarFoto.setText("Sin imagen");

            }
        });

        // boton de cancelar
        altaAerolineaCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();

                altaAerolineaMostrarFoto.setIcon(null);
                altaAerolineaMostrarFoto.setText("Sin imagen");

            }
        });

        // boton de aceptar
        altaAerolineaAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UsuarioHelper.crearAerolinea(
                            altaAerolineaNickname.getText().trim(),
                            altaAerolineaNombre.getText().trim(),
                            altaAerolineaCorreo.getText().trim(),
                            altaAerolineaLinkWeb.getText().trim(),
                            altaAerolineaDescripcion.getText().trim(),
                            fotoSeleccionada,
                            new String(altaAerolineaContrasena.getPassword()).trim(),
                            new String(altaAerolineaConfirmarContrasena.getPassword()).trim()
                    );

                    JOptionPane.showMessageDialog(altaAerolinea, "Aerolínea creada con éxito.");

                    // Resetear formulario
                    UsuarioHelper.resetFormularioAerolinea(
                            altaAerolineaNickname, altaAerolineaNombre, altaAerolineaCorreo, altaAerolineaLinkWeb, altaAerolineaDescripcion
                    );
                    altaAerolineaMostrarFoto.setIcon(null);
                    altaAerolineaMostrarFoto.setText("Sin imagen");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(altaAerolinea, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*----- ALTA CATEGORIA -----*/
        buttonAltaCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomCategoria = categoriaAltaText.getText().trim();
                try {
                    VueloHelper.crearCategoria(nomCategoria);
                    JOptionPane.showMessageDialog(parentPanel, "Categoría creada con éxito.");

                    // Limpiar campo
                    categoriaAltaText.setText("");

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(parentPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*----- PRECARGA AEROPUERTOS -----*/
        // Agregar botón de precarga al toolbar principal
        precargarAeropuertosButton = new JButton("Precargar Aeropuertos");
        JToolBarPrincipal.add(precargarAeropuertosButton);

        precargarAeropuertosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueloHelper.precargarAeropuertos();
            }
        });

        /*----- ALTA CIUDAD -----*/
        buttonAltaCiudad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = ciudadAltaText.getText().trim();
                String pais = paisAltaCiText.getText().trim();
                String aeropuerto = (String) comboBoxAeropuertosAC.getSelectedItem();
                String descripcion = descripcionAltaCiText.getText().trim();
                String sitioWeb = sitioWebAltaCiText.getText().trim();
                DTFecha fecha = new DTFecha(
                        calendarCiudadAlta.getCalendar().get(Calendar.DAY_OF_MONTH),
                        calendarCiudadAlta.getCalendar().get(Calendar.MONTH) + 1,
                        calendarCiudadAlta.getCalendar().get(Calendar.YEAR)
                );

                boolean creada = VueloHelper.crearCiudad(nombre, pais, aeropuerto, descripcion, sitioWeb, fecha);

                if (creada) {
                    ciudadAltaText.setText("");
                    paisAltaCiText.setText("");
                    comboBoxAeropuertosAC.setSelectedIndex(-1);
                    sitioWebAltaCiText.setText("");
                    descripcionAltaCiText.setText("");
                    calendarCiudadAlta.setCalendar(Calendar.getInstance());
                }
            }
        });

        buttonCancelarCiudad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ciudadAltaText.setText("");
                paisAltaCiText.setText("");
                comboBoxAeropuertosAC.setSelectedIndex(-1);
                sitioWebAltaCiText.setText("");
                descripcionAltaCiText.setText("");
                calendarCiudadAlta.setCalendar(Calendar.getInstance());
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();

            }
        });


// Si queres empezar con ninguno seleccionado
        grupo.clearSelection();

        consultaUsuarioAceptar.addActionListener(e -> {
            String consulta = consultaUsuarioText.getText().trim();
            UsuarioHelper.mostrarDatosUsuario(consultaUsuarioTable1, consulta);
        });
        consultaUsuarioCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(parentPanel, principalVacio);
            }
        });

        ActionListener listener = e -> {
            cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = true;
            descripcionRVConsulta.setText("");
            ciudadORVConsulta.setText("");
            ciudadDRVConsulta.setText("");
            categoriasRVtxt.setText("");
            costoBaseTuRVConsulta.setText("");
            costoBaseEjRVConsulta.setText("");
            costoUnEquipajeExRVConsulta.setText("");
            fechaAltaRVConsulta.setText("");
            comboBoxAeroRVConsulta.removeAllItems();
            comBoxRutVueloConsultaRV.removeAllItems();
            vuelosConsultaRV.removeAllItems();
            cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = false;
            cargarAerolineas(comboBoxAeroRVConsulta);
            cargarAerolineas(comboBoxAeroConsultaV);
            String nicknameAerolinea = (String) comboBoxAeroRVConsulta.getSelectedItem();
            cargarPaquetes(comboBoxPaqueteConsultaPaquete);
            descripcionCPtxt.setText("");
            diasvalidosCPtxt.setText("");
            descuentoCPtxt.setText("");
            costoCPtxt.setText("");
            fechaAltaCPtxt.setText("");
            if (nicknameAerolinea != null) {
                cargarRutas(comBoxRutVueloConsultaRV, nicknameAerolinea);
            }
            if (paqueteVueloRadioButton.isSelected()) {
                UsuarioHelper.cambiarPanel(consultaUsuarioParentPanel, ConsultaPaquete);
            } else if (reservaDeVueloRadioButton.isSelected()) {
                UsuarioHelper.cambiarPanel(consultaUsuarioParentPanel, consultaVuelo);
            } else if (rutaDeVueloRadioButton.isSelected()) {
                UsuarioHelper.cambiarPanel(consultaUsuarioParentPanel, consultaRutaVuelo);
            }
        };

        paqueteVueloRadioButton.addActionListener(listener);
        reservaDeVueloRadioButton.addActionListener(listener);
        rutaDeVueloRadioButton.addActionListener(listener);


        listarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.actualizarTablaUsuarios(consultaUsuarioTable1);
            }
        });

        /*----- MODIFICAR USUARIO -----*/

        modificarUsuarioAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = modificarUsuarioTextInput.getText().trim();
                String tipo = UsuarioHelper.obtenerTipoUsuario(nickname);
                if (nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe proporcionar un nickname", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (tipo == null || tipo.equals("---")) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (tipo.equals("Cliente")) {
                    UsuarioHelper.cargarDatosClienteEnCampos(
                            nickname,
                            modificarClienteNombre,
                            modificarClienteApellido,
                            modificarClienteNacionalidad,
                            modificarClienteComboBox,
                            modificarClienteDocumento,
                            modificarClienteJCalendar,
                            modificarClienteMostrarFoto,
                            foto -> fotoSeleccionada = foto,
                            pass -> contrasenaActual = pass


                    );
                } else if (tipo.equals("Aerolinea")) {
                    UsuarioHelper.cargarDatosAerolineaEnCampos(
                            nickname,
                            modificarAerolineaTextNombre,
                            modificarAerolineaTextArea,
                            modificarAerolineaTextLink,
                            modificarAerolineaMostrarFoto,
                            foto -> fotoSeleccionada = foto,
                            pass -> contrasenaActual = pass
                    );
                }

                UsuarioHelper.abrirPanelModificacionUsuario(parentPanel, modificarCliente, modificarAerolinea, tipo, nickname);
            }
        });

        modificarUsuarioCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar campos básicos
                UsuarioHelper.limpiarCampos(modificarUsuarioTextInput);

                // Limpiar contraseña
                modificarClienteContrasena.setText("");
                modificarClienteConfirmarContrasena.setText("");
                contrasenaActual = null;

                // Limpiar imagen
                fotoSeleccionada = null;
                modificarClienteMostrarFoto.setIcon(null);
                modificarClienteMostrarFoto.setText("Sin imagen");

                // Actualizar tabla y volver al panel principal
                UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                UsuarioHelper.cambiarPanel(parentPanel, principalVacio);
            }
        });


        modificarClienteGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener contraseña escrita (si hay)
                    String nuevaContrasena = new String(modificarClienteContrasena.getPassword()).trim();
                    String contrasenaFinal = nuevaContrasena.isEmpty() ? contrasenaActual : nuevaContrasena;

                    // Validar todos los campos
                    if (UsuarioHelper.modificarClienteValidar(
                            modificarClienteNombre,
                            modificarClienteApellido,
                            modificarClienteNacionalidad,
                            (TipoDoc) modificarClienteComboBox.getSelectedItem(),
                            modificarClienteDocumento,
                            fotoSeleccionada,
                            modificarClienteContrasena,
                            modificarClienteConfirmarContrasena
                    )) {
                        // Guardar cambios
                        UsuarioHelper.guardarCambiosCliente(
                                modificarUsuarioTextInput.getText().trim(),
                                modificarClienteNombre.getText().trim(),
                                modificarClienteApellido.getText().trim(),
                                UsuarioHelper.convertirDTfecha(modificarClienteJCalendar),
                                modificarClienteNacionalidad.getText().trim(),
                                (TipoDoc) modificarClienteComboBox.getSelectedItem(),
                                modificarClienteDocumento.getText().trim(),
                                fotoSeleccionada,
                                contrasenaFinal
                        );

                        // Limpiar campos
                        UsuarioHelper.limpiarCampos(
                                modificarClienteNombre,
                                modificarClienteApellido,
                                modificarClienteNacionalidad,
                                modificarClienteDocumento,
                                modificarUsuarioTextInput
                        );
                        modificarClienteContrasena.setText("");
                        modificarClienteConfirmarContrasena.setText("");
                        fotoSeleccionada = null;
                        modificarClienteMostrarFoto.setIcon(null);
                        modificarClienteMostrarFoto.setText("Sin imagen");

                        // Reset y navegación
                        UsuarioHelper.resetFormulario(
                                modificarClienteComboBox,
                                modificarClienteJCalendar,
                                modificarClienteNombre
                        );
                        UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                        UsuarioHelper.cambiarPanel(parentPanel, modificarUsuario);
                        modificarUsuarioTextInput.requestFocus();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al guardar los cambios del cliente: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        modificarClienteCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.limpiarCampos(
                        modificarClienteNombre,
                        modificarClienteApellido,
                        modificarClienteNacionalidad,
                        modificarClienteDocumento,
                        modificarUsuarioTextInput
                );
                UsuarioHelper.resetFormulario(
                        modificarClienteComboBox,
                        modificarClienteJCalendar,
                        modificarClienteNombre
                );
                UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                UsuarioHelper.cambiarPanel(parentPanel, modificarUsuario);
                modificarUsuarioTextInput.requestFocus();
            }
        });

        modificarAerolineaGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuevaContrasena = new String(modificarClienteContrasena.getPassword()).trim();
                    String contrasenaFinal = nuevaContrasena.isEmpty() ? contrasenaActual : nuevaContrasena;

                    if (UsuarioHelper.modificarAerolineaValidar(
                            modificarAerolineaTextNombre,
                            modificarAerolineaTextLink,
                            modificarAerolineaTextArea,
                            fotoSeleccionada,
                            modificarClienteContrasena,
                            modificarClienteConfirmarContrasena
                    )) {
                        UsuarioHelper.guardarCambiosAerolinea(
                                modificarUsuarioTextInput.getText().trim(),
                                modificarAerolineaTextNombre.getText().trim(),
                                modificarAerolineaTextArea.getText().trim(),
                                modificarAerolineaTextLink.getText().trim(),
                                fotoSeleccionada,
                                contrasenaFinal
                        );

                        UsuarioHelper.limpiarCampos(
                                modificarAerolineaTextNombre,
                                modificarAerolineaTextLink,
                                modificarUsuarioTextInput
                        );
                        modificarAerolineaTextArea.setText("");
                        modificarClienteContrasena.setText("");
                        modificarClienteConfirmarContrasena.setText("");
                        contrasenaActual = null;

                        fotoSeleccionada = null;
                        modificarAerolineaMostrarFoto.setIcon(null);
                        modificarAerolineaMostrarFoto.setText("Sin imagen");

                        UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                        UsuarioHelper.cambiarPanel(parentPanel, modificarUsuario);
                        modificarUsuarioTextInput.requestFocus();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al guardar los cambios de la aerolínea: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        modificarAerolineaCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar campos básicos
                UsuarioHelper.limpiarCampos(
                        modificarAerolineaTextNombre,
                        modificarAerolineaTextLink,
                        modificarUsuarioTextInput
                );
                modificarAerolineaTextArea.setText("");

                // Limpiar contraseña
                modificarClienteContrasena.setText("");
                modificarClienteConfirmarContrasena.setText("");
                contrasenaActual = null;

                // Limpiar imagen
                fotoSeleccionada = null;
                modificarAerolineaMostrarFoto.setIcon(null);
                modificarAerolineaMostrarFoto.setText("Sin imagen");

                // Actualizar tabla y volver al panel principal
                UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                UsuarioHelper.cambiarPanel(parentPanel, modificarUsuario);
                modificarUsuarioTextInput.requestFocus();
            }
        });



        /*----- ALTA RUTA VUELO -----*/
        aceptarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> categoriasSeleccionadas = listCatAltaRuta.getSelectedValuesList();
                    if (categoriasSeleccionadas.isEmpty()) {
                        JOptionPane.showMessageDialog(altaRuta,
                                "Seleccione al menos una categoría",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tomar los datos del formulario
                    String nombreRuta = nombreAltRutaText.getText().trim();
                    String descripcionRuta = descRutaText.getText().trim();
//                    String horaStr = horaText.getText().trim();
                    String costoTuristaStr = costoTurText.getText().trim();
                    String costoEjecutivoStr = costoEjText.getText().trim();
                    String costoEquipajeStr = costoEqExText.getText().trim();
                    String origen = comboBoxCiudadOrigenARV.getSelectedItem().toString();
                    String destino = comboBoxCiudadDestinoARV.getSelectedItem().toString();
                    Calendar fechaCal = fechaAltaRutaVuelo.getCalendar();
                    String nicknameAerolinea = aerolineaVuelo.getSelectedItem().toString();
                    byte[] foto = null;
                    if (rutaImagenRVuelo != null && !rutaImagenRVuelo.isEmpty()) {
                        try {
                            foto = Files.readAllBytes(Paths.get(rutaImagenRVuelo));
                            System.out.println("Imagen cargada correctamente: " + foto.length + " bytes");
                        } catch (IOException ex) {
                            System.err.println("Error al leer la imagen: " + ex.getMessage());
                            JOptionPane.showMessageDialog(altaRuta,
                                    "Advertencia: No se pudo cargar la imagen seleccionada.\nLa ruta de vuelo se creará sin imagen.",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    // Seleccionar aerolínea
                    VueloHelper.seleccionarAerolinea(nicknameAerolinea);
                    // Tomar el string de las ciudades, y solo tomar la parte antes de la coma (para evitar el pais)
                    origen = origen.split(",")[0].trim();
                    destino = destino.split(",")[0].trim();

                    // Ingresar ruta de vuelo (acepto List<String> en categoria, pero no funciona)
                    VueloHelper.ingresarRutaVuelo(
                            nombreRuta,
                            descripcionRuta,
//                            horaStr,
                            costoTuristaStr,
                            costoEjecutivoStr,
                            costoEquipajeStr,
                            origen,
                            destino,
                            fechaCal,
                            categoriasSeleccionadas,
                            foto,
                            null
                    );

                    JOptionPane.showMessageDialog(altaRuta, "Ruta de vuelo registrada con éxito.");
                    nombreAltRutaText.setText("");
                    descRutaText.setText("");
//                    horaText.setText("");
                    costoTurText.setText("");
                    costoEjText.setText("");
                    costoEqExText.setText("");
                    comboBoxCiudadOrigenARV.setSelectedIndex(-1);
                    comboBoxCiudadDestinoARV.setSelectedIndex(-1);
                    fechaAltaRutaVuelo.setCalendar(Calendar.getInstance());
                    aerolineaVuelo.setSelectedItem(null);
                } catch (IllegalArgumentException ex) {
                    // Mostrar solo el mensaje de la excepción
                    JOptionPane.showMessageDialog(altaRuta,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(altaRuta,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonCancelarCrearRutaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposAltaRutaVuelo();
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });


        /*----- ALTA VUELO -----*/
        buttonAltaVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = nombreAltaVuelotxt.getText().trim();
                    String duracion = duracionAltaVuelotxt.getText().trim();
                    String maxTurista = asientosMaxTuristatxt.getText().trim();
                    String maxEjecutivo = asientoMaxEjecutivotxt.getText().trim();
                    Calendar fechaAltaCal = fechaAltaVuelo.getCalendar();
                    Calendar fechaVueloCal = fechaVuelo.getCalendar();
                    byte[] foto = null;
                    if (rutaImagenVuelo != null && !rutaImagenVuelo.isEmpty()) {
                        try {
                            foto = Files.readAllBytes(Paths.get(rutaImagenVuelo));
                            System.out.println("Imagen cargada correctamente: " + foto.length + " bytes");
                        } catch (IOException ex) {
                            System.err.println("Error al leer la imagen: " + ex.getMessage());
                            JOptionPane.showMessageDialog(altaVuelo,
                                    "Advertencia: No se pudo cargar la imagen seleccionada.\nEl vuelo se creará sin imagen.",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }



                    DTRutaVuelo ruta = (DTRutaVuelo) rutasVueloAltaVuelo.getSelectedItem();

                    if (ruta == null) {
                        JOptionPane.showMessageDialog(altaVuelo, "Debe seleccionar una ruta primero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String hora = horaVuelotxt.getText().trim();
                    VueloHelper.ingresarVuelo(nombre, duracion, hora, fechaAltaCal, fechaVueloCal, maxTurista, maxEjecutivo, ruta, foto);
                    String nicknameAerolinea = (String) aerolinea.getSelectedItem();
                    sistema.seleccionarAerolinea(nicknameAerolinea);
                    sistema.darAltaVuelo();
                    JOptionPane.showMessageDialog(altaVuelo, "Vuelo registrado correctamente.");

                    //limpio campos
                    nombreAltaVuelotxt.setText("");
                    duracionAltaVuelotxt.setText("");
                    fechaAltaVuelo.setCalendar(Calendar.getInstance());
                    fechaVuelo.setCalendar(Calendar.getInstance());
                    asientosMaxTuristatxt.setText("");
                    asientoMaxEjecutivotxt.setText("");
                    horaVuelotxt.setText("");
                    rutasVueloAltaVuelo.setSelectedItem(null);
                    aerolinea.setSelectedItem(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(altaVuelo, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonCancelarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposAltaVuelo();
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });


//        /*----- CONSULTA RUTA VUELO -----*/
        comBoxRutVueloConsultaRV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cargandoRutasRV) return; // los booleanos son porque se llama a otras cargas de comboBox y no andaba
                DTRutaVuelo nombreRuta = (DTRutaVuelo) comBoxRutVueloConsultaRV.getSelectedItem();
                String nicknameAerolinea = (String) comboBoxAeroRVConsulta.getSelectedItem();
                if (nombreRuta != null && nicknameAerolinea != null) {
                    mostrarDatosRuta(nicknameAerolinea, nombreRuta.getNombre());
                    mostrarImagen(nombreRuta.getFoto(), labelImagenRVuelo);
                    // Llenar combo de vuelos asociados a esta ruta
                    List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(nombreRuta.getNombre());
                    cargandoVuelosRV = true;
                    vuelosConsultaRV.removeAllItems(); // limpiar lista previa
                    for (DTVuelo v : vuelos) {
                        vuelosConsultaRV.addItem(v); // cargar vuelos
                    }
                    cargandoVuelosRV = false;
                    vuelosConsultaRV.setSelectedIndex(-1);
                } else {
                    VueloHelper.limpiarCampos(
                            descripcionRVConsulta,
                            ciudadORVConsulta,
                            ciudadDRVConsulta,
                            categoriasRVtxt,
                            costoBaseTuRVConsulta,
                            costoBaseEjRVConsulta,
                            costoUnEquipajeExRVConsulta,
                            fechaAltaRVConsulta
                    );
                }
            }
        });


        comboBoxAeroRVConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cargandoAeroRV) return;
                String nickname = (String) comboBoxAeroRVConsulta.getSelectedItem();
                if (nickname != null) {
                    cargarRutas(comBoxRutVueloConsultaRV, nickname);
                    cargandoVuelosRV = true;
                    vuelosConsultaRV.removeAllItems();
                    cargandoVuelosRV = false;
                    mostrarImagen(null, labelImagenRVuelo);
                }

            }
        });


        //es un comboBox, le tengo que cambiar el nombre
        aerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) aerolinea.getSelectedItem();
                if (nickname != null) {
                    cargarRutas(rutasVueloAltaVuelo, nickname);
                }
            }
        });


        vuelosConsultaRV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cargandoVuelosRV) return;
                Object seleccionado = vuelosConsultaRV.getSelectedItem();
                if (seleccionado instanceof DTVuelo) {
                    DTVuelo vueloSeleccionado = (DTVuelo) seleccionado;

                    // 🔹 Crear panel principal con BorderLayout
                    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
                    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    // 🔹 Panel para la imagen (parte superior)
                    byte[] fotoBytes = vueloSeleccionado.getFoto();
                    if (fotoBytes != null && fotoBytes.length > 0) {
                        try {
                            ImageIcon iconoOriginal = new ImageIcon(fotoBytes);
                            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                                    300, 200, Image.SCALE_SMOOTH
                            );

                            JLabel labelImagen = new JLabel(new ImageIcon(imagenEscalada));
                            labelImagen.setHorizontalAlignment(JLabel.CENTER);
                            panelPrincipal.add(labelImagen, BorderLayout.NORTH);

                        } catch (Exception ex) {
                            System.err.println("Error al cargar la imagen: " + ex.getMessage());
                        }
                    }

                    // 🔹 Panel para los detalles textuales (parte inferior)
                    JPanel panelDetalles = new JPanel();
                    panelDetalles.setLayout(new GridLayout(8, 1, 5, 5));
                    panelDetalles.setBorder(BorderFactory.createTitledBorder("Información del Vuelo"));
                    panelDetalles.add(new JLabel(" Nombre: " + vueloSeleccionado.getNombre()));
                    panelDetalles.add(new JLabel(" Fecha: " + vueloSeleccionado.getFechaVuelo().toString()));
                    panelDetalles.add(new JLabel(" Hora: " + vueloSeleccionado.getHoraVuelo().toString()));
                    panelDetalles.add(new JLabel(" Duración: " + vueloSeleccionado.getDuracion().toString()));
                    panelDetalles.add(new JLabel(" Asientos Turista: " + vueloSeleccionado.getAsientosMaxTurista()));
                    panelDetalles.add(new JLabel(" Asientos Ejecutivo: " + vueloSeleccionado.getAsientosMaxEjecutivo()));
                    panelDetalles.add(new JLabel(" Fecha de Alta: " + vueloSeleccionado.getFechaAlta().toString()));
                    panelDetalles.add(new JLabel(" Ruta Asociada: " + vueloSeleccionado.getRuta().getNombre()));

                    panelPrincipal.add(panelDetalles, BorderLayout.CENTER);

                    // 🔹 Mostrar el diálogo
                    JOptionPane.showMessageDialog(
                            null,
                            panelPrincipal,
                            "Detalles del Vuelo - " + vueloSeleccionado.getNombre(),
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
            }
        });

        //------------CONSULTA VUELO-------------------
        comboBoxAeroConsultaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) comboBoxAeroConsultaV.getSelectedItem();
                comboBoxRutasVuelosConsultaV.removeAllItems();
                comboBoxVuelosConsultaV.removeAllItems();
                VueloHelper.limpiarCampos(nombVueloConsultaVtxt, fechaVueloConsultaVtxt, horaVueloConsultaVtxt, duracionVueloConsultaVtxt, maxTuristaConsultaVtxt,
                        maxEjecutivoConsultaVtxt, fechaAltaVueloConsultaVtxt);
                if (nickname != null) {
                    cargarRutas(comboBoxRutasVuelosConsultaV, nickname);
                }
            }
        });
        comboBoxRutasVuelosConsultaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTRutaVuelo rutaSeleccionada = (DTRutaVuelo) comboBoxRutasVuelosConsultaV.getSelectedItem();
                comboBoxVuelosConsultaV.removeAllItems();
                VueloHelper.limpiarCampos(nombVueloConsultaVtxt, fechaVueloConsultaVtxt, horaVueloConsultaVtxt, duracionVueloConsultaVtxt, maxTuristaConsultaVtxt,
                        maxEjecutivoConsultaVtxt, fechaAltaVueloConsultaVtxt);
                if (rutaSeleccionada != null) {
                    cargarVuelos(comboBoxVuelosConsultaV, rutaSeleccionada.getNombre());
                }

            }
        });


        comboBoxVuelosConsultaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTVuelo v = (DTVuelo) comboBoxVuelosConsultaV.getSelectedItem();
                if (v != null) {
                    nombVueloConsultaVtxt.setText(v.getNombre());
                    fechaVueloConsultaVtxt.setText(v.getFechaVuelo().toString());
                    horaVueloConsultaVtxt.setText(v.getHoraVuelo().toString());
                    duracionVueloConsultaVtxt.setText(v.getDuracion().toString());
                    maxTuristaConsultaVtxt.setText(String.valueOf(v.getAsientosMaxTurista()));
                    maxEjecutivoConsultaVtxt.setText(String.valueOf(v.getAsientosMaxEjecutivo()));
                    fechaAltaVueloConsultaVtxt.setText(v.getFechaAlta().toString());
                    cargarReservas(comboBoxReservasConsultaV, v.getNombre());
                    mostrarImagen(v.getFoto(), labelImagenCVuelo);

                } else {
                    comboBoxReservasConsultaV.removeAllItems();
                    VueloHelper.limpiarCampos(nombVueloConsultaVtxt, fechaVueloConsultaVtxt, horaVueloConsultaVtxt, duracionVueloConsultaVtxt, maxTuristaConsultaVtxt,
                            maxEjecutivoConsultaVtxt, fechaAltaVueloConsultaVtxt);
                    mostrarImagen(null, labelImagenCVuelo);

                }

            }
        });

        //--------- CREAR PAQUETE ---------------
        buttonCrearPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomPaq = nombreAltaPaqtxt.getText().trim();
                    String descripcion = descripcionAltaPaqtxt.getText().trim();
                    String periodoVal = períodoAltaPaqtxt.getText().trim();
                    String descuento = descuentoAltaPaqtxt.getText().trim();
                    Calendar fechaCal = calendarAltaPaquete.getCalendar();
                    byte[] foto = null;

                    PaqueteHelper.ingresarPaquete(nomPaq, descripcion, periodoVal, descuento, fechaCal);
                    int diasValidosInt = Integer.parseInt(periodoVal);
                    float descuentoFloat = Float.parseFloat(descuento);

                    if (rutaImagenPaquete != null && !rutaImagenPaquete.isEmpty()) {
                        try {
                            foto = Files.readAllBytes(Paths.get(rutaImagenPaquete));
                            System.out.println("Imagen cargada correctamente: " + foto.length + " bytes");
                        } catch (IOException ex) {
                            System.err.println("Error al leer la imagen: " + ex.getMessage());
                            JOptionPane.showMessageDialog(crearPaquete,
                                    "Advertencia: No se pudo cargar la imagen seleccionada.\nEl vuelo se creará sin imagen.",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    DTFecha fechaAlta = new DTFecha(
                            fechaCal.get(Calendar.DAY_OF_MONTH),
                            fechaCal.get(Calendar.MONTH) + 1,
                            fechaCal.get(Calendar.YEAR)
                    );
                    sistema.crearPaquete(nomPaq, descripcion, null, diasValidosInt, descuentoFloat, fechaAlta, foto);
                    JOptionPane.showMessageDialog(crearPaquete, "Paquete creado correctamente.");

                    nombreAltaPaqtxt.setText("");
                    descripcionAltaPaqtxt.setText("");
                    períodoAltaPaqtxt.setText("");
                    descripcionAltaPaqtxt.setText("");
                    descuentoAltaPaqtxt.setText("");
                    calendarAltaPaquete.setCalendar(Calendar.getInstance());
                } catch (IllegalArgumentException | IllegalStateException ex) {
                    JOptionPane.showMessageDialog(crearPaquete, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(crearPaquete, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        buttonCancelarCrearPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposCrearPaquete();
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

        // CONSULTA PAQUETE DE RUTA DE VUELO
        consultaPaqueteRutaVueloSeleccionarPaqueteAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(consultaPaqueteRutaVueloParentPanel, consultaRutaVuelo);
            }
        });

        //------------COMPRAR PAQUETE --------------

        aceptarComprarPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTPaqueteVuelos paquete = (DTPaqueteVuelos) comboBoxPaquetesComprarPaquete.getSelectedItem();
                DTCliente cliente = (DTCliente) comboBoxClientesComprarPaquete.getSelectedItem();

                if (paquete == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un paquete");
                    return;
                }
                if (cliente == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                    return;
                }

                try {
                    // seteás los seleccionados en el sistema
                    sistema.seleccionarPaquete(paquete.getNombre());
                    sistema.seleccionarCliente(cliente.getNickname());
                    // Verificar si el cliente ya compró el paquete
                    if (sistema.clienteYaComproPaquete()) {
                        JOptionPane.showMessageDialog(null, "El cliente ya compró este paquete");
                        return;
                    }

                    LocalDate hoy = LocalDate.now();
                    DTFecha fechaCompra = new DTFecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
                    float costo = paquete.getDescuento();
                    // calcular vencimiento sumando los días válidos del paquete
                    LocalDate vencimientoLocalDate = hoy.plusDays(paquete.getDiasValidos());
                    DTFecha vencimiento = new DTFecha(
                            vencimientoLocalDate.getDayOfMonth(),
                            vencimientoLocalDate.getMonthValue(),
                            vencimientoLocalDate.getYear()
                    );


                    // ejecutar la compra
                    sistema.realizarCompra(fechaCompra, costo, vencimiento);
                    comboBoxPaquetesComprarPaquete.setSelectedIndex(-1);
                    comboBoxClientesComprarPaquete.setSelectedIndex(-1);

                    JOptionPane.showMessageDialog(null, "Compra realizada con éxito");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar compra: " + ex.getMessage());
                }
            }
        });

        // AGREGAR RUTA VUELO A PAQUETE

        comboBoxAeroAgrRutaaPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) comboBoxAeroAgrRutaaPaquete.getSelectedItem();
                if (nickname != null && !nickname.isEmpty()) {
                    cargarRutas(comboBoxRutaVueloAgrRutaaPaquete, nickname);
                } else {
                    comboBoxRutaVueloAgrRutaaPaquete.removeAllItems(); // Limpia si no hay aerolínea seleccionada
                }
            }
        });


        buttonAceptarAgrRutaaPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                DefaultComboBoxModel<DTPaqueteVuelos> model = new DefaultComboBoxModel<>();
//                for (DTPaqueteVuelos paquete : sistema.obtenerPaquetesNoComprados()) {
//                    model.addElement(paquete);
//                }
//                comboBoxPaqueteAgrRutaaPaquete.setModel(model);
                DTPaqueteVuelos paqueteSeleccionado = (DTPaqueteVuelos) comboBoxPaqueteAgrRutaaPaquete.getSelectedItem();
                String nicknameAerolinea = (String) comboBoxAeroAgrRutaaPaquete.getSelectedItem();

                DTAerolinea aeroSeleccionada = null;
                for (DTAerolinea a : sistema.listarAerolineas()) {
                    if (a.getNickname().equals(nicknameAerolinea)) {
                        aeroSeleccionada = a;
                        break;
                    }
                }

                DTRutaVuelo dtRutaSeleccionada = (DTRutaVuelo) comboBoxRutaVueloAgrRutaaPaquete.getSelectedItem();
                if (dtRutaSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta.");
                    return;
                }
                String nombreRuta = dtRutaSeleccionada.getNombre();

//                 RutaVuelo rutaSeleccionada = (RutaVuelo) comboBoxRutaVueloAgrRutaaPaquete.getSelectedItem();
                TipoAsiento tipoAsientoSeleccionado = (TipoAsiento) comboBoxTipoAsientoAgrRutaaPaquete.getSelectedItem();

                if (paqueteSeleccionado == null || aeroSeleccionada == null || tipoAsientoSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un paquete, una aerolínea y una ruta de vuelo");
                    return;
                }

                // Como cantidad es TextField, primero string y después parseo a int
                String cantidadStr = cantidadAgrRutaaPaquetetxt.getText().trim();
                if (cantidadStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la cantidad de veces que se incluye esta ruta en el paquete.");
                    return;
                }
                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad <= 0) {
                        throw new NumberFormatException("La cantidad debe ser un número positivo.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida: " + ex.getMessage());
                    return;
                }

                try {
                    // primero marco las selecciones hechas en el sistema
                    sistema.seleccionarPaquete(paqueteSeleccionado.getNombre());
                    sistema.seleccionarAerolineaPaquete(aeroSeleccionada);
                    sistema.seleccionarRutaVueloPaquete(nombreRuta);
                    // despues uso el resto de variables recolectadas para pasar como parametro en agregarRutaAPaquete
                    sistema.agregarRutaAPaquete(cantidad, tipoAsientoSeleccionado);
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(buttonAceptarAgrRutaaPaquete),
                            "Ruta agregada al paquete con éxito.");

                    // limpiar tras exito
                    comboBoxPaqueteAgrRutaaPaquete.setSelectedIndex(-1);
                    comboBoxAeroAgrRutaaPaquete.setSelectedIndex(-1);
                    comboBoxRutaVueloAgrRutaaPaquete.setSelectedIndex(-1);
                    comboBoxTipoAsientoAgrRutaaPaquete.setSelectedIndex(-1); // Si el primer item es vacío
                    cantidadAgrRutaaPaquetetxt.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar ruta al paquete: " + ex.getMessage());
                }
            }
        });

        buttonCancelarAgrRutaaPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

        cancelarButtonComprarPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

        //------- CONSULTA PAQUETE ------------
        comboBoxPaqueteConsultaPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cargandoPaquete) return;

                DTPaqueteVuelos paqueteconsulta = (DTPaqueteVuelos) comboBoxPaqueteConsultaPaquete.getSelectedItem();
                try {
                    if (paqueteconsulta == null) {
//                        JOptionPane.showMessageDialog(null, "Debe seleccionar un paquete");
                        return;
                    }
                    sistema.seleccionarPaquete(paqueteconsulta.getNombre());
                    paqueteconsulta = sistema.consultaPaqueteVuelo();
                    if (paqueteconsulta != null) {
                        descripcionCPtxt.setText(paqueteconsulta.getDescripcion());
                        diasvalidosCPtxt.setText(String.valueOf(paqueteconsulta.getDiasValidos()));
                        descuentoCPtxt.setText(String.valueOf(paqueteconsulta.getDescuento()));
                        costoCPtxt.setText(String.valueOf(paqueteconsulta.getCostoTotal()));
                        fechaAltaCPtxt.setText(paqueteconsulta.getFechaAlta().toString());
                        cargarRutasDePaquete(comboBoxRutasVueloCP);
                        consultaCP = true;
                        mostrarImagen(paqueteconsulta.getFoto(), labelImagenPaquete);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al seleccionar paquete " + ex.getMessage());
                }
            }
        });
        comboBoxRutasVueloCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cargandoRutasCP) return;

                DTRutaVuelo seleccionado = (DTRutaVuelo) comboBoxRutasVueloCP.getSelectedItem();
                DTPaqueteVuelos ps = (DTPaqueteVuelos) comboBoxPaqueteConsultaPaquete.getSelectedItem();

                if (!consultaCP) return;
                consultaCP = false;
                if (seleccionado != null) {
                    consultaCP = true;
                    sistema.seleccionarRVPaquete(seleccionado.getNombre());
                    JPanel panelDetalles = new JPanel();
                    panelDetalles.setLayout(new GridLayout(10, 1));
                    panelDetalles.add(new JLabel("Descripción: " + seleccionado.getDescripcion()));
                    panelDetalles.add(new JLabel("Fecha alta: " + seleccionado.getFechaAlta().toString()));
                    panelDetalles.add(new JLabel("Costo Base turista: " + seleccionado.getCostoBase().getCostoTurista()));
                    panelDetalles.add(new JLabel("Costo Base ejecutivo: " + seleccionado.getCostoBase().getCostoEjecutivo()));
                    panelDetalles.add(new JLabel("Ciudad de origen: " + seleccionado.getCiudadOrigen()));
                    panelDetalles.add(new JLabel("Ciudad de destino: " + seleccionado.getCiudadDestino()));
                    panelDetalles.add(new JLabel("Cantidad y tipo asiento: " + sistema.consultaPaqueteVueloRutasCantidadTipo()));

                    JComboBox<String> combovuelos = new JComboBox<>();
                    for (DTVuelo v : sistema.seleccionarRutaVuelo(seleccionado.getNombre())) {
                        combovuelos.addItem(v.getNombre());
                    }
                    panelDetalles.add(new JLabel("Vuelos disponibles:"));
                    panelDetalles.add(combovuelos);
                    JOptionPane.showMessageDialog(
                            null,
                            panelDetalles,
                            "Detalles de ruta de vuelo",
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
            }
        });

        //--------- RESERVA VUELO -------------
        comboBoxAeroReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cargandoAeroRV) return;
                String a = (String) comboBoxAeroReservaV.getSelectedItem();
                comboBoxRutasVueloReservaV.removeAllItems();
                comboBoxVuelosReservaV.removeAllItems();

                if (a != null) {
                    cargarRutas(comboBoxRutasVueloReservaV, a);
                    cargandoVuelosRV = true;
                    vuelosConsultaRV.removeAllItems();
                    cargandoVuelosRV = false;
                }

            }

        });
        comboBoxRutasVueloReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTRutaVuelo rutaSeleccionada = (DTRutaVuelo) comboBoxRutasVueloReservaV.getSelectedItem();
                comboBoxVuelosReservaV.removeAllItems();

                if (rutaSeleccionada != null) {
                    // Llenar combo de vuelos asociados a esta ruta
                    List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(rutaSeleccionada.getNombre());
                    cargandoVuelosRV = true;
                    comboBoxVuelosReservaV.removeAllItems(); // limpiar lista previa
                    for (DTVuelo v : vuelos) {
                        comboBoxVuelosReservaV.addItem(v); // cargar vuelos
                    }
                    cargandoVuelosRV = false;
                    comboBoxVuelosReservaV.setSelectedIndex(-1);
                }


                //comboBoxVuelosReservaV.setSelectedItem(-1);
            }
        });
        comboBoxVuelosReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTVuelo v = (DTVuelo) comboBoxVuelosReservaV.getSelectedItem();
                if (v != null) {
                    sistema.seleccionarVueloParaReserva(v.getNombre());
                    nombreVueloReservaV.setText(v.getNombre());
                    fechaVReservaV.setText(v.getFechaVuelo().toString());
                    horaVReservaV.setText(v.getHoraVuelo().toString());
                    duracionVReservaV.setText(v.getDuracion().toString());
                    cantidadAsientosTReservaV.setText(String.valueOf(v.getAsientosMaxTurista()));
                    cantidadAsientosEReservaV.setText(String.valueOf(v.getAsientosMaxEjecutivo()));
                    fechaAltaVReservaV.setText(v.getFechaAlta().toString());
                    cargarClientesSinVueloSeleccionado(comboBoxClienteReservaV);
                } else {
                    VueloHelper.limpiarCampos(nombreVueloReservaV, fechaVReservaV, horaVReservaV, duracionVReservaV, cantidadAsientosTReservaV,
                            cantidadAsientosEReservaV, fechaAltaVReservaV);
                }
            }
        });
        buttonAceptarReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Declarar variables fuera del try para que sean accesibles en el catch
                TipoAsiento asiento = null;
                int cant = 0;
                int equipajeExtra = 0;

                try {
                    asiento = (TipoAsiento) comboBoxTipoAsientoReservaV.getSelectedItem();

                    // Validar que se haya seleccionado un tipo de asiento
                    if (asiento == null) {
                        JOptionPane.showMessageDialog(null, "ERROR: Debe seleccionar un tipo de asiento.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validar que se haya seleccionado un cliente
                    DTCliente pasajero = (DTCliente) comboBoxClienteReservaV.getSelectedItem();
                    if (pasajero == null) {
                        JOptionPane.showMessageDialog(null, "ERROR: Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validar cantidad de pasajes
                    try {
                        cant = Integer.parseInt(cantPasajesReservaVtxt.getText().trim());
                        if (cant <= 0) {
                            JOptionPane.showMessageDialog(null, "ERROR: La cantidad de pasajes debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: La cantidad de pasajes debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validar equipaje extra
                    try {
                        equipajeExtra = Integer.parseInt(equipajeExtraReservaV.getText().trim());
                        if (equipajeExtra < 0) {
                            JOptionPane.showMessageDialog(null, "ERROR: El equipaje extra no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: El equipaje extra debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Preparar lista de pasajeros (solo agregar el cliente principal si no está ya en la lista)
                    if (!nombresPasajeros.contains(pasajero.getNickname())) {
                        nombresPasajeros.add(0, pasajero.getNickname());
                    }

                    // Validación adicional: verificar que no haya duplicados antes de crear la reserva
                    Set<String> pasajerosUnicos = new HashSet<>(nombresPasajeros);
                    if (nombresPasajeros.size() != pasajerosUnicos.size()) {
                        throw new IllegalArgumentException("ERROR: Hay pasajeros duplicados en la lista. " +
                                "Tiene " + nombresPasajeros.size() + " entradas pero solo " + pasajerosUnicos.size() + " pasajeros únicos. " +
                                "Debe quitar los pasajeros duplicados de la lista.");
                    }

                    // Validación adicional: verificar que la cantidad de pasajeros coincida con la cantidad de pasajes
                    if (pasajerosUnicos.size() != cant) {
                        if (pasajerosUnicos.size() < cant) {
                            throw new IllegalArgumentException("ERROR: No completó la lista de pasajeros. Solicitó " + cant +
                                    " pasajes pero solo tiene " + pasajerosUnicos.size() + " pasajero(s) únicos en la lista. " +
                                    "Debe agregar " + (cant - pasajerosUnicos.size()) + " pasajero(s) más.");
                        } else {
                            throw new IllegalArgumentException("ERROR: Agregó demasiados pasajeros. Solicitó " + cant +
                                    " pasajes pero tiene " + pasajerosUnicos.size() + " pasajero(s) únicos en la lista. " +
                                    "Debe quitar " + (pasajerosUnicos.size() - cant) + " pasajero(s) de la lista.");
                        }
                    }

                    // Usar ReservaHelper para realizar la reserva con manejo de excepciones
                    ReservaHelper.realizarReserva(asiento, cant, equipajeExtra, nombresPasajeros,
                            fechaReservaVJC, null, null);

                    // Limpiar todos los campos del formulario después de la reserva exitosa
                    limpiarFormularioReserva();

                } catch (Exception ex) {
                    // Verificar si es un conflicto que requiere administración
                    if (ex.getMessage() != null && ex.getMessage().startsWith("ADMIN_REQUIRED:")) {
                        // Extraer el mensaje sin el prefijo
                        String mensajeCompleto = ex.getMessage().substring("ADMIN_REQUIRED:".length());

                        // Mostrar opciones de administración
                        String[] opciones = {
                                "1. Cambiar aerolínea",
                                "2. Cambiar ruta de vuelo",
                                "3. Cambiar vuelo",
                                "4. Cambiar cliente",
                                "5. Cancelar caso de uso"
                        };

                        int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                                mensajeCompleto + "\n\n¿Qué desea hacer?",
                                "Conflicto de Reserva - Administración Requerida",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.WARNING_MESSAGE,
                                null,
                                opciones,
                                opciones[4]); // Opción por defecto: Cancelar

                        if (opcionSeleccionada >= 0 && opcionSeleccionada < 5) {
                            try {
                                String opcion = String.valueOf(opcionSeleccionada + 1);
                                sistema.manejarConflictoReserva(opcion, asiento, cant, equipajeExtra, nombresPasajeros,
                                        ReservaHelper.convertirFecha(fechaReservaVJC));

                                // Si llegamos aquí, la operación fue exitosa
                                JOptionPane.showMessageDialog(null, "Operación completada exitosamente.",
                                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                limpiarFormularioReserva();

                            } catch (Exception adminEx) {
                                if (adminEx.getMessage() != null && adminEx.getMessage().startsWith("SUCCESS:")) {
                                    // Es un mensaje de éxito (como cancelación)
                                    JOptionPane.showMessageDialog(null, adminEx.getMessage().substring("SUCCESS:".length()),
                                            "Operación Completada", JOptionPane.INFORMATION_MESSAGE);
                                    limpiarFormularioReserva();
                                } else {
                                    // Es un error real
                                    JOptionPane.showMessageDialog(null, "ERROR: " + adminEx.getMessage(),
                                            "Error en Administración", JOptionPane.ERROR_MESSAGE);
                                    // NO limpiar formulario en caso de error de administración
                                    // Los datos se mantienen para que el administrador pueda corregir
                                }
                            }
                        }
                        // Si el usuario cancela (opcionSeleccionada == -1), NO hacer nada
                        // Los datos se mantienen para que pueda intentar de nuevo
                    } else {
                        // Error normal, mostrar en ventana de Swing
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en Reserva", JOptionPane.ERROR_MESSAGE);

                        // Limpiar todos los campos del formulario después del error
                        limpiarFormularioReserva();
                    }
                }
            }
        });

        buttonCancelarReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormularioReserva();
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

        comboBoxClienteReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTCliente cliente = (DTCliente) comboBoxClienteReservaV.getSelectedItem();
                if (cliente != null) {
                    nombresPasajeros.clear();
                    cargarPasajeros(comboBoxPasajerosReservaV, cliente.getNickname());
                }
            }
        });

        buttonAgregarPasajeroReservaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DTPasajero pasajero = (DTPasajero) comboBoxPasajerosReservaV.getSelectedItem();

                    if (pasajero != null) {
                        // Verificar si el pasajero ya está en la lista
                        if (nombresPasajeros.contains(pasajero.getNicknameCliente())) {
                            // Si ya está, mostrar error y NO permitir agregarlo
                            JOptionPane.showMessageDialog(null,
                                    "ERROR: El pasajero " + pasajero.getNicknameCliente() + " ya está en la lista.\n" +
                                            "No se pueden agregar pasajeros duplicados.\n" +
                                            "Si desea quitarlo, use el botón 'Quitar' o seleccione otro pasajero.",
                                    "Pasajero Duplicado",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Si no está, agregarlo normalmente
                            sistema.nombresPasajes(pasajero.getNicknameCliente(), nombresPasajeros);
                            actualizarListaPasajeros(); // Actualizar la lista visual
                            JOptionPane.showMessageDialog(null, "Pasajero agregado exitosamente: " + pasajero.getNicknameCliente(),
                                    "Pasajero Agregado", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR: Debe seleccionar un pasajero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: Error inesperado al agregar pasajero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener para el botón de quitar pasajero (solo si el botón existe)
        if (buttonQuitarPasajeroReservaV != null) {
            buttonQuitarPasajeroReservaV.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Obtener el pasajero seleccionado en el combo box
                        DTPasajero pasajero = (DTPasajero) comboBoxPasajerosReservaV.getSelectedItem();

                        if (pasajero != null) {
                            String nicknamePasajero = pasajero.getNicknameCliente();

                            // Verificar si el pasajero está en la lista
                            if (nombresPasajeros.contains(nicknamePasajero)) {
                                // Confirmar antes de quitar
                                int opcion = JOptionPane.showConfirmDialog(null,
                                        "¿Desea quitar el pasajero '" + nicknamePasajero + "' de la lista?",
                                        "Quitar Pasajero",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);

                                if (opcion == JOptionPane.YES_OPTION) {
                                    quitarPasajero(nicknamePasajero);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "ERROR: El pasajero " + nicknamePasajero +
                                        " no está en la lista de pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "ERROR: Debe seleccionar un pasajero del combo box.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: Error inesperado al quitar pasajero: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // Listener para doble clic en la lista de pasajeros (para quitar pasajeros)
        if (listaPasajerosReservaVJlist != null) {
            listaPasajerosReservaVJlist.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) { // Doble clic
                        String pasajeroSeleccionado = (String) listaPasajerosReservaVJlist.getSelectedValue();
                        if (pasajeroSeleccionado != null) {
                            int opcion = JOptionPane.showConfirmDialog(null,
                                    "¿Desea quitar el pasajero '" + pasajeroSeleccionado + "' de la lista?",
                                    "Quitar Pasajero",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (opcion == JOptionPane.YES_OPTION) {
                                quitarPasajero(pasajeroSeleccionado);
                            }
                        }
                    }
                }
            });
        }
        comboBoxCiudadOrigenARV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciudadOrigen = (String) comboBoxCiudadOrigenARV.getSelectedItem();
                if (ciudadOrigen != null) {
                    ciudadOrigen = ciudadOrigen.split(",")[0].trim();
                    cargarCiudadesDestino(comboBoxCiudadDestinoARV, ciudadOrigen);
                } else {
                    comboBoxCiudadDestinoARV.removeAllItems();
                }
            }
        });


        buttonImagenAltaVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // 🔹 Filtro para que solo muestre imágenes
                FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filtro);

                // 🔹 Mostrar diálogo
                int resultado = fileChooser.showOpenDialog(mainPrincipal);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();

                    // Guardar la ruta para usarla después al crear el vuelo
                    rutaImagenVuelo = archivoSeleccionado.getAbsolutePath();

                    System.out.println("Imagen seleccionada: " + rutaImagenVuelo);

                    try {
                        // 🔹 Crear y escalar la imagen
                        ImageIcon icono = new ImageIcon(rutaImagenVuelo);
                        Image imagenEscalada = icono.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);

                        // 🔹 Simplemente actualizar el label que ya existe en el formulario
                        labelImagenVuelo.setIcon(new ImageIcon(imagenEscalada));
                        labelImagenVuelo.setText(""); // Limpiar cualquier texto
                        labelImagenVuelo.setVisible(true); // Hacerlo visible

                    } catch (Exception ex) {
                        System.err.println("Error al cargar la imagen: " + ex.getMessage());
                        JOptionPane.showMessageDialog(mainPrincipal,
                            "No se pudo cargar la imagen seleccionada",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonImagenAltaVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Filtro para que solo muestre imágenes
                FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filtro);

                int resultado = fileChooser.showOpenDialog(mainPrincipal);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    rutaImagenVuelo = archivoSeleccionado.getAbsolutePath();

                    System.out.println("Imagen seleccionada: " + rutaImagenVuelo);

                    // Mostrar mensaje de confirmación
                    JOptionPane.showMessageDialog(mainPrincipal,
                            "Imagen seleccionada correctamente:\n" + archivoSeleccionado.getName(),
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        buttonImagenRVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Filtro para que solo muestre imágenes
                FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filtro);

                int resultado = fileChooser.showOpenDialog(mainPrincipal);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    rutaImagenRVuelo = archivoSeleccionado.getAbsolutePath();

                    System.out.println("Imagen seleccionada: " + rutaImagenRVuelo);

                    // Mostrar mensaje de confirmación
                    JOptionPane.showMessageDialog(mainPrincipal,
                            "Imagen seleccionada correctamente:\n" + archivoSeleccionado.getName(),
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        buttonImagenPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Filtro para que solo muestre imágenes
                FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filtro);

                int resultado = fileChooser.showOpenDialog(mainPrincipal);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    rutaImagenPaquete = archivoSeleccionado.getAbsolutePath();

                    System.out.println("Imagen seleccionada: " + rutaImagenPaquete);

                    // Mostrar mensaje de confirmación
                    JOptionPane.showMessageDialog(mainPrincipal,
                            "Imagen seleccionada correctamente:\n" + archivoSeleccionado.getName(),
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Listeners para el panel de administración de rutas de vuelo
        adminRutasAerolineaCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aerolineaSeleccionada = (String) adminRutasAerolineaCombo.getSelectedItem();
                if (aerolineaSeleccionada != null && !aerolineaSeleccionada.isEmpty()) {
                    cargarRutasIngresadas();
                } else {
                    adminRutasRutaCombo.removeAllItems();
                }
            }
        });

        adminRutasAceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rutaSeleccionada = (String) adminRutasRutaCombo.getSelectedItem();
                if (rutaSeleccionada == null || rutaSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta de vuelo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    sistema.seleccionarRutaVueloParaAdministracion(rutaSeleccionada);
                    sistema.aceptarRutaVuelo();

                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(null,
                            "La ruta de vuelo '" + rutaSeleccionada + "' ha sido aceptada exitosamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Recargar las rutas
                    cargarRutasIngresadas();

                } catch (IllegalStateException ex) {
                    if (ex.getMessage() != null && ex.getMessage().startsWith("SUCCESS:")) {
                        JOptionPane.showMessageDialog(null, ex.getMessage().substring("SUCCESS:".length()),
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarRutasIngresadas();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adminRutasRechazarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rutaSeleccionada = (String) adminRutasRutaCombo.getSelectedItem();
                if (rutaSeleccionada == null || rutaSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta de vuelo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Confirmar rechazo
                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea rechazar la ruta de vuelo '" + rutaSeleccionada + "'?",
                        "Confirmar Rechazo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        sistema.seleccionarRutaVueloParaAdministracion(rutaSeleccionada);
                        sistema.rechazarRutaVuelo();

                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null,
                                "La ruta de vuelo '" + rutaSeleccionada + "' ha sido rechazada exitosamente.",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        // Recargar las rutas
                        cargarRutasIngresadas();

                    } catch (IllegalStateException ex) {
                        if (ex.getMessage() != null && ex.getMessage().startsWith("SUCCESS:")) {
                            JOptionPane.showMessageDialog(null, ex.getMessage().substring("SUCCESS:".length()),
                                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            cargarRutasIngresadas();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        adminRutasRecargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sistema.recargarRutasConEstados();
                    JOptionPane.showMessageDialog(null,
                            "Rutas recargadas exitosamente con diferentes estados.\n\n" +
                            "Estados disponibles:\n" +
                            "- INGRESADA: Montevideo-São Paulo, Montevideo-Lima, Montevideo-Miami, Buenos Aires-Córdoba, Lima-Cusco\n" +
                            "- CONFIRMADA: Montevideo-Buenos Aires, Montevideo-Santiago, Montevideo-Madrid, Punta del Este-Buenos Aires, Santiago-Valparaíso\n" +
                            "- RECHAZADA: Montevideo-Bogotá, São Paulo-Río de Janeiro",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Recargar las aerolíneas y rutas
                    cargarAerolineasParaAdministracion();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al recargar rutas: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        altaClienteSubirImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

                int resultado = fileChooser.showOpenDialog(null);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    try {
                        fotoSeleccionada = Files.readAllBytes(archivo.toPath()); // ✅ actualiza la global
                        mostrarImagen(fotoSeleccionada, altaClienteMostrarFoto);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + ex.getMessage());
                    }
                } else {
                    altaClienteMostrarFoto.setIcon(null);
                    altaClienteMostrarFoto.setText("No hay imagen seleccionada");
                    fotoSeleccionada = null; // ✅ opcional: limpiar si cancela
                }
            }
        });

        altaAerolineaSubirImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

                int resultado = fileChooser.showOpenDialog(null);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    try {
                        fotoSeleccionada = Files.readAllBytes(archivo.toPath()); // ✅ sin "byte[]"
                        mostrarImagen(fotoSeleccionada, altaAerolineaMostrarFoto);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + ex.getMessage());
                        fotoSeleccionada = null; // opcional: limpiar si falla
                    }
                } else {
                    altaAerolineaMostrarFoto.setIcon(null);
                    altaAerolineaMostrarFoto.setText("No hay imagen seleccionada");
                    fotoSeleccionada = null; // ✅ limpiar si cancela
                }
            }
        });

        modificarClienteSubirImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

                int resultado = fileChooser.showOpenDialog(null);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    try {
                        fotoSeleccionada = Files.readAllBytes(archivo.toPath()); // actualiza la global
                        mostrarImagen(fotoSeleccionada, modificarClienteMostrarFoto);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + ex.getMessage());
                    }
                } else {
                    modificarClienteMostrarFoto.setIcon(null);
                    modificarClienteMostrarFoto.setText("No hay imagen seleccionada");
                    fotoSeleccionada = null; // opcional: limpiar si cancela
                }
            }
        });

        modificarAerolineaSubirImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

                int resultado = fileChooser.showOpenDialog(null);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    try {
                        fotoSeleccionada = Files.readAllBytes(archivo.toPath()); // actualiza la global
                        mostrarImagen(fotoSeleccionada, modificarAerolineaMostrarFoto);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + ex.getMessage());
                    }
                } else {
                    modificarAerolineaMostrarFoto.setIcon(null);
                    modificarAerolineaMostrarFoto.setText("No hay imagen seleccionada");
                    fotoSeleccionada = null; // opcional: limpiar si cancela
                }
            }
        });
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPrincipal = new JPanel();
        mainPrincipal.setLayout(new BorderLayout(0, 0));
        mainPrincipal.setName("Enviar");
        mainPrincipal.setPreferredSize(new Dimension(1000, 800));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPrincipal.add(buttonsPanel, BorderLayout.NORTH);
        JToolBarPrincipal = new JToolBar();
        buttonsPanel.add(JToolBarPrincipal, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        JToolBarPrincipal.setBorder(BorderFactory.createTitledBorder(null, "Inicio", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        botonInicio = new JButton();
        botonInicio.setText("Inicio");
        JToolBarPrincipal.add(botonInicio);
        comboBoxVuelos = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("Crear ruta de vuelo");
        defaultComboBoxModel1.addElement("Consultar ruta de vuelo");
        defaultComboBoxModel1.addElement("Aceptar/Rechazar Ruta de Vuelo");
        defaultComboBoxModel1.addElement("Crear Vuelo");
        defaultComboBoxModel1.addElement("Consultar Vuelo");
        defaultComboBoxModel1.addElement("Crear Ciudad");
        defaultComboBoxModel1.addElement("Crear Categoría");
        defaultComboBoxModel1.addElement("Reservar vuelo");
        defaultComboBoxModel1.addElement("Consultar rutas mas visitadas");
        comboBoxVuelos.setModel(defaultComboBoxModel1);
        JToolBarPrincipal.add(comboBoxVuelos);
        comboBoxPaquetes = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("");
        defaultComboBoxModel2.addElement("Crear paquete");
        defaultComboBoxModel2.addElement("Agregar ruta a paquete");
        defaultComboBoxModel2.addElement("Consulta de paquete");
        defaultComboBoxModel2.addElement("Comprar paquete");
        comboBoxPaquetes.setModel(defaultComboBoxModel2);
        JToolBarPrincipal.add(comboBoxPaquetes);
        comboBoxUsuario = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("");
        defaultComboBoxModel3.addElement("Crear usuario");
        defaultComboBoxModel3.addElement("Modificar usuario");
        defaultComboBoxModel3.addElement("Consultar usuario");
        comboBoxUsuario.setModel(defaultComboBoxModel3);
        JToolBarPrincipal.add(comboBoxUsuario);
        parentPanel = new JPanel();
        parentPanel.setLayout(new CardLayout(0, 0));
        mainPrincipal.add(parentPanel, BorderLayout.CENTER);
        principalVacio = new JPanel();
        principalVacio.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(principalVacio, "Card6");
        consultaRutaVuelo = new JPanel();
        consultaRutaVuelo.setLayout(new GridLayoutManager(19, 10, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(consultaRutaVuelo, "Card5");
        consultaRutaVuelo.setBorder(BorderFactory.createTitledBorder(null, "Consulta de Ruta Vuelo", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer1 = new Spacer();
        consultaRutaVuelo.add(spacer1, new GridConstraints(2, 0, 17, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Seleccione una Aerolínea");
        consultaRutaVuelo.add(label1, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Rutas de Vuelo asociadas");
        consultaRutaVuelo.add(label2, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Descripción");
        consultaRutaVuelo.add(label3, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(74, 121), null, 0, false));
        comBoxRutVueloConsultaRV = new JComboBox();
        consultaRutaVuelo.add(comBoxRutVueloConsultaRV, new GridConstraints(1, 6, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxAeroRVConsulta = new JComboBox();
        consultaRutaVuelo.add(comboBoxAeroRVConsulta, new GridConstraints(0, 6, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Ciudad origen");
        consultaRutaVuelo.add(label4, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Costo Base Turista");
        consultaRutaVuelo.add(label5, new GridConstraints(6, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Costo Base Ejecutivo");
        consultaRutaVuelo.add(label6, new GridConstraints(7, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costoBaseEjRVConsulta = new JTextArea();
        costoBaseEjRVConsulta.setEditable(false);
        consultaRutaVuelo.add(costoBaseEjRVConsulta, new GridConstraints(7, 6, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Fecha de Alta");
        consultaRutaVuelo.add(label7, new GridConstraints(9, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaAltaRVConsulta = new JTextArea();
        fechaAltaRVConsulta.setEditable(false);
        consultaRutaVuelo.add(fechaAltaRVConsulta, new GridConstraints(9, 6, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        costoEquipajeExRVConsulta = new JLabel();
        costoEquipajeExRVConsulta.setText("");
        consultaRutaVuelo.add(costoEquipajeExRVConsulta, new GridConstraints(10, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costoBaseTuRVConsulta = new JTextArea();
        costoBaseTuRVConsulta.setEditable(false);
        consultaRutaVuelo.add(costoBaseTuRVConsulta, new GridConstraints(6, 6, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Costo de unidad de equipaje extra");
        consultaRutaVuelo.add(label8, new GridConstraints(8, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Vuelos asociados");
        consultaRutaVuelo.add(label9, new GridConstraints(10, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costoUnEquipajeExRVConsulta = new JTextArea();
        costoUnEquipajeExRVConsulta.setEditable(false);
        consultaRutaVuelo.add(costoUnEquipajeExRVConsulta, new GridConstraints(8, 6, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        vuelosConsultaRV = new JComboBox();
        consultaRutaVuelo.add(vuelosConsultaRV, new GridConstraints(10, 7, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        consultaRutaVuelo.add(spacer2, new GridConstraints(13, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        descripcionRVConsulta = new JTextArea();
        descripcionRVConsulta.setEditable(false);
        consultaRutaVuelo.add(descripcionRVConsulta, new GridConstraints(2, 6, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 121), null, 0, false));
        final Spacer spacer3 = new Spacer();
        consultaRutaVuelo.add(spacer3, new GridConstraints(12, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Ciudad destino");
        consultaRutaVuelo.add(label10, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ciudadORVConsulta = new JTextArea();
        ciudadORVConsulta.setEditable(false);
        consultaRutaVuelo.add(ciudadORVConsulta, new GridConstraints(3, 6, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        ciudadDRVConsulta = new JTextArea();
        ciudadDRVConsulta.setEditable(false);
        consultaRutaVuelo.add(ciudadDRVConsulta, new GridConstraints(4, 6, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Categorias:");
        consultaRutaVuelo.add(label11, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        categoriasRVtxt = new JTextArea();
        consultaRutaVuelo.add(categoriasRVtxt, new GridConstraints(5, 6, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer4 = new Spacer();
        consultaRutaVuelo.add(spacer4, new GridConstraints(11, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelImagenRVuelo = new JLabel();
        labelImagenRVuelo.setText("Imagen");
        consultaRutaVuelo.add(labelImagenRVuelo, new GridConstraints(1, 9, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolinea = new JPanel();
        altaAerolinea.setLayout(new GridLayoutManager(13, 4, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(altaAerolinea, "Card1");
        altaAerolinea.setBorder(BorderFactory.createTitledBorder(null, "Alta Aerolinea", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label12 = new JLabel();
        label12.setText("Link a sitio web:");
        altaAerolinea.add(label12, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaLinkWeb = new JTextField();
        altaAerolineaLinkWeb.setText("");
        altaAerolinea.add(altaAerolineaLinkWeb, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Descripcion general:");
        altaAerolinea.add(label13, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaNombre = new JTextField();
        altaAerolinea.add(altaAerolineaNombre, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Nickname:");
        altaAerolinea.add(label14, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaNickname = new JTextField();
        altaAerolinea.add(altaAerolineaNickname, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Nombre:");
        altaAerolinea.add(label15, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        altaAerolinea.add(spacer5, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        altaAerolinea.add(spacer6, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Correo:");
        altaAerolinea.add(label16, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaCorreo = new JTextField();
        altaAerolinea.add(altaAerolineaCorreo, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer7 = new Spacer();
        altaAerolinea.add(spacer7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        altaAerolinea.add(spacer8, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        altaAerolinea.add(spacer9, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        altaAerolinea.add(spacer10, new GridConstraints(12, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        altaAerolineaAceptar = new JButton();
        altaAerolineaAceptar.setText("Aceptar");
        altaAerolinea.add(altaAerolineaAceptar, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaCancelar = new JButton();
        altaAerolineaCancelar.setText("Cancelar");
        altaAerolinea.add(altaAerolineaCancelar, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaDescripcion = new JTextPane();
        altaAerolinea.add(altaAerolineaDescripcion, new GridConstraints(7, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(100, 50), null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Contraseña:");
        altaAerolinea.add(label17, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("Confirmar contraseña:");
        altaAerolinea.add(label18, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaSubirImagen = new JButton();
        altaAerolineaSubirImagen.setText("Subir imagen");
        altaAerolinea.add(altaAerolineaSubirImagen, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaMostrarFoto = new JLabel();
        altaAerolineaMostrarFoto.setText("No hay imagen seleccionada");
        altaAerolinea.add(altaAerolineaMostrarFoto, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaAerolineaContrasena = new JPasswordField();
        altaAerolinea.add(altaAerolineaContrasena, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        altaAerolineaConfirmarContrasena = new JPasswordField();
        altaAerolinea.add(altaAerolineaConfirmarContrasena, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        altaCliente = new JPanel();
        altaCliente.setLayout(new GridLayoutManager(15, 5, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(altaCliente, "Card2");
        altaCliente.setBorder(BorderFactory.createTitledBorder(null, "Alta Cliente", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label19 = new JLabel();
        label19.setText("Apellido:");
        altaCliente.add(label19, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        apellidoAltaCliente = new JTextField();
        apellidoAltaCliente.setText("");
        altaCliente.add(apellidoAltaCliente, new GridConstraints(6, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nombreAltaCliente = new JTextField();
        altaCliente.add(nombreAltaCliente, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Nickname:");
        altaCliente.add(label20, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nicknameAltaCliente = new JTextField();
        altaCliente.add(nicknameAltaCliente, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("Nacionalidad:");
        altaCliente.add(label21, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nacionalidadAltaCliente = new JTextField();
        altaCliente.add(nacionalidadAltaCliente, new GridConstraints(7, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Tipo Documento:");
        altaCliente.add(label22, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxAltaCliente = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        comboBoxAltaCliente.setModel(defaultComboBoxModel4);
        altaCliente.add(comboBoxAltaCliente, new GridConstraints(8, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Nombre:");
        altaCliente.add(label23, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        altaCliente.add(spacer11, new GridConstraints(12, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        altaCliente.add(spacer12, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Correo:");
        altaCliente.add(label24, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        correoAltaCliente = new JTextField();
        altaCliente.add(correoAltaCliente, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer13 = new Spacer();
        altaCliente.add(spacer13, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        altaCliente.add(spacer14, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        altaCliente.add(spacer15, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        altaCliente.add(spacer16, new GridConstraints(14, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        altaCliente.add(spacer17, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer18 = new Spacer();
        altaCliente.add(spacer18, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer19 = new Spacer();
        altaCliente.add(spacer19, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer20 = new Spacer();
        altaCliente.add(spacer20, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer21 = new Spacer();
        altaCliente.add(spacer21, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("Fecha de nacimiento:");
        altaCliente.add(label25, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaCliente.add(JCalendarAltaCliente, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("Número de documento:");
        altaCliente.add(label26, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numeroDocAltaCliente = new JTextField();
        altaCliente.add(numeroDocAltaCliente, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        aceptarAltaClienteButton = new JButton();
        aceptarAltaClienteButton.setText("Aceptar");
        altaCliente.add(aceptarAltaClienteButton, new GridConstraints(13, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelarAltaClienteButton = new JButton();
        cancelarAltaClienteButton.setText("Cancelar");
        altaCliente.add(cancelarAltaClienteButton, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("Contraseña:");
        altaCliente.add(label27, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label28 = new JLabel();
        label28.setText("Confirmar Contraseña:");
        altaCliente.add(label28, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaClienteSubirImagen = new JButton();
        altaClienteSubirImagen.setText("Subir imagen");
        altaCliente.add(altaClienteSubirImagen, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaClienteMostrarFoto = new JLabel();
        altaClienteMostrarFoto.setText("No hay imagen seleccionada");
        altaCliente.add(altaClienteMostrarFoto, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaClienteContrasena = new JPasswordField();
        altaCliente.add(altaClienteContrasena, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        altaClienteConfirmarContrasena = new JPasswordField();
        altaCliente.add(altaClienteConfirmarContrasena, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        altaVuelo = new JPanel();
        altaVuelo.setLayout(new GridLayoutManager(7, 6, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(altaVuelo, "Card3");
        altaVuelo.setBorder(BorderFactory.createTitledBorder(null, "Alta vuelo", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer22 = new Spacer();
        altaVuelo.add(spacer22, new GridConstraints(5, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        fromAltaVuelo = new JPanel();
        fromAltaVuelo.setLayout(new GridLayoutManager(4, 6, new Insets(0, 0, 0, 0), -1, -1));
        altaVuelo.add(fromAltaVuelo, new GridConstraints(2, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(700, 500), null, 0, false));
        fromAltaVuelo.setBorder(BorderFactory.createTitledBorder(null, "Informacion de Vuelo", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
        final JLabel label29 = new JLabel();
        label29.setText("Duracion");
        fromAltaVuelo.add(label29, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label30 = new JLabel();
        label30.setText("Nombre");
        fromAltaVuelo.add(label30, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        asientosMaxTuristatxt = new JTextField();
        fromAltaVuelo.add(asientosMaxTuristatxt, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label31 = new JLabel();
        label31.setText("Cantidad Máxima de Asientos en Clase Ejecutiva");
        fromAltaVuelo.add(label31, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        asientoMaxEjecutivotxt = new JTextField();
        fromAltaVuelo.add(asientoMaxEjecutivotxt, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label32 = new JLabel();
        label32.setText("Cantidad Máxima de Asientos en Clase Turista");
        label32.setVerticalAlignment(1);
        label32.setVerticalTextPosition(1);
        fromAltaVuelo.add(label32, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        fromAltaVuelo.add(panel1, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label33 = new JLabel();
        label33.setText("Fecha de Alta");
        panel1.add(label33, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer23 = new Spacer();
        panel1.add(spacer23, new GridConstraints(1, 1, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label34 = new JLabel();
        label34.setText("Fecha de Vuelo");
        panel1.add(label34, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(fechaAltaVuelo, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(fechaVuelo, new GridConstraints(1, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        duracionAltaVuelotxt = new JTextField();
        fromAltaVuelo.add(duracionAltaVuelotxt, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nombreAltaVuelotxt = new JTextField();
        fromAltaVuelo.add(nombreAltaVuelotxt, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        horaVuelotxt = new JTextField();
        fromAltaVuelo.add(horaVuelotxt, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label35 = new JLabel();
        label35.setText("Hora vuelo");
        fromAltaVuelo.add(label35, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAltaVuelo = new JButton();
        buttonAltaVuelo.setText("Aceptar");
        altaVuelo.add(buttonAltaVuelo, new GridConstraints(5, 3, 2, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label36 = new JLabel();
        label36.setText("Seleccione una Aerolinea");
        altaVuelo.add(label36, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label37 = new JLabel();
        label37.setText("Seleccione una Ruta de Vuelo");
        altaVuelo.add(label37, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancelarVuelo = new JButton();
        buttonCancelarVuelo.setText("Cancelar");
        altaVuelo.add(buttonCancelarVuelo, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer24 = new Spacer();
        altaVuelo.add(spacer24, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer25 = new Spacer();
        altaVuelo.add(spacer25, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer26 = new Spacer();
        altaVuelo.add(spacer26, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        rutasVueloAltaVuelo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        rutasVueloAltaVuelo.setModel(defaultComboBoxModel5);
        altaVuelo.add(rutasVueloAltaVuelo, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aerolinea = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel6 = new DefaultComboBoxModel();
        aerolinea.setModel(defaultComboBoxModel6);
        altaVuelo.add(aerolinea, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer27 = new Spacer();
        altaVuelo.add(spacer27, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonImagenAltaVuelo = new JButton();
        buttonImagenAltaVuelo.setText("Subir imagen");
        altaVuelo.add(buttonImagenAltaVuelo, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaUsuario = new JPanel();
        altaUsuario.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(altaUsuario, "Card7");
        altaUsuario.setBorder(BorderFactory.createTitledBorder(null, "Crear nuevo usuario", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        ButtonCrearNuevoCliente = new JButton();
        ButtonCrearNuevoCliente.setText("Crear nuevo Cliente");
        altaUsuario.add(ButtonCrearNuevoCliente, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonCrearNuevaAerolinea = new JButton();
        ButtonCrearNuevaAerolinea.setText("Crear nueva Aerolinea");
        altaUsuario.add(ButtonCrearNuevaAerolinea, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer28 = new Spacer();
        altaUsuario.add(spacer28, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer29 = new Spacer();
        altaUsuario.add(spacer29, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer30 = new Spacer();
        altaUsuario.add(spacer30, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer31 = new Spacer();
        altaUsuario.add(spacer31, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer32 = new Spacer();
        altaUsuario.add(spacer32, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer33 = new Spacer();
        altaUsuario.add(spacer33, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer34 = new Spacer();
        altaUsuario.add(spacer34, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer35 = new Spacer();
        altaUsuario.add(spacer35, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modificarUsuario = new JPanel();
        modificarUsuario.setLayout(new GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(modificarUsuario, "Card8");
        modificarUsuario.setBorder(BorderFactory.createTitledBorder(null, "Modificar Usuario", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label38 = new JLabel();
        label38.setText("Ingrese Usuario");
        modificarUsuario.add(label38, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarUsuarioTextInput = new JTextField();
        modificarUsuario.add(modificarUsuarioTextInput, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modificarUsuarioAceptar = new JButton();
        modificarUsuarioAceptar.setText("Aceptar");
        modificarUsuario.add(modificarUsuarioAceptar, new GridConstraints(4, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer36 = new Spacer();
        modificarUsuario.add(spacer36, new GridConstraints(5, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modificarUsuarioJPanel1 = new JPanel();
        modificarUsuarioJPanel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        modificarUsuario.add(modificarUsuarioJPanel1, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        modificarUsuariotable1 = new JTable();
        modificarUsuarioJPanel1.add(modificarUsuariotable1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer37 = new Spacer();
        modificarUsuario.add(spacer37, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer38 = new Spacer();
        modificarUsuario.add(spacer38, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer39 = new Spacer();
        modificarUsuario.add(spacer39, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer40 = new Spacer();
        modificarUsuario.add(spacer40, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        modificarUsuarioCancelar = new JButton();
        modificarUsuarioCancelar.setText("Cancelar");
        modificarUsuario.add(modificarUsuarioCancelar, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaRuta = new JPanel();
        altaRuta.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(altaRuta, "Card9");
        altaRuta.setBorder(BorderFactory.createTitledBorder(null, "Dar Alta Ruta Vuelo", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        altaRuta.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label39 = new JLabel();
        label39.setText("Seleccione una Aerolinea");
        panel2.add(label39, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer41 = new Spacer();
        panel2.add(spacer41, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(700, 500), null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Informacion de Vuelo", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
        final JLabel label40 = new JLabel();
        label40.setText("Categoria");
        panel3.add(label40, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombreAltRutaText = new JTextField();
        panel3.add(nombreAltRutaText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label41 = new JLabel();
        label41.setText("Nombre");
        panel3.add(label41, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label42 = new JLabel();
        label42.setText("Ciudad Destino");
        panel3.add(label42, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label43 = new JLabel();
        label43.setText("Costo Ejecutivo");
        panel3.add(label43, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costoEjText = new JTextField();
        panel3.add(costoEjText, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label44 = new JLabel();
        label44.setText("Costo Turista");
        panel3.add(label44, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costoTurText = new JTextField();
        panel3.add(costoTurText, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label45 = new JLabel();
        label45.setText("Costo Unidad Equipaje Extra");
        panel3.add(label45, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costoEqExText = new JTextField();
        panel3.add(costoEqExText, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label46 = new JLabel();
        label46.setText("Ciudad Origen");
        label46.setVerticalAlignment(1);
        label46.setVerticalTextPosition(1);
        panel3.add(label46, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label47 = new JLabel();
        label47.setText("Fecha de Alta");
        panel4.add(label47, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer42 = new Spacer();
        panel4.add(spacer42, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label48 = new JLabel();
        label48.setText("Descripcion");
        panel4.add(label48, new GridConstraints(0, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel4.add(fechaAltaRutaVuelo, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        descRutaText = new JTextArea();
        panel4.add(descRutaText, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        listCatAltaRuta = new JList();
        panel3.add(listCatAltaRuta, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        comboBoxCiudadOrigenARV = new JComboBox();
        panel3.add(comboBoxCiudadOrigenARV, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxCiudadDestinoARV = new JComboBox();
        panel3.add(comboBoxCiudadDestinoARV, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonImagenRVuelo = new JButton();
        buttonImagenRVuelo.setText("Subir imagen");
        panel3.add(buttonImagenRVuelo, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aerolineaVuelo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel7 = new DefaultComboBoxModel();
        aerolineaVuelo.setModel(defaultComboBoxModel7);
        panel2.add(aerolineaVuelo, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aceptarRuta = new JButton();
        aceptarRuta.setText("Aceptar");
        panel2.add(aceptarRuta, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancelarCrearRutaV = new JButton();
        buttonCancelarCrearRutaV.setText("Cancelar");
        panel2.add(buttonCancelarCrearRutaV, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer43 = new Spacer();
        panel2.add(spacer43, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        consultaVuelo = new JPanel();
        consultaVuelo.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(consultaVuelo, "Card10");
        consultaVuelo.setBorder(BorderFactory.createTitledBorder(null, "Consultar Vuelo", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(12, 3, new Insets(0, 0, 0, 0), -1, -1));
        consultaVuelo.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Consulta de  Vuelo", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label49 = new JLabel();
        label49.setText("Seleccione una Aerolínea");
        panel5.add(label49, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxAeroConsultaV = new JComboBox();
        panel5.add(comboBoxAeroConsultaV, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label50 = new JLabel();
        label50.setText("Vuelos asociados");
        panel5.add(label50, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label51 = new JLabel();
        label51.setText("Nombre del vuelo");
        panel5.add(label51, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombVueloConsultaVtxt = new JTextArea();
        nombVueloConsultaVtxt.setEditable(false);
        panel5.add(nombVueloConsultaVtxt, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label52 = new JLabel();
        label52.setText("Fecha del vuelo");
        panel5.add(label52, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaVueloConsultaVtxt = new JTextArea();
        fechaVueloConsultaVtxt.setEditable(false);
        fechaVueloConsultaVtxt.setText("");
        panel5.add(fechaVueloConsultaVtxt, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label53 = new JLabel();
        label53.setText("Hora del vuelo");
        panel5.add(label53, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        horaVueloConsultaVtxt = new JTextArea();
        horaVueloConsultaVtxt.setEditable(false);
        panel5.add(horaVueloConsultaVtxt, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label54 = new JLabel();
        label54.setText("Duración del vuelo");
        panel5.add(label54, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        duracionVueloConsultaVtxt = new JTextArea();
        duracionVueloConsultaVtxt.setEditable(false);
        panel5.add(duracionVueloConsultaVtxt, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label55 = new JLabel();
        label55.setText("Cantidad de asientos máximos en Clase Turista");
        panel5.add(label55, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        maxTuristaConsultaVtxt = new JTextArea();
        maxTuristaConsultaVtxt.setEditable(false);
        panel5.add(maxTuristaConsultaVtxt, new GridConstraints(7, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        maxEjecutivoConsultaVtxt = new JTextArea();
        maxEjecutivoConsultaVtxt.setEditable(false);
        panel5.add(maxEjecutivoConsultaVtxt, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label56 = new JLabel();
        label56.setText("Cantidad de asientos máximos en Clase Ejecutiva");
        panel5.add(label56, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaAltaVueloConsultaVtxt = new JTextArea();
        fechaAltaVueloConsultaVtxt.setEditable(false);
        panel5.add(fechaAltaVueloConsultaVtxt, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 52), null, 0, false));
        final JLabel label57 = new JLabel();
        label57.setText("Fecha de alta de vuelo");
        panel5.add(label57, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(139, 52), null, 0, false));
        final JLabel label58 = new JLabel();
        label58.setText("Seleccionar Ruta de Vuelo");
        panel5.add(label58, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxRutasVuelosConsultaV = new JComboBox();
        panel5.add(comboBoxRutasVuelosConsultaV, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxVuelosConsultaV = new JComboBox();
        panel5.add(comboBoxVuelosConsultaV, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxReservasConsultaV = new JComboBox();
        panel5.add(comboBoxReservasConsultaV, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label59 = new JLabel();
        label59.setText("Reservas");
        panel5.add(label59, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelImagenCVuelo = new JLabel();
        labelImagenCVuelo.setText("Imagen");
        panel5.add(labelImagenCVuelo, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaCiudad = new JPanel();
        altaCiudad.setLayout(new GridBagLayout());
        parentPanel.add(altaCiudad, "Card11");
        final JPanel spacer44 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        altaCiudad.add(spacer44, gbc);
        final JPanel spacer45 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        altaCiudad.add(spacer45, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(12, 9, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 10.0;
        gbc.weighty = 10.0;
        gbc.fill = GridBagConstraints.BOTH;
        altaCiudad.add(panel6, gbc);
        final Spacer spacer46 = new Spacer();
        panel6.add(spacer46, new GridConstraints(0, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer47 = new Spacer();
        panel6.add(spacer47, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        ciudadAltaText = new JTextField();
        panel6.add(ciudadAltaText, new GridConstraints(2, 4, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer48 = new Spacer();
        panel6.add(spacer48, new GridConstraints(2, 6, 9, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer49 = new Spacer();
        panel6.add(spacer49, new GridConstraints(3, 5, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer50 = new Spacer();
        panel6.add(spacer50, new GridConstraints(1, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        CiudadAltaText = new JLabel();
        CiudadAltaText.setText("Ciudad:");
        panel6.add(CiudadAltaText, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AeropuertoCiuText = new JLabel();
        AeropuertoCiuText.setText("Aeropuerto:");
        panel6.add(AeropuertoCiuText, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SitioWebCiuText = new JLabel();
        SitioWebCiuText.setText("Sitio Web:");
        panel6.add(SitioWebCiuText, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descripcionAltaCiText = new JTextField();
        panel6.add(descripcionAltaCiText, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        DescCiudadText = new JLabel();
        DescCiudadText.setText("Descripción:");
        panel6.add(DescCiudadText, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sitioWebAltaCiText = new JTextField();
        panel6.add(sitioWebAltaCiText, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panel6.add(calendarCiudadAlta, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FechaAltaCiudText = new JLabel();
        FechaAltaCiudText.setText("Fecha alta:");
        panel6.add(FechaAltaCiudText, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PaisAltaText = new JLabel();
        PaisAltaText.setText("Pais:");
        panel6.add(PaisAltaText, new GridConstraints(3, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paisAltaCiText = new JTextField();
        panel6.add(paisAltaCiText, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonAltaCiudad = new JButton();
        buttonAltaCiudad.setText("Aceptar");
        panel6.add(buttonAltaCiudad, new GridConstraints(10, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer51 = new Spacer();
        panel6.add(spacer51, new GridConstraints(11, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer52 = new Spacer();
        panel6.add(spacer52, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        buttonCancelarCiudad = new JButton();
        buttonCancelarCiudad.setText("Cancelar");
        panel6.add(buttonCancelarCiudad, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer53 = new Spacer();
        panel6.add(spacer53, new GridConstraints(10, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        comboBoxAeropuertosAC = new JComboBox();
        panel6.add(comboBoxAeropuertosAC, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        altaCategoría = new JPanel();
        altaCategoría.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(altaCategoría, "Card12");
        final JLabel label60 = new JLabel();
        label60.setText("Nombre de la categoría:");
        altaCategoría.add(label60, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer54 = new Spacer();
        altaCategoría.add(spacer54, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer55 = new Spacer();
        altaCategoría.add(spacer55, new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        categoriaAltaText = new JTextField();
        altaCategoría.add(categoriaAltaText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer56 = new Spacer();
        altaCategoría.add(spacer56, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonAltaCategoria = new JButton();
        buttonAltaCategoria.setText("Aceptar");
        altaCategoría.add(buttonAltaCategoria, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarAerolinea = new JPanel();
        modificarAerolinea.setLayout(new GridLayoutManager(10, 4, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(modificarAerolinea, "Card12");
        modificarAerolinea.setBorder(BorderFactory.createTitledBorder(null, "Modificar Aerolinea", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer57 = new Spacer();
        modificarAerolinea.add(spacer57, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label61 = new JLabel();
        label61.setText("Nombre:");
        modificarAerolinea.add(label61, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarAerolineaTextNombre = new JTextField();
        modificarAerolinea.add(modificarAerolineaTextNombre, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modificarAerolineaTextLink = new JTextField();
        modificarAerolinea.add(modificarAerolineaTextLink, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label62 = new JLabel();
        label62.setText("Link a sitio web:");
        modificarAerolinea.add(label62, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label63 = new JLabel();
        label63.setText("Descripcion:");
        modificarAerolinea.add(label63, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarAerolineaTextArea = new JTextArea();
        modificarAerolinea.add(modificarAerolineaTextArea, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer58 = new Spacer();
        modificarAerolinea.add(spacer58, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modificarAerolineaCancelar = new JButton();
        modificarAerolineaCancelar.setText("Cancelar");
        modificarAerolinea.add(modificarAerolineaCancelar, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer59 = new Spacer();
        modificarAerolinea.add(spacer59, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer60 = new Spacer();
        modificarAerolinea.add(spacer60, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer61 = new Spacer();
        modificarAerolinea.add(spacer61, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modificarAerolineaGuardar = new JButton();
        modificarAerolineaGuardar.setText("Guardar");
        modificarAerolinea.add(modificarAerolineaGuardar, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarAerolineaSubirImagen = new JButton();
        modificarAerolineaSubirImagen.setText("Subir imagen");
        modificarAerolinea.add(modificarAerolineaSubirImagen, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarAerolineaMostrarFoto = new JLabel();
        modificarAerolineaMostrarFoto.setText("No hay imagen seleccionada");
        modificarAerolinea.add(modificarAerolineaMostrarFoto, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label64 = new JLabel();
        label64.setText("Contraseña:");
        modificarAerolinea.add(label64, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label65 = new JLabel();
        label65.setText("Confirmar contraseña:");
        modificarAerolinea.add(label65, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarAerolineaContrasena = new JPasswordField();
        modificarAerolinea.add(modificarAerolineaContrasena, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modificarAerolineaConfirmarContrasena = new JPasswordField();
        modificarAerolinea.add(modificarAerolineaConfirmarContrasena, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modificarCliente = new JPanel();
        modificarCliente.setLayout(new GridLayoutManager(13, 4, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(modificarCliente, "Card13");
        modificarCliente.setBorder(BorderFactory.createTitledBorder(null, "Modificar Cliente", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer62 = new Spacer();
        modificarCliente.add(spacer62, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label66 = new JLabel();
        label66.setText("Nombre:");
        modificarCliente.add(label66, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteNombre = new JTextField();
        modificarCliente.add(modificarClienteNombre, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label67 = new JLabel();
        label67.setText("Apellido:");
        modificarCliente.add(label67, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteApellido = new JTextField();
        modificarCliente.add(modificarClienteApellido, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label68 = new JLabel();
        label68.setText("Nacionalidad:");
        modificarCliente.add(label68, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteNacionalidad = new JTextField();
        modificarCliente.add(modificarClienteNacionalidad, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label69 = new JLabel();
        label69.setText("Tipo documento:");
        modificarCliente.add(label69, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel8 = new DefaultComboBoxModel();
        defaultComboBoxModel8.addElement("CI");
        defaultComboBoxModel8.addElement("DNI");
        defaultComboBoxModel8.addElement("Pasaporte");
        modificarClienteComboBox.setModel(defaultComboBoxModel8);
        modificarCliente.add(modificarClienteComboBox, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteDocumento = new JTextField();
        modificarCliente.add(modificarClienteDocumento, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label70 = new JLabel();
        label70.setText("Documento:");
        modificarCliente.add(label70, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteGuardar = new JButton();
        modificarClienteGuardar.setText("Guardar");
        modificarCliente.add(modificarClienteGuardar, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer63 = new Spacer();
        modificarCliente.add(spacer63, new GridConstraints(12, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modificarClienteCancelar = new JButton();
        modificarClienteCancelar.setText("Cancelar");
        modificarCliente.add(modificarClienteCancelar, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer64 = new Spacer();
        modificarCliente.add(spacer64, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer65 = new Spacer();
        modificarCliente.add(spacer65, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer66 = new Spacer();
        modificarCliente.add(spacer66, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modificarCliente.add(modificarClienteJCalendar, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label71 = new JLabel();
        label71.setText("Fecha Nacimiento:");
        modificarCliente.add(label71, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label72 = new JLabel();
        label72.setText("Contraseña:");
        modificarCliente.add(label72, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label73 = new JLabel();
        label73.setText("Confirmar contraseña:");
        modificarCliente.add(label73, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteSubirImagen = new JButton();
        modificarClienteSubirImagen.setText("Subir imagen");
        modificarCliente.add(modificarClienteSubirImagen, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteMostrarFoto = new JLabel();
        modificarClienteMostrarFoto.setText("No hay imagen seleccionada");
        modificarCliente.add(modificarClienteMostrarFoto, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificarClienteContrasena = new JPasswordField();
        modificarCliente.add(modificarClienteContrasena, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modificarClienteConfirmarContrasena = new JPasswordField();
        modificarCliente.add(modificarClienteConfirmarContrasena, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        crearPaquete = new JPanel();
        crearPaquete.setLayout(new GridLayoutManager(9, 5, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(crearPaquete, "Card14");
        final JLabel label74 = new JLabel();
        label74.setText("Nombre:");
        crearPaquete.add(label74, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer67 = new Spacer();
        crearPaquete.add(spacer67, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        nombreAltaPaqtxt = new JTextField();
        crearPaquete.add(nombreAltaPaqtxt, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer68 = new Spacer();
        crearPaquete.add(spacer68, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer69 = new Spacer();
        crearPaquete.add(spacer69, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer70 = new Spacer();
        crearPaquete.add(spacer70, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label75 = new JLabel();
        label75.setText("Descripción:");
        crearPaquete.add(label75, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descripcionAltaPaqtxt = new JTextField();
        crearPaquete.add(descripcionAltaPaqtxt, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label76 = new JLabel();
        label76.setText("Período de validez (días):");
        crearPaquete.add(label76, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        períodoAltaPaqtxt = new JTextField();
        crearPaquete.add(períodoAltaPaqtxt, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label77 = new JLabel();
        label77.setText("Descuento:");
        crearPaquete.add(label77, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descuentoAltaPaqtxt = new JTextField();
        crearPaquete.add(descuentoAltaPaqtxt, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label78 = new JLabel();
        label78.setText("Fecha de alta");
        crearPaquete.add(label78, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        crearPaquete.add(calendarAltaPaquete, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCancelarCrearPaquete = new JButton();
        buttonCancelarCrearPaquete.setText("Cancelar");
        crearPaquete.add(buttonCancelarCrearPaquete, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCrearPaquete = new JButton();
        buttonCrearPaquete.setText("Aceptar");
        crearPaquete.add(buttonCrearPaquete, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer71 = new Spacer();
        crearPaquete.add(spacer71, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        buttonImagenPaquete = new JButton();
        buttonImagenPaquete.setText("Subir Imagen");
        crearPaquete.add(buttonImagenPaquete, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agregarRutaaPaquete = new JPanel();
        agregarRutaaPaquete.setLayout(new GridLayoutManager(8, 6, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(agregarRutaaPaquete, "Card15");
        final JLabel label79 = new JLabel();
        label79.setText("Paquete:");
        agregarRutaaPaquete.add(label79, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label80 = new JLabel();
        label80.setText("Aerolinea:");
        agregarRutaaPaquete.add(label80, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer72 = new Spacer();
        agregarRutaaPaquete.add(spacer72, new GridConstraints(6, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer73 = new Spacer();
        agregarRutaaPaquete.add(spacer73, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer74 = new Spacer();
        agregarRutaaPaquete.add(spacer74, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer75 = new Spacer();
        agregarRutaaPaquete.add(spacer75, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer76 = new Spacer();
        agregarRutaaPaquete.add(spacer76, new GridConstraints(0, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        comboBoxAeroAgrRutaaPaquete = new JComboBox();
        agregarRutaaPaquete.add(comboBoxAeroAgrRutaaPaquete, new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxPaqueteAgrRutaaPaquete = new JComboBox();
        agregarRutaaPaquete.add(comboBoxPaqueteAgrRutaaPaquete, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label81 = new JLabel();
        label81.setText("Ruta de vuelo:");
        agregarRutaaPaquete.add(label81, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxRutaVueloAgrRutaaPaquete = new JComboBox();
        agregarRutaaPaquete.add(comboBoxRutaVueloAgrRutaaPaquete, new GridConstraints(3, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label82 = new JLabel();
        label82.setText("Cantidad:");
        agregarRutaaPaquete.add(label82, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantidadAgrRutaaPaquetetxt = new JTextField();
        agregarRutaaPaquete.add(cantidadAgrRutaaPaquetetxt, new GridConstraints(4, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label83 = new JLabel();
        label83.setText("Tipo de asiento:");
        agregarRutaaPaquete.add(label83, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAceptarAgrRutaaPaquete = new JButton();
        buttonAceptarAgrRutaaPaquete.setText("Aceptar");
        agregarRutaaPaquete.add(buttonAceptarAgrRutaaPaquete, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer77 = new Spacer();
        agregarRutaaPaquete.add(spacer77, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonCancelarAgrRutaaPaquete = new JButton();
        buttonCancelarAgrRutaaPaquete.setText("Cancelar");
        agregarRutaaPaquete.add(buttonCancelarAgrRutaaPaquete, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxTipoAsientoAgrRutaaPaquete = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel9 = new DefaultComboBoxModel();
        defaultComboBoxModel9.addElement("");
        defaultComboBoxModel9.addElement("Turista");
        defaultComboBoxModel9.addElement("Ejecutivo");
        comboBoxTipoAsientoAgrRutaaPaquete.setModel(defaultComboBoxModel9);
        agregarRutaaPaquete.add(comboBoxTipoAsientoAgrRutaaPaquete, new GridConstraints(5, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaPaqueteRutaVuelo = new JPanel();
        consultaPaqueteRutaVuelo.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(consultaPaqueteRutaVuelo, "Card17");
        consultaPaqueteRutaVueloJPanelTable = new JPanel();
        consultaPaqueteRutaVueloJPanelTable.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        consultaPaqueteRutaVuelo.add(consultaPaqueteRutaVueloJPanelTable, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label84 = new JLabel();
        label84.setText("Paquete:");
        consultaPaqueteRutaVueloJPanelTable.add(label84, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer78 = new Spacer();
        consultaPaqueteRutaVueloJPanelTable.add(spacer78, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        consultaPaqueteRutaVueloJPanelTable.add(comboBox1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer79 = new Spacer();
        consultaPaqueteRutaVuelo.add(spacer79, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer80 = new Spacer();
        consultaPaqueteRutaVuelo.add(spacer80, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        consultaPaqueteRutaVueloParentPanel = new JPanel();
        consultaPaqueteRutaVueloParentPanel.setLayout(new CardLayout(0, 0));
        consultaPaqueteRutaVuelo.add(consultaPaqueteRutaVueloParentPanel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        consultaPaqueteRutaVueloSeleccionarPaquete = new JPanel();
        consultaPaqueteRutaVueloSeleccionarPaquete.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        consultaPaqueteRutaVueloParentPanel.add(consultaPaqueteRutaVueloSeleccionarPaquete, "Card1");
        consultaPaqueteRutaVueloSeleccionarPaqueteInput = new JTextField();
        consultaPaqueteRutaVueloSeleccionarPaquete.add(consultaPaqueteRutaVueloSeleccionarPaqueteInput, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        consultaPaqueteRutaVueloSeleccionarPaqueteAceptar = new JButton();
        consultaPaqueteRutaVueloSeleccionarPaqueteAceptar.setText("Aceptar");
        consultaPaqueteRutaVueloSeleccionarPaquete.add(consultaPaqueteRutaVueloSeleccionarPaqueteAceptar, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaPaqueteRutaVueloSeleccionarPaqueteCancelar = new JButton();
        consultaPaqueteRutaVueloSeleccionarPaqueteCancelar.setText("Cancelar");
        consultaPaqueteRutaVueloSeleccionarPaquete.add(consultaPaqueteRutaVueloSeleccionarPaqueteCancelar, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer81 = new Spacer();
        consultaPaqueteRutaVuelo.add(spacer81, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer82 = new Spacer();
        consultaPaqueteRutaVuelo.add(spacer82, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer83 = new Spacer();
        consultaPaqueteRutaVuelo.add(spacer83, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ComprarPaquete = new JPanel();
        ComprarPaquete.setLayout(new GridLayoutManager(7, 7, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(ComprarPaquete, "Card18");
        final Spacer spacer84 = new Spacer();
        ComprarPaquete.add(spacer84, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer85 = new Spacer();
        ComprarPaquete.add(spacer85, new GridConstraints(3, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comboBoxPaquetesComprarPaquete = new JComboBox();
        ComprarPaquete.add(comboBoxPaquetesComprarPaquete, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer86 = new Spacer();
        ComprarPaquete.add(spacer86, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer87 = new Spacer();
        ComprarPaquete.add(spacer87, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer88 = new Spacer();
        ComprarPaquete.add(spacer88, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer89 = new Spacer();
        ComprarPaquete.add(spacer89, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer90 = new Spacer();
        ComprarPaquete.add(spacer90, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label85 = new JLabel();
        label85.setText("Paquete:");
        ComprarPaquete.add(label85, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label86 = new JLabel();
        label86.setText("Cliente:                                  ");
        ComprarPaquete.add(label86, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer91 = new Spacer();
        ComprarPaquete.add(spacer91, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer92 = new Spacer();
        ComprarPaquete.add(spacer92, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cancelarButtonComprarPaquete = new JButton();
        cancelarButtonComprarPaquete.setText("Cancelar");
        ComprarPaquete.add(cancelarButtonComprarPaquete, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aceptarComprarPaquete = new JButton();
        aceptarComprarPaquete.setText("Aceptar");
        ComprarPaquete.add(aceptarComprarPaquete, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxClientesComprarPaquete = new JComboBox();
        ComprarPaquete.add(comboBoxClientesComprarPaquete, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaUsuarioScroll = new JScrollPane();
        parentPanel.add(consultaUsuarioScroll, "Card19");
        consultaUsuario = new JPanel();
        consultaUsuario.setLayout(new GridLayoutManager(8, 9, new Insets(0, 0, 0, 0), -1, -1));
        consultaUsuarioScroll.setViewportView(consultaUsuario);
        consultaUsuario.setBorder(BorderFactory.createTitledBorder(null, "Consulta de Usuario", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        reservaDeVueloRadioButton = new JRadioButton();
        reservaDeVueloRadioButton.setText("Vuelo");
        consultaUsuario.add(reservaDeVueloRadioButton, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaUsuarioText = new JTextField();
        consultaUsuario.add(consultaUsuarioText, new GridConstraints(2, 3, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer93 = new Spacer();
        consultaUsuario.add(spacer93, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer94 = new Spacer();
        consultaUsuario.add(spacer94, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer95 = new Spacer();
        consultaUsuario.add(spacer95, new GridConstraints(4, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label87 = new JLabel();
        label87.setText("Consultar usuario:");
        consultaUsuario.add(label87, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rutaDeVueloRadioButton = new JRadioButton();
        rutaDeVueloRadioButton.setText("Ruta de vuelo");
        consultaUsuario.add(rutaDeVueloRadioButton, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaUsuarioJpanel1 = new JPanel();
        consultaUsuarioJpanel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        consultaUsuario.add(consultaUsuarioJpanel1, new GridConstraints(0, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        consultaUsuarioTable1 = new JTable();
        consultaUsuarioJpanel1.add(consultaUsuarioTable1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        paqueteVueloRadioButton = new JRadioButton();
        paqueteVueloRadioButton.setText("Paquete vuelo");
        consultaUsuario.add(paqueteVueloRadioButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaUsuarioAceptar = new JButton();
        consultaUsuarioAceptar.setText("Aceptar");
        consultaUsuario.add(consultaUsuarioAceptar, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label88 = new JLabel();
        label88.setText("Consultar:");
        consultaUsuario.add(label88, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listarUsuariosButton = new JButton();
        listarUsuariosButton.setText("Listar Usuarios");
        consultaUsuario.add(listarUsuariosButton, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer96 = new Spacer();
        consultaUsuario.add(spacer96, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        consultaUsuarioParentPanel = new JPanel();
        consultaUsuarioParentPanel.setLayout(new CardLayout(0, 0));
        consultaUsuario.add(consultaUsuarioParentPanel, new GridConstraints(6, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), null, null, 0, false));
        final Spacer spacer97 = new Spacer();
        consultaUsuario.add(spacer97, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        consultaUsuarioCancelar = new JButton();
        consultaUsuarioCancelar.setText("Cancelar");
        consultaUsuario.add(consultaUsuarioCancelar, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ConsultaPaquete = new JPanel();
        ConsultaPaquete.setLayout(new GridLayoutManager(10, 6, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(ConsultaPaquete, "Card4");
        final JLabel label89 = new JLabel();
        label89.setText("Paquete:");
        ConsultaPaquete.add(label89, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer98 = new Spacer();
        ConsultaPaquete.add(spacer98, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer99 = new Spacer();
        ConsultaPaquete.add(spacer99, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comboBoxPaqueteConsultaPaquete = new JComboBox();
        ConsultaPaquete.add(comboBoxPaqueteConsultaPaquete, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label90 = new JLabel();
        label90.setText("Descripción:");
        ConsultaPaquete.add(label90, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label91 = new JLabel();
        label91.setText("Dias válidos:");
        ConsultaPaquete.add(label91, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label92 = new JLabel();
        label92.setText("Descuento:");
        ConsultaPaquete.add(label92, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label93 = new JLabel();
        label93.setText("Costo:");
        ConsultaPaquete.add(label93, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label94 = new JLabel();
        label94.setText("Fecha de alta:");
        ConsultaPaquete.add(label94, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descripcionCPtxt = new JTextArea();
        descripcionCPtxt.setEditable(false);
        ConsultaPaquete.add(descripcionCPtxt, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        descuentoCPtxt = new JTextArea();
        descuentoCPtxt.setEditable(false);
        ConsultaPaquete.add(descuentoCPtxt, new GridConstraints(4, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        costoCPtxt = new JTextArea();
        costoCPtxt.setEditable(false);
        ConsultaPaquete.add(costoCPtxt, new GridConstraints(5, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        fechaAltaCPtxt = new JTextArea();
        fechaAltaCPtxt.setEditable(false);
        ConsultaPaquete.add(fechaAltaCPtxt, new GridConstraints(6, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer100 = new Spacer();
        ConsultaPaquete.add(spacer100, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer101 = new Spacer();
        ConsultaPaquete.add(spacer101, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label95 = new JLabel();
        label95.setText("Rutas de vuelo:");
        ConsultaPaquete.add(label95, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxRutasVueloCP = new JComboBox();
        ConsultaPaquete.add(comboBoxRutasVueloCP, new GridConstraints(7, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        diasvalidosCPtxt = new JTextArea();
        diasvalidosCPtxt.setEditable(false);
        ConsultaPaquete.add(diasvalidosCPtxt, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer102 = new Spacer();
        ConsultaPaquete.add(spacer102, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelImagenPaquete = new JLabel();
        labelImagenPaquete.setText("Imagen");
        ConsultaPaquete.add(labelImagenPaquete, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer103 = new Spacer();
        ConsultaPaquete.add(spacer103, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        reservaVuelo = new JPanel();
        reservaVuelo.setLayout(new GridLayoutManager(14, 4, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(reservaVuelo, "Card16");
        final JLabel label96 = new JLabel();
        label96.setText("Aerolínea:");
        reservaVuelo.add(label96, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer104 = new Spacer();
        reservaVuelo.add(spacer104, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        comboBoxAeroReservaV = new JComboBox();
        reservaVuelo.add(comboBoxAeroReservaV, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label97 = new JLabel();
        label97.setText("Rutas de vuelo:");
        reservaVuelo.add(label97, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxRutasVueloReservaV = new JComboBox();
        reservaVuelo.add(comboBoxRutasVueloReservaV, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label98 = new JLabel();
        label98.setText("Vuelos:");
        reservaVuelo.add(label98, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxVuelosReservaV = new JComboBox();
        reservaVuelo.add(comboBoxVuelosReservaV, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label99 = new JLabel();
        label99.setText("");
        reservaVuelo.add(label99, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        reservaVuelo.add(panel7, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(null, "Datos del vuelo a reservar", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label100 = new JLabel();
        label100.setText("Nombre del vuelo");
        panel7.add(label100, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombreVueloReservaV = new JTextArea();
        nombreVueloReservaV.setEditable(false);
        panel7.add(nombreVueloReservaV, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label101 = new JLabel();
        label101.setText("Fecha del vuelo");
        panel7.add(label101, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaVReservaV = new JTextArea();
        fechaVReservaV.setEditable(false);
        fechaVReservaV.setText("");
        panel7.add(fechaVReservaV, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label102 = new JLabel();
        label102.setText("Hora del vuelo");
        panel7.add(label102, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        horaVReservaV = new JTextArea();
        horaVReservaV.setEditable(false);
        panel7.add(horaVReservaV, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label103 = new JLabel();
        label103.setText("Duración del vuelo");
        panel7.add(label103, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        duracionVReservaV = new JTextArea();
        duracionVReservaV.setEditable(false);
        panel7.add(duracionVReservaV, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label104 = new JLabel();
        label104.setText("Cantidad de asientos máximos en Clase Turista");
        panel7.add(label104, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantidadAsientosTReservaV = new JTextArea();
        cantidadAsientosTReservaV.setEditable(false);
        panel7.add(cantidadAsientosTReservaV, new GridConstraints(4, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        cantidadAsientosEReservaV = new JTextArea();
        cantidadAsientosEReservaV.setEditable(false);
        panel7.add(cantidadAsientosEReservaV, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label105 = new JLabel();
        label105.setText("Cantidad de asientos máximos en Clase Ejecutiva");
        panel7.add(label105, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaAltaVReservaV = new JTextArea();
        fechaAltaVReservaV.setEditable(false);
        panel7.add(fechaAltaVReservaV, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label106 = new JLabel();
        label106.setText("Fecha de alta de vuelo");
        panel7.add(label106, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label107 = new JLabel();
        label107.setText("Cliente:");
        reservaVuelo.add(label107, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer105 = new Spacer();
        reservaVuelo.add(spacer105, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comboBoxClienteReservaV = new JComboBox();
        reservaVuelo.add(comboBoxClienteReservaV, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label108 = new JLabel();
        label108.setText("Tipo asiento:");
        reservaVuelo.add(label108, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxTipoAsientoReservaV = new JComboBox();
        reservaVuelo.add(comboBoxTipoAsientoReservaV, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label109 = new JLabel();
        label109.setText("Cantidad de pasajes:");
        reservaVuelo.add(label109, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cantPasajesReservaVtxt = new JTextField();
        reservaVuelo.add(cantPasajesReservaVtxt, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label110 = new JLabel();
        label110.setText("Cantidad equipaje extra:");
        reservaVuelo.add(label110, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        equipajeExtraReservaV = new JTextField();
        reservaVuelo.add(equipajeExtraReservaV, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label111 = new JLabel();
        label111.setText("Pasajeros:");
        reservaVuelo.add(label111, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label112 = new JLabel();
        label112.setText("");
        reservaVuelo.add(label112, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancelarReservaV = new JButton();
        buttonCancelarReservaV.setText("Cancelar");
        reservaVuelo.add(buttonCancelarReservaV, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAceptarReservaV = new JButton();
        buttonAceptarReservaV.setText("Aceptar");
        reservaVuelo.add(buttonAceptarReservaV, new GridConstraints(12, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer106 = new Spacer();
        reservaVuelo.add(spacer106, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer107 = new Spacer();
        reservaVuelo.add(spacer107, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label113 = new JLabel();
        label113.setText("Fecha de reserva:");
        reservaVuelo.add(label113, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reservaVuelo.add(fechaReservaVJC, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBoxPasajerosReservaV = new JComboBox();
        reservaVuelo.add(comboBoxPasajerosReservaV, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAgregarPasajeroReservaV = new JButton();
        buttonAgregarPasajeroReservaV.setText("Agregar");
        reservaVuelo.add(buttonAgregarPasajeroReservaV, new GridConstraints(11, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAceptarReservaVuelo = new JButton();
        buttonAceptarReservaVuelo.setText("Quitar Pasajero");
        reservaVuelo.add(buttonAceptarReservaVuelo, new GridConstraints(12, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonQuitarPasajeroReservaV = new JButton();
        buttonQuitarPasajeroReservaV.setText("Quitar");
        reservaVuelo.add(buttonQuitarPasajeroReservaV, new GridConstraints(12, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminRutasPanel = new JPanel();
        adminRutasPanel.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), -1, -1));
        parentPanel.add(adminRutasPanel, "Card20");
        final JLabel label114 = new JLabel();
        Font label114Font = this.$$$getFont$$$("Dialog", Font.BOLD, 16, label114.getFont());
        if (label114Font != null) label114.setFont(label114Font);
        label114.setText("Administración de Rutas de Vuelo");
        adminRutasPanel.add(label114, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label115 = new JLabel();
        label115.setText("Seleccionar Aerolínea:");
        adminRutasPanel.add(label115, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminRutasAerolineaCombo = new JComboBox();
        adminRutasPanel.add(adminRutasAerolineaCombo, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label116 = new JLabel();
        label116.setText("Rutas Ingresadas:");
        adminRutasPanel.add(label116, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminRutasRutaCombo = new JComboBox();
        adminRutasPanel.add(adminRutasRutaCombo, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        adminRutasPanel.add(panel8, new GridConstraints(3, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        adminRutasAceptarButton = new JButton();
        adminRutasAceptarButton.setText("Aceptar Ruta");
        panel8.add(adminRutasAceptarButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminRutasRechazarButton = new JButton();
        adminRutasRechazarButton.setText("Rechazar Ruta");
        panel8.add(adminRutasRechazarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminRutasRecargarButton = new JButton();
        adminRutasRecargarButton.setText("Recargar Rutas con Estados");
        panel8.add(adminRutasRecargarButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultarRutasMasVisitadas = new JPanel();
        consultarRutasMasVisitadas.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        parentPanel.add(consultarRutasMasVisitadas, "Card21");
        final Spacer spacer108 = new Spacer();
        consultarRutasMasVisitadas.add(spacer108, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer109 = new Spacer();
        consultarRutasMasVisitadas.add(spacer109, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer110 = new Spacer();
        consultarRutasMasVisitadas.add(spacer110, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer111 = new Spacer();
        consultarRutasMasVisitadas.add(spacer111, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        consultarrutasmasvisitadasjscrollpane = new JScrollPane();
        consultarRutasMasVisitadas.add(consultarrutasmasvisitadasjscrollpane, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        consultarRutasMasVisitadasTable = new JTable();
        consultarrutasmasvisitadasjscrollpane.setViewportView(consultarRutasMasVisitadasTable);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPrincipal;
    }

}