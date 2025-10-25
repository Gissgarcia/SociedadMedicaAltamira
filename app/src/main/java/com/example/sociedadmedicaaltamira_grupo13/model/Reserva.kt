package com.example.sociedadmedicaaltamira_grupo13.model

data class Reserva(
    val id: Long = System.currentTimeMillis(),
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val docTipo: String,
    val docNumero: String,
    val email: String,
    val fechaMillis: Long,
    val createdAt: Long = System.currentTimeMillis()
)