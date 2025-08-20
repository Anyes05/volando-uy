package presentacion;

import com.toedter.calendar.JCalendar;

import javax.swing.*;

public class Principal {
    private JPanel mainPrincipal;
    private JInternalFrame altaRutaVuelo;
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
    private JInternalFrame altaUsuario;
    private JInternalFrame altaAerolinea;
    private JButton crearNuevoClienteButton;
    private JButton crearNuevaAerolineaButton;
    private JInternalFrame altaCliente;
    private JTextField Apellido;
    private JTextField Nombre;
    private JTextField Nickname;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JTextPane textPane1;
    private JInternalFrame consultaUsuario;
    private JTextField textField3;
    private JButton aceptarButton1;
    private JRadioButton usuarioRadioButton;
    private JRadioButton rutaDeVueloRadioButton;
    private JRadioButton reservaDeVueloRadioButton;
    private JRadioButton paqueteVueloRadioButton;
    private JTextField textField4;
    private JButton confirmarButton;
    private JInternalFrame modificarUsuario;
    private JTextField textField5;
    private JButton aceptarButton2;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextArea textArea2;
    private JButton guardarButton;

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
