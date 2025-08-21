package presentacion;

import  com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class Principal {
    private JPanel mainPrincipal;
    private JPanel altaRutaVuelo;
    private JComboBox aerolinea;
    private JPanel fromAltaRuta;
    private JTextField nombre;
    private JComboBox categoria;
    private JTextField ciudadOrigen;
    private JTextField ciudadDestino;
    private JTextField costoEjecutivo;
    private JTextField costoTurista;
    private JTextField equipajeExtra;
    private JTextField hora;
    private JCalendar JCalendar1;
    private JTextArea textArea1;
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
    private JTextField textField3;
    private JButton aceptarButton1;
    private JRadioButton usuarioRadioButton;
    private JRadioButton rutaDeVueloRadioButton;
    private JRadioButton reservaDeVueloRadioButton;
    private JRadioButton paqueteVueloRadioButton;
    private JTextField textField4;
    private JButton confirmarButton;
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
    private JPanel panelVacio;
    private JToolBar JToolBarPrincipal;
    private JButton button2;

    public Principal() {
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(panelVacio);
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
                        parentPanel.add(altaRutaVuelo);
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
        panelVacio.addComponentListener(new ComponentAdapter() {
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new Principal().mainPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // Esto es necesario porque JCalendar no es estándar de Swing
        JCalendar1 = new com.toedter.calendar.JCalendar();
    }


}
