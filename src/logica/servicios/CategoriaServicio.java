package logica.servicios;

import dato.dao.CategoriaDAO;
import dato.entidades.Categoria;

public class CategoriaServicio {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void registrarCategoria(String nombre) {
        Categoria c = new Categoria(nombre);
        categoriaDAO.guardar(c);  // Se guarda en la BD
    }

    public Categoria obtenerCategoria(Long id) {
        return categoriaDAO.buscarPorId(id);
    }

    public Categoria buscarCategoriaPorNombre(String nombre) {
        return categoriaDAO.buscarCategoriaPorNombre(nombre);
    }
}
