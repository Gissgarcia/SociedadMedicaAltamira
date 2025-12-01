package com.example.sociedadmedicaaltamira_grupo13.data.remote

import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query



interface ReservaApiService {

    @POST("api/reservas")
    suspend fun crearReserva(
        @Body request: ReservaRequest
    ): ReservaResponse

    @GET("api/reservas")
    suspend fun getTodasReservas(): List<ReservaResponse>

    @GET("api/reservas/por-usuario")
    suspend fun getReservasPorUsuario(
        @Query("correo") correo: String
    ): List<ReservaResponse>

    @DELETE("api/reservas/{id}")
    suspend fun eliminarReserva(
        @Path("id") id: Long
    )
}

