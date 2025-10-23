package logica.DataTypes;

public enum EstadoRutaVuelo {
    INGRESADA("Ingresada"),
    CONFIRMADA("Confirmada"),
    RECHAZADA("Rechazada");

    private final String descripcion;

    EstadoRutaVuelo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
