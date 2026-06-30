package canvia.modulo05screenplay;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Una TASK (tarea) de Screenplay: representa un objetivo de negocio del actor.
 *
 * Una Task agrupa una o más Interactions (aquí, un Get). Se escribe en lenguaje de
 * negocio ("consultar el estado") y esconde el detalle técnico.
 *
 * `instrumented(...)` deja que Serenity cree la instancia y la registre en el reporte.
 */
public class ConsultarEstado implements Task {

    public static ConsultarEstado delServicio() {
        return instrumented(ConsultarEstado.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/ping"));
    }
}
