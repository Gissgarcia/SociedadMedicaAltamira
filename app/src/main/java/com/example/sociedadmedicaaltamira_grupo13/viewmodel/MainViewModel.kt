package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sociedadmedicaaltamira_grupo13.model.User

class MainViewModel : ViewModel() {

    // Usuario actualmente logueado (null si no hay sesión)
    val currentUser = mutableStateOf<User?>(null)

    fun setCurrentUser(user: User) {
        currentUser.value = user
    }

    fun logout() {
        currentUser.value = null
    }
}
