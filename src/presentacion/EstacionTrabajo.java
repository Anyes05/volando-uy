package presentacion;

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
import java.util.Calendar;
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
    private JPanel consultaUsuario;
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
    private JTextPane nombreRVConsulta;
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
    private JTextPane descripcionRVConsulta;
    private JTextArea costoBaseEjRVConsulta;
    private JTextArea ciudadORVConsulta;
    private JTextArea ciudadDRVConsulta;
    private JTextArea costoBaseTuRVConsulta;
    private JLabel costoEquipajeExRVConsulta;
    private JTextArea costoUnEquipajeExRVConsulta;
    private JTextField horaVuelotxt;
    private JPanel consultaUsuarioJpanel2;
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
    private JComboBox comboBoxAeroAgrRutaaPaquete;
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
    private JComboBox reservaVueloSeleccionarUsuarioTipoAsiento;
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
    }


    private void cargarAerolineas(JComboBox<String> combo) {
        combo.removeAllItems(); // limpiar combo por si ya tiene algo
        for (DTAerolinea a : sistema.listarAerolineas()) {
            combo.addItem(a.getNickname());
        }
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
            JOptionPane.showMessageDialog(parentPanel, "No se encontró la ruta.");
        }
    }

    private void cargarRutas(JComboBox<DTRutaVuelo> comboRutas, String nicknameAerolinea) {
        boolean esConsulta = (comboRutas == comBoxRutVueloConsultaRV);
        if (esConsulta) cargandoRutasRV = true;
        comboRutas.removeAllItems(); // Limpiar combo
        List<DTRutaVuelo> rutas = sistema.seleccionarAerolineaRet(nicknameAerolinea);
        for (DTRutaVuelo ruta : rutas) {
            comboRutas.addItem(ruta);
        }
        if (esConsulta) {
            cargandoRutasRV = false;
            comboRutas.setSelectedIndex(-1); // evita disparo inicial
        }
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

        /*----- TABLAS -----*/
        // Columnas de la tabla

        JScrollPane scroll = new JScrollPane(consultaUsuarioTable1);
        consultaUsuarioJpanel1.setLayout(new BorderLayout());
        consultaUsuarioJpanel1.add(scroll, BorderLayout.CENTER);

        JScrollPane scroll2 = new JScrollPane(consultaUsuarioTable2);
        consultaUsuarioJpanel2.setLayout(new BorderLayout());
        consultaUsuarioJpanel2.add(scroll2, BorderLayout.CENTER);

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
                        ;
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
                        parentPanel.add(consultaUsuario);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
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
        ButtonGroup grupoConsultaUsuario = new ButtonGroup();
        grupoConsultaUsuario.add(paqueteVueloRadioButton);
        grupoConsultaUsuario.add(usuarioRadioButton);
        grupoConsultaUsuario.add(reservaDeVueloRadioButton);
        grupoConsultaUsuario.add(rutaDeVueloRadioButton);

        consultaUsuarioAceptar.addActionListener(e -> {
            String consulta = consultaUsuarioText.getText().trim();
            if (paqueteVueloRadioButton.isSelected()) {
                // lógica para opción 1
                //  UsuarioHelper.mostrarPaquetes(consultaUsuarioTable2);
            } else if (usuarioRadioButton.isSelected()) {
                if (consulta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un nickname", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    UsuarioHelper.mostrarDatosUsuario(consultaUsuarioTable2, consulta);
                }

            } else if (reservaDeVueloRadioButton.isSelected()) {
                // lógica para opción 3
                // UsuarioHelper.mostrarReservasPorVuelo(consultaUsuarioTable2,consulta);
            } else if (rutaDeVueloRadioButton.isSelected()) {
                // lógica para opción 4
                // UsuarioHelper.mostrarRutaVuelo(consultaUsuarioTable2, consulta);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opción.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    int maxTurista = Integer.parseInt(asientosMaxTuristatxt.getText().trim());
                    int maxEjecutivo = Integer.parseInt(asientoMaxEjecutivotxt.getText().trim());
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
                if (nickname != null) {
                    cargarRutas(comboBoxRutasVuelosConsultaV, nickname);
                }
            }
        });
        comboBoxRutasVuelosConsultaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTRutaVuelo rutaSeleccionada = (DTRutaVuelo) comboBoxRutasVuelosConsultaV.getSelectedItem();
                if (rutaSeleccionada != null) {
                    cargarVuelos(comboBoxVuelosConsultaV, rutaSeleccionada.getNombre());
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
                    sistema.crearPaquete(nomPaq, descripcion, diasValidosInt, descuentoFloat, fechaAlta);
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
                UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarRutaVuelo);
            }
        });

        reservaVueloSeleccionarRutaVueloAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarVuelo);
            }
        });


        reservaVueloSeleccionarVueloAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(reservaVueloParentPanel,reservaVueloSeleccionarUsuarioJPanel);
            }
        });


        reservaVueloSeleccionarUsuarioAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(reservaVueloParentPanel, reservaVueloPasajes);
            }
        });
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(parentPanel,principalVacio);
            }
        });

        // CONSULTA PAQUETE DE RUTA DE VUELO
        consultaPaqueteRutaVueloSeleccionarPaqueteAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.cambiarPanel(consultaPaqueteRutaVueloParentPanel,consultaRutaVuelo);
            }
        });

    }
}

