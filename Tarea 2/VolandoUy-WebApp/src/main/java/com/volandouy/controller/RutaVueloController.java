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

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import logica.DataTypes.DTFecha;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.EstadoRutaVuelo;
import logica.clase.Sistema;

/**
 * Controlador para manejar las operaciones relacionadas con rutas de vuelo
 * Recibe multipart/form-data desde el formulario (incluye file)
 */
@WebServlet("/api/rutas/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,   // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class RutaVueloController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RutaVueloController.class.getName());

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Sistema sistema = Sistema.getInstance();

    /**
     * GET combinado:
     * - Si pathInfo == null o "/" : devuelve la lista filtrada (no confirmadas) para la aerolínea del session (igual que tu primer doGet).
     * - Si pathInfo == "/{nombre}" : scaffold que devuelve 404 (igual que tu segundo doGet).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo();
            String nicknameParam = request.getParameter("nick");

            if (pathInfo == null || pathInfo.equals("/")) {
                // validar sesión si corresponde (opcional)
                HttpSession session = request.getSession(false);
                String nickname = null;
                if (session != null && session.getAttribute("usuarioLogueado") != null) {
                    nickname = session.getAttribute("usuarioLogueado").toString();
                }

                if (pathInfo == null || pathInfo.equals("/")) {
                    nickname = nicknameParam;

                    if (nickname == null || nickname.isBlank()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Debe indicar una aerolínea")));
                        return;
                    }

                    List<DTRutaVuelo> rutas;
                    try {
                        rutas = sistema.seleccionarAerolineaRet(nickname);
                    } catch (Exception ex) {
                        LOG.log(Level.SEVERE, "Error obteniendo rutas", ex);
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Error obteniendo rutas", "detail", ex.getMessage())));
                        return;
                    }

                    List<Map<String, Object>> outList = new ArrayList<>();
                    for (DTRutaVuelo r : rutas) {
                        if (r.getEstado() == EstadoRutaVuelo.CONFIRMADA) {
                            outList.add(Map.of("nombre", r.getNombre()));
                        }
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(outList));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    try (PrintWriter errOut = response.getWriter()) {
                        errOut.print(objectMapper.writeValueAsString(Map.of("error", "Ruta no encontrada")));
                    }
                }
            }
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forzar UTF-8 antes de leer parámetros
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

            // --- Leer parámetros (multipart/form-data) ---
            String nombre = trim(request.getParameter("nombre"));
            String descripcionCorta = trim(request.getParameter("descripcionCorta"));
            String descripcion = trim(request.getParameter("descripcion"));
            String horaSalida = trim(request.getParameter("horaSalida"));
            String fechaAltaStr = trim(request.getParameter("fechaAlta"));

            // Convertir a float de forma segura SIN métodos auxiliares.
            String costoTuristaParam = request.getParameter("costoTurista");
            float costoTurista = (costoTuristaParam != null && !costoTuristaParam.isBlank())
                    ? Float.parseFloat(costoTuristaParam.trim())
                    : 0f;

            String costoEjecutivoParam = request.getParameter("costoEjecutivo");
            float costoEjecutivo = (costoEjecutivoParam != null && !costoEjecutivoParam.isBlank())
                    ? Float.parseFloat(costoEjecutivoParam.trim())
                    : 0f;

            String costoEquipajeParam = request.getParameter("costoEquipaje");
            float costoEquipaje = (costoEquipajeParam != null && !costoEquipajeParam.isBlank())
                    ? Float.parseFloat(costoEquipajeParam.trim())
                    : 0f;

            String ciudadOrigen = trim(request.getParameter("ciudadOrigen"));
            String ciudadDestino = trim(request.getParameter("ciudadDestino"));

            List<String> categorias = new ArrayList<>();
            // Primero intentamos parameterValues (select multiple)
            String[] catArray = request.getParameterValues("categorias");
            if (catArray != null && catArray.length > 0) {
                for (String c : catArray) if (c != null && !c.isBlank()) categorias.add(c.trim());
            } else {
                // Soporte para una cadena separada por comas
                String categoriasParam = trim(request.getParameter("categorias"));
                if (categoriasParam != null && !categoriasParam.isBlank()) {
                    String[] parts = categoriasParam.split("\\s*,\\s*");
                    for (String p : parts) if (!p.isBlank()) categorias.add(p.trim());
                }
            }

            // Foto (multipart)
            byte[] fotoBytes = null;
            try {
                Part fotoPart = request.getPart("foto");
                if (fotoPart != null && fotoPart.getSize() > 0) {
                    try (InputStream is = fotoPart.getInputStream()) {
                        fotoBytes = is.readAllBytes();
                    }
                }
            } catch (IllegalStateException | ServletException ex) {
                // si el request no es multipart o excede límites, fotoPart puede fallar -> manejarlo sin romper todo
                LOG.info("No se pudo leer fotoPart (posible no multipart o tamaño excedido): " + ex.getMessage());
                fotoBytes = null;
            }

            // LOG de depuración
            byte[] finalFotoBytes = fotoBytes;
            LOG.info(() -> "POST /api/rutas recibido. nombre=" + nombre
                    + ", horaSalida=" + horaSalida
                    + ", fechaAlta=" + fechaAltaStr
                    + ", costoTurista=" + costoTurista
                    + ", costoEjecutivo=" + costoEjecutivo
                    + ", costoEquipaje=" + costoEquipaje
                    + ", ciudadOrigen=" + ciudadOrigen
                    + ", ciudadDestino=" + ciudadDestino
                    + ", categorias=" + categorias
                    + ", fotoBytes=" + (finalFotoBytes == null ? 0 : finalFotoBytes.length));

            // --- Validaciones mínimas del servidor ---
            if (isEmpty(nombre) || isEmpty(descripcion) || isEmpty(horaSalida)
                    || costoTurista == 0f || costoEjecutivo == 0f
                    || isEmpty(ciudadOrigen) || isEmpty(ciudadDestino)) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Campos obligatorios faltantes")));
                return;
            }

            // Fecha de alta (opcional)
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
            // ahora, usar los datos del session para detectar si el usuario logueado es una aerolinea, en caso de serlo, permitir la operacion
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuarioLogueado") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "No autenticado")));
                return;
            }
            String nickname = (String) session.getAttribute("usuarioLogueado");


            // --- Llamada a la lógica (caja negra) ---
            try {
                // Preparar DTFecha si fechaAlta fue enviada
                DTFecha dtFecha = null;
                if (fechaAlta != null) {
                    dtFecha = new DTFecha(fechaAlta.getDayOfMonth(), fechaAlta.getMonthValue(), fechaAlta.getYear());
                }

                // Usamos los métodos que mostraste en tu código original:
                LOG.info("Invocando sistema.ingresarDatosRuta(...)");
                sistema.seleccionarAerolinea(nickname);
                sistema.ingresarDatosRuta(
                        nombre,
                        descripcion,
                        costoTurista,
                        costoEjecutivo,
                        costoEquipaje,
                        ciudadOrigen,
                        ciudadDestino,
                        dtFecha,
                        categorias,
                        fotoBytes
                );

                sistema.registrarRuta(); // o metodo equivalente en tu sistema

                LOG.info("sistema.registrarRuta() ejecutado sin lanzar excepción.");
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

    // Helpers
    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isEmpty(String s) { return s == null || s.isBlank(); }
}
