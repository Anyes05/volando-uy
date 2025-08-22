package presentacion;

import  com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

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
    private JTextField Apellido;
    private JTextField Nombre;
    private JTextField Nickname;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JTextPane textPane1;
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
    private JTextField textField3;
    private JButton aceptarButton1;
    private JRadioButton rutaDeVueloRadioButton;
    private JRadioButton reservaDeVueloRadioButton;
    private JTextField textField4;
    private JRadioButton paqueteVueloRadioButton;
    private JButton confirmarButton;
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
    private JButton button2;

    public EstacionTrabajo() {
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(principalVacio);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });

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
                        parentPanel.add(consultaUsuario);
                        parentPanel.repaint();
                        parentPanel.revalidate();
                        break;
                }
                }
        });
        ButtonCrearNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(altaCliente);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        ButtonCrearNuevaAerolinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(altaAerolinea);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
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
    }






}
