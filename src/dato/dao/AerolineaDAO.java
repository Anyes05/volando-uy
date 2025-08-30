package dato.dao;

import dato.entidades.Aerolinea;
import jakarta.persistence.TypedQuery;

public class AerolineaDAO extends GenericDAO<Aerolinea> {

    public AerolineaDAO() {
        super(Aerolinea.class);
    }

    public Aerolinea buscarPorNickname(String nickname) {
        TypedQuery<Aerolinea> query = em.createQuery(
                "SELECT a FROM Aerolinea a WHERE a.nickname = :nickname", Aerolinea.class);
        query.setParameter("nickname", nickname);
        return query.getResultStream().findFirst().orElse(null);
    }
}

