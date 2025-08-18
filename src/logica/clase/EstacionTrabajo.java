package logica.clase;

import logica.DataTypes.*;

import java.util.ArrayList;
import java.util.List;

public class EstacionTrabajo implements IEstacionTrabajo{

    private static EstacionTrabajo Instance = null;
    private List<PaqueteVuelo> paqueteVuelos;
    private List<Usuario> usuarios;
    private List<Ciudad> ciudades;
    private String recuerdaAerolinea; // Para recordar la aerolinea seleccionada
    private DTRutaVuelo recorderRutaVuelo; // Para recordar la ruta de vuelo seleccionada

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


    // ALTA RUTA VUELO
    public List<DTAerolinea> listarAerolineas(){
        List<DTAerolinea> listaAerolineas = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Aerolinea a) {
                listaAerolineas.add(new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb()));
            }
        }
        return listaAerolineas;
    }
    public void seleccionarAerolinea(String nickname) {
        nickname = nickname.toLowerCase();
        for (Usuario u : usuarios) {    // Recorremos la lista de usuarios
            // Verificamos si el usuario es una Aerolinea y si su nickname coincide con el proporcionado
            if (u instanceof Aerolinea a && a.getNickname().toLowerCase().equals(nickname)) {
                recuerdaAerolinea = a.getNickname();
                return;
            }
        }
    }

    public DTRutaVuelo ingresarDatosRuta(String nombreRuta, String descripcion, DTHora hora, float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOrigen, String ciudadDestino, DTFecha fechaAlta, String categoria) {
        // Verificar si la aerolinea ha sido seleccionada
        if (recuerdaAerolinea == null) {
            throw new IllegalStateException("Debe seleccionar una aerolinea antes de ingresar los datos de la ruta.");
        }
        // Buscar la aerolinea por su nickname
        Aerolinea aerolineaSeleccionada = null;
        for (Usuario u : usuarios) {
            if (u instanceof Aerolinea a && a.getNickname().equals(recuerdaAerolinea)) {
                aerolineaSeleccionada = a;
                break;
            }
        }
        if (aerolineaSeleccionada == null) {
            throw new IllegalStateException("La aerolinea seleccionada no existe.");
        }
        //Crear DTAerolinea
        DTAerolinea aerolineaDT = new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb());
        // Crear la hora del vuelo
        DTHora horaVuelo = new DTHora(hora.getHora(), hora.getMinutos());
        // Crear la fecha de alta
        DTFecha fechaAltaDT = new DTFecha(fechaAlta.getDia(), fechaAlta.getMes(), fechaAlta.getAno());
        // Crear el costo base
        CostoBase costoBase = new CostoBase(costoTurista, costoEjecutivo, costoEquipajeExtra);
        // Crear los DTCiudad para origen y destino si existen
        DTCiudad ciudadOrigenDT = null;
        DTCiudad ciudadDestinoDT = null;
        for (Ciudad ciudad : ciudades) {
            if (ciudad.getNombre().equalsIgnoreCase(ciudadOrigen)) {
                ciudadOrigenDT = new DTCiudad(ciudad.getNombre(), ciudad.getPais());
            }
            if (ciudad.getNombre().equalsIgnoreCase(ciudadDestino)) {
                ciudadDestinoDT = new DTCiudad(ciudad.getNombre(), ciudad.getPais());
            }
        }
        if (ciudadOrigenDT == null || ciudadDestinoDT == null) {
            throw new IllegalArgumentException("Una de las ciudades no existe.");
        }
        // Crear la ruta de vuelo
        DTRutaVuelo DTrutaVuelo = new DTRutaVuelo(horaVuelo, nombreRuta, descripcion, fechaAltaDT, costoBase, aerolineaDT, ciudadOrigenDT, ciudadDestinoDT);
        // Retornar el DTRutaVuelo creado
        recorderRutaVuelo = DTrutaVuelo; // Guardar la ruta de vuelo seleccionada
        return DTrutaVuelo;
    }
    public void registrarRuta() {
        {
            // Verificar si la aerolinea ha sido seleccionada
            if (recuerdaAerolinea == null) {
                throw new IllegalStateException("Debe seleccionar una aerolinea antes de registrar la ruta.");
            }
            // Buscar la aerolinea por su nickname
            Aerolinea aerolineaSeleccionada = null;
            for (Usuario u : usuarios) {
                if (u instanceof Aerolinea a && a.getNickname().equals(recuerdaAerolinea)) {
                    aerolineaSeleccionada = a;
                    break;
                }
            }
            if (aerolineaSeleccionada == null) {
                throw new IllegalStateException("La aerolinea seleccionada no existe.");
            }
            // Crear la ruta de vuelo
            if (recorderRutaVuelo == null) {
                throw new IllegalStateException("Debe ingresar los datos de la ruta antes de registrarla.");
            }
            RutaVuelo nuevaRuta = new RutaVuelo(
                    recorderRutaVuelo.getNombre(),
                    recorderRutaVuelo.getDescripcion(),
                    recorderRutaVuelo.getCostoBase()
            );
            nuevaRuta.setFechaAlta(recorderRutaVuelo.getFechaAlta());
            // Buscar las ciudades de origen y destino y asignarlas a la nueva ruta
            Ciudad ciudadOrigen = null;
            Ciudad ciudadDestino = null;
            for (Ciudad ciudad : ciudades) {
                if (ciudad.getNombre().equalsIgnoreCase(recorderRutaVuelo.getCiudadOrigen().getNombre())) {
                    ciudadOrigen = ciudad;
                }
                if (ciudad.getNombre().equalsIgnoreCase(recorderRutaVuelo.getCiudadDestino().getNombre())) {
                    ciudadDestino = ciudad;
                }
            }
            if (ciudadOrigen == null || ciudadDestino == null) {
                throw new IllegalArgumentException("Una de las ciudades no existe.");
            }
            nuevaRuta.setCiudadOrigen(ciudadOrigen);
            nuevaRuta.setCiudadDestino(ciudadDestino);
            // Agregar la nueva ruta a la lista de rutas de la aerolinea
            aerolineaSeleccionada.getRutasVuelo().add(nuevaRuta);
            // Limpiar las variables de selección
            recuerdaAerolinea = null;
            recorderRutaVuelo = null;
        }
    }

    // CONSULTA RUTA VUELO
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

    public List<DTRutaVuelo> listarRutaVuelo(){
        return null;
    }
    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo){
        return null;
    }
    public List<DTVueloReserva> seleccionarVuelo(String nombre){
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
