package logica.clase;

import logica.DataTypes.*;

import java.util.ArrayList;
import java.util.List;

public class EstacionTrabajo implements IEstacionTrabajo{

    private static EstacionTrabajo Instance = null;
    private List<PaqueteVuelo> paqueteVuelos;
    private List<Usuario> usuarios;
    private List<Ciudad> ciudades;

    private EstacionTrabajo(){
        paqueteVuelos = new ArrayList<>();
        usuarios = new ArrayList<>();
        ciudades = new ArrayList<>();
    }
    public static EstacionTrabajo getInstance(){
        if(Instance == null){
            Instance = new EstacionTrabajo();
        }
        return Instance;
    }

    // ALTA USUARIO -- en proceso
    // Auxiliares
    private boolean existeNickname(String nickname){
        for (Usuario u : usuarios) {
            if (u.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
    private boolean existeCorreo(String correo){
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    public void altaCliente(String nickname, String nombre, String correo, String apellido, DTFecha fechaNac, String nacionalidad,TipoDoc tipoDocumento,String numeroDocumento){
        if (existeNickname(nickname)) {
            throw new IllegalArgumentException("El nickname ya existe."); // No se si es necesario o se tiene en cuenta en la parte de diseño
        }
        if (existeCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico ya existe.");
        }
        Cliente c = new Cliente(nickname, nombre, correo, apellido, fechaNac, nacionalidad, tipoDocumento, numeroDocumento);
        usuarios.add(c);
    }
    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb){
        if (existeNickname(nickname)) {
            throw new IllegalArgumentException("El nickname ya existe.");
        }
        if (existeCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico ya existe.");
        }
        Aerolinea a = new Aerolinea(nickname, nombre, correo, descripcion, linkSitioWeb);
        usuarios.add(a);
    }

    // CONSULTA DE USUARIO
    public List<DTUsuario> consultarUsuarios(){
        return null;
    }
    public DTDatoUsuario mostrarDatosUsuario(String nickname){
        return null;
    }

    // MODIFICAR DATOS DE USUARIO


    public List<DTPaqueteVuelos> mostrarPaquete(){
        return null;
    }
    public void seleccionarPaquete(String nombrePaquete){
    }
    public List<DTCliente> mostrarClientes(){
        return null;
    }
    public void seleccionarCliente(String nombreCliente){

    }
    public void relizarCompra(DTFecha fechaCompra, int costo, DTFecha vencimineto){

    }

    public void crearPaquete(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta){

    }
    public List<DTAerolinea> listarAerolineas(){
        return null;
    }
    public void seleccionarAerolinea(String nickname){

    }
    public List<DTRutaVuelo> listarRutaVuelo(){
        return null;
    }
    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo){
        return null;
    }
    public List<DTVueloReserva> seleccionarVuelo(String nombre){
        return null;
    }
    public boolean validarNombre(String nombre){
        return false;
    }
    public DTRutaVuelo ingresarDatosRuta(String nombreRuta, String descripcion, DTHora hora, float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOigen, String ciudadDestino, DTFecha fechaAlta, String categoria){
        return null;
    }
    public DTRutaVuelo registrarRuta(){
return null;
    }
    public DTPaqueteVueloRutaVuelo imprimirPaqueteVuelo(String nombre){
        return null;
    }
    public List<DTRutaVuelo> darAltaVuelo(){
        return null;
    }
    public void seleccionarCliente(){

    }
    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva){

    }
    public void AltaReservaVuelo(int costo){

    }
}
