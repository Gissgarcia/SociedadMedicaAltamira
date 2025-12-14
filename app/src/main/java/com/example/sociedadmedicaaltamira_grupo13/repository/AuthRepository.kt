package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.data.remote.RetrofitClient
import com.example.sociedadmedicaaltamira_grupo13.data.remote.UsuarioApiService
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.*

class AuthRepository {

    // ✅ como tu RetrofitClient usa createUsuarioService()
    private val api: UsuarioApiService = RetrofitClient.createUsuarioService()

    suspend fun login(email: String, password: String): UsuarioResponse {
        return api.login(
            LoginRequest(
                email = email.trim(),
                password = password
            )
        )
    }

    suspend fun register(name: String, email: String, password: String): UsuarioResponse {
        return api.register(
            RegistroRequest(
                name = name.trim(),
                email = email.trim(),
                password = password
            )
        )
    }

    suspend fun forgotPassword(email: String): SimpleMessageResponse {
        return api.forgotPassword(
            ForgotPasswordRequest(email.trim())
        )
    }

    suspend fun resetPassword(token: String, newPassword: String): SimpleMessageResponse {
        return api.resetPassword(
            ResetPasswordRequest(
                token = token.trim(),
                newPassword = newPassword
            )
        )
    }

    // ⚠️ updateProfile requiere JWT -> por eso creamos servicio con tokenProvider
    suspend fun updateProfile(jwt: String, name: String, email: String): UsuarioResponse {
        val cleanJwt = jwt.removePrefix("Bearer ").trim()

        val apiWithToken = RetrofitClient.createUsuarioService(tokenProvider = { cleanJwt })

        return apiWithToken.updateProfile(
            // puedes enviar token por header manual o dejar que el interceptor lo agregue
            token = "Bearer $cleanJwt",
            body = UpdateProfileRequest(
                name = name.trim(),
                email = email.trim()
            )
        )
    }
}
