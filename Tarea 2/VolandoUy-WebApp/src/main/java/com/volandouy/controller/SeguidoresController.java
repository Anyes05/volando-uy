package com.volandouy.controller;

import logica.servicios.SeguidoresServicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/seguidores/*")
public class SeguidoresController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SeguidoresServicio seguidoresServicio = new SeguidoresServicio();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(objectMapper.writeValueAsString(Map.of("error", "Sesión no válida")));
            return;
        }

        String nickSeguidor = (String) session.getAttribute("usuarioLogueado");
        String pathInfo = request.getPathInfo(); // ej: /juan/follow

        try {
            if (pathInfo != null && pathInfo.startsWith("/")) {
                pathInfo = pathInfo.substring(1);
            }
            String[] parts = pathInfo.split("/");

            if (parts.length == 2 && "follow".equals(parts[1])) {
                String nickSeguido = parts[0];
                seguidoresServicio.seguir(nickSeguidor, nickSeguido);
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(Map.of("message", "Ahora sigues a " + nickSeguido)));
            } else if (parts.length == 2 && "unfollow".equals(parts[1])) {
                String nickSeguido = parts[0];
                seguidoresServicio.dejarDeSeguir(nickSeguidor, nickSeguido);
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(Map.of("message", "Has dejado de seguir a " + nickSeguido)));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Ruta no válida")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", e.getMessage())));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo(); // ej: /juan/seguidores
        try {
            if (pathInfo != null && pathInfo.startsWith("/")) {
                pathInfo = pathInfo.substring(1);
            }
            String[] parts = pathInfo.split("/");

            if (parts.length == 2 && "seguidores".equals(parts[1])) {
                String nick = parts[0];
                List<String> seguidores = seguidoresServicio.listarSeguidores(nick);
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(Map.of("seguidores", seguidores)));
            } else if (parts.length == 2 && "seguidos".equals(parts[1])) {
                String nick = parts[0];
                List<String> seguidos = seguidoresServicio.listarSeguidos(nick);
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(Map.of("seguidos", seguidos)));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(objectMapper.writeValueAsString(Map.of("error", "Ruta no válida")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(objectMapper.writeValueAsString(Map.of("error", e.getMessage())));
        }
    }
}