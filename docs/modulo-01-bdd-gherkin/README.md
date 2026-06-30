# Módulo 1 — Fundamentos de BDD y Gherkin

> Objetivo: entender qué es BDD, por qué existe, y escribir tus primeros escenarios
> en Gherkin con corrección. Sin esto, el resto del curso es solo "copiar sintaxis".

---

## 1.1 ¿Qué problema resuelve BDD?

Imagina tres personas en un proyecto:

- **Negocio / Product Owner**: sabe *qué* debe hacer el sistema y *por qué*.
- **Desarrollo**: sabe *cómo* construirlo.
- **QA**: sabe *qué puede salir mal*.

El clásico problema: cada uno entiende el requisito de forma distinta. El PO dice
"el usuario debe poder reservar", el dev construye otra cosa, y QA prueba una tercera.
El resultado se descubre tarde y caro.

**BDD (Behavior Driven Development)** ataca esto con una idea simple:

> Describir el comportamiento esperado del sistema en un **lenguaje común**,
> entendible por todos, **antes** de programar — y que ese mismo lenguaje sea
> ejecutable como prueba automatizada.

BDD **no es una herramienta**, es una **práctica de colaboración**. Cucumber, Gherkin
y Serenity son solo las herramientas que la hacen ejecutable.

---

## 1.2 Los "Three Amigos"

Antes de escribir código, las tres voces (negocio, dev, QA) se reúnen y conversan sobre
un requisito con ejemplos concretos. A esto se le llama **"Three Amigos"** o
**Example Mapping**.

Ejemplo de conversación sobre "reservar una habitación":

- ¿Y si la fecha de salida es anterior a la de entrada? → escenario negativo.
- ¿Y si el hotel está lleno? → otro escenario.
- ¿Qué datos son obligatorios? → reglas.

Cada **ejemplo concreto** se convierte luego en un **escenario Gherkin**. Por eso BDD
también se llama *Specification by Example* (especificación mediante ejemplos).

> 🎯 Idea central: **los ejemplos concretos eliminan la ambigüedad** que las
> descripciones abstractas no logran.

---

## 1.3 De ejemplo a Gherkin

**Gherkin** es el lenguaje estructurado (casi natural) con el que escribimos esos
ejemplos. Es lo que viste en el smoke test:

```gherkin
# language: es
Característica: Smoke - la API de bookings responde

  Escenario: El healthcheck responde correctamente
    Dado que la API de bookings está disponible
    Cuando consulto el estado del servicio
    Entonces el servicio responde con código 201
```

### Estructura de un archivo `.feature`

```gherkin
# language: es                          ← idioma de las palabras clave
Característica: <nombre>                 ← agrupa escenarios de una funcionalidad
  <descripción libre opcional>          ← contexto de negocio (no se ejecuta)

  Escenario: <nombre>                   ← un ejemplo concreto y ejecutable
    Dado <contexto inicial>             ← estado previo (precondición)
    Cuando <acción>                     ← el evento que disparamos
    Entonces <resultado esperado>       ← la verificación
```

### Las palabras clave (Given/When/Then)

| Español | Inglés | Significado | Pregunta que responde |
|---------|--------|-------------|------------------------|
| `Dado` | `Given` | Precondición / estado inicial | ¿En qué situación partimos? |
| `Cuando` | `When` | La acción bajo prueba | ¿Qué hace el usuario/sistema? |
| `Entonces` | `Then` | El resultado verificable | ¿Qué debería pasar? |
| `Y` / `Pero` | `And` / `But` | Encadenar pasos del mismo tipo | (continúa el anterior) |

> 📌 Usamos `# language: es` para escribir en español. Cucumber soporta +70 idiomas.
> Internamente `Dado`=`Given`, `Cuando`=`When`, `Entonces`=`Then`.

---

## 1.4 La regla de oro: Given-When-Then

Un buen escenario tiene **una sola** acción principal (`Cuando`) y verifica **un
comportamiento**. Estructura mental:

- **Given** = el mundo *antes* (sustantivos, estado). *No* acciones.
- **When** = *el* evento que estás probando. Idealmente **uno solo**.
- **Then** = consecuencias *observables*. Verificaciones, no acciones.

### ❌ Mal escenario (mezcla acciones y verificaciones)

```gherkin
Escenario: Reservar
  Dado que creo una reserva y verifico que existe y luego la pago
  Entonces todo funciona
```

Problemas: el `Dado` hace acciones y verificaciones; el `Entonces` es vago
("todo funciona" no es verificable).

### ✅ Buen escenario

```gherkin
Escenario: Crear una reserva con datos válidos
  Dado que tengo los datos de un huésped válido
  Cuando creo una reserva
  Entonces la reserva se guarda con un identificador único
  Y los datos de la reserva coinciden con los que envié
```

Cada paso es claro, declarativo y verificable.

---

## 1.5 Declarativo vs Imperativo

Esta es la distinción que separa escenarios buenos de malos.

### ❌ Imperativo (describe *cómo*, lleno de detalles técnicos)

```gherkin
Cuando envío un POST a "/booking" con header "Content-Type: application/json"
  y body "{ \"firstname\": \"Juan\" ... }"
Entonces el status code es 200 y el JSONPath "$.bookingid" no es nulo
```

Frágil, ilegible para negocio, acoplado a la implementación.

### ✅ Declarativo (describe *qué*, en lenguaje de negocio)

```gherkin
Cuando creo una reserva con datos válidos
Entonces la reserva se registra correctamente
```

Los detalles técnicos (POST, JSON, status code) viven en el **Step Definition** (Java),
no en el `.feature`. **El `.feature` habla de negocio; el Java habla de HTTP.**

> 🎯 Regla práctica: si un PO no técnico no entiende tu escenario, es demasiado imperativo.

---

## 1.6 Anatomía completa que veremos más adelante

Estas estructuras las dominaremos en el Módulo 2, pero conviene que las reconozcas:

```gherkin
Característica: Gestión de reservas

  Antecedentes:                    # "Background": pasos comunes a TODOS los escenarios
    Dado que la API está disponible

  @humo @reservas                  # "Tags": etiquetas para filtrar ejecuciones
  Escenario: Crear una reserva
    Cuando creo una reserva válida
    Entonces se registra correctamente

  Esquema del escenario: Validar precios     # "Scenario Outline": mismo test, varios datos
    Cuando creo una reserva con precio <precio>
    Entonces el total cobrado es <precio>
    Ejemplos:
      | precio |
      | 100    |
      | 250    |
```

| Español | Inglés |
|---------|--------|
| `Antecedentes` | `Background` |
| `Esquema del escenario` | `Scenario Outline` |
| `Ejemplos` | `Examples` |

---

## 1.7 Ejercicios 🛠️

> No necesitas ejecutar nada todavía: este módulo es de escritura y criterio.
> Crea un archivo `src/test/resources/features/modulo-01-bdd-gherkin/practica.feature` y escribe ahí.

**Ejercicio 1.** Escribe en Gherkin (español, declarativo) un escenario para:
*"consultar una reserva existente por su identificador y ver sus datos"*.

**Ejercicio 2.** Escribe un escenario **negativo** para:
*"consultar una reserva que no existe debe indicar que no se encontró"*.

**Ejercicio 3.** Toma este escenario imperativo y reescríbelo declarativo:

```gherkin
Escenario: Borrar
  Cuando envío DELETE a "/booking/5" con token "abc123" en la cookie
  Entonces recibo status 201 y el GET a "/booking/5" devuelve 404
```

**Ejercicio 4.** Identifica el error en este escenario y corrígelo:

```gherkin
Escenario: Login y reserva
  Dado que hago login y obtengo un token y creo una reserva
  Cuando verifico que la reserva existe
  Entonces está todo correcto
```

<details>
<summary>💡 Pistas (ábrelas solo después de intentarlo)</summary>

- E1: `Dado` una reserva existente → `Cuando` la consulto por su id → `Entonces` veo sus datos.
- E2: el `Entonces` debe verificar el caso "no encontrado", no un error genérico.
- E3: el `Cuando` debería ser "elimino la reserva" y el `Entonces` "ya no existe / no se encuentra".
- E4: el `Dado` mezcla 3 acciones; el `Cuando` hace una verificación; el `Entonces` es vago.
  Separa: login en el `Dado` (precondición), la acción real en el `Cuando`, verificación real en el `Entonces`.
</details>

---

## ✅ Checklist del Módulo 1

- [ ] Puedo explicar BDD en una frase sin mencionar herramientas.
- [ ] Sé qué aportan los "Three Amigos" / Example Mapping.
- [ ] Distingo `Given` (estado), `When` (acción), `Then` (verificación).
- [ ] Sé diferenciar un escenario **declarativo** de uno **imperativo**.
- [ ] Escribí los 4 ejercicios y comparé con las pistas.

---

## Resumen

- BDD es **colaboración mediante ejemplos**, no una herramienta.
- Gherkin convierte esos ejemplos en escenarios **ejecutables y legibles**.
- Escenarios **declarativos** (qué, negocio) en el `.feature`; los detalles técnicos
  (cómo, HTTP) van en el Step Definition que escribiremos en el Módulo 2.

> Anterior: **[Módulo 0 — Setup](../modulo-00-setup/README.md)**
> · Siguiente: **Módulo 2 — Cucumber en profundidad** (lo creamos cuando termines este).
