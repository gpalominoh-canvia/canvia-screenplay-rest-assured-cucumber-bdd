# Curso: Automatización de APIs con Screenplay + REST Assured + Cucumber (BDD)

Curso práctico, parte por parte, para dominar la automatización de pruebas de API
en **Java** usando **Serenity BDD** con el patrón **Screenplay**, **REST Assured** y
**Cucumber/Gherkin**.

Construiremos de forma incremental una suite de pruebas real contra la API pública
[restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html).

---

## ¿Para quién es este curso?

- QA Automation / SDET que quieren aprender el stack Serenity Screenplay desde cero.
- Conocimientos previos recomendados: Java básico (clases, métodos, listas), nociones
  de HTTP (GET/POST/PUT/DELETE, status codes, JSON). No es obligatorio dominarlos:
  los repasamos cuando hagan falta.

## Stack del curso

| Pieza | Tecnología | Para qué |
|-------|-----------|----------|
| Lenguaje | Java 17 | Todo el código del curso |
| Build | Gradle 8.7 | Compilar, gestionar dependencias, ejecutar |
| BDD | Cucumber 7 + Gherkin | Escenarios en lenguaje natural |
| HTTP | REST Assured | Llamadas y validaciones a la API |
| Patrón | Screenplay | Organizar el código orientado a actores |
| Framework | Serenity BDD 5.3.9 | Integra todo + reportes vivientes |
| Aserciones | AssertJ / Hamcrest | Validaciones legibles |
| Reportes | Serenity + Allure | Reporte viviente y reporte alternativo |
| API objetivo | restful-booker | API real con auth y CRUD completo |

---

## Cómo usar el curso

**Una carpeta por módulo**, para que cada parte sea autocontenida y fácil de seguir:

| Dónde | Qué hay |
|-------|---------|
| `docs/modulo-XX-.../README.md` | La teoría + ejercicios del módulo |
| `src/test/java/canvia/moduloXX.../` | El código del módulo (con su propio **Runner**) |
| `src/test/resources/features/modulo-XX-.../` | Los escenarios `.feature` del módulo |

1. Lee el `README.md` del módulo, luego haz los **ejercicios** (marcados con 🛠️).
2. **Demuéstralo aislado**: cada módulo se ejecuta solo con
   `.\gradlew.bat test --tests "*ModuloXX*"` y debe quedar en verde.
3. El código se construye sobre el módulo anterior: no saltes módulos.
4. Al final de cada módulo hay una sección **✅ Checklist** para autoevaluarte.
5. Avanzamos a tu ritmo: cuando termines un módulo, pasamos al siguiente.

---

## Temario (las "partes")

### Bloque 1 — Fundamentos
- **[Módulo 0 — Setup del entorno](modulo-00-setup/README.md)**
  JDK 17, Gradle, estructura del proyecto, primera ejecución y reporte.
- **[Módulo 1 — Fundamentos de BDD y Gherkin](modulo-01-bdd-gherkin/README.md)**
  Qué es BDD, los Three Amigos, sintaxis Gherkin (Feature, Scenario, Given/When/Then).

### Bloque 2 — Cucumber
- **[Módulo 2 — Cucumber en profundidad](modulo-02-cucumber/README.md)**
  Features, Step Definitions, el Runner, Hooks, parámetros, Scenario Outline, Data Tables.

### Bloque 3 — REST Assured
- **[Módulo 3 — REST Assured esencial](modulo-03-rest-assured/README.md)**
  given/when/then, requests, validación de status, body, headers, JSONPath.
- **[Módulo 4 — Serialización JSON y POJOs](modulo-04-json/README.md)**
  Modelos, Jackson, request/response objects, manejo de datos.

### Bloque 4 — Screenplay
- **[Módulo 5 — El patrón Screenplay](modulo-05-screenplay/README.md)**
  Actores, Abilities, Tasks, Interactions, Questions. Por qué supera a Page Objects.
- **[Módulo 6 — Screenplay + REST con Serenity](modulo-06-screenplay-rest/README.md)**
  CallAnApi, Get/Post/Put/Delete, Question sobre respuestas, aserciones.

### Bloque 5 — Integración total
- **[Módulo 7 — Integración end-to-end](modulo-07-integracion/README.md)**
  Flujo encadenado con memoria del actor (remember/recall), asksFor y Questions que deserializan.
- **[Módulo 8 — Autenticación y CRUD completo](modulo-08-auth/README.md)**
  Token de auth, operaciones protegidas (PUT/DELETE), Backgrounds, verificar el efecto.

### Bloque 6 — Calidad y entrega
- **[Módulo 9 — Reportes Serenity, tags y configuración](modulo-09-reportes-serenity/README.md)**
  Reportes vivientes de Serenity, `serenity.conf`, environments, tags, ejecución selectiva.
- **[Módulo 10 — Reportes con Allure](modulo-10-allure/README.md)**
  Integrar Allure en paralelo a Serenity: plugin, adapter, generación, severidad y metadatos.
- **[Módulo 11 — Buenas prácticas y CI](modulo-11-buenas-practicas-ci/README.md)**
  Organización del proyecto, anti-patrones, GitHub Actions (Serenity + Allure), proyecto final.

---

## Proyecto final

Una suite Screenplay completa sobre restful-booker que cubre:
autenticación, CRUD de bookings, validaciones de contrato, escenarios negativos,
datos parametrizados y reportes Serenity ejecutables en CI.

---

> Empieza por el **[Módulo 0 — Setup del entorno](modulo-00-setup/README.md)**.
