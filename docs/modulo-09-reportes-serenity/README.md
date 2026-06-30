# Módulo 9 — Reportes Serenity, tags y configuración

> Objetivo: aprovechar el **reporte viviente** de Serenity, configurar el proyecto con
> `serenity.conf` (incluidos *environments*) y ejecutar pruebas de forma selectiva con tags.

> 📁 Configuración: [serenity.conf](../../src/test/resources/serenity.conf)
>
> ▶️ Genera el reporte: `.\gradlew.bat clean test` → abre `target/site/serenity/index.html`

---

## 9.1 El reporte viviente de Serenity

Tras cualquier ejecución, Serenity genera un reporte HTML en `target/site/serenity/`.
Es la gran ventaja frente a Cucumber "pelado":

| Sección | Qué muestra |
|---------|-------------|
| **Overall Test Results** | total de tests, % éxito, duración |
| **Requirements** | features agrupadas por funcionalidad |
| **Test detail** | cada escenario paso a paso |
| **REST Queries** | request y response HTTP completos de cada llamada |

> 💡 Ábrelo siempre tras una corrida. Para depurar un fallo, ve al escenario y mira la
> sección REST Query: verás el body que enviaste y el que te devolvieron. Oro puro.

---

## 9.2 serenity.conf de cabo a rabo

```hocon
serenity {
  project.name = "Canvia - Screenplay REST Assured Cucumber BDD"
  restquery.record.enabled = true   # registra cada request/response
  report.show.step.details = true
}

restapi {
  baseUrl = "https://restful-booker.herokuapp.com"
}
```

| Clave | Para qué |
|-------|----------|
| `serenity.project.name` | título del reporte |
| `serenity.restquery.record.enabled` | adjunta los detalles REST al reporte |
| `restapi.baseUrl` | URL base de la API |

> 💡 **Consejo:** a partir de aquí, lo ideal es leer `restapi.baseUrl` desde el código en
> vez de la constante de `ApiConfig`. Serenity lo expone con
> `EnvironmentSpecificConfiguration.from(env).getProperty("restapi.baseUrl")`.

---

## 9.3 Environments (multi-entorno)

El bloque `environments` permite correr la misma suite contra dev/staging/prod cambiando
**un solo flag**:

```hocon
environment = default
environments {
  default    { restapi.baseUrl = "https://restful-booker.herokuapp.com" }
  staging    { restapi.baseUrl = "https://staging.miapi.com" }
  production { restapi.baseUrl = "https://api.miapi.com" }
}
```

```powershell
.\gradlew.bat test -Denvironment=staging
```

> 🎯 Esto es clave en CI: el mismo código de pruebas valida varios entornos sin tocar Java.

---

## 9.4 Tags: ejecutar lo que quieras

Etiquetas en los `.feature` (`@humo`, `@auth`, `@modulo08`…) permiten filtrar:

```powershell
# Solo smoke
.\gradlew.bat test -Dcucumber.filter.tags="@humo"

# Bookings pero no los lentos
.\gradlew.bat test -Dcucumber.filter.tags="@bookings and not @lento"

# Varios tags (unión)
.\gradlew.bat test -Dcucumber.filter.tags="@auth or @e2e"
```

Operadores: `and`, `or`, `not`, paréntesis. Misma sintaxis que Cucumber.

> 💡 **Estrategia de tags recomendada:**
> - `@humo` → suite rápida de validación (corre en cada commit).
> - `@regresion` → suite completa (corre de noche / antes de release).
> - `@wip` → trabajo en progreso; **excluido** en CI con `not @wip`.

---

## 9.5 Ejecución selectiva: tags vs --tests

Dos formas de acotar qué corre:

| Mecanismo | Filtra por | Ejemplo |
|-----------|-----------|---------|
| `--tests` (Gradle) | clase Runner / módulo | `--tests "*Modulo08*"` |
| `-Dcucumber.filter.tags` (Cucumber) | tags en el Gherkin | `-Dcucumber.filter.tags="@auth"` |

Se pueden combinar:
```powershell
.\gradlew.bat test --tests "*Modulo08*" -Dcucumber.filter.tags="@auth"
```

---

## 9.6 Ejercicios 🛠️

1. Ejecuta solo los escenarios `@humo` de todo el curso y mira cuántos corren.
2. Cambia `serenity.project.name` y verifica que el reporte muestra el nuevo título.
3. Añade un tag `@contrato` a los escenarios de validación de contrato (M3) y córrelo solo.
4. Lee `restapi.baseUrl` desde `serenity.conf` en `ApiConfig` usando
   `EnvironmentSpecificConfiguration` y ejecuta con `-Denvironment=staging`.

---

## ✅ Checklist del Módulo 9

- [ ] Sé abrir e interpretar el reporte de Serenity (incluida la REST Query).
- [ ] Configuro el proyecto desde `serenity.conf`.
- [ ] Uso `environments` para cambiar de entorno con `-Denvironment=...`.
- [ ] Filtro ejecuciones con `-Dcucumber.filter.tags="..."`.
- [ ] Distingo `--tests` (Gradle) de los tags (Cucumber).

---

> Anterior: **[Módulo 8 — Autenticación](../modulo-08-auth/README.md)**
> · Siguiente: **[Módulo 10 — Reportes con Allure](../modulo-10-allure/README.md)**
