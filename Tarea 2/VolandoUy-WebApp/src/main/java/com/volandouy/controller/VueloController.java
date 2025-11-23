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
import logica.DataTypes.DTHora;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTVuelo;
import logica.DataTypes.DTVueloReserva;
import logica.DataTypes.EstadoRutaVuelo;
import com.volandouy.central.CentralService;
import com.volandouy.central.ServiceFactory;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // La inicialización es lazy, se hace automáticamente al llamar getCentralService()

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

                LOG.info("Invocando getCentralService().ingresarDatosVuelo(...)");
                getCentralService().seleccionarAerolinea(nickname);
                DTRutaVuelo rutaVuelo = getCentralService().seleccionarRutaVueloRet(ruta);
                getCentralService().ingresarDatosVuelo(
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

                getCentralService().darAltaVuelo();

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        LOG.info("=== DOGET VUELO CONTROLLER ===");
        LOG.info("Request URI: " + request.getRequestURI());
        LOG.info("Path Info: " + request.getPathInfo());
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // La inicialización es lazy, se hace automáticamente al llamar getCentralService()

            String pathInfo = request.getPathInfo(); // ejemplo: /aerolineas, /rutas/latam, /vuelos/montevideo-madrid
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/vuelos/ - Sin especificar recurso específico
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Debe especificar un recurso: aerolineas, rutas/{nickname}, vuelos/{nombreRuta}, reservas/{nombreVuelo}")));
                return;
            }

            String[] pathParts = pathInfo.split("/");
            
            if (pathParts.length >= 2) {
                String resource = pathParts[1]; // primera parte después de /
                
                switch (resource) {
                    case "aerolineas":
                        handleGetAerolineas(response, out);
                        break;
                        
                    case "rutas":
                        if (pathParts.length >= 3) {
                            String nickname = pathParts[2];
                            handleGetRutas(nickname, response, out);
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print(objectMapper.writeValueAsString(Map.of("error", "Debe especificar el nickname de la aerolínea: /api/vuelos/rutas/{nickname}")));
                        }
                        break;
                        
                    case "vuelos":
                        if (pathParts.length >= 3) {
                            String nombreRuta = pathParts[2];
                            handleGetVuelos(nombreRuta, response, out);
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print(objectMapper.writeValueAsString(Map.of("error", "Debe especificar el nombre de la ruta: /api/vuelos/vuelos/{nombreRuta}")));
                        }
                        break;
                        
                    case "reservas":
                        if (pathParts.length >= 3) {
                            String nombreVuelo = pathParts[2];
                            handleGetReservas(nombreVuelo, request, response, out);
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print(objectMapper.writeValueAsString(Map.of("error", "Debe especificar el nombre del vuelo: /api/vuelos/reservas/{nombreVuelo}")));
                        }
                        break;
                        
                    default:
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Recurso no encontrado: " + resource)));
                        break;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Ruta inválida")));
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error en GET /api/vuelos", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor", "detail", ex.getMessage())));
        } finally {
            out.flush();
        }
    }

    /**
     * GET /api/vuelos/aerolineas - Lista todas las aerolíneas
     */
    private void handleGetAerolineas(HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("=== HANDLE GET AEROLINEAS ===");
            List<DTAerolinea> aerolineas = getCentralService().listarAerolineas();
            LOG.info("Aerolíneas obtenidas: " + (aerolineas != null ? aerolineas.size() : "null"));
            
            List<Map<String, Object>> result = new ArrayList<>();
            for (DTAerolinea aero : aerolineas) {
                Map<String, Object> aeroMap = new HashMap<>();
                aeroMap.put("nickname", aero.getNickname());
                aeroMap.put("nombre", aero.getNombre());
                aeroMap.put("correo", aero.getCorreo());
                aeroMap.put("descripcion", aero.getDescripcion());
                aeroMap.put("linkSitioWeb", aero.getLinkSitioWeb());
                result.add(aeroMap);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(result));
            
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener aerolíneas", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener aerolíneas", "detail", ex.getMessage())));
        }
    }

    /**
     * GET /api/vuelos/rutas/{nickname} - Lista rutas confirmadas de una aerolínea
     */
    private void handleGetRutas(String nickname, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTRutaVuelo> rutas = getCentralService().listarRutasPorAerolinea(nickname);
            
            List<Map<String, Object>> result = new ArrayList<>();
            for (DTRutaVuelo ruta : rutas) {
                // Solo mostrar rutas confirmadas para consulta de vuelos
                if (ruta.getEstado() == EstadoRutaVuelo.CONFIRMADA) {
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
                    
                    result.add(rutaMap);
                }
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(result));
            
        } catch (IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Aerolínea no encontrada", "detail", ex.getMessage())));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener rutas de aerolínea: " + nickname, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener rutas", "detail", ex.getMessage())));
        }
    }

    /**
     * GET /api/vuelos/vuelos/{nombreRuta} - Lista vuelos de una ruta específica
     */
    private void handleGetVuelos(String nombreRuta, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTVuelo> vuelos = getCentralService().listarVuelosDeRuta(nombreRuta);
            
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
                
                // Incluir información de la ruta
                if (vuelo.getRuta() != null) {
                    Map<String, Object> rutaMap = new HashMap<>();
                    rutaMap.put("nombre", vuelo.getRuta().getNombre());
                    rutaMap.put("descripcion", vuelo.getRuta().getDescripcion());
                    vueloMap.put("ruta", rutaMap);
                }
                
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

    /**
     * GET /api/vuelos/reservas/{nombreVuelo} - Lista reservas de un vuelo según tipo de usuario
     */
    private void handleGetReservas(String nombreVuelo, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            // Obtener información de sesión
            HttpSession session = request.getSession(false);
            String usuarioLogueado = null;
            String tipoUsuario = null;
            
            if (session != null) {
                usuarioLogueado = (String) session.getAttribute("usuarioLogueado");
                tipoUsuario = (String) session.getAttribute("tipoUsuario");
            }

            // Validar si usuario no tiene permisos
            if (!"aerolinea".equalsIgnoreCase(tipoUsuario) && !"cliente".equalsIgnoreCase(tipoUsuario)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Sin permisos para ver reservas")));
                return;
            }
            
            List<DTVueloReserva> reservas = getCentralService().listarReservasVuelo(nombreVuelo);
            List<Map<String, Object>> result = new ArrayList<>();
            
            // Verificar si la aerolínea es propietaria del vuelo (se verificará al procesar cada reserva)
            
            for (DTVueloReserva vueloReserva : reservas) {
                // Verificar permisos según el tipo de usuario
                if ("aerolinea".equalsIgnoreCase(tipoUsuario) && usuarioLogueado != null) {
                    // Verificar si la aerolínea es propietaria del vuelo
                    boolean esAerolineaPropietaria = false;
                    try {
                        DTVuelo vuelo = vueloReserva.getVuelo();
                        if (vuelo != null && vuelo.getRuta() != null && vuelo.getRuta().getAerolinea() != null) {
                            esAerolineaPropietaria = usuarioLogueado.equals(vuelo.getRuta().getAerolinea().getNickname());
                        }
                    } catch (Exception e) {
                        LOG.log(Level.WARNING, "Error verificando propiedad del vuelo: " + e.getMessage(), e);
                    }
                    
                    // Solo las aerolíneas propietarias pueden ver las reservas de sus vuelos
                    if (esAerolineaPropietaria) {
                        Map<String, Object> reservaMap = new HashMap<>();
                        reservaMap.put("id", vueloReserva.getReserva().getId());
                        reservaMap.put("cliente", vueloReserva.getReserva().getNickname());
                        reservaMap.put("fechaReserva", vueloReserva.getReserva().getFechaReserva() != null ? vueloReserva.getReserva().getFechaReserva().toString() : null);
                        // Extraer el costo total del objeto DTCostoBase
                        Object costoReservaObj = vueloReserva.getReserva().getCostoReserva();
                        float costoTotal = 0.0f;
                        if (costoReservaObj instanceof logica.DataTypes.DTCostoBase) {
                            costoTotal = ((logica.DataTypes.DTCostoBase) costoReservaObj).getCostoTotal();
                        } else if (costoReservaObj instanceof Number) {
                            costoTotal = ((Number) costoReservaObj).floatValue();
                        }
                        reservaMap.put("costoReserva", costoTotal);
                        
                        // TODO: Los pasajeros deberían venir en DTVueloReserva o necesitamos un método del Web Service
                        // Por ahora, omitimos la información de pasajeros ya que no está disponible en el DTO
                        /*
                        // Incluir información de pasajeros si está disponible
                        try {
                            // Obtener la reserva real de la base de datos para acceder a los pasajeros
                            ReservaServicio reservaServicio = new ReservaServicio();
                            dato.entidades.Reserva reservaReal = reservaServicio.buscarPorId(vueloReserva.getReserva().getId());
                            if (reservaReal != null && reservaReal.getPasajeros() != null && !reservaReal.getPasajeros().isEmpty()) {
                                List<Map<String, Object>> pasajerosData = new ArrayList<>();
                                for (dato.entidades.Pasaje pasaje : reservaReal.getPasajeros()) {
                                    String nombre = pasaje.getNombrePasajero();
                                    String apellido = pasaje.getApellidoPasajero();
                                    
                                    // Si los campos específicos están vacíos, usar datos del cliente
                                    if (nombre == null || nombre.trim().isEmpty()) {
                                        nombre = pasaje.getPasajero() != null ? pasaje.getPasajero().getNombre() : "N/A";
                                    }
                                    if (apellido == null || apellido.trim().isEmpty()) {
                                        apellido = pasaje.getPasajero() != null ? pasaje.getPasajero().getApellido() : "N/A";
                                    }
                                    
                                    Map<String, Object> pasajeroData = new HashMap<>();
                                    pasajeroData.put("nombre", nombre != null ? nombre.trim() : "");
                                    pasajeroData.put("apellido", apellido != null ? apellido.trim() : "");
                                    pasajeroData.put("nickname", pasaje.getPasajero() != null ? pasaje.getPasajero().getNickname() : null);
                                    pasajeroData.put("tipoAsiento", pasaje.getTipoAsiento() != null ? pasaje.getTipoAsiento().toString() : null);
                                    
                                    pasajerosData.add(pasajeroData);
                                }
                                reservaMap.put("pasajeros", pasajerosData);
                            }
                        } catch (Exception e) {
                            LOG.log(Level.WARNING, "Error obteniendo pasajeros de reserva: " + e.getMessage(), e);
                        }
                        */
                        
                        result.add(reservaMap);
                    }
                    
                } else if ("cliente".equalsIgnoreCase(tipoUsuario) && usuarioLogueado != null) {
                    // Los clientes solo pueden ver sus propias reservas
                    if (usuarioLogueado.equals(vueloReserva.getReserva().getNickname())) {
                        Map<String, Object> reservaMap = new HashMap<>();
                        reservaMap.put("id", vueloReserva.getReserva().getId());
                        reservaMap.put("cliente", vueloReserva.getReserva().getNickname());
                        reservaMap.put("fechaReserva", vueloReserva.getReserva().getFechaReserva() != null ? vueloReserva.getReserva().getFechaReserva().toString() : null);
                        // Extraer el costo total del objeto DTCostoBase
                        Object costoReservaObj = vueloReserva.getReserva().getCostoReserva();
                        float costoTotal = 0.0f;
                        if (costoReservaObj instanceof logica.DataTypes.DTCostoBase) {
                            costoTotal = ((logica.DataTypes.DTCostoBase) costoReservaObj).getCostoTotal();
                        } else if (costoReservaObj instanceof Number) {
                            costoTotal = ((Number) costoReservaObj).floatValue();
                        }
                        reservaMap.put("costoReserva", costoTotal);
                        
                        // TODO: Los pasajeros deberían venir en DTVueloReserva o necesitamos un método del Web Service
                        // Por ahora, omitimos la información de pasajeros ya que no está disponible en el DTO
                        /*
                        // Incluir información de pasajeros si está disponible
                        try {
                            // Obtener la reserva real de la base de datos para acceder a los pasajeros
                            ReservaServicio reservaServicio = new ReservaServicio();
                            dato.entidades.Reserva reservaReal = reservaServicio.buscarPorId(vueloReserva.getReserva().getId());
                            if (reservaReal != null && reservaReal.getPasajeros() != null && !reservaReal.getPasajeros().isEmpty()) {
                                List<Map<String, Object>> pasajerosData = new ArrayList<>();
                                for (dato.entidades.Pasaje pasaje : reservaReal.getPasajeros()) {
                                    String nombre = pasaje.getNombrePasajero();
                                    String apellido = pasaje.getApellidoPasajero();
                                    
                                    // Si los campos específicos están vacíos, usar datos del cliente
                                    if (nombre == null || nombre.trim().isEmpty()) {
                                        nombre = pasaje.getPasajero() != null ? pasaje.getPasajero().getNombre() : "N/A";
                                    }
                                    if (apellido == null || apellido.trim().isEmpty()) {
                                        apellido = pasaje.getPasajero() != null ? pasaje.getPasajero().getApellido() : "N/A";
                                    }
                                    
                                    Map<String, Object> pasajeroData = new HashMap<>();
                                    pasajeroData.put("nombre", nombre != null ? nombre.trim() : "");
                                    pasajeroData.put("apellido", apellido != null ? apellido.trim() : "");
                                    pasajeroData.put("nickname", pasaje.getPasajero() != null ? pasaje.getPasajero().getNickname() : null);
                                    pasajeroData.put("tipoAsiento", pasaje.getTipoAsiento() != null ? pasaje.getTipoAsiento().toString() : null);
                                    
                                    pasajerosData.add(pasajeroData);
                                }
                                reservaMap.put("pasajeros", pasajerosData);
                            }
                        } catch (Exception e) {
                            LOG.log(Level.WARNING, "Error obteniendo pasajeros de reserva: " + e.getMessage(), e);
                        }
                        */
                        
                        result.add(reservaMap);
                    }
                }
                // Aerolíneas no propietarias y visitantes anónimos no pueden ver reservas
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(result));
            
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener reservas del vuelo: " + nombreVuelo, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al obtener reservas", "detail", ex.getMessage())));
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