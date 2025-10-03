package dato.dao;

import dato.entidades.Cliente;
import jakarta.persistence.TypedQuery;

import java.util.List;

import jakarta.persistence.EntityManager;

public class ClienteDAO extends GenericDAO<Cliente> {

    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente buscarPorNickname(String nickname) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.nickname = :nickname", Cliente.class);
            query.setParameter("nickname", nickname); // debe coincidir con el nombre en la query
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }


    }

}

