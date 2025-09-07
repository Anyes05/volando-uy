package presentacion;

import dato.entidades.RutaVuelo;
import presentacion.helpers.*;
import logica.DataTypes.*;
import logica.clase.Factory;
import logica.clase.ISistema;

import com.toedter.calendar.JCalendar;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;


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

    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextArea textArea2;
    private JButton guardarButton;
    private JPanel parentPanel;
    private JPanel buttonsPanel;
    private JButton botonInicio;
    private JComboBox comboBoxVuelos;
    private JComboBox comboBoxUsuario;
    private JPanel consultaRutaVuelo;
    private JToolBar JToolBarPrincipal;
    //private JList list1;
    private JComboBox comboBoxAeroRVConsulta;
    private JTextArea nombreRVConsulta;
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
    private JTextField ciudadDText;
    private JTextField ciudadOText;
    private JTextField costoEjText;
    private JTextField costoEqExText;
    private JComboBox comboBoxCat;
    private JPanel altaCiudad;
    private JTextField ciudadAltaText;
    private JButton buttonAltaCiudad;
    private JTextField aeropuertoAltaText;
    private JLabel DescCiudadText;
    private JLabel AeropuertoCiuText;
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
    private JButton reservaVueloSeleccionarRutaVueloCancelar;
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
    private JPanel reservaVueloListarAerolineasJPanelTable;
    private JTable reservaVueloTablePrincipal;
    private JPanel reservaVueloParentPanel;
    private JPanel reservaVueloSeleccionarAerolinea;
    private JTextField reservaVueloSeleccionarAerolineaInput;
    private JButton reservaVueloSeleccionarAerolineaAceptar;
    private JButton reservaVueloSeleccionarAerolineaCancelar;
    private JPanel reservaVueloSeleccionarRutaVuelo;
    private JButton reservaVueloSeleccionarRutaVueloAceptar;
    private JTextField reservaVueloSeleccionarRutaVueloInput;
    private JPanel reservaVueloSeleccionarVuelo;
    private JTextField reservaVueloSeleccionarVueloInput;
    private JButton reservaVueloSeleccionarVueloAceptar;
    private JButton reservaVueloSeleccionarVueloCancelar;
    private JPanel reservaVueloSeleccionarUsuario;
    private JPanel reservaVueloListarUsuariosJPanelTable;
    private JTable reservaVueloListarUsuariosTable;
    private JTextField reservaVueloSeleccionarUsuarioCliente;
    private JPanel reservaVueloSeleccionarUsuarioJPanel;
    private JComboBox<TipoAsiento> reservaVueloSeleccionarUsuarioTipoAsiento;
    private JButton reservaVueloSeleccionarUsuarioAceptar;
    private JButton reservaVueloSeleccionarUsuarioCancelar;
    private JSpinner reservaVueloSeleccionarUsuarioCantidadPasajes;
    private JSpinner reservaVueloSeleccionarUsuarioCantidadEquipajeExtra;
    private JCalendar reservaVueloSeleccionarUsuarioJCalendar;
    private JPanel reservaVueloPasajes;
    private JTextField apellidoTextField;
    private JLabel reservaVueloPasajesNombre;
    private JTextField reservaVueloPasajesApellido;
    private JSpinner reservaVueloPasajesCantidadEquipajeExtra;
    private JButton aceptarButton;
    private JButton cancelarButton;

    //CONSULTA PAQUETE DE RUTA DE VUELO
    private JPanel consultaPaqueteRutaVuelo;
    private JPanel consultaPaqueteRutaVueloJPanelTable;
    private JTable consultaPaqueteRutaVueloTablaPrincipal;
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



    //  private JButton precargarAeropuertosButton;

    private boolean cargandoAeroRV = false;//estos booleanos son para la carga de los comboBox, porque sino no me funcionaba
    private boolean cargandoRutasRV = false;
    private boolean cargandoVuelosRV = false;
    private boolean cargandoVuelos = false;

    private ISistema sistema; // Variable para almacenar la instancia de ISistema obtenida a través del Factory

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
        listCatAltaRuta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Inicializar ComboBox de TipoAsiento para reserva de vuelo
        reservaVueloSeleccionarUsuarioTipoAsiento = new JComboBox<>(TipoAsiento.values());
        reservaVueloSeleccionarUsuarioTipoAsiento.setSelectedIndex(0); // Seleccionar Turista por defecto

    }

    private void inicializarComboBoxTipoAsiento() {
        if (reservaVueloSeleccionarUsuarioTipoAsiento != null) {
            // Limpiar el ComboBox
            reservaVueloSeleccionarUsuarioTipoAsiento.removeAllItems();

            // Agregar los valores del enum
            for (TipoAsiento tipo : TipoAsiento.values()) {
                reservaVueloSeleccionarUsuarioTipoAsiento.addItem(tipo);
            }

            // Seleccionar Turista por defecto
            reservaVueloSeleccionarUsuarioTipoAsiento.setSelectedIndex(0);

            System.out.println("ComboBox TipoAsiento reinicializado con " + reservaVueloSeleccionarUsuarioTipoAsiento.getItemCount() + " elementos");

            // Forzar repaint del componente
            reservaVueloSeleccionarUsuarioTipoAsiento.repaint();
            reservaVueloSeleccionarUsuarioTipoAsiento.revalidate();

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(null,
                    "ComboBox TipoAsiento inicializado con " + reservaVueloSeleccionarUsuarioTipoAsiento.getItemCount() + " elementos:\n" +
                            "- " + TipoAsiento.Turista + "\n" +
                            "- " + TipoAsiento.Ejecutivo,
                    "Debug", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.err.println("ERROR: reservaVueloSeleccionarUsuarioTipoAsiento es null!");
            JOptionPane.showMessageDialog(null, "ERROR: ComboBox TipoAsiento es null!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarComboBoxTipoAsientoPaquete() {
        comboBoxTipoAsientoAgrRutaaPaquete.removeAllItems();
        for (TipoAsiento tipo : TipoAsiento.values()) {
            comboBoxTipoAsientoAgrRutaaPaquete.addItem(tipo);
        }
        comboBoxTipoAsientoAgrRutaaPaquete.setSelectedIndex(-1);
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
        for (dato.entidades.Categoria c : sistema.getCategorias()) {
            modelo.addElement(c.getNombre());
        }
        lista.setModel(modelo);
        lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void mostrarDatosRuta(String nicknameAerolinea, String nombreRuta) {
        DTRutaVuelo ruta = VueloHelper.getRutasDeAerolinea(nicknameAerolinea, nombreRuta);
        if (ruta != null) {
            nombreRVConsulta.setText(ruta.getNombre());
            descripcionRVConsulta.setText(ruta.getDescripcion());
            ciudadORVConsulta.setText(String.valueOf(ruta.getCiudadOrigen()));
            ciudadDRVConsulta.setText(String.valueOf(ruta.getCiudadDestino()));
            costoBaseTuRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoTurista()));
            costoBaseEjRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoEjecutivo()));
            costoUnEquipajeExRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoEquipajeExtra()));
            fechaAltaRVConsulta.setText(ruta.getFechaAlta().toString());
        } else {
            VueloHelper.limpiarCampos(
                    nombreRVConsulta,
                    descripcionRVConsulta,
                    ciudadORVConsulta,
                    ciudadDRVConsulta,
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

    //---PAQUETES------
    private void cargarPaquetes(JComboBox<DTPaqueteVuelos> combo){
        combo.removeAllItems();
        for (DTPaqueteVuelos p : sistema.mostrarPaquete()){
            combo.addItem(p);
        }
        combo.setSelectedIndex(-1);
    }
    //----CLIENTES------
    private void cargarClientes(JComboBox<DTCliente> combo){
        combo.removeAllItems();
        for (DTCliente c : sistema.mostrarClientes()){
            combo.addItem(c);
        }
        combo.setSelectedIndex(-1);
    }


//    private void cargarAerolineas(JComboBox<String> combo) {
//        combo.removeAllItems(); // limpiar combo por si ya tiene algo
//        for (DTAerolinea a : sistema.listarAerolineas()) {
//            combo.addItem(a.getNickname());
//        }
//        combo.setSelectedIndex(-1);
//    }
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
        horaText.setText("");
        costoTurText.setText("");
        costoEjText.setText("");
        costoEqExText.setText("");
        ciudadOText.setText("");
        ciudadDText.setText("");
        fechaAltaRutaVuelo.setCalendar(Calendar.getInstance());
        aerolineaVuelo.setSelectedItem(null);
    }

    public EstacionTrabajo() {
        // Obtener la instancia de ISistema a través del Factory
        Factory factory = new Factory();
        this.sistema = factory.getSistema();


        // Inicializar ComboBox de TipoAsiento como respaldo
        if (reservaVueloSeleccionarUsuarioTipoAsiento == null) {
            reservaVueloSeleccionarUsuarioTipoAsiento = new JComboBox<>(TipoAsiento.values());
            reservaVueloSeleccionarUsuarioTipoAsiento.setSelectedIndex(0);
            System.out.println("ComboBox TipoAsiento inicializado en constructor");
        }

        // Agregar listener al panel de pasajes para inicializar ComboBox cuando se muestre
        reservaVueloPasajes.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                inicializarComboBoxTipoAsiento();
            }
        });

        // También agregar listener al panel de selección de usuario
        reservaVueloSeleccionarUsuarioJPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                inicializarComboBoxTipoAsiento();
            }
        });

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
                        parentPanel.removeAll();
                        cargarAerolineas(aerolineaVuelo);
                        cargarCategorias(listCatAltaRuta);
                        parentPanel.add(altaRuta);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar ruta de vuelo":
                        parentPanel.removeAll();
                        cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = true;
                        nombreRVConsulta.setText("");
                        descripcionRVConsulta.setText("");
                        ciudadORVConsulta.setText("");
                        ciudadDRVConsulta.setText("");
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
                    case "Crear Vuelo":
                        parentPanel.removeAll();
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
                        parentPanel.removeAll();
                        parentPanel.add(altaCiudad);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Categoría":
                        parentPanel.removeAll();
                        parentPanel.add(altaCategoría);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;

                    case "Reservar vuelo":
                        // Inicializar ComboBox de TipoAsiento cuando se entra a reservar vuelo
                        inicializarComboBoxTipoAsiento();
                        UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarAerolinea);
                        UsuarioHelper.cambiarPanel(parentPanel,reservaVuelo);
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
                        parentPanel.add(crearPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Agregar ruta a paquete":
                        parentPanel.removeAll();
                        cargarPaquetes(comboBoxPaqueteAgrRutaaPaquete);
                        cargarAerolineas(comboBoxAeroAgrRutaaPaquete);
                        cargarRutas(comboBoxRutaVueloAgrRutaaPaquete, (String) comboBoxAeroAgrRutaaPaquete.getSelectedItem());
                        inicializarComboBoxTipoAsientoPaquete();
                        parentPanel.add(agregarRutaaPaquete);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consulta de paquete":
                        UsuarioHelper.cambiarPanel(consultaPaqueteRutaVueloParentPanel,consultaPaqueteRutaVueloSeleccionarPaquete);
                        UsuarioHelper.cambiarPanel(parentPanel,consultaPaqueteRutaVuelo);
                        break;
                    case "Comprar paquete":
                        parentPanel.removeAll();
                        cargarPaquetes(comboBoxPaquetesComprarPaquete);
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
                        parentPanel.removeAll();
                        parentPanel.add(altaUsuario);
                        parentPanel.repaint();
                        parentPanel.revalidate();
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
                            numeroDocAltaCliente
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
                String aeropuerto = aeropuertoAltaText.getText().trim();
                String descripcion = descripcionAltaCiText.getText().trim();
                String sitioWeb = sitioWebAltaCiText.getText().trim();
                DTFecha fecha = new DTFecha(
                        calendarCiudadAlta.getCalendar().get(Calendar.DAY_OF_MONTH),
                        calendarCiudadAlta.getCalendar().get(Calendar.MONTH) + 1,
                        calendarCiudadAlta.getCalendar().get(Calendar.YEAR)
                );

                VueloHelper.crearCiudad(nombre, pais, aeropuerto, descripcion, sitioWeb, fecha);

                // Limpiar campos
                ciudadAltaText.setText("");
                paisAltaCiText.setText("");
                aeropuertoAltaText.setText("");
                sitioWebAltaCiText.setText("");
                descripcionAltaCiText.setText("");
                calendarCiudadAlta.setCalendar(Calendar.getInstance());
            }
        });

        /*----- CONSULTA USUARIO -----*/
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(paqueteVueloRadioButton);
        grupo.add(reservaDeVueloRadioButton);
        grupo.add(rutaDeVueloRadioButton);

// Si querés empezar con ninguno seleccionado
        grupo.clearSelection();

        consultaUsuarioAceptar.addActionListener(e -> {
            String consulta = consultaUsuarioText.getText().trim();
            UsuarioHelper.mostrarDatosUsuario(consultaUsuarioTable1, consulta);
        });

        ActionListener listener = e -> {
            cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = true;
            nombreRVConsulta.setText("");
            descripcionRVConsulta.setText("");
            ciudadORVConsulta.setText("");
            ciudadDRVConsulta.setText("");
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
            if (nicknameAerolinea != null) {
                cargarRutas(comBoxRutVueloConsultaRV, nicknameAerolinea);
            }
            if (paqueteVueloRadioButton.isSelected()) {
                UsuarioHelper.cambiarPanel(consultaUsuarioParentPanel, consultaPaqueteRutaVuelo);
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
                    String horaStr = horaText.getText().trim();
                    String costoTuristaStr = costoTurText.getText().trim();
                    String costoEjecutivoStr = costoEjText.getText().trim();
                    String costoEquipajeStr = costoEqExText.getText().trim();
                    String origen = ciudadOText.getText().trim();
                    String destino = ciudadDText.getText().trim();
                    Calendar fechaCal = fechaAltaRutaVuelo.getCalendar();
                    String nicknameAerolinea = aerolineaVuelo.getSelectedItem().toString();

                    // Seleccionar aerolínea
                    VueloHelper.seleccionarAerolinea(nicknameAerolinea);

                    // Ingresar ruta de vuelo (acepto List<String> en categoria, pero no funciona)
                    VueloHelper.ingresarRutaVuelo(
                            nombreRuta,
                            descripcionRuta,
                            horaStr,
                            costoTuristaStr,
                            costoEjecutivoStr,
                            costoEquipajeStr,
                            origen,
                            destino,
                            fechaCal,
                            categoriasSeleccionadas
                    );

                    JOptionPane.showMessageDialog(altaRuta, "Ruta de vuelo registrada con éxito.");
                    nombreAltRutaText.setText("");
                    descRutaText.setText("");
                    horaText.setText("");
                    costoTurText.setText("");
                    costoEjText.setText("");
                    costoEqExText.setText("");
                    ciudadOText.setText("");
                    ciudadDText.setText("");
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
//                    DTFecha fechaAlta = new DTFecha(
//                            fechaAltaCal.get(Calendar.DAY_OF_MONTH),
//                            fechaAltaCal.get(Calendar.MONTH) + 1,
//                            fechaAltaCal.get(Calendar.YEAR)
//                    );
//                    DTFecha fechaVue = new DTFecha(
//                            fechaVueloCal.get(Calendar.DAY_OF_MONTH),
//                            fechaVueloCal.get(Calendar.MONTH) + 1,
//                            fechaVueloCal.get(Calendar.YEAR)
//                    );


                    DTRutaVuelo ruta = (DTRutaVuelo) rutasVueloAltaVuelo.getSelectedItem();

                    if (ruta == null) {
                        JOptionPane.showMessageDialog(altaVuelo, "Debe seleccionar una ruta primero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String hora = horaVuelotxt.getText().trim();
                    VueloHelper.ingresarVuelo(nombre, duracion, hora, fechaAltaCal, fechaVueloCal, maxTurista, maxEjecutivo, ruta);
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
                    // Llenar combo de vuelos asociados a esta ruta
                    List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(nombreRuta.getNombre());
                    cargandoVuelosRV = true;
                    vuelosConsultaRV.removeAllItems(); // limpiar lista previa
                    for (DTVuelo v : vuelos) {
                        vuelosConsultaRV.addItem(v); // cargar vuelos
                    }
                    cargandoVuelosRV = false;
                    vuelosConsultaRV.setSelectedIndex(-1);
                }else{
                    VueloHelper.limpiarCampos(
                            nombreRVConsulta,
                            descripcionRVConsulta,
                            ciudadORVConsulta,
                            ciudadDRVConsulta,
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

                    JPanel panelDetalles = new JPanel();
                    panelDetalles.setLayout(new GridLayout(5, 1));
                    panelDetalles.add(new JLabel("Nombre: " + vueloSeleccionado.getNombre()));
                    panelDetalles.add(new JLabel("Fecha: " + vueloSeleccionado.getFechaVuelo().toString()));
                    panelDetalles.add(new JLabel("Hora: " + vueloSeleccionado.getHoraVuelo().toString()));
                    panelDetalles.add(new JLabel("Duración: " + vueloSeleccionado.getDuracion().toString()));
                    panelDetalles.add(new JLabel("Asientos Turista: " + vueloSeleccionado.getAsientosMaxTurista()));
                    panelDetalles.add(new JLabel("Asientos Ejecutivo: " + vueloSeleccionado.getAsientosMaxEjecutivo()));
                    panelDetalles.add(new JLabel("Fecha de Alta: " + vueloSeleccionado.getFechaAlta().toString()));
                    panelDetalles.add(new JLabel("Ruta Asociada: " + vueloSeleccionado.getRuta().getNombre()));
                    JOptionPane.showMessageDialog(
                            null,
                            panelDetalles,
                            "Detalles del Vuelo",
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
                VueloHelper.limpiarCampos(nombVueloConsultaVtxt,fechaVueloConsultaVtxt,horaVueloConsultaVtxt,duracionVueloConsultaVtxt,maxTuristaConsultaVtxt,
                        maxEjecutivoConsultaVtxt,fechaAltaVueloConsultaVtxt);
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
                VueloHelper.limpiarCampos(nombVueloConsultaVtxt,fechaVueloConsultaVtxt,horaVueloConsultaVtxt,duracionVueloConsultaVtxt,maxTuristaConsultaVtxt,
                maxEjecutivoConsultaVtxt,fechaAltaVueloConsultaVtxt);
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
                } else {
                    VueloHelper.limpiarCampos(nombVueloConsultaVtxt,fechaVueloConsultaVtxt,horaVueloConsultaVtxt,duracionVueloConsultaVtxt,maxTuristaConsultaVtxt,
                            maxEjecutivoConsultaVtxt,fechaAltaVueloConsultaVtxt);
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

                    PaqueteHelper.ingresarPaquete(nomPaq, descripcion, periodoVal, descuento, fechaCal);
                    int diasValidosInt = Integer.parseInt(periodoVal);
                    float descuentoFloat = Float.parseFloat(descuento);

                    DTFecha fechaAlta = new DTFecha(
                            fechaCal.get(Calendar.DAY_OF_MONTH),
                            fechaCal.get(Calendar.MONTH) + 1,
                            fechaCal.get(Calendar.YEAR)
                    );
                    sistema.crearPaquete(nomPaq, descripcion,null, diasValidosInt, descuentoFloat, fechaAlta);
                    JOptionPane.showMessageDialog(crearPaquete, "Paquete creado correctamente.");

                    nombreAltaPaqtxt.setText("");
                    descripcionAltaPaqtxt.setText("");
                    períodoAltaPaqtxt.setText("");
                    descripcionAltaPaqtxt.setText("");
                    descuentoAltaPaqtxt.setText("");
                    calendarAltaPaquete.setCalendar(Calendar.getInstance());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(crearPaquete, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonCancelarCrearPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombreAltaPaqtxt.setText("");
                descripcionAltaPaqtxt.setText("");
                períodoAltaPaqtxt.setText("");
                descripcionAltaPaqtxt.setText("");
                descuentoAltaPaqtxt.setText("");
                calendarAltaPaquete.setCalendar(Calendar.getInstance());
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

        // RESERVA VUELO
        reservaVueloSeleccionarAerolineaAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cargar aerolíneas en la tabla
                ReservaHelper.cargarAerolineasEnTabla(reservaVueloTablePrincipal);
                UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarRutaVuelo);
            }
        });

        reservaVueloSeleccionarRutaVueloAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener aerolínea seleccionada y cargar sus rutas
                String aerolineaSeleccionada = reservaVueloSeleccionarAerolineaInput.getText().trim();
                if (aerolineaSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una aerolínea.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    List<DTRutaVuelo> rutas = ReservaHelper.obtenerRutasDeAerolinea(aerolineaSeleccionada);
                    DefaultComboBoxModel<DTRutaVuelo> model = new DefaultComboBoxModel<>();
                    for (DTRutaVuelo ruta : rutas) {
                        model.addElement(ruta);
                    }
                    comBoxRutVueloConsultaRV.setModel(model);
                    UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarVuelo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar rutas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        reservaVueloSeleccionarVueloAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener ruta seleccionada y cargar sus vuelos
                DTRutaVuelo rutaSeleccionada = (DTRutaVuelo) comBoxRutVueloConsultaRV.getSelectedItem();
                if (rutaSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    List<DTVuelo> vuelos = ReservaHelper.obtenerVuelosDeRuta(rutaSeleccionada.getNombre());
                    DefaultComboBoxModel<DTVuelo> model = new DefaultComboBoxModel<>();
                    for (DTVuelo vuelo : vuelos) {
                        model.addElement(vuelo);
                    }
                    vuelosConsultaRV.setModel(model);
                    UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarUsuarioJPanel);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar vuelos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        reservaVueloSeleccionarUsuarioAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener vuelo seleccionado
                DTVuelo vueloSeleccionado = (DTVuelo) vuelosConsultaRV.getSelectedItem();
                if (vueloSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Seleccionar el vuelo para la reserva
                ReservaHelper.seleccionarVuelo(vueloSeleccionado.getNombre());

                // Cargar clientes en la tabla
                ReservaHelper.cargarClientesEnTabla(reservaVueloListarUsuariosTable);

                // Agregar listener para selección de clientes
                ReservaHelper.configurarSeleccionCliente(reservaVueloListarUsuariosTable, reservaVueloSeleccionarUsuarioCliente);

                // Asegurar que el ComboBox de TipoAsiento esté inicializado
                inicializarComboBoxTipoAsiento();

                UsuarioHelper.cambiarPanel(reservaVueloParentPanel, reservaVueloPasajes);
            }
        });

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener datos del formulario
                    String clienteSeleccionado = reservaVueloSeleccionarUsuarioCliente.getText().trim();
                    if (clienteSeleccionado.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Obtener tipo de asiento
                    TipoAsiento tipoAsiento = (TipoAsiento) reservaVueloSeleccionarUsuarioTipoAsiento.getSelectedItem();
                    if (tipoAsiento == null) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de asiento.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Obtener cantidad de pasajes y equipaje extra
                    int cantidadPasajes = (Integer) reservaVueloSeleccionarUsuarioCantidadPasajes.getValue();
                    int equipajeExtra = (Integer) reservaVueloSeleccionarUsuarioCantidadEquipajeExtra.getValue();

                    // Validar datos adicionales si hay múltiples pasajes
                    if (cantidadPasajes > 1) {
                        // Verificar si hay datos para pasajeros adicionales
                        String nombreAdicional = apellidoTextField.getText().trim();
                        String apellidoAdicional = reservaVueloPasajesApellido.getText().trim();

                        if (nombreAdicional.isEmpty() || apellidoAdicional.isEmpty()) {
                            // Mostrar mensaje informativo
                            JOptionPane.showMessageDialog(null,
                                    "Se crearán " + cantidadPasajes + " pasajes para el cliente: " + clienteSeleccionado +
                                            "\n\nNota: Todos los pasajes se asignan al mismo cliente. " +
                                            "Los campos de nombre y apellido se usarán para identificar a los pasajeros adicionales.",
                                    "Información de Reserva",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Confirmar datos de pasajeros adicionales
                            int confirmacion = JOptionPane.showConfirmDialog(null,
                                    "Se crearán " + cantidadPasajes + " pasajes:\n" +
                                            "1. " + clienteSeleccionado + " (cliente principal)\n" +
                                            "2-" + cantidadPasajes + ". " + nombreAdicional + " " + apellidoAdicional +
                                            " (asociado al cliente principal)\n\n" +
                                            "¿Desea continuar con la reserva?",
                                    "Confirmar Reserva",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (confirmacion != JOptionPane.YES_OPTION) {
                                return; // Cancelar la operación
                            }
                        }
                    }

                    // Crear lista de pasajeros usando el helper
                    List<String> nombresPasajeros = ReservaHelper.obtenerPasajerosParaReserva(
                            clienteSeleccionado,
                            cantidadPasajes,
                            apellidoTextField, // Campo de nombre
                            reservaVueloPasajesApellido // Campo de apellido
                    );

                    // Realizar la reserva
                    ReservaHelper.realizarReserva(
                            tipoAsiento,
                            cantidadPasajes,
                            equipajeExtra,
                            nombresPasajeros,
                            reservaVueloSeleccionarUsuarioJCalendar,
                            apellidoTextField.getText().trim(),
                            reservaVueloPasajesApellido.getText().trim()
                    );

                    // Limpiar formulario y volver al inicio
                    ReservaHelper.limpiarFormularioReserva(
                            reservaVueloSeleccionarAerolineaInput,
                            reservaVueloSeleccionarRutaVueloInput,
                            reservaVueloSeleccionarVueloInput,
                            reservaVueloSeleccionarUsuarioCliente,
                            reservaVueloSeleccionarUsuarioTipoAsiento,
                            reservaVueloSeleccionarUsuarioCantidadPasajes,
                            reservaVueloSeleccionarUsuarioCantidadEquipajeExtra,
                            reservaVueloSeleccionarUsuarioJCalendar
                    );

                    // Limpiar también los campos específicos de pasaje
                    ReservaHelper.limpiarFormularioPasaje(
                            apellidoTextField, // Este campo parece ser para nombre
                            reservaVueloPasajesApellido,
                            reservaVueloPasajesCantidadEquipajeExtra
                    );

                    UsuarioHelper.cambiarPanel(parentPanel, principalVacio);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // CONSULTA PAQUETE DE RUTA DE VUELO
        consultaPaqueteRutaVueloSeleccionarPaqueteAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(consultaPaqueteRutaVueloParentPanel,consultaRutaVuelo);
            }
        });

            //------------COMPRAR PAQUETE --------------

        aceptarComprarPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTPaqueteVuelos paquete = (DTPaqueteVuelos) comboBoxPaquetesComprarPaquete.getSelectedItem();
                DTCliente cliente = (DTCliente) comboBoxClientesComprarPaquete.getSelectedItem();

                if (paquete == null || cliente == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un paquete y un cliente");
                    return;
                }

                try {
                    // seteás los seleccionados en el sistema
                    sistema.seleccionarPaquete(paquete.getNombre());
                    sistema.seleccionarCliente(cliente.getNickname());

                    // ahora armás los demás datos de la compra

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
                    TipoAsiento tipoAsiento = paquete.getTipoAsiento();

                    // ejecutar la compra
                    sistema.realizarCompra(fechaCompra, costo, vencimiento, tipoAsiento);

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
                DTPaqueteVuelos paqueteSeleccionado = (DTPaqueteVuelos) comboBoxPaqueteAgrRutaaPaquete.getSelectedItem();
                String nicknameAerolinea = (String) comboBoxAeroAgrRutaaPaquete.getSelectedItem();
                DTAerolinea aerolineaSeleccionada = null;
                for (DTAerolinea a : sistema.listarAerolineas()) {
                    if (a.getNickname().equals(nicknameAerolinea)) {
                        aerolineaSeleccionada = a;
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

                if (paqueteSeleccionado == null || aerolineaSeleccionada == null || tipoAsientoSeleccionado == null) {
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
                    sistema.seleccionarAerolinea(aerolineaSeleccionada.getNickname());
                    sistema.seleccionarRutaVueloPaquete(nombreRuta);
                    RutaVuelo rutaSeleccionada = sistema.getRutaVueloSeleccionada();
                    // despues uso el resto de variables recolectadas para pasar como parametro en agregarRutaAPaquete
                    sistema.agregarRutaAPaquete(rutaSeleccionada, cantidad, tipoAsientoSeleccionado);
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(buttonAceptarAgrRutaaPaquete),
                            "Ruta agregada al paquete con éxito.");

                    // limpiar tras exito
                    comboBoxPaqueteAgrRutaaPaquete.setSelectedIndex(-1);
                    comboBoxAeroAgrRutaaPaquete.setSelectedIndex(-1);
                    comboBoxRutaVueloAgrRutaaPaquete.setSelectedIndex(-1);
                    comboBoxTipoAsientoAgrRutaaPaquete.setSelectedIndex(0); // Si el primer item es vacío
                    cantidadAgrRutaaPaquetetxt.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar ruta al paquete: " + ex.getMessage());
                }
            }
        });
    }
}







