package canvia.modulo05screenplay;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * MÓDULO 5 — Steps en estilo Screenplay.
 *
 * Fíjate en el patrón: el actor "intenta" Tasks/Interactions (attemptsTo) y luego
 * "debería" ver algo (should). El "Dado que ... puede consumir la API" vive en
 * {@link canvia.shared.ScreenplaySteps}.
 */
public class EstadoYReservasStepDefinitions {

    @Cuando("consulta el estado del servicio")
    public void consultaElEstado() {
        // Usamos una Task de dominio (mejor que llamar a la Interaction directamente).
        theActorInTheSpotlight().attemptsTo(
                ConsultarEstado.delServicio());
    }

    @Cuando("pide la lista de reservas")
    public void pideLaLista() {
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/booking"));
    }

    @Entonces("el servicio responde con código {int}")
    public void elServicioResponde(int codigo) {
        theActorInTheSpotlight().should(
                seeThatResponse("el código es " + codigo,
                        response -> response.statusCode(codigo)));
    }

    @Entonces("ve que hay al menos {int} reserva(s)")
    public void veQueHayAlMenos(int minimo) {
        // Usamos una Question custom + un matcher de Hamcrest.
        theActorInTheSpotlight().should(
                seeThat(CantidadDeReservas.total(), greaterThanOrEqualTo(minimo)));
    }
}
