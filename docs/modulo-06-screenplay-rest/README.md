# Módulo 6 — Screenplay + REST con Serenity

> Objetivo: usar las **interactions REST** de Serenity Screenplay (`Get`, `Post`, `Put`,
> `Delete`) dentro de Tasks de dominio, y personalizar las requests con `.with(...)`.

> 📁 Código de este módulo:
> - Feature: [crud-screenplay.feature](../../src/test/resources/features/modulo-06-screenplay-rest/crud-screenplay.feature)
> - Tasks: [CrearReserva.java](../../src/test/java/canvia/modulo06screenplayrest/CrearReserva.java) · [ConsultarReserva.java](../../src/test/java/canvia/modulo06screenplayrest/ConsultarReserva.java)
> - Steps: [CrudScreenplayStepDefinitions.java](../../src/test/java/canvia/modulo06screenplayrest/CrudScreenplayStepDefinitions.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo06*"`

---

## 6.1 Las interactions REST de Serenity

Serenity Screenplay trae interactions listas para cada verbo HTTP:

| Verbo | Interaction | Uso |
|-------|-------------|-----|
| GET | `Get.resource("/booking")` | consultar |
| POST | `Post.to("/booking")` | crear |
| PUT | `Put.to("/booking/1")` | reemplazar |
| PATCH | `Patch.to("/booking/1")` | modificar parcial |
| DELETE | `Delete.from("/booking/1")` | borrar |

Todas requieren que el actor tenga la habilidad `CallAnApi` (que ya le dimos en el `Dado`).

---

## 6.2 Personalizar la request con `.with(...)`

`.with(request -> ...)` te da acceso al `RequestSpecification` de REST Assured, así que
puedes configurar todo lo del Módulo 3:

```java
Post.to("/booking")
    .with(request -> request
        .contentType("application/json")
        .accept("application/json")
        .body(datos));            // ← un POJO Booking
```

> 💡 Esto une todo lo aprendido: **Screenplay** (estructura) + **REST Assured** (la
> request) + **POJOs** (el body). Cada módulo anterior cobra sentido aquí.

---

## 6.3 Tasks de dominio reutilizables

En vez de poner el `Post` en el step, lo encapsulamos en una Task con nombre de negocio:

```java
public class CrearReserva implements Task {
    public static CrearReserva con(Booking datos) { return instrumented(CrearReserva.class, datos); }
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/booking").with(req -> req.contentType("application/json").body(datos)));
    }
}
```

El step queda limpísimo y la Task se reutiliza en otros módulos:
```java
theActorInTheSpotlight().attemptsTo(CrearReserva.con(datos));
```

> 🎯 Regla: **un step llama Tasks; una Task llama Interactions.** No mezcles niveles.

---

## 6.4 Leer la respuesta

Tras una interaction, la respuesta está en `SerenityRest.lastResponse()`:

```java
int id = SerenityRest.lastResponse().jsonPath().getInt("bookingid");
```

Y para validar:
```java
theActorInTheSpotlight().should(
    seeThatResponse("incluye id", r -> r.body("bookingid", notNullValue())));
```

> ⚠️ `seeThatResponse` viene de
> `net.serenitybdd.screenplay.rest.questions.ResponseConsequence`. Es el puente entre
> Screenplay y las validaciones de REST Assured.

---

## 6.5 Ejercicios 🛠️

1. Crea una Task `ActualizarParcialmente` con `Patch.to(...)` que cambie solo el
   `additionalneeds` de una reserva (ojo: PATCH también requiere auth → lo verás en M8).
2. Añade una Question `IdDeLaUltimaReserva` que devuelva el `bookingid` y úsala con
   `seeThat(..., notNullValue())` en vez de leer `lastResponse()` en el step.
3. Refactoriza el step "consultar" para que reciba el nombre esperado desde el `.feature`
   con un Scenario Outline de 2 filas.

---

## ✅ Checklist del Módulo 6

- [ ] Conozco las interactions `Get/Post/Put/Patch/Delete` de Serenity.
- [ ] Personalizo requests con `.with(request -> ...)`.
- [ ] Encapsulo verbos HTTP en **Tasks de dominio** reutilizables.
- [ ] Leo y valido la respuesta con `lastResponse()` y `seeThatResponse`.
- [ ] `.\gradlew.bat test --tests "*Modulo06*"` queda en verde.

---

> Anterior: **[Módulo 5 — Screenplay](../modulo-05-screenplay/README.md)**
> · Siguiente: **[Módulo 7 — Integración E2E](../modulo-07-integracion/README.md)**
