package com.volandouy.controller;

import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;
import logica.DataTypes.DTUsuario;
import logica.DataTypes.DTCliente;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTReserva;
import logica.DataTypes.DTCompraPaquete;
import logica.DataTypes.DTRutaVuelo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                // GET /api/usuarios - Listar todos los usuarios con información completa
                try {
                    List<Map<String, Object>> usuariosCompletos = new ArrayList<>();
                    
                    // Obtener clientes
                    List<DTCliente> clientes = sistema.listarClientes();
                    for (DTCliente cliente : clientes) {
                        Map<String, Object> usuarioMap = new HashMap<>();
                        usuarioMap.put("nickname", cliente.getNickname());
                        usuarioMap.put("nombre", cliente.getNombre());
                        usuarioMap.put("correo", cliente.getCorreo());
                        usuarioMap.put("foto", cliente.getFoto());
                        usuarioMap.put("tipo", "cliente");
                        usuarioMap.put("apellido", cliente.getApellido());
                        usuarioMap.put("nacionalidad", cliente.getNacionalidad());
                        usuarioMap.put("numeroDocumento", cliente.getNumeroDocumento());
                        usuarioMap.put("tipoDocumento", cliente.getTipoDocumento() != null ? cliente.getTipoDocumento().toString() : null);
                        usuarioMap.put("fechaNacimiento", cliente.getFechaNacimiento());
                        usuariosCompletos.add(usuarioMap);
                    }
                    
                    // Obtener aerolíneas
                    List<DTAerolinea> aerolineas = sistema.listarAerolineas();
                    for (DTAerolinea aerolinea : aerolineas) {
                        Map<String, Object> usuarioMap = new HashMap<>();
                        usuarioMap.put("nickname", aerolinea.getNickname());
                        usuarioMap.put("nombre", aerolinea.getNombre());
                        usuarioMap.put("correo", aerolinea.getCorreo());
                        usuarioMap.put("foto", aerolinea.getFoto());
                        usuarioMap.put("tipo", "aerolinea");
                        usuarioMap.put("descripcion", aerolinea.getDescripcion());
                        usuarioMap.put("sitioWeb", aerolinea.getLinkSitioWeb());
                        usuariosCompletos.add(usuarioMap);
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(Map.of("usuarios", usuariosCompletos)));
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
            } else if (pathInfo.equals("/debug")) {
                // GET /api/usuarios/debug - Endpoint de debug para verificar mostrarDatosUsuario
                String nickname = request.getParameter("nickname");
                if (nickname == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Parámetro nickname requerido")));
                    return;
                }
                
                try {
                    LOG.info("=== DEBUG: Probando mostrarDatosUsuario para: " + nickname + " ===");
                    
                    // Probar mostrarDatosUsuarioMod
                    var datosMod = sistema.mostrarDatosUsuarioMod(nickname);
                    LOG.info("mostrarDatosUsuarioMod resultado: " + (datosMod != null ? "OK" : "NULL"));
                    
                    // Probar mostrarDatosUsuario
                    var datosCompletos = sistema.mostrarDatosUsuario(nickname);
                    LOG.info("mostrarDatosUsuario resultado: " + (datosCompletos != null ? "OK" : "NULL"));
                    
                    Map<String, Object> debugInfo = new HashMap<>();
                    debugInfo.put("nickname", nickname);
                    debugInfo.put("mostrarDatosUsuarioMod", datosMod != null ? "OK" : "NULL");
                    debugInfo.put("mostrarDatosUsuario", datosCompletos != null ? "OK" : "NULL");
                    
                    if (datosCompletos instanceof DTCliente) {
                        DTCliente cliente = (DTCliente) datosCompletos;
                        debugInfo.put("tipo", "DTCliente");
                        debugInfo.put("reservas_count", cliente.getReserva() != null ? cliente.getReserva().size() : 0);
                        debugInfo.put("reservas", cliente.getReserva());
                    } else if (datosCompletos instanceof DTAerolinea) {
                        debugInfo.put("tipo", "DTAerolinea");
                    } else {
                        debugInfo.put("tipo", datosCompletos != null ? datosCompletos.getClass().getSimpleName() : "NULL");
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(debugInfo));
                    
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en debug endpoint: " + ex.getMessage(), ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error en debug: " + ex.getMessage())));
                }
            } else if (pathInfo.equals("/test")) {
                // GET /api/usuarios/test - Endpoint simple de prueba
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(Map.of(
                    "message", "Endpoint de usuarios funcionando correctamente",
                    "timestamp", System.currentTimeMillis(),
                    "servlet", "UsuarioController"
                )));
            } else if (pathInfo.equals("/check-reservas")) {
                // GET /api/usuarios/check-reservas?nickname=anita20182005 - Verificar reservas específicamente
                String nickname = request.getParameter("nickname");
                if (nickname == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Parámetro nickname requerido")));
                    return;
                }
                
                try {
                    LOG.info("=== CHECK RESERVAS: Verificando reservas para: " + nickname + " ===");
                    
                    // Probar mostrarDatosUsuarioMod
                    var datosMod = sistema.mostrarDatosUsuarioMod(nickname);
                    LOG.info("mostrarDatosUsuarioMod: " + (datosMod != null ? "OK" : "NULL"));
                    
                    // Probar mostrarDatosUsuario
                    var datosCompletos = sistema.mostrarDatosUsuario(nickname);
                    LOG.info("mostrarDatosUsuario: " + (datosCompletos != null ? "OK" : "NULL"));
                    
                    Map<String, Object> result = new HashMap<>();
                    result.put("nickname", nickname);
                    result.put("mostrarDatosUsuarioMod", datosMod != null ? "OK" : "NULL");
                    result.put("mostrarDatosUsuario", datosCompletos != null ? "OK" : "NULL");
                    
                    if (datosCompletos instanceof DTCliente) {
                        DTCliente cliente = (DTCliente) datosCompletos;
                        result.put("tipo", "DTCliente");
                        result.put("reservas_count", cliente.getReserva() != null ? cliente.getReserva().size() : 0);
                        
                        if (cliente.getReserva() != null && !cliente.getReserva().isEmpty()) {
                            List<Map<String, Object>> reservasInfo = new ArrayList<>();
                            for (DTReserva reserva : cliente.getReserva()) {
                                Map<String, Object> reservaInfo = new HashMap<>();
                                reservaInfo.put("id", reserva.getId());
                                reservaInfo.put("tipo", reserva.getClass().getSimpleName());
                                reservaInfo.put("fechaReserva", reserva.getFechaReserva() != null ? reserva.getFechaReserva().toString() : null);
                                reservaInfo.put("costoReserva", reserva.getCostoReserva());
                                reservasInfo.add(reservaInfo);
                            }
                            result.put("reservas_detalle", reservasInfo);
                        } else {
                            result.put("reservas_detalle", "ARRAY_VACIO");
                        }
                    } else {
                        result.put("tipo", datosCompletos != null ? datosCompletos.getClass().getSimpleName() : "NULL");
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(result));
                    
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en check-reservas: " + ex.getMessage(), ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error: " + ex.getMessage())));
                }
            } else if (pathInfo.equals("/verificar-bd")) {
                // GET /api/usuarios/verificar-bd?nickname=anita20182005 - Verificar directamente en BD
                String nickname = request.getParameter("nickname");
                if (nickname == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Parámetro nickname requerido")));
                    return;
                }
                
                try {
                    LOG.info("=== VERIFICAR BD: Verificando reservas en BD para: " + nickname + " ===");
                    
                    Map<String, Object> result = new HashMap<>();
                    result.put("nickname", nickname);
                    
                    // Verificar si el usuario existe en la BD
                    try {
                        var datosMod = sistema.mostrarDatosUsuarioMod(nickname);
                        result.put("usuario_existe", datosMod != null);
                        result.put("tipo_usuario", datosMod != null ? datosMod.getClass().getSimpleName() : "NO_ENCONTRADO");
                        
                        if (datosMod instanceof DTCliente) {
                            result.put("es_cliente", true);
                            result.put("apellido", ((DTCliente) datosMod).getApellido());
                            result.put("nacionalidad", ((DTCliente) datosMod).getNacionalidad());
                        } else {
                            result.put("es_cliente", false);
                        }
                    } catch (Exception e) {
                        result.put("usuario_existe", false);
                        result.put("error_usuario", e.getMessage());
                    }
                    
                    // Verificar método mostrarDatosUsuario
                    try {
                        var datosCompletos = sistema.mostrarDatosUsuario(nickname);
                        result.put("mostrarDatosUsuario_funciona", datosCompletos != null);
                        
                        if (datosCompletos instanceof DTCliente) {
                            DTCliente cliente = (DTCliente) datosCompletos;
                            result.put("reservas_en_mostrarDatosUsuario", cliente.getReserva() != null ? cliente.getReserva().size() : 0);
                        }
                    } catch (Exception e) {
                        result.put("mostrarDatosUsuario_funciona", false);
                        result.put("error_mostrarDatosUsuario", e.getMessage());
                    }
                    
                    result.put("diagnostico", "Verificación completa de BD completada");
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(result));
                    
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error en verificar-bd: " + ex.getMessage(), ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error: " + ex.getMessage())));
                }
            } else {
                // GET /api/usuarios/{nickname} - Obtener datos específicos de un usuario
                String nickname = pathInfo.substring(1); // Remover el '/' inicial
                try {
                    LOG.info("Intentando obtener datos del usuario: " + nickname);
                    
                    // Usar mostrarDatosUsuarioMod como fallback seguro
                    var datosUsuario = sistema.mostrarDatosUsuarioMod(nickname);
                    LOG.info("Datos del usuario obtenidos: " + (datosUsuario != null ? "OK" : "NULL"));
                    
                    if (datosUsuario != null) {
                        LOG.info("Extrayendo datos del usuario...");
                        Map<String, Object> usuarioData = extraerDatosUsuario(datosUsuario);
                        
                        // Para aerolíneas, intentar obtener todas las rutas
                        if (datosUsuario instanceof DTAerolinea) {
                            try {
                                LOG.info("Intentando obtener todas las rutas de la aerolínea: " + nickname);
                                var datosCompletos = sistema.mostrarDatosUsuario(nickname);
                                if (datosCompletos instanceof DTAerolinea) {
                                    DTAerolinea aerolineaCompleta = (DTAerolinea) datosCompletos;
                                    int numRutas = aerolineaCompleta.getRutasVuelo() != null ? aerolineaCompleta.getRutasVuelo().size() : 0;
                                    LOG.info("Rutas obtenidas con mostrarDatosUsuario: " + numRutas);
                                    
                                    // Procesar rutas
                                    if (aerolineaCompleta.getRutasVuelo() != null && !aerolineaCompleta.getRutasVuelo().isEmpty()) {
                                        List<Map<String, Object>> rutasData = new ArrayList<>();
                                        for (DTRutaVuelo ruta : aerolineaCompleta.getRutasVuelo()) {
                                            Map<String, Object> rutaMap = new HashMap<>();
                                            rutaMap.put("nombre", ruta.getNombre());
                                            rutaMap.put("descripcion", ruta.getDescripcion());
                                            rutaMap.put("fechaAlta", ruta.getFechaAlta() != null ? ruta.getFechaAlta().toString() : null);
                                            rutaMap.put("costoBase", ruta.getCostoBase());
                                            rutaMap.put("estado", ruta.getEstado() != null ? ruta.getEstado().toString() : null);
                                            
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
                                            
                                            rutasData.add(rutaMap);
                                        }
                                        usuarioData.put("rutas", rutasData);
                                        LOG.info("Rutas procesadas y agregadas: " + rutasData.size());
                                    }
                                }
                            } catch (Exception e) {
                                LOG.log(Level.SEVERE, "Error al procesar rutas de la aerolínea: " + e.getMessage(), e);
                            }
                        }
                        
                        // Para clientes, usar método alternativo que funciona
                        if (datosUsuario instanceof DTCliente) {
                            try {
                                LOG.info("Usando método alternativo para obtener reservas del cliente: " + nickname);
                                
                                // Crear DTCliente con reservas usando el método que sabemos que funciona
                                DTCliente clienteConReservas = null;
                                
                                // Intentar con mostrarDatosUsuario primero
                                try {
                                    LOG.info("Llamando a sistema.mostrarDatosUsuario para: " + nickname);
                                    var datosCompletos = sistema.mostrarDatosUsuario(nickname);
                                    LOG.info("Respuesta de mostrarDatosUsuario: " + (datosCompletos != null ? "OK" : "NULL"));
                                    if (datosCompletos instanceof DTCliente) {
                                        clienteConReservas = (DTCliente) datosCompletos;
                                        int numReservas = clienteConReservas.getReserva() != null ? clienteConReservas.getReserva().size() : 0;
                                        LOG.info("Reservas obtenidas con mostrarDatosUsuario: " + numReservas);
                                        if (numReservas > 0) {
                                            LOG.info("Primera reserva: " + clienteConReservas.getReserva().get(0).getClass().getSimpleName());
                                        }
                                    } else {
                                        LOG.warning("mostrarDatosUsuario no devolvió un DTCliente, devolvió: " + 
                                                  (datosCompletos != null ? datosCompletos.getClass().getSimpleName() : "null"));
                                    }
                                } catch (Exception e) {
                                    LOG.log(Level.SEVERE, "mostrarDatosUsuario falló para " + nickname + ": " + e.getMessage(), e);
                                    e.printStackTrace();
                                }
                                
                                // Si no funcionó, intentar con el método mostrarDatosUsuarioMod que ya tenemos
                                if (clienteConReservas == null) {
                                    LOG.info("mostrarDatosUsuario no devolvió datos, intentando con mostrarDatosUsuarioMod");
                                    clienteConReservas = (DTCliente) datosUsuario;
                                }
                                
                                // Procesar reservas y paquetes
                                List<Map<String, Object>> reservasData = new ArrayList<>();
                                List<Map<String, Object>> paquetesData = new ArrayList<>();
                                
                                if (clienteConReservas.getReserva() != null && !clienteConReservas.getReserva().isEmpty()) {
                                    LOG.info("Procesando " + clienteConReservas.getReserva().size() + " reservas...");
                                    
                                    for (DTReserva reserva : clienteConReservas.getReserva()) {
                                        if (reserva instanceof DTCompraPaquete) {
                                            DTCompraPaquete compraPaquete = (DTCompraPaquete) reserva;
                                            Map<String, Object> paqueteMap = new HashMap<>();
                                            paqueteMap.put("id", compraPaquete.getId());
                                            paqueteMap.put("fechaCompra", compraPaquete.getFechaReserva() != null ? compraPaquete.getFechaReserva().toString() : null);
                                            // Extraer el costo total del objeto DTCostoBase
                                            Object costoReservaObj = compraPaquete.getCostoReserva();
                                            float costoTotal = 0.0f;
                                            if (costoReservaObj instanceof logica.DataTypes.DTCostoBase) {
                                                costoTotal = ((logica.DataTypes.DTCostoBase) costoReservaObj).getCostoTotal();
                                            } else if (costoReservaObj instanceof Number) {
                                                costoTotal = ((Number) costoReservaObj).floatValue();
                                            }
                                            paqueteMap.put("costoTotal", costoTotal);
                                            paqueteMap.put("vencimiento", compraPaquete.getVencimiento() != null ? compraPaquete.getVencimiento().toString() : null);
                                            paqueteMap.put("nickname", compraPaquete.getNickname());
                                            paquetesData.add(paqueteMap);
                                            LOG.info("Paquete agregado: " + compraPaquete.getId());
                                        } else {
                                            Map<String, Object> reservaMap = new HashMap<>();
                                            reservaMap.put("id", reserva.getId());
                                            reservaMap.put("fechaReserva", reserva.getFechaReserva() != null ? reserva.getFechaReserva().toString() : null);
                                            // Extraer el costo total del objeto DTCostoBase
                                            Object costoReservaObj = reserva.getCostoReserva();
                                            float costoTotal = 0.0f;
                                            if (costoReservaObj instanceof logica.DataTypes.DTCostoBase) {
                                                costoTotal = ((logica.DataTypes.DTCostoBase) costoReservaObj).getCostoTotal();
                                            } else if (costoReservaObj instanceof Number) {
                                                costoTotal = ((Number) costoReservaObj).floatValue();
                                            }
                                            reservaMap.put("costoReserva", costoTotal);
                                            reservaMap.put("nickname", reserva.getNickname());
                                            reservasData.add(reservaMap);
                                            LOG.info("Reserva agregada: " + reserva.getId());
                                        }
                                    }
                                } else {
                                    LOG.info("No hay reservas para procesar - arrays vacíos");
                                    
                                    // Intentar obtener reservas directamente desde el sistema como fallback
                                    try {
                                        LOG.info("Intentando obtener reservas directamente desde el sistema...");
                                        var datosCompletosFallback = sistema.mostrarDatosUsuario(nickname);
                                        if (datosCompletosFallback instanceof DTCliente) {
                                            DTCliente clienteFallback = (DTCliente) datosCompletosFallback;
                                            if (clienteFallback.getReserva() != null && !clienteFallback.getReserva().isEmpty()) {
                                                LOG.info("Reservas encontradas en fallback: " + clienteFallback.getReserva().size());
                                                for (DTReserva reserva : clienteFallback.getReserva()) {
                                                    if (reserva instanceof DTCompraPaquete) {
                                                        DTCompraPaquete compraPaquete = (DTCompraPaquete) reserva;
                                                        Map<String, Object> paqueteMap = new HashMap<>();
                                                        paqueteMap.put("id", compraPaquete.getId());
                                                        paqueteMap.put("fechaCompra", compraPaquete.getFechaReserva() != null ? compraPaquete.getFechaReserva().toString() : null);
                                                        // Extraer el costo total del objeto DTCostoBase
                                                        Object costoReservaObj = compraPaquete.getCostoReserva();
                                                        float costoTotal = 0.0f;
                                                        if (costoReservaObj instanceof logica.DataTypes.DTCostoBase) {
                                                            costoTotal = ((logica.DataTypes.DTCostoBase) costoReservaObj).getCostoTotal();
                                                        } else if (costoReservaObj instanceof Number) {
                                                            costoTotal = ((Number) costoReservaObj).floatValue();
                                                        }
                                                        paqueteMap.put("costoTotal", costoTotal);
                                                        paqueteMap.put("vencimiento", compraPaquete.getVencimiento() != null ? compraPaquete.getVencimiento().toString() : null);
                                                        paqueteMap.put("nickname", compraPaquete.getNickname());
                                                        paquetesData.add(paqueteMap);
                                                    } else {
                                                        Map<String, Object> reservaMap = new HashMap<>();
                                                        reservaMap.put("id", reserva.getId());
                                                        reservaMap.put("fechaReserva", reserva.getFechaReserva() != null ? reserva.getFechaReserva().toString() : null);
                                                        // Extraer el costo total del objeto DTCostoBase
                                                        Object costoReservaObj = reserva.getCostoReserva();
                                                        float costoTotal = 0.0f;
                                                        if (costoReservaObj instanceof logica.DataTypes.DTCostoBase) {
                                                            costoTotal = ((logica.DataTypes.DTCostoBase) costoReservaObj).getCostoTotal();
                                                        } else if (costoReservaObj instanceof Number) {
                                                            costoTotal = ((Number) costoReservaObj).floatValue();
                                                        }
                                                        reservaMap.put("costoReserva", costoTotal);
                                                        reservaMap.put("nickname", reserva.getNickname());
                                                        reservasData.add(reservaMap);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception fallbackError) {
                                        LOG.warning("Error en fallback de reservas: " + fallbackError.getMessage());
                                    }
                                }
                                
                                usuarioData.put("reservas", reservasData);
                                usuarioData.put("paquetes", paquetesData);
                                LOG.info("Reservas procesadas: " + reservasData.size() + ", Paquetes procesados: " + paquetesData.size());
                                
                            } catch (Exception e) {
                                LOG.log(Level.SEVERE, "Error al procesar reservas del cliente: " + e.getMessage(), e);
                                usuarioData.put("reservas", new ArrayList<>());
                                usuarioData.put("paquetes", new ArrayList<>());
                            }
                        }
                        
                        LOG.info("Datos extraídos exitosamente");
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.print(objectMapper.writeValueAsString(usuarioData));
                    } else {
                        LOG.warning("Usuario no encontrado: " + nickname);
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no encontrado")));
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al obtener datos del usuario: " + nickname, ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
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


    // Crear nuevo usuario (cliente o aerolínea)
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

                // Incluir reservas del cliente (incluyendo paquetes comprados)
                if (cliente.getReserva() != null && !cliente.getReserva().isEmpty()) {
                    List<Map<String, Object>> reservasData = new ArrayList<>();
                    List<Map<String, Object>> paquetesData = new ArrayList<>();
                    
                    for (DTReserva reserva : cliente.getReserva()) {
                        Map<String, Object> reservaMap = new HashMap<>();
                        reservaMap.put("id", reserva.getId());
                        reservaMap.put("fechaReserva", reserva.getFechaReserva() != null ? reserva.getFechaReserva().toString() : null);
                        reservaMap.put("costoReserva", reserva.getCostoReserva());
                        reservaMap.put("nickname", reserva.getNickname());
                        
                        // Verificar si es una compra de paquete
                        if (reserva instanceof DTCompraPaquete) {
                            DTCompraPaquete compraPaquete = (DTCompraPaquete) reserva;
                            Map<String, Object> paqueteMap = new HashMap<>();
                            paqueteMap.put("id", compraPaquete.getId());
                            paqueteMap.put("nombrePaquete", compraPaquete.getNombrePaquete());
                            paqueteMap.put("fechaCompra", compraPaquete.getFechaReserva() != null ? compraPaquete.getFechaReserva().toString() : null);
                            paqueteMap.put("costoTotal", compraPaquete.getCostoReserva());
                            paqueteMap.put("vencimiento", compraPaquete.getVencimiento() != null ? compraPaquete.getVencimiento().toString() : null);
                            paqueteMap.put("nickname", compraPaquete.getNickname());
                            paquetesData.add(paqueteMap);
                        } else {
                            reservasData.add(reservaMap);
                        }
                    }
                    
                    datos.put("reservas", reservasData);
                    datos.put("paquetes", paquetesData);
                }

            } else if (dtUsuario instanceof DTAerolinea) {
                DTAerolinea aerolinea = (DTAerolinea) dtUsuario;
                datos.put("tipo", "aerolinea");
                datos.put("descripcion", aerolinea.getDescripcion());
                datos.put("sitioWeb", aerolinea.getLinkSitioWeb());
                
                // Incluir rutas de vuelo de la aerolínea
                if (aerolinea.getRutasVuelo() != null && !aerolinea.getRutasVuelo().isEmpty()) {
                    List<Map<String, Object>> rutasData = new ArrayList<>();
                    for (DTRutaVuelo ruta : aerolinea.getRutasVuelo()) {
                        Map<String, Object> rutaMap = new HashMap<>();
                        rutaMap.put("nombre", ruta.getNombre());
                        rutaMap.put("descripcion", ruta.getDescripcion());
                        rutaMap.put("fechaAlta", ruta.getFechaAlta() != null ? ruta.getFechaAlta().toString() : null);
                        rutaMap.put("costoBase", ruta.getCostoBase());
                        rutaMap.put("estado", ruta.getEstado() != null ? ruta.getEstado().toString() : null);
                        
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
                        
                        rutasData.add(rutaMap);
                    }
                    datos.put("rutas", rutasData);
                    LOG.info("Rutas de aerolínea agregadas: " + rutasData.size());
                }
            }
        } catch (Exception e) {
            LOG.warning("Error al extraer datos específicos del usuario: " + e.getMessage());
            datos.put("tipo", "desconocido");
        }

        return datos;
    }
}
