package presentacion;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.util.Locale;

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
    private JTextField textField1;
    private JTextField textField2;
    private JSpinner spinner1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new Principal().mainPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public class DemoJCalendar {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                JFrame f = new JFrame("Calendario");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Locale.setDefault(new Locale("es", "UY"));
                JDateChooser chooser = new JDateChooser();
                chooser.setDate(new java.util.Date());

                f.add(chooser);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            });
        }
}
