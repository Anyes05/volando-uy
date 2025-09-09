package logica.clase;


import dato.entidades.Aeropuerto;
import dato.entidades.Cantidad;
import dato.entidades.Cliente;
import dato.entidades.CompraPaquete;
import dato.entidades.Pasaje;
import dato.entidades.Reserva;
import dato.entidades.RutaVuelo;
import dato.entidades.Usuario;
import logica.DataTypes.*;
import dato.entidades.Aerolinea;
import logica.servicios.UsuarioServicio;
import logica.servicios.ClienteServicio;
import logica.servicios.AerolineaServicio;
import logica.servicios.*;
import dato.entidades.*;
import logica.servicios.*;

import java.util.ArrayList;
import java.util.List;

public class Sistema implements ISistema {

    private static Sistema Instance = null;
    private List<PaqueteVuelo> paqueteVuelos;
    private List<Categoria> categorias = new ArrayList<>();
    private List<Usuario> usuarios;
    private List<Ciudad> ciudades;
    private List<Vuelo> vuelos;
    private String recuerdaAerolinea; // Para recordar la aerolinea seleccionada
    private dato.entidades.RutaVuelo recordarRutaVuelo; // Para recordar la ruta de vuelo seleccionada
    private Aerolinea aerolineaSeleccionada;
    private DTVuelo recordarDatosVuelo;
    private dato.entidades.Cliente clienteSeleccionado;
    private dato.entidades.PaqueteVuelo paqueteSeleccionado;
    private List<DTVuelo> listaDTVuelos;
    private String nicknameUsuarioAModificar;
    private String vueloSeleccionadoParaReserva; // Para recordar el vuelo seleccionado para reserva
    private RutaVuelo rutaVueloSeleccionada;

    private Sistema() {
        paqueteVuelos = new ArrayList<>();
        usuarios = new ArrayList<>();
        ciudades = new ArrayList<>();
        listaDTVuelos = new ArrayList<>();
        vuelos = new ArrayList<>();


    }

    public static Sistema getInstance() {
        if (Instance == null) {
            Instance = new Sistema();
        }
        return Instance;
    }

    public static boolean esNombreValido(String nombre) {
        if (nombre == null) return false;

        String limpio = nombre.trim();

        if (limpio.length() < 3) {
            return false;
        }

        // Letras Unicode + espacios internos + apóstrofos + guiones
        String patron = "^\\p{L}+(?:[ \\p{L}'-]+)*$";

        return limpio.matches(patron);
    }

    // ALTA USUARIO
    private boolean existeNickname(String nickname) {
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

        // Verificar si existe en clientes
        dato.entidades.Cliente cliente = clienteServicio.buscarClientePorNickname(nickname);
        if (cliente != null) {
            return true;
        }

        // Verificar si existe en aerolíneas
        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nickname);
        if (aerolinea != null) {
            return true;
        }

        return false;
    }

    private boolean existeCorreo(String correo) {
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

        // Buscar por correo en clientes
        List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();
        for (dato.entidades.Cliente c : clientes) {
            if (c.getCorreo().equals(correo)) {
                return true;
            }
        }

        // Buscar por correo en aerolíneas
        List<Aerolinea> aerolineas = aerolineaServicio.listarAerolineas();
        for (Aerolinea a : aerolineas) {
            if (a.getCorreo().equals(correo)) {
                return true;
            }
        }

        return false;
    }

    private boolean existeDocumento(String numeroDocumento) {
        ClienteServicio clienteServicio = new ClienteServicio();

        // Buscar por número de documento en clientes
        List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();
        for (dato.entidades.Cliente c : clientes) {
            if (c.getNumeroDocumento().equals(numeroDocumento)) {
                return true;
            }
        }

        return false;
    }

    public void altaCliente(String nickname, String nombre, String correo, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        if (existeNickname(nickname)) {
            throw new IllegalArgumentException("El nickname ya existe.");
        }
        if (existeCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico ya existe.");
        }
        if (existeDocumento(numeroDocumento)) {
            throw new IllegalArgumentException("El número de documento ya existe.");
        }
        if (!esNombreValido(nickname) || !esNombreValido(nombre) || !esNombreValido(apellido) || !esNombreValido(nacionalidad)) {
            throw new IllegalArgumentException("El nickname, nombre, apellido o nacionalidad son demasiado cortos o contienen caracteres especiales");
        }

        try {
            ClienteServicio clienteServicio = new ClienteServicio();
            clienteServicio.crearCliente(nickname, nombre, correo, apellido, fechaNac, nacionalidad, tipoDocumento, numeroDocumento);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear el cliente: " + e.getMessage());
        }
    }

    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) {
        if (existeNickname(nickname)) {
            throw new IllegalArgumentException("El nickname ya existe.");
        }
        if (existeCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico ya existe.");
        }
        if (!esNombreValido(nickname) || !esNombreValido(nombre) || !esNombreValido(correo) || !esNombreValido(descripcion)) {
            throw new IllegalArgumentException("Nickname, nombre, correo o descripcion han sido ingresados incorrectamente, muy cortos o usan caracteres especiales");
        }

        try {
            AerolineaServicio aerolineaServicio = new AerolineaServicio();
            aerolineaServicio.crearAerolinea(nickname, nombre, correo, descripcion, linkSitioWeb);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear la aerolínea: " + e.getMessage());
        }
    }

    // CONSULTA DE USUARIO
    public List<DTUsuario> consultarUsuarios() {
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

        List<DTUsuario> lista = new ArrayList<>();

        // Obtener clientes
        List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();
        for (dato.entidades.Cliente c : clientes) {
            lista.add(new DTUsuario(c.getNickname(), c.getNombre(), c.getCorreo()));
        }

        // Obtener aerolíneas
        List<Aerolinea> aerolineas = aerolineaServicio.listarAerolineas();
        for (Aerolinea a : aerolineas) {
            lista.add(new DTUsuario(a.getNickname(), a.getNombre(), a.getCorreo()));
        }

        if (lista.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados.");
        }

        return lista;
    }

    public DTUsuario mostrarDatosUsuario(String nickname) {
        for (Usuario u : usuarios) {
            if (u.getNickname().equals(nickname)) {
                if (u instanceof Cliente c) { // me falta también los paquetes que compró.
                    List<DTReserva> reservasDTO = new ArrayList<>();
                    for (Reserva r : c.getReservas()) {
                        if (r instanceof CompraPaquete cp) {
                            reservasDTO.add(new DTCompraPaquete(cp.getFechaReserva(), cp.getCostoReserva(), cp.getVencimiento()));
                        } else {
                            reservasDTO.add(new DTReserva(r.getFechaReserva(), r.getCostoReserva()));
                        }
                    }
                    return new DTCliente(
                            c.getNickname(),
                            c.getNombre(),
                            c.getCorreo(),
                            c.getApellido(),
                            c.getTipoDoc(),
                            c.getNumeroDocumento(),
                            c.getFechaNacimiento(),
                            c.getNacionalidad(),
                            reservasDTO
                    );
                } else if (u instanceof Aerolinea a) {
                    List<DTRutaVuelo> rutasDTO = new ArrayList<>();
                    for (dato.entidades.RutaVuelo rv : a.getRutasVuelo()) {
                        rutasDTO.add(new DTRutaVuelo(
                                rv.getNombre(),
                                rv.getDescripcion(),
                                rv.getFechaAlta(),
                                rv.getCostoBase(),
                                new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>()),
                                new DTCiudad(rv.getCiudadOrigen().getNombre(), rv.getCiudadOrigen().getPais()),
                                new DTCiudad(rv.getCiudadDestino().getNombre(), rv.getCiudadDestino().getPais())
                        ));
                    }
                    return new DTAerolinea(
                            a.getNickname(),
                            a.getNombre(),
                            a.getCorreo(),
                            a.getDescripcion(),
                            a.getLinkSitioWeb(),
                            rutasDTO
                    );
                }
            }
        }
        throw new IllegalArgumentException("Usuario no encontrado");
    }

//    //Si el administrador selecciona una ruta de vuelo o un vuelo reservado o un
//    //paquete comprado, se muestra la información detallada, tal como se indica en
//    //el caso de uso Consulta de Ruta de Vuelo, Consulta de Vuelo y Consulta
//    //de Paquete de Rutas de Vuelo, respectivamente.
//    // Lo que quiere decir que en realidad, no iría una función extra, sino que directamente uso las funciones para el caso respectivo,
//    // esto se tendrá en cuenta una vez que integré las funcionalidades.
//
//    // MODIFICAR DATOS DE USUARIO
//
//    //El caso de uso comienza cuando el administrador desea modificar el
//    //perfil de un usuario. Para ello el sistema muestra la lista de todos los
//    //usuarios y el administrador elige uno.
//    // Para esto ya me sirve:

    //    // public List<DTUsuario> consultarUsuarios()
//
//    // Luego, el sistema muestra todos
//    // los datos del usuario.
//    // Podría usar mostrarDatosUsuario(String nickname), pero para no mostrar las reservas, compras de paquete, o rutas de vuelo:
//
    public DTUsuario mostrarDatosUsuarioMod(String nickname) {
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

        Cliente cliente = clienteServicio.buscarClientePorNickname(nickname);
        if (cliente != null) {
            return new DTCliente(
                    cliente.getNickname(),
                    cliente.getNombre(),
                    cliente.getCorreo(),
                    cliente.getApellido(),
                    cliente.getTipoDoc(),
                    cliente.getNumeroDocumento(),
                    cliente.getFechaNacimiento(),
                    cliente.getNacionalidad(),
                    new ArrayList<>()
            );
        }
        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nickname);
        if (aerolinea != null) {
            return new DTAerolinea(
                    aerolinea.getNickname(),
                    aerolinea.getNombre(),
                    aerolinea.getCorreo(),
                    aerolinea.getDescripcion(),
                    aerolinea.getLinkSitioWeb(),
                    new ArrayList<>()
            );
        }

        throw new IllegalArgumentException("Usuario no encontrado");
    }

    // El administrador puede editar todos los datos
    //básicos, menos el nickname y el correo electrónico. Cuando termina la
    //edición, el sistema actualiza los datos del usuario.

    public void seleccionarUsuarioAMod(String nickname) {
        this.nicknameUsuarioAModificar = nickname;
    }

    // Para Cliente
    public void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        if (nicknameUsuarioAModificar == null) {
            throw new IllegalStateException("Debe seleccionar un usuario antes de modificar sus datos.");
        }
        ClienteServicio clienteServicio = new ClienteServicio();
        Cliente cliente = clienteServicio.buscarClientePorNickname(nicknameUsuarioAModificar);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setFechaNacimiento(fechaNac);
        cliente.setNacionalidad(nacionalidad);
        cliente.setTipoDoc(tipoDocumento);
        cliente.setNumeroDocumento(numeroDocumento);
        clienteServicio.actualizarCliente(cliente);

    }

    // Para Aerolinea
    public void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb) {
        if (nicknameUsuarioAModificar == null) {
            throw new IllegalStateException("Debe seleccionar un usuario antes de modificar sus datos.");
        }
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nicknameUsuarioAModificar);
        if (aerolinea == null) {
            throw new IllegalArgumentException("Aerolínea no encontrada");
        }
        aerolinea.setNombre(nombre);
        aerolinea.setDescripcion(descripcion);
        aerolinea.setLinkSitioWeb(linkSitioWeb);
        aerolineaServicio.actualizarAerolinea(aerolinea);
    }

    // ALTA RUTA VUELO
    public List<DTAerolinea> listarAerolineas() {
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        List<DTAerolinea> listarAerolineas = new ArrayList<>();
        for (Aerolinea a : aerolineaServicio.listarAerolineas()) {
            listarAerolineas.add(new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>())); // Le pasé este último parametro de lista vacía porque necesitaba la lista para otro caso
        }
        return listarAerolineas;
    }

    public void seleccionarAerolinea(String nickname) {
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nickname);
        if (aerolinea != null) {
            // Si tienes una variable interna para la aerolínea seleccionada, asígnala aquí
            // aerolineaSeleccionada = aerolinea; // si existe en tu clase
            recuerdaAerolinea = aerolinea.getNickname();
        } else {
            // aerolineaSeleccionada = null; // si existe en tu clase
            recuerdaAerolinea = null;
        }
    }

    public void ingresarDatosRuta(String nombreRuta, String descripcion, DTHora hora, float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOrigen, String ciudadDestino, DTFecha fechaAlta, String categoria) {
        if (recuerdaAerolinea == null) {
            throw new IllegalStateException("Debe seleccionar una aerolínea antes de ingresar los datos de la ruta.");
        }

        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        CiudadServicio ciudadServicio = new CiudadServicio();
        CategoriaServicio categoriaServicio = new CategoriaServicio();

        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(recuerdaAerolinea);
        if (aerolinea == null) {
            throw new IllegalStateException("La aerolínea seleccionada no existe.");
        }
        if (!esNombreValido(nombreRuta) || !esNombreValido(descripcion) || !esNombreValido(ciudadDestino) || !esNombreValido(ciudadOrigen) || !esNombreValido(categoria)) {
            throw new IllegalArgumentException("El nombre de la ruta, la descripcion, alguna de las ciudades o la categoria, han sido mal ingresados");
        }
        if (costoEjecutivo <= 0 || costoTurista <= 0 || costoEquipajeExtra < 0) {
            throw new IllegalArgumentException("Uno de los costos no es valido. Ingrese un valor mayor a 0");
        }

        dato.entidades.Ciudad origen = ciudadServicio.buscarCiudadPorNombre(ciudadOrigen);
        dato.entidades.Ciudad destino = ciudadServicio.buscarCiudadPorNombre(ciudadDestino);

        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Una de las ciudades no existe.");
        }

        dato.entidades.Categoria cat = categoriaServicio.obtenerCategoriaPorNombre(categoria);
        if (cat == null) {
            throw new IllegalArgumentException("La categoría no existe.");
        }

        DTCostoBase costoBase = new DTCostoBase(costoTurista, costoEjecutivo, costoEquipajeExtra);


        dato.entidades.RutaVuelo rutaVuelo = new dato.entidades.RutaVuelo(
                nombreRuta,
                descripcion,
                costoBase,
                fechaAlta
        );

        rutaVuelo.setCiudadDestino(destino);
        rutaVuelo.setCiudadOrigen(origen);
        rutaVuelo.getCategorias().add(cat);
        rutaVuelo.getAerolineas().add(aerolinea);

        recordarRutaVuelo = rutaVuelo;
    }

    public void registrarRuta() {
        if (recuerdaAerolinea == null) {
            throw new IllegalStateException("Debe seleccionar una aerolínea antes de registrar la ruta.");
        }
        if (recordarRutaVuelo == null) {
            throw new IllegalStateException("Debe ingresar los datos de la ruta antes de registrarla.");
        }

        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        CiudadServicio ciudadServicio = new CiudadServicio();
        RutaVueloServicio rutaVueloServicio = new RutaVueloServicio();

        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(recuerdaAerolinea);
        if (aerolinea == null) {
            throw new IllegalStateException("La aerolínea seleccionada no existe.");
        }

        dato.entidades.Ciudad ciudadOrigen = ciudadServicio.buscarCiudadPorNombre(recordarRutaVuelo.getCiudadOrigen().getNombre());
        dato.entidades.Ciudad ciudadDestino = ciudadServicio.buscarCiudadPorNombre(recordarRutaVuelo.getCiudadDestino().getNombre());
        if (ciudadOrigen == null || ciudadDestino == null) {
            throw new IllegalArgumentException("Una de las ciudades no existe.");
        }

        // Registrar la ruta usando el servicio
        rutaVueloServicio.registrarRutaVuelo(
                recordarRutaVuelo.getNombre(),
                recordarRutaVuelo.getDescripcion(),
                recordarRutaVuelo.getCostoBase(),
                recordarRutaVuelo.getFechaAlta(),
                aerolinea,
                ciudadOrigen,
                ciudadDestino,
                recordarRutaVuelo.getCategorias()
        );

        // Limpiar las variables de selección
        recuerdaAerolinea = null;
        recordarRutaVuelo = null;
    }

    // ALTA CATEGORIA
    public void altaCategoria(String nombre) {
        if (!esNombreValido(nombre)) {
            throw new IllegalArgumentException("Ingrese un nombre con mas de 3 caracteres");
        }
        CategoriaServicio categoriaServicio = new CategoriaServicio();
        categoriaServicio.registrarCategoria(nombre);
    }

    public List<dato.entidades.Categoria> getCategorias() {
        CategoriaServicio categoriaServicio = new CategoriaServicio();
        return categoriaServicio.listarCategorias();
    }

    // ALTA CIUDAD
    public void altaCiudad(String nombre, String pais, String aeropuerto, String descripcion, String sitioWeb, DTFecha fechaAlta){
        CiudadServicio ciudadServicio = new CiudadServicio();

        // Verificar si la ciudad ya existe usando el servicio
        dato.entidades.Ciudad ciudadExistente = ciudadServicio.buscarCiudadPorNombreYPais(nombre, pais);
        if (ciudadExistente != null) {
            throw new IllegalArgumentException("La ciudad ya existe.");
        }
        if ( !esNombreValido(nombre) || !esNombreValido(pais) || !esNombreValido(aeropuerto) || !esNombreValido(descripcion)) {
            throw new IllegalArgumentException("Nombre, pais aeropuerto o descrpcion mal ingresados");
        }
        AeropuertoServicio aeropuertoServicio = new AeropuertoServicio();
        boolean existeAeropuerto = aeropuertoServicio.existeAeropuerto(aeropuerto);
        if (!existeAeropuerto) {
            throw new IllegalArgumentException("El aeropuerto no existe.");
        }

        // Crear la ciudad usando el servicio
        ciudadServicio.registrarCiudad(nombre, pais, aeropuerto, descripcion, sitioWeb, fechaAlta);
    }
    
    // PRECARGA DE AEROPUERTOS
    public void precargarAeropuertos() {
        AeropuertoServicio aeropuertoServicio = new AeropuertoServicio();
        aeropuertoServicio.precargarAeropuertos();
    }

    // PRECARGA DE CATEGORÍAS
    public void precargarCategorias() {
        CategoriaServicio categoriaServicio = new CategoriaServicio();
        categoriaServicio.precargarCategorias();
    }

    // PRECARGA DE USUARIOS (CLIENTES Y AEROLÍNEAS)
    public void precargarUsuarios() {
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

        clienteServicio.precargarClientes();
        aerolineaServicio.precargarAerolineas();
    }

    // PRECARGA DE RUTAS DE VUELO
    public void precargarRutasVuelo() {
        RutaVueloServicio rutaVueloServicio = new RutaVueloServicio();
        rutaVueloServicio.precargarRutasVuelo();
    }

    // PRECARGA DE VUELOS
    public void precargarVuelos() {
        VueloServicio vueloServicio = new VueloServicio();
        vueloServicio.precargarVuelos();
    }

    // PRECARGA COMPLETA DEL SISTEMA
    public void precargarSistemaCompleto() {
        System.out.println("=== INICIANDO PRECARGA COMPLETA DEL SISTEMA ===");

        try {
            System.out.println("1. Precargando aeropuertos...");
            precargarAeropuertos();

            System.out.println("2. Precargando categorías...");
            precargarCategorias();

            System.out.println("3. Precargando usuarios (clientes y aerolíneas)...");
            precargarUsuarios();

            System.out.println("4. Precargando rutas de vuelo...");
            precargarRutasVuelo();

            System.out.println("5. Precargando vuelos...");
            precargarVuelos();

            System.out.println("=== PRECARGA COMPLETA FINALIZADA EXITOSAMENTE ===");

        } catch (Exception e) {
            System.err.println("Error durante la precarga completa: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en la precarga completa del sistema", e);
        }
    }


    // CONSULTA RUTA VUELO
    public List<DTRutaVuelo> listarRutaVuelo(String nombreAerolinea) {
        List<DTRutaVuelo> listaRutas = new ArrayList<>();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        Aerolinea aerolinea = aerolineaServicio.buscarAerolineaPorNickname(nombreAerolinea);
        if (aerolinea == null) {
            throw new IllegalArgumentException("No se encontró una aerolínea con el nickname: " + nombreAerolinea);
        }
        for (RutaVuelo r : aerolinea.getRutasVuelo()) {
            listaRutas.add(new DTRutaVuelo(
                    r.getNombre(),
                    r.getDescripcion(),
                    r.getFechaAlta(),
                    r.getCostoBase(),
                    new DTAerolinea(aerolinea.getNickname(), aerolinea.getNombre(), aerolinea.getCorreo(), aerolinea.getDescripcion(), aerolinea.getLinkSitioWeb(), new ArrayList<>()),
                    new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
                    new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
            ));
        }
        return listaRutas;
    }

    // ALTA DE VUELO
    public List<DTRutaVuelo> seleccionarAerolineaRet(String nickname){
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        this.recuerdaAerolinea = nickname;
        return aerolineaServicio.obtenerRutasDeAerolinea(nickname);
    }

    public DTRutaVuelo seleccionarRutaVueloRet(String nombre) {
        if (recuerdaAerolinea == null) {
            throw new IllegalStateException("Debe seleccionar una aerolínea antes de seleccionar una ruta.");
        }
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        Aerolinea aerolineaSeleccionada = aerolineaServicio.buscarAerolineaPorNickname(recuerdaAerolinea);
        for (dato.entidades.RutaVuelo rv : aerolineaSeleccionada.getRutasVuelo()) {
            if (rv.getNombre().equalsIgnoreCase(nombre)) {
                dato.entidades.RutaVuelo recordarRuta = rv;
                DTRutaVuelo dtRuta = new DTRutaVuelo(
                        rv.getNombre(),
                        rv.getDescripcion(),
                        rv.getFechaAlta(),
                        rv.getCostoBase(),
                        new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>()),
                        new DTCiudad(rv.getCiudadOrigen().getNombre(), rv.getCiudadOrigen().getPais()),
                        new DTCiudad(rv.getCiudadDestino().getNombre(), rv.getCiudadDestino().getPais())

                );
                return dtRuta;
            }
        }
        throw new IllegalStateException("No se encontró la ruta con el nombre: " + nombre);
    }

    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo, DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta, DTRutaVuelo ruta) {

        if (ruta == null) { //recordarRutaVuelo==NULL
            throw new IllegalStateException("Debe seleccionar una ruta antes de ingresar los datos del vuelo.");
        }
        if (!esNombreValido(nombre)){
            throw new IllegalStateException("El nombre del vuelo no es valido");
        }

        DTVuelo dtVuelo = new DTVuelo(duracion, nombre, fecha, horaVuelo, maxEjecutivo, fechaAlta, maxTurista, ruta);
        recordarDatosVuelo = dtVuelo;
        return dtVuelo;
    }

    public void darAltaVuelo() {
        if (recordarDatosVuelo == null) {
            throw new IllegalStateException("Debe ingresar los datos del vuelo antes de registrarlo.");
        }

        VueloServicio vueloServicio = new VueloServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();


        Aerolinea aerolineaSeleccionada = aerolineaServicio.buscarAerolineaPorNickname(recuerdaAerolinea);
        if (aerolineaSeleccionada == null) {
            throw new IllegalStateException("No se encontró la aerolínea seleccionada.");
        }

        if (vueloServicio.buscarVueloPorNombre(recordarDatosVuelo.getNombre()) != null) {
            throw new IllegalStateException("Ya existe un vuelo con ese nombre.");
        }

        // Buscar la ruta asociada
        dato.entidades.RutaVuelo ruta = null;
        for (dato.entidades.RutaVuelo r : aerolineaSeleccionada.getRutasVuelo()) {
            if (r.getNombre().equalsIgnoreCase(recordarDatosVuelo.getRuta().getNombre())) {
                ruta = r;
                break;
            }
        }
        if (ruta == null) {
            throw new IllegalStateException("No se encontró la ruta asociada al vuelo.");
        }

        // Crear el vuelo y asociar la ruta
        dato.entidades.Vuelo nuevoVuelo = new dato.entidades.Vuelo(
                recordarDatosVuelo.getNombre(),
                recordarDatosVuelo.getFechaVuelo(),
                recordarDatosVuelo.getHoraVuelo(),
                recordarDatosVuelo.getDuracion(),
                recordarDatosVuelo.getAsientosMaxTurista(),
                recordarDatosVuelo.getAsientosMaxEjecutivo(),
                recordarDatosVuelo.getFechaAlta()
        );
        nuevoVuelo.setRutaVuelo(ruta);

        // Guardar el vuelo en la base de datos
        vueloServicio.registrarVuelo(
                nuevoVuelo.getNombre(),
                nuevoVuelo.getFechaVuelo(),
                nuevoVuelo.getHoraVuelo(),
                nuevoVuelo.getDuracion(),
                nuevoVuelo.getAsientosMaxTurista(),
                nuevoVuelo.getAsientosMaxEjecutivo(),
                nuevoVuelo.getFechaAlta(),
                nuevoVuelo.getRutaVuelo()
        );

        recordarDatosVuelo = null;
    }

    //CONSULTA VUELO

    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo){
        listaDTVuelos.clear();
        VueloServicio vueloServicio = new VueloServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

        List<dato.entidades.Vuelo> vuelos = vueloServicio.listarVuelos();

        for (dato.entidades.Vuelo v : vuelos) {
            dato.entidades.RutaVuelo r = v.getRutaVuelo();
                if (r!= null && r.getNombre().equalsIgnoreCase(nombreRutaVuelo)) {
                    DTAerolinea dtAerolinea = r.getAerolineas() != null ? new DTAerolinea(r.getAerolineas().get(0).getNickname(), r.getAerolineas().get(0).getNombre(), r.getAerolineas().get(0).getCorreo(), r.getAerolineas().get(0).getDescripcion(), r.getAerolineas().get(0).getLinkSitioWeb(), new ArrayList<>()) : null;
                    DTRutaVuelo dtRuta = new DTRutaVuelo(
                            r.getNombre(),
                            r.getDescripcion(),
                            r.getFechaAlta(),
                            r.getCostoBase(),
                            dtAerolinea,
                            new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
                            new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
                    );
                    DTVuelo dtVuelo = new DTVuelo(v.getDuracion(), v.getNombre(), v.getFechaVuelo(), v.getHoraVuelo(), v.getAsientosMaxEjecutivo(), v.getFechaAlta(), v.getAsientosMaxTurista(), dtRuta);
                    listaDTVuelos.add(dtVuelo);
                }

        }
        return listaDTVuelos;
    }

    public List<DTVueloReserva> seleccionarVuelo(String nombre){
        List<DTVueloReserva> listaReservas = new ArrayList<>();
        DTVuelo vueloSeleccionado = null;
        for (DTVuelo dtVuelo : listaDTVuelos) {
            if (dtVuelo.getNombre().equalsIgnoreCase(nombre)) {
                vueloSeleccionado = dtVuelo;
                break;
            }
        }
        if (vueloSeleccionado == null)
            throw new IllegalStateException("No se encontró el vuelo.");

        VueloServicio vueloServicio = new VueloServicio();
        List<dato.entidades.Vuelo> vuelos = vueloServicio.listarVuelos();

        // Recorremos la lista de vuelos para tomar las reservas
        for (dato.entidades.Vuelo v : vuelos) {
            if (v.getNombre().equalsIgnoreCase(nombre)) {
                for (Reserva r : v.getReserva()) {
                    DTReserva dtReserva = new DTReserva(r.getFechaReserva(), r.getCostoReserva());
                    DTVueloReserva dtVueloR = new DTVueloReserva(vueloSeleccionado, dtReserva);
                    listaReservas.add(dtVueloR);
                }
            }
        }
        return listaReservas;
    }

    public List<DTRutaVuelo> listarRutaVueloDeVuelo(){
        List<DTRutaVuelo> listaRutas = new ArrayList<>();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        List<Aerolinea> aerolineas = aerolineaServicio.listarAerolineas();
        for (Aerolinea a : aerolineas) {
            for (RutaVuelo r : a.getRutasVuelo()) {
                listaRutas.add(new DTRutaVuelo(
                        r.getNombre(),
                        r.getDescripcion(),
                        r.getFechaAlta(),
                        r.getCostoBase(),
                        new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>()),
                        new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
                        new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
                ));
            }

        }
        return listaRutas;
    }

    // RESERVA VUELO

    public List<DTCliente> mostrarClientesSinVueloSeleccionado() {
        ClienteServicio clienteServicio = new ClienteServicio();
        List<DTCliente> listaClientes = new ArrayList<>();
        List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();
        for (Usuario u : clientes) {
            if (u instanceof Cliente c) {
                if (!c.getReservas().isEmpty()) {
                    if (c.getReservas().stream().noneMatch(r -> r instanceof dato.entidades.CompraComun compraComun && compraComun.getVuelo() != null && compraComun.getVuelo().getNombre().equals(vueloSeleccionadoParaReserva))) {
                        listaClientes.add(new DTCliente(
                                c.getNickname(),
                                c.getNombre(),
                                c.getCorreo(),
                                c.getApellido(),
                                c.getTipoDoc(),
                                c.getNumeroDocumento(),
                                c.getFechaNacimiento(),
                                c.getNacionalidad(),
                                new ArrayList<>()
                        ));
                    }
                }
            }
        }
        return listaClientes;
    }

    public void seleccionarVueloParaReserva(String nombreVuelo) {
        this.vueloSeleccionadoParaReserva = nombreVuelo;
    }

    public List<DTCliente> listarClientes() {
        ClienteServicio clienteServicio = new ClienteServicio();
        List<DTCliente> listaClientes = new ArrayList<>();
        List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();

        for (dato.entidades.Cliente c : clientes) {
            listaClientes.add(new DTCliente(
                    c.getNickname(),
                    c.getNombre(),
                    c.getCorreo(),
                    c.getApellido(),
                    c.getTipoDoc(),
                    c.getNumeroDocumento(),
                    c.getFechaNacimiento(),
                    c.getNacionalidad(),
                    new ArrayList<>() // Sin reservas para simplificar
            ));
        }
        return listaClientes;
    }

    // Método de prueba para verificar conexión a BD
    public void probarConexionBD() {
        try {
            ClienteServicio clienteServicio = new ClienteServicio();
            List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();
            System.out.println("=== PRUEBA DE CONEXIÓN BD ===");
            System.out.println("Clientes encontrados: " + clientes.size());
            for (dato.entidades.Cliente c : clientes) {
                System.out.println("- Cliente: " + c.getNickname() + " (" + c.getNombre() + ")");
            }

            VueloServicio vueloServicio = new VueloServicio();
            List<dato.entidades.Vuelo> vuelos = vueloServicio.listarVuelos();
            System.out.println("Vuelos encontrados: " + vuelos.size());
            for (dato.entidades.Vuelo v : vuelos) {
                System.out.println("- Vuelo: " + v.getNombre());
            }
            System.out.println("=== FIN PRUEBA BD ===");
        } catch (Exception e) {
            System.err.println("Error en prueba de BD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva) {
        System.out.println("=== INICIANDO PROCESO DE RESERVA ===");
        System.out.println("Vuelo seleccionado: " + vueloSeleccionadoParaReserva);
        System.out.println("Tipo asiento: " + tipoAsiento);
        System.out.println("Cantidad pasajes: " + cantidadPasaje);
        System.out.println("Equipaje extra: " + equipajeExtra);
        System.out.println("Pasajeros: " + nombresPasajeros);

        if (vueloSeleccionadoParaReserva == null) {
            throw new IllegalStateException("Debe seleccionar un vuelo antes de reservar.");
        }

        // Usar un EntityManager compartido para toda la operación
        jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("volandouyPU");
        jakarta.persistence.EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            VueloServicio vueloServicio = new VueloServicio();
            ClienteServicio clienteServicio = new ClienteServicio();

            dato.entidades.Vuelo vueloSeleccionado = vueloServicio.buscarVueloPorNombre(vueloSeleccionadoParaReserva);
            if (vueloSeleccionado == null) {
                throw new IllegalStateException("No se encontró el vuelo seleccionado.");
            }

            String nicknameClientePrincipal = nombresPasajeros.get(0);
            if (nicknameClientePrincipal == null || nicknameClientePrincipal.trim().isEmpty()) {
                throw new IllegalArgumentException("No se ha seleccionado un cliente principal. Debe seleccionar un cliente de la tabla.");
            }

            Cliente clientePrincipal = clienteServicio.buscarClientePorNickname(nicknameClientePrincipal.trim());
            if (clientePrincipal == null) {
                throw new IllegalArgumentException("No se encontró el cliente principal con nickname: " + nicknameClientePrincipal.trim() +
                        ". Verifique que el cliente existe en el sistema.");
            }

            // Verificar disponibilidad de asientos
            if (tipoAsiento == TipoAsiento.Ejecutivo && vueloSeleccionado.getAsientosMaxEjecutivo() < cantidadPasaje) {
                throw new IllegalStateException("No hay suficientes asientos ejecutivos disponibles.");
            }
            if (tipoAsiento == TipoAsiento.Turista && vueloSeleccionado.getAsientosMaxTurista() < cantidadPasaje) {
                throw new IllegalStateException("No hay suficientes asientos turista disponibles.");
            }

            // Calcular costo total ANTES de crear la reserva
            DTCostoBase costoBase = vueloSeleccionado.getRutaVuelo().getCostoBase();
            float costoTotal;
            if (tipoAsiento == TipoAsiento.Ejecutivo) {
                costoTotal = (costoBase.getCostoEjecutivo() * cantidadPasaje) + (equipajeExtra * costoBase.getCostoEquipajeExtra());
            } else {
                costoTotal = (costoBase.getCostoTurista() * cantidadPasaje) + (equipajeExtra * costoBase.getCostoEquipajeExtra());
            }

            System.out.println("Costo total calculado ANTES de crear reserva: " + costoTotal);

            // Crear la reserva directamente en el EntityManager
            dato.entidades.CompraComun reserva = new dato.entidades.CompraComun(clientePrincipal, fechaReserva, tipoAsiento, equipajeExtra);
            reserva.setVuelo(vueloSeleccionado);

            // Establecer correctamente el costo base con la cantidad de equipaje extra
            costoBase.setCantidadEquipajeExtra(equipajeExtra);
            costoBase.setCostoTotal(costoTotal);
            reserva.setCostoReserva(costoBase);

            em.persist(reserva);
            em.flush(); // Forzar la escritura inmediata para obtener el ID

            System.out.println("Reserva creada con ID: " + reserva.getId());

            // Crear pasajes según la cantidad especificada
            System.out.println("Creando " + cantidadPasaje + " pasajes...");

            // Calcular costo individual del pasaje
            float costoIndividualPasaje;
            if (tipoAsiento == TipoAsiento.Ejecutivo) {
                costoIndividualPasaje = costoBase.getCostoEjecutivo();
            } else {
                costoIndividualPasaje = costoBase.getCostoTurista();
            }

            // Crear un pasaje por cada cantidad especificada
            for (int i = 0; i < cantidadPasaje; i++) {
                String nombrePasajero = nombresPasajeros.get(i); // Usar el cliente correspondiente
                System.out.println("Procesando pasaje " + (i + 1) + " para: " + nombrePasajero);

                Cliente pasajero = clienteServicio.buscarClientePorNickname(nombrePasajero);
                if (pasajero == null) {
                    throw new IllegalArgumentException("No se encontró el pasajero: " + nombrePasajero);
                }

                // Crear pasaje directamente en el EntityManager
                dato.entidades.Pasaje pasaje = new dato.entidades.Pasaje();
                pasaje.setNombrePasajero(pasajero.getNombre());
                pasaje.setApellidoPasajero(pasajero.getApellido());
                pasaje.setPasajero(pasajero);
                pasaje.setReserva(reserva);
                pasaje.setTipoAsiento(tipoAsiento);
                pasaje.setCostoPasaje((int) costoIndividualPasaje); // Establecer el costo del pasaje

                // Debug: verificar el valor de tipoAsiento antes de persistir
                System.out.println("DEBUG: tipoAsiento = " + tipoAsiento + " (ordinal: " + tipoAsiento.ordinal() + ")");


                // Establecer la relación bidireccional
                reserva.getPasajeros().add(pasaje);
                em.persist(pasaje);
                em.flush(); // Forzar la escritura inmediata

                System.out.println("Pasaje " + (i + 1) + " creado para: " + nombrePasajero +
                        " (" + pasaje.getNombrePasajero() + " " + pasaje.getApellidoPasajero() +
                        ") con ID: " + pasaje.getId() + " y costo: " + costoIndividualPasaje);
            }

            em.getTransaction().commit();
            System.out.println("Reserva completada exitosamente. ID: " + reserva.getId());

            // Limpiar selección
            vueloSeleccionadoParaReserva = null;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error en el proceso de reserva: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("Error al realizar la reserva: " + e.getMessage(), e);
        } finally {
            em.close();
            emf.close();
        }
    }

    // PAQUETES DE VUELO
//
    // CREAR PAQUETE VUELO
    public void crearPaquete(String nombrePaquete, String descripcion,TipoAsiento tipoAsiento, int diasValidos, float descuento, DTFecha fechaAlta) {
//        for (PaqueteVuelo p : paqueteVuelos) {
//            if (p.getNombre().equalsIgnoreCase(nombrePaquete)) {
//                throw new IllegalArgumentException("El nombre del paquete ya existe.");
//            }
//        }
        logica.servicios.PaqueteVueloServicio Servicio = new PaqueteVueloServicio();

        dato.entidades.PaqueteVuelo paqueteVueloexistente = Servicio.obtenerPaquetePorNombre(nombrePaquete);
        if (paqueteVueloexistente != null) {
            throw new IllegalArgumentException("El nombre del paquete ya existe.");
        }
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
        }
        if (diasValidos <= 0) {
            throw new IllegalArgumentException("Los días válidos deben ser mayores a 0.");
        }
        if (nombrePaquete.length() <= 3 || descripcion.length() <= 10) {
            throw new IllegalStateException("Nombre o descripcion muy cortos, ingrese un nombre con mas de 3 caracteres o una descripcion con 10 o mas caracteres");
        }

        try {
            dato.entidades.PaqueteVuelo paqueteVuelo = Servicio.registrarPaqueteVuelo(nombrePaquete, descripcion/*tipoAsiento*/, diasValidos, descuento, fechaAlta);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear el paquete de vuelo: " + e.getMessage());
        }
    }

    // AGREGAR RUTAS DE VUELO A PAQUETE
    public List<DTPaqueteVuelos> mostrarPaquete() {
        PaqueteVueloServicio paqueteVueloServicio = new PaqueteVueloServicio();
        List<DTPaqueteVuelos> listaPaquetes = new ArrayList<>();
        List<dato.entidades.PaqueteVuelo> paquetes = paqueteVueloServicio.listarPaquetes();
        for (dato.entidades.PaqueteVuelo p : paquetes) {
            if (p.getCantidad() != null) {
                DTPaqueteVuelos dtPaquete = new DTPaqueteVuelos(
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getDiasValidos(),
                        p.getDescuento(),
                        p.getFechaAlta()
                );
                listaPaquetes.add(dtPaquete);
            }
        }
        return listaPaquetes;
    }

    public List<DTPaqueteVuelos> obtenerPaquetesNoComprados() {
        PaqueteVueloServicio paqueteVueloServicio = new PaqueteVueloServicio();
        List<DTPaqueteVuelos> listaPaquetes = new ArrayList<>();
        List<dato.entidades.PaqueteVuelo> paquetes = paqueteVueloServicio.listarPaquetes();
        for (dato.entidades.PaqueteVuelo p : paquetes) {
            if (!p.isComprado() && p.getCantidad() != null) {
                DTPaqueteVuelos dtPaquete = new DTPaqueteVuelos(
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getDiasValidos(),
                        p.getDescuento(),
                        p.getFechaAlta()
                );
                listaPaquetes.add(dtPaquete);
            }
        }
        return listaPaquetes;
    }

    public List<DTPaqueteVuelos> mostrarPaqueteConRutas() {
        PaqueteVueloServicio paqueteVueloServicio = new PaqueteVueloServicio();
        List<DTPaqueteVuelos> listaPaquetes = new ArrayList<>();
        List<PaqueteVuelo> paquetes = paqueteVueloServicio.listarPaquetes();

        for (PaqueteVuelo p : paquetes) {
            if (p.getCantidad() != null && !p.getCantidad().isEmpty()) {

                // Creamos el DTO del paquete usando entidades
                DTPaqueteVuelos dtPaquete = new DTPaqueteVuelos(
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getDiasValidos(),
                        p.getDescuento(),
                        p.getFechaAlta()
                );

                // Copiamos las rutas
                List<RutaVuelo> rutas = new ArrayList<>();
                for (Cantidad c : p.getCantidad()) {
                    if (c.getRutaVuelo() != null) {
                        rutas.add(c.getRutaVuelo());
                    }
                }

                dtPaquete.setRutas(rutas);

                listaPaquetes.add(dtPaquete);
            }
        }

        return listaPaquetes;
    }

    public boolean clienteYaComproPaquete() {
        CompraPaqueteServicio cps = new CompraPaqueteServicio();
        List<CompraPaquete> compras = cps.buscarPorCliente(clienteSeleccionado);

        for (CompraPaquete cp : compras) {
            if (cp.getPaqueteVuelo().getId().equals(paqueteSeleccionado.getId())) {
                return true; // si ya lo compró
            }
        }
        return false;
    }



    public void seleccionarPaquete(String nombrePaquete) {
        PaqueteVueloServicio paqueteVueloServicio = new PaqueteVueloServicio();
        dato.entidades.PaqueteVuelo paquete = paqueteVueloServicio.obtenerPaquetePorNombre(nombrePaquete);
        if (paquete != null) {
            paqueteSeleccionado = paquete;
            return;
        }
        throw new IllegalArgumentException("No se encontró un paquete con el nombre: " + nombrePaquete);
    }

    public void seleccionarAerolineaPaquete(DTAerolinea DTaerolinea) {
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        aerolineaSeleccionada = aerolineaServicio.buscarAerolineaPorNickname(DTaerolinea.getNickname());
    }

    public void seleccionarRutaVueloPaquete(String nombreRuta) {
//        if (aerolineaSeleccionada == null) {
//            throw new IllegalStateException("No hay aerolínea seleccionada.");
//        }
        for (RutaVuelo r : aerolineaSeleccionada.getRutasVuelo()) {
            if (r.getNombre().equals(nombreRuta)) {
                rutaVueloSeleccionada = r;
            }
        }
        if (rutaVueloSeleccionada == null) {
            throw new IllegalArgumentException("Ruta de vuelo no encontrada: " + nombreRuta);
        }
    }

    public void seleccionarRVPaquete(String nombreRuta) {
//        if (aerolineaSeleccionada == null) {
//            throw new IllegalStateException("No hay aerolínea seleccionada.");
//        }

        for (Cantidad c : paqueteSeleccionado.getCantidad()) {
            if (c.getRutaVuelo().getNombre().equals(nombreRuta)) {
                rutaVueloSeleccionada = c.getRutaVuelo();
            }
        }
        if (rutaVueloSeleccionada == null) {
            throw new IllegalArgumentException("Ruta de vuelo no encontrada: " + nombreRuta);
        }
    }

    public void agregarRutaAPaquete (int cant, TipoAsiento tipoAsiento) {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de agregar una ruta.");
        }
        if (rutaVueloSeleccionada == null) {
            throw new IllegalArgumentException("La ruta seleccionada no puede ser nula.");
        }
        if (paqueteSeleccionado.isComprado()) {
            throw new IllegalStateException("Este paquete ya fue comprado, no se pueden agregar mas rutas");
        }
        for (Cantidad c : paqueteSeleccionado.getCantidad()) {
            if (c.getRutaVuelo() != null && c.getRutaVuelo().getNombre().equalsIgnoreCase(rutaVueloSeleccionada.getNombre())) {
                throw new IllegalArgumentException("La ruta ya está agregada al paquete.");
            }
        }

        PaqueteVueloServicio paqueteVueloServicio = new PaqueteVueloServicio();
        CantidadServicio cantidadServicio = new CantidadServicio();

        Cantidad nuevaCantidad = cantidadServicio.registrarCantidad(cant);

        nuevaCantidad.setRutaVuelo(rutaVueloSeleccionada);
        nuevaCantidad.setPaqueteVuelo(paqueteSeleccionado);
        nuevaCantidad.setTipoAsiento(tipoAsiento);
        nuevaCantidad.setCant(cant);

        paqueteSeleccionado.addCantidad(nuevaCantidad);

        float nuevoCostoTotal = 0;
        for (Cantidad c : paqueteSeleccionado.getCantidad()) {
            if (c.getRutaVuelo() != null) {
                float costoUnidad = (c.getTipoAsiento() == TipoAsiento.Ejecutivo)
                        ? c.getRutaVuelo().getCostoBase().getCostoEjecutivo()
                        : c.getRutaVuelo().getCostoBase().getCostoTurista();
                nuevoCostoTotal += c.getCant() * costoUnidad;
            }
        }

        nuevoCostoTotal = nuevoCostoTotal - (nuevoCostoTotal * (paqueteSeleccionado.getDescuento() / 100));
        paqueteSeleccionado.setCostoTotal(nuevoCostoTotal);
        paqueteVueloServicio.actualizarPaquete(paqueteSeleccionado);
    }

    public DTPaqueteVuelos consultaPaqueteVuelo () {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de consultar.");
        }

        DTPaqueteVuelos dtPaquete = new DTPaqueteVuelos(
                paqueteSeleccionado.getNombre(),
                paqueteSeleccionado.getDescripcion(),
                paqueteSeleccionado.getDiasValidos(),
                paqueteSeleccionado.getDescuento(),
                paqueteSeleccionado.getFechaAlta()
        );
        dtPaquete.setCantidad(paqueteSeleccionado.getCantidad());
        dtPaquete.setCostoTotal(paqueteSeleccionado.getCostoTotal());

        return dtPaquete;
    }

    public List <DTRutaVuelo> consultaPaqueteVueloRutas() {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de consultar.");
        }
        PaqueteVueloServicio paqueteVueloServicio = new PaqueteVueloServicio();
        return paqueteVueloServicio.obtenerRutasDePaquete(paqueteSeleccionado.getNombre());
    }


    public String consultaPaqueteVueloRutasCantidadTipo() {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de consultar.");
        }
        if (paqueteSeleccionado.getCantidad() == null || paqueteSeleccionado.getCantidad().isEmpty()) {
            throw new IllegalStateException("El paquete no tiene rutas asociadas.");
        }
        if (rutaVueloSeleccionada == null) {
            throw new IllegalStateException("No hay una ruta de vuelo seleccionada.");
        }
        String rutasConCantidad = "";
        if (paqueteSeleccionado.getCantidad() != null) {
            for (Cantidad c : paqueteSeleccionado.getCantidad()) {
                if (c.getRutaVuelo() == rutaVueloSeleccionada) {
                    rutasConCantidad = "Cantidad: " + c.getCant() + ", Tipo Asiento: " + c.getTipoAsiento();

                }
            }
        }
        return rutasConCantidad;
    }


    public List <String> listarAerolineasRutaVuelo () {
        List<String> listaAerolineas = new ArrayList<>();
        if (rutaVueloSeleccionada == null) {
            throw new IllegalStateException("No hay una ruta de vuelo seleccionada.");
        }
        if (rutaVueloSeleccionada.getAerolineas() != null) {
            for (Aerolinea a : rutaVueloSeleccionada.getAerolineas()) {
                listaAerolineas.add(a.getNickname());
            }
        }
        return listaAerolineas;
    }
    
    // COMPRA PAQUETE

    public List<DTCliente> mostrarClientes() {
        ClienteServicio clienteServicio = new ClienteServicio();
        List<DTCliente> listaClientes = new ArrayList<>();
        List<dato.entidades.Cliente> clientes = clienteServicio.listarClientes();
        for (Usuario u : clientes) {
            if (u instanceof Cliente c) {
                listaClientes.add(new DTCliente(
                        c.getNickname(),
                        c.getNombre(),
                        c.getCorreo(),
                        c.getApellido(),
                        c.getTipoDoc(),
                        c.getNumeroDocumento(),
                        c.getFechaNacimiento(),
                        c.getNacionalidad(),
                        new ArrayList<>()
                ));
            }
        }
        return listaClientes;
    }


    public void seleccionarCliente(String nombreCliente){
        ClienteServicio clienteServicio = new ClienteServicio();
        dato.entidades.Cliente cliente = clienteServicio.buscarClientePorNickname(nombreCliente);
        if (cliente != null) {
            clienteSeleccionado = cliente;
            return;
        }
        throw new IllegalArgumentException("No se encontró un cliente con el nombre: " + nombreCliente);
    }

    public void realizarCompra(DTFecha fechaCompra, float costo, DTFecha vencimiento/*TipoAsiento tipoAsiento*/){
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de realizar la compra.");
        }
        if (clienteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un cliente antes de realizar la compra.");
        }
        if (costo < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }
        CompraPaqueteServicio servicioCompraPaquete = new CompraPaqueteServicio();
        PaqueteVueloServicio servicioPaqueteVuelo = new PaqueteVueloServicio();
        try {
            dato.entidades.CompraPaquete compraPaquete = servicioCompraPaquete.registrarCompraPaquete(clienteSeleccionado, fechaCompra, vencimiento/*tipoAsiento*/, paqueteSeleccionado);
            compraPaquete.setCostoTotal(costo); // falta saber bien como se calcula el costo de un paquete.
            clienteSeleccionado.addReserva(compraPaquete);
            clienteSeleccionado.incrementarCantidadPaquetes();
            paqueteSeleccionado.setComprado(true);
            paqueteSeleccionado.agregarCompraPaquete(compraPaquete);
            servicioPaqueteVuelo.actualizarPaquete(paqueteSeleccionado);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error");
        }
    }
}

