package com.example.sociedadmedicaaltamira_grupo13.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL_USUARIO = "http://10.0.2.2:8081/"
    private const val BASE_URL_RESERVA = "http://10.0.2.2:8083/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Si tokenProvider es != null, agrega el header:
     * Authorization: Bearer <token>
     */
    private fun getHttpClient(tokenProvider: (() -> String?)? = null): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

        if (tokenProvider != null) {
            builder.addInterceptor { chain ->
                val original = chain.request()
                val token = tokenProvider()

                val newRequestBuilder = original.newBuilder()
                if (!token.isNullOrBlank()) {
                    newRequestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(newRequestBuilder.build())
            }
        }

        return builder.build()
    }

    // ---------- SERVICIO USUARIO (login / registro) ----------
    fun createUsuarioService(tokenProvider: (() -> String?)? = null): UsuarioApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_USUARIO)
            .client(getHttpClient(tokenProvider))   // normalmente sin token
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApiService::class.java)
    }

    // ---------- SERVICIO RESERVA (requiere JWT) ----------
    fun createReservaService(tokenProvider: (() -> String?)? = null): ReservaApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL_RESERVA)
            .client(getHttpClient(tokenProvider))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReservaApiService::class.java)
}

