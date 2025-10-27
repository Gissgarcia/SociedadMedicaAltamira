package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.model.Reserva
import kotlinx.coroutines.flow.StateFlow
import com.example.sociedadmedicaaltamira_grupo13.repository.AppMemory



class ReservaRepository {
    suspend fun save(reserva: Reserva): Long {
        AppMemory.addReserva(reserva)
        return reserva.id
    }
    fun observeReservas(): StateFlow<List<Reserva>> = AppMemory.reservas
    fun remove(id: Long) = AppMemory.removeReserva(id)       // opcional
    fun clear() = AppMemory.clearReservas()                  // opcional
}
