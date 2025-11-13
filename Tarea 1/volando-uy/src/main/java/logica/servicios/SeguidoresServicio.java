package logica.servicios;

import dato.dao.GenericDAO;
import dato.entidades.Seguidores;
import dato.entidades.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SeguidoresServicio {
    private GenericDAO<Seguidores> seguidoresDAO;
    private GenericDAO<Usuario> usuarioDAO;

    public SeguidoresServicio() {
        this.seguidoresDAO = new GenericDAO<>(Seguidores.class);
        this.usuarioDAO = new GenericDAO<>(Usuario.class);
    }

    // Crear relación de seguimiento
    public void seguir(String nickSeguidor, String nickSeguido) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            Usuario seguidor = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nickSeguidor)
                    .getSingleResult();

            Usuario seguido = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nickSeguido)
                    .getSingleResult();

            // Validaciones
            if (seguidor.equals(seguido)) {
                throw new IllegalArgumentException("Un usuario no puede seguirse a sí mismo");
            }

            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Seguidores s WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido",
                            Long.class)
                    .setParameter("seguidor", nickSeguidor)
                    .setParameter("seguido", nickSeguido)
                    .getSingleResult();

            if (count > 0) {
                throw new IllegalArgumentException("Ya existe la relación de seguimiento");
            }

            // Persistir
            em.getTransaction().begin();
            Seguidores s = new Seguidores();
            s.setSeguidor(seguidor);
            s.setSeguido(seguido);
            em.persist(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar relación de seguimiento
    public void dejarDeSeguir(String nickSeguidor, String nickSeguido) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Seguidores relacion = em.createQuery(
                            "SELECT s FROM Seguidores s WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido",
                            Seguidores.class)
                    .setParameter("seguidor", nickSeguidor)
                    .setParameter("seguido", nickSeguido)
                    .getSingleResult();

            em.remove(relacion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Listar a quién sigue un usuario
    public List<String> listarSeguidos(String nickSeguidor) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s.seguido.nickname FROM Seguidores s WHERE s.seguidor.nickname = :nick",
                            String.class)
                    .setParameter("nick", nickSeguidor)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Listar quiénes siguen a un usuario
    public List<String> listarSeguidores(String nickSeguido) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s.seguidor.nickname FROM Seguidores s WHERE s.seguido.nickname = :nick",
                            String.class)
                    .setParameter("nick", nickSeguido)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Método para precargar relaciones de seguimiento
    public void precargarSeguidores() {
        try {
            // Array de relaciones de seguimiento: {seguidor, seguido}
            String[][] relacionesSeguimiento = {
                // Clientes siguen a aerolíneas
                {"jperez", "pluna"},
                {"jperez", "latam"},
                {"mgarcia", "aerolineas"},
                {"mgarcia", "latam"},
                {"rlopez", "gol"},
                {"acastro", "sky"},
                {"acastro", "latam"},
                {"fmartinez", "pluna"},
                {"srodriguez", "avianca"},
                {"dfernandez", "aerolineas"},
                {"lgonzalez", "pluna"},
                {"chernandez", "gol"},
                {"mruiz", "sky"},
                
                // Clientes se siguen entre sí (red social)
                {"jperez", "mgarcia"},
                {"mgarcia", "rlopez"},
                {"rlopez", "acastro"},
                {"acastro", "fmartinez"},
                {"fmartinez", "srodriguez"},
                {"srodriguez", "dfernandez"},
                {"dfernandez", "lgonzalez"},
                {"lgonzalez", "chernandez"},
                {"chernandez", "mruiz"},
                {"mruiz", "jperez"},
                
                // Algunos clientes siguen a otros clientes (relaciones bidireccionales)
                {"jperez", "acastro"},
                {"acastro", "jperez"},
                {"mgarcia", "srodriguez"},
                {"srodriguez", "mgarcia"},
                
                // Aerolíneas se siguen entre sí (competencia/colaboración)
                {"pluna", "latam"},
                {"latam", "aerolineas"},
                {"aerolineas", "gol"},
                {"gol", "sky"},
                {"sky", "avianca"},
                {"avianca", "copa"},
                {"copa", "iberia"},
                {"iberia", "airfrance"},
                {"airfrance", "american"},
                {"american", "delta"},
                {"delta", "united"},
                
                // Algunas aerolíneas siguen a otras (relaciones bidireccionales)
                {"latam", "pluna"},
                {"aerolineas", "latam"},
                
                // Clientes siguen a múltiples aerolíneas
                {"jperez", "aerolineas"},
                {"mgarcia", "pluna"},
                {"rlopez", "latam"},
                {"acastro", "aerolineas"},
                {"fmartinez", "latam"},
                {"srodriguez", "pluna"},
                {"dfernandez", "latam"},
                {"lgonzalez", "aerolineas"},
                {"chernandez", "latam"},
                {"mruiz", "pluna"}
            };

            int relacionesCreadas = 0;
            int relacionesExistentes = 0;
            int errores = 0;

            for (String[] relacion : relacionesSeguimiento) {
                String seguidor = relacion[0];
                String seguido = relacion[1];
                
                try {
                    // Verificar si la relación ya existe
                    EntityManager em = GenericDAO.emf.createEntityManager();
                    try {
                        Long count = em.createQuery(
                                        "SELECT COUNT(s) FROM Seguidores s WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido",
                                        Long.class)
                                .setParameter("seguidor", seguidor)
                                .setParameter("seguido", seguido)
                                .getSingleResult();
                        
                        if (count > 0) {
                            relacionesExistentes++;
                            continue; // Ya existe, saltar
                        }
                    } finally {
                        em.close();
                    }
                    
                    // Crear la relación
                    seguir(seguidor, seguido);
                    relacionesCreadas++;
                    System.out.println("Relación de seguimiento creada: " + seguidor + " -> " + seguido);
                } catch (IllegalArgumentException e) {
                    // Ya existe o no puede seguirse a sí mismo, ignorar
                    if (e.getMessage().contains("Ya existe")) {
                        relacionesExistentes++;
                    } else {
                        errores++;
                        System.err.println("Error al crear relación " + seguidor + " -> " + seguido + ": " + e.getMessage());
                    }
                } catch (Exception e) {
                    errores++;
                    System.err.println("Error al crear relación " + seguidor + " -> " + seguido + ": " + e.getMessage());
                }
            }

            System.out.println("Precarga de seguidores completada:");
            System.out.println("  - Relaciones creadas: " + relacionesCreadas);
            System.out.println("  - Relaciones ya existentes: " + relacionesExistentes);
            System.out.println("  - Errores: " + errores);
            
        } catch (Exception e) {
            throw new RuntimeException("Error en la precarga de seguidores: " + e.getMessage(), e);
        }
    }
}