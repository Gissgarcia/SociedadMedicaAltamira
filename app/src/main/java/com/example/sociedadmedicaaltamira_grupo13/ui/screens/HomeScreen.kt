package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.R
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel // ✅ El MISMO que viene desde MainActivity
) {
    val items = listOf(Screen.Home, Screen.Profile)
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo Altamira",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            viewModel.navigateTo(screen) // ✅ Evento de navegación
                        },
                        icon = {
                            Icon(
                                imageVector = if (screen == Screen.Home) Icons.Filled.Home else Icons.Filled.Person,
                                contentDescription = screen.route
                            )
                        },
                        label = { Text(if (screen == Screen.Home) "Inicio" else "Perfil") }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.topappbanner),
                contentDescription = "Banner App",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            Text(text = "¡Sociedad Médica Altamira!", color = Color(0xFF0D47A1))

            Button(onClick = { viewModel.navigateTo(Screen.Reserva) }) {
                Text("Reserva")
            }
            Button(onClick = { viewModel.navigateTo(Screen.Auth) }) {
                Text("Iniciar sesión / Registrarse")
            }
        }
    }
}
