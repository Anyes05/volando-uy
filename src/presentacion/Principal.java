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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new Principal().mainPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
