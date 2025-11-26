@echo off
REM Script para desplegar web.war en Tomcat
REM Este script copia el WAR a webapps y Tomcat lo despliega automáticamente

echo ==========================================
echo Desplegando web.war en Tomcat
echo ==========================================
echo.

REM Verificar que existe web.war
if not exist "dist\web.war" (
    echo ERROR: No se encontro web.war en dist/
    echo Por favor, ejecute build.bat primero para generar el WAR
    pause
    exit /b 1
)

REM Detectar Tomcat
set TOMCAT_PATH=
if exist "C:\apache-tomcat-9.0.110" (
    set TOMCAT_PATH=C:\apache-tomcat-9.0.110
) else if exist "%CATALINA_HOME%" (
    set TOMCAT_PATH=%CATALINA_HOME%
) else (
    echo ERROR: No se encontro Tomcat
    echo Por favor, configure CATALINA_HOME o instale Tomcat en C:\apache-tomcat-9.0.110
    pause
    exit /b 1
)

echo Tomcat encontrado en: %TOMCAT_PATH%
echo.

REM Detener Tomcat si esta corriendo (opcional, pero recomendado)
echo Verificando si Tomcat esta corriendo...
netstat -ano | findstr :8080 >nul
if %ERRORLEVEL% EQU 0 (
    echo Tomcat esta corriendo. Deteniendo...
    call "%TOMCAT_PATH%\bin\shutdown.bat"
    timeout /t 5 /nobreak >nul
    echo Tomcat detenido.
) else (
    echo Tomcat no esta corriendo.
)
echo.

REM Copiar WAR a webapps
echo Copiando web.war a %TOMCAT_PATH%\webapps\...
copy "dist\web.war" "%TOMCAT_PATH%\webapps\web.war" /Y
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo copiar web.war
    pause
    exit /b 1
)
echo [OK] web.war copiado correctamente
echo.

REM Eliminar directorio anterior si existe (para forzar redespliegue)
if exist "%TOMCAT_PATH%\webapps\web" (
    echo Eliminando despliegue anterior...
    rmdir /s /q "%TOMCAT_PATH%\webapps\web"
    echo [OK] Despliegue anterior eliminado
    echo.
)

REM Iniciar Tomcat
echo Iniciando Tomcat...
set CATALINA_HOME=%TOMCAT_PATH%
call "%TOMCAT_PATH%\bin\startup.bat"
echo.

echo ==========================================
echo Despliegue completado!
echo ==========================================
echo.
echo Tomcat se esta iniciando. Espera unos segundos y luego accede a:
echo http://localhost:8080/web/
echo.
echo NOTA: Si Tomcat ya estaba corriendo, el WAR se desplegara automaticamente.
echo       Puede tardar unos segundos en desplegarse.
echo.
pause

