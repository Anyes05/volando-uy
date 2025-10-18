<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Simple - VolandoUY</title>
    <link rel="stylesheet" href="static/css/style.css">
    <style>
        .test-info {
            background: #01AAF5;
            color: white;
            padding: 20px;
            margin: 20px;
            border-radius: 8px;
        }
        .test-links {
            background: #2B3033;
            color: white;
            padding: 20px;
            margin: 20px;
            border-radius: 8px;
        }
        .test-links a {
            color: #01AAF5;
            text-decoration: none;
            margin-right: 15px;
        }
        .test-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="test-info">
        <h1>Test Simple de CSS</h1>
        <p>Si ves este texto con fondo azul (#01AAF5), el CSS inline está funcionando.</p>
        <p>Si el fondo de la página es gris oscuro (#2B3033), el CSS externo está cargando.</p>
        <p><strong>Context Path:</strong> ${pageContext.request.contextPath}</p>
    </div>
    
    <div class="test-links">
        <h2>Enlaces de Prueba</h2>
        <p><a href="static/css/style.css" target="_blank">Ver CSS directamente</a></p>
        <p><a href="debug-css.jsp">Debug CSS</a></p>
        <p><a href="index.jsp">Página Principal</a></p>
        <p><a href="consultaUsuario.jsp">Consulta Usuario</a></p>
    </div>
    
    <div class="test-info">
        <h2>Instrucciones</h2>
        <p>1. Verifica que el fondo de la página sea gris oscuro</p>
        <p>2. Haz clic en "Ver CSS directamente" para verificar que el archivo existe</p>
        <p>3. Si el CSS no carga, revisa la consola del navegador (F12)</p>
    </div>
</body>
</html>
