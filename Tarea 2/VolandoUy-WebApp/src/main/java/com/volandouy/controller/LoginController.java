package com.volandouy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import logica.clase.ISistema;
import logica.clase.Sistema;
import logica.DataTypes.DTUsuario;
import logica.DataTypes.DTCliente;
import logica.DataTypes.DTAerolinea;

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
    private final Sistema sistema = Sistema.getInstance();

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

            LOG.info("Intento de login para email: " + email);

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Email y contraseña son obligatorios")));
                return;
            }

            // Buscar usuario por correo
            List<DTUsuario> usuarios = sistema.consultarUsuarios();
            DTUsuario usuarioEncontrado = usuarios.stream()
                    .filter(u -> u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(email))
                    .findFirst()
                    .orElse(null);

            if (usuarioEncontrado == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Credenciales inválidas")));
                LOG.info("Usuario no encontrado para email: " + email);
                return;
            }

            // Verificar contraseña
            String storedPassword = usuarioEncontrado.getContrasena();
            if (storedPassword == null || !storedPassword.equals(password)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Credenciales inválidas")));
                LOG.info("Contraseña inválida para usuario: " + usuarioEncontrado.getNickname());
                return;
            }

            // Crear o reutilizar sesión
            HttpSession session = request.getSession(true);
            guardarDatosEnSesion(session, usuarioEncontrado);

            LOG.info("Usuario logueado exitosamente: " + usuarioEncontrado.getNickname());

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(Map.of(
                    "mensaje", "Login exitoso",
                    "nickname", session.getAttribute("usuarioLogueado"),
                    "nombre", session.getAttribute("nombreUsuario"),
                    "correo", session.getAttribute("correoUsuario"),
                    "foto", session.getAttribute("fotoUsuario"),
                    "tipoUsuario", session.getAttribute("tipoUsuario")
            )));

        } catch (Exception e) {
            LOG.severe("Error en login: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor", "detail", e.getMessage())));
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
            if (session != null) {
                session.invalidate();
                LOG.info("Sesión cerrada correctamente");
            }
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");
            return;
        }

        // Si ya hay sesión, devolver los datos del usuario (para restaurar sessionStorage)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            out.print(objectMapper.writeValueAsString(Map.of(
                    "sesionActiva", true,
                    "nickname", session.getAttribute("usuarioLogueado"),
                    "nombre", session.getAttribute("nombreUsuario"),
                    "correo", session.getAttribute("correoUsuario"),
                    "foto", session.getAttribute("fotoUsuario"),
                    "tipoUsuario", session.getAttribute("tipoUsuario")
            )));
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
            DTUsuario datosExtendidos = sistema.mostrarDatosUsuarioMod(usuario.getNickname());

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

        } catch (Exception e) {
            LOG.info("No se pudo determinar tipoUsuario: " + e.getMessage());
        }

        session.setAttribute("tipoUsuario", tipo);
        LOG.info("Tipo de usuario detectado y guardado en sesión: " + tipo);
    }

}
