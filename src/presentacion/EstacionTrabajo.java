package presentacion;
import logica.clase.Categoria;
import logica.clase.Sistema;
import presentacion.helpers.*;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;


import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
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
    private JTextField textField5;
    private JButton aceptarButton2;
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
    private JList list2;
    private JTextArea nomVueloRVConsulta;
    private JTextArea fechaVueloRVConsulta;
    private JTextArea horaVueloRVConsulta;
    private JTextArea duracionVueloRVConsulta;
    private JTextArea cantAsientosMaxTuristaRVConsulta;
    private JTextArea cantAsientoMaxEjecutivoRVConsulta;
    private JTextArea fechaAltaVueloRVConsulta;
    private JButton aceptarButton1;
    private JRadioButton rutaDeVueloRadioButton;
    private JRadioButton reservaDeVueloRadioButton;
    private JTextField textField4;
    private JRadioButton paqueteVueloRadioButton;
    private JRadioButton usuarioRadioButton;
    private JPanel principalVacio;
    private JPanel altaRuta;
    private JCalendar fechaVuelo;
    private JCalendar fechaAltaVuelo;
    private JPanel consultaVuelo;
    private JComboBox comboBox4;
    private JComboBox aerolineaVuelo;
    private JComboBox<DTRutaVuelo> rutasVueloAltaVuelo;
    private JTextArea descRutaText;
    private JTextField correoAltaCliente;
    private JCalendar JCalendarAltaCliente;
    private JTextField numeroDocAltaCliente;
    private JTable consultaUsuarioTable1;
    private JTable consultaUsuarioTable2;
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
    private JButton button2;
    private JList<String> listCatAltaRuta;
    private JComboBox<DTRutaVuelo> comBoxRutVueloConsultaRV;
    private JComboBox vuelosConsultaRV;
    private JTextPane descripcionRVConsulta;


    public EstacionTrabajo() {

/// ///////// TABLAS ////////////
        // Columnas de la tabla
        String[] columnas = {"Nickname", "Nombre", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Usar el atributo declarado, no crear una nueva tabla
        consultaUsuarioTable1.setModel(modelo);
        consultaUsuarioTable1.setAutoCreateRowSorter(true);

        // Agregar la tabla a un JScrollPane para que se vean los encabezados
        JScrollPane scroll = new JScrollPane(consultaUsuarioTable1);
        consultaUsuario.setLayout(new BorderLayout());
        consultaUsuario.add(scroll, BorderLayout.CENTER);



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

        // Combo boxes
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
                        cargarAerolineas(comboBoxAeroRVConsulta);
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
                }
            }
        });

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
        /*consultaRutaVuelo.addComponentListener(new ComponentAdapter() {
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
        });*/

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

                    // Ingresar ruta de vuelo (modifica el método para aceptar List<String>)
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(altaRuta,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonAltaCiudad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = ciudadAltaText.getText().trim();
                String pais = paisAltaCiText.getText().trim();
                DTFecha fecha = new DTFecha(
                        calendarCiudadAlta.getCalendar().get(Calendar.DAY_OF_MONTH),
                        calendarCiudadAlta.getCalendar().get(Calendar.MONTH) + 1,
                        calendarCiudadAlta.getCalendar().get(Calendar.YEAR)
                );

                // Si querés agregar un aeropuerto vacío por ahora, pasás null
                VueloHelper.crearCiudad(nombre, pais, null, fecha);

                // Limpiar campos
                ciudadAltaText.setText("");
                paisAltaCiText.setText("");
                aeropuertoAltaText.setText("");
                sitioWebAltaCiText.setText("");
                descripcionAltaCiText.setText("");
                calendarCiudadAlta.setCalendar(Calendar.getInstance());
            }
        });
        buttonAltaCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomCategoria = categoriaAltaText.getText().trim();
                try {
                    VueloHelper.crearCategoria(nomCategoria); // delega a Sistema
                    JOptionPane.showMessageDialog(parentPanel, "Categoría creada con éxito.");

                    // Limpiar campo
                    categoriaAltaText.setText("");

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(parentPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonAltaVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = nombreAltaVuelotxt.getText().trim();
                    String duracion = duracionAltaVuelotxt.getText().trim();
                    Calendar fechaCal = fechaAltaVuelo.getCalendar();
                    DTFecha fecha = new DTFecha(
                            fechaCal.get(Calendar.DAY_OF_MONTH),
                            fechaCal.get(Calendar.MONTH) + 1,
                            fechaCal.get(Calendar.YEAR)
                    );
                    int maxTurista = Integer.parseInt(asientosMaxTuristatxt.getText().trim());
                    int maxEjecutivo = Integer.parseInt(asientoMaxEjecutivotxt.getText().trim());
                    DTRutaVuelo ruta = (DTRutaVuelo) rutasVueloAltaVuelo.getSelectedItem(); // la ruta que seleccionaste en el combo

                    if (ruta == null) {
                        JOptionPane.showMessageDialog(altaVuelo, "Debe seleccionar una ruta primero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String hora = horaText.getText().trim();
                    VueloHelper.ingresarVuelo(nombre, duracion, hora, fechaCal, maxTurista, maxEjecutivo, ruta);
                    Sistema.getInstance().darAltaVuelo();
                    JOptionPane.showMessageDialog(altaVuelo, "Vuelo registrado correctamente.");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(altaVuelo, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        comboBoxAeroRVConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) comboBoxAeroRVConsulta.getSelectedItem();
                if (nickname != null) {
                    cargarRutas(comBoxRutVueloConsultaRV,nickname);
                }
            }
        });

        comBoxRutVueloConsultaRV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTRutaVuelo nombreRuta = (DTRutaVuelo) comBoxRutVueloConsultaRV.getSelectedItem();
                String nicknameAerolinea = (String) comboBoxAeroRVConsulta.getSelectedItem();
                if (nombreRuta != null && nicknameAerolinea != null) {
                    mostrarDatosRuta(nicknameAerolinea, nombreRuta.getNombre());
                }
            }
        });


        comboBoxAeroRVConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) comboBoxAeroRVConsulta.getSelectedItem();
                if (nickname != null) {
                    cargarRutas(comBoxRutVueloConsultaRV,nickname);
                }
            }
        });


        aerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = (String) aerolinea.getSelectedItem();
                if (nickname != null) {
                    cargarRutas(rutasVueloAltaVuelo, nickname);
                }
            }
        });
    }

    private void mostrarDatosRuta(String nicknameAerolinea,String nombreRuta) {
        DTRutaVuelo ruta = VueloHelper.getRutasDeAerolinea(nicknameAerolinea, nombreRuta);
        if (ruta != null) {
            nombreRVConsulta.setText(ruta.getNombre());
            descripcionRVConsulta.setText(ruta.getDescripcion());
            costoBaseRVConsulta.setText(String.valueOf(ruta.getCostoBase()));
            fechaAltaRVConsulta.setText(ruta.getFechaAlta().toString());
        } else {
            JOptionPane.showMessageDialog(parentPanel, "No se encontró la ruta.");
        }
    }



    private void cargarRutas(JComboBox<DTRutaVuelo>comboRutas, String nicknameAerolinea) {
        comboRutas.removeAllItems(); // Limpiar combo
        List<DTRutaVuelo> rutas = Sistema.getInstance().listarRutaVuelo(nicknameAerolinea);
        for (DTRutaVuelo ruta : rutas) {
            comboRutas.addItem(ruta);
        }
    }


    private void cargarAerolineas(JComboBox<String> combo) {
        combo.removeAllItems(); // limpiar combo por si ya tiene algo
        for (DTAerolinea a : Sistema.getInstance().listarAerolineas()) {
            combo.addItem(a.getNickname());
        }
    }
    private void cargarCategorias(JList<String> lista) {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (Categoria c : Sistema.getInstance().getCategorias()) {
            modelo.addElement(c.getNombre());
        }
        lista.setModel(modelo);
        lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Permitir varias selecciones
    }






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
        calendarCiudadAlta = new JCalendar();
        listCatAltaRuta = new JList<>();
        listCatAltaRuta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }






}
