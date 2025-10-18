<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%
try {
    // Respuesta simple de prueba
    out.print("{\"status\":\"ok\",\"message\":\"Servidor funcionando correctamente\"}");
} catch (Exception e) {
    out.print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
}
%>
