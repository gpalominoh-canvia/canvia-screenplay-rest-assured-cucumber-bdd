package canvia.modulo07integracion;

import canvia.support.model.Booking;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * MÓDULO 7 — Flujo end-to-end encadenando Tasks y leyendo el resultado con una Question.
 *
 * Observa que NO guardamos el id en un campo: vive en la memoria del actor (Módulo 7).
 * La verificación final usa `asksFor(...)` para obtener un POJO y validar con AssertJ.
 */
public class FlujoE2EStepDefinitions {

    @Cuando("crea la reserva de {string} {string} por {int}")
    public void creaLaReserva(String nombre, String apellido, int precio) {
        Booking datos = Booking.unaReserva()
                .conNombre(nombre, apellido)
                .conPrecio(precio)
                .conDeposito(false)
                .conFechas("2024-03-01", "2024-03-07")
                .conNecesidades("Cena");

        theActorInTheSpotlight().attemptsTo(CrearReserva.con(datos));
    }

    @Y("consulta la reserva que acaba de crear")
    public void consultaLaReserva() {
        theActorInTheSpotlight().attemptsTo(ConsultarReservaRecordada.laQueAcabaDeCrear());
    }

    @Entonces("la reserva recuperada tiene nombre {string} y precio {int}")
    public void laReservaRecuperadaTiene(String nombreEsperado, int precioEsperado) {
        Booking recuperada = theActorInTheSpotlight().asksFor(DatosDeLaReserva.recuperada());

        assertThat(recuperada.getFirstname()).isEqualTo(nombreEsperado);
        assertThat(recuperada.getTotalprice()).isEqualTo(precioEsperado);
    }
}
