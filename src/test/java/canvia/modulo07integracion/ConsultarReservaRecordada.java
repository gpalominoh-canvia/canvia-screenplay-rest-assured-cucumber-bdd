package canvia.modulo07integracion;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task que consulta la reserva cuyo id quedó GUARDADO en la memoria del actor.
 * Recupera el id con `actor.recall(...)` — no necesita parámetros.
 */
public class ConsultarReservaRecordada implements Task {

    public static ConsultarReservaRecordada laQueAcabaDeCrear() {
        return instrumented(ConsultarReservaRecordada.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int id = actor.recall(CrearReserva.ID_RESERVA);
        actor.attemptsTo(
                Get.resource("/booking/" + id)
                        .with(request -> request.accept("application/json")));
    }
}
