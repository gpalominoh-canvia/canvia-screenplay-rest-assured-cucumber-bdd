# Guía de contribución

¡Gracias por contribuir! Este proyecto es un curso de automatización de pruebas de API.
Para mantener la calidad y la coherencia, sigue estas pautas.

## Requisitos previos

- **JDK 17+** con `JAVA_HOME` configurado.
- No necesitas instalar Gradle: usa el wrapper (`./gradlew` o `.\gradlew.bat`).

## Modelo de ramas (Git Flow)

| Rama | Propósito |
|------|-----------|
| `main` | Versiones publicadas (releases con tag). **No** se commitea directo. |
| `develop` | Integración del trabajo en curso. Base de las features. |
| `feature/*` | Una funcionalidad o módulo. Se abre desde `develop`. |
| `release/*` | Preparación de una versión (opcional). |
| `hotfix/*` | Correcciones urgentes sobre `main`. |

### Flujo para una contribución

```bash
git checkout develop
git pull
git checkout -b feature/mi-cambio

# ...trabaja y commitea...

git push -u origin feature/mi-cambio
# Abre un Pull Request hacia `develop`
```

## Convención de commits

Usamos **Conventional Commits** en **español**:

| Prefijo | Uso |
|---------|-----|
| `feat:` | nueva funcionalidad / módulo |
| `fix:` | corrección de un bug |
| `docs:` | documentación |
| `refactor:` | cambio interno sin alterar comportamiento |
| `test:` | añadir o ajustar pruebas |
| `chore:` | tareas varias (build, deps) |
| `ci:` | pipeline / GitHub Actions |

Ejemplo: `feat: módulo 12 con validación de contrato JSON Schema`.

> No agregues `Co-Authored-By` a los commits.

## Estándares de código

- **Español** en features, steps, tasks, questions y comentarios.
- Respeta la pirámide **Step → Task → Interaction** (ver
  [Módulo 11](docs/modulo-11-buenas-practicas-ci/README.md)).
- **Una carpeta por módulo**, con su propio `Runner`, ejecutable aislado.
- Escenarios **declarativos** (lenguaje de negocio), no imperativos.
- Nada de credenciales, tokens ni `.env` en el repositorio.

## Antes de abrir el PR

1. Que la suite pase en verde:
   ```bash
   ./gradlew clean test
   ```
2. Si tocaste un solo módulo, valida ese módulo:
   ```bash
   ./gradlew test --tests "*ModuloXX*"
   ```
3. Actualiza la documentación (`docs/`) y el [CHANGELOG.md](CHANGELOG.md) si aplica.

## Reportar errores o proponer mejoras

Abre un **Issue** usando las plantillas disponibles (bug / feature). Incluye pasos para
reproducir, comportamiento esperado y logs relevantes.
