# Módulo 0 — Setup del entorno

> Objetivo: dejar el proyecto funcionando y ejecutar la primera prueba con su reporte.
> Al terminar, entenderás qué hace cada archivo de configuración.

---

## 0.1 Requisitos

| Herramienta | Versión | Cómo verificar |
|-------------|---------|----------------|
| JDK | 17 o superior | `java -version` |
| Gradle | No necesitas instalarlo | usamos el **wrapper** (`gradlew`) |
| IDE | IntelliJ IDEA (recomendado) o VS Code | — |
| Git | cualquiera | `git --version` |

> ⚠️ Serenity BDD 4.x **requiere JDK 17+**. Si tu `java -version` muestra 1.8 (Java 8)
> en la terminal pero tu variable `JAVA_HOME` apunta a un JDK 17, el wrapper de Gradle
> usará `JAVA_HOME` y todo funcionará. Aun así, lo ideal es tener el 17 también en el PATH.

### Verifica tu JAVA_HOME (Windows PowerShell)

```powershell
echo $env:JAVA_HOME
# Debe apuntar a un jdk-17.x, por ejemplo:
# C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot
```

Si no lo tienes, instala **Eclipse Temurin 17** desde https://adoptium.net y define `JAVA_HOME`.

---

## 0.2 Estructura del proyecto

El proyecto está organizado **una carpeta por módulo**, para que cada parte del curso
sea autocontenida y se pueda ejecutar de forma aislada:

```
canvia-screenplay-rest-assured-cucumber-bdd/
├── build.gradle              ← dependencias y configuración de build
├── settings.gradle           ← nombre del proyecto
├── gradle.properties         ← ajustes de la JVM de Gradle
├── gradlew / gradlew.bat     ← wrapper: ejecuta Gradle sin instalarlo
├── gradle/wrapper/           ← jar + properties del wrapper (versión de Gradle)
├── docs/                     ← este curso, una carpeta por módulo
│   ├── modulo-00-setup/
│   ├── modulo-01-bdd-gherkin/
│   └── ...
└── src/test/
    ├── java/canvia/
    │   ├── hooks/            ← preparación compartida del Stage (Screenplay)
    │   └── modulo00setup/    ← código del Módulo 0: Runner + Steps
    │       ├── Modulo00Runner.java       ← arranca SOLO los features del módulo
    │       └── SmokeStepDefinitions.java ← "pegamento" Gherkin ↔ Java
    └── resources/
        ├── serenity.conf     ← URLs y configuración de Serenity
        └── features/
            └── modulo-00-setup/   ← los .feature de este módulo
```

> 📌 **Una carpeta por módulo.** En `docs/modulo-XX-...` está la teoría; en
> `src/test/java/canvia/moduloXX...` el código; en `features/modulo-XX-...` los
> escenarios. Cada módulo tiene su propio **Runner** para demostrarlo aislado.

> 📌 Todo el código vive en `src/test/` porque las pruebas automatizadas, técnicamente,
> son código de test. No hay `src/main`: no construimos una aplicación, la probamos.

---

## 0.3 Anatomía de `build.gradle`

Abre [build.gradle](../../build.gradle). Las piezas clave:

```gradle
plugins {
    id 'java'
    id 'net.serenity-bdd.serenity-gradle-plugin' version '5.3.9'  // tareas de reporte
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }    // fuerza Java 17
}

dependencies {
    testImplementation "net.serenity-bdd:serenity-core:5.3.9"
    testImplementation "net.serenity-bdd:serenity-screenplay:5.3.9"
    testImplementation "net.serenity-bdd:serenity-screenplay-rest:5.3.9"  // Screenplay + REST Assured
    testImplementation "net.serenity-bdd:serenity-cucumber:5.3.9"
    testImplementation "net.serenity-bdd:serenity-junit5:5.3.9"
    // ...JUnit Platform, AssertJ, logback
}

test {
    useJUnitPlatform()
    finalizedBy 'aggregate'   // genera el reporte HTML al terminar
}
```

Cada dependencia mapea a una pieza del temario:
- `serenity-screenplay` → patrón Screenplay (Módulo 5).
- `serenity-screenplay-rest` → Tasks REST como `Get`, `Post` (Módulo 6).
- `serenity-cucumber` → integra los `.feature` (Módulo 2).

---

## 0.4 Anatomía de `serenity.conf`

Abre [src/test/resources/serenity.conf](../../src/test/resources/serenity.conf).
Aquí centralizamos la **URL base** de la API para no repetirla en el código:

```hocon
restapi {
  baseUrl = "https://restful-booker.herokuapp.com"
}
```

En el Módulo 9 usaremos la sección `environments` para correr contra distintos entornos
(dev, staging, prod) con un solo flag.

---

## 0.5 Primera ejecución 🛠️

Desde la raíz del proyecto, en PowerShell, ejecuta **solo el Módulo 0**:

```powershell
.\gradlew.bat clean test --tests "canvia.modulo00setup.Modulo00Runner"
```

> 💡 Cada módulo se ejecuta aislado con su Runner: `--tests "*Modulo00*"`,
> `--tests "*Modulo01*"`, etc. Para correr **todo el curso**: `.\gradlew.bat clean test`.

La **primera vez** descargará Gradle 8.7 y todas las dependencias de Serenity
(puede tardar varios minutos). Las siguientes ejecuciones son rápidas.

Deberías ver algo como:

```
> Task :test
Característica: Smoke - la API de bookings responde
  Escenario: El healthcheck responde correctamente .......... PASSED

BUILD SUCCESSFUL
```

> Si ves `BUILD SUCCESSFUL`, ¡tu entorno está listo! 🎉

### ¿Qué acabas de ejecutar?

El smoke test ([smoke.feature](../../src/test/resources/features/modulo-00-setup/smoke.feature)) hace una
llamada `GET /ping` al healthcheck de restful-booker y valida que responde `201`.
Está escrito en estilo Screenplay (lo entenderás del todo en el Módulo 5); por ahora
basta con que **corra en verde**.

---

## 0.6 El reporte Serenity 🛠️

Tras ejecutar, abre en tu navegador:

```
target/site/serenity/index.html
```

Verás el **reporte viviente** de Serenity: resultados, pasos, y el detalle de cada
request/response HTTP. Este reporte es una de las grandes ventajas de Serenity sobre
Cucumber "pelado".

---

## 0.7 Comandos que usarás todo el curso

| Comando | Qué hace |
|---------|----------|
| `.\gradlew.bat clean test` | Limpia, ejecuta **todos** los módulos y genera el reporte |
| `.\gradlew.bat test --tests "*Modulo00*"` | Ejecuta **solo** ese módulo (cámbialo por 01, 02, …) |
| `.\gradlew.bat test` | Ejecuta sin limpiar (más rápido) |
| `.\gradlew.bat clean` | Borra `build/` y `target/` |

> En Linux/Mac sería `./gradlew` en vez de `.\gradlew.bat`.

---

## ✅ Checklist del Módulo 0

- [ ] `echo $env:JAVA_HOME` apunta a un JDK 17.
- [ ] `.\gradlew.bat clean test` termina en `BUILD SUCCESSFUL`.
- [ ] Abrí `target/site/serenity/index.html` y vi el escenario en verde.
- [ ] Entiendo qué hace cada carpeta de `src/test/`.
- [ ] Entiendo que la URL base vive en `serenity.conf`.

---

## ¿Problemas frecuentes?

| Síntoma | Causa / Solución |
|---------|------------------|
| `Unsupported class file major version` | Gradle usó Java 8. Verifica `JAVA_HOME=JDK17`. |
| El smoke falla con timeout/500 | restful-booker es gratuito y a veces "duerme". Reintenta. |
| `Could not find serenity-core:5.3.9` | Sin internet o repositorio bloqueado. Revisa tu red/proxy. |

---

> Siguiente: **[Módulo 1 — Fundamentos de BDD y Gherkin](../modulo-01-bdd-gherkin/README.md)**
