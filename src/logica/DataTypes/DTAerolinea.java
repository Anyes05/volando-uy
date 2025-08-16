package DataTypes;

public class DTAerolinea  extends DTUsuario{
    private String descripcion;
    private String linkSitioWeb;

    public DTAerolinea(String nickname,String nombre, String correo ,String descripcion, String linksitioweb){
        super(nickname,nombre,correo);
        this.descripcion=descripcion;
        this.linkSitioWeb=linksitioweb;
    }

    // Getters
    public String getDescripcion() {
        return this.descripcion;
    }

    public String getLinkSitioWeb() {
        return this.linkSitioWeb;
    }

    // Setters
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLinkSitioWeb(String linkSitioWeb) {
        this.linkSitioWeb = linkSitioWeb;
    }

}
