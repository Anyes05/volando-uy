# 🔧 Solución: Error 404 en /api/central/ping

## Problema
Estás obteniendo un error 404 al intentar acceder a:
```
http://localhost:8080/api/central/ping
```

## Causas Posibles

1. **El proyecto no se recompiló** después de agregar `CentralProxyController`
2. **El WAR no se reconstruyó** con el nuevo servlet
3. **Tomcat necesita reiniciarse** para cargar los cambios
4. **El servlet no está en el classpath** correcto

## ✅ Solución Paso a Paso

### Opción 1: Recompilar y Redesplegar desde IntelliJ

1. **Recompila el proyecto:**
   - En IntelliJ: `Build` → `Rebuild Project`
   - O presiona `Ctrl + Shift + F9`

2. **Verifica que no hay errores de compilación:**
   - Revisa la ventana "Build" en la parte inferior
   - Debe decir "BUILD SUCCESSFUL"

3. **Redesplega en Tomcat:**
   - Si usas Tomcat integrado en IntelliJ:
     - Ve a `Run` → `Edit Configurations...`
     - Selecciona tu configuración de Tomcat
     - En la pestaña "Deployment", verifica que `VolandoUy-WebApp:war` esté agregado
     - Click en "Apply" y "OK"
   - **Reinicia Tomcat:**
     - Click en el botón de "Stop" (cuadrado rojo)
     - Luego click en "Run" (triángulo verde) para iniciar nuevamente

4. **Espera a que Tomcat termine de iniciar:**
   - Deberías ver en la consola: "Server startup in [X] milliseconds"

5. **Prueba nuevamente:**
   - Abre: `http://localhost:8080/api/central/ping`

### Opción 2: Compilar y Desplegar Manualmente

1. **Abre una terminal/PowerShell** en la carpeta del proyecto:
   ```powershell
   cd "Tarea 2\VolandoUy-WebApp"
   ```

2. **Limpia y compila:**
   ```powershell
   mvn clean compile
   ```

3. **Empaqueta el WAR:**
   ```powershell
   mvn package
   ```

4. **Busca el archivo WAR generado:**
   - Debe estar en: `target\VolandoUy-WebApp.war`

5. **Despliega en Tomcat:**
   - Si Tomcat está ejecutándose, deténlo primero
   - Copia `VolandoUy-WebApp.war` a la carpeta `webapps` de Tomcat
   - O elimina la carpeta `webapps\VolandoUy-WebApp` si existe
   - Inicia Tomcat nuevamente

6. **Prueba:**
   - Abre: `http://localhost:8080/api/central/ping`

### Opción 3: Verificar que el Servlet Está Compilado

1. **Verifica que el archivo .class existe:**
   - Navega a: `Tarea 2\VolandoUy-WebApp\target\classes\com\volandouy\controller\`
   - Debe existir: `CentralProxyController.class`

2. **Si no existe, recompila:**
   - En IntelliJ: `Build` → `Rebuild Project`

### Opción 4: Verificar Logs de Tomcat

1. **Revisa los logs de Tomcat:**
   - En IntelliJ: Ve a la pestaña "Tomcat" en la parte inferior
   - O busca el archivo `catalina.log` en la carpeta `logs` de Tomcat

2. **Busca errores relacionados con:**
   - `CentralProxyController`
   - `ClassNotFoundException`
   - `ServletException`

3. **Si hay errores, compártelos** para diagnosticar mejor

## 🔍 Verificaciones Adicionales

### Verificar que el Servlet Está Correctamente Anotado

El archivo `CentralProxyController.java` debe tener:
```java
@WebServlet("/api/central/*")
public class CentralProxyController extends HttpServlet {
    // ...
}
```

### Verificar que el Proyecto Está Configurado Correctamente

1. **Verifica el `pom.xml`:**
   - Debe tener la dependencia `javax.servlet-api`
   - Debe tener el plugin `maven-war-plugin`

2. **Verifica la estructura del proyecto:**
   ```
   Tarea 2/VolandoUy-WebApp/
   ├── src/main/java/com/volandouy/controller/
   │   └── CentralProxyController.java  ← Debe existir
   └── src/main/webapp/WEB-INF/
       └── web.xml
   ```

### Verificar que Tomcat Está Ejecutándose Correctamente

1. **Prueba la página principal:**
   - Abre: `http://localhost:8080/`
   - Deberías ver algo (aunque sea un error 404 de la raíz, significa que Tomcat funciona)

2. **Verifica el puerto:**
   - El puerto por defecto de Tomcat es 8080
   - Si cambiaste el puerto, ajusta la URL

## 🚨 Si Nada Funciona

### Paso 1: Verificar que el Servidor Central Está Ejecutándose

Antes de probar el endpoint REST, asegúrate de que el Servidor Central funciona:

1. **Verifica que `PublicadorWS` está ejecutándose**
2. **Prueba el WSDL:**
   - Abre: `http://localhost:8082/centralws?wsdl`
   - Deberías ver el XML del WSDL

### Paso 2: Crear un Servlet de Prueba Simple

Para verificar que los servlets funcionan, crea uno de prueba:

```java
package com.volandouy.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.getWriter().write("Servlet funciona!");
    }
}
```

1. **Compila y despliega**
2. **Prueba:** `http://localhost:8080/test`
3. **Si funciona:** El problema es específico de `CentralProxyController`
4. **Si no funciona:** Hay un problema más general con la configuración de servlets

### Paso 3: Verificar Dependencias

Asegúrate de que todas las dependencias estén en el `pom.xml`:

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
```

## 📝 Checklist Final

Antes de reportar que no funciona, verifica:

- [ ] El proyecto se recompiló sin errores
- [ ] El archivo `CentralProxyController.class` existe en `target/classes`
- [ ] Tomcat se reinició después de compilar
- [ ] El WAR se reconstruyó (`mvn package`)
- [ ] No hay errores en los logs de Tomcat
- [ ] El Servidor Central está ejecutándose en el puerto 8082
- [ ] La URL es correcta: `http://localhost:8080/api/central/ping` (no `/api/central/ping/`)

## 💡 Solución Rápida (Recomendada)

**En IntelliJ:**

1. `Build` → `Rebuild Project`
2. Detén Tomcat (botón Stop)
3. Inicia Tomcat nuevamente (botón Run)
4. Espera a que termine de iniciar
5. Prueba: `http://localhost:8080/api/central/ping`

**Si aún no funciona, comparte:**
- Los logs de Tomcat
- El contenido de la carpeta `target/classes/com/volandouy/controller/`
- Cualquier error que aparezca en la consola

