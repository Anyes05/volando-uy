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

import java.util.Date;
import java.util.Calendar;
import java.util.List;

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
            tipo = "Paquete";
        } else if (r instanceof CompraComun) {
            tipo = "Común";
        } else {
            tipo = "Desconocido";
        }

        return tipo + " | Fecha: " + formatearFecha(r.getFechaReserva()) +
                " | Costo: " + r.getCostoReserva();
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
            JTextField sitioWebField
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
            JCalendar fechaNacimientoCalendar
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

        // Validar nombre
        if (nombre.getText().trim().length() < 4) {
            JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        // Validar sitio web
        String sitioText = sitioWeb.getText().trim();
        if (!sitioText.matches("^[^\\s]+\\.[^\\s]+$")){
            JOptionPane.showMessageDialog(null, "El sitio web debe comenzar con http:// o https:// y ser válido.", "Error", JOptionPane.ERROR_MESSAGE);
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
            case Pasaporte:
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

    public static boolean validarCliente(
            JTextField nickname,
            JTextField nombre,
            JTextField apellido,
            JTextField correo,
            JTextField nacionalidad,
            TipoDoc tipoDocumento,
            JTextField numeroDocumento
    ) {
        String nick = nickname.getText().trim();
        String nom = nombre.getText().trim();
        String ape = apellido.getText().trim();
        String mail = correo.getText().trim();
        String nac = nacionalidad.getText().trim();
        String doc = numeroDocumento.getText().trim();


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

        // --- Validar documento según tipo ---
        switch (tipoDocumento) {
            case CI: // Cédula uruguaya
                // Formato: 12345678 o 1.234.567-8
                if (!doc.matches("^[0-9]{7,8}$") && !doc.matches("^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9]$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese una CI uruguaya válida (ej: 12345678 o 1.234.567-8).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case Pasaporte:
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
            JTextArea descripcion
    ) {
        // Validar nickname
        if (nickname.getText().trim().length() < 4) {
            JOptionPane.showMessageDialog(null, "El nickname debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar nombre
        if (nombre.getText().trim().length() < 2) {
            JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 2 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (!sitioText.matches("^[^\\s]+\\.[^\\s]+$")){
            JOptionPane.showMessageDialog(null, "El sitio web debe comenzar con http:// o https:// y ser válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar descripción
        if (descripcion.getText().trim().length() < 10) {
            JOptionPane.showMessageDialog(null, "La descripción debe tener al menos 10 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
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
                                    Date fechaNacimiento) throws Exception {

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
                numeroDocumento
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

    // reiniciar ComboBox y Calendar
    public static void resetFormulario(JComboBox<?> combo, JCalendar calendario, JTextField primerCampo) {
        if (combo != null) combo.setSelectedIndex(0);
        if (calendario != null) calendario.setDate(new Date());
        if (primerCampo != null) primerCampo.requestFocus();
    }

    /// ////// Aerolinea ///////////



    public static void crearAerolinea(
            String nickname,
            String nombre,
            String correo,
            String sitioWeb,
            String descripcion
    ) throws Exception {
        // Validaciones
        if (!validarAerolinea(
                new JTextField(nickname),
                new JTextField(nombre),
                new JTextField(correo),
                new JTextField(sitioWeb),
                new JTextArea(descripcion)
        )) {
            throw new Exception("Datos de la aerolínea inválidos.");
        }

        // Llamada al sistema
        getSistema().altaAerolinea(
                nickname,
                nombre,
                correo,
                descripcion,
                sitioWeb
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
                        modelo.addRow(new Object[]{
                                "Ruta",
                                ruta.getNombre() + " - " + ruta.getDescripcion(),
                                ruta.getCostoBase()
                        });
                    }
                }

                tabla.setModel(modelo);
                tabla.setAutoCreateRowSorter(true);
                return;
            }

            // Si no se encontró en ninguno
            JOptionPane.showMessageDialog(null, "Usuario no encontrado: " + nickname, "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


/*
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
            DTUsuario usuario = getSistema().mostrarDatosUsuario(nickname);

            String[] columnas = {"Campo", "Valor", "Valor"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            // Cliente
            if (usuario instanceof DTCliente c) {
                modelo.addRow(new Object[]{"Nickname", c.getNickname(),""});
                modelo.addRow(new Object[]{"Nombre", c.getNombre(),""});
                modelo.addRow(new Object[]{"Correo", c.getCorreo(),""});
                modelo.addRow(new Object[]{"Apellido", c.getApellido(),""});
                modelo.addRow(new Object[]{"Tipo Doc", c.getTipoDocumento(),""});
                modelo.addRow(new Object[]{"Documento", c.getNumeroDocumento(),""});
                modelo.addRow(new Object[]{"Fecha Nac", c.getFechaNacimiento(),""});
                modelo.addRow(new Object[]{"Nacionalidad", c.getNacionalidad(),""});

                // Reservas y paquetes
                if (c.getReserva() != null) {
                    for (DTReserva r : c.getReserva()) {
                        modelo.addRow(new Object[]{"Reserva", formatearReserva(r),""});
                    }

                }
            }
            // Aerolínea
            else if (usuario instanceof DTAerolinea a) {
                modelo.addRow(new Object[]{"Nickname", a.getNickname(),""});
                modelo.addRow(new Object[]{"Nombre", a.getNombre(),""});
                modelo.addRow(new Object[]{"Correo", a.getCorreo(),""});
                modelo.addRow(new Object[]{"Descripción", a.getDescripcion(),""});
                modelo.addRow(new Object[]{"Sitio Web", a.getLinkSitioWeb(),""});

                if (a.getRutasVuelo() != null) {
                    for (DTRutaVuelo ruta : a.getRutasVuelo()) {
                        modelo.addRow(new Object[]{"Ruta", ruta.getNombre()+"-"+ruta.getDescripcion(), ruta.getCostoBase()});
                    }
                }
            }

            // Asignar modelo a la tabla
            tabla.setModel(modelo);
            tabla.setAutoCreateRowSorter(true);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado: " + nickname, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

   /* ////////PAQUETES DE VUELO/////////// ESTE NO RECIBE PARAMETROS POR QUE TODAVIA NO ESTA IMPLEMENTADO EN SISTEMA
    public static void mostrarPaquetes(JTable tabla) {
        try {
            // 1. Obtener todos los paquetes desde Sistema
            List<DTPaqueteVuelos> paquetes = Sistema.getInstance().mostrarPaquete();

            // Evitar null
            if (paquetes == null) {
                paquetes = new ArrayList<>();
            }

            // 2. Definir columnas
            String[] columnas = {
                    "Nombre",
                    "Descripción",
                    "Tipo Asiento",
                    "Días Válidos",
                    "Descuento",
                    "Costo Total"
            };
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            // 3. Llenar la tabla
            if (!paquetes.isEmpty()) {
                for (DTPaqueteVuelos p : paquetes) {
                    modelo.addRow(new Object[] {
                            p.getNombre(),
                            p.getDescripcion(),
                            p.getTipoAsiento(),
                            p.getDiasValidos(),
                            String.format("%.2f%%", p.getDescuento()),
                            String.format("$ %.2f", p.getCostoTotal())
                    });
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "No hay paquetes registrados",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            // 4. Asignar modelo a la tabla
            tabla.setModel(modelo);
            tabla.setAutoCreateRowSorter(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar paquetes: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
*/
    //////////////MOSTRAR RUTA VUELO/////////////
   /* public static void mostrarRutaVuelo(JTable tabla, String nombreRuta) {
        if (nombreRuta == null || nombreRuta.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Debe proporcionar nombre de ruta",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        try {
            // 1. Obtener los vuelos de la ruta desde Sistema
            List<DTVuelo> vuelos = Sistema.getInstance().seleccionarRutaVuelo(nombreRuta);

            // Evitar null
            if (vuelos == null) {
                vuelos = new ArrayList<>();
            }

            // 2. Definir columnas
            String[] columnas = {
                    "Nombre Vuelo",
                    "Duración",
                    "Fecha Vuelo",
                    "Hora Vuelo",
                    "Asientos Ejecutivo",
                    "Asientos Turista",
                    "Ruta",
                    "Origen",
                    "Destino",
                    "Costo Base"
            };
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            // 3. Llenar la tabla
            if (!vuelos.isEmpty()) {
                for (DTVuelo v : vuelos) {
                    DTRutaVuelo ruta = v.getRuta();
                    modelo.addRow(new Object[] {
                            v.getNombre(),
                            v.getDuracion(),
                            v.getFechaVuelo(),
                            v.getHoraVuelo(),
                            v.getAsientosMaxEjecutivo(),
                            v.getAsientosMaxTurista(),
                            ruta.getNombre(),
                            ruta.getCiudadOrigen().getNombre() + " (" + ruta.getCiudadOrigen().getPais() + ")",
                            ruta.getCiudadDestino().getNombre() + " (" + ruta.getCiudadDestino().getPais() + ")",
                            String.format("$ %.2f", ruta.getCostoBase())
                    });
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "No hay vuelos para la ruta: " + nombreRuta + ", o no existe.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            // 4. Asignar modelo a la tabla
            tabla.setModel(modelo);
            tabla.setAutoCreateRowSorter(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar vuelos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void mostrarReservasPorVuelo(JTable tabla, String nombreVuelo) {
        if (nombreVuelo == null || nombreVuelo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Debe proporcionar nombre de vuelo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            // 1. Obtener las reservas del vuelo desde Sistema
            List<DTVueloReserva> reservas = Sistema.getInstance().seleccionarVuelo(nombreVuelo);

            if (reservas == null) {
                reservas = new ArrayList<>();
            }

            // 2. Definir columnas
            String[] columnas = {
                    "Nombre Vuelo",
                    "Duración",
                    "Fecha Vuelo",
                    "Hora Vuelo",
                    "Ruta",
                    "Origen",
                    "Destino",
                    "Fecha Reserva",
                    "Costo Reserva"
            };
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

            // 3. Llenar la tabla
            if (!reservas.isEmpty()) {
                for (DTVueloReserva vr : reservas) {
                    DTVuelo v = vr.getVuelo();
                    DTRutaVuelo ruta = v.getRuta();
                    DTReserva r = vr.getReserva();

                    modelo.addRow(new Object[]{
                            v.getNombre(),
                            v.getDuracion(),
                            v.getFechaVuelo(),
                            v.getHoraVuelo(),
                            ruta.getNombre(),
                            ruta.getCiudadOrigen().getNombre() + " (" + ruta.getCiudadOrigen().getPais() + ")",
                            ruta.getCiudadDestino().getNombre() + " (" + ruta.getCiudadDestino().getPais() + ")",
                            r.getFechaReserva(),
                            String.format("$ %.2f", r.getCostoReserva())
                    });
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "No hay reservas para el vuelo: " + nombreVuelo,
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            // 4. Asignar modelo a la tabla
            tabla.setModel(modelo);
            tabla.setAutoCreateRowSorter(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar reservas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
*/


}
