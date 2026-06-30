package canvia.modulo07integracion;

import canvia.support.model.Booking;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que crea una reserva y GUARDA el id en la memoria del actor (remember).
 *
 * Así, otras Tasks del mismo escenario pueden recuperar el id con `actor.recall(...)`
 * sin pasarlo a mano entre steps. Es la forma idiomática de compartir estado en Screenplay.
 */
public class CrearReserva implements Task {

    public static final String ID_RESERVA = "idReserva";

    private final Booking datos;

    public CrearReserva(Booking datos) {
        this.datos = datos;
    }

    public static CrearReserva con(Booking datos) {
        return instrumented(CrearReserva.class, datos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/booking")
                        .with(request -> request
                                .contentType("application/json")
                                .accept("application/json")
                                .body(datos)));

        int id = SerenityRest.lastResponse().jsonPath().getInt("bookingid");
        actor.remember(ID_RESERVA, id);
    }
}
