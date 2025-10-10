package presentacion.helpers;

import dato.entidades.Aerolinea;
import dato.entidades.Cliente;
import dato.entidades.Reserva;
import dato.entidades.RutaVuelo;
import dato.entidades.CompraComun;
import dato.entidades.CompraPaquete;
import dato.entidades.Usuario;
import logica.clase.Factory;
import logica.clase.ISistema;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;
import logica.clase.*;

import java.awt.*;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

import logica.servicios.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;


public class UsuarioHelper {

    //Formatear fecha
    public static String formatearFecha(DTFecha fecha) {
        if (fecha == null) return "";
        return String.format("%02d/%02d/%04d",
                fecha.getDia(),
                fecha.getMes(),
                fecha.getAno());
    }

    public static void mostrarImagen(byte[] fotoBytes, JLabel imagen) {
        if (imagen == null) {
            System.err.println("Warning: labelImagenConsultaVuelo no está inicializado");
            return;
        }

        if (fotoBytes == null || fotoBytes.length == 0) {
            // No hay imagen, mostrar mensaje o dejar vacío
            imagen.setIcon(null);
            imagen.setText("Sin imagen");
            imagen.setHorizontalAlignment(JLabel.CENTER);
            return;
        }

        try {
            // 🔹 Convertir bytes a ImageIcon
            ImageIcon iconoOriginal = new ImageIcon(fotoBytes);

            // 🔹 Escalar la imagen para que se ajuste al label (opcional pero recomendado)
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    200, 150, // Ajusta el tamaño según tus necesidades
                    Image.SCALE_SMOOTH
            );

            // 🔹 Mostrar la imagen escalada
            imagen.setIcon(new ImageIcon(imagenEscalada));
            imagen.setText(""); // Limpiar cualquier texto

        } catch (Exception ex) {
            System.err.println("Error al cargar la imagen del vuelo: " + ex.getMessage());
            imagen.setIcon(null);
            imagen.setText("Error al cargar imagen");
            imagen.setHorizontalAlignment(JLabel.CENTER);
        }
    };

    public static Calendar convertirCalendarDesdeDTFecha(DTFecha fecha) {
        if (fecha == null) return null;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(fecha.getAno(), fecha.getMes() - 1, fecha.getDia());
        return cal;
    }

    public static DTFecha convertirDTfecha(JCalendar jcalendar) {
        if (jcalendar == null || jcalendar.getDate() == null) {
            return null;
        }

        Date fecha = jcalendar.getDate(); // Obtenemos el Date del JCalendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH) + 1; // ¡Ojo! Enero = 0
        int anio = cal.get(Calendar.YEAR);

        return new DTFecha(dia, mes, anio);
    }


    //Formatear reserva
    public static String formatearReserva(Reserva r) {
        if (r == null) return "";

        String tipo;

        if (r instanceof CompraPaquete) {
            tipo = "Paquete" +
                    " | Costo: " + ((CompraPaquete) r).getPaqueteVuelo().getCostoTotal();

        } else if (r instanceof CompraComun) {
            tipo = "Común" +
                    " | Costo: " + ((CompraComun) r).getCostoReserva();
            ;
        } else {
            tipo = "Desconocido";
        }

        return tipo + " | Fecha: " + formatearFecha(r.getFechaReserva());

    }


    private Sistema sistema; // o el controlador donde está mostrarDatosUsuario

    public UsuarioHelper(Sistema sistema) {
        this.sistema = sistema;
    }

    // CAMBIAR PANEL

    public static void cambiarPanel(JPanel parentPanel, JPanel panelNuevo) {
        parentPanel.removeAll();
        parentPanel.add(panelNuevo);
        parentPanel.repaint();
        parentPanel.revalidate();
    }

    // Método helper para obtener la instancia de ISistema a través del Factory
    private static ISistema getSistema() {
        Factory factory = new Factory();
        return factory.getSistema();
    }


    // SABER SI ES CLIENTE O AEROLINEA
    public static String obtenerTipoUsuario(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            return "---";
        }

        try {
            AerolineaServicio aerolineaServicio = new AerolineaServicio();
            ClienteServicio clienteServicio = new ClienteServicio();

            if (aerolineaServicio.buscarAerolineaPorNickname(nickname) != null) {
                return "Aerolinea";
            }

            if (clienteServicio.buscarClientePorNickname(nickname) != null) {
                return "Cliente";
            }

            // Si no se encuentra en ninguno, devolvemos "---"
            return "---";

        } catch (Exception e) {
            return "---";
        }
    }

    //MODIFICAR USUARIO
    public static void abrirPanelModificacionUsuario(
            JPanel parentPanel,
            JPanel modificarCliente,
            JPanel modificarAerolinea,
            String tipo,
            String nickname
    ) {
        getSistema().seleccionarUsuarioAMod(nickname);

        if (tipo.equals("Cliente")) {
            cambiarPanel(parentPanel, modificarCliente);
        } else if (tipo.equals("Aerolinea")) {
            cambiarPanel(parentPanel, modificarAerolinea);
        }
    }

    public static void guardarCambiosCliente(
            String nickname,
            String nombre,
            String apellido,
            DTFecha fechaNac,
            String nacionalidad,
            TipoDoc tipoDocumento,
            String numeroDocumento
    ) {
        try {
            ClienteServicio clienteServicio = new ClienteServicio();
            Cliente cliente = clienteServicio.buscarClientePorNickname(nickname);

            if (cliente == null) {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con ese nickname.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar los campos
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setFechaNacimiento(fechaNac);
            cliente.setNacionalidad(nacionalidad);
            cliente.setTipoDoc(tipoDocumento);
            cliente.setNumeroDocumento(numeroDocumento);

            // Persistir los cambios
            clienteServicio.actualizarCliente(cliente);

            JOptionPane.showMessageDialog(null, "Datos del cliente modificados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void guardarCambiosAerolinea(String nickname, String nombre, String descripcion, String linkSitioWeb) {
        try {
            AerolineaServicio aerolineaServicio = new AerolineaServicio();
            dato.entidades.Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nickname);

            if (aerolinea == null) {
                JOptionPane.showMessageDialog(null, "No se encontró la aerolínea con ese nickname.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar los campos
            aerolinea.setNombre(nombre);
            aerolinea.setDescripcion(descripcion);
            aerolinea.setLinkSitioWeb(linkSitioWeb);

            // Persistir los cambios
            aerolineaServicio.actualizarAerolinea(aerolinea);

            JOptionPane.showMessageDialog(null, "Datos de la aerolínea modificados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar aerolínea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void cargarDatosAerolineaEnCampos(
            String nickname,
            JTextField nombreField,
            JTextArea descripcionField,
            JTextField sitioWebField,
            JLabel fotoLabel,
            Consumer<byte[]> setFotoSeleccionada
    ) {
        try {
            AerolineaServicio aerolineaServicio = new AerolineaServicio();
            dato.entidades.Aerolinea aero = aerolineaServicio.buscarAerolineaPorNickname(nickname);

            if (aero == null) {
                JOptionPane.showMessageDialog(null, "No se encontró la aerolínea con ese nickname.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            nombreField.setText(aero.getNombre());
            descripcionField.setText(aero.getDescripcion());
            sitioWebField.setText(aero.getLinkSitioWeb());

            byte[] foto = aero.getFoto();

            if (foto != null && foto.length > 0) {
                mostrarImagen(foto, fotoLabel);
                setFotoSeleccionada.accept(foto);
            } else {
                fotoLabel.setIcon(null);
                fotoLabel.setText("Sin imagen");
                setFotoSeleccionada.accept(null);
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos de la aerolínea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void cargarDatosClienteEnCampos(
            String nickname,
            JTextField nombreField,
            JTextField apellidoField,
            JTextField nacionalidadField,
            JComboBox<TipoDoc> tipoDocCombo,
            JTextField documentoField,
            JCalendar fechaNacimientoCalendar,
            JLabel fotoLabel,
            Consumer<byte[]> setFotoSeleccionada
    ) {
        try {
            ClienteServicio clienteServicio = new ClienteServicio();
            dato.entidades.Cliente cliente = clienteServicio.buscarClientePorNickname(nickname);

            if (cliente == null) {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con ese nickname.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            nombreField.setText(cliente.getNombre());
            apellidoField.setText(cliente.getApellido());
            nacionalidadField.setText(cliente.getNacionalidad());
            tipoDocCombo.setSelectedItem(cliente.getTipoDoc());
            documentoField.setText(cliente.getNumeroDocumento());

            byte[] foto = cliente.getFoto();

            if (foto != null && foto.length > 0) {
                mostrarImagen(foto, fotoLabel);
                setFotoSeleccionada.accept(foto);
            } else {
                fotoLabel.setIcon(null);
                fotoLabel.setText("Sin imagen");
                setFotoSeleccionada.accept(null);
            }

            // Setear fecha
            DTFecha fn = cliente.getFechaNacimiento();
            Calendar cal = convertirCalendarDesdeDTFecha(fn);
            if (cal != null) {
                fechaNacimientoCalendar.setCalendar(cal);
            } else {
                fechaNacimientoCalendar.setDate(new Date());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos del cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // LIMPIEZA
    public static void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    public static void limpiarTextPane(JTextPane... panes) {
        for (JTextPane pane : panes) {
            pane.setText("");
        }
    }

    // reiniciar ComboBox y Calendar
    public static void resetFormulario(JComboBox<?> combo, JCalendar calendario, JTextField primerCampo) {
        if (combo != null) combo.setSelectedIndex(0);
        if (calendario != null) calendario.setDate(new Date());
        if (primerCampo != null) primerCampo.requestFocus();
    }

    // ACTUALIZAR TABLA DE LISTADO DE USUARIOS
    public static void actualizarTablaUsuarios(JTable tablaUsuarios) {
        if (tablaUsuarios == null) return;

        String[] columnas = {"Tipo", "Nickname", "Nombre", "Correo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        try {
            UsuarioServicio usuarioServicio = new UsuarioServicio();

            // Trae todos los usuarios de una sola vez
            List<dato.entidades.Usuario> usuarios = usuarioServicio.listarUsuarios();

            for (Usuario u : usuarios) {
                String tipo;
                if (u instanceof Cliente) {
                    tipo = "Cliente";
                } else if (u instanceof Aerolinea) {
                    tipo = "Aerolínea";
                } else {
                    tipo = "Desconocido";
                }

                modeloTabla.addRow(new Object[]{
                        tipo,
                        u.getNickname(),
                        u.getNombre(),
                        u.getCorreo()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    tablaUsuarios,
                    "Error al consultar usuarios: " + ex.getMessage(),
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        tablaUsuarios.setModel(modeloTabla);
    }


    // VALIDACION
    public static boolean modificarAerolineaValidar(
            JTextField nombre,
            JTextField sitioWeb,
            JTextArea descripcion
    ) {

        String nombreTexto = nombre.getText().trim();

        if (nombreTexto.length() < 4) {
            JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Solo letras y espacios (sin símbolos ni números)
        if (!nombreTexto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras y espacios, sin símbolos ni números.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        // Validar sitio web
        String sitioText = sitioWeb.getText().trim();
        if (!sitioText.matches(".*\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "El sitio web debe contener un dominio válido como .com, .ar, etc.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar descripción
        if (descripcion.getText().trim().length() < 10) {
            JOptionPane.showMessageDialog(null, "La descripción debe tener al menos 10 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean modificarClienteValidar(
            JTextField nombre,
            JTextField apellido,
            JTextField nacionalidad,
            TipoDoc tipoDocumento,
            JTextField numeroDocumento
    ) {
        String nom = nombre.getText().trim();
        String ape = apellido.getText().trim();
        String nac = nacionalidad.getText().trim();
        String doc = numeroDocumento.getText().trim();


        // Nombre y apellido solo letras
        if (!nom.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!ape.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El apellido solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nacionalidad
        if (!nac.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "La nacionalidad solo puede contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // --- Validar documento según tipo ---
        switch (tipoDocumento) {
            case CI: // Cédula uruguaya
                // Formato: 12345678 o 1.234.567-8
                if (!doc.matches("^[0-9]{7,8}$") && !doc.matches("^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9]$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese una CI uruguaya válida (ej: 12345678 o 1.234.567-8).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case PASAPORTE:
                // Letras + números, entre 6 y 9 caracteres
                if (!doc.matches("^[A-Z0-9]{6,9}$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese un pasaporte válido (ej: A1234567).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case DNI:
                // 7-8 dígitos + opcional una letra
                if (!doc.matches("^[0-9]{7,8}[A-Za-z]?$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese un DNI válido (ej: 12345678 o 12345678A).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de documento válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }

        return true;
    }


    public static boolean validarFechaNacimiento(JCalendar calendarioNacimiento) {
        if (calendarioNacimiento == null || calendarioNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha de nacimiento.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Date fecha = calendarioNacimiento.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        int año = cal.get(Calendar.YEAR);

        // Validar que el año sea numérico y positivo
        if (año < 0) {
            JOptionPane.showMessageDialog(null, "El año de nacimiento no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Date hoy = new Date();

        // No puede ser futura
        if (fecha.after(hoy)) {
            JOptionPane.showMessageDialog(null, "La fecha de nacimiento no puede ser en el futuro.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Debe tener más de 10 años
        Calendar minEdad = Calendar.getInstance();
        minEdad.add(Calendar.YEAR, -10);
        if (fecha.after(minEdad.getTime())) {
            JOptionPane.showMessageDialog(null, "Debe tener al menos 10 años de edad.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // No puede tener más de 100 años
        Calendar maxEdad = Calendar.getInstance();
        maxEdad.add(Calendar.YEAR, -100);
        if (fecha.before(maxEdad.getTime())) {
            JOptionPane.showMessageDialog(null, "La edad no puede superar los 100 años.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean validarCliente(
            JTextField nickname,
            JTextField nombre,
            JTextField apellido,
            JTextField correo,
            JTextField nacionalidad,
            TipoDoc tipoDocumento,
            JTextField numeroDocumento,
            JCalendar fechaNac,
            JPasswordField password,
            JPasswordField confirmarPassword,
            byte[] foto

    ) {
        String nick = nickname.getText().trim();
        String nom = nombre.getText().trim();
        String ape = apellido.getText().trim();
        String mail = correo.getText().trim();
        String nac = nacionalidad.getText().trim();
        String doc = numeroDocumento.getText().trim();

        if (!validarFechaNacimiento(fechaNac)) {
            return false;
        }

        if (nick.isEmpty() || nom.isEmpty() || ape.isEmpty() || mail.isEmpty() || nac.isEmpty() || doc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nickname mínimo 4
        if (nick.length() < 4) {
            JOptionPane.showMessageDialog(null, "El nickname debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nombre y apellido solo letras
        if (!nom.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!ape.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El apellido solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nacionalidad
        if (!nac.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "La nacionalidad solo puede contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Correo
        if (!mail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(null, "Ingrese un correo electrónico válido (ej: usuario@dominio.com).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Foto cargada
        if (foto == null || foto.length == 0) {
            JOptionPane.showMessageDialog(null, "Debe subir una imagen de perfil.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Contraseña
        String pass = new String(password.getPassword()).trim();
        String confirm = new String(confirmarPassword.getPassword()).trim();

        if (pass.length() < 6 || !pass.matches(".*[A-Z].*") || !pass.matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres, una mayúscula y un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // --- Validar documento según tipo ---
        switch (tipoDocumento) {
            case CI: // Cédula uruguaya
                // Formato: 12345678 o 1.234.567-8
                if (!doc.matches("^[0-9]{7,8}$") && !doc.matches("^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9]$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese una CI uruguaya válida (ej: 12345678 o 1.234.567-8).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case PASAPORTE:
                // Letras + números, entre 6 y 9 caracteres
                if (!doc.matches("^[A-Z0-9]{6,9}$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese un pasaporte válido (ej: A1234567).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case DNI:
                // 7-8 dígitos + opcional una letra
                if (!doc.matches("^[0-9]{7,8}[A-Za-z]?$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese un DNI válido (ej: 12345678 o 12345678A).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de documento válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }

        return true;
    }

    public static boolean validarAerolinea(
            JTextField nickname,
            JTextField nombre,
            JTextField correo,
            JTextField sitioWeb,
            JTextArea descripcion,
            JPasswordField password,
            JPasswordField confirmarPassword,
            byte[] foto

    ) {
        // Validar nickname
        if (nickname.getText().trim().length() < 4) {
            JOptionPane.showMessageDialog(null, "El nickname debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String nombreTexto = nombre.getText().trim();

        if (nombreTexto.length() < 4) {
            JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Solo letras y espacios (sin símbolos ni números)
        if (!nombreTexto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras y espacios, sin símbolos ni números.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar correo
        String correoText = correo.getText().trim();
        if (!correoText.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "El correo electrónico no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar sitio web
        String sitioText = sitioWeb.getText().trim();
        if (!sitioText.matches(".*\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "El sitio web debe contener un dominio válido como .com, .ar, etc.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Validar descripción
        if (descripcion.getText().trim().length() < 10) {
            JOptionPane.showMessageDialog(null, "La descripción debe tener al menos 10 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar imagen cargada
        if (foto == null || foto.length == 0) {
            JOptionPane.showMessageDialog(null, "Debe subir una imagen de la aerolínea.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar contraseña
        String pass = new String(password.getPassword()).trim();
        String confirm = new String(confirmarPassword.getPassword()).trim();

        if (pass.length() < 6 || !pass.matches(".*[A-Z].*") || !pass.matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres, una mayúscula y un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        return true;
    }

    // ver que zona es
    public static void crearCliente(String nickname,
                                    String nombre,
                                    String correo,
                                    String apellido,
                                    String nacionalidad,
                                    TipoDoc tipoDocumento,
                                    String numeroDocumento,
                                    Date fechaNacimiento,
                                    byte[] foto,
                                    String contrasena) throws Exception {

        // Convertir Date a DTFecha
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH) + 1;
        int ano = cal.get(Calendar.YEAR);
        DTFecha fechaNac = new DTFecha(dia, mes, ano);

        // Llamar a la función de Sistema
        getSistema().altaCliente(
                nickname,
                nombre,
                correo,
                apellido,
                fechaNac,
                nacionalidad,
                tipoDocumento,
                numeroDocumento,
                foto,
                contrasena
        );
    }

    public static DTFecha convertirDTfecha(Date fecha) {
        if (fecha == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH) + 1; // ¡Ojo! Enero = 0
        int anio = cal.get(Calendar.YEAR);

        return new DTFecha(dia, mes, anio);
    }


    /// ////// Aerolinea ///////////


    public static void crearAerolinea(
            String nickname,
            String nombre,
            String correo,
            String sitioWeb,
            String descripcion,
            byte[] foto,
            String contrasena,
            String confirmarContrasena

    ) throws Exception {
        // Validaciones
        if (!validarAerolinea(
                new JTextField(nickname),
                new JTextField(nombre),
                new JTextField(correo),
                new JTextField(sitioWeb),
                new JTextArea(descripcion),
                new JPasswordField(contrasena),
                new JPasswordField(confirmarContrasena),
                foto
        )) {
            throw new Exception("Datos de la aerolínea inválidos.");
        }

        // Llamada al sistema
        getSistema().altaAerolinea(
                nickname,
                nombre,
                correo,
                descripcion,
                sitioWeb,
                foto,
                contrasena
        );
    }

    public static void resetFormularioAerolinea(
            JTextField nickname,
            JTextField nombre,
            JTextField correo,
            JTextField sitioWeb,
            JTextPane descripcion
    ) {
        limpiarCampos(nickname, nombre, correo, sitioWeb);
        descripcion.setText("");
        nickname.requestFocus();
    }

    public static void mostrarDatosUsuario(JTable tabla, String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Debe proporcionar un Nickname",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            ClienteServicio clienteServicio = new ClienteServicio();
            AerolineaServicio aerolineaServicio = new AerolineaServicio();

            String[] columnas = {"Campo", "Valor", "Valor"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            // Buscar cliente
            Cliente c = clienteServicio.buscarClientePorNickname(nickname);
            if (c != null) {
                modelo.addRow(new Object[]{"Nickname", c.getNickname(), ""});
                modelo.addRow(new Object[]{"Nombre", c.getNombre(), ""});
                modelo.addRow(new Object[]{"Correo", c.getCorreo(), ""});
                modelo.addRow(new Object[]{"Apellido", c.getApellido(), ""});
                modelo.addRow(new Object[]{"Tipo Doc", c.getTipoDoc(), ""});
                modelo.addRow(new Object[]{"Documento", c.getNumeroDocumento(), ""});
                modelo.addRow(new Object[]{"Fecha Nac", c.getFechaNacimiento(), ""});
                modelo.addRow(new Object[]{"Nacionalidad", c.getNacionalidad(), ""});

                if (c.getReservas() != null) {
                    for (Reserva r : c.getReservas()) {
                        modelo.addRow(new Object[]{"Reserva", formatearReserva(r), ""});
                    }
                }

                tabla.setModel(modelo);
                tabla.setAutoCreateRowSorter(true);
                return;
            }

            // Buscar aerolínea
            Aerolinea a = aerolineaServicio.buscarAerolineaPorNickname(nickname);
            if (a != null) {
                modelo.addRow(new Object[]{"Nickname", a.getNickname(), ""});
                modelo.addRow(new Object[]{"Nombre", a.getNombre(), ""});
                modelo.addRow(new Object[]{"Correo", a.getCorreo(), ""});
                modelo.addRow(new Object[]{"Descripción", a.getDescripcion(), ""});
                modelo.addRow(new Object[]{"Sitio Web", a.getLinkSitioWeb(), ""});

                if (a.getRutasVuelo() != null) {
                    for (RutaVuelo ruta : a.getRutasVuelo()) {
                        DTCostoBase costo = ruta.getCostoBase();
                        String costoFormateado = (costo != null)
                                ? String.format(
                                "Turista: $%.2f | Ejecutivo: $%.2f | Equipaje Extra: $%.2f x%d | Total: $%.2f",
                                costo.getCostoTurista(),
                                costo.getCostoEjecutivo(),
                                costo.getCostoEquipajeExtra(),
                                costo.getCantidadEquipajeExtra(),
                                costo.getCostoTotal()
                        )
                                : "Sin costo";

                        modelo.addRow(new Object[]{
                                "Ruta",
                                ruta.getNombre() + " - " + ruta.getDescripcion(),
                                costoFormateado
                        });
                    }
                }

                tabla.setModel(modelo);
                tabla.setAutoCreateRowSorter(true);
                return;
            }

            // Si no se encontró en ninguno
            JOptionPane.showMessageDialog(
                    null,
                    "Usuario no encontrado: " + nickname,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


}
