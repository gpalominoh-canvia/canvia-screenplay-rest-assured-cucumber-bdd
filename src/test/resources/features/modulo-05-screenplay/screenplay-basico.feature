# language: es
@modulo05 @screenplay
Característica: El patrón Screenplay

  Entender el modelo de actores: Abilities, Tasks, Interactions y Questions.

  Escenario: Un actor consulta el estado del servicio
    Dado que Giancarlo puede consumir la API de bookings
    Cuando consulta el estado del servicio
    Entonces el servicio responde con código 201

  Escenario: Un actor pregunta cuántas reservas hay
    Dado que Giancarlo puede consumir la API de bookings
    Cuando pide la lista de reservas
    Entonces ve que hay al menos 1 reserva
