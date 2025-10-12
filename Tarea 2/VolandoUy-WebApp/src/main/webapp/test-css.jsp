<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test CSS - VolandoUY</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .test-container {
            padding: 20px;
            background: #2B3033;
            color: white;
            font-family: "Poppins", Arial, sans-serif;
        }
        .test-box {
            background: #01AAF5;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="test-container">
        <h1>Test de CSS - VolandoUY</h1>
        
        <div class="test-box">
            <h2>Información del Contexto</h2>
            <p><strong>Context Path:</strong> ${pageContext.request.contextPath}</p>
            <p><strong>Request URI:</strong> ${pageContext.request.requestURI}</p>
            <p><strong>Server Name:</strong> ${pageContext.request.serverName}</p>
            <p><strong>Server Port:</strong> ${pageContext.request.serverPort}</p>
        </div>
        
        <div class="test-box">
            <h2>Test de CSS</h2>
            <p>Si ves este texto con fondo azul (#01AAF5), el CSS está funcionando.</p>
            <p>Si el fondo de la página es gris oscuro (#2B3033), el CSS principal está cargando.</p>
        </div>
        
        <div class="test-box">
            <h2>Enlaces de Prueba</h2>
            <p><a href="${pageContext.request.contextPath}/css/style.css" target="_blank">Ver CSS directamente</a></p>
            <p><a href="${pageContext.request.contextPath}/index.jsp">Volver al inicio</a></p>
        </div>
    </div>
</body>
</html>
