package canvia.shared;

import canvia.support.ApiConfig;
import io.cucumber.java.es.Dado;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

/**
 * Steps COMPARTIDOS por los módulos de Screenplay (5 al 8).
 *
 * Da de alta un actor y le concede la "habilidad" de llamar a la API. Al nombrarlo, el
 * actor queda en el "spotlight" (foco), de modo que los demás steps usan
 * theActorInTheSpotlight() sin volver a nombrarlo.
 *
 * Se incluye en el glue de cada módulo Screenplay: "canvia.moduloXX,canvia.shared,canvia.hooks".
 */
public class ScreenplaySteps {

    @Dado("que {word} puede consumir la API de bookings")
    public void puedeConsumirLaApi(String nombreActor) {
        OnStage.theActorCalled(nombreActor)
                .whoCan(CallAnApi.at(ApiConfig.BASE_URL));
    }
}
