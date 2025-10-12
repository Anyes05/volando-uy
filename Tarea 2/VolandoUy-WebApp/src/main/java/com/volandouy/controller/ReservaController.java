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
 * Controlador para manejar las operaciones relacionadas con reservas
 */
@WebServlet("/api/reservas/*")
public class ReservaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Object> reservas;

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
                // Devolver todas las reservas
                String json = objectMapper.writeValueAsString(reservas);
                out.print(json);
            } else {
                // Buscar reserva específica por ID
                String idStr = pathInfo.substring(1); // Remover la barra inicial
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
                    String json = objectMapper.writeValueAsString(reserva);
                    out.print(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Reserva no encontrada\"}");
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
            
            // Parsear el JSON de la nueva reserva
            Object nuevaReserva = objectMapper.readValue(body, Object.class);
            
            // Agregar la nueva reserva
            reservas.add(nuevaReserva);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Reserva creada exitosamente\"}");
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
}
