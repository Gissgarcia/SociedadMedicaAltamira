package com.example.sociedadmedicaaltamira_grupo13.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociedadmedicaaltamira_grupo13.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    val loggedInName: String? = null
)

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = AuthRepository()

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun updateName(v: String) { _state.value = _state.value.copy(name = v) }
    fun updateEmail(v: String) { _state.value = _state.value.copy(email = v) }
    fun updatePassword(v: String) { _state.value = _state.value.copy(password = v) }

    fun register() = viewModelScope.launch {
        val s = _state.value
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) {
            _state.value = s.copy(message = "Email no válido"); return@launch
        }
        if (s.password.length < 6) { _state.value = s.copy(message = "Clave mínima 6 caracteres"); return@launch }

        _state.value = s.copy(isLoading = true, message = null)
        repo.register(s.name, s.email, s.password)
            .onSuccess { _state.value = _state.value.copy(isLoading = false, message = "Registrado con éxito") }
            .onFailure { _state.value = _state.value.copy(isLoading = false, message = it.message) }
    }

    fun login() = viewModelScope.launch {
        val s = _state.value
        _state.value = s.copy(isLoading = true, message = null)
        repo.login(s.email, s.password)
            .onSuccess { _state.value = _state.value.copy(isLoading = false, loggedInName = it.name, message = "Bienvenido ${it.name}") }
            .onFailure { _state.value = _state.value.copy(isLoading = false, message = it.message) }
    }
}