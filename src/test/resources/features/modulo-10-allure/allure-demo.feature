# language: es
@modulo10 @allure
Característica: Reportes con Allure

  Demostrar la generación de reportes Allure en paralelo al reporte de Serenity.
  Los tags especiales (severity, story...) se convierten en metadatos en Allure.

  @severity:critical
  Escenario: El servicio responde para el reporte de Allure
    Dado que Giancarlo puede consumir la API de bookings
    Cuando consulta el estado del servicio para Allure
    Entonces el servicio responde con código 201 en Allure

  @severity:normal
  Escenario: Hay reservas para el reporte de Allure
    Dado que Giancarlo puede consumir la API de bookings
    Cuando pide la lista de reservas para Allure
    Entonces hay al menos 1 reserva en Allure
