package com.volandouy.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import logica.DataTypes.DTAerolinea;
import logica.clase.Sistema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/api/aerolineas/*")
public class AerolineaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AerolineaController.class.getName());

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Sistema sistema = Sistema.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo(); // / o /{nombre}

            // ⚠️ Si no hay un path específico -> listar aerolíneas
            if (pathInfo == null || pathInfo.equals("/")) {
                HttpSession session = request.getSession(false);
                String nickname = null;
                if (session != null && session.getAttribute("usuarioLogueado") != null) {
                    nickname = session.getAttribute("usuarioLogueado").toString();
                }

                List<DTAerolinea> aerolineas = new ArrayList<>();
                try {
                    aerolineas = sistema.listarAerolineas();
                    LOG.info("Aerolíneas obtenidas exitosamente: " + aerolineas.size());
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error al obtener aerolíneas: " + ex.getMessage(), ex);
                    // En caso de error, devolver lista vacía en lugar de fallar
                    aerolineas = new ArrayList<>();
                }

                // Configurar mapper JSON
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                try {
                    String jsonResponse = objectMapper.writeValueAsString(aerolineas);
                    out.print(jsonResponse);
                    response.setStatus(HttpServletResponse.SC_OK);
                    LOG.info("Respuesta JSON enviada exitosamente");
                } catch (Exception jsonEx) {
                    LOG.log(Level.SEVERE, "Error al serializar JSON: " + jsonEx.getMessage(), jsonEx);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print("{\"error\":\"Error interno del servidor\"}");
                }
                return;
            }
            // 🛑 Si hay un path adicional que no existe
            else {
                String nombre = pathInfo.substring(1);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(objectMapper.writeValueAsString(Map.of(
                        "error", "Aerolínea no encontrada (scaffold)",
                        "nombre", nombre
                )));
                return;
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error /api/aerolineas GET", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Error interno")));
        } finally {
            out.flush();
        }
    }
}
