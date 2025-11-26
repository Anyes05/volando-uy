#!/bin/bash

# Script para desplegar web.war en Tomcat
# Este script copia el WAR a webapps y Tomcat lo despliega automáticamente

echo "=========================================="
echo "Desplegando web.war en Tomcat"
echo "=========================================="
echo ""

# Verificar que existe web.war
if [ ! -f "dist/web.war" ]; then
    echo "ERROR: No se encontró web.war en dist/"
    echo "Por favor, ejecute build.sh primero para generar el WAR"
    exit 1
fi

# Detectar Tomcat
TOMCAT_PATH=""
if [ -d "$CATALINA_HOME" ]; then
    TOMCAT_PATH="$CATALINA_HOME"
elif [ -d "/opt/tomcat" ]; then
    TOMCAT_PATH="/opt/tomcat"
elif [ -d "$HOME/apache-tomcat-9.0.110" ]; then
    TOMCAT_PATH="$HOME/apache-tomcat-9.0.110"
else
    echo "ERROR: No se encontró Tomcat"
    echo "Por favor, configure CATALINA_HOME o instale Tomcat"
    exit 1
fi

echo "Tomcat encontrado en: $TOMCAT_PATH"
echo ""

# Detener Tomcat si está corriendo (opcional, pero recomendado)
echo "Verificando si Tomcat está corriendo..."
if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1 ; then
    echo "Tomcat está corriendo. Deteniendo..."
    "$TOMCAT_PATH/bin/shutdown.sh"
    sleep 5
    echo "Tomcat detenido."
else
    echo "Tomcat no está corriendo."
fi
echo ""

# Copiar WAR a webapps
echo "Copiando web.war a $TOMCAT_PATH/webapps/..."
cp "dist/web.war" "$TOMCAT_PATH/webapps/web.war"
if [ $? -ne 0 ]; then
    echo "ERROR: No se pudo copiar web.war"
    exit 1
fi
echo "[OK] web.war copiado correctamente"
echo ""

# Eliminar directorio anterior si existe (para forzar redespliegue)
if [ -d "$TOMCAT_PATH/webapps/web" ]; then
    echo "Eliminando despliegue anterior..."
    rm -rf "$TOMCAT_PATH/webapps/web"
    echo "[OK] Despliegue anterior eliminado"
    echo ""
fi

# Iniciar Tomcat
echo "Iniciando Tomcat..."
export CATALINA_HOME="$TOMCAT_PATH"
"$TOMCAT_PATH/bin/startup.sh"
echo ""

echo "=========================================="
echo "Despliegue completado!"
echo "=========================================="
echo ""
echo "Tomcat se está iniciando. Espera unos segundos y luego accede a:"
echo "http://localhost:8080/web/"
echo ""
echo "NOTA: Si Tomcat ya estaba corriendo, el WAR se desplegará automáticamente."
echo "      Puede tardar unos segundos en desplegarse."
echo ""

