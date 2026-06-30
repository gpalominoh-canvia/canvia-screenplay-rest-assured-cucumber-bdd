# Módulo 11 — Buenas prácticas y CI

> Objetivo: consolidar criterios de organización y calidad del proyecto, evitar
> anti-patrones, y ejecutar la suite automáticamente en **GitHub Actions** con sus reportes.

> 📁 Workflow: [.github/workflows/tests.yml](../../.github/workflows/tests.yml)

---

## 11.1 Organización del proyecto (lo que has construido)

```
src/test/java/canvia/
├── support/            # compartido: ApiConfig, model/ (POJOs)
├── hooks/              # @Before global (StageHooks)
├── shared/             # steps comunes (actor + ability)
└── moduloXX.../        # por módulo: Runner + Tasks + Questions + Steps
```

| Capa | Responsabilidad | Regla |
|------|-----------------|-------|
| **Feature** (`.feature`) | qué probar, en negocio | declarativo, sin detalles HTTP |
| **Step Definitions** | orquestar Tasks | finos; sin lógica REST |
| **Tasks** | objetivos de negocio | reutilizables, una intención |
| **Interactions** | acciones HTTP concretas | las trae Serenity |
| **Questions** | observar el sistema | devuelven, no validan |
| **Models** (POJOs) | datos | en `support/model` |

---

## 11.2 La pirámide de niveles (no la rompas)

```
Step → Task → Interaction        ✅ correcto
Step → Interaction               ⚠️ aceptable en casos triviales
Step → (lógica REST cruda)       ❌ evítalo en Screenplay
```

> 🎯 Si un step tiene más de 3-4 líneas o un `if`, probablemente deba ser una Task.

---

## 11.3 Anti-patrones frecuentes

| ❌ Anti-patrón | ✅ En su lugar |
|----------------|----------------|
| Escenarios imperativos (`POST /booking con body...`) | Lenguaje de negocio (`crea una reserva`) |
| Ids fijos (`/booking/1`) | Crear el dato o tomar uno real de la lista |
| Credenciales en el código | Variables de entorno / secretos |
| Escenarios dependientes entre sí | Cada escenario autónomo (crea sus datos) |
| `Thread.sleep(...)` | Esperas/condiciones explícitas |
| Validar solo el status | Validar el **efecto** (ej. GET → 404 tras borrar) |
| Un escenario gigante de 20 pasos | Varios escenarios enfocados |
| Datos hardcodeados que chocan | Datos dinámicos por ejecución |

---

## 11.4 Convenciones de este curso

- **Una carpeta por módulo**, autocontenida y ejecutable aislada (`--tests "*ModuloXX*"`).
- **Tags** consistentes: `@humo`, `@regresion`, `@wip`, `@<dominio>`.
- **Español** en features, steps, tasks y comentarios.
- **Constantes** para claves de memoria del actor (`ID_RESERVA`, `TOKEN`).
- Nada de `.env`, tokens ni secretos en el repo (ver [.gitignore](../../.gitignore)).

---

## 11.5 Integración Continua (GitHub Actions)

El workflow [tests.yml](../../.github/workflows/tests.yml) hace, en cada push/PR:

1. **Checkout** del código.
2. Instala **JDK 17** (Temurin).
3. Da permiso de ejecución al wrapper (`chmod +x ./gradlew`).
4. Ejecuta `./gradlew clean test` (con `continue-on-error` para no perder los reportes).
5. Genera el reporte **Allure** (`./gradlew allureReport`).
6. Publica como artefactos los reportes **Serenity** y **Allure**.

```yaml
- uses: actions/setup-java@v4
  with: { distribution: temurin, java-version: '17' }
- run: chmod +x ./gradlew
- run: ./gradlew clean test
  continue-on-error: true
- run: ./gradlew allureReport
  if: always()
- uses: actions/upload-artifact@v4
  with: { name: serenity-report, path: target/site/serenity }
```

> 💡 Tras cada corrida en GitHub, descarga el artefacto `serenity-report` o
> `allure-report` desde la pestaña **Actions** para ver el detalle.

> ⚠️ **Consejo CI:** en pipelines, corre primero la suite `@humo` (rápida) como gate, y
> la `@regresion` (completa) en un job nocturno o pre-release. Así el feedback es veloz.

---

## 11.6 Estrategia de ejecución recomendada

| Momento | Qué corre | Cómo |
|---------|-----------|------|
| Cada commit/PR | smoke | `-Dcucumber.filter.tags="@humo"` |
| Nightly / pre-release | todo | `clean test` |
| Local, un módulo | ese módulo | `--tests "*ModuloXX*"` |
| Por entorno | cualquiera | `-Denvironment=staging` |

---

## 11.7 Proyecto final 🛠️

Reúne todo lo aprendido en una **feature nueva y completa** (carpeta
`features/proyecto-final/`) que cubra, en estilo Screenplay:

1. Autenticación (`@auth`).
2. **CRUD completo** de una reserva: crear → consultar → actualizar → borrar → verificar 404.
3. Al menos un **escenario negativo** (actualizar sin token → 403).
4. Un **Scenario Outline** con 3 juegos de datos.
5. Validación de **contrato** (campos obligatorios y tipos).
6. Tags correctos y un Runner propio (`ProyectoFinalRunner`).
7. Que pase en verde y genere ambos reportes (Serenity + Allure).

> Cuando lo termines, habrás recorrido **todo** lo que el nombre del repo prometía:
> Screenplay + REST Assured + Cucumber + BDD. 🎓

---

## ✅ Checklist del Módulo 11

- [ ] Respeto la pirámide Step → Task → Interaction.
- [ ] Evito los anti-patrones de la tabla 11.3.
- [ ] Mis escenarios son autónomos y sin secretos en el código.
- [ ] Tengo el pipeline de GitHub Actions ejecutando la suite y publicando reportes.
- [ ] Completé el proyecto final en verde con ambos reportes.

---

> Anterior: **[Módulo 10 — Reportes con Allure](../modulo-10-allure/README.md)**
> · Volver al **[Índice del curso](../README.md)**
