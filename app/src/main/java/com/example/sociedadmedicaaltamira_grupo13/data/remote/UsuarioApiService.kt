package com.example.sociedadmedicaaltamira_grupo13.data.remote

import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.LoginRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.RegistroRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.UsuarioListadoResponse
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.UsuarioResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioApiService {

    @POST("api/usuario/registrar")
    suspend fun registrarUsuario(
        @Body request: RegistroRequest
    ): UsuarioResponse

    @POST("api/usuario/login")
    suspend fun login(
        @Body request: LoginRequest
    ): UsuarioResponse

    @GET("api/usuario/me")
    suspend fun getPerfil(): UsuarioResponse

    @GET("api/usuario")
    suspend fun getUsuarios(): List<UsuarioListadoResponse>

    @GET("api/usuario/{id}")
    suspend fun getUsuarioPorId(
        @Path("id") id: Long
    ): UsuarioListadoResponse
}
