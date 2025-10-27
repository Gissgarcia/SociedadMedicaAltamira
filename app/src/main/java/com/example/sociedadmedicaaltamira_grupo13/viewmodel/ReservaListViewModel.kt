package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sociedadmedicaaltamira_grupo13.repository.ReservaRepository
import kotlinx.coroutines.flow.StateFlow
import com.example.sociedadmedicaaltamira_grupo13.model.Reserva

class ReservaListViewModel : ViewModel() {
    private val repo = ReservaRepository()
    val reservas: StateFlow<List<Reserva>> = repo.observeReservas()

    fun remove(id: Long) = repo.remove(id) // opcional
}
