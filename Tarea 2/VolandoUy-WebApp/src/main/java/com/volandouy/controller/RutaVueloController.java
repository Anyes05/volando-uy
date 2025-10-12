package com.volandouy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controlador para manejar las operaciones relacionadas con rutas de vuelo
 */
@WebServlet("/api/rutas/*")
public class RutaVueloController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Object> rutas;

    @Override
    public void init() throws ServletException {
        super.init();
        cargarRutas();
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
                // Devolver todas las rutas
                String json = objectMapper.writeValueAsString(rutas);
                out.print(json);
            } else {
                // Buscar ruta específica por nombre
                String nombre = pathInfo.substring(1); // Remover la barra inicial
                
                Object ruta = rutas.stream()
                    .filter(r -> {
                        try {
                            java.util.Map<String, Object> rutaMap = objectMapper.convertValue(r, java.util.Map.class);
                            return rutaMap.get("nombre").equals(nombre);
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .findFirst()
                    .orElse(null);
                
                if (ruta != null) {
                    String json = objectMapper.writeValueAsString(ruta);
                    out.print(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Ruta no encontrada\"}");
                }
            }
        } catch (Exception e) {
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
        
        try {
            // Leer el cuerpo de la petición
            String body = request.getReader().lines().collect(Collectors.joining());
            
            // Parsear el JSON de la nueva ruta
            Object nuevaRuta = objectMapper.readValue(body, Object.class);
            
            // Agregar la nueva ruta
            rutas.add(nuevaRuta);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Ruta creada exitosamente\"}");
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al procesar la petición\"}");
            out.flush();
            e.printStackTrace();
        }
    }

    /**
     * Carga las rutas desde el archivo JSON
     */
    private void cargarRutas() {
        try {
            InputStream inputStream = getServletContext().getResourceAsStream("/json/rutasVuelo.json");
            if (inputStream != null) {
                rutas = objectMapper.readValue(inputStream, new TypeReference<List<Object>>() {});
                inputStream.close();
            } else {
                rutas = new java.util.ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            rutas = new java.util.ArrayList<>();
        }
    }
}
