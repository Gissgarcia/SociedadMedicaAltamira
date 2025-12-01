package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociedadmedicaaltamira_grupo13.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    // NUEVO: datos reales devueltos por la API
    val userId: Long? = null,
    val role: String? = null,
    val token: String? = null
)

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    // ----- setters que usa AuthScreen -----
    fun updateName(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun updateEmail(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun updatePassword(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun clearMessage() {
        _state.value = _state.value.copy(message = null)
    }

    // ----- LOGIN -----
    fun login() {
        val current = _state.value
        if (current.email.isBlank() || current.password.isBlank()) {
            _state.value = current.copy(message = "Correo y contraseña son obligatorios")
            return
        }

        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, message = null)
            try {
                val response = repository.login(
                    email = current.email,
                    password = current.password
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    message = "Login exitoso",
                    name = response.name,
                    email = response.email,
                    userId = response.userId,
                    role = response.role,
                    token = response.token
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    message = e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    // ----- REGISTRO -----
    fun register() {
        val current = _state.value
        if (current.name.isBlank() || current.email.isBlank() || current.password.isBlank()) {
            _state.value = current.copy(message = "Nombre, correo y contraseña son obligatorios")
            return
        }

        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, message = null)
            try {
                val response = repository.register(
                    nombre = current.name,
                    email = current.email,
                    password = current.password
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    message = "Registro exitoso",
                    name = response.name,
                    email = response.email,
                    userId = response.userId,
                    role = response.role,
                    token = response.token
                )

            } catch (e: Exception) {
                _state.value = current.copy(
                    isLoading = false,
                    message = e.message ?: "Error al registrar usuario"
                )
            }
        }
    }
}
