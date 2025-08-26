package logica.clase;

import logica.DataTypes.*;

import java.util.List;

public interface ISistema {

    // ALTA USUARIO
    public void altaCliente(String nickname, String nombre, String correo, String apellido, DTFecha fechaNac, String nacionalidad,TipoDoc tipoDocumento,String numeroDocumento);
    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb);

//    // CONSULTA DATOS DE USUARIO
//    public List<DTUsuario> consultarUsuarios();
//    public DTUsuario mostrarDatosUsuario(String nickname);
//
//    // MODIFICAR DATOS DE USUARIO
//    public DTUsuario mostrarDatosUsuarioMod(String nickname);
//    public void seleccionarUsuarioAMod (String nickname);
//    public void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento);
//    public void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb);
//
//    //ALTA VUELO
//    public List<DTAerolinea> listarAerolineas();
//    public List<DTRutaVuelo> seleccionarAerolineaRet(String nickname);
//    public DTRutaVuelo seleccionarRutaVueloRet(String nombreRutaVuelo);
//    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo, DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta, DTRutaVuelo ruta);
//    public void darAltaVuelo();
//    public void agregarCiudadParaTest(Ciudad ciudad);
//
//    //CONSULTA VUELO
//    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo);
//    public List<DTVueloReserva> seleccionarVuelo(String nombre);
//
//    // PAQUETES DE VUELO
//    public List<DTPaqueteVuelos> mostrarPaquete();
//    public void seleccionarPaquete(String nombrePaquete);
//    public List<DTCliente> mostrarClientes();
//    public void seleccionarCliente(String nombreCliente);
//    public void realizarCompra(DTFecha fechaCompra, int costo, DTFecha vencimiento, TipoAsiento tipoAsiento);
    //public void agregarRutaAPaquete(RutaVuelo ruta, int cant);
//    public void crearPaquete(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta);
//
//    public void seleccionarAerolinea(String nickname);
//
//    public List<DTRutaVuelo> listarRutaVuelo(String nombreAerolinea);
//    // ALTA CATEGORIA
//    public void altaCategoria(String nombre);
//    // ALTA CIUDAD
//    public void altaCiudad(String nombre, String pais, Aeropuerto aeropuerto, DTFecha fechaAlta);
//
//    // RESERVA VUELO
//    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva);
//    public DTRutaVuelo ingresarDatosRuta(String nombreRuta, String descripcion, DTHora hora, float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOigen, String ciudadDestino, DTFecha fechaAlta, String categoria);
//    // CONSULTA PAQUETE
//    public void registrarRuta();
//    public DTPaqueteVueloRutaVuelo imprimirPaqueteVuelo(String nombre);
//


}


