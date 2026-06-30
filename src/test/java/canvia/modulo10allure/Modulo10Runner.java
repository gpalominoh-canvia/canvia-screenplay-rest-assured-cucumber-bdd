package canvia.modulo10allure;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner del MÓDULO 10. Registra DOS plugins de Cucumber separados por coma:
 *  - el reporter de Serenity (reporte viviente)
 *  - el adapter de Allure (genera build/allure-results)
 *
 * Tras ejecutar, genera el HTML de Allure con:
 *   .\gradlew.bat allureReport     (o allureServe para verlo en el navegador)
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-10-allure")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo10allure,canvia.shared,canvia.hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class Modulo10Runner {
}
