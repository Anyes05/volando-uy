package com.volandouy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.volandouy.central.CentralService;
import com.volandouy.central.ServiceFactory;
import logica.DataTypes.DTPaqueteVuelos;
import logica.DataTypes.DTRutaVuelo;

/**
 * Controlador para manejar las operaciones relacionadas con paquetes
 */
@WebServlet("/api/paquetes/*")
public class PaqueteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(PaqueteController.class.getName());
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

    public PaqueteController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        this.objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Convierte un array de bytes de foto a Base64 string
     */
    private String convertirFotoABase64(byte[] fotoBytes) {
        if (fotoBytes == null || fotoBytes.length == 0) {
            return null;
        }
        try {
            return Base64.getEncoder().encodeToString(fotoBytes);
        } catch (Exception e) {
            LOG.warning("Error convirtiendo foto a Base64: " + e.getMessage());
            return null;
        }
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
                // GET /api/paquetes - Listar todos los paquetes
                LOG.info("GET /api/paquetes - Listando paquetes");
                try {
                    List<DTPaqueteVuelos> paquetes = getCentralService().mostrarPaquetes();
                    
                    // Convertir paquetes a formato simplificado para evitar problemas de serialización
                    List<Map<String, Object>> paquetesSimples = new ArrayList<>();
                    for (DTPaqueteVuelos paquete : paquetes) {
                        // El costo total viene en DTPaqueteVuelos
                        float costoTotal = paquete.getCostoTotal();
                        
                        // Log para debugging
                        LOG.info("Paquete: " + paquete.getNombre() + ", CostoTotal: " + costoTotal);
                        
                        Map<String, Object> paqueteSimple = new HashMap<>();
                        paqueteSimple.put("nombre", paquete.getNombre() != null ? paquete.getNombre() : "");
                        paqueteSimple.put("descripcion", paquete.getDescripcion() != null ? paquete.getDescripcion() : "");
                        paqueteSimple.put("diasValidos", paquete.getDiasValidos());
                        paqueteSimple.put("descuento", paquete.getDescuento());
                        paqueteSimple.put("costoTotal", costoTotal);
                        paqueteSimple.put("fechaAlta", paquete.getFechaAlta() != null ? paquete.getFechaAlta().toString() : "");
                        paqueteSimple.put("foto", convertirFotoABase64(paquete.getFoto()));
                        paquetesSimples.add(paqueteSimple);
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    try {
                        out.print(objectMapper.writeValueAsString(Map.of("paquetes", paquetesSimples)));
                    } catch (Exception e) {
                        LOG.severe("Error al serializar paquetes: " + e.getMessage());
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(objectMapper.writeValueAsString(Map.of("error", "Error al serializar respuesta")));
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al consultar paquetes", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
                }
            } else if (pathInfo.startsWith("/")) {
                // GET /api/paquetes/{nombre} - Obtener paquete específico
                String nombrePaquete = pathInfo.substring(1);
                LOG.info("GET /api/paquetes/" + nombrePaquete + " - Consultando paquete específico");
                
                try {
                    // Seleccionar el paquete
                    centralService.seleccionarPaquete(nombrePaquete);
                    
                    // Obtener datos del paquete
                    DTPaqueteVuelos paquete = centralService.consultaPaqueteVuelo();
                    
                    // Obtener rutas del paquete
                    List<DTRutaVuelo> rutas = centralService.consultaPaqueteVueloRutas();
                    
                    // Obtener cantidades del paquete
                        List<logica.DataTypes.DTCantidad> cantidades = paquete.getCantidad();
                    
                    // Crear respuesta simplificada para evitar problemas de serialización
                    Map<String, Object> paqueteSimple = Map.of(
                        "nombre", paquete.getNombre() != null ? paquete.getNombre() : "",
                        "descripcion", paquete.getDescripcion() != null ? paquete.getDescripcion() : "",
                        "diasValidos", paquete.getDiasValidos(),
                        "descuento", paquete.getDescuento(),
                        "costoTotal", paquete.getCostoTotal(),
                        "fechaAlta", paquete.getFechaAlta() != null ? paquete.getFechaAlta().toString() : "",
                        "foto", convertirFotoABase64(paquete.getFoto())
                    );
                    
                    // Crear rutas simplificadas con cantidades
                    List<Map<String, Object>> rutasSimples = new ArrayList<>();
                    for (DTRutaVuelo ruta : rutas) {
                        // Buscar la cantidad correspondiente a esta ruta
                        int cantidad = 0;
                        String tipoAsiento = "No especificado";
                        for (logica.DataTypes.DTCantidad c : cantidades) {
                            if (c != null && c.getRutaVueloNombre() != null && c.getRutaVueloNombre().equals(ruta.getNombre())) {
                                cantidad = c.getCant();
                                tipoAsiento = c.getTipoAsiento() != null ? c.getTipoAsiento().toString() : "No especificado";
                                break;
                            }
                        }
                        
                        Map<String, Object> rutaSimple = Map.of(
                            "nombre", ruta.getNombre() != null ? ruta.getNombre() : "",
                            "descripcion", ruta.getDescripcion() != null ? ruta.getDescripcion() : "",
                            "fechaAlta", ruta.getFechaAlta() != null ? ruta.getFechaAlta().toString() : "",
                            "ciudadOrigen", ruta.getCiudadOrigen() != null ? ruta.getCiudadOrigen().toString() : "",
                            "ciudadDestino", ruta.getCiudadDestino() != null ? ruta.getCiudadDestino().toString() : "",
                            "costoBaseTurista", ruta.getCostoBase() != null ? ruta.getCostoBase().getCostoTurista() : 0,
                            "costoBaseEjecutivo", ruta.getCostoBase() != null ? ruta.getCostoBase().getCostoEjecutivo() : 0,
                            "cantidad", cantidad,
                            "tipoAsiento", tipoAsiento
                        );
                        rutasSimples.add(rutaSimple);
                    }
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(objectMapper.writeValueAsString(Map.of(
                        "paquete", paqueteSimple,
                        "rutas", rutasSimples
                    )));
                } catch (IllegalArgumentException ex) {
                    LOG.warning("Paquete no encontrado: " + nombrePaquete);
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Paquete no encontrado: " + ex.getMessage())));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al consultar paquete: " + nombrePaquete, ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor: " + ex.getMessage())));
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error inesperado en PaqueteController", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno del servidor")));
        }
        
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Leer el cuerpo de la petición
            String body = request.getReader().lines().collect(java.util.stream.Collectors.joining());
            
            // Parsear el JSON del nuevo paquete
            Object nuevoPaquete = objectMapper.readValue(body, Object.class);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Paquete creado exitosamente\"}");
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al procesar la petición\"}");
            out.flush();
            e.printStackTrace();
        }
    }
}
