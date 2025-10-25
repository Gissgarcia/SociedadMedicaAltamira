package com.example.sociedadmedicaaltamira_grupo13.navigation

import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen

// ✅ Representa los distintos tipos de navegación
sealed class NavigationEvent {

    // Ir a una pantalla específica
    data class NavigateTo(
        val route: Screen,
        val popUpToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    // Volver atrás
    object PopBackStack : NavigationEvent()

    // Subir un nivel (similar a volver atrás)
    object NavigateUp : NavigationEvent()
}
