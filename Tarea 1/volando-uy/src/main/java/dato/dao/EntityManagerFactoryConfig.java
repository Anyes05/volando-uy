package dato.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.config.ConfiguracionDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Clase utilitaria para configurar el EntityManagerFactory
 * usando propiedades externas desde ~/volandouy/database.properties
 */
public class EntityManagerFactoryConfig {
    
    private static EntityManagerFactory emf = null;
    
    /**
     * Obtiene o crea el EntityManagerFactory configurado con propiedades externas
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (EntityManagerFactoryConfig.class) {
                if (emf == null) {
                    emf = createEntityManagerFactory();
                }
            }
        }
        return emf;
    }
    
    /**
     * Crea el EntityManagerFactory con configuración externa
     */
    private static EntityManagerFactory createEntityManagerFactory() {
        Properties dbProps = ConfiguracionDatabase.getPropiedades();
        
        // Crear mapa de propiedades para JPA
        Map<String, String> properties = new HashMap<>();
        
        // Propiedades de conexión JDBC
        properties.put("jakarta.persistence.jdbc.driver", dbProps.getProperty("db.driver", "org.postgresql.Driver"));
        properties.put("jakarta.persistence.jdbc.url", dbProps.getProperty("db.url", "jdbc:postgresql://localhost:5432/volandoUy"));
        properties.put("jakarta.persistence.jdbc.user", dbProps.getProperty("db.user", "postgres"));
        properties.put("jakarta.persistence.jdbc.password", dbProps.getProperty("db.password", "0000"));
        
        // Propiedades de Hibernate
        properties.put("hibernate.dialect", dbProps.getProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"));
        properties.put("hibernate.hbm2ddl.auto", dbProps.getProperty("hibernate.hbm2ddl.auto", "update"));
        properties.put("hibernate.show_sql", dbProps.getProperty("hibernate.show_sql", "true"));
        properties.put("hibernate.format_sql", dbProps.getProperty("hibernate.format_sql", "true"));
        properties.put("hibernate.connection.autocommit", dbProps.getProperty("hibernate.connection.autocommit", "false"));
        properties.put("hibernate.jdbc.batch_size", dbProps.getProperty("hibernate.jdbc.batch_size", "20"));
        properties.put("hibernate.order_inserts", dbProps.getProperty("hibernate.order_inserts", "true"));
        properties.put("hibernate.order_updates", dbProps.getProperty("hibernate.order_updates", "true"));
        properties.put("hibernate.jdbc.batch_versioned_data", dbProps.getProperty("hibernate.jdbc.batch_versioned_data", "true"));
        properties.put("hibernate.jdbc.lob.non_contextual_creation", dbProps.getProperty("hibernate.jdbc.lob.non_contextual_creation", "true"));
        
        // Crear EntityManagerFactory con las propiedades
        return Persistence.createEntityManagerFactory("volandouyPU", properties);
    }
    
    /**
     * Cierra el EntityManagerFactory
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }
}

