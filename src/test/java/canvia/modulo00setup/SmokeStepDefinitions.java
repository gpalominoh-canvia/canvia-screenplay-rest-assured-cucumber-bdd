package canvia.modulo00setup;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

/**
 * Steps del MÓDULO 0 (smoke). Escrito en estilo Screenplay (se explica a fondo en el
 * Módulo 5). Por ahora solo prueba que el entorno compila y se conecta a la API.
 *
 * La preparación del Stage vive en {@link canvia.hooks.StageHooks}.
 */
public class SmokeStepDefinitions {

    @Dado("que la API de bookings está disponible")
    public void laApiEstaDisponible() {
        theActorCalled("QA").whoCan(
                CallAnApi.at("https://restful-booker.herokuapp.com"));
    }

    @Cuando("consulto el estado del servicio")
    public void consultoElEstado() {
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/ping"));
    }

    @Entonces("el servicio responde con código {int}")
    public void elServicioResponde(int codigoEsperado) {
        theActorInTheSpotlight().should(
                seeThatResponse("el código de estado es " + codigoEsperado,
                        response -> response.statusCode(codigoEsperado)));
    }
}
