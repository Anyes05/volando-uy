package com.volandouy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
// Las entidades y servicios vienen de la librería externa
// Se importarán automáticamente cuando se compile


/**
 * Controlador para manejar las operaciones relacionadas con usuarios
 * Utiliza el Servidor Central para acceder a los datos
 */
@WebServlet("/api/usuarios/*")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("UsuarioController inicializado - usando librería externa");
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
                // TODO: Implementar usando servicios de la librería externa
                out.print("{\"message\": \"Endpoint usuarios - usando librería externa\"}");
            } else {
                // TODO: Implementar búsqueda por ID usando servicios de la librería externa
                String idStr = pathInfo.substring(1);
                out.print("{\"message\": \"Usuario ID: " + idStr + " - usando librería externa\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"ID de usuario inválido\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}");
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
            // TODO: Implementar creación usando servicios de la librería externa
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Usuario creado - usando librería externa\"}");
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al procesar la petición\"}");
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"ID de usuario requerido\"}");
            out.flush();
            return;
        }
        
        try {
            String idStr = pathInfo.substring(1);
            // TODO: Implementar actualización usando servicios de la librería externa
            
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Usuario actualizado - usando librería externa\"}");
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al actualizar el usuario\"}");
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"ID de usuario requerido\"}");
            out.flush();
            return;
        }
        
        try {
            String idStr = pathInfo.substring(1);
            // TODO: Implementar eliminación usando servicios de la librería externa
            
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Usuario eliminado - usando librería externa\"}");
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al eliminar el usuario\"}");
            out.flush();
            e.printStackTrace();
        }
    }

    // TODO: Implementar conversión a JSON usando entidades de la librería externa
    
    @Override
    public void destroy() {
        super.destroy();
    }
}
