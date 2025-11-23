package com.volandouy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volandouy.central.CentralService;
import com.volandouy.central.ServiceFactory;
import logica.DataTypes.DTUsuario;
import logica.DataTypes.DTCliente;
import logica.DataTypes.DTAerolinea;

import com.volandouy.helper.DeviceDetector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CentralService centralService;
    
    private CentralService getCentralService() {
        if (centralService == null) {
            try {
                centralService = ServiceFactory.getCentralService();
            } catch (Exception e) {
                LOG.severe("Error al obtener CentralService: " + e.getMessage());
                throw new RuntimeException("No se pudo conectar al Servidor Central. " +
                        "Asegúrate de que el Servidor Central esté ejecutándose.", e);
            }
        }
        return centralService;
    }

    // LOGIN
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Email y contraseña son obligatorios")));
                return;
            }

            // Buscar usuario por correo o nickname
            List<DTUsuario> usuarios = getCentralService().consultarUsuarios();
            DTUsuario usuarioEncontrado = usuarios.stream()
                    .filter(u -> {
                        // Buscar por correo
                        if (u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(email)) {
                            return true;
                        }
                        // Buscar por nickname
                        if (u.getNickname() != null && u.getNickname().equalsIgnoreCase(email)) {
                            return true;
                        }
                        return false;
                    })
                    .findFirst()
                    .orElse(null);

            if (usuarioEncontrado == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Credenciales inválidas")));
                return;
            }

            // Verificar contraseña
            String storedPassword = usuarioEncontrado.getContrasena();
            if (storedPassword == null || !storedPassword.equals(password)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Credenciales inválidas")));
                return;
            }

            // Verificar si es móvil y si el usuario es aerolínea (no permitido en móvil)
            boolean isMobilePhone = DeviceDetector.isMobilePhone(request);
            if (isMobilePhone) {
                // Determinar tipo de usuario antes de guardar en sesión
                String tipoUsuario = determinarTipoUsuario(usuarioEncontrado);
                if ("aerolinea".equals(tipoUsuario)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Las aerolíneas no pueden iniciar sesión desde dispositivos móviles. Por favor, use la versión de escritorio.")));
                    return;
                }
            }

            // Crear o reutilizar sesión
            HttpSession session = request.getSession(true);
            guardarDatosEnSesion(session, usuarioEncontrado);

            LOG.info("Usuario logueado exitosamente: " + usuarioEncontrado.getNickname());

            response.setStatus(HttpServletResponse.SC_OK);
            
            // Crear Map manualmente para manejar valores null
            Map<String, Object> responseData = new java.util.HashMap<>();
            responseData.put("mensaje", "Login exitoso");
            responseData.put("nickname", session.getAttribute("usuarioLogueado"));
            responseData.put("nombre", session.getAttribute("nombreUsuario"));
            responseData.put("correo", session.getAttribute("correoUsuario"));
            responseData.put("foto", session.getAttribute("fotoUsuario"));
            responseData.put("tipoUsuario", session.getAttribute("tipoUsuario"));
            
            out.print(objectMapper.writeValueAsString(responseData));

        } catch (Exception e) {
            LOG.severe("Error en login: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            // Mensaje más descriptivo para el usuario
            String mensajeError = e.getMessage();
            if (mensajeError != null && (mensajeError.contains("Connection") || 
                    mensajeError.contains("connect") || mensajeError.contains("refused") ||
                    mensajeError.contains("Servidor Central"))) {
                mensajeError = "No se pudo conectar al Servidor Central. " +
                        "Asegúrate de que el Servidor Central esté ejecutándose.";
            } else {
                mensajeError = "Error interno del servidor: " + (mensajeError != null ? mensajeError : e.getClass().getSimpleName());
            }
            
            out.print(objectMapper.writeValueAsString(Map.of("error", mensajeError, "detail", e.getClass().getSimpleName())));
        } finally {
            out.flush();
        }
    }

    // 🔹 NUEVO: si hay sesión previa, devuelve los datos del usuario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);

        if ("/logout".equals(path)) {
            // Detectar si es móvil para redirigir correctamente
            boolean isMobilePhone = DeviceDetector.isMobilePhone(request);
            if (session != null) {
                session.invalidate();
                LOG.info("Sesión cerrada correctamente");
            }
            // En móvil, redirigir a inicio de sesión; en desktop, a inicio
            String redirectPath = isMobilePhone ? "/inicioSesion.jsp" : "/inicio.jsp";
            response.sendRedirect(request.getContextPath() + redirectPath);
            return;
        }

        // Si ya hay sesión, devolver los datos del usuario (para restaurar sessionStorage)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            // Crear Map manualmente para manejar valores null
            Map<String, Object> responseData = new java.util.HashMap<>();
            responseData.put("sesionActiva", true);
            responseData.put("nickname", session.getAttribute("usuarioLogueado"));
            responseData.put("nombre", session.getAttribute("nombreUsuario"));
            responseData.put("correo", session.getAttribute("correoUsuario"));
            responseData.put("foto", session.getAttribute("fotoUsuario"));
            responseData.put("tipoUsuario", session.getAttribute("tipoUsuario"));
            
            out.print(objectMapper.writeValueAsString(responseData));
        } else {
            out.print(objectMapper.writeValueAsString(Map.of("sesionActiva", false)));
        }
        out.flush();
    }


    private void guardarDatosEnSesion(HttpSession session, DTUsuario usuario) {
        session.setAttribute("usuarioLogueado", usuario.getNickname());
        session.setAttribute("nombreUsuario", usuario.getNombre());
        session.setAttribute("correoUsuario", usuario.getCorreo());

        // Guardar foto en Base64 si existe
        String fotoBase64 = null;
        try {
            byte[] fotoBytes = usuario.getFoto();
            if (fotoBytes != null && fotoBytes.length > 0) {
                fotoBase64 = Base64.getEncoder().encodeToString(fotoBytes);
            }
        } catch (Exception e) {
            LOG.info("No se pudo obtener foto del usuario: " + e.getMessage());
        }
        session.setAttribute("fotoUsuario", fotoBase64);

        // ---------------------------------------
        // Detección del tipo de usuario SIN atributo "tipo"
        // ---------------------------------------
        String tipo = "desconocido";
        try {
            // Primero: intentar obtener datos extendidos desde el sistema
            DTUsuario datosExtendidos = getCentralService().mostrarDatosUsuarioMod(usuario.getNickname());
            
            if (datosExtendidos != null) {
                // Si devuelve un objeto con apellido => cliente
                try {
                    String apellido = (String) datosExtendidos.getClass()
                            .getMethod("getApellido")
                            .invoke(datosExtendidos);
                    if (apellido != null) tipo = "cliente";
                } catch (NoSuchMethodException ignored) {}

                // Si devuelve un objeto con descripción => aerolínea
                try {
                    String descripcion = (String) datosExtendidos.getClass()
                            .getMethod("getDescripcion")
                            .invoke(datosExtendidos);
                    if (descripcion != null) tipo = "aerolinea";
                } catch (NoSuchMethodException ignored) {}
            } else {
                LOG.warning("mostrarDatosUsuarioMod devolvió null para usuario: " + usuario.getNickname());
            }

        } catch (Exception e) {
            LOG.warning("Error al determinar tipoUsuario para " + usuario.getNickname() + ": " + e.getMessage());
            // Intentar determinar el tipo usando instanceof como fallback
            try {
                if (usuario instanceof DTCliente) {
                    tipo = "cliente";
                } else if (usuario instanceof DTAerolinea) {
                    tipo = "aerolinea";
                }
            } catch (Exception fallbackError) {
                LOG.warning("Error en fallback de tipoUsuario: " + fallbackError.getMessage());
            }
        }

        session.setAttribute("tipoUsuario", tipo);
        LOG.info("Tipo de usuario detectado y guardado en sesión: " + tipo);
    }

    /**
     * Determina el tipo de usuario sin guardar en sesión
     * (usado para validar antes del login)
     */
    private String determinarTipoUsuario(DTUsuario usuario) {
        String tipo = "desconocido";
        try {
            // Intentar obtener datos extendidos desde el sistema
            DTUsuario datosExtendidos = getCentralService().mostrarDatosUsuarioMod(usuario.getNickname());
            
            if (datosExtendidos != null) {
                // Si devuelve un objeto con apellido => cliente
                try {
                    String apellido = (String) datosExtendidos.getClass()
                            .getMethod("getApellido")
                            .invoke(datosExtendidos);
                    if (apellido != null) tipo = "cliente";
                } catch (NoSuchMethodException ignored) {}

                // Si devuelve un objeto con descripción => aerolínea
                try {
                    String descripcion = (String) datosExtendidos.getClass()
                            .getMethod("getDescripcion")
                            .invoke(datosExtendidos);
                    if (descripcion != null) tipo = "aerolinea";
                } catch (NoSuchMethodException ignored) {}
            }

        } catch (Exception e) {
            LOG.warning("Error al determinar tipoUsuario para " + usuario.getNickname() + ": " + e.getMessage());
            // Intentar determinar el tipo usando instanceof como fallback
            try {
                if (usuario instanceof DTCliente) {
                    tipo = "cliente";
                } else if (usuario instanceof DTAerolinea) {
                    tipo = "aerolinea";
                }
            } catch (Exception fallbackError) {
                LOG.warning("Error en fallback de tipoUsuario: " + fallbackError.getMessage());
            }
        }
        return tipo;
    }

}
