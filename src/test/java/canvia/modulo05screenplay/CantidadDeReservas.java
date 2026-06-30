package canvia.modulo05screenplay;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/**
 * Una QUESTION (pregunta) de Screenplay: el actor "observa" el estado del sistema y
 * devuelve un valor. Las Questions NO validan; solo responden. La validación se hace
 * con `actor.should(seeThat(pregunta, matcher))`.
 *
 * Aquí leemos la última respuesta REST y contamos las reservas.
 */
public class CantidadDeReservas implements Question<Integer> {

    public static CantidadDeReservas total() {
        return new CantidadDeReservas();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse()
                .jsonPath()
                .getList("bookingid")
                .size();
    }
}
