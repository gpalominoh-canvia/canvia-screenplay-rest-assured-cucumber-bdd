package canvia.modulo08auth;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que BORRA (DELETE) la reserva recordada. También requiere el token como cookie.
 * restful-booker responde 201 Created al borrar correctamente.
 */
public class BorrarReserva implements Task {

    public static BorrarReserva laRecordada() {
        return instrumented(BorrarReserva.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int id = actor.recall(CrearReserva.ID_RESERVA);
        String token = actor.recall(Autenticarse.TOKEN);

        actor.attemptsTo(
                Delete.from("/booking/" + id)
                        .with(request -> request.cookie("token", token)));
    }
}
