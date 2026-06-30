# Módulo 7 — Integración end-to-end

> Objetivo: encadenar varias Tasks en un flujo real usando la **memoria del actor**
> (`remember`/`recall`) y leer resultados con `asksFor` + una **Question que deserializa**.

> 📁 Código de este módulo:
> - Feature: [flujo-e2e.feature](../../src/test/resources/features/modulo-07-integracion/flujo-e2e.feature)
> - Tasks: [CrearReserva.java](../../src/test/java/canvia/modulo07integracion/CrearReserva.java) · [ConsultarReservaRecordada.java](../../src/test/java/canvia/modulo07integracion/ConsultarReservaRecordada.java)
> - Question: [DatosDeLaReserva.java](../../src/test/java/canvia/modulo07integracion/DatosDeLaReserva.java)
> - Steps: [FlujoE2EStepDefinitions.java](../../src/test/java/canvia/modulo07integracion/FlujoE2EStepDefinitions.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo07*"`

---

## 7.1 La memoria del actor: remember / recall

En flujos con varios pasos necesitas pasar datos (un id, un token) de una Task a otra.
La forma idiomática en Screenplay **no** es un campo en los steps, sino la memoria del actor:

```java
// Guardar (en la Task que crea):
actor.remember("idReserva", id);

// Recuperar (en la Task que consulta):
int id = actor.recall("idReserva");
```

> 💡 Ventaja: las Tasks quedan **independientes** entre sí y reutilizables. El step solo
> orquesta; no acarrea estado. Usa una constante para la clave (`CrearReserva.ID_RESERVA`)
> y evita errores de tipeo.

---

## 7.2 asksFor: consumir una Question

Para obtener el valor de una Question de forma legible:

```java
Booking recuperada = theActorInTheSpotlight().asksFor(DatosDeLaReserva.recuperada());
assertThat(recuperada.getFirstname()).isEqualTo("Luis");
```

- `asksFor(question)` devuelve directamente el valor (`Booking`).
- Útil cuando quieres validar con **AssertJ** sobre un objeto, no con matchers en `should`.

---

## 7.3 Question que deserializa a POJO

Unimos Screenplay (Module 5) + deserialización (Module 4):

```java
public class DatosDeLaReserva implements Question<Booking> {
    public Booking answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(Booking.class);  // JSON → Booking
    }
}
```

`response.as(Clase.class)` deserializa **todo** el body. (Cuando el dato está anidado,
usa `jsonPath().getObject("ruta", Clase.class)`, como en el Módulo 4.)

---

## 7.4 Anatomía del flujo

```gherkin
Antecedentes:
  Dado que Giancarlo puede consumir la API de bookings   # actor + ability

Escenario: Crear y luego recuperar la misma reserva
  Cuando crea la reserva de "Luis" "Gomez" por 450        # Task: crea + remember(id)
  Y consulta la reserva que acaba de crear                # Task: recall(id) + GET
  Entonces la reserva recuperada tiene nombre "Luis" y precio 450  # asksFor + AssertJ
```

Cada paso es una Task; el id viaja por la memoria del actor. **Esto es Screenplay maduro.**

---

## 7.5 Consejos para flujos E2E

- ✅ **Cada escenario debe ser autónomo**: crea sus propios datos, no dependas de que
  "ya exista" algo en el servidor.
- ✅ Usa **constantes** para las claves de memoria (`ID_RESERVA`, `TOKEN`).
- ✅ Mantén las Tasks **pequeñas y con un solo objetivo**; compón flujos encadenándolas.
- ⚠️ Evita escenarios kilométricos: si un flujo tiene 15 pasos, probablemente sean 2-3
  escenarios distintos.

---

## 7.6 Ejercicios 🛠️

1. Añade un paso que, tras crear, verifique con una Question `PrecioDeLaReserva` (Integer)
   que el precio coincide, usando `seeThat(..., equalTo(450))`.
2. Convierte el escenario en un **Scenario Outline** con 3 juegos de datos (nombre/precio).
3. Crea una Task `CrearVariasReservas` que reciba una lista de `Booking` y las cree todas,
   recordando la lista de ids.
4. Haz que la Question `DatosDeLaReserva` falle a propósito (compara con otro nombre) y
   observa el detalle en el reporte de Serenity.

---

## ✅ Checklist del Módulo 7

- [ ] Uso `remember`/`recall` para compartir estado entre Tasks.
- [ ] Consumo Questions con `asksFor(...)`.
- [ ] Tengo una Question que deserializa la respuesta a un POJO.
- [ ] Mis escenarios E2E son autónomos (crean sus propios datos).
- [ ] `.\gradlew.bat test --tests "*Modulo07*"` queda en verde.

---

> Anterior: **[Módulo 6 — Screenplay + REST](../modulo-06-screenplay-rest/README.md)**
> · Siguiente: **[Módulo 8 — Autenticación y avanzado](../modulo-08-auth/README.md)**
