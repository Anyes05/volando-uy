package logica.clase;

import logica.DataTypes.*;

import java.util.ArrayList;
import java.util.List;

public class EstacionTrabajo extends IEstacionTrabajo{

    static EstacionTrabajo Instance = null;
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
    }
    public List<DTUsuario> consultarUsuarios(){
        return null;
    }
    public DTDatoUsuario mostrarDatosUsuario(String nickname){
        return null;
    }
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
    public void altaCliente(String nickname, String nombre, String apellido,String correo, DTFecha fechaNac, String nacionalidad,tipoDoc tipoDocumento,String numeroDocumento){

    }
    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb){

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
return null
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
