# Changelog

Todos los cambios notables de este proyecto se documentan aquí.

El formato sigue [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/)
y el versionado [SemVer](https://semver.org/lang/es/).

## [No publicado]

### Añadido
- Archivos de profesionalización del repositorio: `LICENSE` (MIT), `CONTRIBUTING.md`,
  `CHANGELOG.md`, `CODE_OF_CONDUCT.md`, plantillas de PR e Issues, y badges en el README.

## [1.0.1] - 2026-06-30

### Añadido
- README principal del repositorio con descripción completa del curso, tabla de módulos,
  estructura del proyecto, comandos y flujo Git Flow.

## [1.0.0] - 2026-06-30

### Añadido
- Curso completo (módulos 0 a 11) en Java sobre Serenity BDD 5.3.9, Gradle 8.7,
  Cucumber 7 y REST Assured, contra la API restful-booker.
- Proyecto base ejecutable con Gradle wrapper y patrón Screenplay.
- Una carpeta por módulo (documentación + código + features), ejecutable de forma aislada.
- Módulos: Setup, BDD/Gherkin, Cucumber, REST Assured, JSON/POJOs, Screenplay,
  Screenplay+REST, Integración E2E, Auth+CRUD, Reportes Serenity, Reportes con Allure,
  Buenas prácticas + CI.
- Reportes Serenity y Allure.
- Pipeline de CI con GitHub Actions (`.github/workflows/tests.yml`).
- `.gitattributes` para normalizar fin de línea (gradlew en LF para CI Linux).

[No publicado]: https://github.com/gpalominoh-canvia/canvia-screenplay-rest-assured-cucumber-bdd/compare/v1.0.1...HEAD
[1.0.1]: https://github.com/gpalominoh-canvia/canvia-screenplay-rest-assured-cucumber-bdd/compare/v1.0.0...v1.0.1
[1.0.0]: https://github.com/gpalominoh-canvia/canvia-screenplay-rest-assured-cucumber-bdd/releases/tag/v1.0.0
