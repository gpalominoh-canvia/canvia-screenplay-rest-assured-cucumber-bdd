# Módulo 4 — Serialización JSON y POJOs

> Objetivo: dejar de escribir JSON a mano. Modelar las entidades de la API como objetos
> Java (POJOs) y dejar que la librería serialice/deserialice por ti.

> 📁 Código de este módulo:
> - Modelos: [Booking.java](../../src/test/java/canvia/support/model/Booking.java) · [BookingDates.java](../../src/test/java/canvia/support/model/BookingDates.java)
> - Feature: [crear-reserva-json.feature](../../src/test/resources/features/modulo-04-json/crear-reserva-json.feature)
> - Steps: [JsonStepDefinitions.java](../../src/test/java/canvia/modulo04json/JsonStepDefinitions.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo04*"`

---

## 4.1 ¿Por qué POJOs y no JSON a mano?

JSON como String (frágil, ilegible, propenso a errores de comillas):
```java
.body("{ \"firstname\": \"Sally\", \"totalprice\": 250, ... }")
```

JSON desde un objeto Java (legible, tipado, refactorizable):
```java
Booking reserva = Booking.unaReserva().conNombre("Sally", "Brown").conPrecio(250);
.body(reserva)   // ← se convierte a JSON automáticamente
```

**POJO** = *Plain Old Java Object*: una clase con campos, getters y setters. Nada más.

---

## 4.2 Serializar (Java → JSON)

Cuando pasas un objeto a `.body(...)`, REST Assured lo convierte a JSON usando **Jackson**
(la librería que añadimos en `build.gradle`):

```java
SerenityRest.given()
    .contentType("application/json")
    .body(reserva)              // Booking → { "firstname": "...", ... }
    .when().post("/booking");
```

> ⚠️ Sin `contentType("application/json")` la API no sabe interpretar el body. Es el
> error nº1 al crear recursos.

---

## 4.3 Deserializar (JSON → Java)

A la inversa: tomas la respuesta JSON y la conviertes en un objeto Java:

```java
// La API responde: { "bookingid": 7, "booking": { "firstname": "Giancarlo", ... } }
Booking creada = respuesta.jsonPath().getObject("booking", Booking.class);
assertThat(creada.getFirstname()).isEqualTo("Giancarlo");
```

Trabajar con `creada.getFirstname()` es más seguro que con strings de JSONPath: el
compilador te avisa si te equivocas de campo.

---

## 4.4 Los nombres importan

Los campos del POJO deben coincidir con las claves del JSON:

```java
private String firstname;   // ↔ "firstname" en el JSON
private Integer totalprice;  // ↔ "totalprice"
private BookingDates bookingdates;  // ↔ objeto anidado "bookingdates"
```

Si un nombre **no** puede coincidir (palabra reservada, estilo camelCase…), se mapea con
una anotación:
```java
@JsonProperty("additional_needs")
private String additionalNeeds;
```

> 💡 `@JsonIgnoreProperties(ignoreUnknown = true)` en la clase evita que la
> deserialización **falle** si la API agrega campos nuevos que tu POJO no tiene.
> Es una red de seguridad muy recomendable.

---

## 4.5 El patrón "builder" para datos de prueba

En vez de un constructor con 6 parámetros (ilegible), usamos métodos encadenados:

```java
Booking reserva = Booking.unaReserva()
        .conNombre("Giancarlo", "Palomino")
        .conPrecio(250)
        .conDeposito(true)
        .conFechas("2024-01-01", "2024-01-10")
        .conNecesidades("Desayuno");
```

Se lee casi como una frase. Cada test arma solo los campos que le importan.

> 💡 Más adelante (Módulo 8) esto se vuelve clave para generar **datos dinámicos** y no
> chocar entre ejecuciones.

---

## 4.6 Ejercicios 🛠️

1. Agrega al POJO `Booking` un método `conDeposito()` por defecto en `false` y crea una
   reserva sin depósito; valida que la respuesta lo refleja.
2. Crea un escenario que envíe una reserva **sin `additionalneeds`** y verifica que aun
   así se crea (campo opcional).
3. Deserializa el nodo `booking.bookingdates` a un objeto `BookingDates` y valida que el
   `checkin` es el que enviaste.
4. Provoca un fallo de mapeo: agrega un campo `precioTotal` (mal nombrado) y observa que
   queda `null`. Renómbralo a `totalprice` y verás que ahora sí mapea.

---

## ✅ Checklist del Módulo 4

- [ ] Sé qué es un POJO y por qué evita escribir JSON a mano.
- [ ] Serializo un objeto Java a JSON con `.body(objeto)` + `contentType`.
- [ ] Deserializo una respuesta con `jsonPath().getObject(ruta, Clase.class)`.
- [ ] Entiendo que los nombres de campo deben coincidir (o mapearse con `@JsonProperty`).
- [ ] Uso un builder fluido para construir datos de prueba.
- [ ] `.\gradlew.bat test --tests "*Modulo04*"` queda en verde.

---

> Anterior: **[Módulo 3 — REST Assured](../modulo-03-rest-assured/README.md)**
> · Siguiente: **[Módulo 5 — El patrón Screenplay](../modulo-05-screenplay/README.md)**
