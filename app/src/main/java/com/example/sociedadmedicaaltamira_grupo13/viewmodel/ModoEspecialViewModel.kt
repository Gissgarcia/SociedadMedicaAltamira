package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociedadmedicaaltamira_grupo13.data.SettingsDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ModoUiState(
    val enabled: Boolean = false,
    val isLoading: Boolean = true,      // carga inicial desde DataStore
    val isSaving: Boolean = false,
    val message: String? = null
)

class ModoEspecialViewModel(app: Application): AndroidViewModel(app) {
    private val ds = SettingsDataStore(app)

    private val _state = MutableStateFlow(ModoUiState())
    val state: StateFlow<ModoUiState> = _state.asStateFlow()

    init {
        // Carga inicial
        viewModelScope.launch {
            ds.modoEspecial.collect { saved ->
                _state.update { it.copy(enabled = saved, isLoading = false) }
            }
        }
    }

    fun toggle() = viewModelScope.launch {
        val next = !_state.value.enabled
        _state.update { it.copy(isSaving = true, message = null) }
        ds.setModoEspecial(next)
        _state.update { it.copy(enabled = next, isSaving = false, message = "Guardado con éxito") }
    }

    fun clearMessage() {
        _state.update { it.copy(message = null) }
    }
}
