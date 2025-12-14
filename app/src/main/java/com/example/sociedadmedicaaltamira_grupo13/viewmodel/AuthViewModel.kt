package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociedadmedicaaltamira_grupo13.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,

    val userId: Long? = null,
    val role: String? = null,
    val token: String? = null
)

class AuthViewModel : ViewModel() {

    // ✅ Usa tu repo real
    private val repo = AuthRepository()

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun updateName(v: String) = _state.update { it.copy(name = v) }
    fun updateEmail(v: String) = _state.update { it.copy(email = v) }
    fun updatePassword(v: String) = _state.update { it.copy(password = v) }

    fun clearMessage() = _state.update { it.copy(message = null) }

    // ✅ útil para cuando cambias entre Login/Registro o después de reset
    fun clearPassword() = _state.update { it.copy(password = "") }

    fun login() = viewModelScope.launch {
        val email = state.value.email.trim()
        val pass = state.value.password

        _state.update { it.copy(isLoading = true, message = null) }

        runCatching { repo.login(email, pass) }
            .onSuccess { res ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = "Login exitoso",
                        userId = res.userId,
                        role = res.role,
                        token = res.token
                    )
                }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, message = e.message ?: "Error en login") }
            }
    }

    fun register() = viewModelScope.launch {
        val name = state.value.name.trim()
        val email = state.value.email.trim()
        val pass = state.value.password

        _state.update { it.copy(isLoading = true, message = null) }

        runCatching { repo.register(name, email, pass) }
            .onSuccess { res ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = "Registro exitoso",
                        userId = res.userId,
                        role = res.role,
                        token = res.token
                    )
                }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, message = e.message ?: "Error en registro") }
            }
    }

    // ✅ forgot password -> muestra token demo (si tu backend lo devuelve)
    fun forgotPassword(email: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, message = null) }

        runCatching { repo.forgotPassword(email.trim()) }
            .onSuccess { res ->
                val token = res.token.orEmpty()
                val msg = buildString {
                    append(res.message ?: "Solicitud enviada.")
                    if (token.isNotBlank()) append("\nToken (demo): $token")
                }
                _state.update { it.copy(isLoading = false, message = msg) }
            }
            .onFailure { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = e.message ?: "Error al recuperar contraseña"
                    )
                }
            }
    }

    // ✅ reset password (por si haces pantalla Reset o lo llamas desde Forgot)
    fun resetPassword(token: String, newPassword: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, message = null) }

        runCatching { repo.resetPassword(token.trim(), newPassword) }
            .onSuccess { res ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = res.message ?: "Contraseña actualizada correctamente"
                    )
                }
                clearPassword()
            }
            .onFailure { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = e.message ?: "Error al restablecer contraseña"
                    )
                }
            }
    }
}
