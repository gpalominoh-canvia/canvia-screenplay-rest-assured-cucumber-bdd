# Módulo 2 — Cucumber en profundidad

> Objetivo: dominar la mecánica de Cucumber (cómo un `.feature` se conecta con Java) y
> toda la sintaxis útil de Gherkin: Background, Scenario Outline, Data Tables, hooks y tags.

> 📁 Código de este módulo:
> - Feature: [consultar-bookings.feature](../../src/test/resources/features/modulo-02-cucumber/consultar-bookings.feature)
> - Runner: [Modulo02Runner.java](../../src/test/java/canvia/modulo02cucumber/Modulo02Runner.java)
> - Steps: [ConsultarBookingsStepDefinitions.java](../../src/test/java/canvia/modulo02cucumber/ConsultarBookingsStepDefinitions.java)
>
> ▶️ Ejecútalo: `.\gradlew.bat test --tests "*Modulo02*"`

---

## 2.1 Las 3 piezas de Cucumber

```
  .feature  ──(texto Gherkin)──►  Step Definitions  ──(código Java)──►  API
     ▲                                   ▲
  qué probar                       cómo probarlo
                  Runner: arranca todo
```

1. **Feature** (`.feature`): escenarios en Gherkin (Módulo 1).
2. **Step Definitions** (`.java`): cada paso Gherkin se mapea a un método Java.
3. **Runner**: clase que le dice a Cucumber *dónde* están los features y los steps.

> 💡 En este módulo usamos **REST Assured** (vía `SerenityRest`) dentro de los steps.
> Todavía **no** usamos Screenplay: queremos que veas Cucumber "puro" primero.

---

## 2.2 El Runner

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/modulo-02-cucumber")   // dónde buscar .feature
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "canvia.modulo02cucumber")  // dónde están los steps
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
public class Modulo02Runner {}
```

| Anotación | Para qué |
|-----------|----------|
| `@SelectClasspathResource` | Carpeta de features de ESTE módulo |
| `GLUE_PROPERTY_NAME` | Paquete(s) con los Step Definitions (separa con coma) |
| `PLUGIN_PROPERTY_NAME` | Registra el reporter de Serenity (obligatorio) |

> ⚠️ **Consejo de oro:** si ves `Undefined step` o `No BaseStepListener`, casi siempre es
> el *glue* mal apuntado o el plugin de Serenity sin registrar.

---

## 2.3 Step Definitions: del texto al método

Gherkin:
```gherkin
Cuando consulto la reserva con id 5
```

Java:
```java
@Cuando("consulto la reserva con id {int}")
public void consulto(int id) { ... }
```

### Cucumber Expressions (parámetros)

| Expresión | Captura | Ejemplo Gherkin |
|-----------|---------|-----------------|
| `{int}` | entero | `...con id 5` → `5` |
| `{string}` | texto entre comillas | `...el nombre "Sally"` → `Sally` |
| `{word}` | una palabra sin comillas | `...estado activo` → `activo` |
| `{float}` | decimal | `...precio 99.9` |
| `reserva(s)` | plural opcional | matchea `reserva` y `reservas` |

> 💡 Las Cucumber Expressions son más legibles que las expresiones regulares.
> Úsalas salvo que necesites algo muy específico (ahí sí, regex con `/.../`).

---

## 2.4 Compartir estado entre steps

Un escenario suele tener varios pasos que comparten datos (la respuesta, un id…).
La forma correcta: **campos de instancia** en la clase de steps.

```java
public class ConsultarBookingsStepDefinitions {
    private Response respuesta;          // ← compartido entre steps del MISMO escenario
    private int idReservaExistente;
    ...
}
```

> ⚠️ Cucumber crea **una instancia nueva por escenario**: no hay fugas de estado entre
> escenarios. Para compartir entre *clases* de steps se usa inyección de dependencias
> (PicoContainer) — lo veremos si hace falta más adelante.

---

## 2.5 Background (Antecedentes)

Pasos que se ejecutan **antes de cada escenario** de la feature. Evita repetir:

```gherkin
Antecedentes:
  Dado que la API de bookings está disponible

Escenario: A ...
Escenario: B ...   # ambos ejecutan el Antecedentes primero
```

> 💡 Úsalo para precondiciones comunes (autenticación, datos base). No metas ahí
> acciones que solo necesita un escenario.

---

## 2.6 Scenario Outline (Esquema del escenario)

El **mismo** escenario ejecutado con varios juegos de datos. Evita copy-paste:

```gherkin
Esquema del escenario: Filtrar reservas por distintos nombres
  Cuando filtro las reservas por el nombre "<nombre>"
  Entonces obtengo una respuesta exitosa

  Ejemplos:
    | nombre |
    | Sally  |
    | John   |
    | Mary   |
```

- Los `<placeholders>` se reemplazan con cada fila de `Ejemplos`.
- Se ejecuta **una vez por fila** (aquí: 3 ejecuciones).

> 💡 Es la herramienta ideal para **data-driven testing**: validaciones de límites,
> equivalencias, varios usuarios, etc.

---

## 2.7 Data Tables (Tablas de datos)

Cuando un solo paso necesita **datos estructurados**:

```gherkin
Cuando filtro reservas con los siguientes criterios:
  | firstname | lastname |
  | Sally     | Brown    |
```

```java
@Cuando("filtro reservas con los siguientes criterios:")
public void filtro(DataTable tabla) {
    Map<String, String> criterios = tabla.asMaps().get(0); // {firstname=Sally, lastname=Brown}
    ...
}
```

Formas comunes de leer una `DataTable`:

| Método | Resultado |
|--------|-----------|
| `asMaps()` | `List<Map<String,String>>` (cabecera + filas) |
| `asList()` | `List<String>` (una columna) |
| `asLists()` | `List<List<String>>` (matriz cruda) |

> 💡 **Outline vs Data Table:** Outline repite *el escenario completo* con cada fila;
> Data Table pasa *varios datos a un solo paso*. No los confundas.

---

## 2.8 Hooks (@Before / @After)

Código que corre antes/después de cada escenario, sin ensuciar el Gherkin:

```java
public class StageHooks {
    @Before
    public void prepararEscenario() { OnStage.setTheStage(new OnlineCast()); }
}
```

- `@Before` / `@After`: antes/después de **cada** escenario.
- **Hooks con tag**: `@Before("@humo")` solo corre para escenarios `@humo`.
- `@BeforeStep` / `@AfterStep`: antes/después de **cada paso**.

> ⚠️ Importa los hooks de `io.cucumber.java.*`, **no** de JUnit. Es un error clásico.

---

## 2.9 Tags (Etiquetas)

Etiquetas para clasificar y filtrar escenarios:

```gherkin
@modulo02 @bookings
Característica: ...

  @humo
  Escenario: ...
```

Ejecutar por tag (lo veremos a fondo en el Módulo 9):
```powershell
.\gradlew.bat test -Dcucumber.filter.tags="@humo"
.\gradlew.bat test -Dcucumber.filter.tags="@bookings and not @lento"
```

> 💡 Convención útil: `@humo` (smoke), `@regresion`, `@wip` (work in progress, para excluir).

---

## 2.10 Ejercicios 🛠️

Trabaja sobre [consultar-bookings.feature](../../src/test/resources/features/modulo-02-cucumber/consultar-bookings.feature)
y su clase de steps.

1. Añade un escenario que verifique que **filtrar por un nombre inventado** devuelve una
   respuesta exitosa (status 200) aunque la lista venga vacía.
2. Convierte el escenario "consultar por id" en un **Scenario Outline** que pruebe que la
   reserva existente tiene `firstname` con 2 nombres distintos de los `Ejemplos`
   (pista: necesitarás un step que reciba `{string}` y compare).
3. Agrega un **hook con tag** `@Before("@filtro")` que imprima en consola
   `"Ejecutando filtro..."` y verifica en el reporte que solo corre en ese escenario.
4. Ejecuta solo los escenarios `@parametrizado`:
   `.\gradlew.bat test --tests "*Modulo02*" -Dcucumber.filter.tags="@parametrizado"`.

---

## ✅ Checklist del Módulo 2

- [ ] Sé qué hace cada una de las 3 piezas (feature, steps, runner).
- [ ] Uso Cucumber Expressions (`{int}`, `{string}`) en mis steps.
- [ ] Comparto estado entre steps con campos de instancia.
- [ ] Distingo **Background**, **Scenario Outline** y **Data Table**.
- [ ] Sé crear hooks (`@Before`/`@After`) y filtrar por **tags**.
- [ ] `.\gradlew.bat test --tests "*Modulo02*"` queda en verde.

---

> Anterior: **[Módulo 1 — BDD y Gherkin](../modulo-01-bdd-gherkin/README.md)**
> · Siguiente: **[Módulo 3 — REST Assured esencial](../modulo-03-rest-assured/README.md)**
