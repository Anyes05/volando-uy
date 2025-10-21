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
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/consulta-reservas - Información inicial según tipo de usuario
                handleGetInfoInicial(request, response, out);
            } else if (pathInfo.equals("/test")) {
                // GET /api/consulta-reservas/test - Endpoint de prueba
                handleGetTest(request, response, out);
            } else if (pathInfo.equals("/aerolineas")) {
                // GET /api/consulta-reservas/aerolineas - Listar aerolíneas (para clientes)
                handleGetAerolineas(request, response, out);
            } else if (pathInfo.startsWith("/rutas/")) {
                // GET /api/consulta-reservas/rutas/{aerolinea} - Listar rutas de aerolínea
                String aerolineaNickname = pathInfo.substring(7); // Remover "/rutas/"
                try {
                    aerolineaNickname = java.net.URLDecoder.decode(aerolineaNickname, "UTF-8");
                } catch (Exception e) {
                    LOG.warning("Error al decodificar aerolineaNickname: " + e.getMessage());
                }
                handleGetRutasAerolinea(aerolineaNickname, request, response, out);
            } else if (pathInfo.startsWith("/vuelos/")) {
                // GET /api/consulta-reservas/vuelos/{ruta} - Listar vuelos de ruta
                String rutaNombre = pathInfo.substring(8); // Remover "/vuelos/"
                try {
                    rutaNombre = java.net.URLDecoder.decode(rutaNombre, "UTF-8");
                } catch (Exception e) {
                    LOG.warning("Error al decodificar rutaNombre: " + e.getMessage());
                }
                handleGetVuelosRuta(rutaNombre, request, response, out);
            } else if (pathInfo.startsWith("/reservas-vuelo/")) {
                // GET /api/consulta-reservas/reservas-vuelo/{vuelo} - Listar reservas de vuelo
                String vueloNombre = pathInfo.substring(16); // Remover "/reservas-vuelo/"
                handleGetReservasVuelo(vueloNombre, request, response, out);
            } else if (pathInfo.startsWith("/reserva-cliente/")) {
                // GET /api/consulta-reservas/reserva-cliente/{vuelo} - Obtener reserva del cliente para vuelo
                String vueloNombre = pathInfo.substring(17); // Remover "/reserva-cliente/"
                handleGetReservaCliente(vueloNombre, request, response, out);
            } else if (pathInfo.startsWith("/detalle-reserva/")) {
                // GET /api/consulta-reservas/detalle-reserva/{reservaId} - Detalle completo de reserva
                String reservaId = pathInfo.substring(17); // Remover "/detalle-reserva/"
                handleGetDetalleReserva(reservaId, request, response, out);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Endpoint no encontrado\"}");
            }
        } catch (Exception e) {
            LOG.severe("Error en doGet: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error interno del servidor\"}");
            e.printStackTrace();
        }
        
        out.flush();
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
}
