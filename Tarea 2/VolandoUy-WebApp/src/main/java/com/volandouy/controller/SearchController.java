package com.volandouy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volandouy.central.CentralService;
import com.volandouy.central.ServiceFactory;
import logica.DataTypes.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/api/busqueda")
public class SearchController extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    private CentralService getCentral() {
        try {
            return ServiceFactory.getCentralService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String q = request.getParameter("q");
        if (q == null) q = "";
        String qFinal = q.toLowerCase();

        List<Map<String, Object>> resultados = new ArrayList<>();

        try {
            CentralService central = getCentral();

            /* === RUTAS === */
            List<DTAerolinea> aerolineas = central.listarAerolineas();

            for (DTAerolinea a : aerolineas) {
                List<DTRutaVuelo> rutas = central.listarRutasPorAerolinea(a.getNickname());

                for (DTRutaVuelo r : rutas) {

                    if (r.getEstado() != EstadoRutaVuelo.CONFIRMADA) continue;

                    boolean match =
                            r.getNombre().toLowerCase().contains(qFinal) ||
                                    r.getDescripcion().toLowerCase().contains(qFinal);

                    if (!match) continue;

                    Map<String, Object> item = new HashMap<>();
                    item.put("tipo", "ruta");
                    item.put("nombre", r.getNombre());
                    item.put("descripcion", r.getDescripcion());
                    item.put("fechaAlta", r.getFechaAlta().toString());
                    item.put("aerolinea", r.getAerolinea().getNombre());

                    resultados.add(item);
                }
            }

            /* === PAQUETES === */
            List<DTPaqueteVuelos> paquetes = central.mostrarPaquetes();

            for (DTPaqueteVuelos p : paquetes) {
                boolean match =
                        p.getNombre().toLowerCase().contains(qFinal) ||
                                p.getDescripcion().toLowerCase().contains(qFinal);

                if (!match) continue;

                Map<String, Object> item = new HashMap<>();
                item.put("tipo", "paquete");
                item.put("nombre", p.getNombre());
                item.put("descripcion", p.getDescripcion());
                item.put("fechaAlta", p.getFechaAlta().toString());

                resultados.add(item);
            }

            // Ordenar por fecha (desc)
            resultados.sort((a, b) ->
                    b.get("fechaAlta").toString().compareTo(a.get("fechaAlta").toString())
            );

            out.print(mapper.writeValueAsString(resultados));

        } catch (Exception e) {
            out.print(mapper.writeValueAsString(
                    Map.of("error", e.getMessage())
            ));
        }
    }
}
