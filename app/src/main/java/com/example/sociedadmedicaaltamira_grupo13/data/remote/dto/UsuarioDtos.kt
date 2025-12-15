package com.example.sociedadmedicaaltamira_grupo13.data.remote.dto
import com.google.gson.annotations.SerializedName
// Lo que envías al registrarte
data class RegistroRequest(
    @SerializedName("name")
    val nombre: String,

    @SerializedName("lastName")
    val apellido: String,

    @SerializedName("rut")
    val rut: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val telefono: String,

    @SerializedName("password")
    val password: String
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

