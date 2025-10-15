package logica.clase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {

    @Test
    void testValidarContrasena() {
        // Caso válido
        assertTrue(Sistema.validarContrasena("Abcdef1"));
        // Caso inválido
        assertFalse(Sistema.validarContrasena("abc"));
    }

    @Test
    void testEsNombreValido() {
        // Caso válido
        assertTrue(Sistema.esNombreValido("Juan Pérez"));
        // Caso inválido
        assertFalse(Sistema.esNombreValido("Jo"));
    }
}