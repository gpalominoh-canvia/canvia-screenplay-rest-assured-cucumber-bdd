package canvia.modulo05screenplay;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner del MÓDULO 5. Glue = código del módulo + steps compartidos + hooks.
 *   .\gradlew.bat test --tests "canvia.modulo05screenplay.Modulo05Runner"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-05-screenplay")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo05screenplay,canvia.shared,canvia.hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
public class Modulo05Runner {
}
