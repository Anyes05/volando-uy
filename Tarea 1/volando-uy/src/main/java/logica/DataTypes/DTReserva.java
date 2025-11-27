package logica.DataTypes;

public class DTReserva {
    private DTFecha fechaReserva;
    private DTCostoBase costoReserva;
    private Long id;
    private String nickname;
    private boolean checkInRealizado;

    public DTReserva(DTFecha fechaReserva, DTCostoBase costoReserva) {
        this.fechaReserva = fechaReserva;
        this.costoReserva = costoReserva;
        this.id = null;
        this.nickname = null;
        this.checkInRealizado = false;
    }

    //constructor vacio
    public DTReserva() {

    }


    public DTFecha getFechaReserva() {
        return this.fechaReserva;
    }

    public DTCostoBase getCostoReserva() {
        return this.costoReserva;
    }

    public void setFechaReserva(DTFecha fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setCostoReserva(DTCostoBase costoReserva) {
        this.costoReserva = costoReserva;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTipo(String compraComun) {
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
    }

    public void setEquipajeExtra(int equipajeExtra) {
    }

    public void setVueloNombre(String nombre) {
    }

    public void setCostoTotal(float costoTotal) {
    }

    public void setVencimiento(DTFecha vencimiento) {
    }

    public void setPaqueteNombre(String nombre) {
    }
    public boolean isCheckInRealizado() {
        return checkInRealizado;
    }

    public void setCheckInRealizado(boolean checkInRealizado) {
        this.checkInRealizado = checkInRealizado;
    }
}
