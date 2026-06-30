# Módulo 3 — REST Assured esencial

> Objetivo: dominar el ABC de REST Assured: configurar requests, ejecutar verbos HTTP y
> validar respuestas (status, headers, body con JSONPath, tiempo) de forma legible.

> 📁 Código de este módulo:
> - Feature: [validaciones-rest.feature](../../src/test/resources/features/modulo-03-rest-assured/validaciones-rest.feature)
> - Steps: [ValidacionesRestStepDefinitions.java](../../src/test/java/canvia/modulo03restassured/ValidacionesRestStepDefinitions.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo03*"`

---

## 3.1 El patrón given / when / then de REST Assured

⚠️ REST Assured usa las palabras `given/when/then`, pero **NO** son las de Gherkin.
Aquí son las fases de **una llamada HTTP**:

```java
SerenityRest
    .given()                       // 1. PREPARAR la request
        .baseUri("https://...")
        .contentType("application/json")
        .body(miObjeto)
    .when()                        // 2. EJECUTAR el verbo HTTP
        .post("/booking")
    .then()                        // 3. VALIDAR la respuesta
        .statusCode(200)
        .body("firstname", equalTo("Sally"));
```

| Fase | Qué configuras / haces |
|------|------------------------|
| `given()` | baseUri, headers, params, cookies, body, auth |
| `when()` | `get` / `post` / `put` / `patch` / `delete` |
| `then()` | `statusCode`, `body`, `header`, `contentType`, `time` |

> 💡 Usamos **`SerenityRest`** (no `RestAssured` a secas) para que cada request quede
> registrada en el reporte de Serenity con su detalle completo.

---

## 3.2 Validar el status code

```java
respuesta.then().statusCode(200);
```

Tabla de status que verás en restful-booker:

| Código | Cuándo |
|--------|--------|
| `200 OK` | GET/POST/PUT correctos |
| `201 Created` | el healthcheck `/ping` |
| `403 Forbidden` | falta auth en PUT/DELETE |
| `404 Not Found` | id que no existe |
| `405 Method Not Allowed` | verbo no soportado |

---

## 3.3 Validar el body con JSONPath

REST Assured usa **JSONPath** (sintaxis tipo Groovy) para navegar el JSON:

```java
respuesta.then()
    .body("firstname", equalTo("Sally"))     // campo simple
    .body("bookingdates.checkin", notNullValue())  // campo anidado
    .body("totalprice", greaterThanOrEqualTo(0));  // con matcher numérico
```

Para listas:
```java
.body("bookingid", hasSize(greaterThan(0)))  // hay al menos 1
.body("[0].bookingid", notNullValue())        // primer elemento
```

> 💡 Los **matchers** (`equalTo`, `notNullValue`, `greaterThan`, `hasItem`…) vienen de
> **Hamcrest** (`org.hamcrest.Matchers`). Son muy expresivos: léelos en voz alta.

---

## 3.4 Headers, contentType y tiempo

```java
respuesta.then()
    .contentType("application/json")
    .header("Server", notNullValue());

long ms = respuesta.time();   // tiempo de respuesta en milisegundos
```

---

## 3.5 Extraer datos de la respuesta

Muchas veces necesitas un dato de la respuesta para el siguiente paso (ej. un id):

```java
int id = respuesta.jsonPath().getInt("[0].bookingid");
String nombre = respuesta.jsonPath().getString("firstname");
List<Integer> ids = respuesta.jsonPath().getList("bookingid");
```

> ⚠️ **Consejo:** no dependas de **ids fijos** (`/booking/1`). Esos datos cambian. Pide
> primero la lista (`GET /booking`) y usa un id real. Es lo que hacemos en el `Dado`.

---

## 3.6 Tip: dos estilos de validación

```java
// Estilo fluido de REST Assured (Hamcrest):
respuesta.then().body("totalprice", greaterThanOrEqualTo(0));

// Estilo AssertJ (extrayendo el valor primero):
long tiempo = respuesta.time();
assertThat(tiempo).isLessThan(5000);
```

Ambos valen. **Hamcrest** encaja en la cadena `then()`; **AssertJ** es más cómodo cuando
ya tienes el valor en una variable. Elige el más legible en cada caso.

---

## 3.7 Ejercicios 🛠️

1. Añade un escenario que valide que `GET /booking/{id}` de una reserva existente trae
   `bookingdates.checkin` **no nulo**.
2. Crea un escenario **negativo**: `GET /booking/99999999` debe devolver `404`.
3. Extrae el `totalprice` de una reserva a una variable y valida con AssertJ que es un
   número (`isInstanceOf(Integer.class)` o `isNotNull()`).
4. Mide el tiempo de `GET /booking` y haz que falle a propósito (`isLessThan(1)`) para
   ver cómo se reporta un fallo en Serenity. Luego corrígelo.

---

## ✅ Checklist del Módulo 3

- [ ] Entiendo `given/when/then` de REST Assured (≠ Gherkin).
- [ ] Valido status, contentType y tiempo de respuesta.
- [ ] Navego el JSON con JSONPath y matchers de Hamcrest.
- [ ] Extraigo datos de la respuesta para usarlos en otro paso.
- [ ] No dependo de ids fijos.
- [ ] `.\gradlew.bat test --tests "*Modulo03*"` queda en verde.

---

> Anterior: **[Módulo 2 — Cucumber](../modulo-02-cucumber/README.md)**
> · Siguiente: **[Módulo 4 — JSON y POJOs](../modulo-04-json/README.md)**
