<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Info del Sistema - VolandoUY</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .info-section {
            background: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .code {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 4px;
            padding: 10px;
            margin: 10px 0;
            font-family: monospace;
            word-break: break-all;
        }
        .highlight {
            background: yellow;
            padding: 2px 4px;
            border-radius: 2px;
        }
        .links {
            margin: 20px 0;
        }
        .links a {
            display: block;
            margin: 10px 0;
            padding: 10px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .links a:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Información del Sistema</h1>
    
    <div class="info-section">
        <h2>URLs del Sistema</h2>
        <p><strong>Context Path:</strong> <span class="highlight"><%= request.getContextPath() %></span></p>
        <p><strong>Server Name:</strong> <%= request.getServerName() %></p>
        <p><strong>Server Port:</strong> <%= request.getServerPort() %></p>
        <p><strong>Request URL:</strong> <%= request.getRequestURL() %></p>
    </div>

    <div class="info-section">
        <h2>URLs Correctas para Usar</h2>
        
        <h3>Debug:</h3>
        <div class="code">http://<%= request.getServerName() %>:<%= request.getServerPort() %><%= request.getContextPath() %>/debug.jsp</div>
        
        <h3>Modificar Usuario:</h3>
        <div class="code">http://<%= request.getServerName() %>:<%= request.getServerPort() %><%= request.getContextPath() %>/modificarUsuario.jsp</div>
        
        <h3>API Endpoints:</h3>
        <div class="code">
            GET <%= request.getContextPath() %>/debug/test<br>
            GET <%= request.getContextPath() %>/debug/precargar<br>
            GET <%= request.getContextPath() %>/debug/usuarios<br>
            GET <%= request.getContextPath() %>/api/usuarios/perfil
        </div>
    </div>

    <div class="info-section">
        <h2>Estado de la Sesión</h2>
        <p><strong>Usuario Logueado:</strong> 
            <% 
                String usuario = (String) session.getAttribute("usuarioLogueado");
                if (usuario != null) {
                    out.print("<span class='highlight'>" + usuario + "</span>");
                } else {
                    out.print("<span style='color: red;'>No hay sesión activa</span>");
                }
            %>
        </p>
        <p><strong>Nombre:</strong> <%= session.getAttribute("nombreUsuario") %></p>
        <p><strong>Correo:</strong> <%= session.getAttribute("correoUsuario") %></p>
    </div>

    <div class="info-section">
        <h2>Enlaces Directos</h2>
        <div class="links">
            <a href="<%= request.getContextPath() %>/debug.jsp">Ir a Debug</a>
            <a href="<%= request.getContextPath() %>/modificarUsuario.jsp">Ir a Modificar Usuario</a>
            <a href="<%= request.getContextPath() %>/inicioSesion.jsp">Ir a Inicio de Sesión</a>
            <a href="<%= request.getContextPath() %>/inicio.jsp">Ir al Inicio</a>
        </div>
    </div>

    <div class="info-section">
        <h2>Test Rápido de APIs</h2>
        <button onclick="testAPI()">Test API Usuarios/Perfil</button>
        <div id="api-result" class="code" style="display:none; margin-top:10px;"></div>
    </div>

    <script>
        async function testAPI() {
            const resultDiv = document.getElementById('api-result');
            resultDiv.style.display = 'block';
            resultDiv.textContent = 'Probando API...';

            try {
                const response = await fetch('<%= request.getContextPath() %>/api/usuarios/perfil');
                const data = await response.text();
                
                resultDiv.textContent = `Status: ${response.status}\n\nResponse:\n${data}`;
                
                if (response.ok) {
                    resultDiv.style.background = '#d4edda';
                    resultDiv.style.borderColor = '#c3e6cb';
                    resultDiv.style.color = '#155724';
                } else {
                    resultDiv.style.background = '#f8d7da';
                    resultDiv.style.borderColor = '#f5c6cb';
                    resultDiv.style.color = '#721c24';
                }
            } catch (error) {
                resultDiv.textContent = `Error: ${error.message}`;
                resultDiv.style.background = '#f8d7da';
                resultDiv.style.borderColor = '#f5c6cb';
                resultDiv.style.color = '#721c24';
            }
        }
    </script>
</body>
</html>