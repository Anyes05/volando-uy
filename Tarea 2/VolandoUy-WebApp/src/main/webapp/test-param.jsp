<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Test Param</title>
</head>
<body>
    <h1>Test Param - Verificar Parámetros</h1>
    
    <h2>Información de Parámetros:</h2>
    <p>Content param: <c:out value="${param.content}" escapeXml="true" /></p>
    <p>Content param empty: <c:out value="${empty param.content}" /></p>
    <p>Content param not empty: <c:out value="${not empty param.content}" /></p>
    
    <h2>Test de Include con Parámetro:</h2>
    <c:choose>
        <c:when test="${not empty param.content}">
            <p>Incluyendo: <c:out value="${param.content}" escapeXml="true" /></p>
            <c:set var="contentPage" value="${param.content}" />
            <jsp:include page="${contentPage}" />
        </c:when>
        <c:otherwise>
            <p>No hay parámetro content, mostrando inicio.jsp</p>
            <jsp:include page="inicio.jsp" />
        </c:otherwise>
    </c:choose>
    
    <h2>Test de JavaScript con Parámetros:</h2>
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log('Test JavaScript con parámetros');
        <c:choose>
            <c:when test="${not empty param.content}">
                console.log('Content param: <c:out value="${param.content}" escapeXml="true" />');
            </c:when>
            <c:otherwise>
                console.log('No hay parámetro content');
            </c:otherwise>
        </c:choose>
    });
    </script>
    
    <p>Si puedes ver esta página sin errores, los parámetros están funcionando correctamente.</p>
</body>
</html>
