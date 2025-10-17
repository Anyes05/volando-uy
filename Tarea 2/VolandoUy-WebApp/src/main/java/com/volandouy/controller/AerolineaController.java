package com.volandouy.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTCiudad;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;
import logica.DataTypes.DTRutaVuelo;
import logica.clase.Sistema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para manejar las operaciones relacionadas con paquetes
 */
@WebServlet("/api/aerolineas/*")
public class AerolineaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RutaVueloController.class.getName());

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Sistema sistema = Sistema.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<DTAerolinea> aerolineas = sistema.listarAerolineas();

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String jsonResponse = objectMapper.writeValueAsString(aerolineas);
            out.print(jsonResponse);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar aerolíneas", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error al listar aerolíneas")));
        }
    }
}

