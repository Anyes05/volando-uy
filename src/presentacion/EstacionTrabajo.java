package presentacion;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import presentacion.helpers.*;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;

<<<<<<< Updated upstream
=======
import javax.swing.JList;
import javax.swing.ListSelectionModel;


>>>>>>> Stashed changes
import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< Updated upstream
import java.awt.event.ComponentAdapter;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
=======
import javax.swing.JTable;
>>>>>>> Stashed changes


public class EstacionTrabajo {
    private JPanel mainPrincipal;
    private JPanel altaVuelo;
    private JComboBox aerolinea;
    private JPanel fromAltaVuelo;
    private JTextField nombre;
    private JComboBox categoria;
    private JTextField ciudadOrigen;
    private JTextField ciudadDestino;
    private JCalendar fechaAltaRutaVuelo;
    private JButton Enviar;
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
    private JTextField textField5;
    private JButton aceptarButton2;
    private JPanel parentPanel;
    private JPanel buttonsPanel;
    private JButton botonInicio;
    private JComboBox comboBoxVuelos;
    private JComboBox comboBoxUsuario;
    private JPanel consultaRutaVuelo;
    private JToolBar JToolBarPrincipal;
    private JList list1;
    private JComboBox comboBox2;
    private JTextPane textPane2;
    private JTextPane textPane3;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JList list2;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;
    private JTextArea textArea9;
    private JTextArea textArea10;
    private JTextArea textArea11;
    private JButton consultaUsuarioAceptar;
    private JRadioButton rutaDeVueloRadioButton;
    private JRadioButton reservaDeVueloRadioButton;
    private JTextField consultaUsuarioText;
    private JRadioButton paqueteVueloRadioButton;
    private JRadioButton usuarioRadioButton;
    private JPanel principalVacio;
    private JPanel altaRuta;
    private JCalendar fechaVuelo;
    private JCalendar fechaAltaVuelo;
    private JPanel consultaVuelo;
    private JComboBox comboBox4;
    private JComboBox aerolineaVuelo;
    private JComboBox comboBox5;
    private JTextArea textArea1;
    private JTextField correoAltaCliente;
    private JCalendar JCalendarAltaCliente;
    private JTextField numeroDocAltaCliente;
    private JButton altaAerolineaCancelar;
    private JButton altaAerolineaAceptar;
    private JTextField altaAerolineaNickname;
    private JTextField altaAerolineaNombre;
    private JTextField altaAerolineaCorreo;
    private JTextField altaAerolineaLinkWeb;
    private JPanel consultaUsuarioJpanel2;
    private JPanel consultaUsuarioJpanel1;
    private JTable consultaUsuarioTable1;
    private JTable consultaUsuarioTable2;
    private JPanel modificarUsuarioJPanel1;
    private JTable modificarUsuariotable1;
    private JPanel modificarAerolinea;
    private JPanel modificarCliente;
    private JTextField modificarAerolineaTextNombre;
    private JTextField modificarAerolineaTextLink;
    private JTextArea modificarAerolineaTextArea;
    private JButton modificarAerolineaGuardar;
    private JButton cancelarButton;
    private JButton cancelarButton1;
    private JTextField modificarClienteNombre;
    private JTextField modificarClienteApellido;
    private JTextField modificarClienteNacionalidad;
    private JComboBox modificarClienteComboBox;
    private JTextField modificarClienteDocumento;
    private JButton modificarClienteGuardar;
<<<<<<< Updated upstream
    private JButton button2;
=======
    private JTextField modificarClienteCorreo;
    private JCalendar modificarClienteJCalendar;
    private JTextField modificarAerolineaCorreo;


    private boolean cargandoAeroRV = false;//estos booleanos son para la carga de los comboBox, porque sino no me funcionaba
    private boolean cargandoRutasRV = false;
    private boolean cargandoVuelosRV = false;
>>>>>>> Stashed changes

    public EstacionTrabajo() {

/// ///////// TABLAS ////////////

        JScrollPane scroll = new JScrollPane(consultaUsuarioTable1);
        consultaUsuarioJpanel1.setLayout(new BorderLayout());
        consultaUsuarioJpanel1.add(scroll, BorderLayout.CENTER);

        JScrollPane scroll2 = new JScrollPane(consultaUsuarioTable2);
        consultaUsuarioJpanel2.setLayout(new BorderLayout());
        consultaUsuarioJpanel2.add(scroll2, BorderLayout.CENTER);

        JScrollPane scroll3 = new JScrollPane(modificarUsuariotable1);
        modificarUsuarioJPanel1.setLayout(new BorderLayout());
        modificarUsuarioJPanel1.add(scroll3, BorderLayout.CENTER);






//////////////////// PANEL DE BOTONES//////////////////////////////////////
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

<<<<<<< Updated upstream
        // Combo boxes
        comboBoxVuelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboBoxVuelos.getSelectedItem();
                // Según lo que se elija, haces algo
                switch (seleccionado) {
                    case "Crear ruta de vuelo":
                        parentPanel.removeAll();
                        parentPanel.add(altaRuta);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar ruta de vuelo":
                        parentPanel.removeAll();
                        parentPanel.add(consultaRutaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Crear Vuelo":
                        parentPanel.removeAll();
                        parentPanel.add(altaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                    case "Consultar Vuelo":
                        parentPanel.removeAll();
                        parentPanel.add(consultaVuelo);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                }
            }
        });

=======
        /*----- MENU DE VUELOS Y RUTAS -----*/
//        comboBoxVuelos.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String seleccionado = (String) comboBoxVuelos.getSelectedItem();
//                // Según lo que se elija, haces algo
//                switch (seleccionado) {
//                    case "Crear ruta de vuelo":
//                        parentPanel.removeAll();
//                        cargarAerolineas(aerolineaVuelo);
//                        cargarCategorias(listCatAltaRuta);
//                        parentPanel.add(altaRuta);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
//                    case "Consultar ruta de vuelo":
//                        parentPanel.removeAll();
//                        cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = true;
//                        nombreRVConsulta.setText("");
//                        descripcionRVConsulta.setText("");
//                        ciudadORVConsulta.setText("");
//                        ciudadDRVConsulta.setText("");
//                        costoBaseTuRVConsulta.setText("");
//                        costoBaseEjRVConsulta.setText("");
//                        costoUnEquipajeExRVConsulta.setText("");;
//                        fechaAltaRVConsulta.setText("");
//                        comboBoxAeroRVConsulta.removeAllItems();
//                        comBoxRutVueloConsultaRV.removeAllItems();
//                        vuelosConsultaRV.removeAllItems();
//                        cargandoAeroRV = cargandoRutasRV = cargandoVuelosRV = false;
//                        cargarAerolineas(comboBoxAeroRVConsulta);
//                        parentPanel.add(consultaRutaVuelo);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
//                    case "Crear Vuelo":
//                        parentPanel.removeAll();
//                        cargarAerolineas(aerolinea);
//                        parentPanel.add(altaVuelo);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
//                    case "Consultar Vuelo":
//                        parentPanel.removeAll();
//                        parentPanel.add(consultaVuelo);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
//                    case "Crear Ciudad":
//                        parentPanel.removeAll();
//                        parentPanel.add(altaCiudad);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
//                    case "Crear Categoría":
//                        parentPanel.removeAll();
//                        parentPanel.add(altaCategoría);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
//                }
//            }
//        });

//        /////////////// CONSULTA USUARIO /////////////////
        ButtonGroup grupoConsultaUsuario = new ButtonGroup();
        grupoConsultaUsuario.add(paqueteVueloRadioButton);
        grupoConsultaUsuario.add(usuarioRadioButton);
        grupoConsultaUsuario.add(reservaDeVueloRadioButton);
        grupoConsultaUsuario.add(rutaDeVueloRadioButton);


        // 2. Listener para el botón Aceptar
        consultaUsuarioAceptar.addActionListener(e -> {
            String consulta = consultaUsuarioText.getText().trim();
            if (paqueteVueloRadioButton.isSelected()) {
                // OPCION 1 PAQUETE
                if (consulta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un nombre de paquete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                   // UsuarioHelper.mostrarDatosPaquete(consultaUsuarioTable2, consulta);
                }

            } else if (usuarioRadioButton.isSelected()) {
                // OPCION 2 USUARIO
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

        ///////////////MODIFICAR USUARIO/////////////////

        aceptarButton2.addActionListener(new ActionListener() {
            String consulta = consultaUsuarioText.getText().trim();
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = UsuarioHelper.obtenerTipoUsuario(consulta);
                if (UsuarioHelper.obtenerTipoUsuario(consulta)=="Cliente"){
                    UsuarioHelper.cargarDatosClienteEnCampos(consulta,modificarClienteNombre,modificarClienteApellido,modificarClienteNacionalidad,modificarClienteComboBox,modificarClienteDocumento,modificarClienteCorreo,modificarClienteJCalendar);
                } else {
                    UsuarioHelper.cargarDatosAerolineaEnCampos(consulta,modificarAerolineaTextNombre,modificarAerolineaTextArea,modificarAerolineaTextLink,modificarAerolineaCorreo);
                }
                UsuarioHelper.abrirPanelModificacionUsuario(parentPanel,modificarCliente,modificarAerolinea,usuario);

            }
        });

        modificarClienteGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.guardarCambiosCliente(
                        modificarClienteNombre.getText().trim(),
                        modificarClienteApellido.getText().trim(),
                        UsuarioHelper.convertirDTfecha(modificarClienteJCalendar.getDate()),
                        modificarClienteNacionalidad.getText().trim(),
                        (TipoDoc) modificarClienteComboBox.getSelectedItem(),
                        modificarClienteDocumento.getText().trim()
                );
            }
        });

        modificarAerolineaGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioHelper.guardarCambiosAerolinea(
                        modificarAerolineaTextNombre.getText().trim(),
                        modificarAerolineaTextArea.getText().trim(),
                        modificarAerolineaTextLink.getText().trim()
                );
            }
        });



        /*----- MENU DE USUARIO -----*/
>>>>>>> Stashed changes
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
                        parentPanel.removeAll();
                        UsuarioHelper.actualizarTablaUsuarios(modificarUsuariotable1);
                        parentPanel.add(modificarUsuario);
                        parentPanel.repaint();
                        parentPanel.revalidate();
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

///////////// ALTA AEROLINEA //////////////////
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


/// ///////// ni idea /////////
        consultaRutaVuelo.addComponentListener(new ComponentAdapter() {
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object seleccion = list1.getSelectedValue();
                    // Aquí puedes mostrar detalles, habilitar botones, etc.
                    System.out.println("Ruta seleccionada: " + seleccion);
                }
            }
        });
        fechaAltaRutaVuelo.addComponentListener(new ComponentAdapter() {
        });

////////////////////ALTA CLIENTE//////////////////////////////////
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


<<<<<<< Updated upstream
/////////////// CONSULTA USUARIO /////////////////
        ButtonGroup grupoConsultaUsuario = new ButtonGroup();
        grupoConsultaUsuario.add(paqueteVueloRadioButton);
        grupoConsultaUsuario.add(usuarioRadioButton);
        grupoConsultaUsuario.add(reservaDeVueloRadioButton);
        grupoConsultaUsuario.add(rutaDeVueloRadioButton);
=======
//        vuelosConsultaRV.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (cargandoVuelosRV) return;
//                Object seleccionado = vuelosConsultaRV.getSelectedItem();
//                if (seleccionado instanceof DTVuelo) {
//                    DTVuelo vueloSeleccionado = (DTVuelo) seleccionado;
//
//                    JPanel panelDetalles = new JPanel();
//                    panelDetalles.setLayout(new GridLayout(5, 1));
//                    panelDetalles.add(new JLabel("Nombre: " + vueloSeleccionado.getNombre()));
//                    panelDetalles.add(new JLabel("Fecha: " + vueloSeleccionado.getFechaVuelo().toString()));
//                    panelDetalles.add(new JLabel("Hora: " + vueloSeleccionado.getHoraVuelo().toString()));
//                    panelDetalles.add(new JLabel("Duración: " + vueloSeleccionado.getDuracion().toString()));
//                    panelDetalles.add(new JLabel("Asientos Turista: " + vueloSeleccionado.getAsientosMaxTurista()));
//                    panelDetalles.add(new JLabel("Asientos Ejecutivo: " + vueloSeleccionado.getAsientosMaxEjecutivo()));
//                    panelDetalles.add(new JLabel("Fecha de Alta: " + vueloSeleccionado.getFechaAlta().toString()));
//                    panelDetalles.add(new JLabel("Ruta Asociada: " + vueloSeleccionado.getRuta().getNombre()));
//                    JOptionPane.showMessageDialog(
//                            null,
//                            panelDetalles,
//                            "Detalles del Vuelo",
//                            JOptionPane.PLAIN_MESSAGE
//                    );
//                }
//            }
//        });


    }
>>>>>>> Stashed changes


        // 2. Listener para el botón Aceptar
        consultaUsuarioAceptar.addActionListener(e -> {
            String consulta = consultaUsuarioText.getText().trim();
            if (paqueteVueloRadioButton.isSelected()) {
                // lógica para opción 1
                UsuarioHelper.mostrarPaquetes(consultaUsuarioTable2);
            } else if (usuarioRadioButton.isSelected()) {
                if (consulta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un nickname", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    UsuarioHelper.mostrarDatosUsuario(consultaUsuarioTable2, consulta);
                }

            } else if (reservaDeVueloRadioButton.isSelected()) {
                // lógica para opción 3
                UsuarioHelper.mostrarReservasPorVuelo(consultaUsuarioTable2,consulta);
            } else if (rutaDeVueloRadioButton.isSelected()) {
                // lógica para opción 4
                UsuarioHelper.mostrarRutaVuelo(consultaUsuarioTable2, consulta);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ///////////////MODIFICAR USUARIO/////////////////

        aceptarButton2.addActionListener(new ActionListener() {
            String consulta = consultaUsuarioText.getText().trim();
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = UsuarioHelper.obtenerTipoUsuario(consulta);

                if (tipo == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuario no encontrado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (tipo.equals("cliente")) {
                    parentPanel.removeAll();
                    parentPanel.add(modificarCliente);
                    parentPanel.repaint();
                    parentPanel.revalidate();

                } else if (tipo.equals("aerolinea")) {
                    parentPanel.removeAll();
                    parentPanel.add(modificarAerolinea);
                    parentPanel.repaint();
                    parentPanel.revalidate();

                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "El usuario no es cliente ni aerolínea",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            }
        });
    };



    public static void main(String[] args) {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new EstacionTrabajo().mainPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // IntelliJ lo usará porque el .form tiene fechaAltaVuelo en Custom Create
        fechaVuelo = new JCalendar();
        fechaAltaRutaVuelo = new JCalendar();
        fechaAltaVuelo = new JCalendar();
        JCalendarAltaCliente = new JCalendar();
<<<<<<< Updated upstream
=======
        calendarCiudadAlta = new JCalendar();
        modificarClienteJCalendar = new JCalendar();
        listCatAltaRuta = new JList<>();
        listCatAltaRuta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
>>>>>>> Stashed changes
    }






}
