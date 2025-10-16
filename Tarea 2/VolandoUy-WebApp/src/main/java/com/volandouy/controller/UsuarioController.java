
package com.volandouy.controller;

import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;
import logica.DataTypes.DTUsuario;
import logica.DataTypes.DTCliente;
import logica.DataTypes.DTAerolinea;
import logica.clase.Sistema;
import logica.servicios.ClienteServicio;
import logica.servicios.AerolineaServicio;
import dato.entidades.Cliente;
import dato.entidades.Aerolinea;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

@WebServlet("/api/usuarios/*")

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,    // 5MB
        maxRequestSize = 10 * 1024 * 1024) // 10MB


public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());
    private final ObjectMapper objectMapper;
    private final Sistema sistema = Sistema.getInstance();

    public UsuarioController() {
        // Configurar ObjectMapper para evitar referencias circulares
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        this.objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/usuarios - Listar todos los usuarios
                try {
                    var usuarios = sistema.consultarUsuarios();
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(Map.of("usuarios", usuarios)));
                } catch (IllegalStateException ex) {
                    // No hay usuarios registrados
                    LOG.info("No hay usuarios registrados en el sistema");
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(Map.of("usuarios", new java.util.ArrayList<>(), "mensaje", "No hay usuarios registrados")));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al consultar usuarios", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
                }
            } else if (pathInfo.equals("/perfil")) {
                // GET /api/usuarios/perfil - Obtener datos del usuario logueado
                LOG.info("GET /api/usuarios/perfil - Iniciando proceso");
                HttpSession session = request.getSession(false);
                LOG.info("Session obtenida: " + (session != null ? "existe" : "null"));
                
                if (session == null || session.getAttribute("usuarioLogueado") == null) {
                    LOG.warning("Sesión no válida - session: " + (session != null ? "existe" : "null") + 
                               ", usuarioLogueado: " + (session != null ? session.getAttribute("usuarioLogueado") : "N/A"));
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Sesión no válida")));
                    return;
                }
                
                String nickname = (String) session.getAttribute("usuarioLogueado");
                LOG.info("Nickname obtenido de sesión: " + nickname);
                
                try {
                    LOG.info("Llamando a sistema.mostrarDatosUsuarioMod(" + nickname + ")");
                    var datosUsuario = sistema.mostrarDatosUsuarioMod(nickname);
                    LOG.info("Datos usuario obtenidos: " + (datosUsuario != null ? "existe" : "null"));
                    
                    if (datosUsuario != null) {
                        // Crear Map con los datos necesarios para evitar referencias circulares
                        Map<String, Object> usuarioData = extraerDatosUsuario(datosUsuario);
                        LOG.info("Datos extraídos: " + usuarioData.keySet());
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.print(objectMapper.writeValueAsString(usuarioData));
                    } else {
                        LOG.warning("Datos usuario es null");
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no encontrado")));
                    }
                } catch (IllegalArgumentException ex) {
                    // Usuario no encontrado
                    LOG.info("Usuario no encontrado en el sistema: " + nickname + " - " + ex.getMessage());
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no encontrado: " + ex.getMessage())));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al obtener datos del usuario logueado: " + nickname, ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
                }
            } else {
                // GET /api/usuarios/{nickname} - Obtener datos específicos de un usuario
                String nickname = pathInfo.substring(1); // Remover el '/' inicial
                try {
                    var datosUsuario = sistema.mostrarDatosUsuarioMod(nickname);
                    if (datosUsuario != null) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.print(objectMapper.writeValueAsString(datosUsuario));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no encontrado")));
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al obtener datos del usuario: " + nickname, ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor")));
                }
            }
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo();
            String nickname = null;
            
            if (pathInfo != null && pathInfo.equals("/perfil")) {
                // PUT /api/usuarios/perfil - Modificar perfil del usuario logueado
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("usuarioLogueado") == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Sesión no válida")));
                    return;
                }
                nickname = (String) session.getAttribute("usuarioLogueado");
                LOG.info("PUT /api/usuarios/perfil - Modificar perfil del usuario logueado: " + nickname);
            } else if (pathInfo != null && pathInfo.length() > 1) {
                // PUT /api/usuarios/{nickname} - Modificar usuario específico
                nickname = pathInfo.substring(1); // Remover el '/' inicial
                LOG.info("PUT /api/usuarios/" + nickname + " - Modificar usuario específico");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Endpoint no válido")));
                return;
            }

            // Leer los datos del request
            String contentType = request.getContentType() == null ? "" : request.getContentType().toLowerCase();
            String nombre = null, apellido = null, nacionalidad = null;
            String tipoDoc = null, numeroDoc = null, fechaNacimientoStr = null;
            String descripcion = null, sitioWeb = null;
            byte[] fotoBytes = null;

            if (contentType.contains("application/json")) {
                Map<String, Object> body = objectMapper.readValue(request.getInputStream(), Map.class);
                nombre = trim((String) body.get("nombre"));
                apellido = trim((String) body.get("apellido"));
                nacionalidad = trim((String) body.get("nacionalidad"));
                tipoDoc = trim((String) body.get("tipoDocumento"));
                numeroDoc = trim((String) body.get("numeroDocumento"));
                fechaNacimientoStr = trim((String) body.get("fechaNacimiento"));
                descripcion = trim((String) body.get("descripcion"));
                sitioWeb = trim((String) body.get("sitioWeb"));
            } else {
                // Form data
                nombre = trim(request.getParameter("nombre"));
                apellido = trim(request.getParameter("apellido"));
                nacionalidad = trim(request.getParameter("nacionalidad"));
                tipoDoc = trim(request.getParameter("tipoDocumento"));
                numeroDoc = trim(request.getParameter("numeroDocumento"));
                fechaNacimientoStr = trim(request.getParameter("fechaNacimiento"));
                descripcion = trim(request.getParameter("descripcion"));
                sitioWeb = trim(request.getParameter("sitioWeb"));

                // Manejar archivo de imagen
                Part fotoPart = null;
                try {
                    fotoPart = request.getPart("imagen");
                } catch (IllegalStateException | ServletException ex) {
                    LOG.info("No se pudo obtener la parte 'imagen' del request multipart");
                }
                if (fotoPart != null && fotoPart.getSize() > 0) {
                    try (InputStream inputStream = fotoPart.getInputStream()) {
                        fotoBytes = inputStream.readAllBytes();
                        LOG.info("Imagen recibida - tamaño: " + fotoBytes.length + " bytes");
                    }
                } else {
                    LOG.info("No se recibió imagen o está vacía");
                }
            }

            // Obtener datos actuales del usuario para determinar el tipo
            var datosActuales = sistema.mostrarDatosUsuarioMod(nickname);
            if (datosActuales == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no encontrado")));
                return;
            }

            // Validaciones básicas
            if (isEmpty(nombre)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "El nombre es obligatorio")));
                return;
            }

            // Seleccionar usuario para modificación
            sistema.seleccionarUsuarioAMod(nickname);

            // Determinar tipo de usuario y aplicar modificaciones
            boolean esCliente = false;
            try {
                // Intentar obtener el tipo de usuario usando el nombre de la clase
                esCliente = datosActuales.getClass().getSimpleName().toLowerCase().contains("cliente");
            } catch (Exception e) {
                // Método alternativo: verificar presencia de campos específicos de cliente
                try {
                    datosActuales.getClass().getMethod("getApellido");
                    esCliente = true;
                } catch (NoSuchMethodException ex) {
                    esCliente = false;
                }
            }

            if (esCliente) {
                // Es un cliente
                LocalDate fechaNacimiento = null;
                if (!isEmpty(fechaNacimientoStr)) {
                    try {
                        fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                    } catch (Exception ex) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Formato de fecha inválido")));
                        return;
                    }
                }

                DTFecha dtFecha = null;
                if (fechaNacimiento != null) {
                    dtFecha = new DTFecha(fechaNacimiento.getDayOfMonth(), fechaNacimiento.getMonthValue(), fechaNacimiento.getYear());
                }

                TipoDoc tipoDocEnum = null;
                if (!isEmpty(tipoDoc)) {
                    try {
                        tipoDocEnum = TipoDoc.valueOf(tipoDoc);
                    } catch (IllegalArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Tipo de documento inválido")));
                        return;
                    }
                }

                sistema.modificarDatosCliente(nombre, apellido, dtFecha, nacionalidad, tipoDocEnum, numeroDoc);
                
                // Actualizar foto si se proporcionó
                if (fotoBytes != null && fotoBytes.length > 0) {
                    ClienteServicio clienteServicio = new ClienteServicio();
                    Cliente cliente = clienteServicio.buscarClientePorNickname(nickname);
                    if (cliente != null) {
                        cliente.setFoto(fotoBytes);
                        clienteServicio.actualizarCliente(cliente);
                        LOG.info("Foto actualizada para cliente: " + nickname);
                    }
                }
                
            } else {
                // Es una aerolínea
                sistema.modificarDatosAerolinea(nombre, descripcion, sitioWeb);
                
                // Actualizar foto si se proporcionó
                if (fotoBytes != null && fotoBytes.length > 0) {
                    AerolineaServicio aerolineaServicio = new AerolineaServicio();
                    Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nickname);
                    if (aerolinea != null) {
                        aerolinea.setFoto(fotoBytes);
                        aerolineaServicio.actualizarAerolinea(aerolinea);
                        LOG.info("Foto actualizada para aerolínea: " + nickname);
                    }
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(Map.of("mensaje", "Perfil modificado exitosamente")));

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al modificar usuario", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if (sistema == null) {
                LOG.severe("Sistema.getInstance() devolvió null. Verificar classpath/JAR y carga de la clase.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                return;
            }

            String contentType = request.getContentType() == null ? "" : request.getContentType().toLowerCase();

            String nickname = null, nombre = null, apellido = null, correo = null, nacionalidad = null;
            String tipoDoc = null, numeroDoc = null, fechaNacimientoStr = null, contrasena = null, confirmarContrasena = null;
            String userType = null;
            byte[] fotoBytes = null;
            String descripcion = null; String sitioWeb = null;

            if (contentType.contains("application/json")) {
                Map<String, Object> body = objectMapper.readValue(request.getInputStream(), Map.class);
                nickname = trim((String) body.get("nickname"));
                nombre = trim((String) body.get("nombre"));
                apellido = trim((String) body.get("apellido"));
                correo = trim((String) body.get("correo"));
                nacionalidad = trim((String) body.get("nacionalidad"));
                tipoDoc = trim((String) body.get("tipoDocumento"));
                numeroDoc = trim((String) body.get("numeroDocumento"));
                fechaNacimientoStr = trim((String) body.get("fechaNacimiento"));
                contrasena = trim((String) body.get("contrasena"));
                confirmarContrasena = trim((String) body.get("confirmarContrasena"));
                descripcion = trim((String) body.get("descripcion"));
                sitioWeb = trim((String) body.get("sitioWeb"));
                userType = trim((String) body.get("tipo")); // cliente / aerolinea
            } else {
                nickname = trim(request.getParameter("nickname"));
                nombre = trim(request.getParameter("nombre"));
                apellido = trim(request.getParameter("apellido"));
                correo = trim(request.getParameter("correo"));
                nacionalidad = trim(request.getParameter("nacionalidad"));
                tipoDoc = trim(request.getParameter("tipoDocumento"));
                numeroDoc = trim(request.getParameter("numeroDocumento"));
                fechaNacimientoStr = trim(request.getParameter("fechaNacimiento"));
                contrasena = trim(request.getParameter("contrasena"));
                confirmarContrasena = trim(request.getParameter("confirmarContrasena"));
                descripcion = trim(request.getParameter("descripcion"));
                sitioWeb = trim(request.getParameter("sitioWeb"));
                userType = trim(request.getParameter("tipo"));

                Part fotoPart = null;
                try {
                    fotoPart = request.getPart("foto");
                } catch (IllegalStateException | ServletException ex) {
                    fotoPart = null;
                }
                if (fotoPart != null && fotoPart.getSize() > 0) {
                    try (InputStream is = fotoPart.getInputStream()) {
                        fotoBytes = is.readAllBytes();
                    }
                }
            }

            // LOG de depuración: imprimir todos los parámetros recibidos
            String finalNickname = nickname;
            String finalUserType = userType;
            String finalNombre = nombre;
            String finalApellido = apellido;
            String finalCorreo = correo;
            String finalNacionalidad = nacionalidad;
            String finalTipoDoc = tipoDoc;
            String finalNumeroDoc = numeroDoc;
            String finalFechaNacimientoStr = fechaNacimientoStr;
            String finalContrasena = contrasena;
            byte[] finalFotoBytes = fotoBytes;
            LOG.info(() -> "POST /api/usuarios recibido. tipo=" + finalUserType
                    + ", nickname=" + finalNickname
                    + ", nombre=" + finalNombre
                    + ", apellido=" + finalApellido
                    + ", correo=" + finalCorreo
                    + ", nacionalidad=" + finalNacionalidad
                    + ", tipoDocumento=" + finalTipoDoc
                    + ", numeroDocumento=" + finalNumeroDoc
                    + ", fechaNacimiento=" + finalFechaNacimientoStr
                    + ", contrasenaPresent=" + (finalContrasena != null)
                    + ", fotoBytes=" + (finalFotoBytes == null ? 0 : finalFotoBytes.length));

            // Validaciones mínimas (el sistema también valida)
            if (isEmpty(nickname) || isEmpty(nombre) || isEmpty(correo) || isEmpty(contrasena)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Campos obligatorios faltantes")));
                return;
            }
            if (!contrasena.equals(confirmarContrasena)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Las contraseñas no coinciden")));
                return;
            }

            LocalDate fechaNacimiento = null;
            if (!isEmpty(fechaNacimientoStr)) {
                try {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                } catch (DateTimeParseException ex) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Formato de fecha inválido (usar yyyy-MM-dd)")));
                    return;
                }
            }

            // Preparar DTFecha y TipoDoc para el sistema
            DTFecha dtFecha = null;
            if (fechaNacimiento != null) {
                dtFecha = new DTFecha(fechaNacimiento.getDayOfMonth(), fechaNacimiento.getMonthValue(), fechaNacimiento.getYear());
            }

            logica.DataTypes.TipoDoc tipoDocEnum = null;
            if (tipoDoc != null && !tipoDoc.isBlank()) {
                try {
                    tipoDocEnum = logica.DataTypes.TipoDoc.valueOf(tipoDoc);
                } catch (IllegalArgumentException e) {
                    LOG.info("No se pudo mapear tipoDocumento='" + tipoDoc + "' a enum TipoDoc. Se enviará null.");
                    tipoDocEnum = null;
                }
            }
            if (tipoDocEnum == null && userType.equalsIgnoreCase("cliente")) {
                LOG.info("Debe seleccionar un tipo de Documento válido.");
                throw new IllegalArgumentException("Tipo de Documento inválido");
            }

            // Llamada al sistema: envolver en log y capturar excepción para mostrar detalle en logs
            try {
                if ((userType.equalsIgnoreCase("cliente"))) {
                    sistema.altaCliente(nickname, nombre, correo, apellido, dtFecha, nacionalidad, tipoDocEnum, numeroDoc, fotoBytes, contrasena);
                }
                else if (userType.equalsIgnoreCase("aerolinea")) {
                    sistema.altaAerolinea(nickname, nombre, correo, descripcion, sitioWeb, fotoBytes, contrasena);
                }
            } catch (Exception ex) {
                // Log completo para diagnóstico
                LOG.log(Level.SEVERE, "Error al invocar sistema.altaCliente", ex);
                String msg = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
                if (msg.contains("existe") || msg.contains("ya existe") || msg.contains("duplicate") || msg.contains("duplic")) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario ya existe", "detail", ex.getMessage())));
                    return;
                }
                // devolver mensaje de excepción temporalmente para depuración (quitar en producción)
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor", "detail", ex.getMessage())));
                return;
            }

        } finally {
            out.flush();
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isEmpty(String s) { return s == null || s.isBlank(); }
    
    private Map<String, Object> extraerDatosUsuario(DTUsuario dtUsuario) {
        if (dtUsuario == null) return null;
        
        Map<String, Object> datos = new java.util.HashMap<>();
        
        // Datos básicos de usuario
        datos.put("nickname", dtUsuario.getNickname());
        datos.put("nombre", dtUsuario.getNombre());
        datos.put("correo", dtUsuario.getCorreo());
        
        // Convertir foto a base64 si existe
        if (dtUsuario.getFoto() != null) {
            try {
                String fotoBase64 = java.util.Base64.getEncoder().encodeToString(dtUsuario.getFoto());
                datos.put("foto", fotoBase64);
            } catch (Exception e) {
                LOG.warning("Error al convertir foto a base64: " + e.getMessage());
                datos.put("foto", null);
            }
        } else {
            datos.put("foto", null);
        }
        
        // Verificar tipo de usuario y extraer datos específicos
        try {
            if (dtUsuario instanceof DTCliente) {
                DTCliente cliente = (DTCliente) dtUsuario;
                datos.put("tipo", "cliente");
                datos.put("apellido", cliente.getApellido());
                datos.put("nacionalidad", cliente.getNacionalidad());
                datos.put("numeroDocumento", cliente.getNumeroDocumento());
                
                if (cliente.getTipoDocumento() != null) {
                    datos.put("tipoDocumento", cliente.getTipoDocumento().toString());
                }
                
                if (cliente.getFechaNacimiento() != null) {
                    DTFecha fecha = cliente.getFechaNacimiento();
                    Map<String, Integer> fechaMap = new java.util.HashMap<>();
                    fechaMap.put("dia", fecha.getDia());
                    fechaMap.put("mes", fecha.getMes());
                    fechaMap.put("ano", fecha.getAno());
                    datos.put("fechaNacimiento", fechaMap);
                }
                
            } else if (dtUsuario instanceof DTAerolinea) {
                DTAerolinea aerolinea = (DTAerolinea) dtUsuario;
                datos.put("tipo", "aerolinea");
                datos.put("descripcion", aerolinea.getDescripcion());
                datos.put("sitioWeb", aerolinea.getLinkSitioWeb());
            }
        } catch (Exception e) {
            LOG.warning("Error al extraer datos específicos del usuario: " + e.getMessage());
            datos.put("tipo", "desconocido");
        }
        
        return datos;
    }
}