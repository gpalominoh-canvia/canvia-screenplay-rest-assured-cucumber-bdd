package canvia.modulo03restassured;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner del MÓDULO 3.
 *   .\gradlew.bat test --tests "canvia.modulo03restassured.Modulo03Runner"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-03-rest-assured")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo03restassured")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
public class Modulo03Runner {
}
