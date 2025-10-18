<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Simple</title>
</head>
<body>
    <h1>Test Simple - Servidor Funcionando</h1>
    <p>Si puedes ver esta página, el servidor está funcionando correctamente.</p>
    <p>Fecha y hora: <%= new java.util.Date() %></p>
    
    <h2>Probar APIs:</h2>
    <button onclick="testAerolineas()">Probar API Aerolíneas</button>
    <button onclick="testRutas()">Probar API Rutas</button>
    
    <div id="resultado"></div>
    
    <script>
    function testAerolineas() {
        fetch('<%= request.getContextPath() %>/api/aerolineas')
            .then(response => {
                console.log('Status:', response.status);
                return response.text();
            })
            .then(data => {
                document.getElementById('resultado').innerHTML = '<h3>Resultado Aerolíneas:</h3><pre>' + data + '</pre>';
            })
            .catch(error => {
                document.getElementById('resultado').innerHTML = '<h3>Error Aerolíneas:</h3><pre>' + error + '</pre>';
            });
    }
    
    function testRutas() {
        fetch('<%= request.getContextPath() %>/api/rutas')
            .then(response => {
                console.log('Status:', response.status);
                return response.text();
            })
            .then(data => {
                document.getElementById('resultado').innerHTML = '<h3>Resultado Rutas:</h3><pre>' + data + '</pre>';
            })
            .catch(error => {
                document.getElementById('resultado').innerHTML = '<h3>Error Rutas:</h3><pre>' + error + '</pre>';
            });
    }
    </script>
</body>
</html>