package presentacion.AltaUsuario;
import javax.swing.*;

public class mainAltaUsuario {
    private JPanel mainPanel;
    private JButton crearNuevoClienteButton;
    private JButton crearNuevaAerolineaButton;



    public static void main(String[] args) {
        JFrame frame = new JFrame("AltaUsuario");
        frame.setContentPane(new mainAltaUsuario().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public mainAltaUsuario() {
        crearNuevoClienteButton.addActionListener(e -> {
            JFrame nuevaVentana = new JFrame("Alta Cliente");
            nuevaVentana.setContentPane(new AltaCliente().getPanel());
            nuevaVentana.pack();
            nuevaVentana.setLocationRelativeTo(null); // centrar
            nuevaVentana.setSize(400, 400);
            nuevaVentana.setVisible(true);
        });
    }
}