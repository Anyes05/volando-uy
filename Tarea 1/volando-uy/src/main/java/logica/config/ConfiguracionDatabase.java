package logica.config;

import java.util.Properties;

/**
 * Clase utilitaria para leer la configuración de la base de datos desde archivos .properties
 * ubicados en ~/volandouy/database.properties
 */
public class ConfiguracionDatabase {
    
    private static Properties propiedades;
    
    static {
        propiedades = ConfiguracionExterna.cargarConfigDatabase();
        
        // Si no hay configuración externa, usar valores por defecto
        if (propiedades.isEmpty()) {
            propiedades.setProperty("db.driver", "org.postgresql.Driver");
            propiedades.setProperty("db.url", "jdbc:postgresql://localhost:5432/volandoUy");
            propiedades.setProperty("db.user", "postgres");
            propiedades.setProperty("db.password", "0000");
            propiedades.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            propiedades.setProperty("hibernate.hbm2ddl.auto", "update");
            propiedades.setProperty("hibernate.show_sql", "true");
            propiedades.setProperty("hibernate.format_sql", "true");
            propiedades.setProperty("hibernate.connection.autocommit", "false");
            propiedades.setProperty("hibernate.jdbc.batch_size", "20");
            propiedades.setProperty("hibernate.order_inserts", "true");
            propiedades.setProperty("hibernate.order_updates", "true");
            propiedades.setProperty("hibernate.jdbc.batch_versioned_data", "true");
            propiedades.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
        }
    }
    
    /**
     * Obtiene las propiedades de configuración de la base de datos
     */
    public static Properties getPropiedades() {
        return new Properties(propiedades);
    }
    
    /**
     * Obtiene el driver de la base de datos
     */
    public static String getDriver() {
        return propiedades.getProperty("db.driver", "org.postgresql.Driver");
    }
    
    /**
     * Obtiene la URL de conexión a la base de datos
     */
    public static String getURL() {
        return propiedades.getProperty("db.url", "jdbc:postgresql://localhost:5432/volandoUy");
    }
    
    /**
     * Obtiene el usuario de la base de datos
     */
    public static String getUser() {
        return propiedades.getProperty("db.user", "postgres");
    }
    
    /**
     * Obtiene la contraseña de la base de datos
     */
    public static String getPassword() {
        return propiedades.getProperty("db.password", "0000");
    }
    
    /**
     * Obtiene una propiedad específica de Hibernate
     */
    public static String getHibernateProperty(String key, String defaultValue) {
        return propiedades.getProperty("hibernate." + key, defaultValue);
    }
}

