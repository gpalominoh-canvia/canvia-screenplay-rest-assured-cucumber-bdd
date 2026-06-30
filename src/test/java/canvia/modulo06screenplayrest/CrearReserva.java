package canvia.modulo06screenplayrest;

import canvia.support.model.Booking;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que crea una reserva enviando un POJO {@link Booking} como JSON.
 *
 * La interaction Post permite personalizar la request con `.with(req -> ...)`, igual que
 * en REST Assured: contentType, accept, body, headers...
 */
public class CrearReserva implements Task {

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
    }
}
