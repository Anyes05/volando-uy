@echo off
REM Script para crear los archivos de configuración en ~/volandouy/

set CONFIG_DIR=%USERPROFILE%\volandouy

echo ==========================================
echo Creando archivos de configuracion
echo ==========================================
echo.

REM Crear directorio si no existe
if not exist "%CONFIG_DIR%" (
    mkdir "%CONFIG_DIR%"
    echo Directorio creado: %CONFIG_DIR%
) else (
    echo Directorio ya existe: %CONFIG_DIR%
)
echo.

REM Crear servidor.properties
echo Creando servidor.properties...
(
echo # Configuracion del Servidor Central - Web Services
echo # Este archivo contiene la configuracion de IP, puerto y URL base del servicio web
echo.
echo # Direccion IP del servidor ^(usar 0.0.0.0 para escuchar en todas las interfaces^)
echo servidor.central.ip=0.0.0.0
echo.
echo # Puerto donde se publicara el Web Service
echo servidor.central.puerto=8082
echo.
echo # Contexto/path del servicio web
echo servidor.central.contexto=/centralws
) > "%CONFIG_DIR%\servidor.properties"
echo ✓ servidor.properties creado

REM Crear cliente.properties
echo Creando cliente.properties...
(
echo # Configuracion del Cliente Web - Web Services
echo # Este archivo contiene la URL del Servidor Central para consumir los Web Services
echo.
echo # URL completa del Servidor Central ^(incluye IP, puerto y contexto^)
echo # Formato: http://IP:PUERTO/CONTEXTO
echo # Ejemplo para servidor local: http://localhost:8082/centralws
echo # Ejemplo para servidor remoto: http://192.168.1.100:8082/centralws
echo.
echo servidor.central.url=http://localhost:8082/centralws
echo.
echo # Timeout para las conexiones ^(en milisegundos^)
echo servidor.central.timeout=30000
) > "%CONFIG_DIR%\cliente.properties"
echo ✓ cliente.properties creado

REM Crear database.properties
echo Creando database.properties...
(
echo # Configuracion de la Base de Datos
echo # Este archivo contiene la configuracion de conexion a PostgreSQL
echo.
echo # Driver de la base de datos
echo db.driver=org.postgresql.Driver
echo.
echo # URL de conexion a la base de datos
echo # Formato: jdbc:postgresql://HOST:PUERTO/NOMBRE_BD
echo db.url=jdbc:postgresql://localhost:5432/volandoUy
echo.
echo # Usuario de la base de datos
echo db.user=postgres
echo.
echo # Contrasena de la base de datos
echo db.password=0000
echo.
echo # Configuracion de Hibernate
echo hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
echo hibernate.hbm2ddl.auto=update
echo hibernate.show_sql=true
echo hibernate.format_sql=true
echo hibernate.connection.autocommit=false
echo hibernate.jdbc.batch_size=20
echo hibernate.order_inserts=true
echo hibernate.order_updates=true
echo hibernate.jdbc.batch_versioned_data=true
echo hibernate.jdbc.lob.non_contextual_creation=true
) > "%CONFIG_DIR%\database.properties"
echo ✓ database.properties creado

echo.
echo ==========================================
echo Archivos de configuracion creados!
echo ==========================================
echo Ubicacion: %CONFIG_DIR%
echo.
echo Archivos creados:
dir /b "%CONFIG_DIR%\*.properties"
echo.
echo NOTA: Puede editar estos archivos para ajustar la configuracion
echo sin necesidad de recompilar los archivos JAR/WAR.
echo.

