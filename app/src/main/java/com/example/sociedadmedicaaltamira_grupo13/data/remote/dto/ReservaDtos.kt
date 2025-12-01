package com.example.sociedadmedicaaltamira_grupo13.data.remote.dto

// Lo que envías al backend al crear una reserva
data class ReservaRequest(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipoDocumento: String,
    val numeroDocumento: String,
    val correo: String,
    val fechaReserva: String,
    val especialidad: String,
    val idUsuario: Long = 0L   // más adelante lo puedes llenar con el id real del usuario
)

// Lo que recibes del backend
data class ReservaResponse(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipoDocumento: String,
    val numeroDocumento: String,
    val correo: String,
    val fechaReserva: String,
    val especialidad: String,
    val idUsuario: Long?
)
