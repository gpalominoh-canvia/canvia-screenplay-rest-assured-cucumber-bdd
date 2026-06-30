package canvia.hooks;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Hook COMPARTIDO por todos los módulos.
 *
 * Antes de cada escenario prepara el "escenario teatral" (Stage) de Screenplay con un
 * Cast capaz de llamar APIs. Así ningún módulo tiene que repetir esta preparación.
 *
 * Cada Runner de módulo incluye este paquete (canvia.hooks) en su glue.
 */
public class StageHooks {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }
}
