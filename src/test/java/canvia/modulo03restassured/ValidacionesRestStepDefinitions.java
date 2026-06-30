package canvia.modulo03restassured;

import canvia.support.ApiConfig;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * MÓDULO 3 — REST Assured esencial.
 *
 * REST Assured sigue el patrón GIVEN / WHEN / THEN (¡cuidado, distinto de Gherkin!):
 *   given()  → configuras la request (baseUri, headers, body, params)
 *   when()   → ejecutas el verbo HTTP (get, post, put, delete)
 *   then()   → validas la respuesta (statusCode, body, header, time...)
 *
 * Usamos SerenityRest en vez de RestAssured directo para que cada llamada quede
 * registrada en el reporte de Serenity.
 */
public class ValidacionesRestStepDefinitions {

    private Response respuesta;
    private int idReserva;

    @Dado("que existe una reserva conocida")
    public void queExisteUnaReservaConocida() {
        Response lista = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .when().get("/booking");
        lista.then().statusCode(200);
        idReserva = lista.jsonPath().getInt("[0].bookingid");
    }

    @Cuando("pido el detalle de esa reserva")
    public void pidoElDetalle() {
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .accept("application/json")
                .when().get("/booking/{id}", idReserva);
    }

    @Cuando("consulto el healthcheck")
    public void consultoHealthcheck() {
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .when().get("/ping");
    }

    @Entonces("el código de estado es {int}")
    public void elCodigoDeEstadoEs(int esperado) {
        respuesta.then().statusCode(esperado);
    }

    @Entonces("la respuesta es de tipo JSON")
    public void laRespuestaEsJson() {
        respuesta.then().contentType("application/json");
    }

    @Entonces("el cuerpo contiene los campos firstname, lastname y totalprice")
    public void elCuerpoContieneLosCampos() {
        respuesta.then()
                .body("firstname", notNullValue())
                .body("lastname", notNullValue())
                .body("totalprice", notNullValue());
    }

    @Entonces("el precio total es mayor o igual a {int}")
    public void elPrecioTotalEsMayorOIgual(int minimo) {
        respuesta.then().body("totalprice", greaterThanOrEqualTo(minimo));
    }

    @Entonces("la respuesta llega en menos de {int} milisegundos")
    public void laRespuestaLlegaEnMenosDe(long milisegundos) {
        long tiempo = respuesta.time();
        org.assertj.core.api.Assertions.assertThat(tiempo).isLessThan(milisegundos);
    }
}
