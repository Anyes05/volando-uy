package com.volandouy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.volandouy.central.CentralService;
import com.volandouy.central.ServiceFactory;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTCliente;
import logica.DataTypes.DTPaqueteVuelos;

/**
 * Controlador para manejar las operaciones de compra de paquetes
 * Implementa el caso de uso "Compra de Paquete de Rutas de Vuelo"
 */
@WebServlet("/api/compra-paquetes/*")
public class CompraPaqueteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CompraPaqueteController.class.getName());
    private final ObjectMapper objectMapper;
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

    public CompraPaqueteController() {
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
        
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/compra-paquetes - Verificar si cliente ya compró un paquete específico
                String nombrePaquete = request.getParameter("paquete");
                
                if (nombrePaquete == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Parámetro 'paquete' es requerido")));
                    return;
                }
                
                // Obtener cliente desde la sesión
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("usuarioLogueado") == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no autenticado")));
                    return;
                }
                
                String clienteNickname = (String) session.getAttribute("usuarioLogueado");
                
                LOG.info("GET /api/compra-paquetes - Verificando compra previa para paquete: " + nombrePaquete + ", cliente: " + clienteNickname);
                
                try {
                    // Seleccionar cliente y paquete en el sistema
                    getCentralService().seleccionarCliente(clienteNickname);
                    getCentralService().seleccionarPaquete(nombrePaquete);
                    
                    // Verificar si ya compró el paquete
                    boolean yaCompro = getCentralService().clienteYaComproPaquete();
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(Map.of("yaCompro", yaCompro)));
                    
                } catch (IllegalArgumentException ex) {
                    LOG.warning("Error al verificar compra previa: " + ex.getMessage());
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(objectMapper.writeValueAsString(Map.of("error", ex.getMessage())));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al verificar compra previa", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error inesperado en CompraPaqueteController GET", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor")));
        }
        
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Leer el cuerpo de la petición
            String body = request.getReader().lines().collect(java.util.stream.Collectors.joining());
            
            // Parsear el JSON de la compra
            Map<String, Object> compraData = objectMapper.readValue(body, Map.class);
            
            String nombrePaquete = (String) compraData.get("nombrePaquete");
            Float costo = null;
            if (compraData.get("costo") instanceof Number) {
                costo = ((Number) compraData.get("costo")).floatValue();
            }
            
            if (nombrePaquete == null || costo == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Parámetros requeridos: nombrePaquete, costo")));
                return;
            }
            
            // Obtener cliente desde la sesión
            HttpSession session = request.getSession(false);
            
            // Logging detallado para debugging
            LOG.info("POST /api/compra-paquetes - Verificando sesión");
            LOG.info("Session ID: " + (session != null ? session.getId() : "null"));
            LOG.info("Session isNew: " + (session != null ? session.isNew() : "N/A"));
            if (session != null) {
                LOG.info("Session attributes: " + java.util.Collections.list(session.getAttributeNames()));
                LOG.info("usuarioLogueado attribute: " + session.getAttribute("usuarioLogueado"));
            }
            
            if (session == null) {
                LOG.warning("No hay sesión activa");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "No hay sesión activa. Por favor, inicia sesión.")));
                return;
            }
            
            Object usuarioLogueadoObj = session.getAttribute("usuarioLogueado");
            if (usuarioLogueadoObj == null) {
                LOG.warning("No hay usuario logueado en la sesión");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Usuario no autenticado. Por favor, inicia sesión.")));
                return;
            }
            
            String clienteNickname = (String) usuarioLogueadoObj;
            LOG.info("Cliente identificado: " + clienteNickname);
            
            LOG.info("POST /api/compra-paquetes - Procesando compra para paquete: " + nombrePaquete + ", cliente: " + clienteNickname + ", costo: " + costo);
            
            try {
                // Seleccionar cliente y paquete en el sistema
                getCentralService().seleccionarCliente(clienteNickname);
                getCentralService().seleccionarPaquete(nombrePaquete);
                
                // Verificar si ya compró el paquete
                if (getCentralService().clienteYaComproPaquete()) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "El cliente ya compró este paquete anteriormente")));
                    return;
                }
                
                // Obtener información del paquete para calcular vencimiento
                DTPaqueteVuelos paqueteInfo = getCentralService().consultaPaqueteVuelo();
                
                // Calcular fechas
                LocalDate hoy = LocalDate.now();
                DTFecha fechaCompra = new DTFecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
                LocalDate vencimientoLocalDate = hoy.plusDays(paqueteInfo.getDiasValidos());
                DTFecha vencimiento = new DTFecha(
                    vencimientoLocalDate.getDayOfMonth(),
                    vencimientoLocalDate.getMonthValue(),
                    vencimientoLocalDate.getYear()
                );
                
                // Realizar la compra
                getCentralService().realizarCompra(fechaCompra, costo, vencimiento);
                
                // Crear respuesta con información de la compra
                Map<String, Object> compraResponse = new HashMap<>();
                compraResponse.put("paquete", nombrePaquete);
                compraResponse.put("cliente", clienteNickname);
                compraResponse.put("costo", costo);
                compraResponse.put("fechaCompra", fechaCompra.toString());
                compraResponse.put("vencimiento", vencimiento.toString());
                compraResponse.put("mensaje", "Compra realizada exitosamente");
                
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(objectMapper.writeValueAsString(Map.of("compra", compraResponse)));
                
                LOG.info("Compra realizada exitosamente para cliente: " + clienteNickname + ", paquete: " + nombrePaquete);
                
            } catch (IllegalArgumentException ex) {
                LOG.warning("Error en la compra: " + ex.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", ex.getMessage())));
            } catch (IllegalStateException ex) {
                LOG.warning("Estado inválido para la compra: " + ex.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", ex.getMessage())));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al procesar la compra", ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
            }
            
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error inesperado en CompraPaqueteController POST", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor")));
        }
        
        out.flush();
    }
}
