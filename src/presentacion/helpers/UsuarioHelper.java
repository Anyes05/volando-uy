package presentacion.helpers;

import logica.clase.Sistema;
import logica.DataTypes.*;
import com.toedter.calendar.JCalendar;
import logica.clase.*;
import java.awt.event.ComponentEvent;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;




public class UsuarioHelper {

    //Formatear fecha a string
    public static String formatearFecha(DTFecha fecha) {
        if (fecha == null) return "";
        return String.format("%02d/%02d/%04d",
                fecha.getDia(),
                fecha.getMes(),
                fecha.getAno());
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


    //Formatear reserva

    public static String formatearReserva(DTReserva r) {
        if (r == null) return "";

        String tipo;

        if (r instanceof DTCompraPaquete) {
            tipo = "Paquete";
        } else if (r instanceof DTCompraComun) {
            tipo = "Común";
        } else {
            tipo = "Desconocido";
        }

        return tipo + " | Fecha: " + formatearFecha(r.getFechaReserva()) +
                " | Costo: " + r.getCostoReserva();
    }



   // private Sistema sistema = Sistema.getInstance(); // o el controlador donde está mostrarDatosUsuario


    /// // CAMBIAR PANEL VERLO
    public static void cambiarPanel(JPanel parentPanel, JPanel panelNuevo) {
        parentPanel.removeAll();
        parentPanel.add(panelNuevo);
        parentPanel.repaint();
        parentPanel.revalidate();
    }


    /// SABER SI ES CLIENTE O AEROLINEA///////////
    public static String obtenerTipoUsuario(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            return null;
        }

        try {
            DTUsuario usuario = Sistema.getInstance().mostrarDatosUsuario(nickname);

            if (usuario instanceof DTCliente) {

                return "Cliente";
            } else if (usuario instanceof DTAerolinea) {
                return "Aerolinea";

            } else {
                return "otro";
            }

        } catch (Exception e) {
            return null; // No encontrado o error
        }
    }

    // Método para limpiar campos de texto de un formulario
    public static void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
    //MODIFICAR USUARIO
    public static void abrirPanelModificacionUsuario(JPanel parentPanel,JPanel modificarCliente,JPanel modificarAerolinea, String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = obtenerTipoUsuario(nickname);

        if (tipo == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar en Sistema el usuario a modificar
        Sistema.getInstance().seleccionarUsuarioAMod(nickname);

        // Cambiar panel según tipo
        switch (tipo) {
            case "Cliente" -> {
                cambiarPanel(parentPanel, modificarCliente);
            }
            case "Aerolinea" -> {
                cambiarPanel(parentPanel, modificarAerolinea);
            }
            default -> {
                JOptionPane.showMessageDialog(null, "Tipo de usuario no soportado para modificación", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void guardarCambiosCliente(String nombre, String apellido, DTFecha fechaNac,
                                             String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        try {
            Sistema.getInstance().modificarDatosCliente(nombre, apellido, fechaNac, nacionalidad, tipoDocumento, numeroDocumento);
            JOptionPane.showMessageDialog(null, "Datos del cliente modificados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void guardarCambiosAerolinea(String nombre, String descripcion, String linkSitioWeb) {
        try {
            Sistema.getInstance().modificarDatosAerolinea(nombre, descripcion, linkSitioWeb);
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
            JTextField correoField
    ) {
        try {
            // Obtener los datos del usuario
            DTUsuario usuario = Sistema.getInstance().mostrarDatosUsuario(nickname);

            // Verificar que sea una aerolínea
            if (usuario instanceof DTAerolinea aerolinea) {
                nombreField.setText(aerolinea.getNombre());
                descripcionField.setText(aerolinea.getDescripcion());
                sitioWebField.setText(aerolinea.getLinkSitioWeb());
                correoField.setText(aerolinea.getCorreo());
            } else {
                JOptionPane.showMessageDialog(null, "El usuario seleccionado no es una aerolínea.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JTextField correoField,
            JCalendar fechaNacimientoCalendar   // <-- ahora JCalendar
    ) {
        try {
            DTUsuario usuario = Sistema.getInstance().mostrarDatosUsuario(nickname);

            if (usuario instanceof DTCliente c) {
                nombreField.setText(c.getNombre());
                apellidoField.setText(c.getApellido());
                nacionalidadField.setText(c.getNacionalidad());
                tipoDocCombo.setSelectedItem(c.getTipoDocumento());
                documentoField.setText(c.getNumeroDocumento());
                correoField.setText(c.getCorreo());

                // Setear fecha en JCalendar (no soporta null)
                DTFecha fn = c.getFechaNacimiento();
                if (fn != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.clear(); // evita arrastrar hora/minutos
                    cal.set(fn.getAno(), fn.getMes() - 1, fn.getDia());
                    fechaNacimientoCalendar.setCalendar(cal);
                } else {
                     fechaNacimientoCalendar.setDate(new Date());
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "El usuario seleccionado no es un cliente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar datos del cliente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    // Método para actualizar un JTable con la lista de usuarios
    public static void actualizarTablaUsuarios(JTable tablaUsuarios) {
        if (tablaUsuarios == null) return;

        // Definir las columnas
        String[] columnas = {"Tipo", "Nickname", "Nombre", "Correo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        try {
            List<DTUsuario> usuarios = Sistema.getInstance().consultarUsuarios();
            for (DTUsuario u : usuarios) {
                String tipo;
                if (u instanceof DTCliente) {
                    tipo = "Cliente";
                } else if (u instanceof DTAerolinea) {
                    tipo = "Aerolínea";
                } else {
                    tipo = "Desconocido";
                }

                Object[] fila = {
                        tipo,
                        u.getNickname(),
                        u.getNombre(),
                        u.getCorreo()
                };
                modeloTabla.addRow(fila);
            }
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    tablaUsuarios,
                    ex.getMessage(),
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        // Aplicar el modelo nuevo a la tabla
        tablaUsuarios.setModel(modeloTabla);
    }


    // Para mostrar usuarios en consola
    public static void comprobarUsuarios(){
        try {
            List<DTUsuario> lista = Sistema.getInstance().consultarUsuarios();
            for (DTUsuario u : lista) {
                System.out.println(u.getNickname() + " | " + u.getNombre() + " | " + u.getCorreo());
            }
            JOptionPane.showMessageDialog(null, "Usuarios listados en consola.");
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, "No hay usuarios cargados.");
        }
    }

    // para validar campos de usuario antes de crearlo
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
        Sistema.getInstance().altaCliente(
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

    // reiniciar ComboBox y Calendar
    public static void resetFormulario(JComboBox<?> combo, JCalendar calendario, JTextField primerCampo) {
        if (combo != null) combo.setSelectedIndex(0);
        if (calendario != null) calendario.setDate(new Date());
        if (primerCampo != null) primerCampo.requestFocus();
    }

    /// ////// Aerolinea ///////////
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
        Sistema.getInstance().altaAerolinea(
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
            DTUsuario usuario = Sistema.getInstance().mostrarDatosUsuario(nickname);

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
    }

    ////////PAQUETES DE VUELO/////////// ESTE NO RECIBE PARAMETROS POR QUE TODAVIA NO ESTA IMPLEMENTADO EN SISTEMA

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

*//*
   public static void mostrarDatosPaquete(JTable tabla, String nombrePaquete) {
       if (tabla == null || nombrePaquete == null || nombrePaquete.isEmpty()) {
           JOptionPane.showMessageDialog(null, "Debe proporcionar un nombre de paquete válido", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }

       try {
           // Cuando el backend esté listo, esto funcionará
           PaqueteVuelo paqueteSeleccionado = Sistema.getInstance().seleccionarPaquete(nombrePaquete);
           DTPaqueteVuelos paquete = Sistema.getInstance().consultaPaqueteVuelo(paqueteSeleccionado);

           // Definir columnas horizontales
           String[] columnas = {
                   "Nombre", "Descripción", "Tipo Asiento", "Días Válidos",
                   "Descuento", "Costo Base", "Costo Total", "Fecha Alta"
           };

           DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

           // Agregar fila con los datos del paquete
           modelo.addRow(new Object[]{
                   paquete.getNombre(),
                   paquete.getDescripcion(),
                   paquete.getTipoAsiento(),
                   paquete.getDiasValidos(),
                   paquete.getDescuento(),
                   paquete.getCostoBase() != null ? paquete.getCostoBase().toString() : "",
                   paquete.getCostoTotal(),
                   paquete.getFechaAlta()
           });

           tabla.setModel(modelo);
           tabla.setAutoCreateRowSorter(true);

       } catch (IllegalStateException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "Error al mostrar datos del paquete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
}
