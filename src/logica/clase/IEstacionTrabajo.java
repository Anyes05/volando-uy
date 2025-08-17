package logica.clase;

import logica.DataTypes.*;

import java.util.List;

public interface IEstacionTrabajo {

    public List<DTUsuario> consultarUsuarios();
    public DTDatoUsuario mostrarDatosUsuario(String nickname);
    public List<DTPaqueteVuelos> mostrarPaquete();
    public void seleccionarPaquete(String nombrePaquete);
    public List<DTCliente> mostrarClientes();
    public void seleccionarCliente(String nombreCliente);
    public void relizarCompra(DTFecha fechaCompra, int costo, DTFecha vencimineto);
    public void altaCliente(String nickname, String nombre, String apellido,String correo, DTFecha fechaNac, String nacionalidad,tipoDoc tipoDocumento,String numeroDocumento);
    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb);
    public void crearPaquete(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta);
    public List<DTAerolinea> listarAerolineas();
    public void seleccionarAerolinea(String nickname);
    public List<DTRutaVuelo> listarRutaVuelo();
    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo);
    public List<DTVueloReserva> seleccionarVuelo(String nombre);
    public boolean validarNombre(String nombre);
    public DTRutaVuelo ingresarDatosRuta(String nombreRuta, String descripcion, DTHora hora, float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOigen, String ciudadDestino, DTFecha fechaAlta, String categoria);
    public DTRutaVuelo registrarRuta();
    public DTPaqueteVueloRutaVuelo imprimirPaqueteVuelo(String nombre);
    public List<DTRutaVuelo> darAltaVuelo();
    public void seleccionarCliente();
    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva);
    public void AltaReservaVuelo(int costo);

}


