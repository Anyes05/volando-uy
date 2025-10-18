<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Test Layout</title>
</head>
<body>
    <h1>Test Layout - Verificar Expresiones EL</h1>
    
    <h2>Información de Sesión:</h2>
    <p>Usuario logueado: <c:out value="${sessionScope.usuarioLogueado}" escapeXml="true" /></p>
    <p>Nombre usuario: <c:out value="${sessionScope.nombreUsuario}" escapeXml="true" /></p>
    <p>Tipo usuario: <c:out value="${sessionScope.tipoUsuario}" escapeXml="true" /></p>
    <p>Foto usuario: <c:if test="${not empty sessionScope.fotoUsuario}">Presente</c:if><c:if test="${empty sessionScope.fotoUsuario}">No presente</c:if></p>
    
    <h2>Test de Expresiones EL:</h2>
    <p>Test 1: <c:out value="${not empty sessionScope.usuarioLogueado}" /></p>
    <p>Test 2: <c:out value="${not empty param.content}" /></p>
    
    <h2>Test de JavaScript con EL:</h2>
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log('Test JavaScript con EL');
        <c:choose>
            <c:when test="${not empty sessionScope.usuarioLogueado}">
                console.log('Usuario logueado: <c:out value="${sessionScope.usuarioLogueado}" escapeXml="true" />');
            </c:when>
            <c:otherwise>
                console.log('No hay usuario logueado');
            </c:otherwise>
        </c:choose>
    });
    </script>
    
    <p>Si puedes ver esta página sin errores, las expresiones EL están funcionando correctamente.</p>
</body>
</html>
