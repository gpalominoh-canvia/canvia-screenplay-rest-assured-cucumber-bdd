# language: es
@modulo02 @bookings
Característica: Consultar reservas (mecánica de Cucumber)

  Como QA quiero consultar reservas de la API
  para aprender la sintaxis completa de Gherkin y Cucumber.

  Antecedentes:
    Dado que la API de bookings está disponible

  @humo
  Escenario: La lista de reservas no está vacía
    Cuando consulto la lista de reservas
    Entonces obtengo una respuesta exitosa
    Y la lista contiene al menos 1 reserva

  Escenario: Consultar una reserva existente por su identificador
    Dado que existe al menos una reserva
    Cuando consulto esa reserva por su identificador
    Entonces obtengo una respuesta exitosa
    Y la reserva tiene nombre y apellido

  @parametrizado
  Esquema del escenario: Filtrar reservas por distintos nombres
    Cuando filtro las reservas por el nombre "<nombre>"
    Entonces obtengo una respuesta exitosa

    Ejemplos:
      | nombre |
      | Sally  |
      | John   |
      | Mary   |

  @filtro
  Escenario: Filtrar reservas con varios criterios (tabla de datos)
    Cuando filtro reservas con los siguientes criterios:
      | firstname | lastname |
      | Sally     | Brown    |
    Entonces obtengo una respuesta exitosa
