package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ==========================
// 🚀 EVENTOS DE NAVEGACIÓN
// ==========================
sealed class NavigationEvent {
    data class NavigateTo(
        val route: Screen,
        val popUpToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = true
    ) : NavigationEvent()

    data object PopBackStack : NavigationEvent()
    data object NavigateUp : NavigationEvent()
}

// ==========================
// 🎯 VIEWMODEL PRINCIPAL
// ==========================
class MainViewModel : ViewModel() {

    // ====== 👤 USUARIO ACTUAL (de tu clase User.kt)
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun setCurrentUser(user: User?) {
        _currentUser.value = user
    }

    // ====== 🔁 NAVEGACIÓN GLOBAL
    private val _navigationEvents = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvents: StateFlow<NavigationEvent?> = _navigationEvents.asStateFlow()

    fun navigateTo(
        screen: Screen,
        popUpTo: Screen? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = true
    ) {
        _navigationEvents.value = NavigationEvent.NavigateTo(
            route = screen,
            popUpToRoute = popUpTo,
            inclusive = inclusive,
            singleTop = singleTop
        )
    }

    fun navigateTo(event: NavigationEvent.NavigateTo) {
        _navigationEvents.value = event
    }

    fun navigateBack() {
        _navigationEvents.value = NavigationEvent.PopBackStack
    }

    fun navigateUp() {
        _navigationEvents.value = NavigationEvent.NavigateUp
    }

    fun clearNavigationEvent() {
        _navigationEvents.value = null
    }
}
