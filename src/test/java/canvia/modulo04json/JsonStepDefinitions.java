package canvia.modulo04json;

import canvia.support.ApiConfig;
import canvia.support.model.Booking;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * MÓDULO 4 — Serialización y deserialización JSON con POJOs.
 *
 * - SERIALIZAR: pasamos un objeto {@link Booking} en .body(...) y REST Assured lo
 *   convierte a JSON automáticamente (usando Jackson, que está en el classpath).
 * - DESERIALIZAR: leemos parte de la respuesta y la convertimos de vuelta a un POJO con
 *   jsonPath().getObject(ruta, Clase.class).
 */
public class JsonStepDefinitions {

    private Booking reserva;
    private Response respuesta;

    @Dado("que preparo una reserva de {string} {string} por {int}")
    public void quePreparoUnaReserva(String nombre, String apellido, int precio) {
        reserva = Booking.unaReserva()
                .conNombre(nombre, apellido)
                .conPrecio(precio)
                .conDeposito(true)
                .conFechas("2024-01-01", "2024-01-10")
                .conNecesidades("Desayuno");
    }

    @Cuando("la envío para crear la reserva")
    public void laEnvioParaCrear() {
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .contentType("application/json")   // le decimos a la API que enviamos JSON
                .accept("application/json")        // y que queremos JSON de vuelta
                .body(reserva)                     // ← REST Assured serializa el POJO a JSON
                .when().post("/booking");
    }

    @Entonces("la reserva se crea correctamente")
    public void laReservaSeCrea() {
        respuesta.then().statusCode(200);
    }

    @Entonces("la respuesta deserializada conserva el nombre {string}")
    public void laRespuestaConservaElNombre(String nombreEsperado) {
        // La API responde { "bookingid": N, "booking": { ...la reserva... } }
        // Deserializamos SOLO el nodo "booking" de vuelta a un objeto Java.
        Booking creada = respuesta.jsonPath().getObject("booking", Booking.class);
        assertThat(creada.getFirstname()).isEqualTo(nombreEsperado);
    }

    @Entonces("la respuesta incluye un identificador de reserva")
    public void laRespuestaIncluyeId() {
        respuesta.then().body("bookingid", notNullValue());
    }
}
