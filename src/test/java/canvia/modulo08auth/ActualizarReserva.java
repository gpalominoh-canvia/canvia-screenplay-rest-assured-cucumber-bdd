package canvia.modulo08auth;

import canvia.support.model.Booking;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que ACTUALIZA (PUT) la reserva recordada. PUT requiere autenticación: enviamos el
 * token guardado como cookie `token`.
 */
public class ActualizarReserva implements Task {

    private final Booking nuevosDatos;

    public ActualizarReserva(Booking nuevosDatos) {
        this.nuevosDatos = nuevosDatos;
    }

    public static ActualizarReserva con(Booking nuevosDatos) {
        return instrumented(ActualizarReserva.class, nuevosDatos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int id = actor.recall(CrearReserva.ID_RESERVA);
        String token = actor.recall(Autenticarse.TOKEN);

        actor.attemptsTo(
                Put.to("/booking/" + id)
                        .with(request -> request
                                .contentType("application/json")
                                .accept("application/json")
                                .cookie("token", token)
                                .body(nuevosDatos)));
    }
}
