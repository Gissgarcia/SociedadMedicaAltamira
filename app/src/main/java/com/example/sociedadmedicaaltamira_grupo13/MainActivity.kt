package com.example.sociedadmedicaaltamira_grupo13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sociedadmedicaaltamira_grupo13.navigation.NavigationEvent
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.*
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SociedadMedicaAltamira_Grupo13Theme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                // 🔁 Escucha centralizada de navegación
                LaunchedEffect(Unit) {
                    viewModel.navigationEvents
                        .filterNotNull()
                        .collectLatest { event ->
                            when (event) {
                                is NavigationEvent.NavigateTo -> {
                                    navController.navigate(event.route.route) {
                                        event.popUpToRoute?.let { pop ->
                                            popUpTo(pop.route) { inclusive = event.inclusive }
                                        }
                                        launchSingleTop = event.singleTop
                                        restoreState = true
                                    }
                                }
                                NavigationEvent.PopBackStack -> navController.popBackStack()
                                NavigationEvent.NavigateUp -> navController.navigateUp()
                                else -> Unit
                            }
                            viewModel.clearNavigationEvent()
                        }
                }

                // 🧭 Scaffold con barra inferior compartida (solo Home y Profile)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val routesWithBar = setOf(Screen.Home.route, Screen.Profile.route)
                        val backStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = backStackEntry?.destination?.route

                        if (currentRoute in routesWithBar) {
                            NavigationBar {
                                listOf(Screen.Home, Screen.Profile).forEach { scr ->
                                    val selected = currentRoute == scr.route
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            navController.navigate(scr.route) {
                                                popUpTo(Screen.Home.route) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (scr == Screen.Home)
                                                    Icons.Filled.Home else Icons.Filled.Person,
                                                contentDescription = scr.route
                                            )
                                        },
                                        label = { Text(if (scr == Screen.Home) "Inicio" else "Perfil") }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(navController = navController)
                        }
                        composable(Screen.Reserva.route) {
                            ReservaScreen(navController = navController)
                        }
                        composable(Screen.Auth.route) {
                            // 👇 Pasamos el MainViewModel para setear el usuario y navegar al perfil
                            AuthScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(Screen.ModoEspecial.route) {
                            ModoEspecialScreen(navController)
                        }
                        composable(Screen.ReservaList.route) {
                            ReservaListScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
