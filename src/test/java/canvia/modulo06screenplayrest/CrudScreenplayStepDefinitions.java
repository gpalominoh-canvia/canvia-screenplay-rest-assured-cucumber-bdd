package canvia.modulo06screenplayrest;

import canvia.support.model.Booking;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.rest.SerenityRest;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * MÓDULO 6 — CRUD básico con Screenplay (crear y consultar).
 *
 * Los steps orquestan Tasks de dominio ({@link CrearReserva}, {@link ConsultarReserva}).
 * Guardamos el id de la reserva creada para usarlo en la consulta.
 */
public class CrudScreenplayStepDefinitions {

    private int idCreado;

    @Cuando("crea una reserva a nombre de {string} {string} por {int}")
    public void creaUnaReserva(String nombre, String apellido, int precio) {
        Booking datos = Booking.unaReserva()
                .conNombre(nombre, apellido)
                .conPrecio(precio)
                .conDeposito(true)
                .conFechas("2024-02-01", "2024-02-05")
                .conNecesidades("Almuerzo");

        theActorInTheSpotlight().attemptsTo(CrearReserva.con(datos));

        // La respuesta de POST /booking incluye el bookingid generado.
        idCreado = SerenityRest.lastResponse().jsonPath().getInt("bookingid");
    }

    @Entonces("la reserva se crea con un identificador")
    public void laReservaSeCreaConId() {
        theActorInTheSpotlight().should(
                seeThatResponse("incluye bookingid",
                        response -> response.body("bookingid", notNullValue())));
    }

    @Entonces("al consultarla conserva el nombre {string}")
    public void alConsultarlaConservaElNombre(String nombreEsperado) {
        theActorInTheSpotlight().attemptsTo(ConsultarReserva.conId(idCreado));
        theActorInTheSpotlight().should(
                seeThatResponse("el nombre coincide",
                        response -> response.body("firstname", equalTo(nombreEsperado))));
    }
}
