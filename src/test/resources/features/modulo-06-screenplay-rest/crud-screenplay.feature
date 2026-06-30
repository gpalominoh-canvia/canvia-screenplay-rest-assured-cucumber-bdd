# language: es
@modulo06 @screenplay
Característica: Screenplay con REST - crear y consultar

  Usar las interactions Get y Post de Serenity Screenplay con Tasks de dominio.

  Escenario: Crear una reserva y volver a consultarla
    Dado que Giancarlo puede consumir la API de bookings
    Cuando crea una reserva a nombre de "Ana" "Torres" por 300
    Entonces la reserva se crea con un identificador
    Y al consultarla conserva el nombre "Ana"
