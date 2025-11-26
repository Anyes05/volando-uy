#!/bin/bash

# Script para crear los archivos de configuración en ~/volandouy/

CONFIG_DIR="$HOME/volandouy"

echo "=========================================="
echo "Creando archivos de configuración"
echo "=========================================="
echo ""

# Crear directorio si no existe
if [ ! -d "$CONFIG_DIR" ]; then
    mkdir -p "$CONFIG_DIR"
    echo "Directorio creado: $CONFIG_DIR"
else
    echo "Directorio ya existe: $CONFIG_DIR"
fi
echo ""

# Crear servidor.properties
echo "Creando servidor.properties..."
cat > "$CONFIG_DIR/servidor.properties" << 'EOF'
# Configuración del Servidor Central - Web Services
# Este archivo contiene la configuración de IP, puerto y URL base del servicio web

# Dirección IP del servidor (usar 0.0.0.0 para escuchar en todas las interfaces)
servidor.central.ip=0.0.0.0

# Puerto donde se publicará el Web Service
servidor.central.puerto=8082

# Contexto/path del servicio web
servidor.central.contexto=/centralws
EOF
echo "✓ servidor.properties creado"

# Crear cliente.properties
echo "Creando cliente.properties..."
cat > "$CONFIG_DIR/cliente.properties" << 'EOF'
# Configuración del Cliente Web - Web Services
# Este archivo contiene la URL del Servidor Central para consumir los Web Services

# URL completa del Servidor Central (incluye IP, puerto y contexto)
# Formato: http://IP:PUERTO/CONTEXTO
# Ejemplo para servidor local: http://localhost:8082/centralws
# Ejemplo para servidor remoto: http://192.168.1.100:8082/centralws

servidor.central.url=http://localhost:8082/centralws

# Timeout para las conexiones (en milisegundos)
servidor.central.timeout=30000
EOF
echo "✓ cliente.properties creado"

# Crear database.properties
echo "Creando database.properties..."
cat > "$CONFIG_DIR/database.properties" << 'EOF'
# Configuración de la Base de Datos
# Este archivo contiene la configuración de conexión a PostgreSQL

# Driver de la base de datos
db.driver=org.postgresql.Driver

# URL de conexión a la base de datos
# Formato: jdbc:postgresql://HOST:PUERTO/NOMBRE_BD
db.url=jdbc:postgresql://localhost:5432/volandoUy

# Usuario de la base de datos
db.user=postgres

# Contraseña de la base de datos
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
EOF
echo "✓ database.properties creado"

echo ""
echo "=========================================="
echo "Archivos de configuración creados!"
echo "=========================================="
echo "Ubicación: $CONFIG_DIR"
echo ""
echo "Archivos creados:"
ls -la "$CONFIG_DIR"/*.properties
echo ""
echo "NOTA: Puede editar estos archivos para ajustar la configuración"
echo "sin necesidad de recompilar los archivos JAR/WAR."
echo ""

