package logica.servicios;

import dato.dao.GenericDAO;
import dato.entidades.Reserva;

public class ReservaServicio {
    private GenericDAO<Reserva> reservaDAO;

    public ReservaServicio() {
        this.reservaDAO = new GenericDAO<>(Reserva.class);
    }

    public Reserva buscarPorId(Long id) {
        return reservaDAO.buscarPorId(id);
    }

    public void crearReserva(Reserva reserva) throws Exception {
        // Aquí puedes agregar validaciones específicas para la reserva
        reservaDAO.crear(reserva);
    }

    public void actualizarReserva(Reserva reserva) {
        reservaDAO.actualizar(reserva);
    }

    public void eliminarReserva(Long id) {
        reservaDAO.eliminar(id);
    }
}
