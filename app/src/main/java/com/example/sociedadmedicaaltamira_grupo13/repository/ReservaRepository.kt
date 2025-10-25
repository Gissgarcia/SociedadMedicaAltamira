package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.model.Reserva

class ReservaRepository {
    suspend fun save(reserva: Reserva): Long {
        AppMemory.addReserva(reserva)
        return reserva.id
    }
    fun observeReservas() = AppMemory.reservas
}