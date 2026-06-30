package canvia.support;

/**
 * Configuración compartida de la API objetivo del curso.
 *
 * En el Módulo 9 aprenderás a leer esta URL desde `serenity.conf` (para soportar
 * varios entornos). Por ahora la centralizamos aquí para no repetirla.
 */
public final class ApiConfig {

    public static final String BASE_URL = "https://restful-booker.herokuapp.com";

    // Credenciales públicas de la API de práctica (NO son secretos reales).
    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "password123";

    private ApiConfig() {
        // Clase de utilidad: no se instancia.
    }
}
