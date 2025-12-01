package com.example.sociedadmedicaaltamira_grupo13.data.remote

import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservaApiService {

    @POST("api/reserva")
    suspend fun crearReserva(
        @Body request: ReservaRequest
    ): ReservaResponse

    @GET("api/reserva")
    suspend fun getTodasReservas(): List<ReservaResponse>

    @GET("api/reserva/usuario/{idUsuario}")
    suspend fun getReservasPorUsuario(
        @Path("idUsuario") idUsuario: Long
    ): List<ReservaResponse>

    @DELETE("api/reserva/{id}")
    suspend fun eliminarReserva(
        @Path("id") id: Long
    )
}
