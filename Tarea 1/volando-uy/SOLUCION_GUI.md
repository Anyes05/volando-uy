# Solución para Ejecutar la GUI desde el JAR

## Problema

Los componentes GUI generados por IntelliJ IDEA desde el archivo `.form` no se inicializan correctamente cuando se ejecuta desde un JAR.

## Solución: Incluir forms_rt.jar

### Paso 1: Localizar forms_rt.jar

El archivo `forms_rt.jar` se encuentra en el directorio de instalación de IntelliJ IDEA:
- **Windows**: `C:\Program Files\JetBrains\IntelliJ IDEA <versión>\lib\forms_rt.jar`
- **Linux/Mac**: `/opt/idea/lib/forms_rt.jar` o `~/idea/lib/forms_rt.jar`

### Paso 2: Copiar forms_rt.jar al proyecto

1. Crear un directorio `lib` en la raíz del proyecto:
   ```
   Tarea 1/volando-uy/lib/
   ```

2. Copiar `forms_rt.jar` a ese directorio:
   ```
   Tarea 1/volando-uy/lib/forms_rt.jar
   ```

### Paso 3: Instalar el JAR en el repositorio local de Maven

Ejecutar desde el directorio del proyecto:
```powershell
cd "Tarea 1\volando-uy"
mvn install:install-file -Dfile=lib/forms_rt.jar -DgroupId=com.intellij -DartifactId=forms_rt -Dversion=1.0 -Dpackaging=jar
```

**NOTA:** Este paso solo necesita ejecutarse una vez. El `pom.xml` está configurado para hacerlo automáticamente, pero si falla, puedes ejecutarlo manualmente.

### Paso 4: Recompilar

Ejecutar desde la raíz del proyecto:
```powershell
cd "Tarea 1\volando-uy"
mvn clean package -DskipTests
```

O desde la raíz del proyecto completo:
```powershell
.\build.bat
```

### Paso 5: Probar

```powershell
java -jar dist\servidor.jar gui
```

## Alternativa: Configurar IntelliJ para generar código en archivos fuente

1. Ir a `File` > `Settings` > `Editor` > `GUI Designer`
2. En "Generate GUI into", seleccionar "Java source code" en lugar de "Binary class files"
3. Recompilar el proyecto

Esto hará que IntelliJ genere el código de la GUI directamente en los archivos `.java`, lo que facilita su inclusión en el JAR.

