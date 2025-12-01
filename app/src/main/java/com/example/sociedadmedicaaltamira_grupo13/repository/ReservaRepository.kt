package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.data.remote.RetrofitClient
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse

/**
 * Repository de Reservas: orquesta las llamadas al microservicio de reservas.
 * La UI nunca llama Retrofit directamente, siempre pasa por aquí.
 */
class ReservaRepository {

    private val reservaApi = RetrofitClient.createReservaService()

    suspend fun crearReserva(request: ReservaRequest): ReservaResponse {
        return reservaApi.crearReserva(request)
    }

    suspend fun obtenerTodasReservas(): List<ReservaResponse> {
        return reservaApi.getTodasReservas()
    }

    suspend fun obtenerReservasPorUsuario(idUsuario: Long): List<ReservaResponse> {
        return reservaApi.getReservasPorUsuario(idUsuario)
    }

    suspend fun eliminarReserva(id: Long) {
        reservaApi.eliminarReserva(id)
    }
}
