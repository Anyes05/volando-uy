package logica.clase;

import dato.entidades.*;
import logica.DataTypes.*;

import java.util.List;

public interface ISistema {

    // PRECARGA DE AEROPUERTOS
    public void precargarAeropuertos();

    // PRECARGA DE CATEGORÍAS
    public void precargarCategorias();

    // PRECARGA DE USUARIOS (CLIENTES Y AEROLÍNEAS)
    public void precargarUsuarios();

    // PRECARGA DE RUTAS DE VUELO
    public void precargarRutasVuelo();

    // PRECARGA DE VUELOS
    public void precargarVuelos();

    // PRECARGA COMPLETA DEL SISTEMA
    public void precargarSistemaCompleto();

    // ALTA USUARIO
    public void altaCliente(String nickname, String nombre, String correo, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento);

    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb);

    // ALTA CATEGORIA
    public void altaCategoria(String nombre);

    public List<dato.entidades.Categoria> getCategorias();

    // ALTA CIUDAD
    public void altaCiudad(String nombre, String pais, String aeropuerto, String descripcion, String sitioWeb, DTFecha fechaAlta);

    public List<String> listarAeropuertos();

    //ALTA RUTA VUELO
    public List<DTAerolinea> listarAerolineas();

    public void seleccionarAerolinea(String nickname);

    public void ingresarDatosRuta(String nombreRuta, String descripcion, /*DTHora hora,*/ float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOigen, String ciudadDestino, DTFecha fechaAlta, List<String> categorias);

    public void registrarRuta();

    public List<DTCiudad> listarCiudades();

    public List<DTCiudad> listarCiudadesDestino(List<DTCiudad> ciudades, String ciudadOrigen);

    // CONSULTA DATOS DE USUARIO
    public List<DTUsuario> consultarUsuarios();

    public DTUsuario mostrarDatosUsuario(String nickname);

    // MODIFICAR DATOS DE USUARIO
    public DTUsuario mostrarDatosUsuarioMod(String nickname);

    public void seleccionarUsuarioAMod(String nickname);

    public void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento);

    public void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb);

    //ALTA VUELO
    public List<DTRutaVuelo> seleccionarAerolineaRet(String nickname);

    public DTRutaVuelo seleccionarRutaVueloRet(String nombreRutaVuelo);

    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo, DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta, DTRutaVuelo ruta);

    public void darAltaVuelo();

    public List<DTRutaVuelo> listarRutaVuelo(String nombreAerolinea);

    //CONSULTA VUELO
    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo);

    public List<DTRutaVuelo> listarRutaVueloDeVuelo();

    public List<DTVueloReserva> listarReservasVuelo(String nombre);

    // PAQUETES DE VUELO
    public void crearPaquete(String nombrePaquete, String descripcion, TipoAsiento tipoAsiento, int diasValidos, float descuento, DTFecha fechaAlta);

    //AGREGAR RUTA DE VUELO A PAQUETE
    public List<DTPaqueteVuelos> mostrarPaquete();

    public void seleccionarPaquete(String nombrePaquete);

    public void seleccionarRutaVueloPaquete(String nombreRutaVuelo);

    public void seleccionarAerolineaPaquete(DTAerolinea DTaerolinea);

    public void agregarRutaAPaquete(int cant, TipoAsiento tipoAsiento);

    public List<DTPaqueteVuelos> obtenerPaquetesNoComprados();


    // COMPRA DE PAQUETE DE VUELO
    public List<DTPaqueteVuelos> mostrarPaqueteConRutas();

    public List<DTCliente> mostrarClientes();

    public void seleccionarCliente(String nombreCliente);

    public void realizarCompra(DTFecha fechaCompra, float costo, DTFecha vencimiento/*, TipoAsiento tipoAsiento*/);

    public boolean clienteYaComproPaquete();

    // CREAR PAQUETE DE VUELO
    // public void crearPaquete(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta);

    // RESERVA VUELO
    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva);

    public void seleccionarVueloParaReserva(String nombreVuelo);

    public List<DTCliente> listarClientes();

    //public List<DTCliente> mostrarClientesSinVueloSeleccionado();
    public List<String> nombresPasajes(String nombre, List<String> nombresPasajeros);

    public List<DTPasajero> pasajeros(String nombreCliente);

    // ADMINISTRACIÓN DE CONFLICTOS DE RESERVA
    public void manejarConflictoReserva(String opcionSeleccionada, TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva);

    // CONSULTA PAQUETE RUTAS DE VUELO
    public DTPaqueteVuelos consultaPaqueteVuelo();

    public String consultaPaqueteVueloRutasCantidadTipo();

    public List<String> listarAerolineasRutaVuelo();

    public List<DTRutaVuelo> consultaPaqueteVueloRutas();

    public void seleccionarRVPaquete(String nombreRuta);

}


