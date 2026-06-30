package canvia.modulo08auth;

import canvia.support.model.Booking;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

/**
 * MÓDULO 8 — Autenticación + actualizar + borrar (completa el CRUD).
 *
 * El token y el id de la reserva viven en la memoria del actor (remember/recall), por eso
 * los steps son tan limpios: solo orquestan Tasks.
 */
public class AuthCrudStepDefinitions {

    @Y("Giancarlo se autentica como administrador")
    public void seAutentica() {
        theActorInTheSpotlight().attemptsTo(Autenticarse.comoAdministrador());
    }

    @Y("existe una reserva creada por Giancarlo")
    public void existeUnaReserva() {
        Booking datos = Booking.unaReserva()
                .conNombre("Reserva", "Base")
                .conPrecio(100)
                .conDeposito(true)
                .conFechas("2024-04-01", "2024-04-03")
                .conNecesidades("Desayuno");

        theActorInTheSpotlight().attemptsTo(CrearReserva.con(datos));
    }

    @Cuando("actualiza esa reserva con el nombre {string} y precio {int}")
    public void actualizaLaReserva(String nombre, int precio) {
        Booking nuevos = Booking.unaReserva()
                .conNombre(nombre, "Actualizado")
                .conPrecio(precio)
                .conDeposito(true)
                .conFechas("2024-04-01", "2024-04-03")
                .conNecesidades("Cena");

        theActorInTheSpotlight().attemptsTo(ActualizarReserva.con(nuevos));
    }

    @Entonces("la reserva actualizada refleja el nombre {string}")
    public void laReservaActualizadaRefleja(String nombreEsperado) {
        theActorInTheSpotlight().should(
                seeThatResponse("el nombre se actualizó",
                        response -> response.statusCode(200)
                                .body("firstname", equalTo(nombreEsperado))));
    }

    @Cuando("borra esa reserva")
    public void borraLaReserva() {
        theActorInTheSpotlight().attemptsTo(BorrarReserva.laRecordada());
    }

    @Entonces("la reserva ya no existe")
    public void laReservaYaNoExiste() {
        int id = theActorInTheSpotlight().recall(CrearReserva.ID_RESERVA);
        theActorInTheSpotlight().attemptsTo(Get.resource("/booking/" + id));
        theActorInTheSpotlight().should(
                seeThatResponse("ya no se encuentra",
                        response -> response.statusCode(404)));
    }
}
