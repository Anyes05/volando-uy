package com.volandouy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import logica.clase.Sistema;
import logica.DataTypes.*;
import dato.entidades.*;
import logica.servicios.*;

/**
 * Controlador para manejar las operaciones relacionadas con reservas de vuelo
 * Implementa el caso de uso completo de reserva para clientes
 */
@WebServlet("/api/reservas/*")
public class ReservaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ReservaController.class.getName());
    
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Object> reservas;
    private Sistema sistema = Sistema.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        cargarReservas();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Devolver todas las reservas del cliente autenticado
                handleGetReservasCliente(request, response, out);
            } else if (pathInfo.equals("/aerolineas")) {
                // Listar aerolíneas disponibles
                handleGetAerolineas(request, response, out);
            } else if (pathInfo.startsWith("/rutas/")) {
                // Obtener rutas de una aerolínea específica
                String aerolineaNickname = pathInfo.substring(7); // Remover "/rutas/"
                handleGetRutasPorAerolinea(aerolineaNickname, request, response, out);
            } else if (pathInfo.startsWith("/vuelos/")) {
                // Obtener vuelos de una ruta específica
                String rutaNombre = pathInfo.substring(8); // Remover "/vuelos/"
                handleGetVuelosPorRuta(rutaNombre, request, response, out);
            } else if (pathInfo.startsWith("/vuelo-detalle/")) {
                // Obtener detalles de un vuelo específico
                String vueloNombre = pathInfo.substring(15); // Remover "/vuelo-detalle/"
                handleGetDetalleVuelo(vueloNombre, request, response, out);
            } else if (pathInfo.startsWith("/paquetes-cliente")) {
                // Obtener paquetes disponibles del cliente
                handleGetPaquetesCliente(request, response, out);
            } else if (pathInfo.equals("/usuarios")) {
                // Listar todos los clientes disponibles para pasajeros
                handleGetClientes(request, response, out);
            } else if (pathInfo.equals("/usuarios-test")) {
                // Endpoint de prueba para clientes (sin autenticación)
                handleGetClientesTest(request, response, out);
            } else if (pathInfo.startsWith("/verificar-reserva-existente/")) {
                // Verificar si ya existe una reserva del cliente para un vuelo
                String vueloNombre = pathInfo.substring(29); // Remover "/verificar-reserva-existente/"
                handleVerificarReservaExistente(vueloNombre, request, response, out);
            } else {
                // Buscar reserva específica por ID
                String idStr = pathInfo.substring(1); // Remover la barra inicial
                handleGetReservaPorId(idStr, request, response, out);
            }
        } catch (Exception e) {
            LOG.severe("Error en doGet: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error interno del servidor\"}");
            e.printStackTrace();
        }
        
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Leer el cuerpo de la petición
            String body = request.getReader().lines().collect(Collectors.joining());
            LOG.info("Datos recibidos para nueva reserva: " + body);
            
            // Parsear el JSON de la nueva reserva
            Map<String, Object> reservaData = objectMapper.readValue(body, Map.class);
            
            // Crear la reserva usando el sistema
            handleCrearReserva(reservaData, request, response, out);
            
        } catch (Exception e) {
            LOG.severe("Error en doPost: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Error al procesar la petición: " + e.getMessage() + "\"}");
            out.flush();
            e.printStackTrace();
        }
    }

    /**
     * Carga las reservas desde el archivo JSON
     */
    private void cargarReservas() {
        try {
            InputStream inputStream = getServletContext().getResourceAsStream("/json/reservas.json");
            if (inputStream != null) {
                reservas = objectMapper.readValue(inputStream, new TypeReference<List<Object>>() {});
                inputStream.close();
            } else {
                reservas = new java.util.ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            reservas = new java.util.ArrayList<>();
        }
    }

    // ========== MÉTODOS PARA EL CASO DE USO DE RESERVA ==========

    /**
     * Obtiene las aerolíneas disponibles para hacer reservas
     */
    private void handleGetAerolineas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
            
            // Crear una lista simplificada para el frontend
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
     * Obtiene las rutas de vuelo de una aerolínea específica
     */
    private void handleGetRutasPorAerolinea(String aerolineaNickname, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            // Usar la función correcta del sistema que existe realmente
            List<DTRutaVuelo> rutas = sistema.listarRutaVuelo(aerolineaNickname);
            
            // Las rutas ya vienen filtradas por CONFIRMADA desde el sistema
            List<Map<String, Object>> rutasDisponibles = new ArrayList<>();
            for (DTRutaVuelo ruta : rutas) {
                Map<String, Object> rutaMap = new HashMap<>();
                rutaMap.put("nombre", ruta.getNombre());
                rutaMap.put("descripcion", ruta.getDescripcion());
                rutaMap.put("ciudadOrigen", ruta.getCiudadOrigen().getNombre());
                rutaMap.put("ciudadDestino", ruta.getCiudadDestino().getNombre());
                rutasDisponibles.add(rutaMap);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(rutasDisponibles));
            
        } catch (Exception e) {
            LOG.severe("Error al obtener rutas de aerolínea " + aerolineaNickname + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener rutas de la aerolínea\"}");
        }
    }

    /**
     * Obtiene los vuelos disponibles de una ruta específica
     */
    private void handleGetVuelosPorRuta(String rutaNombre, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(rutaNombre);
            
            // Crear lista simplificada de vuelos disponibles
            List<Map<String, Object>> vuelosDisponibles = new ArrayList<>();
            for (DTVuelo vuelo : vuelos) {
                Map<String, Object> vueloMap = new HashMap<>();
                vueloMap.put("nombre", vuelo.getNombre());
                vueloMap.put("fechaVuelo", vuelo.getFechaVuelo().toString());
                vueloMap.put("horaVuelo", vuelo.getHoraVuelo().toString());
                vueloMap.put("duracion", vuelo.getDuracion().toString());
                vueloMap.put("asientosMaxTurista", vuelo.getAsientosMaxTurista());
                vueloMap.put("asientosMaxEjecutivo", vuelo.getAsientosMaxEjecutivo());
                vuelosDisponibles.add(vueloMap);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(vuelosDisponibles));
            
        } catch (Exception e) {
            LOG.severe("Error al obtener vuelos de ruta " + rutaNombre + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener vuelos de la ruta\"}");
        }
    }

    /**
     * Obtiene los detalles completos de un vuelo específico
     */
    private void handleGetDetalleVuelo(String vueloNombre, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            // Buscar el vuelo entre todas las aerolíneas y rutas de forma más eficiente
            DTVuelo vuelo = null;
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();
            
            // Buscar el vuelo entre todas las aerolíneas y rutas
            outer: for (DTAerolinea aerolinea : aerolineas) {
                List<DTRutaVuelo> rutas = sistema.listarRutaVuelo(aerolinea.getNickname());
                for (DTRutaVuelo ruta : rutas) {
                    List<DTVuelo> vuelos = sistema.seleccionarRutaVuelo(ruta.getNombre());
                    for (DTVuelo v : vuelos) {
                        if (v.getNombre().equals(vueloNombre)) {
                            vuelo = v;
                            break outer;
                        }
                    }
                }
            }
            
            if (vuelo == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Vuelo no encontrado\"}");
                return;
            }

            // Crear objeto con detalles completos del vuelo
            Map<String, Object> detalleVuelo = new HashMap<>();
            detalleVuelo.put("nombre", vuelo.getNombre());
            detalleVuelo.put("fechaVuelo", vuelo.getFechaVuelo().toString());
            detalleVuelo.put("horaVuelo", vuelo.getHoraVuelo().toString());
            detalleVuelo.put("duracion", vuelo.getDuracion().toString());
            detalleVuelo.put("asientosMaxTurista", vuelo.getAsientosMaxTurista());
            detalleVuelo.put("asientosMaxEjecutivo", vuelo.getAsientosMaxEjecutivo());
            
            // Información de la ruta
            if (vuelo.getRuta() != null) {
                Map<String, Object> rutaInfo = new HashMap<>();
                rutaInfo.put("nombre", vuelo.getRuta().getNombre());
                rutaInfo.put("descripcion", vuelo.getRuta().getDescripcion());
                rutaInfo.put("costoBaseTurista", vuelo.getRuta().getCostoBase().getCostoTurista());
                rutaInfo.put("costoBaseEjecutivo", vuelo.getRuta().getCostoBase().getCostoEjecutivo());
                rutaInfo.put("costoEquipajeExtra", vuelo.getRuta().getCostoBase().getCostoEquipajeExtra());
                detalleVuelo.put("ruta", rutaInfo);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(detalleVuelo));
            
        } catch (Exception e) {
            LOG.severe("Error al obtener detalles del vuelo " + vueloNombre + ": " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener detalles del vuelo\"}");
        }
    }

    /**
     * Obtiene los paquetes disponibles del cliente autenticado
     */
    private void handleGetPaquetesCliente(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
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

            // Obtener paquetes del cliente - necesitaríamos una función en el sistema
            // Por ahora simular algunos paquetes disponibles del cliente
            List<Map<String, Object>> paquetesDisponibles = new ArrayList<>();
            
            // TODO: Implementar la función para obtener paquetes del cliente específico
            // Esto requeriría una nueva función en el Sistema como:
            // List<DTPaqueteVuelos> paquetesCliente = sistema.obtenerPaquetesCliente(nicknameCliente);
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(paquetesDisponibles));
            
        } catch (Exception e) {
            LOG.severe("Error al obtener paquetes del cliente: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener paquetes del cliente\"}");
        }
    }

    /**
     * Obtiene la lista de todos los clientes disponibles para ser seleccionados como pasajeros
     */
    private void handleGetClientes(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Iniciando handleGetClientes");
            
            // Verificar autenticación
            HttpSession session = request.getSession(false);
            if (session == null) {
                LOG.warning("Sesión no encontrada en handleGetClientes");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Usuario no autenticado\"}");
                return;
            }

            String nicknameCliente = (String) session.getAttribute("usuarioLogueado");
            if (nicknameCliente == null) {
                LOG.warning("Usuario no autenticado en handleGetClientes");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Usuario no autenticado\"}");
                return;
            }

            LOG.info("Usuario autenticado: " + nicknameCliente);

            // Obtener todos los clientes del sistema
            List<DTCliente> clientes = sistema.listarClientes();
            LOG.info("Clientes obtenidos del sistema: " + clientes.size());
            
            // Crear lista simplificada para el frontend (excluyendo al cliente principal)
            List<Map<String, Object>> clientesDisponibles = new ArrayList<>();
            for (DTCliente cliente : clientes) {
                // Excluir al cliente que está haciendo la reserva (ya está incluido automáticamente)
                if (!cliente.getNickname().equals(nicknameCliente)) {
                    Map<String, Object> clienteMap = new HashMap<>();
                    clienteMap.put("nickname", cliente.getNickname());
                    clienteMap.put("nombre", cliente.getNombre());
                    clienteMap.put("apellido", cliente.getApellido());
                    clienteMap.put("email", cliente.getCorreo());
                    clientesDisponibles.add(clienteMap);
                }
            }
            
            LOG.info("Clientes disponibles para pasajeros: " + clientesDisponibles.size());
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(clientesDisponibles));
            
        } catch (Exception e) {
            LOG.severe("Error al obtener clientes: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener clientes: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Endpoint de prueba para obtener clientes (sin autenticación)
     */
    private void handleGetClientesTest(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            LOG.info("Iniciando handleGetClientesTest");
            
            // Obtener todos los clientes del sistema
            List<DTCliente> clientes = sistema.listarClientes();
            LOG.info("Clientes obtenidos del sistema: " + clientes.size());
            
            // Crear lista simplificada para el frontend
            List<Map<String, Object>> clientesDisponibles = new ArrayList<>();
            for (DTCliente cliente : clientes) {
                Map<String, Object> clienteMap = new HashMap<>();
                clienteMap.put("nickname", cliente.getNickname());
                clienteMap.put("nombre", cliente.getNombre());
                clienteMap.put("apellido", cliente.getApellido());
                clienteMap.put("email", cliente.getCorreo());
                clientesDisponibles.add(clienteMap);
            }
            
            LOG.info("Clientes disponibles: " + clientesDisponibles.size());
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(clientesDisponibles));
            
        } catch (Exception e) {
            LOG.severe("Error al obtener clientes (test): " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener clientes: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Verifica si ya existe una reserva del cliente para un vuelo específico
     */
    private void handleVerificarReservaExistente(String vueloNombre, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
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

            // Verificar si existe reserva - usar función del sistema
            boolean existeReserva = false;
            try {
                // Buscar si el cliente ya tiene reserva en este vuelo
                List<DTVueloReserva> reservasVuelo = sistema.listarReservasVuelo(vueloNombre);
                for (DTVueloReserva vueloReserva : reservasVuelo) {
                    if (vueloReserva.getReserva().getNickname().equals(nicknameCliente)) {
                        existeReserva = true;
                        break;
                    }
                }
            } catch (Exception e) {
                // Si hay error al buscar reservas, asumir que no existe
                LOG.warning("Error al buscar reservas existentes: " + e.getMessage());
                existeReserva = false;
            }
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("existeReserva", existeReserva);
            resultado.put("opciones", new String[]{"cambiar_aerolinea", "cambiar_ruta", "cambiar_vuelo", "cancelar"});
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(resultado));
            
        } catch (Exception e) {
            LOG.severe("Error al verificar reserva existente: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al verificar reserva existente\"}");
        }
    }

    /**
     * Crea una nueva reserva con todos los datos proporcionados
     */
    private void handleCrearReserva(Map<String, Object> reservaData, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
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

            // Extraer datos de la reserva
            String vueloNombre = (String) reservaData.get("vueloNombre");
            String tipoAsiento = (String) reservaData.get("tipoAsiento");
            Integer cantidadPasajes = (Integer) reservaData.get("cantidadPasajes");
            Integer equipajeExtra = (Integer) reservaData.get("equipajeExtra");
            String formaPago = (String) reservaData.get("formaPago");
            String paqueteId = (String) reservaData.get("paqueteId");
            @SuppressWarnings("unchecked")
            List<Map<String, String>> pasajeros = (List<Map<String, String>>) reservaData.get("pasajeros");

            // Validar datos requeridos
            if (vueloNombre == null || tipoAsiento == null || cantidadPasajes == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Faltan datos obligatorios para la reserva\"}");
                return;
            }

            // Seleccionar vuelo para la reserva (igual que en Swing)
            sistema.seleccionarVueloParaReserva(vueloNombre);

            // Crear lista de nombres de pasajeros - incluir al cliente principal
            List<String> nombresPasajeros = new ArrayList<>();
            nombresPasajeros.add(nicknameCliente); // Agregar el cliente principal primero
            
            // Agregar pasajeros adicionales usando sus nicknames
            if (pasajeros != null) {
                for (Map<String, String> pasajero : pasajeros) {
                    String nicknamePasajero = pasajero.get("nickname");
                    if (nicknamePasajero != null && !nicknamePasajero.trim().isEmpty()) {
                        // Validar que el nickname existe en el sistema
                        String nicknameValidado = validarNicknamePasajero(nicknamePasajero);
                        if (nicknameValidado != null) {
                            sistema.nombresPasajes(nicknameValidado, nombresPasajeros);
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("{\"error\": \"El nickname '" + nicknamePasajero + "' no está registrado en el sistema\"}");
                            return;
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"error\": \"El nickname es obligatorio para todos los pasajeros\"}");
                        return;
                    }
                }
            }

            // Determinar tipo de asiento
            TipoAsiento tipoAsientoEnum = "ejecutivo".equals(tipoAsiento) ? TipoAsiento.Ejecutivo : TipoAsiento.Turista;

            // Crear la reserva usando el mismo método que en Swing
            if ("paquete".equals(formaPago) && paqueteId != null && !paqueteId.isEmpty()) {
                // Reserva con paquete - TODO: Implementar cuando esté listo
                response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                out.print("{\"error\": \"Reserva con paquete no implementada aún\"}");
                return;
            } else {
                // Reserva normal - usar la misma función que usa Swing
                // Crear la fecha actual para la reserva
                java.time.LocalDate hoy = java.time.LocalDate.now();
                DTFecha fechaReserva = new DTFecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
                
                try {
                    sistema.datosReserva(tipoAsientoEnum, cantidadPasajes, equipajeExtra != null ? equipajeExtra : 0, nombresPasajeros, fechaReserva);
                    
                    // Si llegamos aquí sin excepción, algo inesperado pasó
                    // porque datosReserva siempre lanza IllegalStateException con SUCCESS: cuando es exitoso
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print("{\"error\": \"Error inesperado en la reserva\"}");
                    return;
                    
                } catch (IllegalStateException e) {
                    // Verificar si es un mensaje de éxito especial (como en ReservaHelper)
                    if (e.getMessage() != null && e.getMessage().startsWith("SUCCESS:")) {
                        String mensajeExito = e.getMessage().substring(8); // Remover "SUCCESS:"
                        
                        // Respuesta exitosa
                        Map<String, Object> respuesta = new HashMap<>();
                        respuesta.put("success", true);
                        respuesta.put("mensaje", "Reserva creada exitosamente");
                        respuesta.put("detalles", mensajeExito);
                        respuesta.put("fechaReserva", new java.util.Date().toString());
                        respuesta.put("cliente", nicknameCliente);
                        respuesta.put("vuelo", vueloNombre);

                        response.setStatus(HttpServletResponse.SC_CREATED);
                        out.print(objectMapper.writeValueAsString(respuesta));

                        // Agregar a la lista local para compatibilidad
                        reservas.add(respuesta);
                        return;
                        
                    } else if (e.getMessage() != null && e.getMessage().startsWith("ADMIN_REQUIRED:")) {
                        // Manejo de conflictos de reserva - cliente ya tiene reserva para este vuelo
                        String mensajeConflicto = e.getMessage().substring(15); // Remover "ADMIN_REQUIRED:"
                        
                        Map<String, Object> conflictoRespuesta = new HashMap<>();
                        conflictoRespuesta.put("conflicto", true);
                        conflictoRespuesta.put("mensaje", "Ya existe una reserva para este vuelo");
                        conflictoRespuesta.put("detalles", mensajeConflicto);
                        conflictoRespuesta.put("cliente", nicknameCliente);
                        conflictoRespuesta.put("vuelo", vueloNombre);
                        
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        out.print(objectMapper.writeValueAsString(conflictoRespuesta));
                        return;
                    } else {
                        // Otros errores del sistema
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"error\": \"" + e.getMessage() + "\"}");
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    // Errores de validación de datos
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\": \"" + e.getMessage() + "\"}");
                    return;
                }
            }

        } catch (Exception e) {
            // Manejar conflictos de reserva similar al Swing
            if (e.getMessage() != null && e.getMessage().startsWith("ADMIN_REQUIRED:")) {
                // Conflicto que requiere administración - devolver opciones
                Map<String, Object> conflicto = new HashMap<>();
                conflicto.put("error", "CONFLICT");
                conflicto.put("mensaje", e.getMessage().substring("ADMIN_REQUIRED:".length()));
                conflicto.put("opciones", new String[]{
                    "1. Cambiar aerolínea",
                    "2. Cambiar ruta de vuelo", 
                    "3. Cambiar vuelo",
                    "4. Cambiar cliente",
                    "5. Cancelar caso de uso"
                });
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.print(objectMapper.writeValueAsString(conflicto));
            } else {
                // Error normal
                LOG.severe("Error al crear reserva: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\": \"Error al crear la reserva: " + e.getMessage() + "\"}");
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtiene las reservas del cliente autenticado
     */
    private void handleGetReservasCliente(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
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

            // Por ahora devolver las reservas del JSON y las agregadas en memoria
            // TODO: Implementar función específica para obtener reservas de cliente desde el sistema
            
            // Filtrar solo las reservas del cliente actual
            List<Map<String, Object>> reservasCliente = new ArrayList<>();
            for (Object obj : reservas) {
                if (obj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> reserva = (Map<String, Object>) obj;
                    if (nicknameCliente.equals(reserva.get("cliente"))) {
                        reservasCliente.add(reserva);
                    }
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(reservasCliente));

        } catch (Exception e) {
            LOG.severe("Error al obtener reservas del cliente: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al obtener reservas del cliente\"}");
        }
    }

    /**
     * Obtiene una reserva específica por ID
     */
    private void handleGetReservaPorId(String idStr, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            int id = Integer.parseInt(idStr);
            
            Object reserva = reservas.stream()
                .filter(r -> {
                    try {
                        java.util.Map<String, Object> reservaMap = objectMapper.convertValue(r, java.util.Map.class);
                        return reservaMap.get("id").equals(id);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);
            
            if (reserva != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(reserva));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Reserva no encontrada\"}");
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"ID de reserva inválido\"}");
        } catch (Exception e) {
            LOG.severe("Error al obtener reserva por ID: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error interno del servidor\"}");
        }
    }

    /**
     * Valida que un email de pasajero existe en el sistema y devuelve su nickname
     * @param email El email a validar
     * @return El nickname del cliente si existe, null si no existe
     */
    private String validarEmailPasajero(String email) {
        try {
            // Obtener todos los clientes del sistema
            List<DTCliente> clientes = sistema.listarClientes();
            
            // Buscar el cliente por email
            for (DTCliente cliente : clientes) {
                if (cliente.getCorreo().equalsIgnoreCase(email.trim())) {
                    return cliente.getNickname(); // Devolver el nickname del cliente
                }
            }
            
            return null; // No encontrado
            
        } catch (Exception e) {
            LOG.warning("Error al validar email de pasajero: " + e.getMessage());
            return null;
        }
    }

    /**
     * Valida que un nickname de pasajero existe en el sistema
     * @param nickname El nickname a validar
     * @return El nickname si existe, null si no existe
     */
    private String validarNicknamePasajero(String nickname) {
        try {
            // Obtener todos los clientes del sistema
            List<DTCliente> clientes = sistema.listarClientes();
            
            // Buscar el cliente por nickname
            for (DTCliente cliente : clientes) {
                if (cliente.getNickname().equalsIgnoreCase(nickname.trim())) {
                    return cliente.getNickname(); // Devolver el nickname exacto del sistema
                }
            }
            
            return null; // No encontrado
            
        } catch (Exception e) {
            LOG.warning("Error al validar nickname de pasajero: " + e.getMessage());
            return null;
        }
    }
}
