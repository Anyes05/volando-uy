package com.volandouy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import logica.DataTypes.DTFecha;
import logica.clase.Sistema;

/**
 * Controlador para manejar las operaciones relacionadas con rutas de vuelo
 */
@WebServlet("/api/rutas/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,    // 5MB
        maxRequestSize = 10 * 1024 * 1024) // 10MB

public class RutaVueloController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RutaVueloController.class.getName());

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

            // Campos esperados
            String nombre = null;
            String descripcionCorta = null;
            String descripcion = null;
            String horaSalida = null; // como string HH:mm
            String fechaAltaStr = null; // yyyy-MM-dd
            float costoTurista = 0;
            float costoEjecutivo = 0;
            float costoEquipaje = 0;
            String ciudadOrigen = null;
            String ciudadDestino = null;
            List<String> categorias = new ArrayList<>();
            byte[] fotoBytes = null;

            if (contentType.contains("application/json")) {
                Map<String, Object> body = objectMapper.readValue(request.getInputStream(), Map.class);
                nombre = trim((String) body.get("nombre"));
                descripcionCorta = trim((String) body.get("descripcionCorta"));
                descripcion = trim((String) body.get("descripcion"));
                horaSalida = trim((String) body.get("horaSalida"));
                fechaAltaStr = trim((String) body.get("fechaAlta"));
                costoTurista = body.get("costoTurista") instanceof Number ? ((Number) body.get("costoTurista")).floatValue() : 0;
                costoEjecutivo = body.get("costoEjecutivo") instanceof Number ? ((Number) body.get("costoEjecutivo")).floatValue() : 0;
                costoEquipaje = body.get("costoEquipaje") instanceof Number ? ((Number) body.get("costoEquipaje")).floatValue() : 0;
                ciudadOrigen = trim((String) body.get("ciudadOrigen"));
                ciudadDestino = trim((String) body.get("ciudadDestino"));

                Object catObj = body.get("categorias");
                if (catObj instanceof List) {
                    for (Object o : (List<?>) catObj) {
                        if (o != null) categorias.add(o.toString());
                    }
                } else if (catObj instanceof String) {
                    String s = ((String) catObj).trim();
                    if (!s.isEmpty()) {
                        String[] parts = s.split("\\s*,\\s*");
                        for (String p : parts) categorias.add(p);
                    }
                }

                // Si el JSON incluye la foto como base64, no está manejado aquí.
                // Para envío de archivos use multipart/form-data.
            } else {
                // form-data / urlencoded / multipart
                nombre = trim(request.getParameter("nombre"));
                descripcionCorta = trim(request.getParameter("descripcionCorta"));
                descripcion = trim(request.getParameter("descripcion"));
                horaSalida = trim(request.getParameter("horaSalida"));
                fechaAltaStr = trim(request.getParameter("fechaAlta"));
                costoTurista = (request.getParameter("costoTurista") != null && !request.getParameter("costoTurista").isBlank()) ? Float.parseFloat(request.getParameter("costoTurista")) : 0f;
                costoEjecutivo = (request.getParameter("costoEjecutivo") != null && !request.getParameter("costoEjecutivo").isBlank()) ? Float.parseFloat(request.getParameter("costoEjecutivo")) : 0f;
                costoEquipaje = (request.getParameter("costoEquipaje") != null && !request.getParameter("costoEquipaje").isBlank()) ? Float.parseFloat(request.getParameter("costoEquipaje")) : 0f;
                ciudadOrigen = trim(request.getParameter("ciudadOrigen"));
                ciudadDestino = trim(request.getParameter("ciudadDestino"));

                String categoriasParam = trim(request.getParameter("categorias"));
                if (categoriasParam != null && !categoriasParam.isEmpty()) {
                    String[] parts = categoriasParam.split("\\s*,\\s*");
                    for (String p : parts) categorias.add(p);
                } else {
                    // soporte para múltiples inputs con mismo name (select multiple)
                    String[] catArray = request.getParameterValues("categorias");
                    if (catArray != null) {
                        for (String c : catArray) if (c != null && !c.isBlank()) categorias.add(c);
                    }
                }

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

            // Validaciones mínimas
            if (isEmpty(nombre) || isEmpty(descripcion) || isEmpty(horaSalida) || costoTurista == 0 || costoEjecutivo == 0 || isEmpty(ciudadOrigen) || isEmpty(ciudadDestino)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Campos obligatorios faltantes")));
                return;
            }

            LocalDate fechaAlta = null;
            if (!isEmpty(fechaAltaStr)) {
                try {
                    fechaAlta = LocalDate.parse(fechaAltaStr);
                } catch (DateTimeParseException ex) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Formato de fecha inválido (usar yyyy-MM-dd)")));
                    return;
                }
            }

            // Llamada al sistema - adaptar la firma si es necesario
            try {
                LOG.info("Invocando sistema.altaRutaVuelo(...)");
                sistema.ingresarDatosRuta(nombre, descripcion, costoTurista, costoEjecutivo, costoEquipaje, ciudadOrigen, ciudadDestino, new DTFecha(fechaAlta.getDayOfMonth(), fechaAlta.getMonthValue(), fechaAlta.getYear()), categorias, fotoBytes);
                sistema.registrarRuta();
                LOG.info("sistema.altaRutaVuelo() ejecutado sin lanzar excepción.");
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(objectMapper.writeValueAsString(Map.of("mensaje", "Ruta creada")));
                return;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al invocar sistema.altaRutaVuelo", ex);
                String msg = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
                if (msg.contains("existe") || msg.contains("ya existe") || msg.contains("duplicate") || msg.contains("duplic")) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Ruta ya existe", "detail", ex.getMessage())));
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

    // Ejemplo / scaffold de doGet para listar/consultar rutas usando Sistema.
    // Mantener o reemplazar según la implementación real del sistema.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo(); // / o /{nombre}
            if (pathInfo == null || pathInfo.equals("/")) {
                // TODO: reemplazar por llamada real a sistema para listar rutas
                // Ejemplo: List<DTRuta> lista = sistema.listarRutas();
                out.print(objectMapper.writeValueAsString(List.of())); // placeholder vacío
                return;
            } else {
                String nombre = pathInfo.substring(1);
                // TODO: usar sistema.consultaRutaVuelo(nombre) si existe
                // Ejemplo: DTRuta dto = sistema.consultaRutaVuelo(nombre);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Ruta no encontrada (scaffold)")));
                return;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor")));
            LOG.log(Level.SEVERE, "Error en GET /api/rutas", e);
        } finally {
            out.flush();
        }
    }

    // Helpers
    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isEmpty(String s) { return s == null || s.isBlank(); }

    private static Double parseDouble(Object o) {
        if (o == null) return null;
        if (o instanceof Number) return ((Number) o).doubleValue();
        try {
            return Double.parseDouble(o.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private static Double parseDouble(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
//    private void cargarRutas() {
//        try {
//            InputStream inputStream = getServletContext().getResourceAsStream("/json/rutasVuelo.json");
//            if (inputStream != null) {
//                rutas = objectMapper.readValue(inputStream, new TypeReference<List<Object>>() {});
//                inputStream.close();
//            } else {
//                rutas = new java.util.ArrayList<>();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            rutas = new java.util.ArrayList<>();
//        }
//    }
}


