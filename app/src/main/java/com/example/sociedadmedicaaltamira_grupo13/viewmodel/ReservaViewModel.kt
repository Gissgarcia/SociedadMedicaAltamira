package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse
import com.example.sociedadmedicaaltamira_grupo13.repository.ReservaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReservaUiState(
    val reservas: List<ReservaResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

class ReservaViewModel(
    private val repository: ReservaRepository = ReservaRepository()
) : ViewModel() {

    // ───────── Estado de lista ─────────
    private val _uiState = MutableStateFlow(ReservaUiState())
    val uiState: StateFlow<ReservaUiState> = _uiState.asStateFlow()

    // ───────── Estado del formulario ─────────
    var nombre by mutableStateOf("")
        private set
    var apellido by mutableStateOf("")
        private set
    var edad by mutableStateOf("")
        private set
    var tipoDocumento by mutableStateOf("")
        private set
    var numeroDocumento by mutableStateOf("")
        private set
    var correo by mutableStateOf("")
        private set
    var fechaReserva by mutableStateOf("")
        private set
    var especialidad by mutableStateOf("")
        private set

    var idUsuario: Long = 0L   // cuando tengas id de usuario logueado, lo asignas desde fuera

    // ───────── Actualizadores de formulario ─────────
    fun onNombreChange(v: String) { nombre = v }
    fun onApellidoChange(v: String) { apellido = v }
    fun onEdadChange(v: String) { edad = v }
    fun onTipoDocumentoChange(v: String) { tipoDocumento = v }
    fun onNumeroDocumentoChange(v: String) { numeroDocumento = v }
    fun onCorreoChange(v: String) { correo = v }
    fun onFechaReservaChange(v: String) { fechaReserva = v }
    fun onEspecialidadChange(v: String) { especialidad = v }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(error = null, successMessage = null)
    }

    // ───────── Crear reserva ─────────
    fun crearReserva() {
        val edadInt = edad.toIntOrNull() ?: 0

        val request = ReservaRequest(
            nombre = nombre,
            apellido = apellido,
            edad = edadInt,
            tipoDocumento = tipoDocumento,
            numeroDocumento = numeroDocumento,
            correo = correo,
            fechaReserva = fechaReserva,
            especialidad = especialidad,
            idUsuario = idUsuario
        )

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
            try {
                repository.crearReserva(request)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Reserva creada correctamente"
                )

                // opcional: limpiar formulario
                nombre = ""
                apellido = ""
                edad = ""
                tipoDocumento = ""
                numeroDocumento = ""
                // mantenemos correo si quieres
                fechaReserva = ""
                especialidad = ""

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al crear la reserva"
                )
            }
        }
    }

    // ───────── Cargar reservas de un usuario (Mis reservas) ─────────
    fun cargarReservasUsuario(idUsuario: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val lista = repository.obtenerReservasPorUsuario(idUsuario)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reservas = lista
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al obtener tus reservas"
                )
            }
        }
    }

    // ───────── Cargar todas las reservas (ADMIN) ─────────
    fun cargarTodasReservas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val lista = repository.obtenerTodasReservas()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reservas = lista
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al obtener las reservas"
                )
            }
        }
    }

    // ───────── Eliminar reserva ─────────
    fun eliminarReserva(id: Long) {
        viewModelScope.launch {
            try {
                repository.eliminarReserva(id)
                val nuevaLista = _uiState.value.reservas.filterNot { it.id == id }
                _uiState.value = _uiState.value.copy(reservas = nuevaLista)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al eliminar la reserva"
                )
            }
        }
    }
}
