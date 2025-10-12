<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error del servidor - VolandoUY</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .error-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            text-align: center;
            padding: 20px;
        }
        .error-code {
            font-size: 120px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 24px;
            color: white;
            margin-bottom: 30px;
        }
        .error-description {
            font-size: 16px;
            color: #b8c5ca;
            margin-bottom: 40px;
            max-width: 600px;
        }
        .error-actions {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
            justify-content: center;
        }
        .btn-error {
            padding: 12px 24px;
            background: linear-gradient(135deg, #01AAF5, #0288B6);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-error:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(1, 170, 245, 0.3);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">500</div>
        <div class="error-message">Error interno del servidor</div>
        <div class="error-description">
            Ha ocurrido un error interno en el servidor. Nuestro equipo técnico ha sido notificado 
            y está trabajando para solucionarlo. Por favor, intenta nuevamente en unos momentos.
        </div>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn-error">
                <i class="fas fa-home"></i> Ir al inicio
            </a>
            <a href="javascript:history.back()" class="btn-error">
                <i class="fas fa-arrow-left"></i> Volver atrás
            </a>
        </div>
    </div>
</body>
</html>
