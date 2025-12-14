package com.example.sociedadmedicaaltamira_grupo13.data.remote.dto

// ✅ Requests actuales (si ya los tienes, puedes dejarlos igual)
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegistroRequest(
    val name: String,
    val email: String,
    val password: String
)

// ✅ Response de tu backend
data class UsuarioResponse(
    val token: String?,
    val userId: Long?,
    val name: String?,
    val email: String?,
    val role: String?
)
data class ForgotPasswordRequest(val email: String)

data class ResetPasswordRequest(
    val token: String,
    val newPassword: String
)

data class UpdateProfileRequest(
    val name: String,
    val email: String
)

data class SimpleMessageResponse(
    val message: String? = null,
    val token: String? = null
)
