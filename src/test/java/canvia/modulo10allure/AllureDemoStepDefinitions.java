package canvia.modulo10allure;

import canvia.support.ApiConfig;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * MÓDULO 10 — Steps de demostración para Allure.
 *
 * Son escenarios sencillos en estilo Screenplay; lo interesante de este módulo NO es el
 * código sino la CONFIGURACIÓN (build.gradle + allure.properties + doble plugin en el
 * Runner) que hace que, además del reporte Serenity, se genere el reporte Allure.
 */
public class AllureDemoStepDefinitions {

    @Cuando("consulta el estado del servicio para Allure")
    public void consultaEstado() {
        theActorInTheSpotlight().attemptsTo(Get.resource("/ping"));
    }

    @Entonces("el servicio responde con código {int} en Allure")
    public void servicioResponde(int codigo) {
        theActorInTheSpotlight().should(
                seeThatResponse("código " + codigo,
                        response -> response.statusCode(codigo)));
    }

    @Cuando("pide la lista de reservas para Allure")
    public void pideLista() {
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/booking").with(req -> req.baseUri(ApiConfig.BASE_URL)));
    }

    @Entonces("hay al menos {int} reserva(s) en Allure")
    public void hayReservas(int minimo) {
        int total = SerenityRest.lastResponse().jsonPath().getList("bookingid").size();
        assertThat(total).isGreaterThanOrEqualTo(minimo);
    }
}
