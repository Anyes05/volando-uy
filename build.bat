@echo off
setlocal enabledelayedexpansion
REM Script de compilación para generar los archivos JAR y WAR
REM Genera: servidor.jar, web.war

REM Cambiar al directorio del script
pushd "%~dp0"
set SCRIPT_DIR=%CD%

echo ==========================================
echo Compilando VolandoUy - Generando archivos
echo ==========================================
echo.

REM Compilar Tarea 1 (servidor.jar)
echo [1/2] Compilando Tarea 1 - Servidor Central...
cd /d "%SCRIPT_DIR%\Tarea 1\volando-uy"
if not exist "%CD%" (
    echo ERROR: No se pudo acceder al directorio Tarea 1\volando-uy
    popd
    exit /b 1
)
call mvn clean package -DskipTests
set COMPILE_ERROR=%ERRORLEVEL%
cd /d "%SCRIPT_DIR%"
if !COMPILE_ERROR! NEQ 0 (
    echo ERROR: Fallo la compilacion de Tarea 1
    popd
    exit /b 1
)
echo [OK] Tarea 1 compilada correctamente

REM Compilar Tarea 2 (web.war)
echo.
echo [2/2] Compilando Tarea 2 - Aplicacion Web...
cd /d "%SCRIPT_DIR%\Tarea 2\VolandoUy-WebApp"
if not exist "%CD%" (
    echo ERROR: No se pudo acceder al directorio Tarea 2\VolandoUy-WebApp
    popd
    exit /b 1
)
call mvn clean package -DskipTests
set COMPILE_ERROR=%ERRORLEVEL%
cd /d "%SCRIPT_DIR%"
if !COMPILE_ERROR! NEQ 0 (
    echo ERROR: Fallo la compilacion de Tarea 2
    popd
    exit /b 1
)
echo [OK] Tarea 2 compilada correctamente

REM Copiar archivos a directorio de distribucion
echo.
echo Copiando archivos generados...
if not exist "%SCRIPT_DIR%\dist" (
    mkdir "%SCRIPT_DIR%\dist"
    if !ERRORLEVEL! NEQ 0 (
        echo ERROR: No se pudo crear el directorio dist
        popd
        exit /b 1
    )
)

REM Verificar que existe servidor.jar antes de copiar
set JAR_SOURCE=%SCRIPT_DIR%\Tarea 1\volando-uy\target\servidor.jar
set JAR_DEST=%SCRIPT_DIR%\dist\servidor.jar

if not exist "!JAR_SOURCE!" (
    echo ERROR: No se encontro servidor.jar en Tarea 1\volando-uy\target\
    echo Verifique que la compilacion fue exitosa
    popd
    exit /b 1
)

copy "!JAR_SOURCE!" "!JAR_DEST!" /Y
if !ERRORLEVEL! NEQ 0 (
    echo ERROR: No se pudo copiar servidor.jar
    popd
    exit /b 1
) else (
    echo [OK] servidor.jar copiado a dist/
)

REM Verificar que existe web.war antes de copiar
set WAR_SOURCE=%SCRIPT_DIR%\Tarea 2\VolandoUy-WebApp\target\web.war
set WAR_DEST=%SCRIPT_DIR%\dist\web.war

if not exist "!WAR_SOURCE!" (
    echo ERROR: No se encontro web.war en Tarea 2\VolandoUy-WebApp\target\
    echo Verifique que la compilacion fue exitosa
    popd
    exit /b 1
)

copy "!WAR_SOURCE!" "!WAR_DEST!" /Y
if !ERRORLEVEL! NEQ 0 (
    echo ERROR: No se pudo copiar web.war
    popd
    exit /b 1
) else (
    echo [OK] web.war copiado a dist/
)

echo.
echo ==========================================
echo Compilacion completada!
echo ==========================================
echo Archivos generados en el directorio 'dist':
if exist "%SCRIPT_DIR%\dist\servidor.jar" (
    echo   [OK] servidor.jar
) else (
    echo   [ERROR] servidor.jar - NO ENCONTRADO
)
if exist "%SCRIPT_DIR%\dist\web.war" (
    echo   [OK] web.war
) else (
    echo   [ERROR] web.war - NO ENCONTRADO
)
echo.
echo NOTA: Asegurese de configurar los archivos .properties en:
echo %USERPROFILE%\volandouy\
echo.

popd
endlocal

