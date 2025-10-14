
package com.volandouy.controller;

import logica.DataTypes.DTFecha;
import logica.clase.Sistema;

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
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/usuarios/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,    // 5MB
        maxRequestSize = 10 * 1024 * 1024) // 10MB
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());
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

            // Llamada al sistema: envolver en log y capturar excepción para mostrar detalle en logs
            try {
                LOG.info("Invocando sistema.altaCliente(...)");
                sistema.altaCliente(nickname, nombre, correo, apellido, dtFecha, nacionalidad, tipoDocEnum, numeroDoc, fotoBytes, contrasena);
                LOG.info("sistema.altaCliente() ejecutado sin lanzar excepción.");
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(objectMapper.writeValueAsString(Map.of("mensaje", "Cliente creado")));
                return;
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
}