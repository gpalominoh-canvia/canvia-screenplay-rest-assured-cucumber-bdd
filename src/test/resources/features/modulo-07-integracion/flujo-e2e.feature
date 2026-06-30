# language: es
@modulo07 @e2e
Característica: Flujo end-to-end de reservas

  Encadenar varias Tasks con la memoria del actor (remember/recall) y leer la respuesta
  con una Question que deserializa a un POJO.

  Antecedentes:
    Dado que Giancarlo puede consumir la API de bookings

  Escenario: Crear y luego recuperar la misma reserva
    Cuando crea la reserva de "Luis" "Gomez" por 450
    Y consulta la reserva que acaba de crear
    Entonces la reserva recuperada tiene nombre "Luis" y precio 450
