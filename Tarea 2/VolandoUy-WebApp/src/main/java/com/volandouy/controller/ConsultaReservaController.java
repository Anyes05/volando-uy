package com.volandouy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dato.entidades.Reserva;
import logica.clase.Sistema;
import logica.DataTypes.*;
import logica.servicios.ReservaServicio;

/**
 * Controlador para manejar la consulta de reservas de vuelo
 * Implementa el caso de uso "Consulta de Reserva de vuelo"
 */
@WebServlet("/api/consulta-reservas/*")
public class ConsultaReservaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ConsultaReservaController.class.getName());

    private ObjectMapper objectMapper = new ObjectMapper();
    private Sistema sistema = Sistema.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO = " + pathInfo); // debug

        if (pathInfo != null && pathInfo.startsWith("/pdf/")) {
            String reservaId = pathInfo.substring("/pdf/".length()); // "/pdf/1" -> "1"
            handleGetPdfReserva(reservaId, request, response);
            return; // MUY IMPORTANTE: salir del método
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                handleGetInfoInicial(request, response, out);
            } else if (pathInfo.equals("/test")) {
                handleGetTest(request, response, out);
            } else if (pathInfo.equals("/aerolineas")) {
                handleGetAerolineas(request, response, out);
            } else if (pathInfo.equals("/reservas-checkin")) {
                handleGetReservasCheckin(request, response, out);
            } else if (pathInfo.startsWith("/rutas/")) {
                String aerolineaNickname = pathInfo.substring(7); // "/rutas/"
                handleGetRutasAerolinea(aerolineaNickname, request, response, out);
            } else if (pathInfo.startsWith("/vuelos/")) {
                String rutaNombre = pathInfo.substring(8); // "/vuelos/"
                handleGetVuelosRuta(rutaNombre, request, response, out);
            } else if (pathInfo.startsWith("/reservas-vuelo/")) {
                String vueloNombre = pathInfo.substring(16); // "/reservas-vuelo/"
                handleGetReservasVuelo(vueloNombre, request, response, out);
            } else if (pathInfo.startsWith("/reserva-cliente/")) {
                String vueloNombre = pathInfo.substring(17); // "/reserva-cliente/"
                handleGetReservaCliente(vueloNombre, request, response, out);
            } else if (pathInfo.startsWith("/detalle-reserva/")) {
                String reservaId = pathInfo.substring(17); // "/detalle-reserva/"
                handleGetDetalleReserva(reservaId, request, response, out);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Endpoint no encontrado\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error interno del servidor\"}");
        }

        out.flush();
    }

    private void handleGetPdfReserva(String reservaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(reservaId);
            Map<String, Object> det = buscarReservaPorId(id);

            if (det == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType("application/json");
                response.getWriter().print("{\"error\":\"Reserva no encontrada\"}");
                return;
            }

            // ========= 1) Extraer datos de la reserva =========
            String nickCliente   = String.valueOf(det.getOrDefault("nicknameCliente", "N/A"));
            String fechaReserva  = String.valueOf(det.getOrDefault("fechaReserva", "N/A"));

            Map vuelo = (Map) det.get("vuelo");
            String vueloNombre   = vuelo != null ? String.valueOf(vuelo.getOrDefault("nombre", "N/A"))       : "N/A";
            String vueloFecha    = vuelo != null ? String.valueOf(vuelo.getOrDefault("fechaVuelo", "N/A"))   : "N/A";
            String vueloHora     = vuelo != null ? String.valueOf(vuelo.getOrDefault("horaVuelo", "N/A"))    : "N/A";
            String vueloDuracion = vuelo != null ? String.valueOf(vuelo.getOrDefault("duracion", "N/A"))     : "N/A";

            Map ruta = (Map) det.get("ruta");
            String rutaNombre    = ruta != null ? String.valueOf(ruta.getOrDefault("nombre", "N/A"))         : "N/A";
            String rutaDesc      = ruta != null ? String.valueOf(ruta.getOrDefault("descripcion", "N/A"))    : "N/A";
            String ciudadOrigen  = ruta != null ? String.valueOf(ruta.getOrDefault("ciudadOrigen", "N/A"))   : "N/A";
            String ciudadDestino = ruta != null ? String.valueOf(ruta.getOrDefault("ciudadDestino", "N/A"))  : "N/A";

            Map aerolinea = (Map) det.get("aerolinea");
            String aeroNombre = aerolinea != null ? String.valueOf(aerolinea.getOrDefault("nombre", "N/A")) : "N/A";
            String aeroDesc   = aerolinea != null ? String.valueOf(aerolinea.getOrDefault("descripcion", "N/A")) : "N/A";

            String costoTexto = "N/A";
            Object costoObj = det.get("costoReserva");
            if (costoObj instanceof Map) {
                Object ct = ((Map) costoObj).get("costoTotal");
                if (ct != null) costoTexto = String.valueOf(ct);
            } else if (costoObj != null) {
                costoTexto = String.valueOf(costoObj);
            }

            java.util.List<String> pas = (java.util.List<String>) det.get("pasajeros");
            java.util.List<String> lineasPasajeros = new java.util.ArrayList<>();
            if (pas != null && !pas.isEmpty()) {
                for (String p : pas) {
                    lineasPasajeros.add("• " + p);
                }
            } else {
                lineasPasajeros.add("No hay pasajeros registrados");
            }

            String fechaGeneracion = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    .format(new java.util.Date());

            // ========= 2) Utilidad para escapar texto PDF (sin romper parentesis) =========
            java.util.function.Function<String, String> esc = (s) -> {
                if (s == null) s = "";
                return s.replace("\\", "\\\\")
                        .replace("(", "\\(")
                        .replace(")", "\\)");
            };

            // ========= 3) Construimos el contenido del stream =========
            StringBuilder content = new StringBuilder();

            // Header gris (ajustado para que no se corte)
            content.append("0.9 g\n");                 // gris claro
            content.append("40 720 530 60 re\n");     // x=40, y=720, width=530, height=60 (dentro de la pagina)
            content.append("f\n");
            content.append("0 g\n");                  // volver a negro

            // Barra azul fina bajo el header (VolandoUy style, tono aproximado)
            content.append("0.13 0.59 0.79 rg\n");    // color de relleno azul (aprox #2298CA)
            content.append("40 715 530 4 re\n");
            content.append("f\n");
            content.append("0 g\n");                  // negro otra vez

            content.append("BT\n");

            // Titulo grande en azul
            content.append("0.13 0.59 0.79 rg\n");    // texto azul
            content.append("/F1 18 Tf\n");
            content.append("60 760 Td\n");
            content.append("(").append(esc.apply("VolandoUy - Comprobante de Check In")).append(") Tj\n");

            // Subtitulo con fecha en gris oscuro
            content.append("0.3 g\n");
            content.append("0 -20 Td\n");
            content.append("/F1 10 Tf\n");
            content.append("(")
                    .append(esc.apply("Generado: " + fechaGeneracion))
                    .append(") Tj\n");

            // Volver a texto negro normal
            content.append("0 g\n");

            // Seccion: Datos generales de la reserva
            content.append("0 -40 Td\n");
            content.append("/F1 14 Tf\n");
            content.append("(").append(esc.apply("Informacion de la Reserva")).append(") Tj\n");

            content.append("0 -18 Td\n");
            content.append("/F1 11 Tf\n");
            content.append("(").append(esc.apply("ID de reserva: " + id)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Cliente: " + nickCliente)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Fecha de reserva: " + fechaReserva)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Costo total: " + costoTexto)).append(") Tj\n");

            // Separador visual
            content.append("0 -16 Td\n");
            content.append("(").append(esc.apply("----------------------------------------")).append(") Tj\n");

            // Seccion: Vuelo
            content.append("0 -24 Td\n");
            content.append("/F1 14 Tf\n");
            content.append("(").append(esc.apply("Informacion del Vuelo")).append(") Tj\n");

            content.append("0 -18 Td\n");
            content.append("/F1 11 Tf\n");
            content.append("(").append(esc.apply("Nombre de vuelo: " + vueloNombre)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Fecha: " + vueloFecha)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Hora: " + vueloHora)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Duracion: " + vueloDuracion)).append(") Tj\n");

            // Separador
            content.append("0 -16 Td\n");
            content.append("(").append(esc.apply("----------------------------------------")).append(") Tj\n");

            // Seccion: Ruta
            content.append("0 -24 Td\n");
            content.append("/F1 14 Tf\n");
            content.append("(").append(esc.apply("Informacion de la Ruta")).append(") Tj\n");

            content.append("0 -18 Td\n");
            content.append("/F1 11 Tf\n");
            content.append("(").append(esc.apply("Ruta: " + rutaNombre)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Descripcion: " + rutaDesc)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Origen: " + ciudadOrigen)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Destino: " + ciudadDestino)).append(") Tj\n");

            // Separador
            content.append("0 -16 Td\n");
            content.append("(").append(esc.apply("----------------------------------------")).append(") Tj\n");

            // Seccion: Aerolinea
            content.append("0 -24 Td\n");
            content.append("/F1 14 Tf\n");
            content.append("(").append(esc.apply("Informacion de la Aerolinea")).append(") Tj\n");

            content.append("0 -18 Td\n");
            content.append("/F1 11 Tf\n");
            content.append("(").append(esc.apply("Nombre: " + aeroNombre)).append(") Tj\n");

            content.append("0 -14 Td\n");
            content.append("(").append(esc.apply("Descripcion: " + aeroDesc)).append(") Tj\n");

            // Separador
            content.append("0 -16 Td\n");
            content.append("(").append(esc.apply("----------------------------------------")).append(") Tj\n");

            // Seccion: Pasajeros
            content.append("0 -24 Td\n");
            content.append("/F1 14 Tf\n");
            content.append("(").append(esc.apply("Pasajeros")).append(") Tj\n");

            content.append("0 -18 Td\n");
            content.append("/F1 11 Tf\n");

            for (String lineaPas : lineasPasajeros) {
                content.append("(").append(esc.apply(lineaPas)).append(") Tj\n");
                content.append("0 -14 Td\n");
            }

            content.append("ET\n");

            String contentStr = content.toString();

            // ========= 4) Armamos el PDF raw completo =========
            StringBuilder pdf = new StringBuilder();
            pdf.append("%PDF-1.4\n");

            // Objeto 1: Catalogo
            pdf.append("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n");

            // Objeto 2: Paginas
            pdf.append("2 0 obj << /Type /Pages /Count 1 /Kids [3 0 R] >> endobj\n");

            // Objeto 3: Pagina
            pdf.append("3 0 obj << /Type /Page /Parent 2 0 R ")
                    .append("/MediaBox [0 0 612 792] ")
                    .append("/Resources << /Font << /F1 5 0 R >> >> ")
                    .append("/Contents 4 0 R ")
                    .append(">> endobj\n");

            // Objeto 4: Contenido (stream)
            byte[] contentBytes = contentStr.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
            pdf.append("4 0 obj << /Length ").append(contentBytes.length).append(" >>\n");
            pdf.append("stream\n");
            pdf.append(contentStr);
            pdf.append("endstream\n");
            pdf.append("endobj\n");

            // Objeto 5: Fuente
            pdf.append("5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n");

            // xref / trailer simple (offsets dummy, pero los viewers lo aceptan)
            pdf.append("xref\n");
            pdf.append("0 6\n");
            pdf.append("0000000000 65535 f \n");
            pdf.append("0000000010 00000 n \n");
            pdf.append("0000000060 00000 n \n");
            pdf.append("0000000114 00000 n \n");
            pdf.append("0000000200 00000 n \n");
            pdf.append("0000000300 00000 n \n");
            pdf.append("trailer << /Root 1 0 R /Size 6 >>\n");
            pdf.append("startxref\n");
            pdf.append("400\n");
            pdf.append("%%EOF");

            // ========= 5) Enviar respuesta =========
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Checkin_" + id + ".pdf");
            response.getOutputStream().write(pdf.toString().getBytes(java.nio.charset.StandardCharsets.US_ASCII));
            response.getOutputStream().flush();

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().print("{\"error\":\"ID de reserva invalido\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().print("{\"error\":\"No se pudo generar el PDF\"}");
        }
    }





    /**
     * Obtiene información inicial según el tipo de usuario logueado
     */
    private void handleGetInfoInicial(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Iniciando handleGetInfoInicial");

            HttpSession session = request.getSession(false);
            if (session == null) {
                LOG.warning("Sesión no encontrada");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Usuario no autenticado\"}");
                return;
            }

            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            String usuarioLogueado = (String) session.getAttribute("usuarioLogueado");

            LOG.info("Usuario logueado: " + usuarioLogueado + ", Tipo: " + tipoUsuario);

            Map<String, Object> infoInicial = new HashMap<>();
            infoInicial.put("tipoUsuario", tipoUsuario);
            infoInicial.put("usuarioLogueado", usuarioLogueado);

            if ("aerolinea".equals(tipoUsuario)) {
                LOG.info("Procesando como aerolínea: " + usuarioLogueado);
                try {
                    // Verificar que el sistema esté inicializado
                    if (sistema == null) {
                        LOG.warning("Sistema no inicializado");
                        infoInicial.put("rutas", new ArrayList<>());
                        infoInicial.put("warning", "Sistema no inicializado - no se pueden cargar las rutas");
                    } else {
                        // Para aerolínea: mostrar sus rutas directamente
                        List<DTRutaVuelo> rutas = sistema.listarRutaVuelo(usuarioLogueado);
                        LOG.info("Rutas encontradas: " + (rutas != null ? rutas.size() : "null"));

                        List<Map<String, Object>> rutasSimplificadas = new ArrayList<>();
                        if (rutas != null) {
                            for (DTRutaVuelo ruta : rutas) {
                                Map<String, Object> rutaMap = new HashMap<>();
                                rutaMap.put("nombre", ruta.getNombre());
                                rutaMap.put("descripcion", ruta.getDescripcion());
                                if (ruta.getCiudadOrigen() != null) {
                                    rutaMap.put("ciudadOrigen", ruta.getCiudadOrigen().getNombre());
                                }
                                if (ruta.getCiudadDestino() != null) {
                                    rutaMap.put("ciudadDestino", ruta.getCiudadDestino().getNombre());
                                }
                                rutasSimplificadas.add(rutaMap);
                            }
                        }
                        infoInicial.put("rutas", rutasSimplificadas);
                    }
                } catch (Exception e) {
                    LOG.severe("Error al obtener rutas de aerolínea: " + e.getMessage());
                    e.printStackTrace();
                    infoInicial.put("rutas", new ArrayList<>());
                    infoInicial.put("error", "Error al cargar rutas: " + e.getMessage());
                }
            } else if ("cliente".equals(tipoUsuario)) {
                LOG.info("Procesando como cliente");
                try {
                    // Verificar que el sistema esté inicializado
                    if (sistema == null) {
                        LOG.warning("Sistema no inicializado");
                        infoInicial.put("aerolineas", new ArrayList<>());
                        infoInicial.put("warning", "Sistema no inicializado - no se pueden cargar las aerolíneas");
                    } else {
                        // Para cliente: mostrar aerolíneas disponibles
                        List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                        LOG.info("Aerolíneas encontradas: " + (aerolineas != null ? aerolineas.size() : "null"));

                        List<Map<String, Object>> aerolineasSimplificadas = new ArrayList<>();
                        if (aerolineas != null) {
                            for (DTAerolinea aerolinea : aerolineas) {
                                Map<String, Object> aerolineaMap = new HashMap<>();
                                aerolineaMap.put("nickname", aerolinea.getNickname());
                                aerolineaMap.put("nombre", aerolinea.getNombre());
                                aerolineaMap.put("descripcion", aerolinea.getDescripcion());
                                aerolineasSimplificadas.add(aerolineaMap);
                            }
                        }
                        infoInicial.put("aerolineas", aerolineasSimplificadas);
                    }
                } catch (Exception e) {
                    LOG.severe("Error al obtener aerolíneas: " + e.getMessage());
                    e.printStackTrace();
                    infoInicial.put("aerolineas", new ArrayList<>());
                    infoInicial.put("error", "Error al cargar aerolíneas: " + e.getMessage());
                }
            } else {
                LOG.warning("Tipo de usuario no reconocido: " + tipoUsuario);
                infoInicial.put("error", "Tipo de usuario no reconocido: " + tipoUsuario);
            }

            LOG.info("Enviando respuesta con información inicial");
            response.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = objectMapper.writeValueAsString(infoInicial);
            LOG.info("Respuesta JSON: " + jsonResponse);
            out.print(jsonResponse);

        } catch (Exception e) {
            LOG.severe("Error crítico en handleGetInfoInicial: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Endpoint de prueba para verificar que el controlador funciona
     */
    private void handleGetTest(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Endpoint de prueba llamado");

            Map<String, Object> testResponse = new HashMap<>();
            testResponse.put("status", "ok");
            testResponse.put("message", "Controlador funcionando correctamente");
            testResponse.put("timestamp", new java.util.Date().toString());

            HttpSession session = request.getSession(false);
            if (session != null) {
                testResponse.put("sessionExists", true);
                testResponse.put("usuarioLogueado", session.getAttribute("usuarioLogueado"));
                testResponse.put("tipoUsuario", session.getAttribute("tipoUsuario"));
            } else {
                testResponse.put("sessionExists", false);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(testResponse));

        } catch (Exception e) {
            LOG.severe("Error en endpoint de prueba: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error en endpoint de prueba: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Obtiene las aerolíneas disponibles (para clientes)
     */
    private void handleGetAerolineas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();

            List<Map<String, Object>> aerolineasSimplificadas = new ArrayList<>();
            for (DTAerolinea aerolinea : aerolineas) {
                Map<String, Object> aerolineaMap = new HashMap<>();
                aerolineaMap.put("nickname", aerolinea.getNickname());
                aerolineaMap.put("nombre", aerolinea.getNombre());
                aerolineaMap.put("descripcion", aerolinea.getDescripcion());
                aerolineasSimplificadas.add(aerolineaMap);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(aerolineasSimplificadas));

        } catch (Exception e) {
            LOG.severe("Error al obtener aerolíneas: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener aerolíneas\"}");
        }
    }

    /**
     * Obtiene las rutas de una aerolínea específica
     */
    private void handleGetRutasAerolinea(String aerolineaNickname, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Obteniendo rutas para aerolínea: " + aerolineaNickname);
            List<DTRutaVuelo> rutas = sistema.listarRutaVuelo(aerolineaNickname);
            LOG.info("Se encontraron " + rutas.size() + " rutas para la aerolínea " + aerolineaNickname);

            List<Map<String, Object>> rutasSimplificadas = new ArrayList<>();
            for (DTRutaVuelo ruta : rutas) {
                Map<String, Object> rutaMap = new HashMap<>();
                rutaMap.put("nombre", ruta.getNombre());
                rutaMap.put("descripcion", ruta.getDescripcion());
                rutaMap.put("ciudadOrigen", ruta.getCiudadOrigen().getNombre());
                rutaMap.put("ciudadDestino", ruta.getCiudadDestino().getNombre());
                rutaMap.put("estado", ruta.getEstado().toString());
                rutasSimplificadas.add(rutaMap);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = objectMapper.writeValueAsString(rutasSimplificadas);
            LOG.info("Respuesta JSON: " + jsonResponse);
            out.print(jsonResponse);

        } catch (Exception e) {
            LOG.severe("Error al obtener rutas de aerolínea " + aerolineaNickname + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener rutas de la aerolínea\"}");
        }
    }

    /**
     * Obtiene los vuelos de una ruta específica
     */
    private void handleGetVuelosRuta(String rutaNombre, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(rutaNombre);

            List<Map<String, Object>> vuelosSimplificados = new ArrayList<>();
            for (DTVuelo vuelo : vuelos) {
                Map<String, Object> vueloMap = new HashMap<>();
                vueloMap.put("nombre", vuelo.getNombre());
                vueloMap.put("fechaVuelo", vuelo.getFechaVuelo().toString());
                vueloMap.put("horaVuelo", vuelo.getHoraVuelo().toString());
                vueloMap.put("duracion", vuelo.getDuracion().toString());
                vueloMap.put("asientosMaxTurista", vuelo.getAsientosMaxTurista());
                vueloMap.put("asientosMaxEjecutivo", vuelo.getAsientosMaxEjecutivo());
                vueloMap.put("fechaAlta", vuelo.getFechaAlta().toString());
                vuelosSimplificados.add(vueloMap);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(vuelosSimplificados));

        } catch (Exception e) {
            LOG.severe("Error al obtener vuelos de ruta " + rutaNombre + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener vuelos de la ruta\"}");
        }
    }

    /**
     * Obtiene todas las reservas de un vuelo específico (para aerolíneas)
     */
    private void handleGetReservasVuelo(String vueloNombre, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTVueloReserva> reservasVuelo = sistema.listarReservasVuelo(vueloNombre);

            List<Map<String, Object>> reservasSimplificadas = new ArrayList<>();
            for (DTVueloReserva vueloReserva : reservasVuelo) {
                Map<String, Object> reservaMap = new HashMap<>();
                reservaMap.put("id", vueloReserva.getReserva().getId());
                reservaMap.put("nicknameCliente", vueloReserva.getReserva().getNickname());
                reservaMap.put("fechaReserva", vueloReserva.getReserva().getFechaReserva().toString());
                reservaMap.put("costoReserva", vueloReserva.getReserva().getCostoReserva());
                reservaMap.put("vueloNombre", vueloReserva.getVuelo().getNombre());
                reservaMap.put("fechaVuelo", vueloReserva.getVuelo().getFechaVuelo().toString());
                reservaMap.put("horaVuelo", vueloReserva.getVuelo().getHoraVuelo().toString());
                reservasSimplificadas.add(reservaMap);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(reservasSimplificadas));

        } catch (Exception e) {
            LOG.severe("Error al obtener reservas del vuelo " + vueloNombre + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener reservas del vuelo\"}");
        }
    }

    /**
     * Obtiene la reserva del cliente autenticado para un vuelo específico
     */
    private void handleGetReservaCliente(String vueloNombre, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Usuario no autenticado\"}");
                return;
            }

            String nicknameCliente = (String) session.getAttribute("usuarioLogueado");
            if (nicknameCliente == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Usuario no autenticado\"}");
                return;
            }

            // Buscar la reserva del cliente para este vuelo
            List<DTVueloReserva> reservasVuelo = sistema.listarReservasVuelo(vueloNombre);
            Map<String, Object> reservaCliente = null;

            for (DTVueloReserva vueloReserva : reservasVuelo) {
                if (vueloReserva.getReserva().getNickname().equals(nicknameCliente)) {
                    reservaCliente = new HashMap<>();
                    reservaCliente.put("id", vueloReserva.getReserva().getId());
                    reservaCliente.put("nicknameCliente", vueloReserva.getReserva().getNickname());
                    reservaCliente.put("fechaReserva", vueloReserva.getReserva().getFechaReserva().toString());
                    reservaCliente.put("costoReserva", vueloReserva.getReserva().getCostoReserva());
                    reservaCliente.put("vueloNombre", vueloReserva.getVuelo().getNombre());
                    reservaCliente.put("fechaVuelo", vueloReserva.getVuelo().getFechaVuelo().toString());
                    reservaCliente.put("horaVuelo", vueloReserva.getVuelo().getHoraVuelo().toString());
                    break;
                }
            }

            if (reservaCliente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"No se encontró reserva del cliente para este vuelo\"}");
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(reservaCliente));

        } catch (Exception e) {
            LOG.severe("Error al obtener reserva del cliente para vuelo " + vueloNombre + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener reserva del cliente\"}");
        }
    }

    /**
     * Obtiene el detalle completo de una reserva específica
     */
    private void handleGetDetalleReserva(String reservaId, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            // Buscar la reserva por ID en todas las aerolíneas y vuelos
            Long id = Long.parseLong(reservaId);
            Map<String, Object> detalleReserva = buscarReservaPorId(id);

            if (detalleReserva == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Reserva no encontrada\"}");
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(detalleReserva));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"ID de reserva inválido\"}");
        } catch (Exception e) {
            LOG.severe("Error al obtener detalle de reserva " + reservaId + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener detalle de la reserva\"}");
        }
    }

    /**
     * Busca una reserva por ID en todas las aerolíneas y vuelos
     */
    private Map<String, Object> buscarReservaPorId(Long reservaId) {
        try {
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();

            for (DTAerolinea aerolinea : aerolineas) {
                List<DTRutaVuelo> rutas = sistema.listarRutaVuelo(aerolinea.getNickname());
                for (DTRutaVuelo ruta : rutas) {
                    List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(ruta.getNombre());
                    for (DTVuelo vuelo : vuelos) {
                        List<DTVueloReserva> reservasVuelo = sistema.listarReservasVuelo(vuelo.getNombre());
                        for (DTVueloReserva vueloReserva : reservasVuelo) {
                            if (vueloReserva.getReserva().getId().equals(reservaId)) {
                                // Encontramos la reserva, crear detalle completo
                                Map<String, Object> detalle = new HashMap<>();
                                detalle.put("id", vueloReserva.getReserva().getId());
                                detalle.put("nicknameCliente", vueloReserva.getReserva().getNickname());
                                detalle.put("fechaReserva", vueloReserva.getReserva().getFechaReserva().toString());
                                detalle.put("costoReserva", vueloReserva.getReserva().getCostoReserva());

                                // Información del vuelo
                                Map<String, Object> vueloInfo = new HashMap<>();
                                vueloInfo.put("nombre", vueloReserva.getVuelo().getNombre());
                                vueloInfo.put("fechaVuelo", vueloReserva.getVuelo().getFechaVuelo().toString());
                                vueloInfo.put("horaVuelo", vueloReserva.getVuelo().getHoraVuelo().toString());
                                vueloInfo.put("duracion", vueloReserva.getVuelo().getDuracion().toString());
                                detalle.put("vuelo", vueloInfo);

                                // Información de la ruta
                                Map<String, Object> rutaInfo = new HashMap<>();
                                rutaInfo.put("nombre", ruta.getNombre());
                                rutaInfo.put("descripcion", ruta.getDescripcion());
                                rutaInfo.put("ciudadOrigen", ruta.getCiudadOrigen().getNombre());
                                rutaInfo.put("ciudadDestino", ruta.getCiudadDestino().getNombre());
                                detalle.put("ruta", rutaInfo);

                                // Información de la aerolínea
                                Map<String, Object> aerolineaInfo = new HashMap<>();
                                aerolineaInfo.put("nickname", aerolinea.getNickname());
                                aerolineaInfo.put("nombre", aerolinea.getNombre());
                                aerolineaInfo.put("descripcion", aerolinea.getDescripcion());
                                detalle.put("aerolinea", aerolineaInfo);

                                // Información de pasajeros
                                List<String> nombresPasajeros = new ArrayList<>();
                                try {
                                    // Buscar la reserva real en la base de datos usando el ID
                                    ReservaServicio reservaServicio = new ReservaServicio();
                                    dato.entidades.Reserva reservaReal = reservaServicio.buscarPorId(vueloReserva.getReserva().getId());
                                    if (reservaReal != null && reservaReal.getPasajeros() != null) {
                                        for (dato.entidades.Pasaje pasaje : reservaReal.getPasajeros()) {
                                            String nombreCompleto = pasaje.getNombrePasajero() + " " + pasaje.getApellidoPasajero();
                                            nombresPasajeros.add(nombreCompleto);
                                        }
                                    }
                                } catch (Exception e) {
                                    LOG.warning("Error al obtener pasajeros de la reserva " + vueloReserva.getReserva().getId() + ": " + e.getMessage());
                                }
                                detalle.put("pasajeros", nombresPasajeros);

                                return detalle;
                            }
                        }
                    }
                }
            }

            return null; // No encontrada

        } catch (Exception e) {
            LOG.warning("Error al buscar reserva por ID: " + e.getMessage());
            return null;
        }
    }

    // ================================
    // NUEVO: Reservas con check-in del cliente
    // ================================
    private void handleGetReservasCheckin(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Obteniendo reservas con check-in para cliente...");

            HttpSession session = request.getSession(false);
            if (session == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Usuario no autenticado\"}");
                return;
            }

            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            String nicknameCliente = (String) session.getAttribute("usuarioLogueado");

            if (nicknameCliente == null || !"cliente".equalsIgnoreCase(tipoUsuario)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print("{\"error\": \"Solo un cliente puede consultar sus reservas con check-in\"}");
                return;
            }

            List<Reserva> reservas = sistema.listarReservasCheck(nicknameCliente);
            LOG.info("Llamando a sistema.listarDTReservasCheck para cliente: " + nicknameCliente);
            List<DTReserva> reservasCheck = sistema.listarDTReservasCheck(reservas);

            if (reservasCheck == null) {
                reservasCheck = new ArrayList<>();
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(reservasCheck));

        } catch (Exception e) {
            LOG.severe("Error al obtener reservas con check-in del cliente: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener reservas con check-in\"}");
        }
    }
}
