package com.example.sociedadmedicaaltamira_grupo13.navigation.Screen

// ✅ Sealed class que define las rutas de navegación de toda la app
sealed class Screen(val route: String) {

    // 🔹 Rutas principales sin argumentos
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object Reserva : Screen("reserva")
    data object Auth : Screen("auth")   // Login / Registro

    // 🆕 Nueva pantalla: gestión de estado persistente (modo especial)
    data object ModoEspecial : Screen("modo_especial")

    // 🔹 Ejemplo de pantalla con argumento dinámico
    data class Detail(val itemId: String) : Screen("detail_page/{itemId}") {
        fun buildRoute(): String = route.replace("{itemId}", itemId)
    }
}
