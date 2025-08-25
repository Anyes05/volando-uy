package dato.dao;

import dato.entidades.CompraComun;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CompraComunDAO extends GenericDAO<CompraComun> {

    public CompraComunDAO() {
        super(CompraComun.class);
    }

    // Algunos atributos por los que se puede buscar.
    public CompraComun buscarPorId(Long id) {
        return em.find(CompraComun.class, id);
    }

    public List<CompraComun> buscarPorTipoAsiento(String tipoAsiento) {
        TypedQuery<CompraComun> query = em.createQuery(
                "SELECT cc FROM CompraComun cc WHERE cc.tipoAsiento = :tipoAsiento", CompraComun.class);
        query.setParameter("tipoAsiento", tipoAsiento);
        return query.getResultList();
    }
}
