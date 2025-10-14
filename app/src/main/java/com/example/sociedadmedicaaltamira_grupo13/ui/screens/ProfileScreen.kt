package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val items = listOf(Screen.Home, Screen.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(1) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            // Navegación directa con NavController (sin perder tu estructura original)
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(
                                text = when (screen) {
                                    Screen.Home -> "Inicio"
                                    Screen.Profile -> "Perfil"
                                    else -> screen.route
                                }
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = if (screen == Screen.Home) Icons.Filled.Home else Icons.Filled.Person,
                                contentDescription = screen.route
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "¡Bienvenido al Perfil!")
        }
    }


}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    SociedadMedicaAltamira_Grupo13Theme {
        // NavController simulado para la vista previa
        val navController = rememberNavController()
        val viewModel: MainViewModel = viewModel()
        ProfileScreen(navController = navController, viewModel = viewModel)
    }
}