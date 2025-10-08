package dato.entidades;

import jakarta.persistence.*;
import dato.entidades.RutaVuelo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aerolineas")
public class Aerolinea extends Usuario {
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = true)
    private String linkSitioWeb;

    // Mapear las rutas con JPA (many to many con rutaVuelo)

    @ManyToMany(mappedBy = "aerolineas", fetch = FetchType.EAGER) // aquí va el nombre de la propiedad en RutaVuelo
    private List<RutaVuelo> rutasVuelo = new ArrayList<>();


    public Aerolinea() {
    }

    public Aerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb, byte[] foto) {
        super(nickname, nombre, correo, foto);
        this.descripcion = descripcion;
        this.linkSitioWeb = linkSitioWeb;
    }

    // getters y setters

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLinkSitioWeb() {
        return linkSitioWeb;
    }

    public void setLinkSitioWeb(String linkSitioWeb) {
        this.linkSitioWeb = linkSitioWeb;
    }

    public List<RutaVuelo> getRutasVuelo() {
        return rutasVuelo;
    }
}
