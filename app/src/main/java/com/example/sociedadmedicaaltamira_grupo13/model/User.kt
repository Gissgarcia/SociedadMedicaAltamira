package com.example.sociedadmedicaaltamira_grupo13.model


data class User(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val email: String,
    val passwordHash: String
)