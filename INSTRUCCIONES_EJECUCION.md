# 🚀 Instrucciones Paso a Paso - Ejecutar el Sistema

## 📋 Índice
1. [Paso 1: Configurar el Servidor Central](#paso-1-configurar-el-servidor-central)
2. [Paso 2: Ejecutar el Servidor Central](#paso-2-ejecutar-el-servidor-central)
3. [Paso 3: Verificar que el Servidor Central Funciona](#paso-3-verificar-que-el-servidor-central-funciona)
4. [Paso 4: Configurar el Servidor Web](#paso-4-configurar-el-servidor-web)
5. [Paso 5: Compilar y Desplegar el Servidor Web](#paso-5-compilar-y-desplegar-el-servidor-web)
6. [Paso 6: Probar los Endpoints REST](#paso-6-probar-los-endpoints-rest)
7. [Solución de Problemas](#solución-de-problemas)

---

## Paso 1: Configurar el Servidor Central

### 📍 Ubicación del archivo:
```
Tarea 1/volando-uy/src/main/resources/config.properties
```

### 🔧 Opción A: Desarrollo Local (Todo en tu computadora)

Abre el archivo `config.properties` y déjalo así:

```properties
# Configuración del Servidor Central - Web Services
servidor.central.ip=0.0.0.0
servidor.central.puerto=8082
servidor.central.contexto=/centralws
```

**Explicación:**
- `0.0.0.0` = Escucha en todas las interfaces (localhost y red local)
- `8082` = Puerto donde se publicará el servicio
- `/centralws` = Path del servicio web

### 🔧 Opción B: Servidor en Red Local (Otra computadora puede conectarse)

Si quieres que otra computadora en tu red pueda conectarse:

1. **Obtén tu IP local:**
   - **Windows:** Abre PowerShell y ejecuta: `ipconfig`
     - Busca "Dirección IPv4" (ejemplo: `192.168.1.100`)
   - **Linux/Mac:** Abre terminal y ejecuta: `ifconfig` o `ip addr`
     - Busca "inet" (ejemplo: `192.168.1.100`)

2. **Edita el archivo** (puedes dejar `0.0.0.0` que funciona igual):
   ```properties
   servidor.central.ip=0.0.0.0
   servidor.central.puerto=8082
   servidor.central.contexto=/centralws
   ```

   **Nota:** `0.0.0.0` permite conexiones desde cualquier IP. Si quieres restringir, usa tu IP específica.

### ✅ Verificación:
- [ ] Archivo guardado correctamente
- [ ] No hay espacios extra antes/después del `=`
- [ ] El puerto 8082 no está siendo usado por otra aplicación

---

## Paso 2: Ejecutar el Servidor Central

### 🎯 Método 1: Desde IntelliJ IDEA (Recomendado)

1. **Abre el proyecto en IntelliJ:**
   - Abre la carpeta `Tarea 1/volando-uy` como proyecto Maven

2. **Navega al archivo:**
   - Ve a: `src/main/java/logica/ws/PublicadorWS.java`

3. **Ejecuta el programa:**
   - **Opción A:** Click derecho en `PublicadorWS.java` → `Run 'PublicadorWS.main()'`
   - **Opción B:** Click en el icono ▶️ verde al lado de `public static void main`
   - **Opción C:** Presiona `Shift + F10`

4. **Verifica la salida:**
   Deberías ver algo como esto en la consola:
   ```
   ==========================================
   >>> Configuración del Servidor Central:
   >>> IP: 0.0.0.0
   >>> Puerto: 8082
   >>> Contexto: /centralws
   >>> URL completa: http://localhost:8082/centralws
   ==========================================
   >>> Iniciando Publicador CentralWS...
   >>> ✓ CentralWS publicado correctamente.
   >>> ✓ WSDL disponible en: http://localhost:8082/centralws?wsdl
   >>> ✓ Servicio escuchando en todas las interfaces (0.0.0.0)
   >>> Presiona Ctrl+C para detener el servidor.
   ```

5. **¡IMPORTANTE!** Deja esta ventana/consola abierta. El servidor debe seguir ejecutándose.

### 🎯 Método 2: Desde Línea de Comandos (Terminal)

1. **Abre una terminal/PowerShell:**
   - Navega a la carpeta del proyecto:
     ```bash
     cd "Tarea 1/volando-uy"
     ```

2. **Compila el proyecto:**
   ```bash
   mvn clean compile
   ```

3. **Ejecuta el publicador:**
   ```bash
   mvn exec:java -Dexec.mainClass="logica.ws.PublicadorWS"
   ```

4. **Verifica la salida** (debe ser similar al Método 1)

### ⚠️ Si el puerto 8082 está ocupado:

**Error típico:** `Address already in use` o `Puerto en uso`

**Solución:**
1. Cambia el puerto en `config.properties`:
   ```properties
   servidor.central.puerto=8083
   ```
2. O cierra la aplicación que está usando el puerto 8082

---

## Paso 3: Verificar que el Servidor Central Funciona

### 🌐 Método 1: Desde el Navegador

1. **Abre tu navegador** (Chrome, Firefox, Edge, etc.)

2. **Ve a la URL del WSDL:**
   ```
   http://localhost:8082/centralws?wsdl
   ```

3. **Resultado esperado:**
   - Deberías ver un documento XML (el WSDL del servicio)
   - Si ves el XML, ¡el servidor está funcionando! ✅

### 🌐 Método 2: Probar el método "ping"

1. **Abre tu navegador**

2. **Ve a:**
   ```
   http://localhost:8082/centralws
   ```

3. **Deberías ver información del servicio**

### 🔍 Si no funciona:

- **Verifica que el servidor esté ejecutándose** (debe haber una consola abierta)
- **Verifica el puerto** en `config.properties`
- **Verifica que no haya firewall bloqueando** el puerto 8082
- **Prueba con `127.0.0.1` en lugar de `localhost`:** `http://127.0.0.1:8082/centralws?wsdl`

---

## Paso 4: Configurar el Servidor Web

### 📍 Ubicación del archivo:
```
Tarea 2/VolandoUy-WebApp/src/main/resources/config.properties
```

### 🔧 Configuración

Abre el archivo `config.properties` del Servidor Web.

### Opción A: Servidor Central en la misma máquina (localhost)

```properties
# Configuración del Servidor Web - Cliente Web Services
servidor.central.url=http://localhost:8082/centralws
servidor.central.timeout=30000
```

### Opción B: Servidor Central en otra máquina de la red

Si el Servidor Central está en otra computadora:

1. **Obtén la IP del Servidor Central** (de la computadora donde ejecutaste `PublicadorWS`)

2. **Edita el archivo:**
   ```properties
   servidor.central.url=http://192.168.1.100:8082/centralws
   servidor.central.timeout=30000
   ```
   
   **Reemplaza `192.168.1.100`** por la IP real del Servidor Central.

### ✅ Verificación:
- [ ] Archivo guardado correctamente
- [ ] La URL coincide con la del Servidor Central
- [ ] El Servidor Central está ejecutándose

---

## Paso 5: Compilar y Desplegar el Servidor Web

### 🎯 Método 1: Desde IntelliJ con Tomcat Integrado

1. **Abre el proyecto:**
   - Abre la carpeta `Tarea 2/VolandoUy-WebApp` como proyecto Maven

2. **Configura Tomcat:**
   - Ve a `Run` → `Edit Configurations...`
   - Click en `+` → `Tomcat Server` → `Local`
   - En `Deployment`, agrega el artefacto `VolandoUy-WebApp:war`
   - En `Server`, configura el puerto (por defecto 8080)

3. **Ejecuta:**
   - Click en `Run` o presiona `Shift + F10`

### 🎯 Método 2: Compilar WAR y Desplegar Manualmente

1. **Abre una terminal en la carpeta del Servidor Web:**
   ```bash
   cd "Tarea 2/VolandoUy-WebApp"
   ```

2. **Compila y empaqueta:**
   ```bash
   mvn clean package
   ```

3. **Busca el archivo WAR generado:**
   - Debe estar en: `target/VolandoUy-WebApp.war`

4. **Despliega en Tomcat:**
   - Copia `VolandoUy-WebApp.war` a la carpeta `webapps` de Tomcat
   - O usa el Manager de Tomcat para desplegar

5. **Inicia Tomcat** (si no está corriendo)

### 🎯 Método 3: Usar Maven con Plugin de Tomcat

1. **Agrega el plugin al `pom.xml`** (si no está):
   ```xml
   <plugin>
       <groupId>org.apache.tomcat.maven</groupId>
       <artifactId>tomcat7-maven-plugin</artifactId>
       <version>2.2</version>
       <configuration>
           <port>8080</port>
           <path>/</path>
       </configuration>
   </plugin>
   ```

2. **Ejecuta:**
   ```bash
   mvn tomcat7:run
   ```

### ✅ Verificación:
- [ ] El servidor web inicia sin errores
- [ ] Puedes acceder a: `http://localhost:8080`
- [ ] No hay errores en la consola relacionados con Web Services

---

## Paso 6: Probar los Endpoints REST

### 🌐 Método 1: Desde el Navegador

1. **Prueba el endpoint "ping":**
   ```
   http://localhost:8080/VolandoUy-WebApp/api/central/ping
   ```
   
   **Resultado esperado:** JSON como:
   ```json
   {
     "mensaje": "Servicio CentralWS operativo - 1234567890",
     "timestamp": "1234567890"
   }
   ```

2. **Prueba listar aerolíneas:**
   ```
   http://localhost:8080/VolandoUy-WebApp/api/central/aerolineas
   ```
   
   **Resultado esperado:** Array JSON con aerolíneas

3. **Prueba listar ciudades:**
   ```
   http://localhost:8080/VolandoUy-WebApp/api/central/ciudades
   ```

### 🌐 Método 2: Usando cURL (Terminal)

```bash
# Ping
curl http://localhost:8080/VolandoUy-WebApp/api/central/ping

# Aerolíneas
curl http://localhost:8080/VolandoUy-WebApp/api/central/aerolineas

# Ciudades
curl http://localhost:8080/VolandoUy-WebApp/api/central/ciudades
```

### 🌐 Método 3: Usando Postman o Insomnia

1. **Abre Postman/Insomnia**

2. **Crea una nueva petición GET:**
   - URL: `http://localhost:8080/VolandoUy-WebApp/api/central/ping`
   - Method: `GET`

3. **Envía la petición**

4. **Verifica la respuesta JSON**

### 📋 Endpoints Disponibles:

| Endpoint | Descripción |
|----------|-------------|
| `GET /api/central/ping` | Probar conexión |
| `GET /api/central/aerolineas` | Listar aerolíneas |
| `GET /api/central/ciudades` | Listar ciudades |
| `GET /api/central/aeropuertos` | Listar aeropuertos |
| `GET /api/central/rutas` | Listar todas las rutas |
| `GET /api/central/rutas?aerolinea=NICKNAME` | Rutas de una aerolínea |
| `GET /api/central/vuelos?ruta=NOMBRE_RUTA` | Vuelos de una ruta |
| `GET /api/central/clientes` | Listar clientes |
| `GET /api/central/usuarios` | Listar usuarios |
| `GET /api/central/paquetes` | Listar paquetes |

### ✅ Verificación:
- [ ] El endpoint `/api/central/ping` responde correctamente
- [ ] Los otros endpoints devuelven datos JSON
- [ ] No hay errores de conexión al Servidor Central

---

## Solución de Problemas

### ❌ Error: "Connection refused" al probar endpoints

**Causa:** El Servidor Central no está ejecutándose o la URL es incorrecta.

**Solución:**
1. Verifica que `PublicadorWS` esté ejecutándose
2. Verifica la URL en `Tarea 2/.../config.properties`
3. Prueba acceder al WSDL: `http://localhost:8082/centralws?wsdl`

### ❌ Error: "Puerto 8082 ya en uso"

**Causa:** Otra aplicación está usando el puerto 8082.

**Solución:**
1. Cambia el puerto en `config.properties` del Servidor Central:
   ```properties
   servidor.central.puerto=8083
   ```
2. Actualiza también la URL en el Servidor Web:
   ```properties
   servidor.central.url=http://localhost:8083/centralws
   ```

### ❌ Error: "WSDL not found" o "404 Not Found"

**Causa:** La URL del WSDL es incorrecta o el servicio no se publicó correctamente.

**Solución:**
1. Verifica que `PublicadorWS` muestre "✓ CentralWS publicado correctamente"
2. Verifica la URL en el navegador: debe terminar con `?wsdl`
3. Revisa los logs del `PublicadorWS` para ver errores

### ❌ Error: "Timeout" al consumir servicios

**Causa:** El servidor tarda mucho en responder o hay problemas de red.

**Solución:**
1. Aumenta el timeout en `config.properties` del Servidor Web:
   ```properties
   servidor.central.timeout=60000
   ```
2. Verifica la conectividad de red
3. Verifica que el Servidor Central esté respondiendo

### ❌ Error: "ClassNotFoundException" o errores de compilación

**Causa:** Faltan dependencias o el proyecto no está compilado.

**Solución:**
1. Ejecuta `mvn clean install` en ambos proyectos
2. Verifica que las dependencias JAX-WS estén en los `pom.xml`
3. Refresca el proyecto en IntelliJ: `File` → `Invalidate Caches / Restart`

### ❌ El Servidor Web no puede conectarse al Servidor Central

**Causa:** Firewall bloqueando o IP incorrecta.

**Solución:**
1. Verifica que ambos servidores estén en la misma red
2. Desactiva temporalmente el firewall para probar
3. Usa `localhost` si están en la misma máquina
4. Verifica la IP del Servidor Central con `ipconfig` (Windows) o `ifconfig` (Linux/Mac)

---

## ✅ Checklist Final

Antes de considerar que todo está funcionando:

- [ ] Servidor Central ejecutándose (`PublicadorWS`)
- [ ] WSDL accesible: `http://localhost:8082/centralws?wsdl`
- [ ] Servidor Web desplegado y ejecutándose
- [ ] Endpoint `/api/central/ping` responde correctamente
- [ ] Endpoint `/api/central/aerolineas` devuelve datos
- [ ] No hay errores en las consolas de ambos servidores

---

## 🎉 ¡Listo!

Si todos los pasos funcionan correctamente, tienes:
- ✅ Servidor Central publicando Web Services SOAP
- ✅ Servidor Web consumiendo los servicios
- ✅ Endpoints REST disponibles para el Dispositivo Móvil
- ✅ Configuración externa (sin hardcodeo)

**Próximo paso:** Integra estos endpoints en tu aplicación web o móvil.

---

## 📞 ¿Necesitas Ayuda?

Si encuentras problemas:
1. Revisa los logs de ambos servidores
2. Verifica la configuración en los archivos `.properties`
3. Consulta la sección "Solución de Problemas" arriba
4. Revisa `GUIA_WEBSERVICES.md` para más detalles técnicos

