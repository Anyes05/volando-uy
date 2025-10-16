package com.volandouy.controller;

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
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;
import logica.DataTypes.DTRutaVuelo;
import logica.clase.Sistema;

@WebServlet("/api/vuelos/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class VueloController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(VueloController.class.getName());

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
                LOG.severe("Sistema no inicializado.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                return;
            }

            // Parámetros (multipart/form-data)
            String rutaParam = trim(request.getParameter("rutasvuelo")); // puede ser id o nombre según front
            String rutaNombreParam = trim(request.getParameter("rutasvueloNombre")); // hidden con texto de la opción

            // Normalizar: si rutaParam es "undefined", vacío o nulo, usar rutaNombreParam
            String ruta = rutaParam;
            if (isEmpty(ruta) || "undefined".equalsIgnoreCase(ruta) || "null".equalsIgnoreCase(ruta)) {
                ruta = rutaNombreParam;
            }

            if (isEmpty(ruta)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Seleccione una ruta de vuelo")));
                return;
            }

            String nombre = trim(request.getParameter("nombre"));
            String duracionStr = trim(request.getParameter("duracion")); // nuevo campo HH:MM
            String fechaVueloStr = trim(request.getParameter("fechaVuelo"));
            String horaSalidaStr = trim(request.getParameter("horaSalida"));
            String fechaAltaStr = trim(request.getParameter("fechaAlta"));
            String cantidadTuristaParam = request.getParameter("cantidadAsientosTuristas");
            String cantidadEjecutivoParam = request.getParameter("cantidadAsientosEjecutivo");

            int cantidadTurista = (cantidadTuristaParam != null && !cantidadTuristaParam.isBlank())
                    ? Integer.parseInt(cantidadTuristaParam.trim()) : 0;
            int cantidadEjecutivo = (cantidadEjecutivoParam != null && !cantidadEjecutivoParam.isBlank())
                    ? Integer.parseInt(cantidadEjecutivoParam.trim()) : 0;

            // Foto
            byte[] fotoBytes = null;
            try {
                Part fotoPart = request.getPart("foto");
                if (fotoPart != null && fotoPart.getSize() > 0) {
                    try (InputStream is = fotoPart.getInputStream()) {
                        fotoBytes = is.readAllBytes();
                    }
                }
            } catch (IllegalStateException | ServletException ex) {
                LOG.info("No se pudo leer fotoPart: " + ex.getMessage());
                fotoBytes = null;
            }

            byte[] finalFotoBytes = fotoBytes;
            LOG.info(() -> "POST /api/vuelos recibido. nombre=" + nombre
                    + ", duracion=" + duracionStr
                    + ", duracion=" + fechaVueloStr
                    + ", fechaAlta=" + fechaAltaStr
                    + ", cantTurista=" + cantidadTurista + ", cantEjecutivo=" + cantidadEjecutivo
                    + ", fotoBytes=" + (finalFotoBytes == null ? 0 : finalFotoBytes.length));

            // Validaciones básicas
            if (isEmpty(nombre) || isEmpty(duracionStr) || cantidadTurista <= 0 || cantidadEjecutivo <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Campos obligatorios faltantes o inválidos")));
                return;
            }

            // Parsear duración HH:MM -> minutos totales
            DTHora duracion = null;
            int duracionTotalMinutos;
            try {
                duracionTotalMinutos = parseDurationToMinutes(duracionStr);
                duracion = new DTHora(0, duracionTotalMinutos);
                if (duracionTotalMinutos <= 0) throw new NumberFormatException("Duración inválida");
            } catch (NumberFormatException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Formato de duración inválido (usar HH:MM o minutos enteros)")));
                return;
            }

            // Fecha de alta opcional
            LocalDate fechaAlta = null;
            LocalDate fechaVuelo = null;
            if (!isEmpty(fechaAltaStr) && !isEmpty(fechaVueloStr)) {
                try {
                    fechaAlta = LocalDate.parse(fechaAltaStr);
                    fechaVuelo = LocalDate.parse(fechaVueloStr);
                    if (fechaAlta.isAfter(fechaVuelo)) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "La fecha de alta no puede ser posterior a la fecha del vuelo")));
                        return;
                    }
                } catch (DateTimeParseException ex) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Formato de fecha inválido (usar yyyy-MM-dd)")));
                    return;
                }

            }

            // Sesión y autorización (debe estar logueado y ser aerolínea)
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuarioLogueado") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "No autenticado")));
                return;
            }
            String nickname = (String) session.getAttribute("usuarioLogueado");
            String tipo = session.getAttribute("tipoUsuario") != null ? session.getAttribute("tipoUsuario").toString() : "desconocido";
            if (!"aerolinea".equalsIgnoreCase(tipo)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Operación permitida solo para aerolíneas")));
                return;
            }

            DTHora dt = new DTHora(0, duracionTotalMinutos);
            DTHora horaVuelo;
            try {
                String[] parts = horaSalidaStr.split(":");
                int h = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);
                if (m < 0 || m > 59 || h < 0 || h > 23) throw new NumberFormatException("Hora inválida");
                horaVuelo = new DTHora(h, m);
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Formato de hora inválido (usar HH:MM)")));
                return;
            }


            // Llamada a la lógica de negocio
            try {
                DTFecha dtFecha = null;
                if (fechaAlta != null) {
                    dtFecha = new DTFecha(fechaAlta.getDayOfMonth(), fechaAlta.getMonthValue(), fechaAlta.getYear());
                }
                DTFecha dtFechaVuelo = null;
                if (fechaVuelo != null) {
                    dtFechaVuelo = new DTFecha(fechaVuelo.getDayOfMonth(), fechaVuelo.getMonthValue(), fechaVuelo.getYear());
                }

                LOG.info("Invocando sistema.ingresarDatosVuelo(...)");
                sistema.seleccionarAerolinea(nickname);
                DTRutaVuelo rutaVuelo = sistema.seleccionarRutaVueloRet(ruta);
                sistema.ingresarDatosVuelo(
                        nombre,
                        dtFechaVuelo,
                        horaVuelo,
                        duracion,
                        cantidadTurista,
                        cantidadEjecutivo,
                        dtFecha,
                        rutaVuelo,
                        fotoBytes

                );

                sistema.darAltaVuelo();

                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(objectMapper.writeValueAsString(Map.of("mensaje", "Vuelo creado")));
                return;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al registrar vuelo", ex);
                String msg = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
                if (msg.contains("existe") || msg.contains("ya existe") || msg.contains("duplicate") || msg.contains("duplic")) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Vuelo ya existe", "detail", ex.getMessage())));
                    return;
                }
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor", "detail", ex.getMessage())));
                return;
            }

        } finally {
            out.flush();
        }
    }

    // Helpers
    private static int parseDurationToMinutes(String s) {
        if (s == null) throw new IllegalArgumentException("null");
        s = s.trim();
        // aceptar HH:MM
        if (s.matches("\\d{1,2}:\\d{2}")) {
            String[] parts = s.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            if (m < 0 || m > 59 || h < 0) throw new NumberFormatException("Duración inválida");
            return h * 60 + m;
        }
        // aceptar solo minutos como entero (fallback)
        if (s.matches("\\d+")) {
            return Integer.parseInt(s);
        }
        throw new NumberFormatException("Formato inválido");
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isEmpty(String s) { return s == null || s.isBlank(); }
}