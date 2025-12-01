package com.example.sociedadmedicaaltamira_grupo13.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val token: String
)
