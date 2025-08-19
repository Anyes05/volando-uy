package presentacion.AltaUsuario;

import javax.swing.*;

public class AltaCliente {
    private JPanel panelCliente;
    private JTextField Nickname;
    private JTextField Nombre;
    private JTextField Apellido;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton aceptarButton;
    private JButton cancelarButton;

    public JPanel getPanel() {
        return panelCliente;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Alta Cliente");
        frame.setContentPane(new AltaCliente().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null); // centrar en pantalla
        frame.setVisible(true);
    }

}
