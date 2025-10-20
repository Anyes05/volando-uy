package logica.servicios;

import dato.dao.UsoPaqueteDAO;
import dato.entidades.CompraPaquete;
import dato.entidades.UsoPaquete;
import dato.entidades.Vuelo;
import dato.entidades.Cantidad;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

import java.util.List;

public class UsoPaqueteServicio {
    private UsoPaqueteDAO usoPaqueteDAO;

    public UsoPaqueteServicio() {
        this.usoPaqueteDAO = new UsoPaqueteDAO();
    }

    public void registrarUso(CompraPaquete compraPaquete, Vuelo vuelo, Cantidad cantidad, int cantidadUsada, TipoAsiento tipoAsiento, DTFecha fechaUso) {
        UsoPaquete usoPaquete = new UsoPaquete(compraPaquete, vuelo, cantidad, cantidadUsada, tipoAsiento, fechaUso);
        usoPaqueteDAO.registrarUso(usoPaquete);
    }

    public List<UsoPaquete> buscarPorCompraPaquete(CompraPaquete compraPaquete) {
        return usoPaqueteDAO.buscarPorCompraPaquete(compraPaquete);
    }
}
