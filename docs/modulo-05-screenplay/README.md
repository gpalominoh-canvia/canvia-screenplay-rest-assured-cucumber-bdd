# Módulo 5 — El patrón Screenplay

> Objetivo: entender el modelo mental de Screenplay (actores que persiguen objetivos) y
> escribir tus primeras **Tasks** y **Questions**. Este patrón es el corazón del curso.

> 📁 Código de este módulo:
> - Feature: [screenplay-basico.feature](../../src/test/resources/features/modulo-05-screenplay/screenplay-basico.feature)
> - Task: [ConsultarEstado.java](../../src/test/java/canvia/modulo05screenplay/ConsultarEstado.java)
> - Question: [CantidadDeReservas.java](../../src/test/java/canvia/modulo05screenplay/CantidadDeReservas.java)
> - Steps: [EstadoYReservasStepDefinitions.java](../../src/test/java/canvia/modulo05screenplay/EstadoYReservasStepDefinitions.java)
> - Step compartido: [ScreenplaySteps.java](../../src/test/java/canvia/shared/ScreenplaySteps.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo05*"`

---

## 5.1 ¿Por qué Screenplay?

En los Módulos 2-4 metimos la lógica REST **dentro de los step definitions**. Funciona,
pero a escala se vuelve un problema: steps largos, código duplicado, difícil de reutilizar.

Screenplay reorganiza el código alrededor de **actores** que tienen **habilidades** y
realizan **tareas** para cumplir objetivos. Es el sucesor de Page Objects: en lugar de
"páginas", piensas en *quién hace qué*.

> 🎯 Frase clave: **"Un actor, con una habilidad, intenta una tarea y luego se pregunta
> por un resultado."**

---

## 5.2 Los 5 elementos

```
   Actor  ──tiene──►  Ability        (CallAnApi: puede llamar a la API)
     │
     ├──attemptsTo──► Task           (objetivo de negocio: "ConsultarEstado")
     │                  └──► Interaction   (acción concreta: Get.resource("/ping"))
     │
     └──should(seeThat──► Question)   (observa y responde: "¿cuántas reservas hay?")
```

| Elemento | Qué es | Ejemplo en este módulo |
|----------|--------|------------------------|
| **Actor** | quien ejecuta | `Giancarlo` |
| **Ability** | lo que el actor *puede* hacer | `CallAnApi.at(BASE_URL)` |
| **Task** | objetivo de negocio (alto nivel) | `ConsultarEstado.delServicio()` |
| **Interaction** | acción técnica concreta | `Get.resource("/ping")` |
| **Question** | observa el sistema y devuelve un valor | `CantidadDeReservas.total()` |

> 💡 **Task vs Interaction:** una *Interaction* es un ladrillo (un GET). Una *Task* es una
> pared hecha de ladrillos (un objetivo que puede agrupar varias interactions). En
> escenarios reales, tus steps llaman **Tasks**, no Interactions sueltas.

---

## 5.3 El Actor y su Ability

```java
OnStage.theActorCalled("Giancarlo")
       .whoCan(CallAnApi.at("https://restful-booker.herokuapp.com"));
```

- `OnStage` es el "escenario teatral": gestiona los actores.
- `theActorCalled(nombre)` crea/recupera un actor y lo pone en el **spotlight** (foco).
- `whoCan(...)` le concede una habilidad.
- Después, otros steps usan `theActorInTheSpotlight()` sin repetir el nombre.

> El `@Before` que prepara el escenario está en [StageHooks.java](../../src/test/java/canvia/hooks/StageHooks.java)
> y el Given del actor en [ScreenplaySteps.java](../../src/test/java/canvia/shared/ScreenplaySteps.java),
> compartidos por los módulos 5-8.

---

## 5.4 Escribir una Task

```java
public class ConsultarEstado implements Task {

    public static ConsultarEstado delServicio() {
        return instrumented(ConsultarEstado.class);   // Serenity la registra en el reporte
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource("/ping"));
    }
}
```

- Implementa `Task` (o `Performable`).
- El método factory estático (`delServicio()`) la hace legible: `ConsultarEstado.delServicio()`.
- `instrumented(...)` es **obligatorio** para que aparezca en el reporte.
- Dentro de `performAs`, el actor ejecuta interactions u otras tasks.

---

## 5.5 Escribir una Question

```java
public class CantidadDeReservas implements Question<Integer> {

    public static CantidadDeReservas total() { return new CantidadDeReservas(); }

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getList("bookingid").size();
    }
}
```

- Una Question **no valida**: solo devuelve un valor (aquí, un `Integer`).
- La validación la hace el actor: `actor.should(seeThat(pregunta, matcher))`.

```java
theActorInTheSpotlight().should(
    seeThat(CantidadDeReservas.total(), greaterThanOrEqualTo(1)));
```

> 💡 `SerenityRest.lastResponse()` te da la última respuesta REST del actor. Es la forma
> sencilla de inspeccionar resultados en Screenplay REST.

---

## 5.6 attemptsTo y should

Las dos mitades de un escenario Screenplay:

```java
// Acción (When):
theActorInTheSpotlight().attemptsTo(ConsultarEstado.delServicio());

// Verificación (Then):
theActorInTheSpotlight().should(seeThatResponse(r -> r.statusCode(201)));
```

- `attemptsTo(...)` → ejecuta Tasks/Interactions.
- `should(...)` → comprueba Questions o respuestas (`seeThat` / `seeThatResponse`).

---

## 5.7 Ejercicios 🛠️

1. Crea una Task `PedirLaListaDeReservas` (que envuelva `Get.resource("/booking")`) y úsala
   en el step "pide la lista de reservas" en vez de la Interaction directa.
2. Crea una Question `PrimerNombreDeReserva` que devuelva el `firstname` de la primera
   reserva y valida con `seeThat(..., equalTo(...))` (usa un nombre que sepas que existe,
   o primero créala).
3. Añade un segundo actor "Maria" en un escenario y haz que cada uno consulte el estado.
   Observa en el reporte cómo Serenity los distingue.
4. Refactor: mueve la URL base para que la Task no dependa de `ApiConfig` directamente
   (pista: el actor ya tiene la habilidad `CallAnApi`, las interactions la usan solas).

---

## ✅ Checklist del Módulo 5

- [ ] Explico Screenplay con la frase "actor + habilidad + tarea + pregunta".
- [ ] Distingo **Task** (negocio) de **Interaction** (técnica).
- [ ] Escribo una Task con `instrumented(...)` y método factory.
- [ ] Escribo una Question que devuelve un valor y la valido con `seeThat`.
- [ ] Uso `attemptsTo` (acción) y `should` (verificación).
- [ ] `.\gradlew.bat test --tests "*Modulo05*"` queda en verde.

---

> Anterior: **[Módulo 4 — JSON y POJOs](../modulo-04-json/README.md)**
> · Siguiente: **[Módulo 6 — Screenplay + REST](../modulo-06-screenplay-rest/README.md)**
