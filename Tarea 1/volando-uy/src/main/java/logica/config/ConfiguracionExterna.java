package logica.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Clase utilitaria para leer configuración desde archivos .properties ubicados
 * en el directorio ~/volandouy/ (directorio home del usuario).
 * 
 * Esta clase permite que la configuración se modifique sin necesidad de
 * recompilar los archivos JAR/WAR.
 */
public class ConfiguracionExterna {
    
    private static final String DIR_CONFIG = "volandouy";
    private static final String ARCHIVO_CONFIG_SERVIDOR = "servidor.properties";
    private static final String ARCHIVO_CONFIG_CLIENTE = "cliente.properties";
    private static final String ARCHIVO_CONFIG_DB = "database.properties";
    
    /**
     * Obtiene el directorio de configuración (~/volandouy/)
     */
    private static File getDirectorioConfig() {
        String userHome = System.getProperty("user.home");
        Path configDir = Paths.get(userHome, DIR_CONFIG);
        
        // Crear el directorio si no existe
        if (!Files.exists(configDir)) {
            try {
                Files.createDirectories(configDir);
                System.out.println(">>> Directorio de configuración creado: " + configDir);
            } catch (IOException e) {
                System.err.println(">>> ERROR: No se pudo crear el directorio de configuración: " + configDir);
                e.printStackTrace();
            }
        }
        
        return configDir.toFile();
    }
    
    /**
     * Carga un archivo de propiedades desde el directorio de configuración
     */
    private static Properties cargarPropiedades(String nombreArchivo) {
        Properties props = new Properties();
        File configFile = new File(getDirectorioConfig(), nombreArchivo);
        
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
                System.out.println(">>> Configuración cargada desde: " + configFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                System.err.println(">>> ADVERTENCIA: No se encontró el archivo " + nombreArchivo);
            } catch (IOException e) {
                System.err.println(">>> ERROR al leer " + nombreArchivo + ": " + e.getMessage());
            }
        } else {
            System.err.println(">>> ADVERTENCIA: Archivo de configuración no encontrado: " + configFile.getAbsolutePath());
            System.err.println(">>> Se usarán valores por defecto.");
        }
        
        return props;
    }
    
    /**
     * Carga las propiedades del servidor (IP, puerto, contexto)
     */
    public static Properties cargarConfigServidor() {
        return cargarPropiedades(ARCHIVO_CONFIG_SERVIDOR);
    }
    
    /**
     * Carga las propiedades del cliente (URL del servidor central)
     */
    public static Properties cargarConfigCliente() {
        return cargarPropiedades(ARCHIVO_CONFIG_CLIENTE);
    }
    
    /**
     * Carga las propiedades de la base de datos
     */
    public static Properties cargarConfigDatabase() {
        return cargarPropiedades(ARCHIVO_CONFIG_DB);
    }
    
    /**
     * Obtiene la ruta completa del archivo de configuración
     */
    public static String getRutaArchivoConfig(String nombreArchivo) {
        return new File(getDirectorioConfig(), nombreArchivo).getAbsolutePath();
    }
    
    /**
     * Verifica si existe un archivo de configuración
     */
    public static boolean existeArchivoConfig(String nombreArchivo) {
        File configFile = new File(getDirectorioConfig(), nombreArchivo);
        return configFile.exists();
    }
}

