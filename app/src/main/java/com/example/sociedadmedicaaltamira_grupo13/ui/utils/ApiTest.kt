package com.example.sociedadmedicaaltamira_grupo13.ui.utils

import com.example.sociedadmedicaaltamira_grupo13.data.remote.RetrofitClient
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ApiTest {

    fun probarLogin() {
        val service = RetrofitClient.createUsuarioService()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.login(
                    LoginRequest(
                        email = "genesis@example.com",
                        password = "123456"
                    )
                )

                // 👇 Cambiamos esto:
                // println("TOKEN recibido: ${response.token}")

                println("RESPUESTA login: $response")

            } catch (e: Exception) {
                println("ERROR login: ${e.message}")
            }
        }
    }
}
