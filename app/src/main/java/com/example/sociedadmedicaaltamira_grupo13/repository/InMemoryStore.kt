package com.example.sociedadmedicaaltamira_grupo13.repository

import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.model.Reserva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AppMemory {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())

    val users: StateFlow<List<User>> = _users
    val reservas: StateFlow<List<Reserva>> = _reservas

    fun addUser(u: User) { _users.value = _users.value + u }
    fun addReserva(r: Reserva) { _reservas.value = _reservas.value + r }
}