package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.data.remote.RetrofitClient
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.LoginRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.RegistroRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.UsuarioResponse

/**
 * Habla con la API de usuario usando Retrofit.
 * La UI nunca toca Retrofit directo, siempre pasa por aquí.
 */
class AuthRepository {

    private val usuarioApi = RetrofitClient.createUsuarioService()

    suspend fun login(email: String, password: String): UsuarioResponse {
        val request = LoginRequest(
            email = email,
            password = password
        )
        return usuarioApi.login(request)
    }

    suspend fun register(
        nombre: String,
        email: String,
        password: String
    ): UsuarioResponse {
        // Como tu pantalla solo pide nombre/email/password,
        // llenamos los otros campos con vacío por ahora.
        val request = RegistroRequest(
            nombre = nombre,
            apellido = "",
            rut = "",
            email = email,
            telefono = "",
            password = password
        )
        return usuarioApi.registrarUsuario(request)
    }
}
