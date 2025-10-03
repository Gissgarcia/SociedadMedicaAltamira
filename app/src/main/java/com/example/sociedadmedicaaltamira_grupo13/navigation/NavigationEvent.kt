package com.example.sociedadmedicaaltamira_grupo13.navigation

import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen

//Representa los distintos tipos de navegación

sealed class NavigationEvent {
    /**
     * Evento para navegar a un destino específico
     * @param route la [AppDestinatios] (objeto de ruta tipada) a la que navegar
     * @param popUpToRoute la ruta de destino en la pila de navegacion hasta la cual se debe hacer "pop"
     * Si es null, no se hace 'pop' a un destino especifico
     * @param inclusive si 'true', la ruta especificada en [popUpToRoute] tambien se elimina de la pila
     * @param singletop si 'true', evita multiples copias del mismo destino en la parte superior de la pila si ya esta presente(util para navegacion de barra inferior/lateral).
     */

    data class NavigateTo(
        val route: Screen, //ahora recibe un objeto AppDestinations (mas seguro)
        val popUpToRoute: Screen? = null, //Acepta un objeto appdestinations
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    /**
     * Evento para volver a la pantalla anterior en la pila de navegacion
     */

    object PopBackStack : NavigationEvent()

    /**
     * Evento para navegar hacia arriba en la jerarquia de la app
     * Generalmente es equivalente a [PopBackStack] a menos que se use un grafo de navegqacion con una jerarquia padre-hijo definida
     */

    object NavigateUp : NavigationEvent()

}
