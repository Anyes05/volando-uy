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
 * Controlador para manejar las operaciones relacionadas con paquetes
 */
@WebServlet("/api/paquetes/*")
public class PaqueteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Object> paquetes;

    @Override
    public void init() throws ServletException {
        super.init();
        cargarPaquetes();
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
                // Devolver todos los paquetes
                String json = objectMapper.writeValueAsString(paquetes);
                out.print(json);
            } else {
                // Buscar paquete específico por ID
                String idStr = pathInfo.substring(1); // Remover la barra inicial
                int id = Integer.parseInt(idStr);
                
                Object paquete = paquetes.stream()
                    .filter(p -> {
                        try {
                            java.util.Map<String, Object> paqueteMap = objectMapper.convertValue(p, java.util.Map.class);
                            return paqueteMap.get("id").equals(id);
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .findFirst()
                    .orElse(null);
                
                if (paquete != null) {
                    String json = objectMapper.writeValueAsString(paquete);
                    out.print(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Paquete no encontrado\"}");
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
            
            // Parsear el JSON del nuevo paquete
            Object nuevoPaquete = objectMapper.readValue(body, Object.class);
            
            // Agregar el nuevo paquete
            paquetes.add(nuevoPaquete);
            
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

    /**
     * Carga los paquetes desde el archivo JSON
     */
    private void cargarPaquetes() {
        try {
            InputStream inputStream = getServletContext().getResourceAsStream("/json/paquetes.json");
            if (inputStream != null) {
                paquetes = objectMapper.readValue(inputStream, new TypeReference<List<Object>>() {});
                inputStream.close();
            } else {
                paquetes = new java.util.ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            paquetes = new java.util.ArrayList<>();
        }
    }
}
