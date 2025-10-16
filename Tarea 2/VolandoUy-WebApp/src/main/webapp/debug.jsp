<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Debug - VolandoUY</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .debug-section {
            background: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        button {
            background: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin: 5px;
        }
        button:hover {
            background: #0056b3;
        }
        .result {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 4px;
            padding: 15px;
            margin: 10px 0;
            white-space: pre-wrap;
            font-family: monospace;
            max-height: 300px;
            overflow-y: auto;
        }
        .error {
            background: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }
        .success {
            background: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
        }
    </style>
</head>
<body>
    <h1>Debug VolandoUY - Sistema de Pruebas</h1>
    
    <div class="debug-section">
        <h2>1. Test Básico del Sistema</h2>
        <p>Verifica que el sistema esté funcionando correctamente.</p>
        <button onclick="testSistema()">Test Sistema</button>
        <div id="result-sistema" class="result" style="display:none;"></div>
    </div>

    <div class="debug-section">
        <h2>2. Precargar Datos</h2>
        <p>Carga datos de prueba en el sistema.</p>
        <button onclick="precargarDatos()">Precargar Sistema</button>
        <div id="result-precargar" class="result" style="display:none;"></div>
    </div>

    <div class="debug-section">
        <h2>3. Verificar Usuarios</h2>
        <p>Lista todos los usuarios registrados en el sistema.</p>
        <button onclick="verificarUsuarios()">Listar Usuarios</button>
        <div id="result-usuarios" class="result" style="display:none;"></div>
    </div>

    <div class="debug-section">
        <h2>4. Test de Sesión</h2>
        <p>Verifica la sesión actual del usuario.</p>
        <button onclick="testSesion()">Verificar Sesión</button>
        <div id="result-sesion" class="result" style="display:none;"></div>
    </div>

    <div class="debug-section">
        <h2>5. Test API Usuarios/Perfil</h2>
        <p>Prueba el endpoint específico para obtener perfil del usuario logueado.</p>
        <button onclick="testPerfil()">Test Perfil</button>
        <div id="result-perfil" class="result" style="display:none;"></div>
    </div>

    <script>
        async function testSistema() {
            await executeTest('/debug/test', 'result-sistema');
        }

        async function precargarDatos() {
            await executeTest('/debug/precargar', 'result-precargar');
        }

        async function verificarUsuarios() {
            await executeTest('/debug/usuarios', 'result-usuarios');
        }

        async function testSesion() {
            const resultDiv = document.getElementById('result-sesion');
            resultDiv.style.display = 'block';
            resultDiv.className = 'result';
            
            const sessionData = {
                usuarioLogueado: '<%= session.getAttribute("usuarioLogueado") %>',
                nombreUsuario: '<%= session.getAttribute("nombreUsuario") %>',
                correoUsuario: '<%= session.getAttribute("correoUsuario") %>'
            };
            
            resultDiv.textContent = JSON.stringify(sessionData, null, 2);
            
            if (sessionData.usuarioLogueado && sessionData.usuarioLogueado !== 'null') {
                resultDiv.className = 'result success';
            } else {
                resultDiv.className = 'result error';
            }
        }

        async function testPerfil() {
            await executeTest('/api/usuarios/perfil', 'result-perfil');
        }

        async function executeTest(endpoint, resultId) {
            const resultDiv = document.getElementById(resultId);
            resultDiv.style.display = 'block';
            resultDiv.className = 'result';
            resultDiv.textContent = 'Ejecutando test...';

            try {
                const response = await fetch('<%= request.getContextPath() %>' + endpoint);
                const data = await response.text();
                
                resultDiv.textContent = `Status: ${response.status}\n\nResponse:\n${data}`;
                
                if (response.ok) {
                    resultDiv.className = 'result success';
                } else {
                    resultDiv.className = 'result error';
                }
            } catch (error) {
                resultDiv.textContent = `Error: ${error.message}`;
                resultDiv.className = 'result error';
            }
        }
    </script>

    <div style="margin-top: 40px; text-align: center;">
        <a href="<%= request.getContextPath() %>/inicio.jsp" style="color: #007bff;">← Volver al Inicio</a>
    </div>
</body>
</html>