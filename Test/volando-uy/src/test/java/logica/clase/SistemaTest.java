package logica.clase;
import dato.entidades.*;
import logica.DataTypes.*;
import logica.clase.Sistema;


import logica.servicios.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


//NOTAS: Modifique validarContrasena
class SistemaTest {
    //LIMPIAR BD
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    static void initEMF() {
        emf = Persistence.createEntityManagerFactory("volandouyPU");
    }

    @BeforeEach
    void limpiarBasePostgres() {
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createQuery("DELETE FROM CompraPaquete").executeUpdate();
        em.createQuery("DELETE FROM Pasaje").executeUpdate();
        em.createQuery("DELETE FROM Reserva").executeUpdate();
        em.createQuery("DELETE FROM CompraComun").executeUpdate();
        em.createQuery("DELETE FROM PaqueteVuelo").executeUpdate();
        em.createQuery("DELETE FROM Cantidad").executeUpdate();
        em.createQuery("DELETE FROM Vuelo").executeUpdate();       // primero vuelos
        em.createQuery("DELETE FROM RutaVuelo").executeUpdate();   // luego rutas
        em.createQuery("DELETE FROM Categoria").executeUpdate();
        em.createQuery("DELETE FROM Aeropuerto").executeUpdate();
        em.createQuery("DELETE FROM Ciudad").executeUpdate();
        em.createQuery("DELETE FROM Aerolinea").executeUpdate();
        em.createQuery("DELETE FROM Cliente").executeUpdate();
        em.createQuery("DELETE FROM Usuario").executeUpdate();

        tx.commit();
        em.close();
    }


    //CASOS DE USO

    //ALTASUSUARIO
    //AltaCliente
    @Test
    void testAltaCliente() {
        Sistema s = Sistema.getInstance();

        assertDoesNotThrow(() -> s.altaCliente("baseNick", "Juan", "base@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCBASE", new byte[]{1}, "Abcdef1")); // válido
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("baseNick", "Otro", "otro@mail.com", "López", new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOC123", new byte[]{1}, "Abcdef1")); // nickname duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNuevo", "Otro", "base@mail.com", "López", new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOC124", new byte[]{1}, "Abcdef1")); // correo duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNuevo2", "Otro", "otro2@mail.com", "López", new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOCBASE", new byte[]{1}, "Abcdef1")); // documento duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente(null, "Juan", "nicknull@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCNICKNULL", new byte[]{1}, "Abcdef1")); // nickname null
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNomCorto", "Jo", "nomcorto@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCNOMCORTO", new byte[]{1}, "Abcdef1")); // nombre corto
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickApeEsp", "Juan", "apeesp@mail.com", "   ", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCAPEESP", new byte[]{1}, "Abcdef1")); // apellido espacios
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNacNull", "Juan", "nacnull@mail.com", "Pérez", new DTFecha(1,1,1990), null, TipoDoc.CI, "DOCNACNULL", new byte[]{1}, "Abcdef1")); // nacionalidad null
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickFotoNull", "Juan", "foto@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCFOTO", null, "Abcdef1")); // foto nula
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickPassInvalida", "Juan", "pass@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCPASS", new byte[]{1}, "abc")); // contraseña inválida
    }
    //AltaAerolinea
    @Test
    void testAltaAerolinea() {
        Sistema s = Sistema.getInstance();

        assertDoesNotThrow(() -> s.altaAerolinea("aeroNick", "Aerolíneas", "aero@mail.com", "Empresa aérea uruguaya", "https://aerolineas.com", new byte[]{1}, "Abcdef1")); // valido (fallaba por tildes)
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("aeroNick", "Otra", "otra@mail.com", "Otra descripción", "https://otra.com", new byte[]{1}, "Abcdef1")); // nickname duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("aeroNueva", "Otra", "aero@mail.com", "Otra descripción", "https://otra.com", new byte[]{1}, "Abcdef1")); // correo duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea(null, "Aerolíneas", "nicknull@mail.com", "Empresa aérea", "https://a.com", new byte[]{1}, "Abcdef1")); // nickname null
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickNomCorto", "Ae", "nomcorto@mail.com", "Empresa aérea", "https://a.com", new byte[]{1}, "Abcdef1")); // nombre corto
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickDescEsp", "Aerolíneas", "descesp@mail.com", "   ", "https://a.com", new byte[]{1}, "Abcdef1")); // descripcion invalida
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickFotoNull", "Aerolíneas", "foto@mail.com", "Empresa aérea", "https://a.com", null, "Abcdef1")); // foto nula
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickPassInv", "Aerolíneas", "pass@mail.com", "Empresa aérea", "https://a.com", new byte[]{1}, "abc")); // contrasena invalida
    }

    //CONSULTAUSUARIO
    @Test
    void testConsultarUsuarios() {
        Sistema s = Sistema.getInstance();
        assertThrows(IllegalStateException.class, () -> s.consultarUsuarios()); // lista vacía
        s.altaCliente("nickCliente", "Juan", "cliente@mail.com", "Perez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123", new byte[]{1}, "Abcdef1"); // alta cliente
        s.altaAerolinea("nickAero", "Aerolineas", "aero@mail.com", "Empresa aerea", "https://a.com", new byte[]{1}, "Abcdef1"); // alta aerolinea
        List<DTUsuario> usuarios = assertDoesNotThrow(() -> s.consultarUsuarios()); // lista con usuarios
        assertEquals(2, usuarios.size()); // contiene cliente y aerolinea
    }

    //MODIFICAR USUARIO
    //Modificar Cliente
    @Test
    void testModificarDatosCliente(){
        Sistema s=Sistema.getInstance();
        s.seleccionarUsuarioAMod(null);
        assertThrows(IllegalStateException.class,()->s.modificarDatosCliente("Juan","Perez",new DTFecha(1,1,1990),"Uruguayo",TipoDoc.CI,"DOC123")); //sin usuario seleccionado
        s.seleccionarUsuarioAMod("inexistente");
        assertThrows(IllegalArgumentException.class,()->s.modificarDatosCliente("Juan","Perez",new DTFecha(1,1,1990),"Uruguayo",TipoDoc.CI,"DOC123")); //cliente no encontrado
        s.altaCliente("nickCliente","Pedro","cliente@mail.com","Lopez",new DTFecha(2,2,1992),"Uruguayo",TipoDoc.CI,"DOC999",new byte[]{1},"Abcdef1"); //alta cliente
        s.seleccionarUsuarioAMod("nickCliente");
        assertDoesNotThrow(()->s.modificarDatosCliente("Juan","Perez",new DTFecha(1,1,1990),"Argentino",TipoDoc.PASAPORTE,"DOC123")); //modificacion valida
    }
    //Modificar Aerolinea
    @Test
    void testModificarDatosAerolinea(){
        Sistema s=Sistema.getInstance();
        s.seleccionarUsuarioAMod(null);
        assertThrows(IllegalStateException.class,()->s.modificarDatosAerolinea("NuevaNombre","NuevaDesc","https://nuevo.com")); //sin usuario seleccionado
        s.seleccionarUsuarioAMod("inexistente");
        assertThrows(IllegalArgumentException.class,()->s.modificarDatosAerolinea("NuevaNombre","NuevaDesc","https://nuevo.com")); //aerolinea no encontrada
        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","Empresa aerea","https://a.com",new byte[]{1},"Abcdef1"); //alta aerolinea
        s.seleccionarUsuarioAMod("nickAero");
        assertDoesNotThrow(()->s.modificarDatosAerolinea("NuevaNombre","NuevaDesc","https://nuevo.com")); //modificacion valida
    }
    //ALTA RUTA DE VUELO
    //IngresarDatosRuta
    @Test
    void testIngresarDatosRuta(){
        Sistema s=Sistema.getInstance();
        CiudadServicio ciudadServicio=new CiudadServicio();
        CategoriaServicio categoriaServicio=new CategoriaServicio();
        ciudadServicio.registrarCiudad("Montevideo","Uruguay","AeropuertoMVD_T","DescMVD","https://mvd.com",new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("BuenosAires","Argentina","AeropuertoBUE_T","DescBUE","https://bue.com",new DTFecha(1,1,2024));
        categoriaServicio.registrarCategoria("CategoriaT");

        //aerolínea no seleccionada
        assertThrows(IllegalStateException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));

        //alta y selección de aerolínea válida
        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","EmpresaAerea","https://a.com",new byte[]{1},"Abcdef1");
        s.seleccionarAerolinea("nickAero");

        //validaciones
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",-10,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"CiudadX","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","CiudadY",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaX"),new byte[]{1}));

        //caso válido
        assertDoesNotThrow(()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
    }

    @Test
    void testRegistrarRuta(){
        Sistema s=Sistema.getInstance();
        CiudadServicio ciudadServicio=new CiudadServicio();
        CategoriaServicio categoriaServicio=new CategoriaServicio();
        ciudadServicio.registrarCiudad("Montevideo","Uruguay","AeropuertoMVD_T","DescMVD","https://mvd.com",new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("BuenosAires","Argentina","AeropuertoBUE_T","DescBUE","https://bue.com",new DTFecha(1,1,2024));
        categoriaServicio.registrarCategoria("CategoriaT");

        assertThrows(IllegalStateException.class,()->s.registrarRuta());

        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","EmpresaAerea","https://a.com",new byte[]{1},"Abcdef1");
        s.seleccionarAerolinea("nickAero");
        assertThrows(IllegalStateException.class,()->s.registrarRuta());

        s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1});
        assertDoesNotThrow(()->s.registrarRuta());
    }

    //CONSULTAR RUTA DE VUELO

    @Test
    void testListarRutaVuelo() throws Exception {
        Sistema s = Sistema.getInstance();

        // 1. Alta y selección de aerolínea
        s.altaAerolinea("aeroTestListar","LineaAereaTest","testlistar@air.com",
                "Descripcion aerolinea valida","https://testlistar.com",new byte[]{1},"Clave123");
        s.seleccionarAerolineaRet("aeroTestListar");

        // 2. Ciudades válidas
        CiudadServicio ciudadServicio = new CiudadServicio();
        ciudadServicio.registrarCiudad("CiudadOrigenListar","Uruguay","AeropuertoOrigenListar",
                "Descripcion ciudad origen listar","https://ciudadorigenlistar.com",new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("CiudadDestinoListar","Argentina","AeropuertoDestinoListar",
                "Descripcion ciudad destino listar","https://ciudaddestinolist.com",new DTFecha(2,2,2024));

        // 3. Categoría
        s.altaCategoria("CategoriaListar");

        // 4. Ingresar y registrar ruta bajo la aerolínea seleccionada
        s.ingresarDatosRuta("RutaListar","Descripcion de ruta listar valida",100,200,50,
                "CiudadOrigenListar","CiudadDestinoListar",
                new DTFecha(1,1,2025),List.of("CategoriaListar"),new byte[]{1});
        s.seleccionarAerolineaRet("aeroTestListar");
        s.registrarRuta();

        // 5. Confirmar ruta usando el flujo oficial de administración
        s.seleccionarAerolineaParaAdministracion("aeroTestListar");
        s.seleccionarRutaVueloParaAdministracion("RutaListar");
        try {
            s.aceptarRutaVuelo();
        } catch (IllegalStateException e) {
            // aceptarRutaVuelo lanza excepción con mensaje SUCCESS, la ignoramos si es ese caso
            if (!e.getMessage().startsWith("SUCCESS")) {
                throw e;
            }
        }

        // 6. Ejecutar listarRutaVuelo
        List<DTRutaVuelo> rutas = s.listarRutaVuelo("aeroTestListar");

        // 7. Validar
        assertEquals(1, rutas.size());
        assertEquals("RutaListar", rutas.get(0).getNombre());
        assertEquals("CONFIRMADA", rutas.get(0).getEstado().name());
    }



    //ALTA VUELO
    @Test
    void testAltaVuelo() {
        Sistema s = Sistema.getInstance();

        // 1. Alta de aerolínea
        s.altaAerolinea("aeroTest", "TestAir", "test@air.com", "Desc",
                "https://test.com", new byte[]{1}, "Clave123");

        // 2. Seleccionar aerolínea
        s.seleccionarAerolineaRet("aeroTest");

        // 3. Alta de ciudades con nombres únicos y válidos
        CiudadServicio ciudadServicio = new CiudadServicio();
        ciudadServicio.registrarCiudad("MontevideoTest", "Uruguay", "AeropuertoMontevideo",
                "Descripcion ciudad 1", "https://ciudad1.com", new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("BuenosAiresTest", "Argentina", "AeropuertoBuenosAires",
                "Descripcion ciudad 2", "https://ciudad2.com", new DTFecha(2,2,2024));

        // 4. Alta de categoría
        s.altaCategoria("CategoriaX");

        // 5. Alta de ruta (usando los nombres de ciudades únicos)
        s.ingresarDatosRuta("RutaDummy", "Desc ruta", 100, 200, 50,
                "MontevideoTest", "BuenosAiresTest",
                new DTFecha(1,1,2025), List.of("CategoriaX"), new byte[]{1});
        s.registrarRuta();

        // Confirmar la ruta
        RutaVueloServicio rutaServicio = new RutaVueloServicio();
        RutaVuelo ruta = rutaServicio.buscarRutaVueloPorNombre("RutaDummy");
        ruta.setEstado(EstadoRutaVuelo.CONFIRMADA);

        // 6. Ingresar datos del vuelo
        DTRutaVuelo dtRuta = new DTRutaVuelo(
                ruta.getNombre(), ruta.getDescripcion(), ruta.getFechaAlta(),
                ruta.getCostoBase(), null,
                new DTCiudad(ruta.getCiudadOrigen().getNombre(), ruta.getCiudadOrigen().getPais()),
                new DTCiudad(ruta.getCiudadDestino().getNombre(), ruta.getCiudadDestino().getPais()),
                null, ruta.getEstado()
        );

        DTVuelo dtVuelo = s.ingresarDatosVuelo(
                "VueloDummy",
                new DTFecha(10,10,2025),
                new DTHora(12,30),
                new DTHora(2,0),
                100, 20,
                new DTFecha(9,9,2025),
                dtRuta,
                new byte[]{1}
        );

        assertNotNull(dtVuelo);
        assertEquals("VueloDummy", dtVuelo.getNombre());

        // 7. Dar de alta el vuelo
        s.seleccionarAerolineaRet("aeroTest"); // <-- reforzar selección
        s.darAltaVuelo();

        // 8. Verificar que el vuelo quedó registrado
        VueloServicio vueloServicio = new VueloServicio();
        Vuelo vueloPersistido = vueloServicio.buscarVueloPorNombre("VueloDummy");

        assertNotNull(vueloPersistido);
        assertEquals("VueloDummy", vueloPersistido.getNombre());
        assertEquals("RutaDummy", vueloPersistido.getRutaVuelo().getNombre());
    }

    //RESERVA
    @Test
    void testDatoReserva() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Setup base ---
        s.altaAerolinea("aeroTest","TestAir","test@air.com","Desc","https://test.com",new byte[]{1},"Clave123");
        s.seleccionarAerolineaRet("aeroTest");

        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("VillaRodriguezTest","Uruguay","AeropuertoVR","Desc1","https://c1.com",new DTFecha(1,1,2024));
        cs.registrarCiudad("VillaPerezTest","Uruguay","AeropuertoVP","Desc2","https://c2.com",new DTFecha(2,2,2024));

        s.altaCategoria("CategoriaX");
        s.ingresarDatosRuta("RutaAurora","Desc ruta",100,200,50,
                "VillaRodriguezTest","VillaPerezTest",
                new DTFecha(1,1,2025),List.of("CategoriaX"),new byte[]{1});
        s.registrarRuta();

        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaAurora");
        ruta.setEstado(EstadoRutaVuelo.CONFIRMADA);

        DTRutaVuelo dtRuta = new DTRutaVuelo(
                ruta.getNombre(),
                ruta.getDescripcion(),
                ruta.getFechaAlta(),
                ruta.getCostoBase(),
                null,
                new DTCiudad(ruta.getCiudadOrigen().getNombre(),ruta.getCiudadOrigen().getPais()),
                new DTCiudad(ruta.getCiudadDestino().getNombre(),ruta.getCiudadDestino().getPais()),
                null,
                ruta.getEstado()
        );

        s.ingresarDatosVuelo("VueloAuroraTest",new DTFecha(10,10,2025),new DTHora(12,30),new DTHora(2,0),
                100,20,new DTFecha(9,9,2025),dtRuta,new byte[]{1});
        s.seleccionarAerolineaRet("aeroTest");
        s.darAltaVuelo();

        ClienteServicio clienteServicio = new ClienteServicio();
        clienteServicio.crearCliente("cliente1","Nombre1","mail1@test.com","Apellido1",
                new DTFecha(1,1,1990),"Uruguay",TipoDoc.CI,"12345678",new byte[]{1},"pass1");

        // --- IF1: vueloSeleccionadoParaReserva == null ---
        Exception ex1 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,List.of("cliente1"),new DTFecha(15,10,2025));
        });
        System.out.println("IF1 -> " + ex1.getMessage());
        assertTrue(ex1.getMessage().contains("Debe seleccionar un vuelo"));

        // --- IF3: pasajeros vacíos ---
        s.seleccionarVueloParaReserva("VueloAuroraTest");
        Exception ex3 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,List.of(),new DTFecha(15,10,2025));
        });
        System.out.println("IF3 -> " + ex3.getMessage());
        assertTrue(ex3.getMessage().contains("No se ha seleccionado ningún pasajero"));

        // --- IF4: cliente principal vacío ---
        Exception ex4 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,List.of(""),new DTFecha(15,10,2025));
        });
        System.out.println("IF4 -> " + ex4.getMessage());
        assertTrue(ex4.getMessage().contains("No se ha seleccionado un cliente principal"));

        // --- IF5: cliente principal inexistente ---
        Exception ex5 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,List.of("clienteInexistente"),new DTFecha(15,10,2025));
        });
        System.out.println("IF5 -> " + ex5.getMessage());
        assertTrue(ex5.getMessage().contains("No se encontró el cliente principal"));

        // --- IF6: duplicados en pasajeros ---
        List<String> pasajerosDup = List.of("cliente1","cliente1");
        Exception ex6 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,2,0,pasajerosDup,new DTFecha(15,10,2025));
        });
        System.out.println("IF6 -> " + ex6.getMessage());
        assertTrue(ex6.getMessage().contains("pasajeros duplicados"));

        // --- IF7: cantidad incorrecta (sobran pasajeros) ---
        clienteServicio.crearCliente("cliente2","Nombre2","mail2@test.com","Apellido2",
                new DTFecha(2,2,1992),"Uruguay",TipoDoc.CI,"87654321",new byte[]{2},"pass2");
        List<String> pasajerosSobra = List.of("cliente1","cliente2");
        Exception ex7 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,pasajerosSobra,new DTFecha(15,10,2025));
        });
        System.out.println("IF7 -> " + ex7.getMessage());
        assertTrue(ex7.getMessage().contains("Agregó demasiados pasajeros"));

        // --- ADMIN_REQUIRED: cliente ya tiene reserva en ese vuelo ---
        List<String> pasajerosOkAdmin = new ArrayList<>();
        s.nombresPasajes("cliente1", pasajerosOkAdmin);
        Exception exAdmin1 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,pasajerosOkAdmin,new DTFecha(16,10,2025));
        });
        assertTrue(exAdmin1.getMessage().startsWith("SUCCESS:"), "Primera reserva debe ser SUCCESS");

        Exception exAdmin2 = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,pasajerosOkAdmin,new DTFecha(17,10,2025));
        });
        System.out.println("ADMIN -> " + exAdmin2.getMessage());
        assertTrue(exAdmin2.getMessage().contains("ADMIN_REQUIRED"));

        // --- Asientos insuficientes ---
        Exception exSeats = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,101,0,List.of("cliente2"),new DTFecha(18,10,2025));
        });
        System.out.println("SEATS -> " + exSeats.getMessage());
        assertTrue(exSeats.getMessage().contains("No hay suficientes asientos turista"));

        // --- Pasajero inexistente ---
        List<String> pasajerosInexistente = List.of("cliente2","noExiste");
        Exception exNoPasajero = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,2,0,pasajerosInexistente,new DTFecha(19,10,2025));
        });
        System.out.println("NO PASAJERO -> " + exNoPasajero.getMessage());
        assertTrue(exNoPasajero.getMessage().contains("No se encontró el pasajero"));

        // --- Flujo feliz ---
        List<String> pasajerosOk = new ArrayList<>();
        s.nombresPasajes("cliente2", pasajerosOk);
        Exception exFinal = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,pasajerosOk,new DTFecha(20,10,2025));
        });
        System.out.println("SUCCESS -> " + exFinal.getMessage());
        assertTrue(exFinal.getMessage().startsWith("SUCCESS:"));
    }

    //SeleccionarrutaVuelo
    @Test
    void testSeleccionarRutaVuelo() throws Exception {
        Sistema s = Sistema.getInstance();

        // Setup aerolínea
        s.altaAerolinea("aeroTest","TestAir","test@air.com","Desc","https://test.com",new byte[]{1},"Clave123");
        s.seleccionarAerolineaRet("aeroTest");

        // Ciudades inventadas para evitar duplicados
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("VillaRodriguezTest","Uruguay","AeropuertoVR","Desc1","https://c1.com",new DTFecha(1,1,2024));
        cs.registrarCiudad("VillaPerezTest","Uruguay","AeropuertoVP","Desc2","https://c2.com",new DTFecha(2,2,2024));

        // Categoría y ruta
        s.altaCategoria("CategoriaX");
        s.ingresarDatosRuta(
                "RutaAurora","Desc ruta",
                100, // costoTurista
                200, // costoEjecutivo
                50,  // costoEquipajeExtra
                "VillaRodriguezTest","VillaPerezTest",
                new DTFecha(1,1,2025),
                List.of("CategoriaX"),
                new byte[]{1}
        );
        s.registrarRuta();

        // Confirmar estado de la ruta (si tu API lo requiere vía servicio)
        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaAurora");
        ruta.setEstado(EstadoRutaVuelo.CONFIRMADA);

        // Crear vuelo para esa ruta
        DTRutaVuelo dtRuta = new DTRutaVuelo(
                ruta.getNombre(),
                ruta.getDescripcion(),
                ruta.getFechaAlta(),
                ruta.getCostoBase(),
                null, // aerolínea DT opcional (tu método arma esto por dentro)
                new DTCiudad(ruta.getCiudadOrigen().getNombre(), ruta.getCiudadOrigen().getPais()),
                new DTCiudad(ruta.getCiudadDestino().getNombre(), ruta.getCiudadDestino().getPais()),
                ruta.getFoto(),
                ruta.getEstado()
        );

        s.ingresarDatosVuelo(
                "VueloAuroraTest",
                new DTFecha(10,10,2025),
                new DTHora(12,30),
                new DTHora(2,0),
                100, // asientos turista
                20,  // asientos ejecutivo
                new DTFecha(9,9,2025),
                dtRuta,
                new byte[]{1}
        );
        s.seleccionarAerolineaRet("aeroTest");
        s.darAltaVuelo();

        // Ejecutar: seleccionar por nombre de ruta (case-insensitive)
        List<DTVuelo> result = s.seleccionarRutaVuelo("rutaaurora");

        // Validaciones del camino feliz
        assertNotNull(result, "La lista no debe ser nula");
        assertFalse(result.isEmpty(), "Debe devolver al menos un vuelo para la ruta buscada");

        DTVuelo dt = result.get(0);
        assertEquals("VueloAuroraTest", dt.getNombre(), "Nombre del vuelo debe coincidir");
        assertNotNull(dt.getRuta(), "Debe mapear DTRutaVuelo");
        assertEquals("RutaAurora", dt.getRuta().getNombre(), "Nombre de la ruta debe coincidir");
        assertEquals("VillaRodriguezTest", dt.getRuta().getCiudadOrigen().getNombre(), "Origen correcto");
        assertEquals("VillaPerezTest", dt.getRuta().getCiudadDestino().getNombre(), "Destino correcto");
    }

    //Mostrar datos usuario
    @Test
    void testMostrarDatosUsuarioMod() {
        Sistema s = Sistema.getInstance();

        // --- Alta de un cliente válido ---
        assertDoesNotThrow(() -> s.altaCliente(
                "nickCliente", "Juan", "cliente@mail.com", "Pérez",
                new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123",
                new byte[]{1}, "Clave123"
        ));

        // --- Alta de una aerolínea válida ---
        assertDoesNotThrow(() -> s.altaAerolinea(
                "nickAero", "AirTest", "aero@mail.com", "Descripcion",
                "http://aero.com", new byte[]{1}, "Clave123"
        ));

        // --- Caso 1: Cliente ---
        DTUsuario dtoCliente = s.mostrarDatosUsuarioMod("nickCliente");
        assertTrue(dtoCliente instanceof DTCliente);
        assertEquals("nickCliente", dtoCliente.getNickname());
        assertEquals("cliente@mail.com", dtoCliente.getCorreo());

        // --- Caso 2: Aerolínea ---
        DTUsuario dtoAero = s.mostrarDatosUsuarioMod("nickAero");
        assertTrue(dtoAero instanceof DTAerolinea);
        assertEquals("nickAero", dtoAero.getNickname());
        assertEquals("aero@mail.com", dtoAero.getCorreo());

        // --- Caso 3: Usuario inexistente ---
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            s.mostrarDatosUsuarioMod("noExiste");
        });
        assertEquals("Usuario no encontrado", ex.getMessage());
    }



    //LISTAR AEROLINEAS



    // FUNCIONES EXTRAS

    @Test
    void testValidarContrasena() {
        assertTrue(Sistema.validarContrasena("Abcdef1")); // válida
        assertFalse(Sistema.validarContrasena("Ab1")); // muy corta
        assertFalse(Sistema.validarContrasena("abcdef1")); // sin mayúscula
        assertTrue(Sistema.validarContrasena("ABCDEF1")); // sin minúscula, pero válida según lógica actual
        assertFalse(Sistema.validarContrasena("Abcdef")); // sin número
        assertFalse(Sistema.validarContrasena("")); // vacía
        assertFalse(Sistema.validarContrasena("123456")); // solo números
        assertFalse(Sistema.validarContrasena(null)); // null
    }

    @Test
    void testEsNombreValido() {
        assertTrue(Sistema.esNombreValido("Juan Pérez")); // válido
        assertFalse(Sistema.esNombreValido("Jo")); // muy corto
        assertFalse(Sistema.esNombreValido("Juan123")); // con números
        assertFalse(Sistema.esNombreValido("Juan!")); // con símbolo
        assertFalse(Sistema.esNombreValido("   ")); // solo espacios
        assertFalse(Sistema.esNombreValido("")); // vacío
        assertFalse(Sistema.esNombreValido(null)); //null
    }

    //AltaCiudad
    @Test
    void testAltaCiudad(){
        Sistema s=Sistema.getInstance();
        CiudadServicio ciudadServicio=new CiudadServicio();
        AeropuertoServicio aeropuertoServicio=new AeropuertoServicio();
        ciudadServicio.registrarCiudad("Montevideo","Uruguay","AeropuertoBase","DescripcionBase","https://mvd.com",new DTFecha(1,1,2024)); //persistir ciudad
        dato.entidades.Ciudad ciudadBase=ciudadServicio.buscarCiudadPorNombreYPais("Montevideo","Uruguay"); //obtener ciudad persistida
        aeropuertoServicio.registrarAeropuerto("AeropuertoValido",ciudadBase); //registrar aeropuerto valido
        assertThrows(IllegalArgumentException.class,()->s.altaCiudad("CiudadX","","AeropuertoValido","DescripcionValida","https://x.com",new DTFecha(2,2,2024))); //pais invalido
        assertThrows(IllegalArgumentException.class,()->s.altaCiudad("CiudadX","Uruguay","","DescripcionValida","https://x.com",new DTFecha(2,2,2024))); //aeropuerto invalido
        assertThrows(IllegalArgumentException.class,()->s.altaCiudad("CiudadX","Uruguay","AeropuertoValido","","https://x.com",new DTFecha(2,2,2024))); //descripcion invalida
    }

    @Test
    void testListarCiudades() {
        Sistema s = Sistema.getInstance();
        CiudadServicio cs = new CiudadServicio();

        // --- Caso 1: sin ciudades ---
        List<DTCiudad> vacias = s.listarCiudades();
        assertNotNull(vacias, "La lista no debe ser nula");
        assertTrue(vacias.isEmpty(), "Debe estar vacía si no hay ciudades");

        // --- Caso 2: con ciudades registradas ---
        cs.registrarCiudad("MontevideoTest","Uruguay","AeropuertoCarrasco",
                "Capital","http://mv.com",new DTFecha(1,1,2020));
        cs.registrarCiudad("SaltoTest","Uruguay","AeropuertoSalto",
                "Ciudad norte","http://salto.com",new DTFecha(2,2,2020));

        List<DTCiudad> result = s.listarCiudades();
        assertEquals(2, result.size(), "Debe devolver 2 ciudades");

        // Validar que están las ciudades esperadas
        assertTrue(result.stream().anyMatch(c -> c.getNombre().equals("MontevideoTest")));
        assertTrue(result.stream().anyMatch(c -> c.getNombre().equals("SaltoTest")));
    }

    @Test
    void testListarCiudadesDestino() {
        Sistema s = Sistema.getInstance();

        // --- Caso 1: lista vacía ---
        List<DTCiudad> vacias = s.listarCiudadesDestino(new ArrayList<>(), "Montevideo");
        assertNotNull(vacias);
        assertTrue(vacias.isEmpty(), "Si no hay ciudades, debe devolver vacío");

        // --- Caso 2: varias ciudades ---
        List<DTCiudad> ciudades = new ArrayList<>();
        ciudades.add(new DTCiudad("MontevideoTest","Uruguay"));
        ciudades.add(new DTCiudad("SaltoTest","Uruguay"));
        ciudades.add(new DTCiudad("PaysanduTest","Uruguay"));

        List<DTCiudad> destinos = s.listarCiudadesDestino(ciudades, "montevideoTEST"); // case-insensitive
        assertEquals(2, destinos.size(), "Debe excluir la ciudad de origen");

        // Validar que no contiene la ciudad origen
        assertFalse(destinos.stream().anyMatch(c -> c.getNombre().equalsIgnoreCase("MontevideoTest")));
        assertTrue(destinos.stream().anyMatch(c -> c.getNombre().equals("SaltoTest")));
        assertTrue(destinos.stream().anyMatch(c -> c.getNombre().equals("PaysanduTest")));
    }

    @Test
    void testListarRutaVueloDeVuelo() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Paso 1: alta de aerolínea ---
        s.altaAerolinea("aeroTest", "TestAir", "test@air.com", "Descripcion válida",
                "https://test.com", new byte[]{1}, "Clave123");

        // --- Paso 2: selección para registrar rutas ---
        s.seleccionarAerolineaRet("aeroTest");

        // --- Paso 3: alta de ciudades ---
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("VillaRodriguezTest", "Uruguay", "AeropuertoVR", "Desc1",
                "https://c1.com", new DTFecha(1, 1, 2024));
        cs.registrarCiudad("VillaPerezTest", "Uruguay", "AeropuertoVP", "Desc2",
                "https://c2.com", new DTFecha(2, 2, 2024));

        // --- Paso 4: categoría y ruta ---
        s.altaCategoria("CategoriaX");
        s.ingresarDatosRuta("RutaAurora", "Desc ruta", 100, 200, 50,
                "VillaRodriguezTest", "VillaPerezTest",
                new DTFecha(1, 1, 2025), List.of("CategoriaX"), new byte[]{1});
        s.registrarRuta();

        // --- Paso 5: selección para administración y aceptación oficial ---
        s.seleccionarAerolineaParaAdministracion("aeroTest");
        s.seleccionarRutaVueloParaAdministracion("RutaAurora");
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            s.aceptarRutaVuelo();
        });
        assertTrue(ex.getMessage().startsWith("SUCCESS:"), "La ruta debe ser aceptada correctamente");

        // --- Paso 6: ejecutar método a testear ---
        List<DTRutaVuelo> rutas = s.listarRutaVueloDeVuelo();

        // --- Validaciones ---
        assertNotNull(rutas, "La lista no debe ser nula");
        assertFalse(rutas.isEmpty(), "Debe devolver al menos una ruta confirmada");
        assertTrue(rutas.stream().anyMatch(r -> r.getNombre().equals("RutaAurora")),
                "Debe contener la ruta 'RutaAurora'");
    }

    @Test
    void testListarClientes() {
        Sistema s = Sistema.getInstance();

        // --- Alta de clientes ---
        s.altaCliente("clientefulano", "Anaaaa", "anasda@test.com", "Goomemez",
                new DTFecha(1,1,1990), "Uruguay", TipoDoc.CI, "12343258", new byte[]{1}, "Clave1321");

        s.altaCliente("clientemengano", "Luisito", "luis@test.com", "Pérez",
                new DTFecha(2,2,1992), "Uruguay", TipoDoc.CI, "87632321", new byte[]{2}, "Clave232112");

        // --- Ejecutar método ---
        List<DTCliente> clientes = s.listarClientes();

        // --- Validaciones ---
        assertNotNull(clientes, "La lista no debe ser nula");
        assertEquals(2, clientes.size(), "Debe haber dos clientes registrados");

        List<String> nicknames = clientes.stream().map(DTCliente::getNickname).toList();
        assertTrue(nicknames.contains("clientefulano"));
        assertTrue(nicknames.contains("clientemengano"));
    }

    @Test
    void testPasajeros() {
        Sistema s = Sistema.getInstance();

        // --- Alta de clientes válidos ---
        s.altaCliente("clienteAna", "Analia", "analia@test.com", "Gomez",
                new DTFecha(1, 1, 1990), "Uruguay", TipoDoc.CI, "CI123456", new byte[]{1}, "ClaveAna1");

        s.altaCliente("clienteLuis", "Luisito", "luisito@test.com", "Perez",
                new DTFecha(2, 2, 1992), "Uruguay", TipoDoc.CI, "CI876543", new byte[]{2}, "ClaveLuis2");

        s.altaCliente("clienteSofia", "Sofia", "sofia@test.com", "Martinez",
                new DTFecha(3, 3, 1993), "Uruguay", TipoDoc.CI, "CI112233", new byte[]{3}, "ClaveSofi3");

        // --- Ejecutar método ---
        List<DTPasajero> pasajeros = s.pasajeros("clienteLuis");

        // --- Validaciones ---
        assertNotNull(pasajeros, "La lista no debe ser nula");
        assertEquals(2, pasajeros.size(), "Debe excluir al cliente principal");

        List<String> nicknames = pasajeros.stream().map(DTPasajero::getNicknameCliente).toList();
        assertTrue(nicknames.contains("clienteAna"));
        assertTrue(nicknames.contains("clienteSofia"));
        assertFalse(nicknames.contains("clienteLuis"), "No debe incluir al cliente principal");
    }

    @Test
    void testMostrarDatosUsuario_conServicios() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Alta de cliente ---
        assertDoesNotThrow(() -> s.altaCliente(
                "nickClienteTest", "Juan", "cliente@test.com", "Pérez",
                new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123",
                new byte[]{1}, "Abcdef1"
        ));

        // --- Alta de aerolínea ---
        assertDoesNotThrow(() -> s.altaAerolinea(
                "nickAeroTest", "AirTest", "aero@test.com", "Descripcion",
                "http://aero.com", new byte[]{1}, "Abcdef1"
        ));

        // --- Caso 1: Cliente ---
        DTUsuario dtoCliente = s.mostrarDatosUsuario("nickClienteTest");
        assertTrue(dtoCliente instanceof DTCliente);
        DTCliente c = (DTCliente) dtoCliente;
        assertEquals("nickClienteTest", c.getNickname());
        assertEquals("cliente@test.com", c.getCorreo());

        // --- Caso 2: Aerolínea ---
        DTUsuario dtoAero = s.mostrarDatosUsuario("nickAeroTest");
        assertTrue(dtoAero instanceof DTAerolinea);
        DTAerolinea a = (DTAerolinea) dtoAero;
        assertEquals("nickAeroTest", a.getNickname());
        assertEquals("aero@test.com", a.getCorreo());

        // --- Caso 3: Usuario inexistente ---
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            s.mostrarDatosUsuario("noExiste");
        });
        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void testListarAerolineas() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Alta de aerolíneas con nombres válidos ---
        s.altaAerolinea("aerolineauno", "AerolíneaUno", "unooo@air.com", "Descripción Uno", "https://aerolineauno.com", new byte[]{1}, "Clave1123");
        s.altaAerolinea("aerolineados", "AerolíneaDos", "dosss@air.com", "Descripción Dos", "https://aerolineados.com", new byte[]{2}, "Clave2123");

        // --- Ejecutar método ---
        List<DTAerolinea> lista = s.listarAerolineas();

        // --- Validaciones ---
        assertEquals(2, lista.size(), "Debe haber 2 aerolíneas");

        DTAerolinea a1 = lista.stream().filter(a -> a.getNickname().equals("aerolineauno")).findFirst().orElse(null);
        DTAerolinea a2 = lista.stream().filter(a -> a.getNickname().equals("aerolineados")).findFirst().orElse(null);

        assertNotNull(a1, "Debe incluir aerolineauno");
        assertNotNull(a2, "Debe incluir aerolineados");

        assertEquals("AerolíneaUno", a1.getNombre());
        assertEquals("AerolíneaDos", a2.getNombre());

        assertTrue(a1.getRutasVuelo().isEmpty(), "Las rutas deben estar vacías");
        assertTrue(a2.getRutasVuelo().isEmpty(), "Las rutas deben estar vacías");
    }

    @Test
    void testSeleccionarRutaVueloRet() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Alta de aerolínea válida ---
        s.altaAerolinea("aeroTest", "Aerolínea del Sur", "sur@air.com", "Descripción válida", "https://sur.com", new byte[]{1}, "Clave123");
        s.seleccionarAerolinea("aeroTest"); // ✅ esto setea recuerdaAerolinea

        // --- Ciudades necesarias ---
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadOrigen", "Uruguay", "AeropO", "DescO", "https://o.com", new DTFecha(1, 1, 2024));
        cs.registrarCiudad("CiudadDestino", "Uruguay", "AeropD", "DescD", "https://d.com", new DTFecha(2, 2, 2024));

        // --- Categoría y ruta ---
        s.altaCategoria("CategoriaX");
        s.ingresarDatosRuta("RutaSur", "Ruta costera", 120, 250, 40,
                "CiudadOrigen", "CiudadDestino",
                new DTFecha(10, 10, 2025), List.of("CategoriaX"), new byte[]{1});
        s.registrarRuta();

        // --- Confirmar ruta y asociarla a la aerolínea ---
        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaSur");
        ruta.setEstado(EstadoRutaVuelo.CONFIRMADA);

        AerolineaServicio as = new AerolineaServicio();
        Aerolinea aero = as.buscarAerolineaPorNickname("aeroTest");
        aero.getRutasVuelo().add(ruta); // ✅ asociación explícita

        // --- Validar selección exitosa ---
        s.seleccionarAerolinea("aeroTest");
        DTRutaVuelo dtRuta = s.seleccionarRutaVueloRet("RutaSur");
        assertNotNull(dtRuta, "Debe retornar un DTRutaVuelo");
        assertEquals("RutaSur", dtRuta.getNombre());
        assertEquals("CiudadOrigen", dtRuta.getCiudadOrigen().getNombre());
        assertEquals("CiudadDestino", dtRuta.getCiudadDestino().getNombre());
        assertEquals("aeroTest", dtRuta.getAerolinea().getNickname());

        // --- Validar excepción si no existe la ruta ---
        Exception exRuta = assertThrows(IllegalStateException.class, () -> {
            s.seleccionarRutaVueloRet("RutaInexistente");
        });
        System.out.println("Ruta inexistente -> " + exRuta.getMessage());
        assertEquals("No se encontró la ruta con el nombre: RutaInexistente", exRuta.getMessage());

        // --- Validar excepción si no hay aerolínea seleccionada ---
        s.seleccionarAerolinea(null); // limpia recuerdaAerolinea
        Exception exSinAero = assertThrows(IllegalStateException.class, () -> {
            s.seleccionarRutaVueloRet("RutaSur");
        });
        System.out.println("Sin aerolínea -> " + exSinAero.getMessage());
        assertEquals("Debe seleccionar una aerolínea antes de seleccionar una ruta.", exSinAero.getMessage());
    }

}