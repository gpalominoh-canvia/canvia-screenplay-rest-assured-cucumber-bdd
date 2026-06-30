package canvia.modulo08auth;

import canvia.support.ApiConfig;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de AUTENTICACIÓN: POST /auth con usuario y contraseña, y guarda el token devuelto
 * en la memoria del actor para usarlo luego en operaciones protegidas (PUT/DELETE).
 *
 * El body se arma con un Map; REST Assured lo serializa a JSON con Jackson.
 */
public class Autenticarse implements Task {

    public static final String TOKEN = "token";

    public static Autenticarse comoAdministrador() {
        return instrumented(Autenticarse.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/auth")
                        .with(request -> request
                                .contentType("application/json")
                                .body(Map.of(
                                        "username", ApiConfig.ADMIN_USER,
                                        "password", ApiConfig.ADMIN_PASSWORD))));

        String token = SerenityRest.lastResponse().jsonPath().getString("token");
        actor.remember(TOKEN, token);
    }
}
