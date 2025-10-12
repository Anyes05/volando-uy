<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Debug CSS - VolandoUY</title>
    <style>
        body {
            background: #2B3033;
            color: white;
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        .debug-box {
            background: #01AAF5;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
        }
        .error-box {
            background: #ff4444;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <h1>Debug CSS - VolandoUY</h1>
    
    <div class="debug-box">
        <h2>Información del Contexto</h2>
        <p><strong>Context Path:</strong> ${pageContext.request.contextPath}</p>
        <p><strong>Request URI:</strong> ${pageContext.request.requestURI}</p>
        <p><strong>Server Name:</strong> ${pageContext.request.serverName}</p>
        <p><strong>Server Port:</strong> ${pageContext.request.serverPort}</p>
        <p><strong>Context Path Length:</strong> ${pageContext.request.contextPath.length()}</p>
    </div>
    
    <div class="debug-box">
        <h2>Test de Rutas CSS</h2>
        <p><a href="${pageContext.request.contextPath}/css/style.css" target="_blank" style="color: white;">Ruta con contextPath: ${pageContext.request.contextPath}/css/style.css</a></p>
        <p><a href="css/style.css" target="_blank" style="color: white;">Ruta relativa: css/style.css</a></p>
        <p><a href="/css/style.css" target="_blank" style="color: white;">Ruta absoluta: /css/style.css</a></p>
    </div>
    
    <div class="debug-box">
        <h2>Test de Archivos</h2>
        <p><a href="${pageContext.request.contextPath}/img/logoAvionSolo.png" target="_blank" style="color: white;">Logo: ${pageContext.request.contextPath}/img/logoAvionSolo.png</a></p>
        <p><a href="${pageContext.request.contextPath}/js/controladorVista.js" target="_blank" style="color: white;">JS: ${pageContext.request.contextPath}/js/controladorVista.js</a></p>
    </div>
    
    <div class="debug-box">
        <h2>Instrucciones</h2>
        <p>1. Haz clic en los enlaces de arriba para verificar que los archivos existen</p>
        <p>2. Si los archivos no cargan, verifica la estructura de carpetas</p>
        <p>3. Si los archivos cargan pero el CSS no se aplica, el problema está en el HTML</p>
    </div>
    
    <div class="debug-box">
        <h2>Enlaces de Navegación</h2>
        <p><a href="${pageContext.request.contextPath}/" style="color: white;">Página Principal</a></p>
        <p><a href="${pageContext.request.contextPath}/test-css.jsp" style="color: white;">Test CSS Original</a></p>
        <p><a href="${pageContext.request.contextPath}/index.jsp" style="color: white;">Index.jsp</a></p>
    </div>
</body>
</html>
