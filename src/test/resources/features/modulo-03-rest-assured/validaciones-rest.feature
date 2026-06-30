# language: es
@modulo03 @rest
Característica: Validaciones con REST Assured

  Aprender a validar status, headers, cuerpo y a extraer datos con REST Assured.

  Antecedentes:
    Dado que existe una reserva conocida

  Escenario: Validar el contrato de una reserva
    Cuando pido el detalle de esa reserva
    Entonces el código de estado es 200
    Y la respuesta es de tipo JSON
    Y el cuerpo contiene los campos firstname, lastname y totalprice
    Y el precio total es mayor o igual a 0

  Escenario: El healthcheck responde rápido
    Cuando consulto el healthcheck
    Entonces el código de estado es 201
    Y la respuesta llega en menos de 5000 milisegundos
