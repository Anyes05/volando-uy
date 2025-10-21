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
 * Controlador principal para manejar las vistas JSP
 * Reemplaza la funcionalidad del controlador JavaScript
 * Integra datos del Servidor Central cuando es necesario
 */
@WebServlet("/vista/*")
public class VistaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("VistaController inicializado - usando librería externa");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/inicio.jsp";
        }
        
        // Remover la barra inicial si existe
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        
        // Mapear las vistas
        String vista = mapearVista(pathInfo);
        
        // Preparar datos específicos para ciertas vistas
        prepararDatosParaVista(request, vista);
        
        // Redirigir a la vista correspondiente
        request.getRequestDispatcher("/" + vista).forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Mapea las rutas a las vistas JSP correspondientes
     */
    private String mapearVista(String pathInfo) {
        switch (pathInfo) {
            case "inicio.jsp":
            case "inicio":
                return "inicio.jsp";
                
            case "consultaUsuario.jsp":
            case "consultaUsuario":
                return "consultaUsuario.jsp";
                
            case "modificarUsuario.jsp":
            case "modificarUsuario":
                return "modificarUsuario.jsp";
                
            case "altaRutaVuelo.jsp":
            case "altaRutaVuelo":
                return "altaRutaVuelo.jsp";
                
            case "consultaRutaVuelo.jsp":
            case "consultaRutaVuelo":
                return "consultaRutaVuelo.jsp";
                
            case "altaVuelo.jsp":
            case "altaVuelo":
                return "altaVuelo.jsp";
                
            case "consultaVuelo.jsp":
            case "consultaVuelo":
                return "consultaVuelo.jsp";
                
            case "reserva.jsp":
            case "reserva":
                return "reserva.jsp";
                
            case "consultaReserva.jsp":
            case "consultaReserva":
                return "consultaReserva.jsp";
                
            case "consultaPaquete.jsp":
            case "consultaPaquete":
                return "consultaPaquete.jsp";
                
            case "compraPaquete.jsp":
            case "compraPaquete":
                return "compraPaquete.jsp";
                
            case "inicioSesion.jsp":
            case "inicioSesion":
            case "login":
                return "inicioSesion.jsp";
                
            case "registrarUsuario.jsp":
            case "registrarUsuario":
            case "registro":
                return "registrarUsuario.jsp";
                
            default:
                return "inicio.jsp";
        }
    }
    
    /**
     * Prepara datos específicos para ciertas vistas usando la librería externa
     */
    private void prepararDatosParaVista(HttpServletRequest request, String vista) {
        try {
            switch (vista) {
                case "consultaUsuario.jsp":
                    // Los datos se cargan desde JavaScript usando la API REST
                    // No se necesitan datos específicos del servidor aquí
                    break;
                    
                case "registrarUsuario.jsp":
                    // Preparar datos para el formulario de registro
                    request.setAttribute("tiposDocumento", new String[]{"CI", "Pasaporte", "DNI"});
                    request.setAttribute("nacionalidades", new String[]{
                        "Uruguaya", "Argentina", "Brasileña", "Chilena", "Paraguaya", "Peruana"
                    });
                    break;
                    
                case "modificarUsuario.jsp":
                    // TODO: Cargar usuarios para modificación usando servicios de la librería externa
                    request.setAttribute("usuarios", new java.util.ArrayList<>());
                    break;
                    
                default:
                    // Para otras vistas, no se necesitan datos específicos
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error al preparar datos para vista " + vista + ": " + e.getMessage());
            e.printStackTrace();
            // Continuar sin datos adicionales
        }
    }
    
    @Override
    public void destroy() {
        super.destroy();
    }
}
