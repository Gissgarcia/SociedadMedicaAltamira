package com.example.sociedadmedicaaltamira_grupo13.navigation.Screen

// ✅ Solo una sealed class Screen
sealed class Screen(val route: String) {

    // 🔹 Rutas principales sin argumentos
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object Reserva : Screen("reserva")
    data object Auth : Screen("auth")   // login / registro

    // 🔹 Ejemplo de pantalla con argumento (opcional)
    data class Detail(val itemId: String) : Screen("detail_page/{itemId}") {
        fun buildRoute(): String = route.replace("{itemId}", itemId)
    }
}
