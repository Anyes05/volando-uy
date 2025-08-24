package dato.dao;

import dato.entidades.Cliente;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteDAO extends GenericDAO<Cliente> {

    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente buscarPorNickname(String nickname) {
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.nickname = :nickname", Cliente.class);
        query.setParameter("doc", nickname);
        return query.getResultStream().findFirst().orElse(null);
    }

}

