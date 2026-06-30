package canvia.modulo02cucumber;

import canvia.support.ApiConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * MÓDULO 2 — Mecánica de Cucumber con REST Assured (vía SerenityRest).
 *
 * Aquí NO usamos todavía Screenplay: el foco es entender Cucumber (steps, parámetros,
 * Background, Scenario Outline, Data Tables, hooks). Screenplay llega en el Módulo 5.
 *
 * Estado compartido entre steps del MISMO escenario: campos de instancia. Cucumber crea
 * una instancia nueva de esta clase por cada escenario, así que no hay fugas de estado.
 */
public class ConsultarBookingsStepDefinitions {

    private Response respuesta;
    private int idReservaExistente;

    // --- GIVEN -------------------------------------------------------------

    @Dado("que la API de bookings está disponible")
    public void laApiEstaDisponible() {
        // Configuración mínima compartida por el Background.
        // (En SerenityRest definimos el baseUri en cada request.)
    }

    @Dado("que existe al menos una reserva")
    public void queExisteAlMenosUnaReserva() {
        Response lista = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .when().get("/booking");
        lista.then().statusCode(200);
        // Tomamos el id de la primera reserva real para no depender de ids fijos.
        idReservaExistente = lista.jsonPath().getInt("[0].bookingid");
    }

    // --- WHEN --------------------------------------------------------------

    @Cuando("consulto la lista de reservas")
    public void consultoLaListaDeReservas() {
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .when().get("/booking");
    }

    @Cuando("consulto esa reserva por su identificador")
    public void consultoEsaReserva() {
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .when().get("/booking/{id}", idReservaExistente);
    }

    @Cuando("filtro las reservas por el nombre {string}")
    public void filtroPorNombre(String nombre) {
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .queryParam("firstname", nombre)
                .when().get("/booking");
    }

    @Cuando("filtro reservas con los siguientes criterios:")
    public void filtroConCriterios(DataTable tabla) {
        // La tabla tiene una cabecera y una fila: la convertimos a Map columna->valor.
        Map<String, String> criterios = tabla.asMaps().get(0);
        respuesta = SerenityRest.given()
                .baseUri(ApiConfig.BASE_URL)
                .queryParams(criterios)
                .when().get("/booking");
    }

    // --- THEN --------------------------------------------------------------

    @Entonces("obtengo una respuesta exitosa")
    public void obtengoRespuestaExitosa() {
        respuesta.then().statusCode(200);
    }

    @Entonces("la lista contiene al menos {int} reserva(s)")
    public void laListaContieneAlMenos(int minimo) {
        List<?> ids = respuesta.jsonPath().getList("bookingid");
        assertThat(ids).hasSizeGreaterThanOrEqualTo(minimo);
    }

    @Entonces("la reserva tiene nombre y apellido")
    public void laReservaTieneNombreYApellido() {
        respuesta.then()
                .body("firstname", notNullValue())
                .body("lastname", notNullValue());
    }
}
