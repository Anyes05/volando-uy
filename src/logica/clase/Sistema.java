package logica.clase;


import dato.entidades.Aeropuerto;
import dato.entidades.Cliente;
import dato.entidades.CompraPaquete;
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
    private Cliente clienteSeleccionado;
    private PaqueteVuelo paqueteSeleccionado;
    private List<DTVuelo> listaDTVuelos;
    private String nicknameUsuarioAModificar;

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

    public void altaCliente(String nickname, String nombre, String correo, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        if (existeNickname(nickname)) {
            throw new IllegalArgumentException("El nickname ya existe.");
        }
        if (existeCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico ya existe.");
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

        // Crear la ciudad usando el servicio
        ciudadServicio.registrarCiudad(nombre, pais, aeropuerto, descripcion, sitioWeb, fechaAlta);
    }
    
    // PRECARGA DE AEROPUERTOS
    public void precargarAeropuertos() {
        AeropuertoServicio aeropuertoServicio = new AeropuertoServicio();
        aeropuertoServicio.precargarAeropuertos();
    }

    // CONSULTA RUTA VUELO
    public List<DTRutaVuelo> listarRutaVuelo(String nombreAerolinea) {
        List<DTRutaVuelo> listaRutas = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Aerolinea a && a.getNickname().equalsIgnoreCase(nombreAerolinea)) {
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
                return listaRutas;
            }
        }
        throw new IllegalArgumentException("No se encontró una aerolínea con el nickname: " + nombreAerolinea);
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
                nuevoVuelo.getFechaAlta()
        );

        recordarDatosVuelo = null;
    }

//    //CONSULTA VUELO
//
//    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo){
//        listaDTVuelos.clear();
//        VueloServicio vueloServicio = new VueloServicio();
//        AerolineaServicio aerolineaServicio = new AerolineaServicio();
//
//        List<dato.entidades.Vuelo> vuelos = vueloServicio.listarVuelos();
//
//        for (dato.entidades.Vuelo v : vuelos) {
//            dato.entidades.RutaVuelo r = v.getRutaVuelo();
//                if (r!= null && r.getNombre().equalsIgnoreCase(nombreRutaVuelo)) {
//                    Aerolinea aerolinea = r.getAerolineas().isEmpty() ? null : r.getAerolineas().get(0);
//                    DTAerolinea dtAerolinea = aerolinea != null
//                            ? new DTAerolinea(aerolinea.getNickname(), aerolinea.getNombre(), aerolinea.getCorreo(), aerolinea.getDescripcion(), aerolinea.getLinkSitioWeb(), new ArrayList<>())
//                            : null;
//                    DTRutaVuelo dtRuta = new DTRutaVuelo(
//                            r.getNombre(),
//                            r.getDescripcion(),
//                            r.getFechaAlta(),
//                            r.getCostoBase(),
//                            dtAerolinea,
//                            new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
//                            new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
//                    );
//                    DTVuelo dtVuelo = new DTVuelo(v.getDuracion(), v.getNombre(), v.getFechaVuelo(), v.getHoraVuelo(), v.getAsientosMaxEjecutivo(), v.getFechaAlta(), v.getAsientosMaxTurista(), dtRuta);
//                    listaDTVuelos.add(dtVuelo);
//                }
//
//        }
//        return listaDTVuelos;
//    }
//
//    public List<DTVueloReserva> seleccionarVuelo(String nombre){
//        List<DTVueloReserva> listaReservas = new ArrayList<>();
//        DTVuelo vueloSeleccionado = null;
//        for (DTVuelo dtVuelo : listaDTVuelos) {
//            if (dtVuelo.getNombre().equalsIgnoreCase(nombre)) {
//                vueloSeleccionado = dtVuelo;
//                break;
//            }
//        }
//        if (vueloSeleccionado == null)
//            throw new IllegalStateException("No se encontró el vuelo.");
//
//        VueloServicio vueloServicio = new VueloServicio();
//        List<dato.entidades.Vuelo> vuelos = vueloServicio.listarVuelos();
//
//        // Recorremos la lista de vuelos para tomar las reservas
//        for (dato.entidades.Vuelo v : vuelos) {
//            if (v.getNombre().equalsIgnoreCase(nombre)) {
//                for (Reserva r : v.getReserva()) {
//                    DTReserva dtReserva = new DTReserva(r.getFechaReserva(), r.getCostoReserva());
//                    DTVueloReserva dtVueloR = new DTVueloReserva(vueloSeleccionado, dtReserva);
//                    listaReservas.add(dtVueloR);
//                }
//            }
//        }
//        return listaReservas;
//    }

//    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva) {
//        if (recordarDatosVuelo == null) {
//            throw new IllegalStateException("Debe seleccionar un vuelo antes de reservar.");
//        }
//
//        // Buscar el vuelo seleccionado
//        Vuelo vueloSeleccionado = null;
//        for (Vuelo v : vuelos) {
//            if (v.getNombre().equalsIgnoreCase(recordarDatosVuelo.getNombre())) {
//                vueloSeleccionado = v;
//                break;
//            }
//        }
//        if (vueloSeleccionado == null) {
//            throw new IllegalStateException("No se encontró el vuelo seleccionado.");
//        }
//
//        // Buscar el cliente principal (primer nombre en la lista)
//        Cliente clientePrincipal = null;
//        for (Usuario u : usuarios) {
//            if (u instanceof Cliente c && c.getNombre().equalsIgnoreCase(nombresPasajeros.get(0))) {
//                clientePrincipal = c;
//                break;
//            }
//        }
//        if (clientePrincipal == null) {
//            throw new IllegalArgumentException("No se encontró el cliente principal.");
//        }
//        if (tipoAsiento == TipoAsiento.Ejecutivo && vueloSeleccionado.getAsientosMaxEjecutivo() < cantidadPasaje) {
//            throw new IllegalStateException("No hay suficientes asientos ejecutivos disponibles.");
//        }
//        if (tipoAsiento == TipoAsiento.Turista && vueloSeleccionado.getAsientosMaxTurista() < cantidadPasaje) {
//            throw new IllegalStateException("No hay suficientes asientos turista disponibles.");
//        }
//        // Crear la reserva
//        CompraComun reserva = new CompraComun(clientePrincipal, fechaReserva, tipoAsiento, equipajeExtra);
//        reserva.setVuelo(vueloSeleccionado);
//
//        // Crear y agregar los pasajes
//        for (String nombrePasajero : nombresPasajeros) {
//            Cliente pasajero = null;
//            for (Usuario u : usuarios) {
//                if (u instanceof Cliente c && c.getNombre().equalsIgnoreCase(nombrePasajero)) {
//                    pasajero = c;
//                    break;
//                }
//            }
//            if (pasajero == null) {
//                throw new IllegalArgumentException("No se encontró el pasajero: " + nombrePasajero);
//            }
//            Pasaje pasaje = new Pasaje(pasajero, reserva, tipoAsiento);;
//            // Agregar el pasaje a la reserva y al pasajero
//            reserva.getPasajeros().add(pasaje);
//            pasajero.getReservas().add(reserva);
//        }
//        CostoBase costoBase;
//        if (tipoAsiento == TipoAsiento.Ejecutivo) {
//            costoBase = new CostoBase(vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoTurista(), vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEjecutivo(), equipajeExtra * vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEquipajeExtra());
//            float costoTotal = (vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEjecutivo() * cantidadPasaje) + (equipajeExtra * vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEquipajeExtra());
//            reserva.setCostoReserva(costoBase);
//            reserva.setCostoTotal(costoTotal);
//        } else {
//            costoBase = new CostoBase(vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoTurista(), vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEjecutivo(), equipajeExtra * vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEquipajeExtra());
//            float costoTotal = (vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoTurista() * cantidadPasaje) + (equipajeExtra * vueloSeleccionado.getRutaVuelo().get(0).getCostoBase().getCostoEquipajeExtra());
//            reserva.setCostoReserva(costoBase);
//            reserva.setCostoTotal(costoTotal);
//        }
//
//        // Agregar la reserva al vuelo
//        vueloSeleccionado.getReserva().add(reserva);
//
//    }
//
//
/*
//    public List<DTRutaVuelo> listarRutaVueloDeVuelo(){
//        List<DTRutaVuelo> listaRutas = new ArrayList<>();
//        for (Usuario u : usuarios) {
//            if (u instanceof Aerolinea a) {
//                for (RutaVuelo r : a.getRutasVuelo()) {
//                    listaRutas.add(new DTRutaVuelo(
//                            r.getNombre(),
//                            r.getDescripcion(),
//                            r.getFechaAlta(),
//                            r.getCostoBase(),
//                            new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>()),
//                            new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
//                            new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
//                    ));
//                }
//            }
//        }
//        return listaRutas;
//    }


    // CREAR PAQUETE VUELO
    public void crearPaquete(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta, TipoAsiento tipoAsiento) {
        for (PaqueteVuelo p : paqueteVuelos) {
            if (p.getNombre().equalsIgnoreCase(nombrePaquete)) {
                throw new IllegalArgumentException("El nombre del paquete ya existe.");
            }
        }
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
        }
        if (diasValidos <= 0) {
            throw new IllegalArgumentException("Los días válidos deben ser mayores a 0.");
        }

        try {
            PaqueteVuelo paqueteVuelo = new PaqueteVuelo(nombrePaquete, descripcion, diasValidos, descuento, fechaAlta, tipoAsiento);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear el paquete de vuelo: " + e.getMessage());
        }
    }

    public List<DTPaqueteVuelos> mostrarPaquete() {
        List<DTPaqueteVuelos> listaPaquetes = new ArrayList<>();
        for (PaqueteVuelo p : paqueteVuelos) {
            DTPaqueteVuelos dtPaquete = new DTPaqueteVuelos(
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getTipoAsiento(),
                    p.getDiasValidos(),
                    p.getDescuento(),
                    p.getFechaAlta()
            );
            listaPaquetes.add(dtPaquete); // Add the created object to the list
        }
        return listaPaquetes;
    }

    public void seleccionarPaquete(String nombrePaquete) {
        for (PaqueteVuelo p : paqueteVuelos) {
            if (p.getNombre().equalsIgnoreCase(nombrePaquete)) {
                paqueteSeleccionado = p;
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró un paquete con el nombre: " + nombrePaquete);
    }

    public List<DTCliente> mostrarClientes() {
        List<DTCliente> listaClientes = new ArrayList<>();
        for (Usuario u : usuarios) {
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



    public void seleccionarCliente(String nombreCliente) {
        for (Usuario u : usuarios) {
            if (u instanceof Cliente c && c.getNombre().equalsIgnoreCase(nombreCliente)) {
                clienteSeleccionado = c;
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró un cliente con el nombre: " + nombreCliente);
    }

    public void realizarCompra(DTFecha fechaCompra, int costo, DTFecha vencimiento, TipoAsiento tipoAsiento) {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de realizar la compra.");
        }
        if (clienteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un cliente antes de realizar la compra.");
        }
        if (costo < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }
        CompraPaquete compraPaquete = new CompraPaquete(clienteSeleccionado, fechaCompra, vencimiento, tipoAsiento);
        // crear costo base
        DTCostoBase costoBase = new DTCostoBase(0, 0, 0);
        compraPaquete.setCostoTotal(costo); // falta saber bien como se calcula el costo de un paquete.
        clienteSeleccionado.agregarReserva(compraPaquete);
        clienteSeleccionado.incrementarCantidadPaquetes();


    }

    public void agregarRutaAPaquete (RutaVuelo rutaSeleccionada, int cant) {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de agregar una ruta.");
        }
        if (rutaSeleccionada == null) {
            throw new IllegalArgumentException("La ruta seleccionada no puede ser nula.");
        }
        if (paqueteSeleccionado.getRutas().contains(rutaSeleccionada)) {
            throw new IllegalArgumentException("La ruta ya está agregada al paquete.");
        }
        paqueteSeleccionado.getRutas().add(rutaSeleccionada);
        paqueteSeleccionado.sumarCantidad(rutaSeleccionada, cant);
    }

    // consulta paquete vuelo

    public DTPaqueteVuelos consultaPaqueteVuelo (PaqueteVuelo paqueteSeleccionado) {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de consultar.");
        }

        // Verificar que no exista un vuelo con el mismo nombre
        for (Vuelo v : vuelos) {
            if (v.getNombre().equalsIgnoreCase(recordarDatosVuelo.getNombre())) {
                throw new IllegalStateException("Ya existe un vuelo con ese nombre.");
            }
        }
        RutaVuelo ruta = null;
        for (RutaVuelo r : aerolineaSeleccionada.getRutasVuelo()) {
            if (r.getNombre().equalsIgnoreCase(recordarDatosVuelo.getRuta().getNombre())) {
                ruta = r;
                break;
            }
        }
        if (ruta == null) {
            throw new IllegalStateException("No se encontró la ruta asociada al vuelo.");
        }
        Vuelo nuevoVuelo = new Vuelo(
                recordarDatosVuelo.getNombre(),
                recordarDatosVuelo.getFechaVuelo(),
                recordarDatosVuelo.getHoraVuelo(),
                recordarDatosVuelo.getDuracion(),
                recordarDatosVuelo.getAsientosMaxTurista(),
                recordarDatosVuelo.getAsientosMaxEjecutivo(),
                recordarDatosVuelo.getFechaAlta()
        DTPaqueteVuelos dtPaquete = new DTPaqueteVuelos(
                paqueteSeleccionado.getNombre(),
                paqueteSeleccionado.getDescripcion(),
                paqueteSeleccionado.getTipoAsiento(),
                paqueteSeleccionado.getDiasValidos(),
                paqueteSeleccionado.getDescuento(),
                paqueteSeleccionado.getFechaAlta()
        );
        dtPaquete.setCantidad(paqueteSeleccionado.getCantidad());
        dtPaquete.setCostoTotal(paqueteSeleccionado.getCostoTotal());
        dtPaquete.setRutas(paqueteSeleccionado.getRutas());
        dtPaquete.setDTCostoBase(paqueteSeleccionado.getCostoBase());
        return dtPaquete;
    }


}
*/
}

