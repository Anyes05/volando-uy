package dato.dao;

import dato.entidades.Aerolinea;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;

public class AerolineaDAO extends GenericDAO<Aerolinea> {

    public AerolineaDAO() {
        super(Aerolinea.class);
    }

    public Aerolinea buscarPorNickname(String nickname) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Aerolinea> query = em.createQuery(
                    "SELECT a FROM Aerolinea a WHERE a.nickname = :nickname", Aerolinea.class);
            query.setParameter("nickname", nickname);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }

    }
}

