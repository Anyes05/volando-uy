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
    private JTextArea costoBaseEjRVConsulta;
    private JTextArea ciudadORVConsulta;
    private JTextArea ciudadDRVConsulta;
    private JTextArea costoBaseTuRVConsulta;
    private JLabel costoEquipajeExRVConsulta;
    private JTextArea costoUnEquipajeExRVConsulta;
    private JTextField horaVuelotxt;


    private boolean cargandoAeroRV = false;//estos booleanos son para la carga de los comboBox, porque sino no me funcionaba
    private boolean cargandoRutasRV = false;
    private boolean cargandoVuelosRV = false;

    public EstacionTrabajo() {

        /*----- TABLAS -----*/
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
//                    case "Modificar usuario":
//                        parentPanel.removeAll();
//                        parentPanel.add(modificarUsuario);
//                        parentPanel.repaint();
//                        parentPanel.revalidate();
//                        break;
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

        /*----- ALTA RUTA VUELO -----*/
//        aceptarRuta.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    List<String> categoriasSeleccionadas = listCatAltaRuta.getSelectedValuesList();
//                    if (categoriasSeleccionadas.isEmpty()) {
//                        JOptionPane.showMessageDialog(altaRuta,
//                                  "Seleccione al menos una categoría",
//                                "Error",
//                                JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//
//                    // Tomar los datos del formulario
//                    String nombreRuta = nombreAltRutaText.getText().trim();
//                    String descripcionRuta = descRutaText.getText().trim();
//                    String horaStr = horaText.getText().trim();
//                    String costoTuristaStr = costoTurText.getText().trim();
//                    String costoEjecutivoStr = costoEjText.getText().trim();
//                    String costoEquipajeStr = costoEqExText.getText().trim();
//                    String origen = ciudadOText.getText().trim();
//                    String destino = ciudadDText.getText().trim();
//                    Calendar fechaCal = fechaAltaRutaVuelo.getCalendar();
//                    String nicknameAerolinea = aerolineaVuelo.getSelectedItem().toString();
//
//                    // Seleccionar aerolínea
//                    VueloHelper.seleccionarAerolinea(nicknameAerolinea);
//
//                    // Ingresar ruta de vuelo (acepto List<String> en categoria, pero no funciona)
//                    VueloHelper.ingresarRutaVuelo(
//                            nombreRuta,
//                            descripcionRuta,
//                            horaStr,
//                            costoTuristaStr,
//                            costoEjecutivoStr,
//                            costoEquipajeStr,
//                            origen,
//                            destino,
//                            fechaCal,
//                            categoriasSeleccionadas
//                    );
//
//                    JOptionPane.showMessageDialog(altaRuta, "Ruta de vuelo registrada con éxito.");
//                    nombreAltRutaText.setText("");
//                    descRutaText.setText("");
//                    horaText.setText("");
//                    costoTurText.setText("");
//                    costoEjText.setText("");
//                    costoEqExText.setText("");
//                    ciudadOText.setText("");
//                    ciudadDText.setText("");
//                    fechaAltaRutaVuelo.setCalendar(Calendar.getInstance());
//
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(altaRuta,
//                            "Error: " + ex.getMessage(),
//                            "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//

        /*----- ALTA CIUDAD -----*/
//        buttonAltaCiudad.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String nombre = ciudadAltaText.getText().trim();
//                String pais = paisAltaCiText.getText().trim();
//                DTFecha fecha = new DTFecha(
//                        calendarCiudadAlta.getCalendar().get(Calendar.DAY_OF_MONTH),
//                        calendarCiudadAlta.getCalendar().get(Calendar.MONTH) + 1,
//                        calendarCiudadAlta.getCalendar().get(Calendar.YEAR)
//                );

//                // agrego un aeropuerto vacío por ahora, no quiero romper nada
//                VueloHelper.crearCiudad(nombre, pais, null, fecha);
//
//                // Limpiar campos
//                ciudadAltaText.setText("");
//                paisAltaCiText.setText("");
//                aeropuertoAltaText.setText("");
//                sitioWebAltaCiText.setText("");
//                descripcionAltaCiText.setText("");
//                calendarCiudadAlta.setCalendar(Calendar.getInstance());
//            }
//        });

        /*----- ALTA CATEGORIA -----*/
//        buttonAltaCategoria.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String nomCategoria = categoriaAltaText.getText().trim();
//                try {
//                    VueloHelper.crearCategoria(nomCategoria);
//                    JOptionPane.showMessageDialog(parentPanel, "Categoría creada con éxito.");
//
//                    // Limpiar campo
//                    categoriaAltaText.setText("");
//
//                } catch (IllegalArgumentException ex) {
//                    JOptionPane.showMessageDialog(parentPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//
        /*----- ALTA VUELO -----*/
//        buttonAltaVuelo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    String nombre = nombreAltaVuelotxt.getText().trim();
//                    String duracion = duracionAltaVuelotxt.getText().trim();
//                    Calendar fechaCal = fechaAltaVuelo.getCalendar();
//                    DTFecha fecha = new DTFecha(
//                            fechaCal.get(Calendar.DAY_OF_MONTH),
//                            fechaCal.get(Calendar.MONTH) + 1,
//                            fechaCal.get(Calendar.YEAR)
//                    );
//                    int maxTurista = Integer.parseInt(asientosMaxTuristatxt.getText().trim());
//                    int maxEjecutivo = Integer.parseInt(asientoMaxEjecutivotxt.getText().trim());
//                    DTRutaVuelo ruta = (DTRutaVuelo) rutasVueloAltaVuelo.getSelectedItem();
//
//                    if (ruta == null) {
//                        JOptionPane.showMessageDialog(altaVuelo, "Debe seleccionar una ruta primero.", "Error", JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//
//                    String hora = horaVuelotxt.getText().trim();
//                    VueloHelper.ingresarVuelo(nombre, duracion, hora, fechaCal, maxTurista, maxEjecutivo, ruta);
//                    String nicknameAerolinea = (String) aerolinea.getSelectedItem();
//                    Sistema.getInstance().seleccionarAerolinea(nicknameAerolinea);
//                    Sistema.getInstance().darAltaVuelo();
//                    JOptionPane.showMessageDialog(altaVuelo, "Vuelo registrado correctamente.");
//
                         //limpio campos
//                    nombreAltaVuelotxt.setText("");
//                    duracionAltaVuelotxt.setText("");
//                    fechaAltaVuelo.setCalendar(Calendar.getInstance());
//                    asientosMaxTuristatxt.setText("");
//                    asientoMaxEjecutivotxt.setText("");
//                    horaVuelotxt.setText("");
//                    rutasVueloAltaVuelo.setSelectedItem(null);
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(altaVuelo, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//

        /*----- CONSULTA RUTA VUELO -----*/
//        comBoxRutVueloConsultaRV.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (cargandoRutasRV) return; // los booleanos son porque se llama a otras cargas de comboBox y no andaba
//                DTRutaVuelo nombreRuta = (DTRutaVuelo) comBoxRutVueloConsultaRV.getSelectedItem();
//                String nicknameAerolinea = (String) comboBoxAeroRVConsulta.getSelectedItem();
//                if (nombreRuta != null && nicknameAerolinea != null) {
//                    mostrarDatosRuta(nicknameAerolinea, nombreRuta.getNombre());
//                    // Llenar combo de vuelos asociados a esta ruta
//                    List<DTVuelo> vuelos = Sistema.getInstance().seleccionarRutaVuelo(nombreRuta.getNombre());
//                    cargandoVuelosRV = true;
//                    vuelosConsultaRV.removeAllItems(); // limpiar lista previa
//                    for (DTVuelo v : vuelos) {
//                        vuelosConsultaRV.addItem(v); // cargar vuelos
//                    }
//                    cargandoVuelosRV = false;
//                    vuelosConsultaRV.setSelectedIndex(-1);
//                }
//            }
//        });
//
//
//        comboBoxAeroRVConsulta.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (cargandoAeroRV) return;
//                String nickname = (String) comboBoxAeroRVConsulta.getSelectedItem();
//                if (nickname != null) {
//                    cargarRutas(comBoxRutVueloConsultaRV,nickname);
//                    cargandoVuelosRV = true;
//                    vuelosConsultaRV.removeAllItems();
//                    cargandoVuelosRV = false;
//                }
//            }
//        });
//        });

        //es un comboBox, le tengo que cambiar el nombre
//        aerolinea.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String nickname = (String) aerolinea.getSelectedItem();
//                if (nickname != null) {
//                    cargarRutas(rutasVueloAltaVuelo, nickname);
//                }
//            }
//        });


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



//    private void mostrarDatosRuta(String nicknameAerolinea,String nombreRuta) {
//        DTRutaVuelo ruta = VueloHelper.getRutasDeAerolinea(nicknameAerolinea, nombreRuta);
//        if (ruta != null) {
//            nombreRVConsulta.setText(ruta.getNombre());
//            descripcionRVConsulta.setText(ruta.getDescripcion());
//            ciudadORVConsulta.setText(String.valueOf(ruta.getCiudadOrigen()));
//            ciudadDRVConsulta.setText(String.valueOf(ruta.getCiudadDestino()));
//            costoBaseTuRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoTurista()));
//            costoBaseEjRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoEjecutivo()));
//            costoUnEquipajeExRVConsulta.setText(String.valueOf(ruta.getCostoBase().getCostoEquipajeExtra()));
//            fechaAltaRVConsulta.setText(ruta.getFechaAlta().toString());
//        } else {
//            JOptionPane.showMessageDialog(parentPanel, "No se encontró la ruta.");
//        }
//    }
//
//
//
//    private void cargarRutas(JComboBox<DTRutaVuelo>comboRutas, String nicknameAerolinea) {
//        boolean esConsulta = (comboRutas == comBoxRutVueloConsultaRV);
//        if (esConsulta) cargandoRutasRV = true;
//        comboRutas.removeAllItems(); // Limpiar combo
//        List<DTRutaVuelo> rutas = Sistema.getInstance().listarRutaVuelo(nicknameAerolinea);
//        for (DTRutaVuelo ruta : rutas) {
//            comboRutas.addItem(ruta);
//        }
//        if (esConsulta) {
//            cargandoRutasRV = false;
//            comboRutas.setSelectedIndex(-1); // evita disparo inicial
//        }
//    }
//
//
//    private void cargarAerolineas(JComboBox<String> combo) {
//        boolean esConsulta = (combo == comboBoxAeroRVConsulta);
//        if (esConsulta) cargandoAeroRV = true;
//        combo.removeAllItems(); // limpiar combo por si ya tiene algo
//        for (DTAerolinea a : Sistema.getInstance().listarAerolineas()) {
//            combo.addItem(a.getNickname());
//        }
//        if (esConsulta) {
//            cargandoAeroRV = false;
//            combo.setSelectedIndex(-1); // evita seleccion al iniciar
//        }
//    }
//    private void cargarCategorias(JList<String> lista) {
//        DefaultListModel<String> modelo = new DefaultListModel<>();
//        for (Categoria c : Sistema.getInstance().getCategorias()) {
//            modelo.addElement(c.getNombre());
//        }
//        lista.setModel(modelo);
//        lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Permitir varias selecciones, pero no funciona
//    }
//


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
        listCatAltaRuta = new JList<>();
        listCatAltaRuta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }


}
