# canvia-screenplay-rest-assured-cucumber-bdd

[![CI](https://github.com/gpalominoh-canvia/canvia-screenplay-rest-assured-cucumber-bdd/actions/workflows/tests.yml/badge.svg)](https://github.com/gpalominoh-canvia/canvia-screenplay-rest-assured-cucumber-bdd/actions/workflows/tests.yml)
[![Java 17](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)](https://adoptium.net)
[![Gradle 8.7](https://img.shields.io/badge/Gradle-8.7-02303A?logo=gradle&logoColor=white)](https://gradle.org)
[![Serenity BDD 5.3.9](https://img.shields.io/badge/Serenity%20BDD-5.3.9-1f9bcf)](https://serenity-bdd.github.io)
[![Cucumber 7](https://img.shields.io/badge/Cucumber-7-23D96C?logo=cucumber&logoColor=white)](https://cucumber.io)
[![REST Assured](https://img.shields.io/badge/REST%20Assured-API-green)](https://rest-assured.io)
[![Allure](https://img.shields.io/badge/Report-Allure-FF7B00)](https://allurereport.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Reportes](https://img.shields.io/badge/Reportes-en%20l%C3%ADnea-success?logo=githubpages)](https://gpalominoh-canvia.github.io/canvia-screenplay-rest-assured-cucumber-bdd/)

Curso práctico, **parte por parte**, para dominar la automatización de pruebas de **API**
en **Java** usando **Serenity BDD** con el patrón **Screenplay**, **REST Assured** y
**Cucumber/Gherkin** (BDD).

Se construye de forma incremental una suite de pruebas real contra la API pública
[restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html).

> 📚 El curso completo, módulo por módulo, está en **[docs/](docs/README.md)**.

---

## ¿Qué vas a aprender?

- **BDD** y **Gherkin**: escribir escenarios legibles para negocio y QA.
- **Cucumber**: features, step definitions, hooks, Scenario Outline, Data Tables, tags.
- **REST Assured**: requests, validación de status/body/headers, JSONPath.
- **POJOs / JSON**: serializar y deserializar con Jackson.
- **Screenplay**: actores, abilities, tasks, interactions, questions.
- **CRUD + Autenticación**: crear, consultar, actualizar y borrar reservas con token.
- **Reportes**: Serenity (reporte viviente) y **Allure**.
- **CI**: ejecución automática en **GitHub Actions**.

---

## Stack

| Pieza | Tecnología |
|-------|-----------|
| Lenguaje | Java 17 |
| Build | Gradle 8.7 (wrapper incluido) |
| BDD | Cucumber 7 + Gherkin (español) |
| HTTP | REST Assured |
| Patrón | Screenplay |
| Framework | Serenity BDD 5.3.9 |
| Reportes | Serenity + Allure |
| API objetivo | restful-booker |

---

## Requisitos

- **JDK 17+** (ej. [Eclipse Temurin 17](https://adoptium.net)). Verifica con `java -version`
  o que `JAVA_HOME` apunte a un JDK 17.
- No necesitas instalar Gradle: el proyecto incluye el **wrapper** (`gradlew`).
- Conexión a internet (la API y las dependencias se descargan).

---

## Cómo empezar

```powershell
# 1. Clonar
git clone https://github.com/gpalominoh-canvia/canvia-screenplay-rest-assured-cucumber-bdd.git
cd canvia-screenplay-rest-assured-cucumber-bdd

# 2. Ejecutar toda la suite (la 1ª vez descarga Gradle + dependencias)
.\gradlew.bat clean test

# 3. Abrir el reporte
#    target/site/serenity/index.html
```

> En Linux/Mac usa `./gradlew` en lugar de `.\gradlew.bat`.

---

## El curso: sesiones (módulos)

Cada módulo es una **carpeta autocontenida** (teoría + código + escenarios) y se puede
**ejecutar de forma aislada**. Avanza en orden, a tu ritmo.

| # | Módulo | Qué cubre | Ejecutable |
|---|--------|-----------|:----------:|
| 0 | [Setup del entorno](docs/modulo-00-setup/README.md) | JDK, Gradle, estructura, primer test | ✅ |
| 1 | [BDD y Gherkin](docs/modulo-01-bdd-gherkin/README.md) | Fundamentos, Three Amigos, declarativo vs imperativo | 📖 |
| 2 | [Cucumber](docs/modulo-02-cucumber/README.md) | Steps, Background, Scenario Outline, Data Tables, hooks, tags | ✅ |
| 3 | [REST Assured](docs/modulo-03-rest-assured/README.md) | given/when/then, status, body, headers, JSONPath | ✅ |
| 4 | [JSON y POJOs](docs/modulo-04-json/README.md) | Serializar/deserializar con Jackson, builder de datos | ✅ |
| 5 | [Screenplay](docs/modulo-05-screenplay/README.md) | Actores, Abilities, Tasks, Interactions, Questions | ✅ |
| 6 | [Screenplay + REST](docs/modulo-06-screenplay-rest/README.md) | Get/Post/Put/Delete, Tasks de dominio | ✅ |
| 7 | [Integración E2E](docs/modulo-07-integracion/README.md) | Memoria del actor (remember/recall), asksFor | ✅ |
| 8 | [Auth y CRUD completo](docs/modulo-08-auth/README.md) | Token, PUT/DELETE protegidos, verificar el efecto | ✅ |
| 9 | [Reportes Serenity](docs/modulo-09-reportes-serenity/README.md) | `serenity.conf`, environments, tags, ejecución selectiva | 📖 |
| 10 | [Reportes con Allure](docs/modulo-10-allure/README.md) | Plugin, adapter, severidad y metadatos | ✅ |
| 11 | [Buenas prácticas + CI](docs/modulo-11-buenas-practicas-ci/README.md) | Anti-patrones, GitHub Actions, proyecto final | 📖 |

✅ = trae código que corre en verde · 📖 = teórico / configuración

Cada README incluye **tips (💡), consejos (🎯), advertencias (⚠️), ejercicios (🛠️) y un checklist (✅)**.

---

## Estructura del proyecto

```
canvia-screenplay-rest-assured-cucumber-bdd/
├── build.gradle / settings.gradle    # build y dependencias
├── gradlew / gradle/                 # wrapper (no necesitas instalar Gradle)
├── .github/workflows/tests.yml       # CI: ejecuta la suite y publica reportes
├── docs/                             # el curso (una carpeta por módulo)
│   ├── README.md                     # índice del curso
│   └── modulo-XX-.../README.md
└── src/test/
    ├── java/canvia/
    │   ├── support/                  # ApiConfig + model/ (POJOs Booking)
    │   ├── hooks/                    # @Before global (Screenplay Stage)
    │   ├── shared/                   # steps comunes (actor + ability)
    │   └── moduloXX.../              # por módulo: Runner + Tasks + Questions + Steps
    └── resources/
        ├── serenity.conf             # URLs, environments, configuración
        ├── allure.properties         # dónde escribe Allure sus resultados
        └── features/modulo-XX-.../   # los escenarios .feature
```

---

## Comandos útiles

| Comando | Qué hace |
|---------|----------|
| `.\gradlew.bat clean test` | Ejecuta **toda** la suite + reporte Serenity |
| `.\gradlew.bat test --tests "*Modulo05*"` | Ejecuta **solo** ese módulo |
| `.\gradlew.bat test -Dcucumber.filter.tags="@humo"` | Filtra por tag |
| `.\gradlew.bat test -Denvironment=staging` | Cambia de entorno |
| `.\gradlew.bat allureReport` | Genera el reporte Allure |
| `.\gradlew.bat allureServe` | Abre el reporte Allure en el navegador |

**Reportes:** Serenity en `target/site/serenity/index.html` · Allure en `build/reports/allure-report/`.

---

## Flujo de trabajo (Git Flow)

Este repositorio usa **Git Flow**:

- `main` → versiones publicadas (releases, etiquetadas: `v1.0.0`).
- `develop` → rama de integración.
- `feature/*` → desarrollo; se integra a `develop` y luego se libera a `main`.

Para contribuir: parte de `develop`, crea una `feature/...`, y abre un PR hacia `develop`.

---

## API de práctica

[restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html) — API gratuita con
auth y CRUD completo. Credenciales públicas de práctica: `admin` / `password123`.

> ⚠️ Es un servicio gratuito que a veces "duerme": si un test falla por timeout, reintenta.

---

## Contribuir

Las contribuciones son bienvenidas. Lee la **[Guía de contribución](CONTRIBUTING.md)**
(usa Git Flow y Conventional Commits en español) y el
**[Código de Conducta](CODE_OF_CONDUCT.md)**.

- 🐛 ¿Encontraste un error? Abre un [issue de bug](.github/ISSUE_TEMPLATE/bug_report.md).
- 💡 ¿Una idea? Abre una [propuesta de mejora](.github/ISSUE_TEMPLATE/feature_request.md).
- 📋 Historial de cambios en el **[CHANGELOG](CHANGELOG.md)**.

## Licencia

Distribuido bajo licencia **MIT**. Ver [LICENSE](LICENSE) para más detalles.

## Autor

Curso de entrenamiento — **Canvia**. Mantenido por Giancarlo Palomino.
