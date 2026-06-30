package canvia.modulo07integracion;

import canvia.support.model.Booking;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/**
 * Question que deserializa la última respuesta (GET /booking/{id}) a un POJO {@link Booking}.
 *
 * GET /booking/{id} devuelve el objeto reserva en la raíz, así que usamos `.as(Booking.class)`.
 * Se consume con `actor.asksFor(DatosDeLaReserva.recuperada())`.
 */
public class DatosDeLaReserva implements Question<Booking> {

    public static DatosDeLaReserva recuperada() {
        return new DatosDeLaReserva();
    }

    @Override
    public Booking answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(Booking.class);
    }
}
