#!/bin/bash

# Script de compilación para generar los archivos JAR y WAR
# Genera: servidor.jar, web.war

echo "=========================================="
echo "Compilando VolandoUy - Generando archivos"
echo "=========================================="
echo ""

# Compilar Tarea 1 (servidor.jar)
echo "[1/2] Compilando Tarea 1 - Servidor Central..."
cd "Tarea 1/volando-uy"
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "ERROR: Falló la compilación de Tarea 1"
    cd ../..
    exit 1
fi
echo "✓ Tarea 1 compilada correctamente"
cd ../..

# Compilar Tarea 2 (web.war)
echo ""
echo "[2/2] Compilando Tarea 2 - Aplicación Web..."
cd "Tarea 2/VolandoUy-WebApp"
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "ERROR: Falló la compilación de Tarea 2"
    cd ../..
    exit 1
fi
echo "✓ Tarea 2 compilada correctamente"
cd ../..

# Copiar archivos a directorio de distribución
echo ""
echo "Copiando archivos generados..."
mkdir -p dist

cp "Tarea 1/volando-uy/target/servidor.jar" "dist/servidor.jar" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ servidor.jar copiado a dist/"
else
    echo "ADVERTENCIA: No se pudo copiar servidor.jar"
fi

cp "Tarea 2/VolandoUy-WebApp/target/web.war" "dist/web.war" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ web.war copiado a dist/"
else
    echo "ADVERTENCIA: No se pudo copiar web.war"
fi

echo ""
echo "=========================================="
echo "Compilación completada!"
echo "=========================================="
echo "Archivos generados en el directorio 'dist':"
ls -la dist/
echo ""
echo "NOTA: Asegúrese de configurar los archivos .properties en:"
echo "$HOME/volandouy/"
echo ""

