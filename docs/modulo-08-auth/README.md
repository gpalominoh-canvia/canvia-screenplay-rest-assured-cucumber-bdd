# Módulo 8 — Autenticación y CRUD completo

> Objetivo: obtener un **token de autenticación** y usarlo para operaciones protegidas
> (PUT, DELETE), completando el ciclo CRUD. Más patrones avanzados de datos.

> 📁 Código de este módulo:
> - Feature: [auth-crud.feature](../../src/test/resources/features/modulo-08-auth/auth-crud.feature)
> - Tasks: [Autenticarse.java](../../src/test/java/canvia/modulo08auth/Autenticarse.java) · [ActualizarReserva.java](../../src/test/java/canvia/modulo08auth/ActualizarReserva.java) · [BorrarReserva.java](../../src/test/java/canvia/modulo08auth/BorrarReserva.java)
> - Steps: [AuthCrudStepDefinitions.java](../../src/test/java/canvia/modulo08auth/AuthCrudStepDefinitions.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo08*"`

---

## 8.1 El flujo de autenticación

restful-booker pide un **token** para PUT y DELETE. El flujo es:

1. `POST /auth` con `{ "username": "admin", "password": "password123" }`.
2. La API responde `{ "token": "abc123..." }`.
3. Enviamos ese token como **cookie** `token` en cada operación protegida.

```java
// 1 y 2: obtener y recordar el token
Post.to("/auth").with(req -> req.contentType("application/json")
    .body(Map.of("username", ADMIN_USER, "password", ADMIN_PASSWORD)));
String token = SerenityRest.lastResponse().jsonPath().getString("token");
actor.remember("token", token);

// 3: usarlo en un PUT
Put.to("/booking/" + id).with(req -> req.cookie("token", token).body(nuevos));
```

> 🔐 **Consejo de seguridad:** estas credenciales son **públicas y de práctica**. En un
> proyecto real, NUNCA pongas usuario/contraseña en el código: léelos de variables de
> entorno o de un gestor de secretos, y jamás los commitees.

---

## 8.2 El token vive en la memoria del actor

Igual que el id en el Módulo 7, el token se guarda con `remember` y se recupera con
`recall` en las Tasks que lo necesitan:

```java
String token = actor.recall(Autenticarse.TOKEN);
```

Así, `Autenticarse` se ejecuta **una vez** (en el `Antecedentes`) y todas las operaciones
posteriores reutilizan el token. Limpio y sin repetición.

---

## 8.3 Completando el CRUD

| Operación | Task | Verbo | ¿Auth? | Status esperado |
|-----------|------|-------|--------|-----------------|
| Crear | `CrearReserva` | POST | No | 200 |
| Leer | `ConsultarReserva` | GET | No | 200 / 404 |
| Actualizar | `ActualizarReserva` | PUT | **Sí** | 200 |
| Borrar | `BorrarReserva` | DELETE | **Sí** | 201 |

> ⚠️ Si olvidas el token en PUT/DELETE, restful-booker responde **403 Forbidden**. Es el
> primer sitio donde mirar si esos escenarios fallan.

---

## 8.4 Verificar que algo se borró

Un patrón muy común: tras borrar, confirmar que el recurso **ya no existe**:

```java
// borra...
theActorInTheSpotlight().attemptsTo(BorrarReserva.laRecordada());
// ...y verifica el 404
theActorInTheSpotlight().attemptsTo(Get.resource("/booking/" + id));
theActorInTheSpotlight().should(seeThatResponse(r -> r.statusCode(404)));
```

> 💡 No te fíes solo del `201` del DELETE: **verifica el efecto** (que el GET dé 404). Es
> la diferencia entre "la API dijo OK" y "realmente se borró".

---

## 8.5 El Background como precondición compartida

Aquí el `Antecedentes` hace 3 cosas comunes a ambos escenarios:

```gherkin
Antecedentes:
  Dado que Giancarlo puede consumir la API de bookings
  Y Giancarlo se autentica como administrador
  Y existe una reserva creada por Giancarlo
```

Cada escenario (actualizar / borrar) parte ya autenticado y con una reserva lista.

---

## 8.6 Ejercicios 🛠️

1. Crea un escenario **negativo**: intenta actualizar **sin** autenticarte (quita el paso
   del Background con un escenario aparte) y verifica que da `403`.
2. Usa un **Scenario Outline** para actualizar la reserva con 3 precios distintos y validar
   cada uno.
3. Crea una Task `ActualizarParcial` con `Patch` que cambie solo `additionalneeds`.
4. Extrae usuario/contraseña a variables de entorno (`System.getenv(...)`) en `ApiConfig`
   y haz que el test las lea, con un valor por defecto para local.

---

## ✅ Checklist del Módulo 8

- [ ] Obtengo un token con `POST /auth` y lo guardo en la memoria del actor.
- [ ] Envío el token como cookie en operaciones protegidas (PUT/DELETE).
- [ ] Completo el CRUD (crear, leer, actualizar, borrar).
- [ ] Verifico el **efecto** de borrar (GET → 404), no solo el status del DELETE.
- [ ] Entiendo por qué las credenciales no van en el código en proyectos reales.
- [ ] `.\gradlew.bat test --tests "*Modulo08*"` queda en verde.

---

> Anterior: **[Módulo 7 — Integración E2E](../modulo-07-integracion/README.md)**
> · Siguiente: **[Módulo 9 — Reportes Serenity](../modulo-09-reportes-serenity/README.md)**
