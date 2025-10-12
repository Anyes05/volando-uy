# 🚀 Instrucciones de Deployment - VolandoUY WebApp

## 📋 **Prerrequisitos**

### **1. Base de Datos PostgreSQL**
```sql
-- Crear base de datos
CREATE DATABASE volando_uy;

-- Crear usuario (opcional)
CREATE USER volando_user WITH PASSWORD 'volando_pass';
GRANT ALL PRIVILEGES ON DATABASE volando_uy TO volando_user;
```

### **2. Servidor Central (JAR)**
- Copiar el archivo `volando-uy-1.0-SNAPSHOT.jar` del proyecto Tarea 1 a:
  ```
  Tarea 2/VolandoUy-WebApp/lib/volando-uy-server.jar
  ```

**Comandos para copiar:**

**Opción 1: Scripts automáticos**
```bash
# Windows CMD
cd "Tarea 2\VolandoUy-WebApp"
copiar-jar.bat

# PowerShell
cd "Tarea 2\VolandoUy-WebApp"
.\copiar-jar.ps1
```

**Opción 2: Comandos manuales**
```bash
# Windows CMD
copy "Tarea 1\volando-uy\target\volando-uy-1.0-SNAPSHOT.jar" "Tarea 2\VolandoUy-WebApp\lib\volando-uy-server.jar"

# PowerShell
Copy-Item "Tarea 1\volando-uy\target\volando-uy-1.0-SNAPSHOT.jar" "Tarea 2\VolandoUy-WebApp\lib\volando-uy-server.jar"
```

### **3. Configuración de Base de Datos**
**Nota:** La configuración de base de datos está incluida en el JAR del Servidor Central. No es necesario configurar JPA en este proyecto.

## 🔧 **Compilación y Deployment**

### **1. Compilar el Proyecto**
```bash
cd "Tarea 2/VolandoUy-WebApp"
mvn clean compile
```

### **2. Empaquetar WAR**
```bash
mvn package
```

### **3. Desplegar en Tomcat**
```bash
# Copiar el WAR generado a la carpeta webapps de Tomcat
cp target/VolandoUy-WebApp.war $TOMCAT_HOME/webapps/

# O usar el manager de Tomcat para subir el archivo
```

### **4. Iniciar Tomcat**
```bash
# Windows
$TOMCAT_HOME/bin/startup.bat

# Linux/Mac
$TOMCAT_HOME/bin/startup.sh
```

## 🌐 **URLs de Acceso**

### **Páginas Principales**
- **Inicio**: `http://localhost:8080/VolandoUy-WebApp/`
- **Consulta Usuario**: `http://localhost:8080/VolandoUy-WebApp/vista/consultaUsuario`
- **Registro Usuario**: `http://localhost:8080/VolandoUy-WebApp/vista/registrarUsuario`
- **Login**: `http://localhost:8080/VolandoUy-WebApp/vista/inicioSesion`

### **APIs REST**
- **Usuarios**: `http://localhost:8080/VolandoUy-WebApp/api/usuarios`
- **Vuelos**: `http://localhost:8080/VolandoUy-WebApp/api/vuelos`
- **Rutas**: `http://localhost:8080/VolandoUy-WebApp/api/rutas`
- **Reservas**: `http://localhost:8080/VolandoUy-WebApp/api/reservas`
- **Paquetes**: `http://localhost:8080/VolandoUy-WebApp/api/paquetes`

## 🔍 **Funcionalidades Implementadas**

### **✅ Control de Vista con JSP**
- **VistaController**: Maneja navegación entre páginas JSP
- **Integración Servidor Central**: Carga datos desde base de datos
- **JSTL**: Renderizado dinámico en servidor

### **✅ Caso de Uso "Consulta Usuario"**
- **Listado de Usuarios**: Carga desde Servidor Central
- **Filtros**: Por tipo (Cliente/Aerolínea) y búsqueda por nombre
- **Detalle de Usuario**: Información completa con tabs
- **API REST**: Endpoint `/api/usuarios` para datos JSON

### **✅ Arquitectura Completa**
- **Frontend**: JSP + CSS + JavaScript
- **Backend**: Servlets + Servidor Central
- **Base de Datos**: PostgreSQL + JPA/Hibernate
- **APIs**: RESTful para comunicación frontend-backend

## 🐛 **Solución de Problemas**

### **Error de Conexión a Base de Datos**
1. Verificar que PostgreSQL esté ejecutándose
2. Comprobar que la base de datos `volandoUy` exista
3. Verificar configuración en el JAR del Servidor Central

### **Error de JAR del Servidor Central**
1. Verificar que `volando-uy-server.jar` esté en `lib/`
2. Comprobar que el JAR contenga las clases necesarias
3. Revisar logs de Tomcat para errores de clase no encontrada
4. **Comando para copiar el JAR:**
   ```bash
   copy "Tarea 1\volando-uy\target\volando-uy-1.0-SNAPSHOT.jar" "Tarea 2\VolandoUy-WebApp\lib\volando-uy-server.jar"
   ```

### **Error de Compilación Maven**
1. Verificar versión de Java (requiere Java 11+)
2. Limpiar caché: `mvn clean`
3. Verificar dependencias en `pom.xml`

## 📊 **Estructura del Proyecto**

```
VolandoUy-WebApp/
├── src/main/
│   ├── java/com/volandouy/
│   │   ├── controller/          # Servlets controladores
│   │   └── filter/             # Filtros (UTF-8, etc.)
│   ├── resources/META-INF/
│   │   └── persistence.xml     # Configuración JPA
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── lib/            # JAR del Servidor Central
│       │   └── web.xml         # Configuración web
│       ├── css/                # Estilos CSS
│       ├── js/                 # JavaScript
│       ├── img/                # Imágenes
│       └── *.jsp               # Páginas JSP
├── pom.xml                     # Configuración Maven
└── target/                     # Archivos compilados
```

## 🎯 **Próximos Pasos**

1. **Completar otros controladores** (VueloController, RutaController, etc.)
2. **Implementar autenticación** de usuarios
3. **Agregar validaciones** de formularios
4. **Crear pruebas unitarias** e integración
5. **Optimizar rendimiento** y caché

## 📞 **Soporte**

Para problemas técnicos:
1. Revisar logs de Tomcat en `$TOMCAT_HOME/logs/`
2. Verificar logs de la aplicación en consola
3. Comprobar configuración de base de datos
4. Validar que todas las dependencias estén presentes
