package logica.clase;

public class MainTest {
    public static void main(String[] args) {
        Factory factory = new Factory();
        IEstacionTrabajo estacion = factory.getEstacionTrabajo();

        System.out.println(estacion.consultarUsuarios()); // deberia de mostrar null. OK
       // System.out.println(estacion.validarNombre("test")); // deberia de mostrar false. OK
    }
}
