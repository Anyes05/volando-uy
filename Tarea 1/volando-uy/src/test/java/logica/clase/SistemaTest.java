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

        em.createQuery("DELETE FROM UsoPaquete").executeUpdate();
        em.createQuery("DELETE FROM CompraPaquete").executeUpdate();
        em.createQuery("DELETE FROM Pasaje").executeUpdate();
        em.createQuery("DELETE FROM Reserva").executeUpdate();
        em.createQuery("DELETE FROM CompraComun").executeUpdate();
        em.createQuery("DELETE FROM Cantidad").executeUpdate();
        em.createQuery("DELETE FROM PaqueteVuelo").executeUpdate();
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
        assertThrows(IllegalStateException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));

        //alta y selección de aerolínea válida
        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","EmpresaAerea","https://a.com",new byte[]{1},"Abcdef1");
        s.seleccionarAerolinea("nickAero");

        //validaciones
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",-10,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"CiudadX","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","CiudadY",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaX"),new byte[]{1}, null));

        //caso válido
        assertDoesNotThrow(()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null));
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

        s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}, null);
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
                new DTFecha(1,1,2025),List.of("CategoriaListar"),new byte[]{1}, null);
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
                new DTFecha(1,1,2025), List.of("CategoriaX"), new byte[]{1}, null);
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
                new DTFecha(1,1,2025),List.of("CategoriaX"),new byte[]{1}, null);
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

        // Seleccionar vuelo para reserva
        s.seleccionarVueloParaReserva("VueloAuroraTest");

        // --- IF4: cliente principal vacío ---
        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,List.of(""),new DTFecha(15,10,2025));
        });
        System.out.println("IF4 -> " + ex4.getMessage());
        assertTrue(ex4.getMessage().contains("No se ha seleccionado un cliente principal"));

        // --- IF5: cliente principal inexistente ---
        Exception ex5 = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,1,0,List.of("clienteInexistente"),new DTFecha(15,10,2025));
        });
        System.out.println("IF5 -> " + ex5.getMessage());
        assertTrue(ex5.getMessage().contains("No se encontró el cliente principal"));

        // --- IF6: duplicados en pasajeros ---
        List<String> pasajerosDup = List.of("cliente1","cliente1");
        Exception ex6 = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,2,0,pasajerosDup,new DTFecha(15,10,2025));
        });
        System.out.println("IF6 -> " + ex6.getMessage());
        assertTrue(ex6.getMessage().contains("pasajeros duplicados"));

        // --- IF7: cantidad incorrecta (sobran pasajeros) ---
        clienteServicio.crearCliente("cliente2","Nombre2","mail2@test.com","Apellido2",
                new DTFecha(2,2,1992),"Uruguay",TipoDoc.CI,"87654321",new byte[]{2},"pass2");
        List<String> pasajerosSobra = List.of("cliente1","cliente2");
        Exception ex7 = assertThrows(IllegalArgumentException.class, () -> {
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
        Exception exNoPasajero = assertThrows(IllegalArgumentException.class, () -> {
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
                new byte[]{1},
                null
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
                new DTFecha(1, 1, 2025), List.of("CategoriaX"), new byte[]{1}, null);
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
        s.altaCliente("nickClienteTest", "Juan", "cliente@test.com", "Pérez",
                new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123",
                new byte[]{1}, "Abcdef1");

        // --- Alta de aerolínea ---
        s.altaAerolinea("nickAeroTest", "AirTest", "aero@test.com", "Descripcion",
                "http://aero.com", new byte[]{1}, "Abcdef1");

        // --- Alta de ciudades y categoría para poder crear ruta ---
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadOrigen", "Uruguay", "AeropO", "DescO", "https://o.com", new DTFecha(1,1,2024));
        cs.registrarCiudad("CiudadDestino", "Uruguay", "AeropD", "DescD", "https://d.com", new DTFecha(2,2,2024));
        s.altaCategoria("CategoriaX");

        // --- Crear y confirmar ruta para la aerolínea ---
        s.seleccionarAerolineaRet("nickAeroTest");
        s.ingresarDatosRuta("RutaTest", "Ruta costera", 100, 200, 50,
                "CiudadOrigen", "CiudadDestino",
                new DTFecha(10,10,2025), List.of("CategoriaX"), new byte[]{1}, null);
        s.registrarRuta();
        s.seleccionarAerolineaParaAdministracion("nickAeroTest");
        s.seleccionarRutaVueloParaAdministracion("RutaTest");
        assertThrows(IllegalStateException.class, () -> s.aceptarRutaVuelo());

        // --- Crear paquete ---
        s.crearPaquete("PackTest", "Paquete completo", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{1});

        // --- Realizar compra de paquete por el cliente ---
        s.seleccionarCliente("nickClienteTest");
        s.seleccionarPaquete("PackTest");
        s.realizarCompra(new DTFecha(15,10,2025), 500f, new DTFecha(31,12,2025));

        // --- Caso 1: Cliente con reservas ---
        DTUsuario dtoCliente = s.mostrarDatosUsuario("nickClienteTest");
        assertTrue(dtoCliente instanceof DTCliente);
        DTCliente c = (DTCliente) dtoCliente;
        assertEquals("nickClienteTest", c.getNickname());
        assertEquals("cliente@test.com", c.getCorreo());
        assertFalse(c.getReserva().isEmpty(), "El cliente debe tener reservas");
        assertTrue(c.getReserva().get(0) instanceof DTCompraPaquete);

        // --- Caso 2: Aerolínea con rutas ---
        DTUsuario dtoAero = s.mostrarDatosUsuario("nickAeroTest");
        assertTrue(dtoAero instanceof DTAerolinea);
        DTAerolinea a = (DTAerolinea) dtoAero;
        assertEquals("nickAeroTest", a.getNickname());
        assertEquals("aero@test.com", a.getCorreo());
        assertFalse(a.getRutasVuelo().isEmpty(), "La aerolínea debe tener rutas");
        assertEquals("RutaTest", a.getRutasVuelo().get(0).getNombre());

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
                new DTFecha(10, 10, 2025), List.of("CategoriaX"), new byte[]{1}, null);
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

    @Test
    void testAgregarRutaAPaquete() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Alta de aerolínea y ciudades ---
        s.altaAerolinea("aeroTest", "TestAir", "test@air.com", "Desc", "https://test.com", new byte[]{1}, "Clave123");
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadOrigen", "Uruguay", "AeropO", "DescO", "https://o.com", new DTFecha(1, 1, 2024));
        cs.registrarCiudad("CiudadDestino", "Uruguay", "AeropD", "DescD", "https://d.com", new DTFecha(2, 2, 2024));

        // --- Alta de categoría y ruta ---
        s.altaCategoria("CategoriaX");
        s.seleccionarAerolineaRet("aeroTest");
        s.ingresarDatosRuta("RutaTest", "Ruta costera", 100, 200, 50,
                "CiudadOrigen", "CiudadDestino",
                new DTFecha(10, 10, 2025), List.of("CategoriaX"), new byte[]{1}, null);
        s.registrarRuta();

        // --- Confirmar ruta con flujo real ---
        s.seleccionarAerolineaParaAdministracion("aeroTest");
        s.seleccionarRutaVueloParaAdministracion("RutaTest");
        Exception exAceptar = assertThrows(IllegalStateException.class, () -> {
            s.aceptarRutaVuelo();
        });
        assertTrue(exAceptar.getMessage().startsWith("SUCCESS:"), "La ruta debe ser aceptada correctamente");

        // --- Crear paquete ---
        s.crearPaquete("PackTest", "Paquete completo", TipoAsiento.Turista, 30, 10,
                new DTFecha(1, 1, 2025), new byte[]{1});

        // --- Seleccionar paquete, aerolínea y ruta ---
        s.seleccionarPaquete("PackTest");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaTest");

        // --- Agregar ruta al paquete ---
        s.agregarRutaAPaquete(2, TipoAsiento.Turista);

        // --- Validar resultado ---
        PaqueteVueloServicio ps = new PaqueteVueloServicio();
        PaqueteVuelo paquete = ps.obtenerPaquetePorNombre("PackTest");

        assertNotNull(paquete, "El paquete debe existir");
        assertEquals(1, paquete.getCantidad().size(), "Debe tener una ruta agregada");
        assertEquals("RutaTest", paquete.getCantidad().get(0).getRutaVuelo().getNombre());
        assertEquals(2, paquete.getCantidad().get(0).getCant(), "La cantidad de asientos debe coincidir");
        assertEquals(TipoAsiento.Turista, paquete.getCantidad().get(0).getTipoAsiento(), "El tipo de asiento debe coincidir");

        // Validar que el costo total se haya calculado con descuento aplicado
        float costoBaseTurista = paquete.getCantidad().get(0).getRutaVuelo().getCostoBase().getCostoTurista();
        float costoEsperado = (2 * costoBaseTurista) - ((2 * costoBaseTurista) * (paquete.getDescuento() / 100));
        assertEquals(costoEsperado, paquete.getCostoTotal(), 0.01, "El costo total debe ser correcto");
    }

    @Test
    void testListarRutasIngresadas() throws Exception {
        // --- Limpiar base antes de empezar ---
        limpiarBasePostgres();

        Sistema s = Sistema.getInstance();

        // --- Alta de aerolínea ---
        s.altaAerolinea("aeroIngresada", "IngresadaAir", "ingresada@air.com", "Desc", "https://ingresada.com", new byte[]{1}, "Clave123");

        // --- Alta de ciudades y categoría ---
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadAlpha", "Uruguay", "AeropAlpha", "DescAlpha", "https://alpha.com", new DTFecha(1,1,2024));
        cs.registrarCiudad("CiudadBeta", "Uruguay", "AeropBeta", "DescBeta", "https://beta.com", new DTFecha(2,2,2024));
        s.altaCategoria("CategoriaIngresada");

        // --- Caso 1: sin aerolínea seleccionada ---
        Exception ex1 = assertThrows(IllegalStateException.class, () -> {
            s.listarRutasIngresadas();
        });
        assertEquals("Debe seleccionar una aerolínea antes de listar las rutas ingresadas.", ex1.getMessage());

        // --- Crear ruta en estado INGRESADA ---
        s.seleccionarAerolineaRet("aeroIngresada");
        s.ingresarDatosRuta("RutaIngresada", "Ruta de prueba ingresada", 100, 200, 50,
                "CiudadAlpha", "CiudadBeta",
                new DTFecha(10,10,2025), List.of("CategoriaIngresada"), new byte[]{1}, null);
        s.registrarRuta();

        // --- Caso 2: confirmar ruta y luego listar ---
        s.seleccionarAerolineaParaAdministracion("aeroIngresada");
        s.seleccionarRutaVueloParaAdministracion("RutaIngresada");
        Exception exConfirm = assertThrows(IllegalStateException.class, () -> s.aceptarRutaVuelo());
        assertTrue(exConfirm.getMessage().startsWith("SUCCESS:"), "La confirmación debe ser exitosa");

        // Volvemos a seleccionar la aerolínea para administración
        s.seleccionarAerolineaParaAdministracion("aeroIngresada");

        // Ahora ya no hay rutas en estado INGRESADA
        Exception ex2 = assertThrows(IllegalStateException.class, () -> {
            s.listarRutasIngresadas();
        });
        assertEquals("No hay rutas de vuelo en estado 'Ingresada' para la aerolínea seleccionada.", ex2.getMessage());

        // --- Caso 3: crear otra ruta en estado INGRESADA ---
        s.seleccionarAerolineaRet("aeroIngresada");
        s.ingresarDatosRuta("RutaNueva", "Otra ruta ingresada", 150, 250, 60,
                "CiudadAlpha", "CiudadBeta",
                new DTFecha(11,11,2025), List.of("CategoriaIngresada"), new byte[]{1}, null);
        s.registrarRuta();

        s.seleccionarAerolineaParaAdministracion("aeroIngresada");
        List<DTRutaVuelo> rutasIngresadas = s.listarRutasIngresadas();

        assertNotNull(rutasIngresadas);
        assertEquals(1, rutasIngresadas.size());
        assertEquals("RutaNueva", rutasIngresadas.get(0).getNombre());
        assertEquals(EstadoRutaVuelo.INGRESADA, rutasIngresadas.get(0).getEstado());
    }

    @Test
    void testRechazarRutaVuelo() throws Exception {
        // --- Limpiar base antes de empezar ---
        limpiarBasePostgres();

        Sistema s = Sistema.getInstance();

        // --- Alta de aerolínea ---
        s.altaAerolinea("aeroRechazo", "RejectAir", "reject@air.com", "Desc", "https://reject.com", new byte[]{1}, "Clave123");

        // --- Alta de ciudades y categoría ---
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadGamma", "Uruguay", "AeropGamma", "DescGamma", "https://gamma.com", new DTFecha(1,1,2024));
        cs.registrarCiudad("CiudadDelta", "Uruguay", "AeropDelta", "DescDelta", "https://delta.com", new DTFecha(2,2,2024));
        s.altaCategoria("CategoriaRechazo");

        // --- Caso 1: sin ruta seleccionada ---
        Exception ex1 = assertThrows(IllegalStateException.class, () -> {
            s.rechazarRutaVuelo();
        });
        assertEquals("Debe seleccionar una ruta de vuelo antes de rechazarla.", ex1.getMessage());

        // --- Crear ruta en estado INGRESADA ---
        s.seleccionarAerolineaRet("aeroRechazo");
        s.ingresarDatosRuta("RutaRechazo", "Ruta a rechazar", 100, 200, 50,
                "CiudadGamma", "CiudadDelta",
                new DTFecha(10,10,2025), List.of("CategoriaRechazo"), new byte[]{1}, null);
        s.registrarRuta();

        // --- Seleccionar aerolínea y ruta para administración ---
        s.seleccionarAerolineaParaAdministracion("aeroRechazo");
        s.seleccionarRutaVueloParaAdministracion("RutaRechazo");

        // --- Caso 2: rechazar ruta seleccionada ---
        Exception ex2 = assertThrows(IllegalStateException.class, () -> {
            s.rechazarRutaVuelo();
        });
        assertTrue(ex2.getMessage().startsWith("SUCCESS:"), "Debe indicar éxito en el rechazo");

        // --- Verificar que el estado de la ruta cambió a RECHAZADA ---
        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaRechazo");
        assertEquals(EstadoRutaVuelo.RECHAZADA, ruta.getEstado(), "La ruta debe estar en estado RECHAZADA");
    }

    @Test
    void testFuncionesPaquete() throws Exception {
        // --- Limpiar base ---
        limpiarBasePostgres();
        Sistema s = Sistema.getInstance();

        // --- Alta de aerolínea y ciudades ---
        s.altaAerolinea("aeroPack", "PackAir", "pack@air.com", "Desc", "https://pack.com", new byte[]{1}, "Clave123");
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadX", "Uruguay", "AeropX", "DescX", "https://x.com", new DTFecha(1,1,2024));
        cs.registrarCiudad("CiudadY", "Uruguay", "AeropY", "DescY", "https://y.com", new DTFecha(2,2,2024));
        s.altaCategoria("CategoriaPack");

        // --- Crear ruta ---
        s.seleccionarAerolineaRet("aeroPack");
        s.ingresarDatosRuta("RutaPack", "Ruta para paquete", 100, 200, 50,
                "CiudadX", "CiudadY",
                new DTFecha(10,10,2025), List.of("CategoriaPack"), new byte[]{1}, null);
        s.registrarRuta();

        // Confirmar ruta con flujo real
        s.seleccionarAerolineaParaAdministracion("aeroPack");
        s.seleccionarRutaVueloParaAdministracion("RutaPack");
        Exception exAceptar = assertThrows(IllegalStateException.class, () -> s.aceptarRutaVuelo());
        assertTrue(exAceptar.getMessage().startsWith("SUCCESS:"), "La ruta debe ser aceptada correctamente");

        // --- Crear paquete ---
        s.crearPaquete("PackTest", "Paquete de prueba", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{1});

        // --- Caso 1: mostrarPaquete ---
        List<DTPaqueteVuelos> paquetes = s.mostrarPaquete();
        assertFalse(paquetes.isEmpty(), "Debe haber al menos un paquete");
        assertEquals("PackTest", paquetes.get(0).getNombre());

        // --- Caso 2: obtenerPaquetesNoComprados ---
        List<DTPaqueteVuelos> noComprados = s.obtenerPaquetesNoComprados();
        assertFalse(noComprados.isEmpty(), "Debe listar paquetes no comprados");
        assertEquals("PackTest", noComprados.get(0).getNombre());

        // --- Caso 3: mostrarPaqueteConRutas ---
        // Seleccionar paquete, aerolínea y ruta antes de agregar
        s.seleccionarPaquete("PackTest");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroPack", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaPack");
        s.agregarRutaAPaquete(2, TipoAsiento.Turista);

        List<DTPaqueteVuelos> paquetesConRutas = s.mostrarPaqueteConRutas();
        assertFalse(paquetesConRutas.isEmpty(), "Debe listar paquetes con rutas");
        assertEquals("PackTest", paquetesConRutas.get(0).getNombre());
        assertFalse(paquetesConRutas.get(0).getRutas().isEmpty(), "El paquete debe tener rutas asociadas");

        // --- Caso 4: clienteYaComproPaquete ---
        s.altaCliente("clientePack", "Juan", "cliente@pack.com", "Perez",
                new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123",
                new byte[]{1}, "Clave123");
        s.seleccionarCliente("clientePack");
        s.seleccionarPaquete("PackTest");
        s.realizarCompra(new DTFecha(15,10,2025), 500f, new DTFecha(31,12,2025));

        boolean yaCompro = s.clienteYaComproPaquete();
        assertTrue(yaCompro, "El cliente debería figurar como que ya compró el paquete");
    }

    @Test
    void testConsultasPaqueteYClientes() throws Exception {
        limpiarBasePostgres();
        Sistema s = Sistema.getInstance();

        // --- Crear aerolínea, ciudades, ruta y confirmar ---
        s.altaAerolinea("aeroPack", "PackAir", "pack@air.com", "Desc", "https://pack.com", new byte[]{1}, "Clave123");
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("CiudadOrigen", "Uruguay", "AeropOrigen", "DescOrigen", "https://origen.com", new DTFecha(1,1,2024));
        cs.registrarCiudad("CiudadDestino", "Uruguay", "AeropDestino", "DescDestino", "https://destino.com", new DTFecha(2,2,2024));
        s.altaCategoria("CategoriaPack");

        s.seleccionarAerolineaRet("aeroPack");
        s.ingresarDatosRuta("RutaPack", "Ruta para paquete", 100, 200, 50,
                "CiudadOrigen", "CiudadDestino",
                new DTFecha(10,10,2025), List.of("CategoriaPack"), new byte[]{1}, null);
        s.registrarRuta();
        s.seleccionarAerolineaParaAdministracion("aeroPack");
        s.seleccionarRutaVueloParaAdministracion("RutaPack");
        Exception exAceptar = assertThrows(IllegalStateException.class, () -> s.aceptarRutaVuelo());
        assertTrue(exAceptar.getMessage().startsWith("SUCCESS:"));

        // Refrescar la aerolínea para que tenga la ruta confirmada en su lista
        AerolineaServicio as = new AerolineaServicio();
        Aerolinea aero = as.buscarAerolineaPorNickname("aeroPack");
        as.actualizarAerolinea(aero);

        // --- Crear paquete y seleccionarlo ---
        s.crearPaquete("PackTest", "Paquete de prueba", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{1});
        s.seleccionarPaquete("PackTest");

        // --- Caso 1: consultaPaqueteVuelo con paquete seleccionado ---
        DTPaqueteVuelos dtPack = s.consultaPaqueteVuelo();
        assertNotNull(dtPack);
        assertEquals("PackTest", dtPack.getNombre());

        // --- Caso 2: consultaPaqueteVueloRutas sin rutas asociadas ---
        List<DTRutaVuelo> rutasVacias = s.consultaPaqueteVueloRutas();
        assertNotNull(rutasVacias);
        assertTrue(rutasVacias.isEmpty(), "El paquete aún no tiene rutas");

        // --- Caso 3: consultaPaqueteVueloRutasCantidadTipo sin rutas → excepción ---
        Exception exSinRutas = assertThrows(IllegalStateException.class, () -> s.consultaPaqueteVueloRutasCantidadTipo());
        assertEquals("El paquete no tiene rutas asociadas.", exSinRutas.getMessage());

        // --- Agregar ruta al paquete ---
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroPack", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaPack");
        s.agregarRutaAPaquete(2, TipoAsiento.Turista);

        // --- Caso 4: consultaPaqueteVueloRutas con rutas ---
        List<DTRutaVuelo> rutas = s.consultaPaqueteVueloRutas();
        assertEquals(1, rutas.size());
        assertEquals("RutaPack", rutas.get(0).getNombre());

        // --- Caso 5: consultaPaqueteVueloRutasCantidadTipo con ruta seleccionada ---
        String detalle = s.consultaPaqueteVueloRutasCantidadTipo();
        assertTrue(detalle.contains("Cantidad: 2"));
        assertTrue(detalle.contains("Tipo Asiento: Turista"));

        // --- Caso 6: mostrarClientes ---
        s.altaCliente("clientePack", "Juan", "cliente@pack.com", "Perez",
                new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123",
                new byte[]{1}, "Clave123");
        List<DTCliente> clientes = s.mostrarClientes();
        assertFalse(clientes.isEmpty());
        assertEquals("clientePack", clientes.get(0).getNickname());
    }

    @Test
    void testListarAerolineasParaAdministracion() throws Exception {
        limpiarBasePostgres();
        Sistema s = Sistema.getInstance();

        // --- Caso 1: sin aerolíneas ---
        List<DTAerolinea> aerolineasVacias = s.listarAerolineasParaAdministracion();
        assertNotNull(aerolineasVacias);
        assertTrue(aerolineasVacias.isEmpty(), "No debe haber aerolíneas registradas aún");

        // --- Caso 2: con aerolíneas ---
        s.altaAerolinea("aeroTest", "TestAir", "test@air.com", "Descripcion de prueba",
                "https://testair.com", new byte[]{1}, "Clave123");

        List<DTAerolinea> aerolineas = s.listarAerolineasParaAdministracion();
        assertNotNull(aerolineas);
        assertEquals(1, aerolineas.size(), "Debe haber una aerolínea registrada");

        DTAerolinea dto = aerolineas.get(0);
        assertEquals("aeroTest", dto.getNickname());
        assertEquals("TestAir", dto.getNombre());
        assertEquals("test@air.com", dto.getCorreo());
        assertEquals("Descripcion de prueba", dto.getDescripcion());
        assertEquals("https://testair.com", dto.getLinkSitioWeb());
    }

    @Test
    void testPrecargarSistemaCompleto() {
        Sistema s = Sistema.getInstance();

        // Ejecutar precarga completa
        s.precargarSistemaCompleto();

        // Verificar que se cargaron datos
        AeropuertoServicio aeropuertoServicio = new AeropuertoServicio();
        CategoriaServicio categoriaServicio = new CategoriaServicio();
        ClienteServicio clienteServicio = new ClienteServicio();
        AerolineaServicio aerolineaServicio = new AerolineaServicio();
        RutaVueloServicio rutaVueloServicio = new RutaVueloServicio();
        VueloServicio vueloServicio = new VueloServicio();

        assertFalse(aeropuertoServicio.listarAeropuertos().isEmpty(), "Debe haber aeropuertos precargados");
        assertFalse(categoriaServicio.listarCategorias().isEmpty(), "Debe haber categorías precargadas");
        assertFalse(clienteServicio.listarClientes().isEmpty(), "Debe haber clientes precargados");
        assertFalse(aerolineaServicio.listarAerolineas().isEmpty(), "Debe haber aerolíneas precargadas");
        assertFalse(vueloServicio.listarVuelos().isEmpty(), "Debe haber vuelos precargados");
    }

    //RESERVA CON PAQUETE
    @Test
    void testDatoReservaConPaquete() throws Exception {
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
                new DTFecha(1,1,2025),List.of("CategoriaX"),new byte[]{1}, null);
        s.registrarRuta();

        // Confirmar ruta con flujo real de administración ANTES de crear el vuelo
        s.seleccionarAerolineaParaAdministracion("aeroTest");
        s.seleccionarRutaVueloParaAdministracion("RutaAurora");
        Exception exAceptar = assertThrows(IllegalStateException.class, () -> {
            s.aceptarRutaVuelo();
        });
        assertTrue(exAceptar.getMessage().startsWith("SUCCESS:"), "La ruta debe ser aceptada correctamente");

        // Refrescar la aerolínea para que tenga la ruta confirmada en su lista
        AerolineaServicio as = new AerolineaServicio();
        Aerolinea aero = as.buscarAerolineaPorNickname("aeroTest");
        as.actualizarAerolinea(aero);

        // Ahora crear el vuelo con la ruta confirmada
        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaAurora");

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

        // Crear clientes
        ClienteServicio clienteServicio = new ClienteServicio();
        clienteServicio.crearCliente("cliente1","Nombre1","mail1@test.com","Apellido1",
                new DTFecha(1,1,1990),"Uruguay",TipoDoc.CI,"12345678",new byte[]{1},"pass1");
        
        clienteServicio.crearCliente("cliente2","Nombre2","mail2@test.com","Apellido2",
                new DTFecha(2,2,1992),"Uruguay",TipoDoc.CI,"87654321",new byte[]{2},"pass2");

        // Crear paquete
        s.crearPaquete("PackTest", "Paquete completo", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{1});

        // Seleccionar paquete y agregar ruta
        s.seleccionarPaquete("PackTest");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaAurora");
        s.agregarRutaAPaquete(5, TipoAsiento.Turista); // 5 pasajes disponibles

        // Comprar paquete para cliente1
        s.seleccionarCliente("cliente1");
        s.seleccionarPaquete("PackTest");
        s.realizarCompra(new DTFecha(15,10,2025), 500f, new DTFecha(31,12,2025)); // No vencido

        // Obtener el ID del paquete comprado
        PaqueteVueloServicio paqueteServicio = new PaqueteVueloServicio();
        PaqueteVuelo paquete = paqueteServicio.obtenerPaquetePorNombre("PackTest");
        Long paqueteId = paquete.getId();

        // --- IF1: vueloSeleccionadoParaReserva == null ---
        Exception ex1 = assertThrows(IllegalStateException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of("cliente1"),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("IF1 -> " + ex1.getMessage());
        assertTrue(ex1.getMessage().contains("Debe seleccionar un vuelo"));

        // --- IF3: pasajeros vacíos ---
        s.seleccionarVueloParaReserva("VueloAuroraTest");
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of(),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("IF3 -> " + ex3.getMessage());
        assertTrue(ex3.getMessage().contains("No se ha seleccionado ningún pasajero"));

        // --- IF4: cliente principal vacío ---
        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of(""),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("IF4 -> " + ex4.getMessage());
        assertTrue(ex4.getMessage().contains("No se ha seleccionado un cliente principal"));

        // --- IF5: cliente principal inexistente ---
        Exception ex5 = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of("clienteInexistente"),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("IF5 -> " + ex5.getMessage());
        assertTrue(ex5.getMessage().contains("No se encontró el cliente principal"));

        // --- ERROR: paquete no encontrado ---
        Exception exPaquete = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of("cliente1"),new DTFecha(15,10,2025),999L);
        });
        System.out.println("PAQUETE NO ENCONTRADO -> " + exPaquete.getMessage());
        assertTrue(exPaquete.getMessage().contains("No se encontró el paquete con ID"));

        // --- ERROR: paquete pertenece a otro cliente ---
        clienteServicio.crearCliente("cliente3","Nombre3","mail3@test.com","Apellido3",
                new DTFecha(3,3,1993),"Uruguay",TipoDoc.CI,"11111111",new byte[]{3},"pass3");
        
        Exception exOtroCliente = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of("cliente3"),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("OTRO CLIENTE -> " + exOtroCliente.getMessage());
        assertTrue(exOtroCliente.getMessage().contains("No se encontró el paquete con ID"));

        // --- ERROR: paquete vencido ---
        // Crear otro paquete vencido
        s.crearPaquete("PackVencido", "Paquete vencido", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{2});
        s.seleccionarPaquete("PackVencido");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaAurora");
        s.agregarRutaAPaquete(3, TipoAsiento.Turista);
        s.seleccionarCliente("cliente1");
        s.seleccionarPaquete("PackVencido");
        s.realizarCompra(new DTFecha(1,1,2024), 400f, new DTFecha(1,1,2024)); // Vencido
        
        PaqueteVuelo paqueteVencido = paqueteServicio.obtenerPaquetePorNombre("PackVencido");
        Long paqueteVencidoId = paqueteVencido.getId();
        
        Exception exVencido = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,List.of("cliente1"),new DTFecha(15,10,2025),paqueteVencidoId);
        });
        System.out.println("PAQUETE VENCIDO -> " + exVencido.getMessage());
        assertTrue(exVencido.getMessage().contains("El paquete ha vencido"));

        // --- ERROR: no hay disponibilidad en el paquete ---
        Exception exSinDisponibilidad = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Ejecutivo,1,0,List.of("cliente1"),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("SIN DISPONIBILIDAD -> " + exSinDisponibilidad.getMessage());
        assertTrue(exSinDisponibilidad.getMessage().contains("No hay disponibilidad en el paquete"));

        // --- ERROR: cantidad insuficiente en paquete ---
        Exception exCantidadInsuficiente = assertThrows(IllegalArgumentException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,10,0,List.of("cliente1"),new DTFecha(15,10,2025),paqueteId);
        });
        System.out.println("CANTIDAD INSUFICIENTE -> " + exCantidadInsuficiente.getMessage());
        assertTrue(exCantidadInsuficiente.getMessage().contains("No hay suficientes pasajes disponibles en el paquete"));



        // --- Flujo feliz: reserva con paquete ---
        List<String> pasajerosOk = new ArrayList<>();
        pasajerosOk.add("cliente1");
        pasajerosOk.add("cliente2");
        
        Exception exFinal = assertThrows(IllegalStateException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,2,0,pasajerosOk,new DTFecha(20,10,2025),paqueteId);
        });
        System.out.println("SUCCESS -> " + exFinal.getMessage());
        assertTrue(exFinal.getMessage().startsWith("SUCCESS:"));
        assertTrue(exFinal.getMessage().contains("RESERVA CON PAQUETE REALIZADA CON ÉXITO"));
        assertTrue(exFinal.getMessage().contains("Paquete utilizado: PackTest"));
    }

    // OBTENER PAQUETES CLIENTE PARA RUTA
    @Test
    void testObtenerPaquetesClienteParaRuta() throws Exception {
        Sistema s = Sistema.getInstance();

        // --- Setup base ---
        s.altaAerolinea("aeroTest","TestAir","test@air.com","Desc","https://test.com",new byte[]{1},"Clave123");
        s.seleccionarAerolineaRet("aeroTest");

        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("VillaRodriguezTest","Uruguay","AeropuertoVR","Desc1","https://c1.com",new DTFecha(1,1,2024));
        cs.registrarCiudad("VillaPerezTest","Uruguay","AeropuertoVP","Desc2","https://c2.com",new DTFecha(2,2,2024));

        s.altaCategoria("CategoriaX");
        s.ingresarDatosRuta("RutaTest","Desc ruta",100,200,50,
                "VillaRodriguezTest","VillaPerezTest",
                new DTFecha(1,1,2025),List.of("CategoriaX"),new byte[]{1}, null);
        s.registrarRuta();

        // Confirmar ruta con flujo real de administración
        s.seleccionarAerolineaParaAdministracion("aeroTest");
        s.seleccionarRutaVueloParaAdministracion("RutaTest");
        Exception exAceptar = assertThrows(IllegalStateException.class, () -> {
            s.aceptarRutaVuelo();
        });
        assertTrue(exAceptar.getMessage().startsWith("SUCCESS:"), "La ruta debe ser aceptada correctamente");

        // Refrescar la aerolínea para que tenga la ruta confirmada en su lista
        AerolineaServicio as = new AerolineaServicio();
        Aerolinea aero = as.buscarAerolineaPorNickname("aeroTest");
        as.actualizarAerolinea(aero);

        // Crear clientes
        ClienteServicio clienteServicio = new ClienteServicio();
        clienteServicio.crearCliente("clienteTest","NombreTest","mailtest@test.com","ApellidoTest",
                new DTFecha(1,1,1990),"Uruguay",TipoDoc.CI,"12345678",new byte[]{1},"passTest");

        // Crear paquete válido que incluye la ruta
        s.crearPaquete("PackValido", "Paquete válido", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{1});

        s.seleccionarPaquete("PackValido");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaTest");
        s.agregarRutaAPaquete(5, TipoAsiento.Turista); // 5 pasajes disponibles

        // Comprar paquete para clienteTest
        s.seleccionarCliente("clienteTest");
        s.seleccionarPaquete("PackValido");
        s.realizarCompra(new DTFecha(15,10,2025), 450f, new DTFecha(31,12,2025)); // No vencido

        // --- Caso 1: Cliente inexistente ---
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {
            s.obtenerPaquetesClienteParaRuta("clienteInexistente", "RutaTest");
        });
        assertEquals("Cliente no encontrado: clienteInexistente", ex1.getMessage());

        // --- Caso 2: Cliente sin paquetes comprados ---
        clienteServicio.crearCliente("clienteSinPaquetes","NombreSin","mailsin@test.com","ApellidoSin",
                new DTFecha(2,2,1992),"Uruguay",TipoDoc.CI,"87654321",new byte[]{2},"passSin");
        
        List<DTPaqueteVuelos> paquetesVacios = s.obtenerPaquetesClienteParaRuta("clienteSinPaquetes", "RutaTest");
        assertNotNull(paquetesVacios);
        assertTrue(paquetesVacios.isEmpty(), "El cliente sin paquetes debe devolver lista vacía");

        // --- Caso 3: Paquete que no incluye la ruta especificada ---
        // Crear otra ruta
        s.seleccionarAerolineaRet("aeroTest");
        s.ingresarDatosRuta("RutaDiferente","Desc ruta diferente",100,200,50,
                "VillaRodriguezTest","VillaPerezTest",
                new DTFecha(1,1,2025),List.of("CategoriaX"),new byte[]{2}, null);
        s.registrarRuta();

        s.seleccionarAerolineaParaAdministracion("aeroTest");
        s.seleccionarRutaVueloParaAdministracion("RutaDiferente");
        exAceptar = assertThrows(IllegalStateException.class, () -> {
            s.aceptarRutaVuelo();
        });
        assertTrue(exAceptar.getMessage().startsWith("SUCCESS:"));

        as.actualizarAerolinea(aero);

        // Crear paquete con otra ruta
        s.crearPaquete("PackOtraRuta", "Paquete con otra ruta", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{3});

        s.seleccionarPaquete("PackOtraRuta");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaDiferente");
        s.agregarRutaAPaquete(3, TipoAsiento.Turista);

        s.seleccionarCliente("clienteTest");
        s.seleccionarPaquete("PackOtraRuta");
        s.realizarCompra(new DTFecha(15,10,2025), 300f, new DTFecha(31,12,2025));

        // Buscar paquetes para RutaTest, no debe incluir PackOtraRuta
        List<DTPaqueteVuelos> paquetesRutaTest = s.obtenerPaquetesClienteParaRuta("clienteTest", "RutaTest");
        assertEquals(1, paquetesRutaTest.size(), "Debe encontrar solo PackValido");
        assertEquals("PackValido", paquetesRutaTest.get(0).getNombre());

        // --- Caso 4: Paquete vencido ---
        s.crearPaquete("PackVencido", "Paquete vencido", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{4});

        s.seleccionarPaquete("PackVencido");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaTest");
        s.agregarRutaAPaquete(2, TipoAsiento.Turista);

        s.seleccionarCliente("clienteTest");
        s.seleccionarPaquete("PackVencido");
        s.realizarCompra(new DTFecha(1,1,2024), 200f, new DTFecha(1,1,2024)); // Vencido

        // Buscar paquetes para RutaTest, no debe incluir PackVencido
        List<DTPaqueteVuelos> paquetesNoVencidos = s.obtenerPaquetesClienteParaRuta("clienteTest", "RutaTest");
        assertEquals(1, paquetesNoVencidos.size(), "No debe incluir paquetes vencidos");
        assertEquals("PackValido", paquetesNoVencidos.get(0).getNombre());

        // --- Caso 5: Flujo feliz - paquete con cantidad disponible ---
        List<DTPaqueteVuelos> paquetesDisponibles = s.obtenerPaquetesClienteParaRuta("clienteTest", "RutaTest");
        assertNotNull(paquetesDisponibles);
        assertFalse(paquetesDisponibles.isEmpty(), "Debe encontrar paquetes disponibles");
        assertEquals("PackValido", paquetesDisponibles.get(0).getNombre());
        assertEquals(5, paquetesDisponibles.get(0).getCantidadDisponible(), "Debe tener 5 pasajes disponibles");

        // --- Caso 6: Paquete con cantidad parcialmente usada ---
        // Crear otro paquete
        s.crearPaquete("PackParcial", "Paquete parcialmente usado", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{5});

        s.seleccionarPaquete("PackParcial");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaTest");
        s.agregarRutaAPaquete(10, TipoAsiento.Turista); // 10 pasajes disponibles

        s.seleccionarCliente("clienteTest");
        s.seleccionarPaquete("PackParcial");
        s.realizarCompra(new DTFecha(15,10,2025), 900f, new DTFecha(31,12,2025));

        // Crear un vuelo para usar el paquete parcialmente
        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaTest");
        
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

        s.ingresarDatosVuelo("VueloTest",new DTFecha(10,10,2025),new DTHora(12,30),new DTHora(2,0),
                100,20,new DTFecha(9,9,2025),dtRuta,new byte[]{1});
        s.seleccionarAerolineaRet("aeroTest");
        s.darAltaVuelo();

        // Usar parcialmente el paquete (3 pasajes de 10)
        s.seleccionarVueloParaReserva("VueloTest");
        PaqueteVueloServicio paqueteServicio = new PaqueteVueloServicio();
        PaqueteVuelo paqueteParcial = paqueteServicio.obtenerPaquetePorNombre("PackParcial");
        Long paqueteParcialId = paqueteParcial.getId();
        
        List<String> pasajerosParcial = new ArrayList<>();
        pasajerosParcial.add("clienteTest");
        pasajerosParcial.add("clienteSinPaquetes");
        Exception exReservaParcial = assertThrows(IllegalStateException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,2,0,pasajerosParcial,new DTFecha(21,10,2025),paqueteParcialId);
        });
        assertTrue(exReservaParcial.getMessage().startsWith("SUCCESS:"));

        // Buscar paquetes para RutaTest, debe incluir PackParcial con 8 disponibles (10 - 2)
        List<DTPaqueteVuelos> paquetesConParcial = s.obtenerPaquetesClienteParaRuta("clienteTest", "RutaTest");
        assertFalse(paquetesConParcial.isEmpty(), "Debe encontrar paquetes");
        
        // Buscar PackParcial en la lista
        DTPaqueteVuelos packParcialEncontrado = null;
        for (DTPaqueteVuelos p : paquetesConParcial) {
            if (p.getNombre().equals("PackParcial")) {
                packParcialEncontrado = p;
                break;
            }
        }
        assertNotNull(packParcialEncontrado, "Debe encontrar PackParcial");
        assertEquals(8, packParcialEncontrado.getCantidadDisponible(), "Debe tener 8 pasajes disponibles (10 - 2)");

        // --- Caso 7: Paquete con cantidad completamente usada (no debe aparecer) ---
        // Crear otro paquete que se usará completamente
        s.crearPaquete("PackCompleto", "Paquete completamente usado", TipoAsiento.Turista, 30, 10,
                new DTFecha(1,1,2025), new byte[]{6});

        s.seleccionarPaquete("PackCompleto");
        s.seleccionarAerolineaPaquete(new DTAerolinea("aeroTest", "", "", "", "", new ArrayList<>(), null, ""));
        s.seleccionarRutaVueloPaquete("RutaTest");
        s.agregarRutaAPaquete(1, TipoAsiento.Turista); // Solo 1 pasaje disponible

        s.seleccionarCliente("clienteTest");
        s.seleccionarPaquete("PackCompleto");
        s.realizarCompra(new DTFecha(15,10,2025), 90f, new DTFecha(31,12,2025));

        // Usar completamente el paquete (1 pasaje de 1)
        PaqueteVuelo paqueteCompleto = paqueteServicio.obtenerPaquetePorNombre("PackCompleto");
        Long paqueteCompletoId = paqueteCompleto.getId();
        
        List<String> pasajerosCompleto = new ArrayList<>();
        pasajerosCompleto.add("clienteTest"); // Cliente principal debe ser el dueño del paquete
        Exception exReservaCompleto = assertThrows(IllegalStateException.class, () -> {
            s.datosReservaConPaquete(TipoAsiento.Turista,1,0,pasajerosCompleto,new DTFecha(22,10,2025),paqueteCompletoId);
        });
        assertTrue(exReservaCompleto.getMessage().startsWith("SUCCESS:"));

        // Buscar paquetes para RutaTest, NO debe incluir PackCompleto
        List<DTPaqueteVuelos> paquetesSinCompleto = s.obtenerPaquetesClienteParaRuta("clienteTest", "RutaTest");
        for (DTPaqueteVuelos p : paquetesSinCompleto) {
            assertNotEquals("PackCompleto", p.getNombre(), "No debe incluir paquetes completamente usados");
        }
        
        // Verificar que PackCompleto no está en la lista
        boolean packCompletoEncontrado = paquetesSinCompleto.stream()
                .anyMatch(p -> p.getNombre().equals("PackCompleto"));
        assertFalse(packCompletoEncontrado, "PackCompleto no debe estar en la lista porque se usó completamente");
        
        // Verificar que los otros paquetes válidos SÍ están
        assertTrue(paquetesSinCompleto.size() >= 2, "Debe incluir PackValido y PackParcial");
        boolean packValidoEncontrado = paquetesSinCompleto.stream()
                .anyMatch(p -> p.getNombre().equals("PackValido"));
        assertTrue(packValidoEncontrado, "Debe incluir PackValido");
        boolean packParcialEncontradoo = paquetesSinCompleto.stream()
                .anyMatch(p -> p.getNombre().equals("PackParcial"));
        assertTrue(packParcialEncontradoo, "Debe incluir PackParcial");
    }
}
