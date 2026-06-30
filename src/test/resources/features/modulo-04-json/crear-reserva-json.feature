# language: es
@modulo04 @json
Característica: Serialización JSON con POJOs

  Enviar un objeto Java como JSON y deserializar la respuesta de vuelta a un objeto.

  Escenario: Crear una reserva enviando un objeto Java como JSON
    Dado que preparo una reserva de "Giancarlo" "Palomino" por 250
    Cuando la envío para crear la reserva
    Entonces la reserva se crea correctamente
    Y la respuesta deserializada conserva el nombre "Giancarlo"
    Y la respuesta incluye un identificador de reserva
