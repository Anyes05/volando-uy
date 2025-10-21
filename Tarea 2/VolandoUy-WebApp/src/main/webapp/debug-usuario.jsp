<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test Endpoints - Debug</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .result { margin: 20px 0; padding: 10px; border: 1px solid #ccc; }
        .error { background-color: #ffebee; border-color: #f44336; }
        .success { background-color: #e8f5e8; border-color: #4caf50; }
        pre { background-color: #f5f5f5; padding: 10px; overflow-x: auto; }
    </style>
</head>
<body>
    <h1>🔍 Debug de Usuario - anita20182005</h1>
    <p>Este endpoint te mostrará exactamente qué está pasando con tus reservas y paquetes.</p>
    
    <div id="results"></div>
    
    <script>
        async function testDebugEndpoint() {
            const resultsDiv = document.getElementById('results');
            resultsDiv.innerHTML = '<p>Cargando información de debug...</p>';
            
            try {
                // Primero probar endpoint simple
                console.log('Probando endpoint simple...');
                const testResponse = await fetch('/VolandoUy-WebApp/api/usuarios/test');
                console.log('Test response:', testResponse.status);
                
                if (!testResponse.ok) {
                    throw new Error(`Endpoint simple falló: HTTP ${testResponse.status}`);
                }
                
                const testData = await testResponse.json();
                console.log('Test data:', testData);
                
                resultsDiv.innerHTML += `
                    <div class="result success">
                        <h3>✅ Endpoint Simple Funcionando</h3>
                        <p><strong>Status:</strong> ${testResponse.status}</p>
                        <p><strong>Response:</strong></p>
                        <pre>${JSON.stringify(testData, null, 2)}</pre>
                    </div>
                `;
                
                // Ahora probar endpoint específico de reservas
                console.log('Probando endpoint de check-reservas...');
                const checkUrl = '/VolandoUy-WebApp/api/usuarios/check-reservas?nickname=anita20182005';
                console.log('Check URL:', checkUrl);
                
                const checkResponse = await fetch(checkUrl);
                console.log('Check response:', checkResponse.status);
                
                if (checkResponse.ok) {
                    const checkData = await checkResponse.json();
                    console.log('Check data:', checkData);
                    
                    resultsDiv.innerHTML += `
                        <div class="result ${checkData.reservas_count != null && checkData.reservas_count > 0 ? 'success' : 'error'}">
                            <h3>🔍 Verificación de Reservas</h3>
                            <p><strong>Status:</strong> ${checkResponse.status}</p>
                            <p><strong>Reservas encontradas:</strong> ${checkData.reservas_count}</p>
                            <p><strong>mostrarDatosUsuario:</strong> ${checkData.mostrarDatosUsuario}</p>
                            <p><strong>mostrarDatosUsuarioMod:</strong> ${checkData.mostrarDatosUsuarioMod}</p>
                            <p><strong>Detalle de reservas:</strong></p>
                            <pre>${JSON.stringify(checkData.reservas_detalle, null, 2)}</pre>
                        </div>
                    `;
                } else {
                    resultsDiv.innerHTML += `
                        <div class="result error">
                            <h3>❌ Error en Check-Reservas</h3>
                            <p><strong>Status:</strong> ${checkResponse.status}</p>
                            <p>No se pudo verificar las reservas</p>
                        </div>
                    `;
                }
                
                // También probar endpoint de verificación de BD
                console.log('Probando endpoint de verificación de BD...');
                const bdUrl = '/VolandoUy-WebApp/api/usuarios/verificar-bd?nickname=anita20182005';
                console.log('BD URL:', bdUrl);
                
                const bdResponse = await fetch(bdUrl);
                console.log('BD response:', bdResponse.status);
                
                if (bdResponse.ok) {
                    const bdData = await bdResponse.json();
                    console.log('BD data:', bdData);
                    
                    resultsDiv.innerHTML += `
                        <div class="result ${bdData.usuario_existe === true ? 'success' : 'error'}">
                            <h3>🔍 Verificación de Base de Datos</h3>
                            <p><strong>Status:</strong> ${bdResponse.status}</p>
                            <p><strong>Usuario existe:</strong> ${bdData.usuario_existe}</p>
                            <p><strong>Tipo de usuario:</strong> ${bdData.tipo_usuario}</p>
                            <p><strong>Es cliente:</strong> ${bdData.es_cliente}</p>
                            <p><strong>mostrarDatosUsuario funciona:</strong> ${bdData.mostrarDatosUsuario_funciona}</p>
                            <p><strong>Reservas en mostrarDatosUsuario:</strong> ${bdData.reservas_en_mostrarDatosUsuario != null ? bdData.reservas_en_mostrarDatosUsuario : 0}</p>
                            <p><strong>Diagnóstico:</strong> ${bdData.diagnostico}</p>
                            <p><strong>Response completa:</strong></p>
                            <pre>${JSON.stringify(bdData, null, 2)}</pre>
                        </div>
                    `;
                } else {
                    resultsDiv.innerHTML += `
                        <div class="result error">
                            <h3>❌ Error en Verificación de BD</h3>
                            <p><strong>Status:</strong> ${bdResponse.status}</p>
                            <p>No se pudo verificar la base de datos</p>
                        </div>
                    `;
                }
                
                // También probar endpoint de debug original
                console.log('Iniciando llamada al endpoint de debug...');
                const url = '/VolandoUy-WebApp/api/usuarios/debug?nickname=anita20182005';
                console.log('URL:', url);
                
                const response = await fetch(url);
                console.log('Response recibida:', response);
                console.log('Response status:', response.status);
                console.log('Response ok:', response.ok);
                
                if (!response.ok) {
                    throw new Error(`HTTP ${response.status}: ${response.statusText}`);
                }
                
                const data = await response.json();
                console.log('Data recibida:', data);
                
                let statusClass = response.ok ? 'success' : 'error';
                
                resultsDiv.innerHTML = `
                    <div class="result ${statusClass}">
                        <h3>🔍 Debug Usuario (anita20182005)</h3>
                        <p><strong>Status HTTP:</strong> ${response.status}</p>
                        <p><strong>Response:</strong></p>
                        <pre>${JSON.stringify(data, null, 2)}</pre>
                    </div>
                `;
                
                // Análisis automático
                if (data.mostrarDatosUsuario === 'OK' && data.reservas_count === 0) {
                    resultsDiv.innerHTML += `
                        <div class="result error">
                            <h3>📋 Análisis del Problema</h3>
                            <p><strong>Diagnóstico:</strong> El método mostrarDatosUsuario funciona correctamente, pero no encuentra reservas.</p>
                            <p><strong>Posibles causas:</strong></p>
                            <ul>
                                <li>Las reservas no están asociadas correctamente en la base de datos</li>
                                <li>El método no está cargando las reservas correctamente</li>
                                <li>Las reservas existen pero con un formato diferente</li>
                            </ul>
                        </div>
                    `;
                } else if (data.mostrarDatosUsuario === 'NULL') {
                    resultsDiv.innerHTML += `
                        <div class="result error">
                            <h3>📋 Análisis del Problema</h3>
                            <p><strong>Diagnóstico:</strong> El método mostrarDatosUsuario no está funcionando.</p>
                            <p><strong>Causa:</strong> El método depende de una lista de usuarios que no está inicializada correctamente.</p>
                        </div>
                    `;
                }
                
            } catch (error) {
                console.error('Error completo:', error);
                resultsDiv.innerHTML = `
                    <div class="result error">
                        <h3>❌ Error</h3>
                        <p><strong>Error:</strong> ${error.message}</p>
                        <p><strong>Tipo de error:</strong> ${error.name}</p>
                        <p><strong>Stack trace:</strong></p>
                        <pre>${error.stack}</pre>
                        <p>Esto indica que hay un problema con el servidor o el endpoint.</p>
                        <p><strong>Pasos para diagnosticar:</strong></p>
                        <ol>
                            <li>Abre la consola del navegador (F12) y revisa los logs</li>
                            <li>Verifica que el servidor esté ejecutándose</li>
                            <li>Comprueba que el endpoint esté disponible</li>
                        </ol>
                    </div>
                `;
            }
        }
        
        // Ejecutar cuando se carga la página
        document.addEventListener('DOMContentLoaded', testDebugEndpoint);
    </script>
</body>
</html>
