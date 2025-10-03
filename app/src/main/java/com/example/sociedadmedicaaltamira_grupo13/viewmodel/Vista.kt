package com.example.sociedadmedicaaltamira_grupo13.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sociedadmedicaaltamira_grupo13.navigation.NavigationEvent
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    //'receiveAsFlow()':

    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()
    //funcion que emite el evento de navegacion hacia la ruta deseada
    fun navigateTo(screen: Screen){
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp(){
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.NavigateUp)
        }
    }
}