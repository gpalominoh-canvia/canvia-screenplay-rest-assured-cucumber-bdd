package canvia.modulo08auth;

import canvia.support.model.Booking;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que crea una reserva base (POST no requiere auth) y recuerda su id.
 * Sirve de precondición para actualizar/borrar en este módulo.
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
