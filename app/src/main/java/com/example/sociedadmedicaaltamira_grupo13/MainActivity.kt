package com.example.sociedadmedicaaltamira_grupo13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sociedadmedicaaltamira_grupo13.data.SettingsDataStore
import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.AuthScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.ModoEspecialScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.ProfileScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.ReservaListScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.ReservaScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SociedadMedicaAltamira_Grupo13Theme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                // 🔐 DataStore para restaurar sesión
                val context = LocalContext.current
                val settingsDataStore = remember { SettingsDataStore(context) }
                var hasCheckedSession by remember { mutableStateOf(false) }

                // Cargar sesión guardada (si existe) y setear MainViewModel.currentUser
                LaunchedEffect(Unit) {
                    settingsDataStore.userSessionFlow.collectLatest { session ->
                        if (session != null) {
                            viewModel.setCurrentUser(
                                User(
                                    id = session.id,
                                    name = session.name,
                                    email = session.email,
                                    role = session.role,
                                    token = session.token
                                )
                            )
                        }
                        hasCheckedSession = true
                    }
                }

                // Mientras no terminamos de revisar DataStore, mostrar loading
                if (!hasCheckedSession) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    // Elegimos pantalla inicial según si hay usuario logueado
                    val startDestination = if (viewModel.currentUser.value != null) {
                        Screen.Profile.route   // o Screen.Home.route si prefieres
                    } else {
                        Screen.Auth.route
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
                                            label = {
                                                Text(if (scr == Screen.Home) "Inicio" else "Perfil")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = startDestination,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Home.route) {
                                // HomeScreen(navController: NavController, viewModel: MainViewModel)
                                HomeScreen(navController, viewModel)
                            }
                            composable(Screen.Profile.route) {
                                // ProfileScreen(navController: NavController, viewModel: MainViewModel)
                                ProfileScreen(navController, viewModel)
                            }
                            composable(Screen.Reserva.route) {
                                // ReservaScreen(navController: NavController, mainViewModel: MainViewModel, ...)
                                ReservaScreen(navController, viewModel)
                            }
                            composable(Screen.Auth.route) {
                                // AuthScreen(navController: NavController, viewModel: MainViewModel, ...)
                                AuthScreen(navController, viewModel)
                            }
                            composable(Screen.ModoEspecial.route) {
                                ModoEspecialScreen(navController)
                            }
                            composable(Screen.ReservaList.route) {
                                // ReservaListScreen(navController: NavController, viewModel: MainViewModel, ...)
                                ReservaListScreen(navController, viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
