# language: es
@modulo08 @auth
Característica: Autenticación y CRUD completo

  Obtener un token de autenticación y usarlo para actualizar y borrar una reserva.

  Antecedentes:
    Dado que Giancarlo puede consumir la API de bookings
    Y Giancarlo se autentica como administrador
    Y existe una reserva creada por Giancarlo

  Escenario: Actualizar una reserva existente
    Cuando actualiza esa reserva con el nombre "Carlos" y precio 999
    Entonces la reserva actualizada refleja el nombre "Carlos"

  Escenario: Borrar una reserva existente
    Cuando borra esa reserva
    Entonces la reserva ya no existe
