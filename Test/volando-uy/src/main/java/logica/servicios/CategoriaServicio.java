package logica.servicios;

import dato.dao.CategoriaDAO;
import dato.entidades.Categoria;

import java.util.List;
import java.util.ArrayList;

public class CategoriaServicio {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void registrarCategoria(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        if (categoriaDAO.buscarPorNombre(nombre) != null) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        Categoria c = new Categoria(nombre);
        categoriaDAO.guardar(c);  // Se guarda en la BD
    }

    public Categoria obtenerCategoria(Long id) {
        return categoriaDAO.buscarPorId(id);
    }

    public Categoria obtenerCategoriaPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        return categoriaDAO.buscarPorNombre(nombre);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listarCategorias();
    }

    // Método para precargar categorías
    public void precargarCategorias() {
        // Lista de categorías comunes para vuelos
        String[] categoriasData = {
                "Nacional",
                "Regional",
                "Internacional",
                "Doméstico",
                "Charter",
                "Carga",
                "Ejecutivo",
                "Turismo",
                "Negocios",
                "Vacaciones"
        };

        for (String nombreCategoria : categoriasData) {
            // Verificar si la categoría ya existe
            if (categoriaDAO.buscarPorNombre(nombreCategoria) == null) {
                Categoria categoria = new Categoria(nombreCategoria);
                categoriaDAO.guardar(categoria);
                System.out.println("Categoría precargada: " + nombreCategoria);
            }
        }


    }
}
