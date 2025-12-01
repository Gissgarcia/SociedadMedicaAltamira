package com.example.sociedadmedicaaltamira_grupo13.data.remote.dto

// Lo que envías al registrarte
data class RegistroRequest(
    val nombre: String,
    val apellido: String,
    val rut: String,
    val email: String,
    val telefono: String,
    val password: String,
    val role: String = "CLIENT" // o el enum que uses en el backend
)

// Lo que envías al hacer login
data class LoginRequest(
    val email: String,
    val password: String
)

// Lo que el backend te devuelve (ajusta campos a tu respuesta real)
data class UsuarioResponse(
    val userId: Long,
    val name: String,
    val email: String,
    val role: String,
    val token: String
)

// Para listar usuarios (admin)
data class UsuarioListadoResponse(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val role: String
)
