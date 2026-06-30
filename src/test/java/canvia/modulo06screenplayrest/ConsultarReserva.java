package canvia.modulo06screenplayrest;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que consulta una reserva por su identificador.
 *
 * El id se pasa al construir la Task; la interaction Get sustituye el {id} en la ruta.
 */
public class ConsultarReserva implements Task {

    private final int id;

    public ConsultarReserva(int id) {
        this.id = id;
    }

    public static ConsultarReserva conId(int id) {
        return instrumented(ConsultarReserva.class, id);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/booking/" + id)
                        .with(request -> request.accept("application/json")));
    }
}
