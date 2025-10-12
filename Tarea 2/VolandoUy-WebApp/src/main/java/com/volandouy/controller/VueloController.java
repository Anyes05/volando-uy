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
 * Controlador para manejar las operaciones relacionadas con vuelos
 */
@WebServlet("/api/vuelos/*")
public class VueloController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Object> vuelos;

    @Override
    public void init() throws ServletException {
        super.init();
        cargarVuelos();
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
                // Devolver todos los vuelos
                String json = objectMapper.writeValueAsString(vuelos);
                out.print(json);
            } else {
                // Buscar vuelo específico por nombre
                String nombre = pathInfo.substring(1); // Remover la barra inicial
                
                Object vuelo = vuelos.stream()
                    .filter(v -> {
                        try {
                            java.util.Map<String, Object> vueloMap = objectMapper.convertValue(v, java.util.Map.class);
                            return vueloMap.get("nombre").equals(nombre);
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .findFirst()
                    .orElse(null);
                
                if (vuelo != null) {
                    String json = objectMapper.writeValueAsString(vuelo);
                    out.print(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Vuelo no encontrado\"}");
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
            
            // Parsear el JSON del nuevo vuelo
            Object nuevoVuelo = objectMapper.readValue(body, Object.class);
            
            // Agregar el nuevo vuelo
            vuelos.add(nuevoVuelo);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Vuelo creado exitosamente\"}");
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
     * Carga los vuelos desde el archivo JSON
     */
    private void cargarVuelos() {
        try {
            InputStream inputStream = getServletContext().getResourceAsStream("/json/vuelos.json");
            if (inputStream != null) {
                vuelos = objectMapper.readValue(inputStream, new TypeReference<List<Object>>() {});
                inputStream.close();
            } else {
                vuelos = new java.util.ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            vuelos = new java.util.ArrayList<>();
        }
    }
}
