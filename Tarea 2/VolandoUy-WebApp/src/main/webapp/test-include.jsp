<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Test Include</title>
</head>
<body>
    <h1>Test Include - Verificar jsp:include</h1>
    
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
    
    <h2>Test de Include Directo:</h2>
    <p>Incluyendo test-simple.jsp directamente:</p>
    <jsp:include page="test-simple.jsp" />
    
    <p>Si puedes ver esta página sin errores, el jsp:include está funcionando correctamente.</p>
</body>
</html>
