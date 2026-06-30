# language: es
Característica: Smoke - la API de bookings responde

  Escenario: El healthcheck responde correctamente
    Dado que la API de bookings está disponible
    Cuando consulto el estado del servicio
    Entonces el servicio responde con código 201
