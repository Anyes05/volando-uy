package com.volandouy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import logica.clase.Sistema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/debug/*")
public class DebugController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(DebugController.class.getName());
    private final ObjectMapper objectMapper;
    private final Sistema sistema = Sistema.getInstance();

    public DebugController() {
        // Configurar ObjectMapper para evitar referencias circulares
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
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/test")) {
                // Test básico del sistema
                out.print(objectMapper.writeValueAsString(Map.of(
                    "mensaje", "Sistema funcionando",
                    "sistemaInstance", sistema != null ? "OK" : "NULL"
                )));
                
            } else if (pathInfo.equals("/precargar")) {
                // Precargar datos del sistema
                try {
                    sistema.precargarSistemaCompleto();
                    out.print(objectMapper.writeValueAsString(Map.of(
                        "mensaje", "Sistema precargado exitosamente"
                    )));
                } catch (Exception ex) {
                    LOG.severe("Error al precargar sistema: " + ex.getMessage());
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of(
                        "error", "Error al precargar: " + ex.getMessage()
                    )));
                }
                
            } else if (pathInfo.equals("/usuarios")) {
                // Test de consulta de usuarios
                try {
                    var usuarios = sistema.consultarUsuarios();
                    out.print(objectMapper.writeValueAsString(Map.of(
                        "usuarios", usuarios,
                        "cantidad", usuarios.size()
                    )));
                } catch (Exception ex) {
                    LOG.severe("Error al consultar usuarios: " + ex.getMessage());
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(objectMapper.writeValueAsString(Map.of(
                        "error", "Error al consultar usuarios: " + ex.getMessage()
                    )));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(objectMapper.writeValueAsString(Map.of(
                    "error", "Endpoint no encontrado"
                )));
            }
            
        } catch (Exception ex) {
            LOG.severe("Error en DebugController: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of(
                "error", "Error interno: " + ex.getMessage()
            )));
        } finally {
            out.flush();
        }
    }
}