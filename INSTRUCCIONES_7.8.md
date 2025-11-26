# Instrucciones para el Requerimiento 7.8 - Distribución de la Aplicación Final

## Resumen

Este requerimiento implementa la generación de archivos JAR y WAR para distribución, y mueve toda la configuración a archivos externos ubicados en `~/volandouy/` (directorio home del usuario).

## Archivos Generados

1. **servidor.jar** - Contiene el Servidor Central y la Estación de Trabajo
2. **web.war** - Aplicación web para desplegar en Tomcat

## Configuración Externa

Todos los archivos de configuración se encuentran en: `~/volandouy/`

- **servidor.properties** - Configuración del Web Service (IP, puerto, contexto)
- **cliente.properties** - Configuración del cliente web (URL del servidor central)
- **database.properties** - Configuración de la base de datos PostgreSQL

## Pasos para Compilar

### Windows

1. **Crear archivos de configuración:**
   ```cmd
   crear-configuracion.bat
   ```

2. **Compilar todos los proyectos:**
   ```cmd
   build.bat
   ```

   Los archivos generados estarán en el directorio `dist/`:
   - `dist/servidor.jar`
   - `dist/web.war`

### Linux/Mac

1. **Dar permisos de ejecución:**
   ```bash
   chmod +x crear-configuracion.sh build.sh
   ```

2. **Crear archivos de configuración:**
   ```bash
   ./crear-configuracion.sh
   ```

3. **Compilar todos los proyectos:**
   ```bash
   ./build.sh
   ```

   Los archivos generados estarán en el directorio `dist/`:
   - `dist/servidor.jar`
   - `dist/web.war`

## Configuración de Archivos .properties

### 1. servidor.properties

Ubicación: `~/volandouy/servidor.properties`

```properties
# Dirección IP del servidor (0.0.0.0 para todas las interfaces)
servidor.central.ip=0.0.0.0

# Puerto del Web Service
servidor.central.puerto=8082

# Contexto del servicio
servidor.central.contexto=/centralws
```

### 2. cliente.properties

Ubicación: `~/volandouy/cliente.properties`

```properties
# URL del Servidor Central
servidor.central.url=http://localhost:8082/centralws

# Timeout en milisegundos
servidor.central.timeout=30000
```

**IMPORTANTE:** Si el servidor está en otra máquina, cambiar `localhost` por la IP del servidor.

### 3. database.properties

Ubicación: `~/volandouy/database.properties`

```properties
# Configuración de PostgreSQL
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/volandoUy
db.user=postgres
db.password=0000

# Configuración de Hibernate
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
hibernate.format_sql=true
hibernate.connection.autocommit=false
hibernate.jdbc.batch_size=20
hibernate.order_inserts=true
hibernate.order_updates=true
hibernate.jdbc.batch_versioned_data=true
hibernate.jdbc.lob.non_contextual_creation=true
```

## Ejecución

### 1. Ejecutar Servidor Central (servidor.jar)

**Opción A: Ejecutar solo el Web Service**
```bash
java -jar servidor.jar ws
```

**Opción B: Ejecutar solo la Estación de Trabajo (GUI)**
```bash
java -jar servidor.jar gui
```

**Opción C: Ejecutar sin argumentos (inicia GUI por defecto)**
```bash
java -jar servidor.jar
```

**NOTA:** Para ejecutar ambos (Web Service + GUI), abrir dos terminales:
- Terminal 1: `java -jar servidor.jar ws`
- Terminal 2: `java -jar servidor.jar gui`

### 2. Desplegar Aplicación Web (web.war)

1. Copiar `web.war` al directorio `webapps` de Tomcat:
   ```bash
   cp dist/web.war $CATALINA_HOME/webapps/
   ```

2. Iniciar Tomcat (si no está corriendo):
   ```bash
   $CATALINA_HOME/bin/startup.sh  # Linux/Mac
   $CATALINA_HOME/bin/startup.bat  # Windows
   ```

3. Acceder a la aplicación:
   ```
   http://localhost:8080/web/
   ```

## Pruebas de Funcionamiento

### Prueba 1: Verificar Configuración Externa

1. **Verificar que los archivos de configuración existen:**
   - Windows: `dir %USERPROFILE%\volandouy\*.properties`
   - Linux/Mac: `ls -la ~/volandouy/*.properties`

2. **Modificar servidor.properties** cambiando el puerto a 8083
3. **Reiniciar el servidor** y verificar que escucha en el puerto 8083
4. **Verificar en los logs** que se cargó la configuración desde `~/volandouy/servidor.properties`

### Prueba 2: Ejecutar servidor.jar

1. **Asegurarse de que PostgreSQL está corriendo**
2. **Ejecutar el Web Service:**
   ```bash
   java -jar dist/servidor.jar ws
   ```
3. **Verificar que el servicio está disponible:**
   - Abrir navegador: `http://localhost:8082/centralws?wsdl`
   - Debe mostrar el WSDL del servicio

4. **Ejecutar la Estación de Trabajo:**
   ```bash
   java -jar dist/servidor.jar gui
   ```
5. **Verificar que la GUI se abre correctamente**

### Prueba 3: Desplegar y Probar web.war

1. **Asegurarse de que el Web Service está corriendo** (Prueba 2)
2. **Configurar cliente.properties** con la URL correcta del servidor:
   ```properties
   servidor.central.url=http://localhost:8082/centralws
   ```

3. **Desplegar web.war en Tomcat:**
   ```bash
   cp dist/web.war $CATALINA_HOME/webapps/
   ```

4. **Iniciar Tomcat** (si no está corriendo)

5. **Acceder a la aplicación web:**
   ```
   http://localhost:8080/web/
   ```

6. **Probar funcionalidades:**
   - Iniciar sesión
   - Consultar vuelos
   - Realizar reservas
   - Verificar que se conecta correctamente al Web Service

### Prueba 4: Cambiar Configuración Sin Recompilar

1. **Detener el servidor** (Ctrl+C)
2. **Modificar `~/volandouy/servidor.properties`** cambiando el puerto a 8084
3. **Reiniciar el servidor:**
   ```bash
   java -jar dist/servidor.jar ws
   ```
4. **Verificar que el servidor escucha en el puerto 8084** (sin recompilar)
5. **Actualizar `~/volandouy/cliente.properties`** con el nuevo puerto:
   ```properties
   servidor.central.url=http://localhost:8084/centralws
   ```
6. **Reiniciar Tomcat** y verificar que la aplicación web se conecta al nuevo puerto

### Prueba 5: Configuración de Base de Datos Externa

1. **Modificar `~/volandouy/database.properties`** cambiando la URL de la base de datos
2. **Reiniciar el servidor**
3. **Verificar en los logs** que se conecta a la nueva base de datos
4. **Probar operaciones** que requieren acceso a la base de datos

## Estructura de Directorios

```
volando-uy/
├── Tarea 1/
│   └── volando-uy/          # Servidor Central
│       └── target/
│           └── servidor.jar
├── Tarea 2/
│   └── VolandoUy-WebApp/    # Aplicación Web
│       └── target/
│           └── web.war
├── dist/                     # Archivos de distribución
│   ├── servidor.jar
│   └── web.war
├── build.bat                 # Script de compilación (Windows)
├── build.sh                  # Script de compilación (Linux/Mac)
├── crear-configuracion.bat   # Script para crear config (Windows)
└── crear-configuracion.sh    # Script para crear config (Linux/Mac)
```

## Notas Importantes

1. **Los archivos de configuración se crean automáticamente** al ejecutar `crear-configuracion.bat` o `crear-configuracion.sh`

2. **La configuración externa tiene prioridad** sobre la configuración embebida en los recursos

3. **No es necesario recompilar** para cambiar la configuración, solo editar los archivos `.properties` en `~/volandouy/`

4. **El servidor.jar incluye todas las dependencias** gracias al plugin maven-shade-plugin

5. **Para ejecutar en diferentes máquinas:**
   - Servidor: Configurar `servidor.properties` con la IP correcta
   - Cliente: Configurar `cliente.properties` con la URL del servidor

## Solución de Problemas

### Error: "No se encontró el archivo config.properties"
- Verificar que los archivos existen en `~/volandouy/`
- Ejecutar `crear-configuracion.bat` o `crear-configuracion.sh`

### Error: "No se puede conectar a la base de datos"
- Verificar que PostgreSQL está corriendo
- Revisar `~/volandouy/database.properties`
- Verificar usuario y contraseña

### Error: "No se puede conectar al Web Service"
- Verificar que el servidor está corriendo: `java -jar servidor.jar ws`
- Revisar `~/volandouy/cliente.properties` (URL correcta)
- Verificar que el puerto no está en uso

### El servidor no inicia
- Verificar que Java está instalado: `java -version`
- Verificar que todas las dependencias están incluidas en el JAR
- Revisar los logs de error

