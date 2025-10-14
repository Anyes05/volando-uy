
package com.volandouy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

            // Buscar usuario por correo en la lista de usuarios
            List<DTUsuario> usuarios = sistema.consultarUsuarios();
            DTUsuario usuarioEncontrado = null;

            for (DTUsuario usuario : usuarios) {
                if (usuario.getCorreo() != null && usuario.getCorreo().equalsIgnoreCase(email)) {
                    usuarioEncontrado = usuario;
                    break;
                }
            }

            if (usuarioEncontrado == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Credenciales inválidas")));
                LOG.info("Usuario no encontrado para email: " + email);
                return;
            }

            // Verificar contraseña
            // NOTA: DTUsuario debe tener un método getContrasena() o getPassword()
            // Si no lo tiene, necesitarás agregarlo en el Servidor Central
            boolean passwordValida = false;
            try {
                String storedPassword = usuarioEncontrado.getContrasena();
                passwordValida = (storedPassword != null && storedPassword.equals(password));
            } catch (Exception e) {
                LOG.warning("No se pudo verificar contraseña. Asegúrate que DTUsuario tenga getContrasena(): " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Error de configuración del sistema")));
                return;
            }

            if (!passwordValida) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Credenciales inválidas")));
                LOG.info("Contraseña inválida para usuario: " + usuarioEncontrado.getNickname());
                return;
            }

            // Crear sesión HTTP
            HttpSession session = request.getSession(true);
            session.setAttribute("usuarioLogueado", usuarioEncontrado.getNickname());
            session.setAttribute("nombreUsuario", usuarioEncontrado.getNombre());
            session.setAttribute("correoUsuario", usuarioEncontrado.getCorreo());

            // Si el usuario tiene foto, convertirla a Base64

            // Guardar tipo de usuario (cliente o aerolínea)
            if (usuarioEncontrado instanceof DTCliente) {
                session.setAttribute("tipoUsuario", "cliente");
            } else if (usuarioEncontrado instanceof DTAerolinea) {
                session.setAttribute("tipoUsuario", "aerolinea");
            }

            LOG.info("Usuario logueado exitosamente: " + usuarioEncontrado.getNickname());

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(Map.of(
                    "mensaje", "Login exitoso",
                    "nickname", usuarioEncontrado.getNickname(),
                    "nombre", usuarioEncontrado.getNombre()
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
    // ==========================
    // LOGOUT
    // ==========================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                LOG.info("Sesión cerrada correctamente");
            }

            // Redirigir al inicio o login
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        } else {
            // Si llaman a /login por GET, redirigir al formulario
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        }
    }
}

