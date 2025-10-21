package com.volandouy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTVuelo;
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
     * GET endpoints:
     * - Si pathInfo == null o "/" : devuelve la lista de rutas confirmadas para la aerolínea especificada
     * - Si pathInfo == "/{nombre}" : devuelve los detalles de una ruta específica
     * - Si pathInfo == "/{nombre}/vuelos" : devuelve los vuelos de una ruta específica
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo(); // / o /{nombre} o /{nombre}/vuelos
            String nicknameParam = request.getParameter("aerolinea");
            
            LOG.info("PathInfo recibido: " + pathInfo);
            LOG.info("Nickname param: " + nicknameParam);

            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/rutas - Lista rutas confirmadas de una aerolínea o todas las rutas
                LOG.info("Manejando lista de rutas");
                if (nicknameParam == null || nicknameParam.trim().isEmpty()) {
                    // Si no se especifica aerolínea, devolver todas las rutas
                    handleGetTodasLasRutas(request, response, out);
                } else {
                    // Si se especifica aerolínea, devolver solo las de esa aerolínea
                    handleGetRutas(nicknameParam, request, response, out);
                }
                return;
            } else if (pathInfo.equals("/test")) {
                // Endpoint de prueba
                LOG.info("Endpoint de prueba llamado");
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(Map.of("mensaje", "RutaVueloController funcionando correctamente")));
                out.flush();
                return;
            } else if (pathInfo.equals("/test-aerolinea")) {
                // Endpoint de prueba para una aerolínea específica
                String testNickname = request.getParameter("nickname");
                if (testNickname == null || testNickname.trim().isEmpty()) {
                    testNickname = "pluna"; // Default para prueba
                }
                
                LOG.info("Probando aerolínea: " + testNickname);
                try {
                    if (sistema == null) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                        return;
                    }
                    
                    List<DTRutaVuelo> rutas = sistema.listarRutaVuelo(testNickname);
                    Map<String, Object> result = new HashMap<>();
                    result.put("nickname", testNickname);
                    result.put("rutasEncontradas", rutas != null ? rutas.size() : 0);
                    result.put("rutas", rutas);
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(result));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en test-aerolinea para " + testNickname, ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error en test", "detail", ex.getMessage(), "nickname", testNickname)));
                } finally {
                    out.flush();
                }
                return;
            } else if (pathInfo.equals("/precargar")) {
                // Endpoint para precargar el sistema
                LOG.info("Endpoint de precarga llamado");
                try {
                    if (sistema == null) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                        return;
                    }
                    
                    LOG.info("Iniciando precarga del sistema...");
                    sistema.precargarSistemaCompleto();
                    
                    Map<String, Object> result = new HashMap<>();
                    result.put("status", "OK");
                    result.put("message", "Sistema precargado exitosamente");
                    result.put("timestamp", java.time.LocalDateTime.now().toString());
                    
                    // Verificar que la precarga funcionó
                    try {
                        List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                        result.put("aerolineasDisponibles", aerolineas != null ? aerolineas.size() : 0);
                        
                        List<dato.entidades.Categoria> categorias = sistema.getCategorias();
                        result.put("categoriasDisponibles", categorias != null ? categorias.size() : 0);
                    } catch (Exception ex) {
                        result.put("warning", "Precarga completada pero hay problemas verificando datos: " + ex.getMessage());
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(result));
                    out.flush();
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en precarga del sistema", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error en precarga", "detail", ex.getMessage())));
                    out.flush();
                }
                return;
            } else if (pathInfo.equals("/health")) {
                // Endpoint de salud
                LOG.info("Endpoint de salud llamado");
                try {
                    Map<String, Object> healthInfo = new HashMap<>();
                    healthInfo.put("status", "OK");
                    healthInfo.put("timestamp", java.time.LocalDateTime.now().toString());
                    healthInfo.put("sistemaInicializado", sistema != null);
                    
                    if (sistema != null) {
                        try {
                            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                            healthInfo.put("aerolineasDisponibles", aerolineas != null ? aerolineas.size() : 0);
                        } catch (Exception ex) {
                            healthInfo.put("errorAerolineas", ex.getMessage());
                        }
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(healthInfo));
                    out.flush();
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en endpoint de salud", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print("{\"status\":\"ERROR\",\"error\":\"" + ex.getMessage() + "\"}");
                    out.flush();
                }
                return;
            } else if (pathInfo.equals("/debug")) {
                // Endpoint de debug para verificar el sistema
                try {
                    LOG.info("Verificando sistema...");
                    boolean sistemaInicializado = sistema != null;
                    LOG.info("Sistema inicializado: " + sistemaInicializado);
                    
                    Map<String, Object> debugInfo = new HashMap<>();
                    debugInfo.put("sistemaInicializado", sistemaInicializado);
                    debugInfo.put("timestamp", java.time.LocalDateTime.now().toString());
                    
                    if (sistema != null) {
                        try {
                            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                            debugInfo.put("aerolineasDisponibles", aerolineas.size());
                            debugInfo.put("aerolineas", aerolineas.stream().map(a -> a.getNickname()).collect(java.util.stream.Collectors.toList()));
                        } catch (Exception ex) {
                            debugInfo.put("errorAerolineas", ex.getMessage());
                        }
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(debugInfo));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en debug", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error en debug", "detail", ex.getMessage())));
                }
                return;
            } else if (pathInfo.equals("/diagnostico")) {
                // Endpoint de diagnóstico completo
                LOG.info("Endpoint de diagnóstico llamado");
                try {
                    Map<String, Object> diagnostico = new HashMap<>();
                    diagnostico.put("timestamp", java.time.LocalDateTime.now().toString());
                    diagnostico.put("sistemaInicializado", sistema != null);
                    
                    if (sistema != null) {
                        // Probar listar aerolíneas
                        try {
                            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                            diagnostico.put("aerolineasCount", aerolineas != null ? aerolineas.size() : 0);
                            
                            if (aerolineas != null && !aerolineas.isEmpty()) {
                                // Probar obtener rutas de la primera aerolínea
                                DTAerolinea primeraAero = aerolineas.get(0);
                                diagnostico.put("primeraAerolinea", primeraAero.getNickname());
                                
                                try {
                                    List<DTRutaVuelo> rutasTest = sistema.listarRutaVuelo(primeraAero.getNickname());
                                    diagnostico.put("rutasTestCount", rutasTest != null ? rutasTest.size() : 0);
                                    
                                    if (rutasTest != null && !rutasTest.isEmpty()) {
                                        diagnostico.put("primeraRuta", rutasTest.get(0).getNombre());
                                        diagnostico.put("primeraRutaEstado", rutasTest.get(0).getEstado().toString());
                                    }
                                } catch (Exception rutasEx) {
                                    diagnostico.put("errorRutas", rutasEx.getMessage());
                                    diagnostico.put("errorRutasTipo", rutasEx.getClass().getSimpleName());
                                }
                            }
                        } catch (Exception aeroEx) {
                            diagnostico.put("errorAerolineas", aeroEx.getMessage());
                            diagnostico.put("errorAerolineasTipo", aeroEx.getClass().getSimpleName());
                        }
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(diagnostico));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en diagnóstico", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error en diagnóstico", "detail", ex.getMessage())));
                }
                return;
            } else if (pathInfo.equals("/aerolineas")) {
                // Listar aerolíneas disponibles
                try {
                    LOG.info("Listando aerolíneas disponibles");
                    
                    if (sistema == null) {
                        LOG.severe("Sistema no inicializado");
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                        return;
                    }
                    
                    List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                    LOG.info("Aerolíneas encontradas: " + (aerolineas != null ? aerolineas.size() : 0));
                    
                    List<Map<String, Object>> result = new ArrayList<>();
                    if (aerolineas != null) {
                        for (DTAerolinea aero : aerolineas) {
                            Map<String, Object> aeroMap = new HashMap<>();
                            aeroMap.put("nickname", aero.getNickname());
                            aeroMap.put("nombre", aero.getNombre());
                            aeroMap.put("correo", aero.getCorreo());
                            result.add(aeroMap);
                        }
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    String jsonResponse = objectMapper.writeValueAsString(result);
                    out.print(jsonResponse);
                    LOG.info("Respuesta de aerolíneas enviada exitosamente");
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al listar aerolíneas", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    try {
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Error al listar aerolíneas", "detail", ex.getMessage())));
                    } catch (Exception jsonEx) {
                        LOG.log(Level.SEVERE, "Error al serializar respuesta de error", jsonEx);
                        out.print("{\"error\":\"Error interno del servidor\"}");
                    }
                } finally {
                    out.flush();
                }
                return;
            } else if (pathInfo.equals("/categorias")) {
                // Listar categorías disponibles
                try {
                    LOG.info("Listando categorías disponibles");
                    
                    if (sistema == null) {
                        LOG.severe("Sistema no inicializado");
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                        return;
                    }
                    
                    List<dato.entidades.Categoria> categoriasEntidades = sistema.getCategorias();
                    List<String> categorias = new ArrayList<>();
                    
                    if (categoriasEntidades != null) {
                        for (dato.entidades.Categoria cat : categoriasEntidades) {
                            if (cat != null && cat.getNombre() != null) {
                                categorias.add(cat.getNombre());
                            }
                        }
                    }
                    
                    LOG.info("Categorías encontradas: " + categorias.size());
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    String jsonResponse = objectMapper.writeValueAsString(categorias);
                    out.print(jsonResponse);
                    LOG.info("Respuesta de categorías enviada exitosamente");
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al listar categorías", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    try {
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Error al listar categorías", "detail", ex.getMessage())));
                    } catch (Exception jsonEx) {
                        LOG.log(Level.SEVERE, "Error al serializar respuesta de error", jsonEx);
                        out.print("{\"error\":\"Error interno del servidor\"}");
                    }
                } finally {
                    out.flush();
                }
                return;
            } else if (pathInfo.equals("/ciudades")) {
                // Listar ciudades disponibles
                try {
                    LOG.info("Listando ciudades disponibles");
                    
                    if (sistema == null) {
                        LOG.severe("Sistema no inicializado");
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                        return;
                    }
                    
                    List<logica.DataTypes.DTCiudad> ciudadesEntidades = sistema.listarCiudades();
                    List<Map<String, String>> ciudades = new ArrayList<>();
                    
                    if (ciudadesEntidades != null) {
                        for (logica.DataTypes.DTCiudad ciudad : ciudadesEntidades) {
                            if (ciudad != null && ciudad.getNombre() != null) {
                                Map<String, String> ciudadMap = new HashMap<>();
                                ciudadMap.put("nombre", ciudad.getNombre());
                                ciudadMap.put("pais", ciudad.getPais());
                                ciudadMap.put("nombreCompleto", ciudad.getNombre() + ", " + ciudad.getPais());
                                ciudades.add(ciudadMap);
                            }
                        }
                    }
                    
                    LOG.info("Ciudades encontradas: " + ciudades.size());
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    String jsonResponse = objectMapper.writeValueAsString(ciudades);
                    out.print(jsonResponse);
                    LOG.info("Respuesta de ciudades enviada exitosamente");
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al listar ciudades", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    try {
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Error al listar ciudades", "detail", ex.getMessage())));
                    } catch (Exception jsonEx) {
                        LOG.log(Level.SEVERE, "Error al serializar respuesta de error", jsonEx);
                        out.print("{\"error\":\"Error interno del servidor\"}");
                    }
                } finally {
                    out.flush();
                }
                return;
            } else if (pathInfo.equals("/por-categoria")) {
                // GET /api/rutas/por-categoria?categoria=X - Lista rutas por categoría
                String categoriaParam = request.getParameter("categoria");
                LOG.info("Buscando rutas por categoría: " + categoriaParam);
                handleGetRutasPorCategoria(categoriaParam, response, out);
                return;
            } else {
                String[] pathParts = pathInfo.split("/");
                LOG.info("Path parts: " + java.util.Arrays.toString(pathParts));
                String nombreRuta = pathParts[1];
                
                if (pathParts.length >= 3 && "vuelos".equals(pathParts[2])) {
                    // GET /api/rutas/{nombre}/vuelos - Lista vuelos de una ruta
                    LOG.info("Manejando vuelos de ruta: " + nombreRuta);
                    handleGetVuelosRuta(nombreRuta, response, out);
                } else {
                    // GET /api/rutas/{nombre} - Detalles de una ruta específica
                    LOG.info("Manejando detalles de ruta: " + nombreRuta);
                    handleGetRutaDetalle(nombreRuta, response, out);
                }
                return;
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error /api/rutas GET", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno")));
        } finally {
            out.flush();
        }
    }

    /**
     * GET /api/rutas - Lista todas las rutas confirmadas de todas las aerolíneas
     */
    private void handleGetTodasLasRutas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        LOG.info("Obteniendo todas las rutas de todas las aerolíneas");

        // Verificar que el sistema esté inicializado
        if (sistema == null) {
            LOG.severe("Sistema no inicializado");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
            return;
        }

        List<Map<String, Object>> todasLasRutas = new ArrayList<>();
        
        try {
            // Obtener todas las aerolíneas
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
            LOG.info("Aerolíneas encontradas: " + (aerolineas != null ? aerolineas.size() : 0));
            
            if (aerolineas != null) {
                for (DTAerolinea aero : aerolineas) {
                    try {
                        List<DTRutaVuelo> rutasAerolinea = sistema.listarRutaVuelo(aero.getNickname());
                        if (rutasAerolinea != null) {
                            for (DTRutaVuelo r : rutasAerolinea) {
                                if (r.getEstado() == EstadoRutaVuelo.CONFIRMADA) {
                                    Map<String, Object> rutaMap = new HashMap<>();
                                    rutaMap.put("nombre", r.getNombre());
                                    rutaMap.put("descripcion", r.getDescripcion());
                                    rutaMap.put("fechaAlta", r.getFechaAlta() != null ? r.getFechaAlta().toString() : null);
                                    rutaMap.put("costoBase", r.getCostoBase());
                                    rutaMap.put("estado", r.getEstado() != null ? r.getEstado().toString() : null);
                                    rutaMap.put("aerolinea", aero.getNickname()); // Agregar información de aerolínea
                                    
                                    // Información de ciudades
                                    if (r.getCiudadOrigen() != null) {
                                        Map<String, Object> origenMap = new HashMap<>();
                                        origenMap.put("nombre", r.getCiudadOrigen().getNombre());
                                        origenMap.put("pais", r.getCiudadOrigen().getPais());
                                        rutaMap.put("ciudadOrigen", origenMap);
                                    }
                                    
                                    if (r.getCiudadDestino() != null) {
                                        Map<String, Object> destinoMap = new HashMap<>();
                                        destinoMap.put("nombre", r.getCiudadDestino().getNombre());
                                        destinoMap.put("pais", r.getCiudadDestino().getPais());
                                        rutaMap.put("ciudadDestino", destinoMap);
                                    }
                                    
                                    // Categorías - serializar solo nombres para evitar recursión
                                    if (r.getCategorias() != null && !r.getCategorias().isEmpty()) {
                                        List<String> nombresCategorias = new ArrayList<>();
                                        for (dato.entidades.Categoria cat : r.getCategorias()) {
                                            if (cat != null && cat.getNombre() != null) {
                                                nombresCategorias.add(cat.getNombre());
                                            }
                                        }
                                        rutaMap.put("categorias", nombresCategorias);
                                    }
                                    
                                    // Imagen si existe
                                    if (r.getFoto() != null && r.getFoto().length > 0) {
                                        String fotoBase64 = java.util.Base64.getEncoder().encodeToString(r.getFoto());
                                        rutaMap.put("imagen", "data:image/jpeg;base64," + fotoBase64);
                                    }
                                    
                                    todasLasRutas.add(rutaMap);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        LOG.warning("Error obteniendo rutas de aerolínea " + aero.getNickname() + ": " + ex.getMessage());
                    }
                }
            }
            
            LOG.info("Total de rutas confirmadas encontradas: " + todasLasRutas.size());
            
            String jsonResponse = objectMapper.writeValueAsString(todasLasRutas);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(jsonResponse);
            LOG.info("Respuesta JSON de todas las rutas enviada exitosamente");
            
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener todas las rutas: " + ex.getMessage(), ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener todas las rutas", "detail", ex.getMessage())));
        } finally {
            out.flush();
        }
    }

    /**
     * GET /api/rutas - Lista rutas confirmadas de una aerolínea
     * Si no se especifica el parámetro 'aerolinea', intenta usar la aerolínea de la sesión actual
     * (útil para casos como alta de vuelo donde la aerolínea logueada solo ve sus propias rutas)
     */
    private void handleGetRutas(String nicknameParam, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String nickname = nicknameParam;

        if (nickname == null || nickname.trim().isEmpty()) {
            // Intentar obtener la aerolínea de la sesión si no se especifica
            HttpSession session = request.getSession(false);
            
            if (session != null && session.getAttribute("usuarioLogueado") != null) {
                String tipoUsuario = (String) session.getAttribute("tipoUsuario");
                if ("aerolinea".equals(tipoUsuario)) {
                    nickname = (String) session.getAttribute("usuarioLogueado");
                    LOG.info("Usando aerolínea de la sesión: " + nickname);
                }
            }
            
            if (nickname == null || nickname.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Debe especificar una aerolínea o estar logueado como aerolínea")));
                return;
            }
        }

        LOG.info("Buscando rutas para aerolínea: " + nickname);

        // Verificar que el sistema esté inicializado
        if (sistema == null) {
            LOG.severe("Sistema no inicializado");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
            return;
        }

        // Obtener las rutas filtradas desde la lógica de negocio
        List<DTRutaVuelo> rutas = new ArrayList<>();
        try {
            LOG.info("Llamando a sistema.listarRutaVuelo(" + nickname + ")");
            
            // Verificar que el sistema esté funcionando
            if (sistema == null) {
                LOG.severe("Sistema es null al intentar obtener rutas");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
                return;
            }
            
            // Intentar precargar el sistema si no tiene datos
            try {
                List<DTAerolinea> testAerolineas = sistema.listarAerolineas();
                if (testAerolineas == null || testAerolineas.isEmpty()) {
                    LOG.info("No hay aerolíneas cargadas, precargando sistema...");
                    sistema.precargarSistemaCompleto();
                }
            } catch (Exception precargarEx) {
                LOG.warning("Error al precargar sistema: " + precargarEx.getMessage());
            }
            
            // Usar el mismo método que funciona en VueloController
            rutas = sistema.listarRutaVuelo(nickname);
            LOG.info("Rutas obtenidas exitosamente para aerolínea " + nickname + ": " + (rutas != null ? rutas.size() : 0));
            
            if (rutas == null) {
                LOG.warning("sistema.listarRutaVuelo devolvió null para aerolínea: " + nickname);
                rutas = new ArrayList<>();
            }
        } catch (IllegalArgumentException ex) {
            LOG.warning("Aerolínea no encontrada: " + nickname + " - " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Aerolínea no encontrada", "detail", ex.getMessage())));
            return;
        } catch (IllegalStateException ex) {
            LOG.warning("Error de estado al obtener rutas: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error de estado", "detail", ex.getMessage())));
            return;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener rutas para aerolínea " + nickname + ": " + ex.getMessage(), ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener rutas", "detail", ex.getMessage(), "type", ex.getClass().getSimpleName())));
            return;
        }

        LOG.info("Procesando " + rutas.size() + " rutas para aerolínea " + nickname);
        
        // Determinar si es la aerolínea logueada viendo sus propias rutas
        boolean esAerolineaLogueada = false;
        HttpSession session = request.getSession(false);
        LOG.info("=== VERIFICACIÓN DE AEROLÍNEA LOGUEADA ===");
        LOG.info("Nickname solicitado: " + nickname);
        LOG.info("Session existe: " + (session != null));
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            String usuarioLogueado = (String) session.getAttribute("usuarioLogueado");
            LOG.info("Tipo usuario en sesión: " + tipoUsuario);
            LOG.info("Usuario logueado en sesión: " + usuarioLogueado);
            LOG.info("¿Es aerolínea? " + "aerolinea".equals(tipoUsuario));
            LOG.info("¿Nickname coincide? " + nickname.equals(usuarioLogueado));
            if ("aerolinea".equals(tipoUsuario) && nickname.equals(usuarioLogueado)) {
                esAerolineaLogueada = true;
                LOG.info("✓ Es la aerolínea logueada viendo sus propias rutas - mostrando TODAS las rutas");
            } else {
                LOG.info("✗ NO es la aerolínea logueada - solo se mostrarán rutas CONFIRMADAS");
            }
        } else {
            LOG.info("No hay sesión activa o usuario logueado");
        }
        LOG.info("esAerolineaLogueada = " + esAerolineaLogueada);
        LOG.info("=========================================");
        
        List<Map<String, Object>> outList = new ArrayList<>();
        for (DTRutaVuelo r : rutas) {
            LOG.info("Procesando ruta: " + r.getNombre() + " - Estado: " + r.getEstado());
            
            // Si es la aerolínea logueada viendo sus propias rutas, mostrar todas (INGRESADA, CONFIRMADA, RECHAZADA)
            // Si no, solo mostrar las confirmadas
            boolean debeMostrar = esAerolineaLogueada || r.getEstado() == EstadoRutaVuelo.CONFIRMADA;
            
            if (debeMostrar) {
                Map<String, Object> rutaMap = new HashMap<>();
                rutaMap.put("nombre", r.getNombre());
                rutaMap.put("descripcion", r.getDescripcion());
                rutaMap.put("fechaAlta", r.getFechaAlta() != null ? r.getFechaAlta().toString() : null);
                rutaMap.put("costoBase", r.getCostoBase());
                rutaMap.put("estado", r.getEstado() != null ? r.getEstado().toString() : null);
                
                // Información de aerolínea
                if (r.getAerolinea() != null) {
                    rutaMap.put("aerolineaNickname", r.getAerolinea().getNickname());
                    rutaMap.put("aerolineaNombre", r.getAerolinea().getNombre());
                }
                
                // Información de ciudades
                if (r.getCiudadOrigen() != null) {
                    Map<String, Object> origenMap = new HashMap<>();
                    origenMap.put("nombre", r.getCiudadOrigen().getNombre());
                    origenMap.put("pais", r.getCiudadOrigen().getPais());
                    rutaMap.put("ciudadOrigen", origenMap);
                }
                
                if (r.getCiudadDestino() != null) {
                    Map<String, Object> destinoMap = new HashMap<>();
                    destinoMap.put("nombre", r.getCiudadDestino().getNombre());
                    destinoMap.put("pais", r.getCiudadDestino().getPais());
                    rutaMap.put("ciudadDestino", destinoMap);
                }
                
                // Categorías - serializar solo nombres para evitar recursión
                if (r.getCategorias() != null && !r.getCategorias().isEmpty()) {
                    List<String> nombresCategorias = new ArrayList<>();
                    for (dato.entidades.Categoria cat : r.getCategorias()) {
                        if (cat != null && cat.getNombre() != null) {
                            nombresCategorias.add(cat.getNombre());
                        }
                    }
                    rutaMap.put("categorias", nombresCategorias);
                }
                
                // Imagen si existe
                if (r.getFoto() != null && r.getFoto().length > 0) {
                    String fotoBase64 = java.util.Base64.getEncoder().encodeToString(r.getFoto());
                    rutaMap.put("imagen", "data:image/jpeg;base64," + fotoBase64);
                }
                
                outList.add(rutaMap);
                LOG.info("Ruta confirmada agregada: " + r.getNombre());
            } else {
                LOG.info("Ruta " + r.getNombre() + " omitida - Estado: " + r.getEstado());
            }
        }

        try {
            String jsonResponse = objectMapper.writeValueAsString(outList);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(jsonResponse);
            LOG.info("Respuesta JSON de rutas enviada exitosamente: " + outList.size() + " rutas confirmadas de " + rutas.size() + " rutas totales");
        } catch (Exception jsonEx) {
            LOG.log(Level.SEVERE, "Error al serializar JSON de rutas: " + jsonEx.getMessage(), jsonEx);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor")));
            } catch (Exception finalEx) {
                LOG.log(Level.SEVERE, "Error crítico al serializar respuesta de error", finalEx);
                out.print("{\"error\":\"Error interno del servidor\"}");
            }
        } finally {
            out.flush();
        }
    }

    /**
     * GET /api/rutas/{nombre} - Detalles de una ruta específica
     */
    private void handleGetRutaDetalle(String nombreRuta, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            // Buscar la ruta en todas las aerolíneas
            DTRutaVuelo ruta = buscarRutaEnTodasLasAerolineas(nombreRuta);
            
            if (ruta == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Ruta no encontrada")));
                return;
            }

            Map<String, Object> rutaMap = new HashMap<>();
            rutaMap.put("nombre", ruta.getNombre());
            rutaMap.put("descripcion", ruta.getDescripcion());
            rutaMap.put("fechaAlta", ruta.getFechaAlta() != null ? ruta.getFechaAlta().toString() : null);
            rutaMap.put("costoBase", ruta.getCostoBase());
            rutaMap.put("estado", ruta.getEstado() != null ? ruta.getEstado().toString() : null);
            
            // Información de aerolínea
            if (ruta.getAerolinea() != null) {
                rutaMap.put("aerolineaNickname", ruta.getAerolinea().getNickname());
                rutaMap.put("aerolineaNombre", ruta.getAerolinea().getNombre());
            }
            
            // Información de ciudades
            if (ruta.getCiudadOrigen() != null) {
                Map<String, Object> origenMap = new HashMap<>();
                origenMap.put("nombre", ruta.getCiudadOrigen().getNombre());
                origenMap.put("pais", ruta.getCiudadOrigen().getPais());
                rutaMap.put("ciudadOrigen", origenMap);
            }
            
            if (ruta.getCiudadDestino() != null) {
                Map<String, Object> destinoMap = new HashMap<>();
                destinoMap.put("nombre", ruta.getCiudadDestino().getNombre());
                destinoMap.put("pais", ruta.getCiudadDestino().getPais());
                rutaMap.put("ciudadDestino", destinoMap);
            }
            
            // Categorías - serializar solo nombres para evitar recursión
            if (ruta.getCategorias() != null && !ruta.getCategorias().isEmpty()) {
                List<String> nombresCategorias = new ArrayList<>();
                for (dato.entidades.Categoria cat : ruta.getCategorias()) {
                    if (cat != null && cat.getNombre() != null) {
                        nombresCategorias.add(cat.getNombre());
                    }
                }
                rutaMap.put("categorias", nombresCategorias);
            }
            
            // Imagen si existe
            if (ruta.getFoto() != null && ruta.getFoto().length > 0) {
                String fotoBase64 = java.util.Base64.getEncoder().encodeToString(ruta.getFoto());
                rutaMap.put("imagen", "data:image/jpeg;base64," + fotoBase64);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(rutaMap));
            
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener detalles de ruta: " + nombreRuta, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener detalles de la ruta")));
        }
    }

    /**
     * GET /api/rutas/por-categoria?categoria=X - Lista rutas por categoría
     */
    private void handleGetRutasPorCategoria(String categoriaParam, HttpServletResponse response, PrintWriter out) throws IOException {
        if (categoriaParam == null || categoriaParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Debe especificar una categoría")));
            return;
        }

        LOG.info("Buscando rutas para categoría: " + categoriaParam);

        // Verificar que el sistema esté inicializado
        if (sistema == null) {
            LOG.severe("Sistema no inicializado");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Sistema no inicializado")));
            return;
        }

        // Obtener las rutas filtradas por categoría desde la lógica de negocio
        List<DTRutaVuelo> rutas = new ArrayList<>();
        try {
            LOG.info("Buscando rutas por categoría: " + categoriaParam);
            
            // Obtener todas las aerolíneas
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
            
            // Para cada aerolínea, obtener sus rutas y filtrar por categoría
            for (DTAerolinea aero : aerolineas) {
                try {
                    List<DTRutaVuelo> rutasAerolinea = sistema.listarRutaVuelo(aero.getNickname());
                    
                    // Filtrar rutas que contengan la categoría especificada
                    for (DTRutaVuelo ruta : rutasAerolinea) {
                        if (ruta.getCategorias() != null) {
                            // Verificar si alguna categoría de la ruta coincide con el parámetro
                            boolean categoriaEncontrada = false;
                            for (dato.entidades.Categoria categoria : ruta.getCategorias()) {
                                if (categoria != null && categoriaParam.equals(categoria.getNombre())) {
                                    categoriaEncontrada = true;
                                    break;
                                }
                            }
                            if (categoriaEncontrada) {
                                rutas.add(ruta);
                            }
                        }
                    }
                } catch (Exception ex) {
                    LOG.warning("Error obteniendo rutas de aerolínea " + aero.getNickname() + ": " + ex.getMessage());
                }
            }
            
            LOG.info("Rutas obtenidas exitosamente para categoría " + categoriaParam + ": " + rutas.size());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener rutas para categoría " + categoriaParam + ": " + ex.getMessage(), ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener rutas", "detail", ex.getMessage())));
            return;
        }

        List<Map<String, Object>> outList = new ArrayList<>();
        for (DTRutaVuelo r : rutas) {
            if (r.getEstado() == EstadoRutaVuelo.CONFIRMADA) {
                Map<String, Object> rutaMap = new HashMap<>();
                rutaMap.put("nombre", r.getNombre());
                rutaMap.put("descripcion", r.getDescripcion());
                rutaMap.put("fechaAlta", r.getFechaAlta() != null ? r.getFechaAlta().toString() : null);
                rutaMap.put("costoBase", r.getCostoBase());
                rutaMap.put("estado", r.getEstado() != null ? r.getEstado().toString() : null);
                
                // Información de ciudades
                if (r.getCiudadOrigen() != null) {
                    Map<String, Object> origenMap = new HashMap<>();
                    origenMap.put("nombre", r.getCiudadOrigen().getNombre());
                    origenMap.put("pais", r.getCiudadOrigen().getPais());
                    rutaMap.put("ciudadOrigen", origenMap);
                }
                
                if (r.getCiudadDestino() != null) {
                    Map<String, Object> destinoMap = new HashMap<>();
                    destinoMap.put("nombre", r.getCiudadDestino().getNombre());
                    destinoMap.put("pais", r.getCiudadDestino().getPais());
                    rutaMap.put("ciudadDestino", destinoMap);
                }
                
                // Categorías - serializar solo nombres para evitar recursión
                if (r.getCategorias() != null && !r.getCategorias().isEmpty()) {
                    List<String> nombresCategorias = new ArrayList<>();
                    for (dato.entidades.Categoria cat : r.getCategorias()) {
                        if (cat != null && cat.getNombre() != null) {
                            nombresCategorias.add(cat.getNombre());
                        }
                    }
                    rutaMap.put("categorias", nombresCategorias);
                }
                
                // Imagen si existe
                if (r.getFoto() != null && r.getFoto().length > 0) {
                    String fotoBase64 = java.util.Base64.getEncoder().encodeToString(r.getFoto());
                    rutaMap.put("imagen", "data:image/jpeg;base64," + fotoBase64);
                }
                
                outList.add(rutaMap);
            }
        }

        try {
            String jsonResponse = objectMapper.writeValueAsString(outList);
            out.print(jsonResponse);
            response.setStatus(HttpServletResponse.SC_OK);
            LOG.info("Respuesta JSON de rutas por categoría enviada exitosamente");
        } catch (Exception jsonEx) {
            LOG.log(Level.SEVERE, "Error al serializar JSON de rutas por categoría: " + jsonEx.getMessage(), jsonEx);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error interno del servidor\"}");
        }
    }

    /**
     * GET /api/rutas/{nombre}/vuelos - Lista vuelos de una ruta específica
     */
    private void handleGetVuelosRuta(String nombreRuta, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Buscando vuelos para ruta: " + nombreRuta);
            
            // Primero intentar con datos de prueba para verificar que el endpoint funciona
            if ("test".equals(nombreRuta)) {
                List<Map<String, Object>> testResult = new ArrayList<>();
                Map<String, Object> testVuelo = new HashMap<>();
                testVuelo.put("nombre", "Vuelo de Prueba");
                testVuelo.put("fechaVuelo", "2024-01-15");
                testVuelo.put("horaVuelo", "10:30");
                testVuelo.put("duracion", "2:30");
                testVuelo.put("asientosMaxTurista", 150);
                testVuelo.put("asientosMaxEjecutivo", 20);
                testVuelo.put("fechaAlta", "2024-01-01");
                testResult.add(testVuelo);
                
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(testResult));
                return;
            }
            
            List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(nombreRuta);
            LOG.info("Vuelos encontrados: " + vuelos.size());
            
            List<Map<String, Object>> result = new ArrayList<>();
            for (DTVuelo vuelo : vuelos) {
                Map<String, Object> vueloMap = new HashMap<>();
                vueloMap.put("nombre", vuelo.getNombre());
                vueloMap.put("fechaVuelo", vuelo.getFechaVuelo() != null ? vuelo.getFechaVuelo().toString() : null);
                vueloMap.put("horaVuelo", vuelo.getHoraVuelo() != null ? vuelo.getHoraVuelo().toString() : null);
                vueloMap.put("duracion", vuelo.getDuracion() != null ? vuelo.getDuracion().toString() : null);
                vueloMap.put("asientosMaxTurista", vuelo.getAsientosMaxTurista());
                vueloMap.put("asientosMaxEjecutivo", vuelo.getAsientosMaxEjecutivo());
                vueloMap.put("fechaAlta", vuelo.getFechaAlta() != null ? vuelo.getFechaAlta().toString() : null);
                
                // Incluir foto si existe (convertir a base64 para JSON)
                if (vuelo.getFoto() != null && vuelo.getFoto().length > 0) {
                    String fotoBase64 = java.util.Base64.getEncoder().encodeToString(vuelo.getFoto());
                    vueloMap.put("foto", "data:image/jpeg;base64," + fotoBase64);
                }
                
                result.add(vueloMap);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(result));
            
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener vuelos de ruta: " + nombreRuta, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener vuelos", "detail", ex.getMessage())));
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
                    + ", fechaAlta=" + fechaAltaStr
                    + ", costoTurista=" + costoTurista
                    + ", costoEjecutivo=" + costoEjecutivo
                    + ", costoEquipaje=" + costoEquipaje
                    + ", ciudadOrigen=" + ciudadOrigen
                    + ", ciudadDestino=" + ciudadDestino
                    + ", categorias=" + categorias
                    + ", fotoBytes=" + (finalFotoBytes == null ? 0 : finalFotoBytes.length));

            // --- Validaciones mínimas del servidor ---
            if (isEmpty(nombre) || isEmpty(descripcion)
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

    /**
     * Busca una ruta por nombre en todas las aerolíneas
     */
    private DTRutaVuelo buscarRutaEnTodasLasAerolineas(String nombreRuta) {
        try {
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
            if (aerolineas == null || aerolineas.isEmpty()) {
                LOG.warning("No hay aerolíneas disponibles para buscar la ruta");
                return null;
            }
            
            for (DTAerolinea aerolinea : aerolineas) {
                try {
                    // Seleccionar la aerolínea temporalmente
                    sistema.seleccionarAerolinea(aerolinea.getNickname());
                    
                    // Buscar la ruta en esta aerolínea
                    DTRutaVuelo ruta = sistema.seleccionarRutaVueloRet(nombreRuta);
                    if (ruta != null) {
                        LOG.info("Ruta encontrada en aerolínea: " + aerolinea.getNickname());
                        return ruta;
                    }
                } catch (IllegalStateException e) {
                    // Continuar buscando en la siguiente aerolínea
                    continue;
                } catch (Exception e) {
                    LOG.warning("Error al buscar ruta en aerolínea " + aerolinea.getNickname() + ": " + e.getMessage());
                    continue;
                }
            }
            
            LOG.warning("Ruta no encontrada en ninguna aerolínea: " + nombreRuta);
            return null;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al buscar ruta en todas las aerolíneas", e);
            return null;
        }
    }

    // Helpers
    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isEmpty(String s) { return s == null || s.isBlank(); }
}
