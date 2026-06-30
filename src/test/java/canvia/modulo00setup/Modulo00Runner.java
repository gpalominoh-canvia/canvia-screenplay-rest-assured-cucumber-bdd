package canvia.modulo00setup;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner del MÓDULO 0. Ejecuta SOLO los features de este módulo.
 *
 * Demostrar este módulo aislado:
 *   .\gradlew.bat test --tests "canvia.modulo00setup.Modulo00Runner"
 *
 * - SelectClasspathResource: features de ESTE módulo.
 * - glue = código del módulo + hooks compartidos.
 * - plugin = reporter de Serenity (obligatorio, si no falla con "No BaseStepListener").
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-00-setup")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo00setup,canvia.hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
public class Modulo00Runner {
}
