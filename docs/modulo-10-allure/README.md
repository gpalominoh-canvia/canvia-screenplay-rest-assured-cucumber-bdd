# Módulo 10 — Reportes con Allure

> Objetivo: generar reportes **Allure** en paralelo al reporte de Serenity. Allure es un
> estándar de la industria, muy visual, con historial de ejecuciones y categorización.

> 📁 Config: [build.gradle](../../build.gradle) · [allure.properties](../../src/test/resources/allure.properties)
> · Runner: [Modulo10Runner.java](../../src/test/java/canvia/modulo10allure/Modulo10Runner.java)
>
> ▶️ Ejecutar + generar reporte:
> ```powershell
> .\gradlew.bat test --tests "*Modulo10*"
> .\gradlew.bat allureReport     # genera build/reports/allure-report
> .\gradlew.bat allureServe      # lo abre en el navegador
> ```

---

## 10.1 Serenity vs Allure: ¿por qué los dos?

| | Serenity | Allure |
|--|----------|--------|
| Integración | nativa con Screenplay | adapter de Cucumber |
| Detalle REST | request/response automáticos | requiere adjuntar a mano |
| Historial/tendencias | limitado | **fuerte** (trends, retries) |
| Adopción | equipos Serenity | **muy extendida**, multi-lenguaje |

> 💡 No compiten: muchos equipos generan **ambos**. Serenity para el detalle REST y los
> requisitos; Allure para el dashboard ejecutivo y el histórico en CI.

---

## 10.2 Cómo se conecta Allure (3 piezas)

### 1) El plugin Gradle + la dependencia ([build.gradle](../../build.gradle))

```gradle
plugins {
    id 'io.qameta.allure' version '2.12.0'   // tareas allureReport / allureServe
}

dependencies {
    testImplementation 'io.qameta.allure:allure-cucumber7-jvm:2.29.0'  // el adapter
}

allure {
    version = '2.29.0'
    adapter { autoconfigure = false; aspectjWeaver = false }  // control manual
}
```

### 2) Dónde se guardan los resultados ([allure.properties](../../src/test/resources/allure.properties))

```properties
allure.results.directory=build/allure-results
```

### 3) Registrar el adapter en el Runner ([Modulo10Runner.java](../../src/test/java/canvia/modulo10allure/Modulo10Runner.java))

```java
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
    value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
```

> 🎯 La clave: **dos plugins de Cucumber separados por coma**. Uno alimenta Serenity, el
> otro alimenta Allure. La misma ejecución produce los dos reportes.

---

## 10.3 El flujo de generación

```
.\gradlew.bat test       →  build/allure-results/*.json   (datos crudos)
.\gradlew.bat allureReport →  build/reports/allure-report/  (HTML navegable)
.\gradlew.bat allureServe  →  abre el HTML en el navegador
```

> ⚠️ `allure-results` son **datos**, no el reporte. El HTML se genera en un segundo paso.
> En CI: corres los tests, archivas `allure-results` y generas el reporte aparte.

---

## 10.4 Metadatos con tags

El adapter de Allure convierte ciertos tags de Gherkin en **metadatos** del reporte:

```gherkin
@severity:critical
Escenario: ...

@story:Reservas @owner:giancarlo
Escenario: ...
```

| Tag | Efecto en Allure |
|-----|------------------|
| `@severity:critical` | nivel de severidad (blocker/critical/normal/minor/trivial) |
| `@epic:...` `@feature:...` `@story:...` | jerarquía de comportamiento |
| `@issue:ABC-123` | enlace a un issue (configurable) |
| `@tag` simple | etiqueta normal |

> 💡 Con `@severity` y la jerarquía `epic/feature/story` obtienes el dashboard "Behaviors"
> de Allure, ideal para reportar a stakeholders.

---

## 10.5 Allure en CI (preview del Módulo 11)

En GitHub Actions:
```yaml
- run: ./gradlew test
  continue-on-error: true       # generar reporte aunque haya fallos
- run: ./gradlew allureReport
- uses: actions/upload-artifact@v4
  with:
    name: allure-report
    path: build/reports/allure-report
```

> Para historial de tendencias, se conserva la carpeta `allure-results` entre ejecuciones
> (cache/artefacto) y se pasa a `allureReport`.

---

## 10.6 Ejercicios 🛠️

1. Ejecuta `.\gradlew.bat test --tests "*Modulo10*"` y confirma que se crea
   `build/allure-results` con archivos `.json`.
2. Genera el reporte con `.\gradlew.bat allureReport` y ábrelo
   (`build/reports/allure-report/index.html`).
3. Añade `@story:Healthcheck` y `@severity:minor` a un escenario y verifica que aparecen
   en la pestaña **Behaviors** de Allure.
4. Añade el adapter de Allure al Runner de otro módulo (p. ej. M8) y genera un reporte
   Allure de la suite de autenticación.

---

## ✅ Checklist del Módulo 10

- [ ] Entiendo la diferencia entre `allure-results` (datos) y el reporte HTML.
- [ ] Sé que el adapter se registra como **segundo plugin de Cucumber** en el Runner.
- [ ] Genero el reporte con `allureReport` / `allureServe`.
- [ ] Uso tags (`@severity`, `@story`) como metadatos de Allure.
- [ ] `.\gradlew.bat test --tests "*Modulo10*"` queda en verde y crea `build/allure-results`.

---

> Anterior: **[Módulo 9 — Reportes Serenity](../modulo-09-reportes-serenity/README.md)**
> · Siguiente: **[Módulo 11 — Buenas prácticas y CI](../modulo-11-buenas-practicas-ci/README.md)**
