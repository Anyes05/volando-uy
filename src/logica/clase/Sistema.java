package logica.clase;

import logica.DataTypes.*;

import java.util.ArrayList;
import java.util.List;

public class Sistema implements ISistema {

    private static Sistema Instance = null;
    private List<PaqueteVuelo> paqueteVuelos;
    private List<Usuario> usuarios;
    private List<Ciudad> ciudades;
    private List<Vuelo> vuelos;
    private String recuerdaAerolinea; // Para recordar la aerolinea seleccionada
    private DTRutaVuelo recordarRutaVuelo; // Para recordar la ruta de vuelo seleccionada
    private Aerolinea aerolineaSeleccionada;
    private DTVuelo recordarDatosVuelo;
    private List<DTVuelo> listaDTVuelos;
    private String nicknameUsuarioAModificar;
    private List<Categoria> categorias = new ArrayList<>();

    private Sistema(){
        paqueteVuelos = new ArrayList<>();
        usuarios = new ArrayList<>();
        ciudades = new ArrayList<>();
        listaDTVuelos = new ArrayList<>();
        vuelos = new ArrayList<>();


    }
    public static Sistema getInstance(){
        if(Instance == null){
            Instance = new Sistema();
        }
        return Instance;
    }

    // ALTA USUARIO
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
<<<<<<< Updated upstream
    public List<DTUsuario> consultarUsuarios(){
        if (usuarios.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados.");
        }
=======
    public List<DTUsuario> consultarUsuarios() {
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();

>>>>>>> Stashed changes
        List<DTUsuario> lista = new ArrayList<>();
        for (Usuario u : usuarios) {
            lista.add(new DTUsuario (u.getNickname(), u.getNombre(), u.getCorreo()));
        }
        if (lista.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados.");
        }
        return lista;
    }

    public DTUsuario mostrarDatosUsuario(String nickname){
        for (Usuario u : usuarios) {
            if (u.getNickname().equals(nickname)) {
                if (u instanceof Cliente c) { // me falta también los paquetes que compró.
                    // Suponiendo que Reserva tiene un DTO llamado DTReserva
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
                    for (RutaVuelo rv : a.getRutasVuelo()) {
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

    //Si el administrador selecciona una ruta de vuelo o un vuelo reservado o un
    //paquete comprado, se muestra la información detallada, tal como se indica en
    //el caso de uso Consulta de Ruta de Vuelo, Consulta de Vuelo y Consulta
    //de Paquete de Rutas de Vuelo, respectivamente.
    // Lo que quiere decir que en realidad, no iría una función extra, sino que directamente uso las funciones para el caso respectivo,
    // esto se tendrá en cuenta una vez que integré las funcionalidades.

    // MODIFICAR DATOS DE USUARIO

    //El caso de uso comienza cuando el administrador desea modificar el
    //perfil de un usuario. Para ello el sistema muestra la lista de todos los
    //usuarios y el administrador elige uno.
    // Para esto ya me sirve:
    // public List<DTUsuario> consultarUsuarios()

    // Luego, el sistema muestra todos
    // los datos del usuario.
    // Podría usar mostrarDatosUsuario(String nickname), pero para no mostrar las reservas, compras de paquete, o rutas de vuelo:

<<<<<<< Updated upstream
=======
    //    // public List<DTUsuario> consultarUsuarios()
//
//    // Luego, el sistema muestra todos
//    // los datos del usuario.
//    // Podría usar mostrarDatosUsuario(String nickname), pero para no mostrar las reservas, compras de paquete, o rutas de vuelo:
//
>>>>>>> Stashed changes
    public DTUsuario mostrarDatosUsuarioMod(String nickname) {
        for (Usuario u : usuarios) {
            if (u.getNickname().equals(nickname)) {
                if (u instanceof Cliente c) {
                    return new DTCliente(
                            c.getNickname(),
                            c.getNombre(),
                            c.getCorreo(),
                            c.getApellido(),
                            c.getTipoDoc(),
                            c.getNumeroDocumento(),
                            c.getFechaNacimiento(),
                            c.getNacionalidad(),
                            new ArrayList<>()
                    );
                } else if (u instanceof Aerolinea a) {
                    return new DTAerolinea(
                            a.getNickname(),
                            a.getNombre(),
                            a.getCorreo(),
                            a.getDescripcion(),
                            a.getLinkSitioWeb(),
                            new ArrayList<>()
                    );
                }
            }
        }
        throw new IllegalArgumentException("Usuario no encontrado");
    }

    // El administrador puede editar todos los datos
    //básicos, menos el nickname y el correo electrónico. Cuando termina la
    //edición, el sistema actualiza los datos del usuario.
<<<<<<< Updated upstream
    public void seleccionarUsuarioAMod (String nickname) {
=======

    public void seleccionarUsuarioAMod(String nickname) {
>>>>>>> Stashed changes
        this.nicknameUsuarioAModificar = nickname;
    }

    // Para Cliente
    public void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        if (nicknameUsuarioAModificar == null) {
            throw new IllegalStateException("Debe seleccionar un usuario antes de modificar sus datos.");
        }
<<<<<<< Updated upstream
        for (Usuario u : usuarios) {
            if (u instanceof Cliente c && c.getNickname().equals(nicknameUsuarioAModificar)) {
                c.setNombre(nombre);
                c.setApellido(apellido);
                c.setFechaNacimiento(fechaNac);
                c.setNacionalidad(nacionalidad);
                c.setTipoDoc(tipoDocumento);
                c.setNumeroDocumento(numeroDocumento);
                return;
            }
=======
        ClienteServicio clienteServicio = new ClienteServicio();
        Cliente cliente = clienteServicio.buscarClientePorNickname(nicknameUsuarioAModificar);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
>>>>>>> Stashed changes
        }
        throw new IllegalArgumentException("Cliente no encontrado");
    }

    // Para Aerolinea
    public void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb) {
        if (nicknameUsuarioAModificar == null) {
            throw new IllegalStateException("Debe seleccionar un usuario antes de modificar sus datos.");
        }
<<<<<<< Updated upstream
        for (Usuario u : usuarios) {
            if (u instanceof Aerolinea a && a.getNickname().equals(nicknameUsuarioAModificar)) {
                a.setNombre(nombre);
                a.setDescripcion(descripcion);
                a.setLinkSitioWeb(linkSitioWeb);
                return;
            }
        }
        throw new IllegalArgumentException("Aerolínea no encontrada");
    }


    // ALTA RUTA VUELO
    public List<DTAerolinea> listarAerolineas(){
        List<DTAerolinea> listaAerolineas = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Aerolinea a) {
                listaAerolineas.add(new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>())); // Le pasé este último parametro de lista vacía porque necesitaba la lista para otro caso
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
        DTAerolinea aerolineaDT = new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>());
        // Crear la hora del vuelo
        //DTHora horaVuelo = new DTHora(hora.getHora(), hora.getMinutos());
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
        DTRutaVuelo DTrutaVuelo = new DTRutaVuelo( nombreRuta, descripcion, fechaAltaDT, costoBase, aerolineaDT, ciudadOrigenDT, ciudadDestinoDT);
        // Retornar el DTRutaVuelo creado
        recordarRutaVuelo = DTrutaVuelo; // Guardar la ruta de vuelo seleccionada
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
            if (recordarRutaVuelo == null) {
                throw new IllegalStateException("Debe ingresar los datos de la ruta antes de registrarla.");
            }
            RutaVuelo nuevaRuta = new RutaVuelo(
                    recordarRutaVuelo.getNombre(),
                    recordarRutaVuelo.getDescripcion(),
                    recordarRutaVuelo.getCostoBase()
            );
            nuevaRuta.setFechaAlta(recordarRutaVuelo.getFechaAlta());
            // Buscar las ciudades de origen y destino y asignarlas a la nueva ruta
            Ciudad ciudadOrigen = null;
            Ciudad ciudadDestino = null;
            for (Ciudad ciudad : ciudades) {
                if (ciudad.getNombre().equalsIgnoreCase(recordarRutaVuelo.getCiudadOrigen().getNombre())) {
                    ciudadOrigen = ciudad;
                }
                if (ciudad.getNombre().equalsIgnoreCase(recordarRutaVuelo.getCiudadDestino().getNombre())) {
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
            recordarRutaVuelo = null;
        }
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

    public void agregarCiudadParaTest(Ciudad c) {
        ciudades.add(c);
    }

    // ALTA DE VUELO
    public List<DTRutaVuelo> seleccionarAerolineaRet(String nickname){
        for (Usuario u : usuarios) {
            if (u instanceof Aerolinea a) {
                if (a.getNickname().equalsIgnoreCase(nickname)) {
                    this.aerolineaSeleccionada = a; // seleccion

                    List<DTRutaVuelo> listaRutas = new ArrayList<>();
                    for (RutaVuelo r : a.getRutasVuelo()) {
                        //paso las ciudades a DT porque los pide el DTRutaVuelo
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
        }
        throw new IllegalStateException("No se encontró una aerolínea con el nickname: " + nickname);
    }

    public DTRutaVuelo seleccionarRutaVueloRet(String nombre) {

        if (aerolineaSeleccionada == null) {
            throw new IllegalStateException("Debe seleccionar una aerolínea antes de seleccionar una ruta.");
        }
        for (RutaVuelo rv : aerolineaSeleccionada.getRutasVuelo()) {
            if (rv.getNombre().equalsIgnoreCase(nombre)) {
                DTRutaVuelo dtRuta = new DTRutaVuelo(
                        rv.getNombre(),
                        rv.getDescripcion(),
                        rv.getFechaAlta(),
                        rv.getCostoBase(),
                        new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>()),
                        new DTCiudad(rv.getCiudadOrigen().getNombre(), rv.getCiudadOrigen().getPais()),
                        new DTCiudad(rv.getCiudadDestino().getNombre(), rv.getCiudadDestino().getPais())
                );
                recordarRutaVuelo = dtRuta;
                return dtRuta;
            }
        }
        throw new IllegalStateException("No se encontró la ruta con el nombre: " + nombre);
    }


    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo, DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta, DTRutaVuelo ruta) {

        if (recordarRutaVuelo == null) {
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
=======
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


//    // ALTA RUTA VUELO
//    public List<DTAerolinea> listarAerolineas(){
//        List<DTAerolinea> listaAerolineas = new ArrayList<>();
//        for (Usuario u : usuarios) {
//            if (u instanceof Aerolinea a) {
//                listaAerolineas.add(new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>())); // Le pasé este último parametro de lista vacía porque necesitaba la lista para otro caso
//            }
//        }
//        return listaAerolineas;
//    }
//    public void seleccionarAerolinea(String nickname) {
//        nickname = nickname.toLowerCase();
//        for (Usuario u : usuarios) {    // Recorremos la lista de usuarios
//            // Verificamos si el usuario es una Aerolinea y si su nickname coincide con el proporcionado
//            if (u instanceof Aerolinea a && a.getNickname().toLowerCase().equals(nickname)) {
//                aerolineaSeleccionada = a;
//                recuerdaAerolinea = a.getNickname();
//                return;
//            }
//        }
//        aerolineaSeleccionada = null;
//    }
//
//    public DTRutaVuelo ingresarDatosRuta(String nombreRuta, String descripcion, DTHora hora, float costoTurista, float costoEjecutivo, float costoEquipajeExtra, String ciudadOrigen, String ciudadDestino, DTFecha fechaAlta, String categoria) {
//        // Verificar si la aerolinea ha sido seleccionada
//        if (recuerdaAerolinea == null) {
//            throw new IllegalStateException("Debe seleccionar una aerolinea antes de ingresar los datos de la ruta.");
//        }
//        // Buscar la aerolinea por su nickname
//        Aerolinea aerolineaSeleccionada = null;
//        for (Usuario u : usuarios) {
//            if (u instanceof Aerolinea a && a.getNickname().equals(recuerdaAerolinea)) {
//                aerolineaSeleccionada = a;
//                break;
//            }
//        }
//        if (aerolineaSeleccionada == null) {
//            throw new IllegalStateException("La aerolinea seleccionada no existe.");
//        }
//        //Crear DTAerolinea
//        DTAerolinea aerolineaDT = new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>());
//        // Crear la hora del vuelo
//        //DTHora horaVuelo = new DTHora(hora.getHora(), hora.getMinutos());
//        // Crear la fecha de alta
//        DTFecha fechaAltaDT = new DTFecha(fechaAlta.getDia(), fechaAlta.getMes(), fechaAlta.getAno());
//        CostoBase costoBase = new CostoBase(costoTurista, costoEjecutivo, costoEquipajeExtra);
//
//        // Crear los DTCiudad para origen y destino si existen
//        DTCiudad ciudadOrigenDT = null;
//        DTCiudad ciudadDestinoDT = null;
//        for (Ciudad ciudad : ciudades) {
//            if (ciudad.getNombre().equalsIgnoreCase(ciudadOrigen)) {
//                ciudadOrigenDT = new DTCiudad(ciudad.getNombre(), ciudad.getPais());
//            }
//            if (ciudad.getNombre().equalsIgnoreCase(ciudadDestino)) {
//                ciudadDestinoDT = new DTCiudad(ciudad.getNombre(), ciudad.getPais());
//            }
//        }
//        if (ciudadOrigenDT == null || ciudadDestinoDT == null) {
//            throw new IllegalArgumentException("Una de las ciudades no existe.");
//        }
//        // Crear la ruta de vuelo
//        DTRutaVuelo DTrutaVuelo = new DTRutaVuelo( nombreRuta, descripcion, fechaAltaDT, aerolineaDT, ciudadOrigenDT, ciudadDestinoDT);
//            RutaVuelo nuevaRuta = new RutaVuelo(
//                    nombreRuta,
    //                   descripcion,
//                    costoBase
    //           );
//        // Retornar el DTRutaVuelo creado
//        recordarRutaVuelo = DTrutaVuelo; // Guardar la ruta de vuelo seleccionada
//        return DTrutaVuelo;
//    }
//    public void registrarRuta() {
//        {
//            // Verificar si la aerolinea ha sido seleccionada
//            if (recuerdaAerolinea == null) {
//                throw new IllegalStateException("Debe seleccionar una aerolinea antes de registrar la ruta.");
//            }
//            // Buscar la aerolinea por su nickname
//            Aerolinea aerolineaSeleccionada = null;
//            for (Usuario u : usuarios) {
//                if (u instanceof Aerolinea a && a.getNickname().equals(recuerdaAerolinea)) {
//                    aerolineaSeleccionada = a;
//                    break;
//                }
//            }
//            if (aerolineaSeleccionada == null) {
//                throw new IllegalStateException("La aerolinea seleccionada no existe.");
//            }
//            // Crear la ruta de vuelo
//            if (recordarRutaVuelo == null) {
//                throw new IllegalStateException("Debe ingresar los datos de la ruta antes de registrarla.");
//            }
//            RutaVuelo nuevaRuta = new RutaVuelo(
//                    recordarRutaVuelo.getNombre(),
//                    recordarRutaVuelo.getDescripcion(),
//                    recordarRutaVuelo.getCostoBase()
//            );
//            nuevaRuta.setFechaAlta(recordarRutaVuelo.getFechaAlta());
//            // Buscar las ciudades de origen y destino y asignarlas a la nueva ruta
//            Ciudad ciudadOrigen = null;
//            Ciudad ciudadDestino = null;
//            for (Ciudad ciudad : ciudades) {
//                if (ciudad.getNombre().equalsIgnoreCase(recordarRutaVuelo.getCiudadOrigen().getNombre())) {
//                    ciudadOrigen = ciudad;
//                }
//                if (ciudad.getNombre().equalsIgnoreCase(recordarRutaVuelo.getCiudadDestino().getNombre())) {
//                    ciudadDestino = ciudad;
//                }
//            }
//            if (ciudadOrigen == null || ciudadDestino == null) {
//                throw new IllegalArgumentException("Una de las ciudades no existe.");
//            }
//            nuevaRuta.setCiudadOrigen(ciudadOrigen);
//            nuevaRuta.setCiudadDestino(ciudadDestino);
//            // Agregar la nueva ruta a la lista de rutas de la aerolinea
//            aerolineaSeleccionada.getRutasVuelo().add(nuevaRuta);
//            // Limpiar las variables de selección
//            recuerdaAerolinea = null;
//            recordarRutaVuelo = null;
//        }
//    }
//
//    // CONSULTA RUTA VUELO
//    public List<DTRutaVuelo> listarRutaVuelo(String nombreAerolinea) {
//        List<DTRutaVuelo> listaRutas = new ArrayList<>();
//        for (Usuario u : usuarios) {
//            if (u instanceof Aerolinea a && a.getNickname().equalsIgnoreCase(nombreAerolinea)) {
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
//                return listaRutas;
//            }
//        }
//        throw new IllegalArgumentException("No se encontró una aerolínea con el nickname: " + nombreAerolinea);
//    }
//
//
//    public List<DTPaqueteVuelos> mostrarPaquete(){
//        return null;
//    }
//    public void seleccionarPaquete(String nombrePaquete){
//    }
//    public List<DTCliente> mostrarClientes(){
//        return null;
//    }
//    public void seleccionarCliente(String nombreCliente){
//
//    }
//    public void relizarCompra(DTFecha fechaCompra, int costo, DTFecha vencimineto){
//
//    }
//
//    public void crearPaquete(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta){
//
//    }
//
//    public void agregarCiudadParaTest(Ciudad c) {
//        ciudades.add(c);
//    }
//
//    // ALTA DE VUELO
//    public List<DTRutaVuelo> seleccionarAerolineaRet(String nickname){
//        for (Usuario u : usuarios) {
//            if (u instanceof Aerolinea a) {
//                if (a.getNickname().equalsIgnoreCase(nickname)) {
//                    this.aerolineaSeleccionada = a; // seleccion
//
//                    List<DTRutaVuelo> listaRutas = new ArrayList<>();
//                    for (RutaVuelo r : a.getRutasVuelo()) {
//                        //paso las ciudades a DT porque los pide el DTRutaVuelo
//                        listaRutas.add(new DTRutaVuelo(
//                                r.getNombre(),
//                                r.getDescripcion(),
//                                r.getFechaAlta(),
//                                r.getCostoBase(),
//                                new DTAerolinea(a.getNickname(), a.getNombre(), a.getCorreo(), a.getDescripcion(), a.getLinkSitioWeb(), new ArrayList<>()),
//                                new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
//                                new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
//                        ));
//
//                    }
//                    return listaRutas;
//                }
//            }
//        }
//        throw new IllegalStateException("No se encontró una aerolínea con el nickname: " + nickname);
//    }
//
//    public DTRutaVuelo seleccionarRutaVueloRet(String nombre) {
//
//        if (aerolineaSeleccionada == null) {
//            throw new IllegalStateException("Debe seleccionar una aerolínea antes de seleccionar una ruta.");
//        }
//        for (RutaVuelo rv : aerolineaSeleccionada.getRutasVuelo()) {
//            if (rv.getNombre().equalsIgnoreCase(nombre)) {
    //               recordarRutaVuelo = rv;
//                DTRutaVuelo dtRuta = new DTRutaVuelo(
//                        rv.getNombre(),
//                        rv.getDescripcion(),
//                        rv.getFechaAlta(),
//                        rv.getCostoBase(),
//                        new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>()),
//                        new DTCiudad(rv.getCiudadOrigen().getNombre(), rv.getCiudadOrigen().getPais()),
//                        new DTCiudad(rv.getCiudadDestino().getNombre(), rv.getCiudadDestino().getPais())
//
//                );
//                return dtRuta;
//            }
//        }
//        throw new IllegalStateException("No se encontró la ruta con el nombre: " + nombre);
//    }
//
//
//    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo, DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta, DTRutaVuelo ruta) {
//
//        if (ruta == null) { //recordarRutaVuelo==NULL
//            throw new IllegalStateException("Debe seleccionar una ruta antes de ingresar los datos del vuelo.");
//        }
//
//        DTVuelo dtVuelo = new DTVuelo(duracion, nombre, fecha, horaVuelo, maxEjecutivo, fechaAlta, maxTurista, ruta);
//        recordarDatosVuelo = dtVuelo;
//        return dtVuelo;
//    }
//
//
//
//    public void darAltaVuelo() {
//        if (recordarDatosVuelo == null) {
//            throw new IllegalStateException("Debe ingresar los datos del vuelo antes de registrarlo.");
//        }
//
//        // Verificar que no exista un vuelo con el mismo nombre
//        for (Vuelo v : vuelos) {
//            if (v.getNombre().equalsIgnoreCase(recordarDatosVuelo.getNombre())) {
//                throw new IllegalStateException("Ya existe un vuelo con ese nombre.");
//            }
//        }
//        RutaVuelo ruta = null;
//        for (RutaVuelo r : aerolineaSeleccionada.getRutasVuelo()) {
//            if (r.getNombre().equalsIgnoreCase(recordarDatosVuelo.getRuta().getNombre())) {
//                ruta = r;
//                break;
//            }
//        }
//        if (ruta == null) {
//            throw new IllegalStateException("No se encontró la ruta asociada al vuelo.");
//        }
//        Vuelo nuevoVuelo = new Vuelo(
//                recordarDatosVuelo.getNombre(),
//                recordarDatosVuelo.getFechaVuelo(),
//                recordarDatosVuelo.getHoraVuelo(),
//                recordarDatosVuelo.getDuracion(),
//                recordarDatosVuelo.getAsientosMaxEjecutivo(),
//                recordarDatosVuelo.getAsientosMaxTurista(),
//                recordarDatosVuelo.getFechaAlta()
//        );
//
//        vuelos.add(nuevoVuelo);
//        nuevoVuelo.getRutaVuelo().add(ruta);
//
//        recordarDatosVuelo = null;
//    }
//
//    //CONSULTA VUELO
//
//    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo){
//        listaDTVuelos.clear();
//        for (Vuelo v : vuelos) {
//            for (RutaVuelo r : v.getRutaVuelo()) {
//                if (r.getNombre().equalsIgnoreCase(nombreRutaVuelo)) {
//                    DTRutaVuelo dtRuta = new DTRutaVuelo(
//                            r.getNombre(),
//                            r.getDescripcion(),
//                            r.getFechaAlta(),
//                            r.getCostoBase(),
//                            new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>()),
//                            new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
//                            new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
//                    );
//                    DTVuelo dtVuelo = new DTVuelo(v.getDuracion(), v.getNombre(), v.getFechaVuelo(), v.getHoraVuelo(), v.getAsientosMaxEjecutivo(), v.getFechaAlta(), v.getAsientosMaxTurista(), dtRuta);
//
//                    listaDTVuelos.add(dtVuelo);
//                }
//            }
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
//        // Recorremos la lista de vuelos para tomar las reservas
//        for (Vuelo v : vuelos) {
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
//
//    // ALTA CATEGORIA
//    public void altaCategoria(String nombre) {
//        if (nombre == null || nombre.isEmpty()) {
//            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío.");
//        }
//        for (Categoria c : categorias) {
//            if (c.getNombre().equalsIgnoreCase(nombre)) {
//                throw new IllegalArgumentException("La categoría ya existe.");
//            }
//        }
//        Categoria nuevaCategoria = new Categoria(nombre);
//        categorias.add(nuevaCategoria);
//    }
//    public List<Categoria> getCategorias() {
//        return categorias;
//    }
//
//
//    // ALTA CIUDAD
//    public void altaCiudad(String nombre, String pais, Aeropuerto aeropuerto, DTFecha fechaAlta) {
//        // Verificar si el par (nombre, pais) ya existe
//        for (Ciudad c : ciudades) {
//            if (c.getNombre().equalsIgnoreCase(nombre) && c.getPais().equalsIgnoreCase(pais)) {
//                throw new IllegalArgumentException("La ciudad ya existe.");
//            }
//        }
//        // Crear la nueva ciudad
//        Ciudad nuevaCiudad = new Ciudad(nombre, pais, fechaAlta);
//        // Agregar la ciudad a la lista de ciudades
//        ciudades.add(nuevaCiudad);
//        // Si la ciudad tiene un aeropuerto, agregarlo a la lista de aeropuertos de la ciudad
//        if (aeropuerto != null) {
//            nuevaCiudad.getAeropuertos().add(aeropuerto);
//        }
//
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
//
//    public List<DTRutaVuelo> listarRutaVuelo(){
//        return null;
//    }
//
//
//
//    public DTPaqueteVueloRutaVuelo imprimirPaqueteVuelo(String nombre){
//        return null;
//    }
//
//    public void seleccionarCliente(){
//
//    }

    /*
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
*/
        public void seleccionarPaquete(String nombrePaquete) {
            for (PaqueteVuelo p : paqueteVuelos) {
                if (p.getNombre().equalsIgnoreCase(nombrePaquete)) {
                    paqueteSeleccionado = p;
                    return;
                }
            }
            throw new IllegalArgumentException("No se encontró un paquete con el nombre: " + nombrePaquete);
        }
/*
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
            */
    public DTPaqueteVuelos consultaPaqueteVuelo(PaqueteVuelo paqueteSeleccionado) {
        if (paqueteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un paquete antes de consultar.");
        }
    return null;
    }/*
<<<<<<< HEAD
>>>>>>> Stashed changes

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
                recordarDatosVuelo.getAsientosMaxEjecutivo(),
                recordarDatosVuelo.getAsientosMaxTurista(),
                recordarDatosVuelo.getFechaAlta()
        );

        vuelos.add(nuevoVuelo);
        nuevoVuelo.getRutaVuelo().add(ruta);

        recordarDatosVuelo = null;
    }

    //CONSULTA VUELO

    public List<DTVuelo> seleccionarRutaVuelo(String nombreRutaVuelo){
        listaDTVuelos.clear();
        for (Vuelo v : vuelos) {
            for (RutaVuelo r : v.getRutaVuelo()) {
                if (r.getNombre().equalsIgnoreCase(nombreRutaVuelo)) {
                    DTRutaVuelo dtRuta = new DTRutaVuelo(
                            r.getNombre(),
                            r.getDescripcion(),
                            r.getFechaAlta(),
                            r.getCostoBase(),
                            new DTAerolinea(aerolineaSeleccionada.getNickname(), aerolineaSeleccionada.getNombre(), aerolineaSeleccionada.getCorreo(), aerolineaSeleccionada.getDescripcion(), aerolineaSeleccionada.getLinkSitioWeb(), new ArrayList<>()),
                            new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
                            new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
                    );
                    DTVuelo dtVuelo = new DTVuelo(v.getDuracion(), v.getNombre(), v.getFechaVuelo(), v.getHoraVuelo(), v.getAsientosMaxEjecutivo(), v.getFechaAlta(), v.getAsientosMaxTurista(), dtRuta);

                    listaDTVuelos.add(dtVuelo);
                }
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

        // Recorremos la lista de vuelos para tomar las reservas
        for (Vuelo v : vuelos) {
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

    // ALTA CATEGORIA
    public void altaCategoria(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío.");
        }
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("La categoría ya existe.");
            }
        }
        Categoria nuevaCategoria = new Categoria(nombre);
        categorias.add(nuevaCategoria);
    }

    // ALTA CIUDAD
    public void altaCiudad(String nombre, String pais, Aeropuerto aeropuerto, DTFecha fechaAlta) {
        // Verificar si el par (nombre, pais) ya existe
        for (Ciudad c : ciudades) {
            if (c.getNombre().equalsIgnoreCase(nombre) && c.getPais().equalsIgnoreCase(pais)) {
                throw new IllegalArgumentException("La ciudad ya existe.");
            }
        }
        // Crear la nueva ciudad
        Ciudad nuevaCiudad = new Ciudad(nombre, pais, fechaAlta);
        // Agregar la ciudad a la lista de ciudades
        ciudades.add(nuevaCiudad);
        // Si la ciudad tiene un aeropuerto, agregarlo a la lista de aeropuertos de la ciudad
        if (aeropuerto != null) {
            nuevaCiudad.getAeropuertos().add(aeropuerto);
        }

    }

    public List<DTRutaVuelo> listarRutaVuelo(){
        return null;
    }


<<<<<<< Updated upstream
=======
}
*/
    }

>>>>>>> Stashed changes

    public DTPaqueteVueloRutaVuelo imprimirPaqueteVuelo(String nombre){
        return null;
    }

    public void seleccionarCliente(){

    }
    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva){

    }
    public void AltaReservaVuelo(int costo){

    }
}
