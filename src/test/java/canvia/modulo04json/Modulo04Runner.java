package canvia.modulo04json;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner del MÓDULO 4.
 *   .\gradlew.bat test --tests "canvia.modulo04json.Modulo04Runner"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-04-json")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo04json")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
public class Modulo04Runner {
}
