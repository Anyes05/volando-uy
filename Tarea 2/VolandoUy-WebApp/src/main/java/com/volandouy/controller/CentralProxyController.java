package com.volandouy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volandouy.central.CentralService;
import com.volandouy.central.CentralServiceWS;
import com.volandouy.central.ServiceFactory;
import logica.DataTypes.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST que actúa como proxy entre el Dispositivo Móvil y el Servidor Central.
 * 
 * Este controlador expone endpoints REST que internamente consumen el Web Service SOAP
 * del Servidor Central. Esto facilita el consumo desde aplicaciones web móviles.
 * 
 * Endpoints disponibles:
 * - GET /api/central/ping - Probar conexión
 * - GET /api/central/aerolineas - Listar aerolíneas
 * - GET /api/central/ciudades - Listar ciudades
 * - GET /api/central/aeropuertos - Listar aeropuertos
 * - GET /api/central/rutas - Listar todas las rutas
 * - GET /api/central/rutas?aerolinea=NICKNAME - Listar rutas de una aerolínea
 * - GET /api/central/vuelos?ruta=NOMBRE_RUTA - Listar vuelos de una ruta
 * - GET /api/central/clientes - Listar clientes
 * - GET /api/central/usuarios - Listar usuarios
 * - GET /api/central/paquetes - Listar paquetes
 */
@WebServlet("/api/central/*")
public class CentralProxyController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private CentralService centralService;
    private ObjectMapper mapper;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar el servicio una vez al arrancar el servlet
        centralService = ServiceFactory.getCentralService();
        mapper = new ObjectMapper();
        
        System.out.println(">>> CentralProxyController inicializado");
        System.out.println(">>> Conectado al Servidor Central vía Web Services");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar respuesta JSON
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Obtener la operación desde el path
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, 
                           "Operación no especificada. Use: /api/central/{operacion}");
                return;
            }
            
            // Remover el "/" inicial
            String operacion = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
            
            // Procesar según la operación
            switch (operacion.toLowerCase()) {
                case "ping":
                    procesarPing(response);
                    break;
                    
                case "aerolineas":
                    procesarAerolineas(response);
                    break;
                    
                case "ciudades":
                    procesarCiudades(response);
                    break;
                    
                case "aeropuertos":
                    procesarAeropuertos(response);
                    break;
                    
                case "rutas":
                    procesarRutas(request, response);
                    break;
                    
                case "vuelos":
                    procesarVuelos(request, response);
                    break;
                    
                case "clientes":
                    procesarClientes(response);
                    break;
                    
                case "usuarios":
                    procesarUsuarios(response);
                    break;
                    
                case "paquetes":
                    procesarPaquetes(response);
                    break;
                    
                default:
                    enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                               "Operación no encontrada: " + operacion);
            }
            
        } catch (Exception e) {
            System.err.println("ERROR en CentralProxyController: " + e.getMessage());
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error interno: " + e.getMessage());
        }
    }
    
    private void procesarPing(HttpServletResponse response) throws IOException {
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "El servicio no está configurado como Web Service");
            return;
        }
        
        String resultado = ((CentralServiceWS) centralService).ping();
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);
        respuesta.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        mapper.writeValue(response.getWriter(), respuesta);
    }
    
    private void procesarAerolineas(HttpServletResponse response) throws IOException {
        List<DTAerolinea> aerolineas = centralService.listarAerolineas();
        mapper.writeValue(response.getWriter(), aerolineas);
    }
    
    private void procesarCiudades(HttpServletResponse response) throws IOException {
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Operación no disponible en modo local");
            return;
        }
        
        List<DTCiudad> ciudades = ((CentralServiceWS) centralService).listarCiudades();
        mapper.writeValue(response.getWriter(), ciudades);
    }
    
    private void procesarAeropuertos(HttpServletResponse response) throws IOException {
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Operación no disponible en modo local");
            return;
        }
        
        List<String> aeropuertos = ((CentralServiceWS) centralService).listarAeropuertos();
        mapper.writeValue(response.getWriter(), aeropuertos);
    }
    
    private void procesarRutas(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Operación no disponible en modo local");
            return;
        }
        
        String aerolinea = request.getParameter("aerolinea");
        
        List<DTRutaVuelo> rutas;
        if (aerolinea != null && !aerolinea.trim().isEmpty()) {
            // Listar rutas de una aerolínea específica
            rutas = centralService.listarRutasPorAerolinea(aerolinea.trim());
        } else {
            // Listar todas las rutas
            rutas = ((CentralServiceWS) centralService).listarRutasDeVuelo();
        }
        
        mapper.writeValue(response.getWriter(), rutas);
    }
    
    private void procesarVuelos(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String nombreRuta = request.getParameter("ruta");
        
        if (nombreRuta == null || nombreRuta.trim().isEmpty()) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, 
                       "Parámetro 'ruta' requerido. Use: /api/central/vuelos?ruta=NOMBRE_RUTA");
            return;
        }
        
        List<DTVuelo> vuelos = centralService.listarVuelosDeRuta(nombreRuta.trim());
        mapper.writeValue(response.getWriter(), vuelos);
    }
    
    private void procesarClientes(HttpServletResponse response) throws IOException {
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Operación no disponible en modo local");
            return;
        }
        
        List<DTCliente> clientes = ((CentralServiceWS) centralService).listarClientes();
        mapper.writeValue(response.getWriter(), clientes);
    }
    
    private void procesarUsuarios(HttpServletResponse response) throws IOException {
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Operación no disponible en modo local");
            return;
        }
        
        List<DTUsuario> usuarios = ((CentralServiceWS) centralService).consultarUsuarios();
        mapper.writeValue(response.getWriter(), usuarios);
    }
    
    private void procesarPaquetes(HttpServletResponse response) throws IOException {
        if (!(centralService instanceof CentralServiceWS)) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Operación no disponible en modo local");
            return;
        }
        
        List<DTPaqueteVuelos> paquetes = ((CentralServiceWS) centralService).mostrarPaquetes();
        mapper.writeValue(response.getWriter(), paquetes);
    }
    
    private void enviarError(HttpServletResponse response, int statusCode, String mensaje) 
            throws IOException {
        response.setStatus(statusCode);
        Map<String, String> error = new HashMap<>();
        error.put("error", mensaje);
        error.put("status", String.valueOf(statusCode));
        mapper.writeValue(response.getWriter(), error);
    }
}

