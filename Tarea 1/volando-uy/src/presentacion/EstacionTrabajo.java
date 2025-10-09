package presentacion;

import dato.entidades.Categoria;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.LocalDate;
import java.util.List;


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
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new EstacionTrabajo().mainPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
        combo.removeAllItems();
        for (String aero : sistema.listarAeropuertos()) {
            combo.addItem(aero);
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarCiudadesOrigen(JComboBox<String> combo) {
        combo.removeAllItems(); // limpiar combo
        for (DTCiudad c : sistema.listarCiudades()) {
            combo.addItem(c.getNombre() + ", " + c.getPais());
        }
        combo.setSelectedIndex(-1);
    }

    private void cargarCiudadesDestino(JComboBox<String> combo, String ciudadOrigen) {
        combo.removeAllItems(); // limpiar combo
        List<DTCiudad> ciudades = sistema.listarCiudades();
        List<DTCiudad> ciudadesDestino = sistema.listarCiudadesDestino(ciudades, ciudadOrigen);
        for (DTCiudad c : ciudadesDestino) {
            combo.addItem(c.getNombre() + ", " + c.getPais());
        }
        combo.setSelectedIndex(-1);
    }


    private void cargarAerolineas(JComboBox<String> combo) {
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
        comboPaquete.removeAllItems();
        for (DTPaqueteVuelos p : sistema.mostrarPaquete()) {
            comboPaquete.addItem(p);

        }
        cargandoPaquete = true;
        comboPaquete.setSelectedIndex(-1);
    }

    private void cargarPaquetesNoComprados(JComboBox<DTPaqueteVuelos> comboPaquete) {
        comboPaquete.removeAllItems();
        for (DTPaqueteVuelos p : sistema.obtenerPaquetesNoComprados()) {
            comboPaquete.addItem(p);
        }
        cargandoPaquete = true;
        comboPaquete.setSelectedIndex(-1);
    }

    //para el caso de uso de comprar paquete
    private void cargarPaquetesConRutas(JComboBox<DTPaqueteVuelos> combo) {
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
        nombreAltaVuelotxt.setText("");
        duracionAltaVuelotxt.setText("");
        asientosMaxTuristatxt.setText("");
        asientoMaxEjecutivotxt.setText("");
        horaVuelotxt.setText("");
        fechaAltaVuelo.setCalendar(Calendar.getInstance());
        fechaVuelo.setCalendar(Calendar.getInstance());
        rutasVueloAltaVuelo.setSelectedItem(null);
        aerolinea.setSelectedItem(null);
    }

    //----------Boton cancelar Alta ruta vuelo------
    private void limpiarCamposAltaRutaVuelo() {
        nombreAltRutaText.setText("");
        descRutaText.setText("");
//        horaText.setText("");
        costoTurText.setText("");
        costoEjText.setText("");
        costoEqExText.setText("");
        comboBoxCiudadOrigenARV.setSelectedIndex(-1);
        comboBoxCiudadDestinoARV.setSelectedIndex(-1);
        fechaAltaRutaVuelo.setCalendar(Calendar.getInstance());
        aerolineaVuelo.setSelectedItem(null);
    }

    // Limpiar alta ciudad al cambiar de panel
    private void limpiarCamposAltaCiudad() {
        ciudadAltaText.setText("");
        descripcionAltaCiText.setText("");
        comboBoxAeropuertosAC.setSelectedIndex(-1);
        sitioWebAltaCiText.setText("");
        paisAltaCiText.setText("");
        calendarCiudadAlta.setCalendar(Calendar.getInstance());
    }

    private void limpiarCamposCrearPaquete() {
        nombreAltaPaqtxt.setText("");
        descripcionAltaPaqtxt.setText("");
        períodoAltaPaqtxt.setText("");
        descripcionAltaPaqtxt.setText("");
        descuentoAltaPaqtxt.setText("");
        calendarAltaPaquete.setCalendar(Calendar.getInstance());
    }

    public EstacionTrabajo() {
        // Obtener la instancia de ISistema a través del Factory
        Factory factory = new Factory();
        this.sistema = factory.getSistema();

        /*----- CONSULTA USUARIO -----*/
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(paqueteVueloRadioButton);
        grupo.add(reservaDeVueloRadioButton);
        grupo.add(rutaDeVueloRadioButton);

        // Inicializar ComboBox de TipoAsiento como respaldo
        if (reservaVueloSeleccionarUsuarioTipoAsiento == null) {
            reservaVueloSeleccionarUsuarioTipoAsiento = new JComboBox<>(TipoAsiento.values());
            reservaVueloSeleccionarUsuarioTipoAsiento.setSelectedIndex(0);
            System.out.println("ComboBox TipoAsiento inicializado en constructor");
        }

        /*----- TABLAS -----*/
        // Columnas de la tabla
        JScrollPane scroll = new JScrollPane(consultaUsuarioTable1);
        consultaUsuarioJpanel1.setLayout(new BorderLayout());
        consultaUsuarioJpanel1.add(scroll, BorderLayout.CENTER);

        JScrollPane scroll3 = new JScrollPane(modificarUsuariotable1);
        modificarUsuarioJPanel1.setLayout(new BorderLayout());
        modificarUsuarioJPanel1.add(scroll3, BorderLayout.CENTER);


        /*----- PANEL DE BOTONES -----*/
        //Boton de inicio
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

        /*----- MENU DE VUELOS Y RUTAS -----*/
        comboBoxVuelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboBoxVuelos.getSelectedItem();
                // Según lo que se elija, haces algo
                switch (seleccionado) {
                    case "Crear ruta de vuelo":
                        limpiarCamposAltaRutaVuelo();
                        parentPanel.removeAll();

                        cargarCiudadesOrigen(comboBoxCiudadOrigenARV);
                        cargarAerolineas(aerolineaVuelo);
                        cargarCategorias(listCatAltaRuta);
                        parentPanel.add(altaRuta);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar ruta de vuelo":
                        parentPanel.removeAll();
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
                        String nicknameAerolinea = (String) comboBoxAeroRVConsulta.getSelectedItem();
                        if (nicknameAerolinea != null) {
                            cargarRutas(comBoxRutVueloConsultaRV, nicknameAerolinea);
                        }
                        parentPanel.add(consultaRutaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Aceptar/Rechazar Ruta de Vuelo":
                        parentPanel.removeAll();
                        adminRutasAerolineaCombo.removeAllItems();
                        adminRutasRutaCombo.removeAllItems();
                        
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
                        parentPanel.removeAll();
                        limpiarCamposAltaVuelo();
                        cargarAerolineas(aerolinea);
                        parentPanel.add(altaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar Vuelo":
                        parentPanel.removeAll();
                        cargarAerolineas(comboBoxAeroConsultaV);
                        parentPanel.add(consultaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Ciudad":
                        limpiarCamposAltaCiudad();
                        parentPanel.removeAll();
                        cargarAeropuertos(comboBoxAeropuertosAC);
                        parentPanel.add(altaCiudad);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Categoría":
                        parentPanel.removeAll();
                        categoriaAltaText.setText("");
                        parentPanel.add(altaCategoría);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;

                    case "Reservar vuelo":
                        // Inicializar ComboBox de TipoAsiento cuando se entra a reservar vuelo
//                        inicializarComboBoxTipoAsiento();
                        parentPanel.removeAll();
                        cantPasajesReservaVtxt.setText("");
                        equipajeExtraReservaV.setText("");
                        cargarAerolineas(comboBoxAeroReservaV);
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

        /*----- MENU DE PAQUETE -----*/
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
                        parentPanel.removeAll();
                        cantidadAgrRutaaPaquetetxt.setText("");
                        cargarPaquetesNoComprados(comboBoxPaqueteAgrRutaaPaquete);
                        cargarAerolineas(comboBoxAeroAgrRutaaPaquete);
                        cargarRutas(comboBoxRutaVueloAgrRutaaPaquete, (String) comboBoxAeroAgrRutaaPaquete.getSelectedItem());
                        inicializarComboBoxTipoAsientoPaquete();
                        parentPanel.add(agregarRutaaPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consulta de paquete":
                        parentPanel.removeAll();
                        cargarPaquetes(comboBoxPaqueteConsultaPaquete);
                        descripcionCPtxt.setText("");
                        diasvalidosCPtxt.setText("");
                        descuentoCPtxt.setText("");
                        costoCPtxt.setText("");
                        fechaAltaCPtxt.setText("");
                        parentPanel.add(ConsultaPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Comprar paquete":
                        parentPanel.removeAll();
                        cargarPaquetesConRutas(comboBoxPaquetesComprarPaquete);
                        cargarClientes(comboBoxClientesComprarPaquete);
                        parentPanel.add(ComprarPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;

                }
            }
        });

        /*----- MENU DE USUARIO -----*/
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
                        UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                        UsuarioHelper.limpiarCampos(modificarUsuarioTextInput);
                        parentPanel.add(modificarUsuario);
                        UsuarioHelper.cambiarPanel(parentPanel, modificarUsuario);
                        modificarUsuarioTextInput.requestFocus();
                        break;
                    case "Consultar usuario":
                        parentPanel.removeAll();
                        UsuarioHelper.actualizarTablaUsuarios(consultaUsuarioTable1);
                        UsuarioHelper.limpiarCampos(consultaUsuarioText);
                        grupo.clearSelection();
                        UsuarioHelper.cambiarPanel(consultaUsuarioParentPanel, principalVacio);
                        parentPanel.add(consultaUsuarioScroll);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                }
            }
        });
        /*----- PRECARGA COMPLETA DEL SISTEMA -----*/
        // Agregar botón de precarga completa al toolbar principal
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
        /*----- ALTA CLIENTE -----*/
        //Boton de entrada
        ButtonCrearNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(altaCliente);
                parentPanel.repaint();
                parentPanel.revalidate();
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
                            JCalendarAltaCliente
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
                            JCalendarAltaCliente.getDate()
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
                            altaAerolineaDescripcion.getText().trim()
                    );

                    JOptionPane.showMessageDialog(altaAerolinea, "Aerolínea creada con éxito.");

                    // Resetear formulario
                    UsuarioHelper.resetFormularioAerolinea(
                            altaAerolineaNickname, altaAerolineaNombre, altaAerolineaCorreo, altaAerolineaLinkWeb, altaAerolineaDescripcion
                    );


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
                            modificarClienteJCalendar
                    );
                } else if (tipo.equals("Aerolinea")) {
                    UsuarioHelper.cargarDatosAerolineaEnCampos(
                            nickname,
                            modificarAerolineaTextNombre,
                            modificarAerolineaTextArea,
                            modificarAerolineaTextLink
                    );
                }

                UsuarioHelper.abrirPanelModificacionUsuario(parentPanel, modificarCliente, modificarAerolinea, tipo, nickname);
            }
        });

        modificarUsuarioCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.limpiarCampos(modificarUsuarioTextInput);
                UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                UsuarioHelper.cambiarPanel(parentPanel, principalVacio);
            }
        });

        modificarClienteGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (UsuarioHelper.modificarClienteValidar(
                            modificarClienteNombre,
                            modificarClienteApellido,
                            modificarClienteNacionalidad,
                            (TipoDoc) modificarClienteComboBox.getSelectedItem(),
                            modificarClienteDocumento
                    )) {
                        UsuarioHelper.guardarCambiosCliente(
                                modificarUsuarioTextInput.getText().trim(),
                                modificarClienteNombre.getText().trim(),
                                modificarClienteApellido.getText().trim(),
                                UsuarioHelper.convertirDTfecha(modificarClienteJCalendar),
                                modificarClienteNacionalidad.getText().trim(),
                                (TipoDoc) modificarClienteComboBox.getSelectedItem(),
                                modificarClienteDocumento.getText().trim()
                        );
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
                    if (UsuarioHelper.modificarAerolineaValidar(
                            modificarAerolineaTextNombre,
                            modificarAerolineaTextLink,
                            modificarAerolineaTextArea
                    )) {
                        UsuarioHelper.guardarCambiosAerolinea(
                                modificarUsuarioTextInput.getText().trim(),
                                modificarAerolineaTextNombre.getText().trim(),
                                modificarAerolineaTextArea.getText().trim(),
                                modificarAerolineaTextLink.getText().trim()
                        );
                        UsuarioHelper.limpiarCampos(
                                modificarAerolineaTextNombre,
                                modificarAerolineaTextLink,
                                modificarUsuarioTextInput
                        );
                        modificarAerolineaTextArea.setText("");
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
                UsuarioHelper.limpiarCampos(
                        modificarAerolineaTextNombre,
                        modificarAerolineaTextLink,
                        modificarUsuarioTextInput
                );
                modificarAerolineaTextArea.setText("");
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
                            foto
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
    }
}