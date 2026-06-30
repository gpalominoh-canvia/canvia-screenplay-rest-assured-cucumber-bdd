package canvia.modulo06screenplayrest;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner del MÓDULO 6.
 *   .\gradlew.bat test --tests "canvia.modulo06screenplayrest.Modulo06Runner"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-06-screenplay-rest")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo06screenplayrest,canvia.shared,canvia.hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
public class Modulo06Runner {
}
