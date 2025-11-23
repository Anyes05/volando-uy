# 📱 Guía: Acceder a la Aplicación desde tu Celular

## 📋 Requisitos Previos

1. ✅ **Servidor Central ejecutándose** (puerto 8082)
2. ✅ **Servidor Web ejecutándose** (puerto 8080)
3. ✅ **Celular y computadora en la misma red WiFi**

---

## 🔧 Paso 1: Obtener tu IP Local

### Windows:
1. Abre **PowerShell** o **CMD**
2. Ejecuta: `ipconfig`
3. Busca **"Dirección IPv4"** en la sección de tu adaptador WiFi/Ethernet
   - Ejemplo: `192.168.1.100`

### Linux/Mac:
1. Abre la **Terminal**
2. Ejecuta: `ifconfig` o `ip addr`
3. Busca **"inet"** en tu adaptador de red
   - Ejemplo: `192.168.1.100`

**Anota esta IP, la necesitarás en los siguientes pasos.**

---

## 🔧 Paso 2: Configurar el Servidor Central

### Ubicación del archivo:
```
Tarea 1/volando-uy/src/main/resources/config.properties
```

### Configuración:
Asegúrate de que el archivo tenga:
```properties
# Configuración del Servidor Central - Web Services
servidor.central.ip=0.0.0.0
servidor.central.puerto=8082
servidor.central.contexto=/centralws
```

**✅ Importante:** `0.0.0.0` permite conexiones desde cualquier dispositivo en tu red.

---

## 🔧 Paso 3: Configurar el Servidor Web

### Ubicación del archivo:
```
Tarea 2/VolandoUy-WebApp/src/main/resources/config.properties
```

### Configuración:
**Reemplaza `localhost` con tu IP local:**

```properties
# Configuración del Servidor Web - Cliente Web Services
# URL completa del Servidor Central (incluye IP, puerto y contexto)

# ⚠️ IMPORTANTE: Reemplaza 192.168.1.100 con TU IP local
servidor.central.url=http://192.168.1.100:8082/centralws

# Timeout para las conexiones (en milisegundos)
servidor.central.timeout=30000
```

**Ejemplo:** Si tu IP es `192.168.1.100`, la línea debe ser:
```properties
servidor.central.url=http://192.168.1.100:8082/centralws
```

---

## 🔧 Paso 4: Configurar el Servidor Web para Acceso desde Red

### Opción A: Si usas Tomcat integrado en IntelliJ

1. **Edita la configuración de Tomcat:**
   - Ve a `Run` → `Edit Configurations...`
   - Selecciona tu configuración de Tomcat
   - En la pestaña **"Server"**:
     - **VM options:** Agrega: `-Djava.net.preferIPv4Stack=true`
     - **Host:** Cambia de `localhost` a `0.0.0.0` (o déjalo vacío)

2. **O modifica el `pom.xml`** si usas el plugin de Tomcat:
   ```xml
   <plugin>
       <groupId>org.apache.tomcat.maven</groupId>
       <artifactId>tomcat7-maven-plugin</artifactId>
       <version>2.2</version>
       <configuration>
           <port>8080</port>
           <path>/</path>
           <address>0.0.0.0</address>  <!-- Permite acceso desde red -->
       </configuration>
   </plugin>
   ```

### Opción B: Si usas Tomcat standalone

1. **Edita `server.xml`** de Tomcat:
   - Ubicación: `[Tomcat]/conf/server.xml`
   - Busca la línea con `<Connector port="8080"...>`
   - Cambia `address="127.0.0.1"` a `address="0.0.0.0"` o elimina el atributo `address`

---

## 🚀 Paso 5: Reiniciar los Servidores

### 1. Detén el Servidor Central (si está corriendo)
   - Presiona `Ctrl+C` en la consola donde está ejecutándose

### 2. Detén el Servidor Web (si está corriendo)
   - Detén Tomcat desde IntelliJ o desde el Manager

### 3. Recompila el Servidor Web (para aplicar cambios en config.properties)
   ```bash
   cd "Tarea 2/VolandoUy-WebApp"
   mvn clean compile
   ```

### 4. Inicia el Servidor Central
   - Ejecuta `PublicadorWS.java` desde IntelliJ
   - Verifica que muestre: `✓ Servicio escuchando en todas las interfaces (0.0.0.0)`

### 5. Inicia el Servidor Web
   - Ejecuta Tomcat desde IntelliJ o inicia Tomcat standalone

---

## 📱 Paso 6: Acceder desde tu Celular

### 1. Conecta tu celular a la misma red WiFi que tu computadora

### 2. Abre el navegador en tu celular

### 3. Ingresa la URL:
   ```
   http://TU_IP_LOCAL:8080
   ```
   
   **Ejemplo:** Si tu IP es `192.168.1.100`:
   ```
   http://192.168.1.100:8080
   ```

### 4. Si el servidor web está en un contexto específico:
   ```
   http://TU_IP_LOCAL:8080/VolandoUy-WebApp
   ```

---

## ✅ Verificación

### Desde tu computadora:
1. Abre el navegador
2. Ve a: `http://TU_IP_LOCAL:8080`
3. Deberías ver la página de inicio

### Desde tu celular:
1. Abre el navegador
2. Ve a: `http://TU_IP_LOCAL:8080`
3. Deberías ver la misma página de inicio

### Verificar que el Web Service funciona:
Desde tu celular, intenta hacer login o cualquier operación que use el Web Service.

---

## 🔍 Solución de Problemas

### ❌ No puedo acceder desde el celular

**Problema 1: Firewall bloqueando conexiones**
- **Windows:** 
  - Ve a `Configuración` → `Firewall de Windows Defender`
  - `Configuración avanzada` → `Reglas de entrada`
  - Crea una nueva regla para permitir el puerto 8080 y 8082
- **Linux:** 
  ```bash
  sudo ufw allow 8080/tcp
  sudo ufw allow 8082/tcp
  ```

**Problema 2: IP incorrecta en config.properties**
- Verifica que la IP en `Tarea 2/VolandoUy-WebApp/src/main/resources/config.properties` sea tu IP local actual
- No uses `localhost` o `127.0.0.1`

**Problema 3: Servidor Central no accesible**
- Verifica que en `Tarea 1/volando-uy/src/main/resources/config.properties` esté:
  ```properties
  servidor.central.ip=0.0.0.0
  ```
- No uses `127.0.0.1` o `localhost`

**Problema 4: Celular y computadora en redes diferentes**
- Asegúrate de que ambos estén conectados a la misma red WiFi
- Verifica que el celular no esté usando datos móviles

### ❌ Error 500 al hacer operaciones desde el celular

**Problema:** El servidor web no puede conectarse al servidor central

**Solución:**
1. Verifica que el Servidor Central esté ejecutándose
2. Desde tu celular, intenta acceder directamente al WSDL:
   ```
   http://TU_IP_LOCAL:8082/centralws?wsdl
   ```
   Si no funciona, el problema está en el Servidor Central
3. Verifica que `config.properties` del Servidor Web tenga la IP correcta (no `localhost`)

### ❌ La página carga pero las funciones no funcionan

**Problema:** CORS o problemas de configuración

**Solución:**
- Verifica los logs del servidor web para ver errores específicos
- Asegúrate de que el `config.properties` tenga la IP correcta del Servidor Central

---

## 📝 Resumen de URLs

### Desde tu computadora:
- **Servidor Web:** `http://localhost:8080`
- **Servidor Central:** `http://localhost:8082/centralws`

### Desde tu celular (reemplaza `TU_IP_LOCAL` con tu IP):
- **Servidor Web:** `http://TU_IP_LOCAL:8080`
- **Servidor Central:** `http://TU_IP_LOCAL:8082/centralws`

---

## 🎯 Ejemplo Completo

Supongamos que tu IP local es `192.168.1.100`:

### Archivo: `Tarea 1/volando-uy/src/main/resources/config.properties`
```properties
servidor.central.ip=0.0.0.0
servidor.central.puerto=8082
servidor.central.contexto=/centralws
```

### Archivo: `Tarea 2/VolandoUy-WebApp/src/main/resources/config.properties`
```properties
servidor.central.url=http://192.168.1.100:8082/centralws
servidor.central.timeout=30000
```

### URL desde el celular:
```
http://192.168.1.100:8080
```

---

## 💡 Tips Adicionales

1. **IP Dinámica:** Si tu IP cambia frecuentemente, considera configurar una IP estática en tu router
2. **Seguridad:** Esta configuración solo funciona en tu red local. Para acceso externo necesitarías configurar port forwarding en tu router
3. **Pruebas:** Siempre prueba primero desde tu computadora usando la IP local antes de probar desde el celular

---

¡Listo! Ahora deberías poder acceder a la aplicación desde tu celular. 🎉

