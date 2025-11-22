# Guía Completa: Implementación de Web Services en Arquitectura Distribuida

## 📋 Índice
1. [Arquitectura General](#arquitectura-general)
2. [Servidor Central - Publicar Web Services](#servidor-central)
3. [Servidor Web - Consumir Web Services](#servidor-web)
4. [Dispositivo Móvil - Consumir Web Services](#dispositivo-móvil)
5. [Configuración de IPs y Puertos](#configuración)
6. [Ejemplos de Uso](#ejemplos)

---

## 🏗️ Arquitectura General

```
┌─────────────────┐         ┌─────────────────┐
│ Dispositivo     │  HTTP   │  Servidor Web   │
│ Móvil           │─────────▶│  (Tarea 2)      │
│ (Tarea 3)       │         │                 │
└─────────────────┘         └────────┬────────┘
                                     │
                                     │ Web Services (SOAP)
                                     │ HTTP
                                     ▼
                            ┌─────────────────┐
                            │ Servidor Central│
                            │  (Tarea 1)      │
                            │                 │
                            │  - Lógica       │
                            │  - Datos        │
                            └─────────────────┘
```

**Protocolo de Comunicación:**
- Dispositivo Móvil ↔ Servidor Web: **HTTP/REST** (recomendado) o **HTTP directo**
- Servidor Web ↔ Servidor Central: **Web Services SOAP (JAX-WS)**
- Dispositivo Móvil ↔ Servidor Central: **Web Services SOAP** (vía proxy en Servidor Web)

---

## 🖥️ Servidor Central - Publicar Web Services

### Estructura de Archivos

```
Tarea 1/volando-uy/
├── src/main/java/logica/ws/
│   ├── CentralWS.java          # Interfaz del Web Service
│   ├── PublicadorWS.java       # Clase para publicar el servicio
│   └── ConfiguracionWS.java    # Utilidad para leer config.properties
├── src/main/resources/
│   └── config.properties       # Configuración de IP, puerto, contexto
└── pom.xml                     # Dependencias JAX-WS ya incluidas
```

### Paso 1: Configurar el archivo `config.properties`

**Ubicación:** `Tarea 1/volando-uy/src/main/resources/config.properties`

```properties
# Configuración del Servidor Central - Web Services
servidor.central.ip=0.0.0.0
servidor.central.puerto=8082
servidor.central.contexto=/centralws
```

**Explicación:**
- `servidor.central.ip=0.0.0.0`: Escucha en todas las interfaces de red (permite conexiones remotas)
- `servidor.central.puerto=8082`: Puerto donde se publicará el servicio
- `servidor.central.contexto=/centralws`: Path del servicio web

**Para servidor remoto:** Cambia `0.0.0.0` por la IP específica si es necesario.

### Paso 2: El Web Service (`CentralWS.java`)

El Web Service ya está implementado y expone métodos del `Sistema`:

```java
@WebService(name = "CentralWS", targetNamespace = "http://ws.logica/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CentralWS {
    private final Sistema sistema = Sistema.getInstance();
    
    @WebMethod(operationName = "ping")
    public String ping() { ... }
    
    @WebMethod(operationName = "listarAerolineas")
    public List<DTAerolinea> listarAerolineas() { ... }
    
    // ... más métodos
}
```

### Paso 3: Publicar el Servicio

**Opción A: Ejecutar `PublicadorWS.java`**

1. Abre `PublicadorWS.java` en tu IDE
2. Ejecuta como "Java Application"
3. El servicio se publicará en la URL configurada en `config.properties`

**Opción B: Desde línea de comandos**

```bash
cd "Tarea 1/volando-uy"
mvn compile exec:java -Dexec.mainClass="logica.ws.PublicadorWS"
```

### Paso 4: Verificar que el Servicio está Activo

1. Abre tu navegador
2. Ve a: `http://localhost:8082/centralws?wsdl`
3. Deberías ver el WSDL del servicio

**Para verificar desde otra máquina:**
- Reemplaza `localhost` por la IP del servidor
- Ejemplo: `http://192.168.1.100:8082/centralws?wsdl`

---

## 🌐 Servidor Web - Consumir Web Services

### Estructura de Archivos

```
Tarea 2/VolandoUy-WebApp/
├── src/main/java/com/volandouy/central/
│   ├── CentralService.java           # Interfaz del servicio
│   ├── CentralServiceWS.java         # Implementación que consume SOAP
│   ├── CentralServiceLocal.java      # Implementación local (sin WS)
│   ├── ServiceFactory.java           # Factory para obtener instancia
│   └── ConfiguracionClienteWS.java   # Utilidad para leer config
├── src/main/resources/
│   └── config.properties             # URL del Servidor Central
└── pom.xml                           # Dependencias JAX-WS ya incluidas
```

### Paso 1: Configurar el archivo `config.properties`

**Ubicación:** `Tarea 2/VolandoUy-WebApp/src/main/resources/config.properties`

```properties
# URL completa del Servidor Central
servidor.central.url=http://localhost:8082/centralws
servidor.central.timeout=30000
```

**Para servidor remoto:**
```properties
servidor.central.url=http://192.168.1.100:8082/centralws
```

### Paso 2: Usar el Servicio en tus Controladores

**Ejemplo en un Servlet:**

```java
@WebServlet("/api/aerolineas")
public class AerolineaController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener el servicio (lee config.properties automáticamente)
        CentralService servicio = ServiceFactory.getCentralService();
        
        try {
            // Llamar al método del Web Service
            List<DTAerolinea> aerolineas = servicio.listarAerolineas();
            
            // Convertir a JSON y enviar respuesta
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            mapper.writeValue(response.getWriter(), aerolineas);
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
```

**Ejemplo completo:**

```java
package com.volandouy.controller;

import com.volandouy.central.CentralService;
import com.volandouy.central.ServiceFactory;
import logica.DataTypes.DTAerolinea;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/aerolineas")
public class AerolineaAPIController extends HttpServlet {
    
    private CentralService centralService;
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void init() {
        // Inicializar el servicio una vez al arrancar el servlet
        centralService = ServiceFactory.getCentralService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            List<DTAerolinea> aerolineas = centralService.listarAerolineas();
            
            response.setContentType("application/json; charset=UTF-8");
            mapper.writeValue(response.getWriter(), aerolineas);
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
```

### Paso 3: Crear Endpoints REST como Proxy (Recomendado para Móvil)

Para que el Dispositivo Móvil pueda consumir fácilmente, crea endpoints REST en el Servidor Web:

```java
@WebServlet("/api/central/*")
public class CentralProxyController extends HttpServlet {
    
    private CentralService centralService;
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void init() {
        centralService = ServiceFactory.getCentralService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json; charset=UTF-8");
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Operación no especificada\"}");
                return;
            }
            
            String operacion = pathInfo.substring(1); // Remover "/"
            
            switch (operacion) {
                case "ping":
                    String resultado = ((CentralServiceWS) centralService).ping();
                    mapper.writeValue(response.getWriter(), Map.of("mensaje", resultado));
                    break;
                    
                case "aerolineas":
                    List<DTAerolinea> aerolineas = centralService.listarAerolineas();
                    mapper.writeValue(response.getWriter(), aerolineas);
                    break;
                    
                case "ciudades":
                    List<DTCiudad> ciudades = ((CentralServiceWS) centralService).listarCiudades();
                    mapper.writeValue(response.getWriter(), ciudades);
                    break;
                    
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Operación no encontrada\"}");
            }
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
```

---

## 📱 Dispositivo Móvil - Consumir Web Services

### Opción 1: Consumir vía Proxy REST (Recomendado)

El Dispositivo Móvil llama a endpoints REST del Servidor Web, que internamente consume el SOAP:

```javascript
// Ejemplo en JavaScript (aplicación web móvil)

async function listarAerolineas() {
    try {
        const response = await fetch('http://servidor-web:8080/api/central/aerolineas', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const aerolineas = await response.json();
        console.log('Aerolíneas:', aerolineas);
        return aerolineas;
        
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
```

### Opción 2: Consumir SOAP directamente (Avanzado)

Si necesitas llamar directamente al SOAP desde JavaScript, usa una librería como `soap`:

```javascript
// Instalar: npm install soap

const soap = require('soap');

const wsdlUrl = 'http://192.168.1.100:8082/centralws?wsdl';

soap.createClient(wsdlUrl, (err, client) => {
    if (err) {
        console.error('Error:', err);
        return;
    }
    
    // Llamar al método
    client.listarAerolineas((err, result) => {
        if (err) {
            console.error('Error:', err);
            return;
        }
        
        console.log('Aerolíneas:', result);
    });
});
```

### Configuración en el Dispositivo Móvil

Crea un archivo `config.properties` o equivalente:

```properties
servidor.central.url=http://192.168.1.100:8082/centralws
servidor.web.url=http://192.168.1.100:8080
```

---

## ⚙️ Configuración de IPs y Puertos

### Escenario 1: Todo en la misma máquina (Desarrollo)

**Servidor Central (`Tarea 1/volando-uy/src/main/resources/config.properties`):**
```properties
servidor.central.ip=0.0.0.0
servidor.central.puerto=8082
servidor.central.contexto=/centralws
```

**Servidor Web (`Tarea 2/VolandoUy-WebApp/src/main/resources/config.properties`):**
```properties
servidor.central.url=http://localhost:8082/centralws
```

### Escenario 2: Servidores en máquinas diferentes (Producción)

**Servidor Central (IP: 192.168.1.100):**
```properties
servidor.central.ip=0.0.0.0
servidor.central.puerto=8082
servidor.central.contexto=/centralws
```

**Servidor Web (IP: 192.168.1.101):**
```properties
servidor.central.url=http://192.168.1.100:8082/centralws
```

**Dispositivo Móvil:**
```javascript
const SERVIDOR_WEB_URL = 'http://192.168.1.101:8080';
const SERVIDOR_CENTRAL_URL = 'http://192.168.1.100:8082/centralws';
```

### Verificar Conectividad

**Desde el Servidor Web, prueba la conexión:**

```bash
# Verificar que el WSDL es accesible
curl http://192.168.1.100:8082/centralws?wsdl

# O desde el navegador
http://192.168.1.100:8082/centralws?wsdl
```

---

## 📝 Ejemplos de Uso

### Ejemplo 1: Listar Aerolíneas desde Servidor Web

```java
// En un Servlet o Controller
CentralService servicio = ServiceFactory.getCentralService();
List<DTAerolinea> aerolineas = servicio.listarAerolineas();

// Usar en JSP
request.setAttribute("aerolineas", aerolineas);
request.getRequestDispatcher("/consultaAerolinea.jsp").forward(request, response);
```

### Ejemplo 2: Consultar Vuelos de una Ruta

```java
CentralService servicio = ServiceFactory.getCentralService();
List<DTVuelo> vuelos = servicio.listarVuelosDeRuta("Montevideo-Buenos Aires");
```

### Ejemplo 3: Desde JavaScript (Dispositivo Móvil)

```javascript
// Llamar vía proxy REST
async function obtenerVuelos(nombreRuta) {
    const response = await fetch(
        `http://servidor-web:8080/api/central/vuelos?ruta=${encodeURIComponent(nombreRuta)}`,
        {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        }
    );
    
    const vuelos = await response.json();
    return vuelos;
}
```

---

## 🔧 Solución de Problemas

### Error: "Connection refused"

**Causa:** El Servidor Central no está ejecutándose o el puerto está bloqueado.

**Solución:**
1. Verifica que `PublicadorWS` esté ejecutándose
2. Verifica el firewall
3. Verifica la IP y puerto en `config.properties`

### Error: "WSDL not found"

**Causa:** La URL del WSDL es incorrecta.

**Solución:**
1. Verifica la URL en `config.properties` del Servidor Web
2. Asegúrate de que termine con `?wsdl`
3. Prueba acceder al WSDL desde el navegador

### Error: "Timeout"

**Causa:** El servidor tarda mucho en responder.

**Solución:**
1. Aumenta el timeout en `config.properties`:
   ```properties
   servidor.central.timeout=60000
   ```

---

## 📚 Recursos Adicionales

- **JAX-WS Documentation:** https://jakarta.ee/specifications/xml-web-services/
- **SOAP vs REST:** https://www.soapui.org/learn/api/soap-vs-rest-api.html
- **WSDL Viewer:** Usa herramientas como SoapUI o Postman para probar servicios SOAP

---

## ✅ Checklist de Implementación

- [ ] Servidor Central: `config.properties` configurado
- [ ] Servidor Central: `PublicadorWS` ejecutándose
- [ ] Servidor Central: WSDL accesible en `http://IP:PUERTO/centralws?wsdl`
- [ ] Servidor Web: `config.properties` con URL correcta del Servidor Central
- [ ] Servidor Web: `ServiceFactory` usando Web Services
- [ ] Servidor Web: Endpoints REST creados (opcional, para móvil)
- [ ] Dispositivo Móvil: Configuración de URLs correcta
- [ ] Pruebas: Verificar que todas las operaciones funcionan

---

**¡Listo!** Ahora tienes una arquitectura distribuida completa con Web Services configurados externamente. 🎉

