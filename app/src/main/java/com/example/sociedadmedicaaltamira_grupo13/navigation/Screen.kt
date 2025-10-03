package com.example.sociedadmedicaaltamira_grupo13.navigation.Screen

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_page")
    data object Profile : Screen(route = "profile_page")
    data object Settings : Screen(route = "settings_page")

    data class Detail(val itemId: String) : Screen(route = "detail_page/{itemId}") {
        //Función para construir la ruta final con el argumento. Esto evita errores al crear la ruta string
        fun buildRoute(): String {
            //Reemplaza el placeholder "{itemId}" en la ruta base con el valor real.
            return route.replace(oldValue = "{itemId}", newValue = itemId)

        }
    }
}