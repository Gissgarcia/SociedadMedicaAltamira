package com.example.sociedadmedicaaltamira_grupo13.data.remote

import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsuarioApiService {

    @POST("login")
    suspend fun login(@Body body: LoginRequest): UsuarioResponse

    @POST("registro")
    suspend fun register(@Body body: RegistroRequest): UsuarioResponse

    // ✅ NUEVO: forgot
    @POST("password/forgot")
    suspend fun forgotPassword(@Body body: ForgotPasswordRequest): SimpleMessageResponse

    // ✅ NUEVO: reset
    @POST("password/reset")
    suspend fun resetPassword(@Body body: ResetPasswordRequest): SimpleMessageResponse

    // ✅ NUEVO: update profile (requiere JWT)
    @PUT("me")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body body: UpdateProfileRequest
    ): UsuarioResponse
}
