package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.data.remote.RetrofitClient
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse
import com.example.sociedadmedicaaltamira_grupo13.session.Session

/**

 * La UI nunca llama Retrofit directamente, siempre pasa por aquí.
 */
class ReservaRepository {

    // Si dejaste el microservicio de reservas abierto (sin JWT), puedes crear el servicio sin token:
    private val reservaApi = RetrofitClient.createReservaService()

    // Si más adelante proteges con JWT, cambias a:
    // private val reservaApi = RetrofitClient.createReservaService { Session.token }

    suspend fun crearReserva(request: ReservaRequest): ReservaResponse {
        return reservaApi.crearReserva(request)
    }

    suspend fun obtenerTodasReservas(): List<ReservaResponse> {
        return reservaApi.getTodasReservas()
    }

    suspend fun obtenerReservasPorUsuario(): List<ReservaResponse> {
        val correo = Session.correo
            ?: error("Session.correo es null, el usuario no está cargado en Session")
        return reservaApi.getReservasPorUsuario(correo)
    }

    suspend fun eliminarReserva(id: Long) {
        reservaApi.eliminarReserva(id)
    }
}