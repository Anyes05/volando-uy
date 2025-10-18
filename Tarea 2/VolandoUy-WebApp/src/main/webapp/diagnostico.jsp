<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Diagnóstico Completo - VolandoUY</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .section { background: #f5f5f5; padding: 15px; margin: 10px 0; border-radius: 5px; }
        .error { color: red; }
        .success { color: green; }
        .warning { color: orange; }
        pre { background: #eee; padding: 10px; border-radius: 3px; overflow-x: auto; }
    </style>
</head>
<body>
    <h1>🔍 Diagnóstico Completo del Sistema</h1>
    
    <div class="section">
        <h2>1. Estado del Sistema</h2>
        <button onclick="testSistema()">Probar Sistema</button>
        <div id="result-sistema"></div>
    </div>
    
    <div class="section">
        <h2>2. Precargar Datos</h2>
        <button onclick="precargarDatos()">Precargar Sistema</button>
        <div id="result-precargar"></div>
    </div>
    
    <div class="section">
        <h2>3. Probar API Aerolíneas</h2>
        <button onclick="testAerolineas()">Probar /api/aerolineas</button>
        <div id="result-aerolineas"></div>
    </div>
    
    <div class="section">
        <h2>4. Probar API Rutas</h2>
        <button onclick="testRutas()">Probar /api/rutas</button>
        <div id="result-rutas"></div>
    </div>
    
    <div class="section">
        <h2>5. Probar CONSULTARUTAVUELO</h2>
        <button onclick="testConsultaRutaVuelo()">Probar Página</button>
        <div id="result-consulta"></div>
    </div>
    
    <div class="section">
        <h2>6. Información de Sesión</h2>
        <p><strong>Usuario logueado:</strong> <c:out value="${sessionScope.usuarioLogueado}" default="No hay sesión" /></p>
        <p><strong>Tipo usuario:</strong> <c:out value="${sessionScope.tipoUsuario}" default="No especificado" /></p>
        <p><strong>Nombre usuario:</strong> <c:out value="${sessionScope.nombreUsuario}" default="No especificado" /></p>
    </div>

    <script>
        async function testSistema() {
            try {
                const response = await fetch('/VolandoUy-WebApp/debug/test');
                const data = await response.text();
                document.getElementById('result-sistema').innerHTML = 
                    `<pre>${data}</pre>`;
            } catch (error) {
                document.getElementById('result-sistema').innerHTML = 
                    `<div class="error">Error: ${error.message}</div>`;
            }
        }
        
        async function precargarDatos() {
            try {
                const response = await fetch('/VolandoUy-WebApp/debug/precargar');
                const data = await response.text();
                document.getElementById('result-precargar').innerHTML = 
                    `<pre>${data}</pre>`;
            } catch (error) {
                document.getElementById('result-precargar').innerHTML = 
                    `<div class="error">Error: ${error.message}</div>`;
            }
        }
        
        async function testAerolineas() {
            try {
                const response = await fetch('/VolandoUy-WebApp/api/aerolineas');
                const data = await response.text();
                document.getElementById('result-aerolineas').innerHTML = 
                    `<pre>${data}</pre>`;
            } catch (error) {
                document.getElementById('result-aerolineas').innerHTML = 
                    `<div class="error">Error: ${error.message}</div>`;
            }
        }
        
        async function testRutas() {
            try {
                const response = await fetch('/VolandoUy-WebApp/api/rutas');
                const data = await response.text();
                document.getElementById('result-rutas').innerHTML = 
                    `<pre>${data}</pre>`;
            } catch (error) {
                document.getElementById('result-rutas').innerHTML = 
                    `<div class="error">Error: ${error.message}</div>`;
            }
        }
        
        async function testConsultaRutaVuelo() {
            try {
                const response = await fetch('/VolandoUy-WebApp/vista/consultaRutaVuelo');
                const data = await response.text();
                document.getElementById('result-consulta').innerHTML = 
                    `<pre>${data.substring(0, 1000)}...</pre>`;
            } catch (error) {
                document.getElementById('result-consulta').innerHTML = 
                    `<div class="error">Error: ${error.message}</div>`;
            }
        }
    </script>
</body>
</html>
